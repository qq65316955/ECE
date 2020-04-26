package com.yhwl.yhwl.esc.controller.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.yhwl.yhwl.admin.api.dto.UserInfo;
import com.yhwl.yhwl.admin.api.feign.RemoteUserService;
import com.yhwl.yhwl.common.core.constant.SecurityConstants;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.tools.aliyun.OSSClientUtil;
import com.yhwl.yhwl.common.tools.core.collection.CollUtil;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.common.tools.core.util.IdcardUtil;
import com.yhwl.yhwl.common.tools.core.util.StrUtil;
import com.yhwl.yhwl.common.tools.qrcode.QrCodeUtils;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.InOrOutEnum;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.base.TransactionStatusEnum;
import com.yhwl.yhwl.esc.api.entity.*;
import com.yhwl.yhwl.esc.api.vo.AppIndexVo;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import com.yhwl.yhwl.wallet.api.feign.RemoteWalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class EscAppAuthControllerImpl extends BaseController {

	protected final RemoteUserService remoteUserService;
	protected final RemoteWalletService remoteWalletService;

	@Transactional(rollbackFor = Exception.class)
	public void setTransactionPassword(String password) throws HZException {
		String userName = getUserName();
		AccountInfo accountInfo = accountInfoService.findByUserName(userName);
		if(StrUtil.isNotBlank(accountInfo.getTransactionPassword())){
			throw new HZException(LanguageResultCodeEnum.ILLEGAL_OPERATION.getMessage(),LanguageResultCodeEnum.ILLEGAL_OPERATION.getValue());
		}
		accountInfo.setTransactionPassword(password);
		boolean update = accountInfo.updateById();
		if(!update){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void changeTransactionPassword(String password, String code) throws HZException {
		AccountInfo byUserName = accountInfoService.findByUserName(getUserName());
		if(byUserName==null){
			throw new HZException(LanguageResultCodeEnum.USER_NOT_EXISTS.getMessage(),LanguageResultCodeEnum.USER_NOT_EXISTS.getValue());
		}
		R<UserInfo> info = remoteUserService.info(getUserName(), SecurityConstants.FROM_IN);
		boolean chenagepassword = checkSMS(info.getData().getSysUser().getPhone(), "TRANSACTIONPASSWORD", code);
		if(!chenagepassword){
			throw new HZException(LanguageResultCodeEnum.VERIFICATION_INCORRECT.getMessage(),LanguageResultCodeEnum.VERIFICATION_INCORRECT.getValue());
		}
		AccountInfo accountInfo = getAppAccount();
		accountInfo.setTransactionPassword(password);
		boolean update = accountInfo.updateById();
		if(!update){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
	}

	public AppIndexVo appIndex() throws HZException {
		AccountProfit accountProfit = accountProfitService.findByUserName(getUserName());
		if(null == accountProfit){
			throw new HZException(LanguageResultCodeEnum.SERVER_ERROR.getMessage(),LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
		AccountInfo accountInfo=accountInfoService.findByUserName(getUserName());

		AppIndexVo appIndexVo = new AppIndexVo();

		return appIndexVo;
	}

	public List<CurrencyBalance> getCoinList() throws HZException {
		List<CurrencyBalance> currencyBalances = new ArrayList<>();
		if(null == currencyBalances||currencyBalances.size()==0){
			List<CoinList> list = coinListService.list(null);
			File file = null;
			try {
				if(CollUtil.isNotEmpty(list)){
					list.forEach(e->{
						CurrencyBalance currencyBalance = currencyBalanceService.findByUserNameAndCoinName(getUserName(),e.getName());
						if(currencyBalance == null || StrUtil.isEmpty(currencyBalance.getAddress())){
							if(currencyBalance == null){
								currencyBalance = new CurrencyBalance();
							}
							R<LinkedHashMap> wallet = walletService.createAddress(remoteWalletService,getUserName(),e.getLine().getValue());
							currencyBalance.setAddress(String.valueOf(wallet.getData().get("address")));
							currencyBalance.setIcoUrl(e.getIcoUrl());
							currencyBalance.setLine(e.line);
							currencyBalance.setUserName(getUserName());
							currencyBalance.setName(e.getName());

						}
						if(StrUtil.isEmpty(currencyBalance.getQrCode())){
							String  fileSavePath = "/Users/alang/eth/";
							try{
								String encode = QrCodeUtils.encode(currencyBalance.getAddress(), null,fileSavePath,getUserName()+"_"+e.getName(),true);
								OSSClientUtil.uploadObject2OSS(OSSClientUtil.getOSSClient(), new File(fileSavePath + encode), OSSClientUtil.bucketName, OSSClientUtil.folder+"/"+getUserName()+"/");
								currencyBalance.setQrCode(OSSClientUtil.endpoint_get+"/"+OSSClientUtil.folder+"/"+getUserName()+"/"+encode);
							}catch (Exception ex){

							}
						}
						currencyBalance.setPrice(e.getPrice());
						currencyBalance.insertOrUpdate();
						currencyBalances.add(currencyBalance);
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return currencyBalances;
	}

	public Map<String,Object> recharge(String coin) throws HZException {
		CurrencyBalance byUsernameAndCoinname = currencyBalanceService.findByUserNameAndCoinName(getUserName(), coin);
		if(byUsernameAndCoinname==null){
			throw new HZException(LanguageResultCodeEnum.SERVER_ERROR.getMessage(),LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
		Map<String,Object> map = Maps.newHashMap();
		map.put("address",byUsernameAndCoinname.getAddress());
		map.put("qcCode",byUsernameAndCoinname.getQrCode());
		return map;
	}

	public void send(String coin, String address, int amount, String password, String smscode, String sourceTag) throws HZException {
		AccountInfo accountInfo = getAppAccount();
		if(accountInfo.isFreeze()){
			throw new HZException(LanguageResultCodeEnum.ACCOUNT_FREEZE.getMessage(),LanguageResultCodeEnum.ACCOUNT_FREEZE.getValue());
		}
		if(amount <= 0){
			throw new HZException(LanguageResultCodeEnum.PARAM_ERROR.getMessage(),LanguageResultCodeEnum.PARAM_ERROR.getValue());
		}
		if(StrUtil.isEmpty(coin) || StrUtil.isEmpty(address) || StrUtil.isEmpty(password) || StrUtil.isEmpty(smscode)){
			throw new HZException(LanguageResultCodeEnum.PARAM_ERROR.getMessage(),LanguageResultCodeEnum.PARAM_ERROR.getValue());
		}
		if(amount<10||amount%10!=0){
			throw new HZException(LanguageResultCodeEnum.MONEY_NUM_ERROR.getMessage(),LanguageResultCodeEnum.MONEY_NUM_ERROR.getValue());
		}

		if(!accountInfo.isIscash()){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FORBIDDEN.getMessage(),LanguageResultCodeEnum.OPERATION_FORBIDDEN.getValue());
		}
		if(!accountInfo.getTransactionPassword().equals(password)){
			throw new HZException(LanguageResultCodeEnum.TRAN_PASSWORD_INCORRECT.getMessage(),LanguageResultCodeEnum.TRAN_PASSWORD_INCORRECT.getValue());
		}
		boolean send = checkSMS(accountInfo.getPhone(), "SEND", smscode);
		if(!send){
			throw new HZException(LanguageResultCodeEnum.VERIFICATION_INCORRECT.getMessage(),LanguageResultCodeEnum.VERIFICATION_INCORRECT.getValue());
		}
		CurrencyBalance getCurrency = currencyBalanceService.findByAddressAndCoinName(address, coin);
		if(getCurrency==null){
			throw new HZException(LanguageResultCodeEnum.SEND_ADDRESS_ERROR.getMessage(),LanguageResultCodeEnum.SEND_ADDRESS_ERROR.getValue());
		}
		TransactionRecord transactionRecord = new TransactionRecord();
		CurrencyBalance currencyBalance = currencyBalanceService.findByUserNameAndCoinName(accountInfo.getUsername(), coin);
		if(currencyBalance.getBalance()<amount){
			throw new HZException(LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getMessage(),LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getValue());
		}
		if(currencyBalance.getAddress().equals(address)){
			throw new HZException(LanguageResultCodeEnum.SEND_ADDRESS_ERROR.getMessage(),LanguageResultCodeEnum.SEND_ADDRESS_ERROR.getValue());
		}
		// 最低转账额度
		if(amount < 5){
			throw new HZException(LanguageResultCodeEnum.BALANCE_ERROR.getMessage(),LanguageResultCodeEnum.BALANCE_ERROR.getValue());
		}
		currencyBalance.setBalance(currencyBalance.getBalance()-amount);
		boolean b = currencyBalance.updateById();
		if(!b){
			throw new HZException(LanguageResultCodeEnum.SEND_ERROR.getMessage(),LanguageResultCodeEnum.SEND_ERROR.getValue());
		}
		double fee = 2D;
		AppGlobalVariable appGlobalVariable=appGlobalVariableService.getById(appConfigID);
		transactionRecord.setSendAddress(currencyBalance.getAddress());
		transactionRecord.setSendUser(getUserName());
		transactionRecord.setSamount(amount);
		transactionRecord.setCoin(CoinEnum.getByDescp(coin));
		transactionRecord.setStatus(TransactionStatusEnum.WAIT);
		transactionRecord.setHash("");
		transactionRecord.setGetAddress(address);
		transactionRecord.setGetUser(getCurrency==null?"第三方":getCurrency.getUserName());
		transactionRecord.setGamount(getCurrency==null?amount-amount*appGlobalVariable.getFreeAmount():amount-fee);
		transactionRecord.setSourceTag("");
		boolean insert = transactionRecord.insert();
		if(!insert){
			throw new HZException(LanguageResultCodeEnum.SEND_ERROR.getMessage(),LanguageResultCodeEnum.SEND_ERROR.getValue());
		}
	}

	public void sendOut(String coin, String address, int amount, String password, String smscode, String sourceTag) throws HZException {
		AccountInfo accountInfo = getAppAccount();
		if(accountInfo.isFreeze()){
			throw new HZException(LanguageResultCodeEnum.ACCOUNT_FREEZE.getMessage(),LanguageResultCodeEnum.ACCOUNT_FREEZE.getValue());
		}
		if(amount <= 0){
			throw new HZException(LanguageResultCodeEnum.PARAM_ERROR.getMessage(),LanguageResultCodeEnum.PARAM_ERROR.getValue());
		}
		if(StrUtil.isEmpty(coin) || StrUtil.isEmpty(address) || StrUtil.isEmpty(password) || StrUtil.isEmpty(smscode)){
			throw new HZException(LanguageResultCodeEnum.PARAM_ERROR.getMessage(),LanguageResultCodeEnum.PARAM_ERROR.getValue());
		}
		if(amount<10||amount%10!=0){
			throw new HZException(LanguageResultCodeEnum.MONEY_NUM_ERROR.getMessage(),LanguageResultCodeEnum.MONEY_NUM_ERROR.getValue());
		}

		if(!accountInfo.isIscash()){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FORBIDDEN.getMessage(),LanguageResultCodeEnum.OPERATION_FORBIDDEN.getValue());
		}
		if(!accountInfo.getTransactionPassword().equals(password)){
			throw new HZException(LanguageResultCodeEnum.TRAN_PASSWORD_INCORRECT.getMessage(),LanguageResultCodeEnum.TRAN_PASSWORD_INCORRECT.getValue());
		}
		boolean send = checkSMS(accountInfo.getPhone(), "SEND", smscode);
		if(!send){
			throw new HZException(LanguageResultCodeEnum.VERIFICATION_INCORRECT.getMessage(),LanguageResultCodeEnum.VERIFICATION_INCORRECT.getValue());
		}
		CurrencyBalance getCurrency = currencyBalanceService.findByAddressAndCoinName(address, coin);
		if(getCurrency!=null){
			throw new HZException(LanguageResultCodeEnum.SEND_ADDRESS_ERROR.getMessage(),LanguageResultCodeEnum.SEND_ADDRESS_ERROR.getValue());
		}
		TransactionRecord transactionRecord = new TransactionRecord();
		CurrencyBalance currencyBalance = currencyBalanceService.findByUserNameAndCoinName(accountInfo.getUsername(), coin);
		if(currencyBalance.getBalance()<amount){
			throw new HZException(LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getMessage(),LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getValue());
		}
		if(currencyBalance.getAddress().equals(address)){
			throw new HZException(LanguageResultCodeEnum.SEND_ADDRESS_ERROR.getMessage(),LanguageResultCodeEnum.SEND_ADDRESS_ERROR.getValue());
		}
		// 最低转账额度
		if(amount < 5){
			throw new HZException(LanguageResultCodeEnum.BALANCE_ERROR.getMessage(),LanguageResultCodeEnum.BALANCE_ERROR.getValue());
		}
		currencyBalance.setBalance(currencyBalance.getBalance()-amount);
		boolean b = currencyBalance.updateById();
		if(!b){
			throw new HZException(LanguageResultCodeEnum.SEND_ERROR.getMessage(),LanguageResultCodeEnum.SEND_ERROR.getValue());
		}
		double fee = 2D;
		AppGlobalVariable appGlobalVariable=appGlobalVariableService.getById(appConfigID);
		transactionRecord.setSendAddress(currencyBalance.getAddress());
		transactionRecord.setSendUser(getUserName());
		transactionRecord.setSamount(amount);
		transactionRecord.setCoin(CoinEnum.getByDescp(coin));
		transactionRecord.setStatus(TransactionStatusEnum.WAIT);
		transactionRecord.setHash("");
		transactionRecord.setGetAddress(address);
		transactionRecord.setGetUser(getCurrency==null?"第三方":getCurrency.getUserName());
		transactionRecord.setGamount(getCurrency==null?amount-amount*appGlobalVariable.getFreeAmount():amount-fee);
		transactionRecord.setSourceTag("");
		boolean insert = transactionRecord.insert();
		if(!insert){
			throw new HZException(LanguageResultCodeEnum.SEND_ERROR.getMessage(),LanguageResultCodeEnum.SEND_ERROR.getValue());
		}
	}

	public IPage<TransactionRecord> sendHistory(String coin, int page, String type) throws HZException {
		CurrencyBalance currencyBalance = getAppWallet(coin);
		if(currencyBalance==null){
			throw new HZException(LanguageResultCodeEnum.SERVER_ERROR.getMessage(),LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
		IPage<TransactionRecord> transactionRecordIPage = transactionRecordService.pageByAddressAndCoin(new Page<TransactionRecord>(page, 10), currencyBalance.getAddress(),getUserName(), coin,type);
		return transactionRecordIPage;
	}


	public void updateLoginPassword(String newpassword, String code) throws HZException {
		AccountInfo byUserName = accountInfoService.findByUserName(getUserName());
		if(byUserName==null){
			throw new HZException(LanguageResultCodeEnum.USER_NOT_EXISTS.getMessage(),LanguageResultCodeEnum.USER_NOT_EXISTS.getValue());
		}
		R<UserInfo> info = remoteUserService.info(getUserName(), SecurityConstants.FROM_IN);
		boolean forgetpassword = checkSMS(info.getData().getSysUser().getPhone(), "CHENAGEPASSWORD", code);
		if(!forgetpassword){
			throw new HZException(LanguageResultCodeEnum.VERIFICATION_INCORRECT.getMessage(),LanguageResultCodeEnum.VERIFICATION_INCORRECT.getValue());
		}
		R r = remoteUserService.updatePassword(getUserName(), newpassword, SecurityConstants.FROM_IN);
		if(r.getCode()!=0){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
	}

	public IPage<AccountDetails> recomeReward(int page,String type) throws HZException{
		String userName =getUserName();
		IPage<AccountDetails> accountDetailsIPage=null;
		if("LEADER".equals(type)){
			accountDetailsIPage=accountDetailsService.pageByUsernameOnType(new Page<AccountDetails>(page,10),userName,InOrOutEnum.LEADER.getDescp());
		}else if("PUSH".equals(type)) {
			accountDetailsIPage = accountDetailsService.pageByUsernameOnType(new Page<AccountDetails>(page, 10), userName, InOrOutEnum.PUSH.getDescp());
		}
		return accountDetailsIPage;
	}

	public List<MiningShop> getMinerShopList() {
		return miningShopService.list();
	}

	@Transactional(rollbackFor = Exception.class)
	public void buyMiner(String shopId, int num, String chargePassword) throws HZException {
		MiningShop byId = miningShopService.getById(shopId);
		if(byId==null){
			throw new HZException(LanguageResultCodeEnum.PARAM_ERROR.getMessage(),LanguageResultCodeEnum.PARAM_ERROR.getValue());
		}
		if(byId.getCanSell()==0){
			throw new HZException(LanguageResultCodeEnum.INVALID_COMMODITY.getMessage(),LanguageResultCodeEnum.INVALID_COMMODITY.getValue());
		}
		if(num < byId.getBuyCount()){
			throw new HZException(LanguageResultCodeEnum.BUY_FAIL_SIZE.getMessage(),LanguageResultCodeEnum.BUY_FAIL_SIZE.getValue());
		}
		if(StrUtil.isEmpty(getAppAccount().getTransactionPassword())){
			throw new HZException(LanguageResultCodeEnum.TRAN_PASSWORD_NOTSET.getMessage(),LanguageResultCodeEnum.TRAN_PASSWORD_NOTSET.getValue());
		}
		if(!getAppAccount().getTransactionPassword().equals(chargePassword)){
			throw new HZException(LanguageResultCodeEnum.TRAN_PASSWORD_INCORRECT.getMessage(),LanguageResultCodeEnum.TRAN_PASSWORD_INCORRECT.getValue());
		}
		double totalPrice = byId.getPrice()*num;
		CurrencyBalance byAddressAndCoinName = currencyBalanceService.findByUserNameAndCoinName(getUserName(), CoinEnum.USDTETH.getValue());
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
		boolean insert = miningMachine.insert();
		if(!insert){
			throw new HZException(LanguageResultCodeEnum.BUY_FAIL.getMessage(),LanguageResultCodeEnum.BUY_FAIL.getValue());
		}
	}
	public Map<String,Object>  personalCenter() throws HZException{
		AccountInfo byUserName = getAppAccount();
		AccountProfit accountProfit = accountProfitService.findByUserName(byUserName.getUsername());
		if(null == accountProfit){
			throw new HZException(LanguageResultCodeEnum.SERVER_ERROR.getMessage(),LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
		List<UserLevel> levels=userLevelService.findByUsernameOnDownAndLevel(getUserName(),1);
		Double sumreference=accountDetailsService.sumByUserReference(getUserName());
		IdCard idCard=idCardService.findByUsername(getUserName());
		Map<String,Object> map = Maps.newHashMap();
		map.put("invitationCode",byUserName.getInvitationCode());
		map.put("invitationNum",CollUtil.isEmpty(levels)?0:levels.size());
		map.put("power",accountProfit.getTotalPledgeSize());
		map.put("team",accountProfit.getMinerNumber());
		if(accountProfit.getLeaderLevel()>=2){
			map.put("level",accountProfit.getLeaderLevel());
		}else {
			map.put("level",accountProfit.isLevel1()?1:0);
		}
		map.put("miner",accountProfit.getTotalProfit());
		map.put("profit",accountProfit.getTotalProfit()+accountProfit.getTotalLeader()+sumreference);
		map.put("renfence",sumreference);
		map.put("isAuth",idCard==null?null:idCard.getStatus());
		return map;
	}
	public IPage<Information> information(int page) {
		return informationService.findByPage(new Page(page, 10));
	}

	public MiningShop shopInfo(String shopId) {
		return miningShopService.getById(shopId);
	}

	public String upLoadImage( MultipartFile file,String type) throws HZException {

		String imageurl = null;
		try {
			//上传到服务器
			if("ID".equals(type)) {
				String Fronturl = OSSClientUtil.uploadObject2OSS(OSSClientUtil.getOSSClient(), file, "esc/idcard/");
				//拼接图片地址
				imageurl = "http://yanghaowangluo.oss-cn-beijing.aliyuncs.com/esc/idcard/" + Fronturl;
			}
			else if("ALI".equals(type)){
				String Fronturl = OSSClientUtil.uploadObject2OSS(OSSClientUtil.getOSSClient(), file, "esc/ali/");
				//拼接图片地址
				imageurl = "http://yanghaowangluo.oss-cn-beijing.aliyuncs.com/esc/ali/" + Fronturl;
			}
			else if("WE".equals(type)){
				String Fronturl = OSSClientUtil.uploadObject2OSS(OSSClientUtil.getOSSClient(), file, "esc/wechat/");
				//拼接图片地址
				imageurl = "http://yanghaowangluo.oss-cn-beijing.aliyuncs.com/esc/wechat/" + Fronturl;
			}
			else if("CHECK".equals(type)){
				String Fronturl = OSSClientUtil.uploadObject2OSS(OSSClientUtil.getOSSClient(), file, "esc/payCheck/");
				//拼接图片地址
				imageurl = "http://yanghaowangluo.oss-cn-beijing.aliyuncs.com/esc/payCheck/" + Fronturl;
			}
			return imageurl;

		} catch (Exception e) {
			throw new HZException("上传图片失败",206);
		}
	}

	public void uploadId(String idNumber,String idCardFrontImg,String realName, String idCardBackImg, String accountInformationImg) throws HZException{
		IdCard idCard=idCardService.findByUsername(getUserName());
		if(idCard!=null){
			throw new HZException(LanguageResultCodeEnum.IDCARD_UPLOAD.getMessage(),LanguageResultCodeEnum.IDCARD_UPLOAD.getValue());
		}
		if(!IdcardUtil.isValidCard(idNumber)){
			throw new HZException(LanguageResultCodeEnum.IDCARD_ERROR.getMessage(),LanguageResultCodeEnum.IDCARD_ERROR.getValue());
		}
		IdCard idCard1=idCardService.findByNumber(idNumber);
		if(idCard1!=null){
			throw new HZException(LanguageResultCodeEnum.ID_NUMBER_EXSIT.getMessage(),LanguageResultCodeEnum.ID_NUMBER_EXSIT.getValue());
		}
		IdCard idCard2=new IdCard();
		idCard2.setBackUrl(idCardBackImg);
		idCard2.setFrontUrl(idCardFrontImg);
		idCard2.setPeopleUrl(accountInformationImg);
		idCard2.setIdNumber(idNumber);
		idCard2.setRealName(realName);
		idCard2.setUsername(getUserName());
		boolean f=idCard2.insert();
		if(!f){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
	}

	public void uploadAli(String url) throws HZException {
		AccountInfo accountInfo=accountInfoService.findByUserName(getUserName());
		accountInfo.setAliUrl(url);
		boolean f=accountInfo.updateById();
		if(!f){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
	}
	public void uploadWechat(String url) throws HZException {
		AccountInfo accountInfo=accountInfoService.findByUserName(getUserName());
		accountInfo.setWechatUrl(url);
		boolean f=accountInfo.updateById();
		if(!f){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
	}

	public IPage<AccountProfit> myTeam(int page) throws HZException{
		List<String> users=userLevelService.findByUserDown9(getUserName());
		IPage<AccountProfit> accountProfitIPage=accountProfitService.PageByUsername(new Page<AccountProfit>(page,10),users);
		if(accountProfitIPage==null||CollUtil.isEmpty(accountProfitIPage.getRecords())){
			return null;
		}
		for(AccountProfit accountProfit:accountProfitIPage.getRecords()){
			Double referenceprofit=accountDetailsService.sumByUserReferenceAndRemarks(getUserName(),accountProfit.getUsername());
			accountProfit.setSprofit(referenceprofit);
		}
		return accountProfitIPage;
	}

	public Map levelExplain() throws HZException{
		Map map=Maps.newHashMap();
		List<UserLevel> userLevels=userLevelService.findByUsernameOnDownAndLevel(getUserName(),1);
		int lv1=0;
		int lv2=0;
		int lv3=0;
		int lv4=0;
		int f1=0;
		int f2=0;
		int f3=0;
		int d2=0;
		int d1=0;
		int d3=0;
		int g1=0;
		int g2=0;
		int g3=0;
		int h1=0;
		int h2=0;
		int h3=0;
		if(CollUtil.isNotEmpty(userLevels)) {
			for (UserLevel userLevel : userLevels) {
				int countlv1 = 0;
				int countlv2 = 0;
				int countlv3 = 0;
				int countlv4 = 0;
				List<UserLevel> levels = userLevelService.findByUsernameOnDown(userLevel.getRightName());
				for (UserLevel userLevel1 : levels) {
					AccountProfit accountProfit = accountProfitService.findByUserName(userLevel1.getRightName());
					if (accountProfit.isLevel1()) {
						countlv1++;
					}
					if (accountProfit.getLeaderLevel() >= 2) {
						countlv2++;
					}
					if (accountProfit.getLeaderLevel() >= 3) {
						countlv3++;
					}
					if (accountProfit.getLeaderLevel() >= 4) {
						countlv4++;
					}
				}
				//一条线达到2个以上v1
				if (countlv1 >= 2) {
					f2++;
					//两条线达到2个以上V1
					if (f2 <= 2) {
						f3 += countlv1;
					}
				}
				//一条线达到1个以上v1;
				if (countlv1 == 1) {
					f1++;
				}
				//一条线达到2个以上v2
				if (countlv2 >= 2) {
					d2++;
					//两条线达到2个以上V2
					if (d2 <= 2) {
						d3 += countlv2;
					}
				}
				//一条线达到1个以上v2;
				if (countlv3 == 1) {
					d1++;
				}
				//一条线达到1个以上v3
				if (countlv3 >= 1) {
					g1++;
					g3 += countlv3;

				}
				//一条线达到1个以上v4;
				if (countlv4 >= 1) {
					h1++;
					h3 += countlv4;
				}
				//一条线达到1个以上v4;
			}
		}
		if(f2==0&&f1>=2){
			lv1=2;
		}else if(f2==1&&f1==0){
			lv1=2;
		}else if(f2==0&&f1<2){
			lv1=f1;
		}else if(f2>=2){
			lv1=f3;
		}
		if(d2==0&&d1>=2){
			lv2=2;
		}else if(d2==1&&d1==0){
			lv2=2;
		}else if(d2==0&&d1<2){
			lv2=d1;
		}else if(d2>=2){
			lv2=d3;
		}

		if(g1>=3){
			lv3=g3;
		}else if(g1==2) {
			lv3 = 2;
		}else if(g1<2){
			lv3=g3;
		}

		if(h1>=3){
			lv4=h3;
		}else if(h1==2) {
			lv4 = 2;
		}else if(h1<2){
			lv4=h3;
		}

		AccountProfit accountProfit=accountProfitService.findByUserName(getUserName());
		map.put("lv2",lv1);
		map.put("lv3",lv2);
		map.put("lv4",lv3);
		map.put("lv5",lv4);
		map.put("recome",userLevels.size());
		map.put("leaderNum",accountProfit.getLeaderNumber());
		map.put("power",accountProfit.getTotalPledgeSize());
		return map;
	}

	public Map teamTotal() throws HZException{
		int lv1=0;
		int lv2=0;
		int lv3=0;
		int lv4=0;
		int lv5=0;
		List<UserLevel> levels=userLevelService.findByUsernameOnDown(getUserName());
		if(CollUtil.isNotEmpty(levels)){
			for(UserLevel userLevel:levels){
				AccountProfit accountProfit=accountProfitService.findByUserName(userLevel.getRightName());
				if(accountProfit.isLevel1()&&accountProfit.getLeaderLevel()==0){
					lv1++;
				}
				switch (accountProfit.getLeaderLevel()){
					case 2:lv2++;break;
					case 3:lv3++;break;
					case 4:lv4++;break;
					case 5:lv5++;break;
					default:break;
				}
			}
		}
		Map map=Maps.newHashMap();
		map.put("lv1",lv1);
		map.put("lv2",lv2);
		map.put("lv3",lv3);
		map.put("lv4",lv4);
		map.put("lv5",lv5);
		return map;
	}

	public Map freeCharge(){
		AppGlobalVariable appGlobalVariable=appGlobalVariableService.getById(appConfigID);
		Map map=Maps.newHashMap();
		map.put("charge",appGlobalVariable.getFreeAmount());
		return map;
	}

}
