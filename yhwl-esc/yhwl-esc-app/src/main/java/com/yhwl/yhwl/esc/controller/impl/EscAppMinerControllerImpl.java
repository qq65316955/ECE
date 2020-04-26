package com.yhwl.yhwl.esc.controller.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.tools.core.collection.CollUtil;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.common.tools.core.util.StrUtil;
import com.yhwl.yhwl.esc.api.base.*;
import com.yhwl.yhwl.esc.api.entity.*;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import com.yhwl.yhwl.wallet.api.feign.RemoteWalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
@AllArgsConstructor
public class EscAppMinerControllerImpl extends BaseController {

	protected final RemoteWalletService remoteWalletService;

	public List<MiningShop> getMinerShopList() {
		return miningShopService.list();
	}

	public MiningMachine minerInfo(String id) throws HZException {
		MiningMachine miningMachine = miningMachineService.getById(id);
		if(!miningMachine.getUserName().equals(getUserName())){
			throw new HZException(LanguageResultCodeEnum.UNPRIVILEGED_OPERATION.getMessage(),LanguageResultCodeEnum.UNPRIVILEGED_OPERATION.getValue());
		}
		return miningMachine;
	}

	public List<MiningMachine> minerList() throws HZException {
		List<MiningMachine> list=miningMachineService.findByUserName(getUserName());
		return list;
	}

	public IPage<AccountDetails> minerDetails(int page) throws HZException {
		IPage<AccountDetails> accountDetailsIPage = null;
		String userName = getUserName();
		accountDetailsIPage = accountDetailsService.pageByUsernameOnType(new Page<AccountDetails>(page, 10), userName, InOrOutEnum.EXTR.getDescp());

		for(AccountDetails accountDetails : accountDetailsIPage.getRecords()){
			accountDetails.parseString();
		}
		return accountDetailsIPage;
	}

	public void extract(String id) throws HZException{
		MiningMachine miningMachine = miningMachineService.getById(id);
		if(miningMachine==null){
			throw new HZException(LanguageResultCodeEnum.MINER_NOT_FOUND.getMessage(),LanguageResultCodeEnum.MINER_NOT_FOUND.getValue());
		}
		if(!miningMachine.getUserName().equalsIgnoreCase(getUserName())){
			throw new HZException(LanguageResultCodeEnum.UNPRIVILEGED_OPERATION.getMessage(),LanguageResultCodeEnum.UNPRIVILEGED_OPERATION.getValue());
		}
		if(miningMachine.getBalance()<=0){
			throw new HZException(LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getMessage(),LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getValue());
		}
		double balance = miningMachine.getBalance();
		CurrencyBalance currencyBalance = currencyBalanceService.findByUserNameAndCoinName(getUserName(), CoinEnum.ECE.getDescp());
		currencyBalance.setBalance(currencyBalance.getBalance()+miningMachine.getBalance());
		boolean b = currencyBalance.updateById();
		if(!b){
			throw new HZException(LanguageResultCodeEnum.EXTRACT_FAIL.getMessage(),LanguageResultCodeEnum.EXTRACT_FAIL.getValue());
		}
		miningMachine.setBalance(0);
		boolean b1 = miningMachine.updateById();
		if(!b1){
			throw new HZException(LanguageResultCodeEnum.EXTRACT_FAIL.getMessage(),LanguageResultCodeEnum.EXTRACT_FAIL.getValue());
		}
		AccountDetails accountDetails = new AccountDetails();
		accountDetails.setUsername(getUserName());
		accountDetails.setAmount(balance);
		accountDetails.setCurrency(CoinEnum.ECE);
		accountDetails.setRemarks(miningMachine.getId());
		accountDetails.setInOrOut(InOrOutEnum.EXTR);
		accountDetails.setFromuser(FromUseEnum.MINER);
		boolean insert = accountDetails.insert();
		if(!insert){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void buyMiner(String id,String transactionPassword) throws HZException {
		MiningShop byId = miningShopService.getById(id);
		if(byId==null){
			throw new HZException(LanguageResultCodeEnum.PARAM_ERROR.getMessage(),LanguageResultCodeEnum.PARAM_ERROR.getValue());
		}
		if (byId.getCanSell()==0){
			throw new HZException(LanguageResultCodeEnum.MACHINE_NOT_SELL.getMessage(),LanguageResultCodeEnum.MACHINE_NOT_SELL.getValue());
		}
//		MiningMachine miningMachine1=miningMachineService.findByMaxSize(getUserName());
//
//		if(byId.getPrice()<miningMachine1.getPledgeNum()){
//			throw new HZException(LanguageResultCodeEnum.NOT_ALLOW_DOWN.getMessage(),LanguageResultCodeEnum.NOT_ALLOW_DOWN.getValue());
//		}

		AccountInfo accountInfo=accountInfoService.findByUserName(getUserName());
		if(byId.getCanSell()==0){
			throw new HZException(LanguageResultCodeEnum.INVALID_COMMODITY.getMessage(),LanguageResultCodeEnum.INVALID_COMMODITY.getValue());
		}
		if(StrUtil.isEmpty(getAppAccount().getTransactionPassword())){
			throw new HZException(LanguageResultCodeEnum.TRAN_PASSWORD_NOTSET.getMessage(),LanguageResultCodeEnum.TRAN_PASSWORD_NOTSET.getValue());
		}
		if(!getAppAccount().getTransactionPassword().equals(transactionPassword)){
			throw new HZException(LanguageResultCodeEnum.TRAN_PASSWORD_INCORRECT.getMessage(),LanguageResultCodeEnum.TRAN_PASSWORD_INCORRECT.getValue());
		}
		double totalPrice=byId.getPrice();
		CurrencyBalance byAddressAndCoinName = currencyBalanceService.findByUserNameAndCoinName(getUserName(), CoinEnum.ECE.getValue());
		if(byAddressAndCoinName.getBalance()-totalPrice<0){
			throw new HZException(LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getMessage(),LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getValue());
		}
		byAddressAndCoinName.setBalance(byAddressAndCoinName.getBalance()-totalPrice);
		boolean updateById = byAddressAndCoinName.updateById();
		if(!updateById){
			throw new HZException(LanguageResultCodeEnum.BUY_FAIL.getMessage(),LanguageResultCodeEnum.BUY_FAIL.getValue());
		}
		MiningMachine miningMachine = new MiningMachine();
		miningMachine.setUserName(getUserName());
		miningMachine.setCoin(byId.getCoin().getDescp());
		miningMachine.setPower(byId.getTCount());
		miningMachine.setModel(byId.getModel());
		miningMachine.setName(byId.getName());
		miningMachine.setUserId(accountInfo.getId());
		miningMachine.setPledgeNum(byId.getPrice());
		miningMachine.setRemainTime(System.currentTimeMillis()+86400000L*byId.getRound());
		miningMachine.setProfitPerday(byId.getProduce()/byId.getRound());
		miningMachine.setStatus(TransactionStatusEnum.SUCCESS.getDescp());
		boolean insert = miningMachine.insert();
		if(!insert){
			throw new HZException(LanguageResultCodeEnum.BUY_FAIL.getMessage(),LanguageResultCodeEnum.BUY_FAIL.getValue());
		}
		accountProfitService.updatPledgeByUserName(getUserName(),byId.getTCount());
		accountProfitService.updatTotalPledgeByUserName(getUserName(),byId.getTCount());
		//推广奖
		List<UserLevel> userLevels=userLevelService.findAllUp(getUserName());
		for(UserLevel userLevel:userLevels){
			AccountInfo accountInfo1=accountInfoService.findByUserName(userLevel.getLeftName());
			if(!accountInfo1.isAuth()){
				continue;
			}
			List<MiningMachine> leftMachines=miningMachineService.findByUserName(userLevel.getLeftName());
			if(CollUtil.isEmpty(leftMachines)){
				continue;
			}
			double price=byId.getPrice();
			if(price>leftMachines.get(0).getPledgeNum()){
				price=leftMachines.get(0).getPledgeNum();
			}
			double reward=0.0d;
			List<UserLevel> levels=userLevelService.findByUsernameOnDownAndLevel(userLevel.getLeftName(),1);
			CurrencyBalance currencyBalance=currencyBalanceService.findByUserNameAndCoinName(userLevel.getLeftName(),byId.getCoin().getDescp());
			int i=0;
			if(CollUtil.isNotEmpty(levels)){
				i=levels.size();
			}
			switch (userLevel.getLevelNum()){
				case  1: if(i>=1) {
					reward=price*0.07;
				} break;
				case  2: if(i>=2){reward=price*0.03;} break;
				case  3: if(i>=3){reward=price*0.05;} break;
				case  4: if(i>=4){reward=price*0.01;} break;
				case  5: if(i>=5){reward=price*0.01;} break;
				default:break;
			}
			if(userLevel.getLevelNum()>=6&&userLevel.getLevelNum()<=9&&i>=6){
				reward=price*0.01;
			}
			if(reward==0){
				continue;
			}
			currencyBalance.setBalance(currencyBalance.getBalance()+reward);
			currencyBalance.updateById();
			AccountDetails accountDetails=new AccountDetails();
			accountDetails.setCurrency(byId.getCoin());
			accountDetails.setFromuser(FromUseEnum.USER);
			accountDetails.setInOrOut(InOrOutEnum.PUSH);
			accountDetails.setUsername(userLevel.getLeftName());
			accountDetails.setAmount(reward);
			accountDetails.setRemarks(getUserName());
			accountDetails.insert();
		}
		guiji(byAddressAndCoinName,totalPrice);

	}

	@Async
	public void guiji(CurrencyBalance srcCurrencyBalance,double srcUseAmount){
		try{
			R r = walletService.getBalance(remoteWalletService, srcCurrencyBalance.getAddress(), "ETH", "ETH");
			if(r != null && (r.getData() instanceof Double || r.getData() instanceof  Float)){
				double ethBalance = Double.parseDouble(r.getData().toString());
				if(ethBalance < 0.00056){
					walletService.sendCenter(remoteWalletService,srcCurrencyBalance.getAddress(),"ETH","ETH","",0.00056);
					Thread.sleep(60000);
				}
				walletService.sendCoin(remoteWalletService,srcCurrencyBalance.getAddress(),"0x638545ac7Fa9277859d20df52B6558fE7BEF9E27","ECE",srcUseAmount,"ETH","");
			}
		}catch (Exception e){
			log.error("归集失败：{},{}", JSONObject.toJSONString(srcCurrencyBalance),e.getMessage());
		}
	}

	public IPage<MiningMachine> pledgeDetails(int page,String type) throws HZException{
		IPage<MiningMachine> page1=miningMachineService.findPageByType(new Page<MiningMachine>(page,10),getUserName(),type);
		if(CollUtil.isNotEmpty(page1.getRecords())){
			for(MiningMachine miningMachine:page1.getRecords()){
				long nowTime=miningMachine.getRemainTime();
				if(nowTime<System.currentTimeMillis()){
					miningMachine.setStatus(TransactionStatusEnum.OUT.getDescp());
				}
			}
		}
		return page1;
	}
}
