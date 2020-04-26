package com.yhwl.yhwl.esc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.log.annotation.SysLog;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.base.TransactionStatusEnum;
import com.yhwl.yhwl.esc.api.entity.CurrencyBalance;
import com.yhwl.yhwl.esc.api.entity.TransactionRecord;
import com.yhwl.yhwl.esc.api.inteface.EscBackTransactionControllerInterface;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import com.yhwl.yhwl.wallet.api.feign.RemoteWalletService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "esc", tags = "esc 交易接口")
@RequestMapping("/escadmin/transaction")
@RestController
@AllArgsConstructor
@Slf4j
public class EscBackTransactionController extends BaseController implements EscBackTransactionControllerInterface {

    protected final RemoteWalletService remoteWalletService;

    private final RedisTemplate redisTemplate;

    @Override
    @SysLog(value = "bmj_transactionRecordData_交易列表")
    public R transactionRecordData(Page page, String sendUser, String getUser, String coin, String status ) {
        return R.ok(transactionRecordService.findByPage(page,sendUser,getUser,coin,status));
    }

    @Override
    @SysLog(value = "bmj_transferCharges_转入手续费")
    @Transactional(rollbackFor = Exception.class)
    public R transferCharges(String id) {
        try{
            TransactionRecord byId = transactionRecordService.getById(id);
            if(byId==null){
                return R.failed("交易不存在");
            }
            if(byId.getCoin() == CoinEnum.BMJ){
                return R.failed("该币种不需要拨手续费");
            }
            R getBalance= walletService.getBalance(remoteWalletService,byId.getSendAddress(),"ETH", "ETH");
            Double balance=(Double) getBalance.getData();
            if(balance>=0.0056){
                return R.ok(null,"发送地址手续费充足");
            }
            R sendCoin=null;
            if("BMJcoin".equalsIgnoreCase(byId.getCoin().getDescp())) {
                 //sendCoin = walletService.sendCoin(remoteWalletService, byId.getSendAddress(), byId.getGetAddress(),"ETH", 0.0056, "ETH", null);

				sendCoin = walletService.sendCenter(remoteWalletService,byId.getSendAddress(), byId.getCoin().getDescp(), "ETH", "", 0.0056);
            }
            if(sendCoin.getCode()==0){
                byId.setStatus(TransactionStatusEnum.PACKAGEING);
                byId.updateById();
                return R.ok(null,"发送手续费成功");
            }
            return R.failed("交易失败");
        }catch (Exception e){
            log.error("转入手续费异常-->{}",e);
            return R.failed("交易失败");
        }
    }

    @Override
    @SysLog(value = "bmj_sendTransaction_通过转账")
    @Transactional(rollbackFor = Exception.class)
    public R sendTransaction(String id) throws InterruptedException {
    	Object o= null;
		try {
			o = redisTemplate.opsForValue().get(id);
		} catch (Exception e) {
			Thread.sleep(1000);
		}
        try{
            TransactionRecord byId = transactionRecordService.getById(id);
            if(byId==null){
                return R.failed("交易不存在");
            }
            if(o!=null){
				return R.failed("交易正在进行中，请勿重复提交");
			}
            redisTemplate.opsForValue().set(id,System.currentTimeMillis(),30);
			CurrencyBalance currencyBalance=currencyBalanceService.findByAddressAndCoinName(byId.getGetAddress(),byId.getCoin().getDescp());
			if(currencyBalance!=null){
				byId.setStatus(TransactionStatusEnum.SUCCESS);
				byId.updateById();
				currencyBalance.setBalance(currencyBalance.getBalance()+byId.getGamount());
				boolean f=currencyBalance.updateById();
				if(f){
					return R.ok("转账成功");
				}
				return R.failed(null,"转账失败");
			}
			R getBalance = walletService.getBalance(remoteWalletService, byId.getSendAddress(), "ETH", "ETH");
            //获取链上余额
            double balance=0.0d;
            if(getBalance.getCode()==0) {
                balance = (double) getBalance.getData();
            }
            if(getBalance.getCode()==1){
            	return R.failed("获取链上余额失败");
			}
            //判断链上余额是否够当前转账金额
            if(balance<0.00056){

                R sendCenter = walletService.sendCenter(remoteWalletService,byId.getSendAddress() , "ETH", "ETH","",0.00056);
                if(sendCenter.getCode()==0){
					byId.setStatus(TransactionStatusEnum.PENDING);
					byId.updateById();
					return R.ok(null,"拨款中");
				}
                return R.failed("拨款失败");
            }
            R sendCoin = walletService.sendCoin(remoteWalletService, byId.getSendAddress(), byId.getGetAddress(), byId.getCoin().getDescp(), byId.getGamount() , "ETH","");
            if(sendCoin.getCode()==0){
                byId.setStatus(TransactionStatusEnum.SUCCESS);
                byId.updateById();
                return R.ok(null,"成功");
            }
            return R.failed("交易失败");
        }catch (Exception e){
            return R.failed("交易失败");
        }
    }

    @Override
    @SysLog(value = "bmj_refuseTransaction_拒绝转账")
    @Transactional(rollbackFor = Exception.class)
    public R refuseTransaction(String id) {
        try{
            TransactionRecord byId = transactionRecordService.getById(id);
            if(byId==null){
                throw new HZException("交易不存在");
            }
            if(byId.getStatus()==TransactionStatusEnum.WAITFILE){
                throw new HZException("不可重复拒绝");
            }
            byId.setStatus(TransactionStatusEnum.WAITFILE);
            boolean b = byId.updateById();
            if(!b){
                throw new HZException("操作失败");
            }
            CurrencyBalance bmJcoin = currencyBalanceService.findByUserNameAndCoinName(byId.getSendUser(), byId.getCoin().getValue());
            if(bmJcoin==null){
                throw new HZException("该用户无钱包地址");
            }
            bmJcoin.setBalance(bmJcoin.getBalance()+byId.getSamount());
            boolean b1 = bmJcoin.updateById();
            if(!b1){
                throw new HZException("操作失败");
            }
            return R.ok(null,"操作成功").setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
        }catch (HZException e){
            return R.failed(e.getMessage());
        }
    }

    private double getFree(double amount){
        double price = (amount)*0.01;
        if(price < 0.5){
            price = 0.5;
        }
        if(price > 2){
            price = 2;
        }
        return amount + price;
    }
}
