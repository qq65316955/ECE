package com.yhwl.yhwl.esc.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.log.annotation.SysLog;
import com.yhwl.yhwl.common.security.annotation.Inner;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.base.TransactionStatusEnum;
import com.yhwl.yhwl.esc.api.entity.CurrencyBalance;
import com.yhwl.yhwl.esc.api.entity.TransactionRecord;
import com.yhwl.yhwl.esc.api.inteface.EscAppUnauthControllerInterface;
import com.yhwl.yhwl.esc.controller.impl.EscAppUnauthControllerImpl;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Api(value = "ese", tags = "ese(无需授权)")
@RequestMapping("/ese/app/client/unauth")
@RestController
@Slf4j
public class EscAppUnauthController extends BaseController implements EscAppUnauthControllerInterface {

	@Autowired
	public EscAppUnauthControllerImpl escAppUnauthController;
	@Override
	@Inner(false)
	@SysLog(value = "esc_注册")
	public R register(String username,  String password,String transactionPassword, String fromuser, String phone, String code,String country, @RequestParam(required = false)String from) throws HZException {
		try{
			Map<String, Object> register = escAppUnauthController.register(username, password,transactionPassword, fromuser, phone, code,country);
			return R.ok(register).setCode(LanguageResultCodeEnum.REGISTER_SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}
	}

	@Override
	@Inner(false)
	@SysLog(value = "esc_发送验证码")
	public R sendSMSCode(String phone, String type,@RequestParam(required = false) String from) {
		try{
			escAppUnauthController.sendSMSCode(phone,type);
			return R.ok("发送成功").setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e) {
			return R.failed(e.getMessage()).setCode(e.getCode());
		}
	}
	@Inner(false)
	@Override
	@SysLog(value = "esc_验证助记词")
	public R verificationMnemonic(String word, @RequestParam(required = false)String from) {
		try{
			escAppUnauthController.verificationMnemonic(word);
			return R.ok("验证成功").setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}
	}

	@Inner(false)
	@Override
	@SysLog(value = "esc_忘记密码")
	public R forgetPassword(String username, String code, String newpassword,@RequestParam(required = false) String from) {
		try{
			escAppUnauthController.forgetPassword(username,code,newpassword);
			return R.ok(LanguageResultCodeEnum.SUCCESSFUL.getMessage()).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}
	}

	private final String TOKEN = "eceaf0f9af443e54372b95c01248674a009";
	@Override
	@Inner(false)
	@Transactional(rollbackFor = Exception.class)
	@SysLog(value = "esc_入金通知")
	public R getTran(byte[] data) {
		Map<String, Object> result = Maps.newHashMap();
		try{
			String info = new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
			if(info != null){
				JSONObject jsonObject = JSONObject.parseObject(info).getJSONObject("data");
				String token = jsonObject.getString("token");
				if(!TOKEN.equals(token)){
					result.put("code",204);
					result.put("message","this token is error!");
				}else{
					String getAddress =jsonObject.getString("getAddress");
					String sendAddress = jsonObject.getString("sendAddress");
					String coin = jsonObject.getString("coin");
					String hash = jsonObject.getString("hash");
					double amount = jsonObject.getDouble("amount");
					TransactionRecord transactionRecord = transactionRecordService.findByHash(hash);
					if(transactionRecord != null){
						result.put("code",203);
						result.put("message","this transaction already exists database!");
					}else{
						CurrencyBalance currencyBalance = currencyBalanceService.findByAddressAndCoinName(getAddress, coin);
						if(currencyBalance== null){
							//log.error("接收交易失败：{}",jsonObject.toJSONString());
							result.put("code",207);
							result.put("message","outer transaction!");
						}else{
							currencyBalance.setBalance(currencyBalance.getBalance() + amount);
							currencyBalance.updateById();
							transactionRecord = new TransactionRecord();
							transactionRecord.setStatus(TransactionStatusEnum.SUCCESS);
							transactionRecord.setCoin(CoinEnum.valueOf("ECE"));
							transactionRecord.setSamount(amount);
							transactionRecord.setGamount(amount);
							transactionRecord.setSendAddress(sendAddress);
							transactionRecord.setGetAddress(currencyBalance.getAddress());
							transactionRecord.setGetUser(currencyBalance.getUserName());
							transactionRecord.setHash(hash);
							transactionRecord.insert();
							result.put("code",200);
							result.put("message","this transaction has add in database!");
						}
					}
				}
			}else{
				result.put("code",205);
				result.put("message","data is null!");
			}
		}catch (Exception e){
			result.put("code",206);
			result.put("message","server error!");
		}
		return R.ok(result);
	}
}
