package com.yhwl.yhwl.esc.controller.impl;

import cn.hutool.core.util.RandomUtil;
import com.yhwl.yhwl.admin.api.dto.UserInfo;
import com.yhwl.yhwl.admin.api.feign.RemoteUserService;
import com.yhwl.yhwl.common.core.constant.SecurityConstants;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.tools.constant.RegExConstant;
import com.yhwl.yhwl.common.tools.core.collection.CollUtil;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.common.tools.core.lang.MnemonicWordUtil;
import com.yhwl.yhwl.common.tools.core.util.ReUtil;
import com.yhwl.yhwl.common.tools.core.util.StrUtil;
import com.yhwl.yhwl.esc.api.base.*;
import com.yhwl.yhwl.esc.api.entity.*;
import com.yhwl.yhwl.esc.api.tools.machine.MachineTools;
import com.yhwl.yhwl.esc.tools.ZhuTongSmsTools;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import com.yhwl.yhwl.wallet.api.feign.RemoteWalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class EscAppUnauthControllerImpl extends BaseController {
	protected final RemoteUserService remoteUserService;


	protected final RemoteWalletService remoteWalletService;

	@Autowired
	private Environment environment;

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> register(String username, String password, String transactionPassword,String fromuser, String phone, String code,String country) throws HZException {
		int roleId = Integer.valueOf(environment.getProperty("esc.client.role_id"));
		int deptId = Integer.valueOf(environment.getProperty("esc.client.dept_id"));
		String usernamePrefix = environment.getProperty("esc.client.username_prefix");
		R user=walletService.getBalance(remoteWalletService,username,"ETH","ETH");
		if(user.getCode()==1){
			throw new HZException(LanguageResultCodeEnum.USERNAME_ERROR.getMessage(),LanguageResultCodeEnum.USERNAME_ERROR.getValue());
		}
		username = usernamePrefix+username;
		boolean register = checkSMS(phone, "REGISTER", code);
		if(!register){
			throw new HZException(LanguageResultCodeEnum.VERIFICATION_INCORRECT.getMessage(),LanguageResultCodeEnum.VERIFICATION_INCORRECT.getValue());
		}
		AccountInfo accountInfo1=accountInfoService.findByUserName(username);
		if(accountInfo1!=null){
			throw new HZException(LanguageResultCodeEnum.USER_EXISTS.getMessage(),LanguageResultCodeEnum.USER_EXISTS.getValue());
		}
//		基本格式验证
		if(!ReUtil.isMatch(RegExConstant.MOBILE_PHONE, phone)){
			throw new HZException(LanguageResultCodeEnum.PHONE_FORMAT_ERROR.getMessage(),LanguageResultCodeEnum.PHONE_FORMAT_ERROR.getValue());
		}
		if(StrUtil.checkPassword(password) <= 1){
			throw new HZException(LanguageResultCodeEnum.PASSWORD_STRENGTH_INSUFFICIENT.getMessage(),LanguageResultCodeEnum.PASSWORD_STRENGTH_INSUFFICIENT.getValue());
		}
		//feign调用的是查询接口，所以这里若返回的是成功，则代表查询成功，也就是数据已存在的意思
		R<UserInfo> info = remoteUserService.info(username, SecurityConstants.FROM_IN);
		if(info.getCode()==0){
			throw new HZException(LanguageResultCodeEnum.USER_EXISTS.getMessage(),LanguageResultCodeEnum.USER_EXISTS.getValue());
		}
		/**
		 * 添加用户，角色ID默认为999，若后台ID不为999，请手动修改,部门ID为14
		 */
		AccountInfo byInvitationCode = accountInfoService.findByInvitationCode(fromuser);
		if(byInvitationCode==null){
			throw new HZException(LanguageResultCodeEnum.REFERRER_NOT_EXISTS.getMessage(),LanguageResultCodeEnum.REFERRER_NOT_EXISTS.getValue());
		}
		//创建帐号基本信息
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setUsername(username);
		accountInfo.setPhone(phone);
		accountInfo.setCountry(country);
		accountInfo.setTransactionPassword(transactionPassword);
		//生成邀请码
		accountInfo.setInvitationCode(createInvitationCode());
		boolean insert = accountInfo.insert();
		if(!insert){
			throw new HZException(LanguageResultCodeEnum.REGISTER_FAILED.getMessage(),LanguageResultCodeEnum.REGISTER_FAILED.getValue());
		}
		//创建用户收益统计表
		AccountProfit accountProfit = new AccountProfit();
		accountProfit.setUsername(username);
		boolean insert1 = accountProfit.insert();
		if(!insert1){
			throw new HZException(LanguageResultCodeEnum.REGISTER_FAILED.getMessage(),LanguageResultCodeEnum.REGISTER_FAILED.getValue());
		}
		MiningShop byId = miningShopService.getById(1);
		//赠送1T矿机
		AppGlobalVariable app=appGlobalVariableService.getById(appConfigID);
		MiningMachine miningMachine = MachineTools.getAbstractMachine();
		if(app.getFreeMachine()>0){
			miningMachine.setUserName(username);
			miningMachine.setUserId(accountInfo.getId());
			miningMachine.setCoin(byId.getCoin().getDescp());
			miningMachine.setPower(byId.getTCount());
			miningMachine.setModel(byId.getModel());
			miningMachine.setName(byId.getName());
			miningMachine.setPledgeNum(byId.getPrice());
			miningMachine.setRemainTime(System.currentTimeMillis()+86400000L*byId.getRound());
			miningMachine.setProfitPerday(byId.getProduce()/byId.getRound());
			miningMachine.setStatus(TransactionStatusEnum.SUCCESS.getDescp());
			miningMachine.setOnlineState(true);
			miningMachine.setFree(true);
			boolean saveMachine = miningMachineService.save(miningMachine);
			app.setFreeMachine(app.getFreeMachine()-1);
			app.updateById();
			if (!saveMachine) {
				throw new HZException("赠送矿机失败");
			}
		}

		//绑定上下级
		userLevelService.bindding(accountInfo,byInvitationCode);
		//增加上级注册人数
		List<String> upUsers = userLevelService.findByUserUpString(username);
		accountProfitService.updateMinerNumber(upUsers);
		List<String> upUser9=userLevelService.findByUserUpString9(username);
		accountProfitService.updateLeaderNumber(username);
		accountProfitService.updatPledgeByUserName(username,0.012);
		for(String s:upUser9){
			AccountProfit accountProfit2=accountProfitService.findByUserName(s);
			accountProfit2.setTotalPledgeSize(accountProfit2.getTotalPledgeSize()+0.012);
			boolean f=accountProfit2.updateById();
			if(!f){
				log.info("{}用户添加算力失败",s);
			}
		}
		accountProfitService.updateReferenceNum(byInvitationCode.getUsername());
		//调用feign添加用户
		R r = remoteUserService.saveClientUser(username, password,phone,roleId,deptId, SecurityConstants.FROM_IN);
		if(r.getCode()==1){
			throw new HZException(LanguageResultCodeEnum.REGISTER_FAILED.getMessage(),LanguageResultCodeEnum.REGISTER_FAILED.getValue());
		}
		reComeReward(username,miningMachine);
		return null;
	}

	@Async
	public void reComeReward(String username,MiningMachine miningMachine){
		List<UserLevel> userLevels=userLevelService.findAllUp(username);
		for(UserLevel userLevel:userLevels){
			AccountInfo accountInfo1=accountInfoService.findByUserName(userLevel.getLeftName());
			if(!accountInfo1.isAuth()){
				continue;
			}
			double reward=0.0d;
			CurrencyBalance currencyBalance=currencyBalanceService.findByUserNameAndCoinName(userLevel.getLeftName(),miningMachine.getCoin());
			List<UserLevel> userLevels1=userLevelService.findByUsernameOnDownAndLevel(userLevel.getLeftName(),1);
			int i=0;
			if(CollUtil.isNotEmpty(userLevels1)){
				i=userLevels1.size();
			}
			switch (userLevel.getLevelNum()){
				case  1: if(i>=1) {
					reward=miningMachine.getPledgeNum()*0.07;
				} break;
				case  2: if(i>=2){reward=miningMachine.getPledgeNum()*0.03;} break;
				case  3: if(i>=3){reward=miningMachine.getPledgeNum()*0.05;} break;
				case  4: if(i>=4){reward=miningMachine.getPledgeNum()*0.01;} break;
				case  5: if(i>=5){reward=miningMachine.getPledgeNum()*0.01;} break;
				default:break;
			}
			if(userLevel.getLevelNum()>=6&&userLevel.getLevelNum()<=9&&i>=6){
				reward=miningMachine.getPledgeNum()*0.01;
			}
			if(reward==0){
				continue;
			}
			currencyBalance.setBalance(currencyBalance.getBalance()+reward);
			currencyBalance.updateById();
			AccountDetails accountDetails=new AccountDetails();
			accountDetails.setCurrency(CoinEnum.valueOf(miningMachine.getCoin()));
			accountDetails.setFromuser(FromUseEnum.USER);
			accountDetails.setInOrOut(InOrOutEnum.PUSH);
			accountDetails.setUsername(userLevel.getLeftName());
			accountDetails.setAmount(reward);
			accountDetails.setRemarks(username);
			accountDetails.insert();
		}
	}

	private String createInvitationCode() {
		String code = RandomUtil.randomNumbers(7);
		AccountInfo byInvitationCode = accountInfoService.findByInvitationCode(code);
		if(byInvitationCode!=null){
			return createInvitationCode();
		}
		return code;
	}

	private String createBackups()  {
		String mnemonicWord = MnemonicWordUtil.getMnemonicWord();
		AccountInfo byBackups = accountInfoService.findByBackups(mnemonicWord);
		if(byBackups==null){
			return mnemonicWord;
		}else{
			return createBackups();
		}
	}

	public void sendSMSCode(String phone, String type) throws HZException {
		/**
		 * 类型<br/>忘记密码=FORGETPASSWORD，<br/>注册=REGISTER,转账=SEND<br/>TRANSACTIONPASSWORD=交易密码<br/>MODIFYPHONE=绑定手机号<br/>FORGETPASSWORD=忘记密码<br/>CHENAGEPASSWORD=修改密码
		 */
		boolean b = canSendSMS(phone, type);
		if(!b){
			throw new HZException(LanguageResultCodeEnum.SMSCODE_FOUND.getMessage(),LanguageResultCodeEnum.SMSCODE_FOUND.getValue());
		}
		SMSCode smsCode = new SMSCode();
		smsCode.setPhone(phone);
		String code = RandomUtil.randomNumbers(6);
		if(type.equals("REGISTER")){
			if(StrUtil.isEmpty(phone)){
				throw new HZException(LanguageResultCodeEnum.PHONE_FORMAT_ERROR.getMessage(),LanguageResultCodeEnum.PHONE_FORMAT_ERROR.getValue());
			}
			smsCode.setType("REGISTER");
			ZhuTongSmsTools.getResigerCode(phone,code);
		}else if(type.equals("FORGETPASSWORD")){
			smsCode.setType("FORGETPASSWORD");
			ZhuTongSmsTools.forgetPassowrd(phone,code);
		}else{
			AccountInfo byUserName = accountInfoService.findByUserName(getUserName());
			if(byUserName==null){
				throw new HZException(LanguageResultCodeEnum.ILLEGAL_OPERATION.getMessage(),LanguageResultCodeEnum.ILLEGAL_OPERATION.getValue());
			}
			phone = byUserName.getPhone();
			SMSCode byPhoneOnType = smsCodeService.findByPhoneOnType(phone, type);
			if(byPhoneOnType!=null){
				throw new HZException(LanguageResultCodeEnum.SMSCODE_FOUND.getMessage(),LanguageResultCodeEnum.SMSCODE_FOUND.getValue());
			}
			if(StrUtil.isEmpty(byUserName.getPhone())){
				throw new HZException(LanguageResultCodeEnum.PHONE_NOT_BIND.getMessage(),LanguageResultCodeEnum.PHONE_NOT_BIND.getValue());
			}
			if(type.equals("MODIFYPHONE")){
				smsCode.setType("MODIFYPHONE");
				ZhuTongSmsTools.bindPhone(phone,code);
			}else if(type.equals("SEND")){
				smsCode.setType("SEND");
				ZhuTongSmsTools.getTransferCode(phone,code);
			}else if(type.equals("TRANSACTIONPASSWORD")){
				smsCode.setType("TRANSACTIONPASSWORD");
				ZhuTongSmsTools.setTransferPassword(phone,code);
			}else if(type.equals("CHENAGEPASSWORD")){
				smsCode.setType("CHENAGEPASSWORD");
				ZhuTongSmsTools.changePassword(phone,code);
			}else{
				throw new HZException(LanguageResultCodeEnum.PARAM_ERROR.getMessage(),LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
		}
		smsCode.setPhone(phone);
		smsCode.setCode(code);
		smsCode.insert();
	}

	public void verificationMnemonic(String word) throws HZException {
		AccountInfo byBackups = accountInfoService.findByBackups(word);
		if(byBackups==null){
			throw new HZException(LanguageResultCodeEnum.INSUFFICIENT_SPACE.getMessage(),LanguageResultCodeEnum.MNEMONIC_INCORRECT.getValue());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void forgetPassword(String username, String code, String newpassword) throws HZException {
		String usernamePrefix = environment.getProperty("esc.client.username_prefix");
		username=usernamePrefix+username;
		AccountInfo byUserName = accountInfoService.findByUserName(username);
		if(byUserName==null){
			throw new HZException(LanguageResultCodeEnum.USER_NOT_EXISTS.getMessage(),LanguageResultCodeEnum.USER_NOT_EXISTS.getValue());
		}
		R<UserInfo> info = remoteUserService.info(username, SecurityConstants.FROM_IN);
		boolean forgetpassword = checkSMS(info.getData().getSysUser().getPhone(), "FORGETPASSWORD", code);
		if(!forgetpassword){
			throw new HZException(LanguageResultCodeEnum.VERIFICATION_INCORRECT.getMessage(),LanguageResultCodeEnum.VERIFICATION_INCORRECT.getValue());
		}
		R r = remoteUserService.updatePassword(username, newpassword, SecurityConstants.FROM_IN);
		if(r.getCode()!=0){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
	}
}
