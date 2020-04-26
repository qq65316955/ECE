package com.yhwl.yhwl.esc.controller.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.entity.*;
import com.yhwl.yhwl.esc.tools.ZhuTongSmsTools;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class EscAppBallControllerImpl extends BaseController {


	public void grabBall(String id) throws HZException {
		AccountInfo accountInfo=accountInfoService.findByUserName(getUserName());
		if(accountInfo.isFreeze()){
			throw new HZException(LanguageResultCodeEnum.ACCOUNT_FREEZE.getMessage(),LanguageResultCodeEnum.ACCOUNT_FREEZE.getValue());
		}
		EnergyBall ball =energyBallService.getById(id);
		Calendar cal = Calendar.getInstance();   		 // 当前日期
		int hour = cal.get(Calendar.HOUR_OF_DAY);  		 // 获取小时
		int minute = cal.get(Calendar.MINUTE);   		 // 获取分钟
		int minuteOfDay = hour * 60 + minute;			// 从0:00分开是到目前为止的分钟数
		//11:00 - 11:25
		CurrencyBalance currencyBalance=currencyBalanceService.findByUserNameAndCoinName(getUserName(),"FIRE");
		double cost=0d;

		BallOrder ballOrder=new BallOrder();
		int timeSlot=0;
		if(minuteOfDay>660&&minuteOfDay<685){
			if(ball.getLevel()!=1){
				throw new HZException(LanguageResultCodeEnum.GRAP_TIME_OUT.getMessage(),LanguageResultCodeEnum.GRAP_TIME_OUT.getValue());
			}
		}
		else if(minuteOfDay>780&&minuteOfDay<805){
			if(ball.getLevel()!=2){
				throw new HZException(LanguageResultCodeEnum.GRAP_TIME_OUT.getMessage(),LanguageResultCodeEnum.GRAP_TIME_OUT.getValue());
			}
			cost=4;
			timeSlot=2;
		}
		else if(minuteOfDay>900&&minuteOfDay<925){
			if(ball.getLevel()!=3){
				throw new HZException(LanguageResultCodeEnum.GRAP_TIME_OUT.getMessage(),LanguageResultCodeEnum.GRAP_TIME_OUT.getValue());
			}
			cost=8;
			timeSlot=3;

		}
		else if(minuteOfDay>1020&&minuteOfDay<1045){
			if(ball.getLevel()!=4){
				throw new HZException(LanguageResultCodeEnum.GRAP_TIME_OUT.getMessage(),LanguageResultCodeEnum.GRAP_TIME_OUT.getValue());
			}
			cost=16;
			timeSlot=4;
		}
		else if(minuteOfDay>1080&&minuteOfDay<1105){
			if(ball.getLevel()!=5){
				throw new HZException(LanguageResultCodeEnum.GRAP_TIME_OUT.getMessage(),LanguageResultCodeEnum.GRAP_TIME_OUT.getValue());
			}
			cost=32;
			timeSlot=5;
		}
		else if(minuteOfDay>1200&&minuteOfDay<1225){
			if(ball.getLevel()!=6){
				throw new HZException(LanguageResultCodeEnum.GRAP_TIME_OUT.getMessage(),LanguageResultCodeEnum.GRAP_TIME_OUT.getValue());
			}
			cost=60;
			timeSlot=6;
		}else {
			throw new HZException(LanguageResultCodeEnum.GRAP_TIME_OUT.getMessage(),LanguageResultCodeEnum.GRAP_TIME_OUT.getValue());
		}
		List<BallOrder> ballOrders=ballOrderService.findByUsernameAndTime(getUserName(),timeSlot);
		if(ballOrders!=null&&ballOrders.size()==3){
			throw new HZException(LanguageResultCodeEnum.BALL_FULL.getMessage(),LanguageResultCodeEnum.BALL_FULL.getValue());
		}
		ballOrder.setCost(cost);
		ballOrder.setBallId(id);
		ballOrder.setUsername(getUserName());
		ballOrder.setTimeSlot(timeSlot);
		ballOrder.setStatus(0);
		boolean f=ballOrder.insert();
		if(!f){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}

	}

	public IPage ballData(int page){
		IPage iPage=ballPoolService.findByPage(new Page(page,10));
		return iPage;
	}
	public void orderBall(int timeSlot,int ballLevel) throws HZException {
		AccountInfo accountInfo=accountInfoService.findByUserName(getUserName());
		if(accountInfo.isFreeze()){
			throw new HZException(LanguageResultCodeEnum.ACCOUNT_FREEZE.getMessage(),LanguageResultCodeEnum.ACCOUNT_FREEZE.getValue());
		}
		BallOrder ballOrder=new BallOrder();
		int cost=0;
		switch (timeSlot){
			case 1:cost=1; break;
			case 2:cost=2; break;
			case 3:cost=4; break;
			case 4:cost=8; break;
			case 5:cost=16; break;
			case 6:cost=30; break;
			default:throw new HZException(LanguageResultCodeEnum.PARAM_ERROR.getMessage(),LanguageResultCodeEnum.PARAM_ERROR.getValue());
		}
		CurrencyBalance currencyBalance=currencyBalanceService.findByUserNameAndCoinName(getUserName(),"FIRE");
		if(currencyBalance==null){
			throw new HZException(LanguageResultCodeEnum.WALLET_NOT_FOUND.getMessage(),LanguageResultCodeEnum.WALLET_NOT_FOUND.getValue());
		}
		if(currencyBalance.getBalance()<=cost){
			throw  new HZException(LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getMessage(),LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getValue());
		}
		ballOrder.setCost(cost);
		ballOrder.setTimeSlot(timeSlot);
		ballOrder.setUsername(getUserName());
		ballOrder.setBallLevel(ballLevel);
		ballOrder.setStatus(0);
		boolean f=ballOrder.insert();
		if(!f){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
	}

	public void exchange(String leftCoin,String rightCoin,double balance) throws HZException {
		AccountInfo accountInfo=accountInfoService.findByUserName(getUserName());
		if(accountInfo.isFreeze()){
			throw new HZException(LanguageResultCodeEnum.ACCOUNT_FREEZE.getMessage(),LanguageResultCodeEnum.ACCOUNT_FREEZE.getValue());
		}
		CurrencyBalance leftcurrencyBalance=currencyBalanceService.findByUserNameAndCoinName(getUserName(),leftCoin);
		CurrencyBalance rightcurrencyBalance=currencyBalanceService.findByAddressAndCoinName(getUserName(),rightCoin);
		if(leftcurrencyBalance==null){
			throw new HZException(LanguageResultCodeEnum.WALLET_NOT_FOUND.getMessage(),LanguageResultCodeEnum.WALLET_NOT_FOUND.getValue());
		}
		AppGlobalVariable app=appGlobalVariableService.getById(appConfigID);
		//消耗ESC币
		double escCost=balance/app.getEscToFire();
		if(escCost>leftcurrencyBalance.getBalance()+leftcurrencyBalance.getMinerBalance()){
			throw new HZException(LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getMessage(),LanguageResultCodeEnum.BALANCE_NOT_ENOUGH.getValue());
		}
		if(escCost>leftcurrencyBalance.getBalance()){
			leftcurrencyBalance.setBalance(0d);
			leftcurrencyBalance.setMinerBalance(leftcurrencyBalance.getMinerBalance()-(escCost-leftcurrencyBalance.getBalance()));
		}else {
			leftcurrencyBalance.setBalance(leftcurrencyBalance.getBalance()-escCost);
		}
		rightcurrencyBalance.setBalance(rightcurrencyBalance.getBalance()+balance);
		ExchangeDetails exchangeDetails=new ExchangeDetails();
		exchangeDetails.setAmount(balance);
		exchangeDetails.setCoin(rightCoin);
		exchangeDetails.setUsername(getUserName());
		exchangeDetails.setCostEsc(escCost);
		boolean f=exchangeDetails.insert();
		if(!f){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}


	}

	public Map exchangeCharge(double balance){
		Map map= Maps.newHashMap();
		AppGlobalVariable app=appGlobalVariableService.getById(appConfigID);
		map.put("price",app.getEscToFire());
		//消耗ESC币
		double escCost=balance/app.getEscToFire();
		map.put("cost",escCost);
		return map;
	}

	public BallPool ballStatus(){
		List<BallPool> ballPools=ballPoolService.findByUsername(getUserName());
		boolean f=false;
		for(BallPool ballPool:ballPools){
			int status=ballPool.getStatus();
			Long time=ballPool.getPayTime();
			if(status==1&&(System.currentTimeMillis()-time)>0){
				return ballPool;
			}
		}
		return null;
	}

	public Map ballOrderDetail(String id) throws HZException {
		BallPool ballPool=ballPoolService.getById(id);
		AccountInfo sellAccount=accountInfoService.findByUserName(ballPool.getSellUser());
		AccountInfo payAccount=accountInfoService.findByUserName(getUserName());
		IdCard sellid=idCardService.findByUsername(ballPool.getSellUser());
		IdCard payid=idCardService.findByUsername(getUserName());
		if(payid==null){
			throw new HZException(LanguageResultCodeEnum.ID_NOT_AUTH.getMessage(),LanguageResultCodeEnum.ID_NOT_AUTH.getValue());
		}
		if(sellid==null){
			throw new HZException(LanguageResultCodeEnum.SELL_NOT_AUTH.getMessage(),LanguageResultCodeEnum.SELL_NOT_AUTH.getValue());
		}
		Map map = Maps.newHashMap();
		map.put("sellUser",sellid.getRealName());
		map.put("sellPhone",sellAccount.getPhone());
		map.put("alipay",sellAccount.getAliUrl());
		map.put("wechat",sellAccount.getWechatUrl());
		map.put("payUser",payid.getRealName());
		map.put("payPhone",payAccount.getPhone());

		return map;
	}

	public void payCheck(String id,String url,String sellName,String payName,String sellPhone,String payPhone) throws HZException {
		BallPool ballPool=ballPoolService.getById(id);
		ballPool.setStatus(2);
		ballPool.setConfirmTime(System.currentTimeMillis()+18000000);
		boolean b=ballPool.updateById();
		if(!b){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
		PayCheck payCheck=new PayCheck();
		payCheck.setCheckUrl(url);
		payCheck.setPayPhone(payPhone);
		payCheck.setPayUsername(payName);
		payCheck.setSellPhone(sellPhone);
		payCheck.setSellUsername(sellName);
		try {
			ZhuTongSmsTools.sellBall(sellPhone,payName);
		} catch (HZException e) {
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}
		boolean f=payCheck.insert();
		if(!f){
			throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
		}

	}
}
