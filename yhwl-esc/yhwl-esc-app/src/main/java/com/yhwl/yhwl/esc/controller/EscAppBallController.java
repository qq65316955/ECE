package com.yhwl.yhwl.esc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.log.annotation.SysLog;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.entity.BallPool;
import com.yhwl.yhwl.esc.api.inteface.EscAppBallControllerInterface;
import com.yhwl.yhwl.esc.controller.impl.EscAppBallControllerImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Api(value = "ese", tags = "ese(无需授权)能量合约")
@RequestMapping("/ese/app/client/ball")
@RestController
@Slf4j
public class EscAppBallController extends BaseController implements EscAppBallControllerInterface {

	@Autowired
	EscAppBallControllerImpl escAppBallController;

	@Override
	@SysLog("抢球")
	public R grabBall(String id) {
		try {
			escAppBallController.grabBall(id);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (Exception e) {
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog("球列表")
	public R ballData(int page) {
		try {
			IPage iPage=escAppBallController.ballData(page);
			return R.ok(iPage).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (Exception e) {
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog("预约球")
	public R orderBall(int timeSlot,int ballLevel) {
		try {
			escAppBallController.orderBall(timeSlot,ballLevel);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (Exception e) {
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog("兑换")
	public R exchange(String leftCoin, String rightCoin, double balance) {
		try {
			escAppBallController.exchange(leftCoin,rightCoin,balance);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (Exception e) {
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog("兑换手续费")
	public R exchangeCharge(double balance) {
		try {
			Map map=escAppBallController.exchangeCharge(balance);
			return R.ok(map).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (Exception e) {
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	@SysLog("抢购状态")
	public R ballStatus() {

		try {
			BallPool ballPool=escAppBallController.ballStatus();
			return R.ok(ballPool).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (Exception e) {
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}

	}

	@Override
	public R ballOrderDetail(String id) {
		try {
			escAppBallController.ballOrderDetail(id);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (Exception e) {
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R payCheck(String id,String url,String sellName,String payName,String sellPhone,String payPhone) {
		try {
			escAppBallController.payCheck(id,url,sellName,payName,sellPhone,payPhone);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (Exception e) {
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}
}
