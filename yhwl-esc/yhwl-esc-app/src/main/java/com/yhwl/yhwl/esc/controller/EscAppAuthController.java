package com.yhwl.yhwl.esc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.log.annotation.SysLog;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.common.tools.core.util.StrUtil;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.entity.*;
import com.yhwl.yhwl.esc.api.inteface.EscAppAuthControllerInterface;
import com.yhwl.yhwl.esc.api.vo.AppIndexVo;
import com.yhwl.yhwl.esc.controller.impl.EscAppAuthControllerImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@Api(value = "ese", tags = "esc(无需授权)")
@RequestMapping("/ese/app/client/auth")
@RestController
@Slf4j

public class EscAppAuthController extends BaseController implements EscAppAuthControllerInterface {
	@Autowired
	private EscAppAuthControllerImpl escAppAuthController;


	@Override
	@SysLog(value = "esc_client_设置交易密码")
	public R setTransactionPassword(String password) {
		try{
			escAppAuthController.setTransactionPassword(password);
			return R.ok(LanguageResultCodeEnum.SUCCESSFUL.getMessage()).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
}


	@Override
	@SysLog(value = "esc_client_修改交易密码")
	public R changeTransactionPassword(String password, String code) {
		try{
			escAppAuthController.changeTransactionPassword(password,code);
			return R.ok(LanguageResultCodeEnum.SUCCESSFUL.getMessage()).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog(value = "esc_client_主页")
	public R appIndex() {
		try{
			AppIndexVo appIndexVo = escAppAuthController.appIndex();
			return R.ok(appIndexVo).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog(value = "esc_client_获取币种列表")
	public R getCoinList() {
		try{
			List<CurrencyBalance> currencyBalances = escAppAuthController.getCoinList();
			return R.ok(currencyBalances).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog(value = "esc_client_充值")
	public R recharge(String coin) {
		try{
			Map<String, Object> recharge = escAppAuthController.recharge(coin);
			return R.ok(recharge).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog(value = "esc_转账")
	public R send(String coin, String address, int amount, String password, String smscode, String sourceTag) {
		try{
			if(StrUtil.isEmpty(coin)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			if(StrUtil.isEmpty(address)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			if(amount <= 0){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			if(StrUtil.isEmpty(password)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			if(StrUtil.isEmpty(smscode)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			escAppAuthController.send(coin,address,amount,password,smscode,sourceTag);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog(value = "esc_提现")
	public R sendOut(String coin, String address, int amount, String password, String smscode, String sourceTag) {
		try{
			if(StrUtil.isEmpty(coin)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			if(StrUtil.isEmpty(address)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			if(amount <= 0){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			if(StrUtil.isEmpty(password)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			if(StrUtil.isEmpty(smscode)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			escAppAuthController.sendOut(coin,address,amount,password,smscode,sourceTag);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog(value = "esc_client_转账记录")
	public R sendHistory(String coin, int page, String type) {
		try{
			if(StrUtil.isEmpty(coin)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			if(StrUtil.isEmpty(type)){
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			IPage<TransactionRecord> transactionRecordIPage = escAppAuthController.sendHistory(coin, page, type);
			return R.ok(transactionRecordIPage).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog(value = "esc_client_修改登录密码")
	public R changeLoginPasswd(String password, String code) {
		try{
			escAppAuthController.updateLoginPassword(password, code);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog(value = "esc_client_获取矿机列表")
	public R getMinerShopList() throws HZException {
		try{
			List<MiningShop> list = escAppAuthController.getMinerShopList();
			return R.ok(list).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R information(int page) {
		try{
			IPage<Information> informationIPage = escAppAuthController.information(page);
			return R.ok(informationIPage).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}


	@Override
	@SysLog(value = "esc_client_矿机商品详情")
	public R shopInfo(String shopId) {
		try{
			MiningShop shop = escAppAuthController.shopInfo(shopId);
			return R.ok(shop).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}
	@Override
	@SysLog(value = "bmj_client_个人中心")
	public R personalCenter() {
		try{
			Map<String,Object> personalCenter=escAppAuthController.personalCenter();
			return R.ok(personalCenter).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}

	}

	@Override
	public R recomeReward(int page,String type) {
		try{
			IPage<AccountDetails> recomeReward=escAppAuthController.recomeReward(page,type);
			return R.ok(recomeReward).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R upLoadImage(MultipartFile file,String type) {
		try {
			String url = escAppAuthController.upLoadImage(file,type);
			return R.ok(url).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R uploadIDCARD(String idNumber,String idCardFrontImg,String realName, String idCardBackImg, String accountInformationImg) {
		try {
			escAppAuthController.uploadId(idNumber,idCardFrontImg,realName,idCardBackImg,accountInformationImg);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R wechatUpload(String url) {
		try {
			escAppAuthController.uploadWechat(url);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R aliPayUpload(String url) {
		try {
			escAppAuthController.uploadAli(url);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R myTeam(int page){
		try {
			IPage<AccountProfit> iPage=escAppAuthController.myTeam(page);
			return R.ok(iPage).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R levelExplain() {
		try {
			Map map=escAppAuthController.levelExplain();
			return R.ok(map).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R teamTotal() {
		try {
			Map map=escAppAuthController.teamTotal();
			return R.ok(map).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R freeCharge() {
		Map map=escAppAuthController.freeCharge();
		return R.ok(map).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
	}
}
