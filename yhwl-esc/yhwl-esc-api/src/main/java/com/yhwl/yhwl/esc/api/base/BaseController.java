package com.yhwl.yhwl.esc.api.base;


import com.yhwl.yhwl.common.security.util.SecurityUtils;
import com.yhwl.yhwl.common.tools.core.util.StrUtil;
import com.yhwl.yhwl.esc.api.entity.*;
import com.yhwl.yhwl.esc.api.service.*;
import com.yhwl.yhwl.esc.api.tools.EseTools;
import com.yhwl.yhwl.esc.api.tools.RelieveAsync;
import com.yhwl.yhwl.esc.api.tools.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BaseController {
    public final String appConfigID = "191e41e0166347b594b05b498d87eb2d";

    @Autowired
	EseTools eseTools;

    @Autowired
    protected RelieveConfigService relieveConfigService;

    @Autowired
	protected EnergyBallService energyBallService;

    @Autowired
	protected BallPoolService ballPoolService;

    @Autowired
	protected IdCardService idCardService;

    @Autowired
	protected MiningShopService miningShopService;

    @Autowired
    protected UntyingMinerService untyingMinerService;

    @Autowired
	protected PayCheckService payCheckService;

    @Autowired
    protected PledgeLogService pledgeLogService;

    @Autowired
	protected BallOrderService ballOrderService;

    @Autowired
    protected AccountDetailsService accountDetailsService;

    @Autowired
    protected AccountProfitService accountProfitService;

    @Autowired
    protected AppGlobalVariableService appGlobalVariableService;

    @Autowired
    protected CoinListService coinListService;

    @Autowired
    protected CurrencyBalanceService currencyBalanceService;

    @Autowired
    protected InformationService informationService;

    @Autowired
    protected LockProfitService lockProfitService;

    @Autowired
    protected MiningMachineService miningMachineService;

    @Autowired
    protected PledgeDetailsService pledgeDetailsService;

    @Autowired
    protected TransactionRecordService transactionRecordService;

    @Autowired
    protected UserLevelService userLevelService;

    @Autowired
    protected AccountInfoService accountInfoService;

    @Autowired
    protected MinerRentalService minerRentalService;

    @Autowired
    protected RelieveDetailsService relieveDetailsService;

    @Autowired
    protected RelieveAsync relieveAsync;

    @Autowired
    protected SMSCodeService smsCodeService;

    @Autowired
    protected BmjProductService bmjProductService;

    @Autowired
    protected FireMinerDestroyService fireMinerDestroyService;



    public WalletService walletService = new WalletService();

    protected String getUserName(){
        return SecurityUtils.getUser() == null ? null : SecurityUtils.getUser().getUsername();
    }

    protected CurrencyBalance getAppWallet(String coin){
        return currencyBalanceService.findByUserNameAndCoinName(getUserName(), coin);
    }

    protected MiningMachine getAppMiner(String mac){
        return miningMachineService.findByMac(mac);
    }
    protected MinerRental getRentalMiner(String mac){
    	return minerRentalService.findByMac(mac);
	}

    protected AccountInfo getAppAccount(){
        return this.accountInfoService.findByUserName(getUserName());
    }

    /**
     * 短信发送策略
     * 1.数据库没有记录
     * 2.已有数据，但已超过最低发送间隔
     * @param phone
     * @param type
     * @return
     */
    public boolean canSendSMS(String phone,String type){
        if(StrUtil.isEmpty(phone) && StrUtil.isNotEmpty(getUserName())){
            phone = getAppAccount().getPhone();
        }
        SMSCode byPhoneOnType = smsCodeService.findByPhoneOnType(phone, type);
        if(byPhoneOnType==null){
            return true;
        }
        Long s = (System.currentTimeMillis() - byPhoneOnType.getCreateDate().getTime()) / (1000 * 60);
        if(s>1){
            byPhoneOnType.deleteById();
            return true;
        }
        return false;
    }

    /**
     * 校验手机短信验证码
     * 1.是否存在
     * 2.若存在校验是否正确
     *
     * 若错误3次以上，则删除记录
     * @param phone
     * @param type
     * @return
     */
    public boolean checkSMS(String phone,String type,String code){
        SMSCode byPhoneOnType = smsCodeService.findByPhoneOnType(phone, type);
        if(byPhoneOnType==null){
            return false;
        }
        if(!byPhoneOnType.getCode().equals(code)){
            byPhoneOnType.setFaileCount(byPhoneOnType.getFaileCount()+1);
            byPhoneOnType.updateById();
            return false;
        }
        if(byPhoneOnType.getFaileCount()>=3){
            byPhoneOnType.deleteById();
            return false;
        }
        byPhoneOnType.deleteById();
        return true;
    }

}
