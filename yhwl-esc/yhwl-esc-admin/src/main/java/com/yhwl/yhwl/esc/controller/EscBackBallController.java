package com.yhwl.yhwl.esc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.tools.core.collection.CollUtil;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.entity.*;
import com.yhwl.yhwl.esc.api.inteface.EscBackBallControllerInterface;
import com.yhwl.yhwl.esc.tools.ZhuTongSmsTools;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Api(value = "esc", tags = "esc 能量合约接口")
@RequestMapping("/escadmin/ball")
@RestController
@Slf4j
public class EscBackBallController extends BaseController implements EscBackBallControllerInterface {

	@Override
	public R energyBallData(Page page, EnergyBall energyBall) {
		IPage iPage = energyBallService.findByPage(page, energyBall);
		return R.ok(iPage);
	}

	//修改
	@Override
	public R editEnergyBall(EnergyBall energyBall) {
		boolean f = energyBall.updateById();
		if (!f) {
			return R.failed(null, "编辑失败");
		}
		return R.ok("修改成功");
//		if (energyBall == null){
//			return R.failed(null, "编辑失败");
//		}
//		int result = energyBallService.updateEnergyBall(energyBall);
//		if (result == 0){
//			return R.failed(null, "编辑失败");
//		}
//		return R.ok("修改成功");
	}

	//添加
	@Override
	public R addEnergyBall(EnergyBall energyBall) {
		if (energyBall == null) {
			return R.failed("更新失败");
		}
		if (energyBall.getId() == null) {
			Boolean success = energyBall.insert();
			if (success) {
				return R.ok(null, "添加成功");
			} else {
				return R.failed("添加失败");
			}
		}
		return R.failed(null, "添加失败");
	}

	//删除
	@Override
	public R deleteEnergyBall(String id) {
		EnergyBall energyBall = energyBallService.getById(id);
		if (energyBall == null) {
			return R.failed(null, "没有该能量合约");
		}
		boolean f = energyBall.deleteById();
		if (!f) {
			return R.failed(null, "删除失败");
		}
		return R.ok("删除成功");
	}

	//抢购球列表
	@Override
	public R ballOrderData(Page page, BallOrder ballOrder) {
		try {
			IPage<BallOrder> iPage = ballOrderService.findByPage(page, ballOrder);
			for (BallOrder ball : iPage.getRecords()) {
				Integer timeSlot = ball.getTimeSlot();
				switch (timeSlot){
					case 1 : ball.setTime("11:00-11:25");
					break;
					case 2 : ball.setTime("13:00-13:25");
					break;
					case 3 : ball.setTime("15:00-15:25");
					break;
					case 4 : ball.setTime("17:00-17:25");
					break;
					case 5 : ball.setTime("18:00-18:25");
					break;
					case 6 : ball.setTime("20:00-20:25");
					break;
					default: R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
				}
			}
			return R.ok(iPage);
		}catch (Exception e){
			return R.failed(LanguageResultCodeEnum.SERVER_ERROR.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R appointBallOrder(int level, int timeSlot, String username) {
		//找出未预约未抢购的球
		BallOrder ballOrder = ballOrderService.findByUsernameAndLevelAndStatus(username, level, 0,0);
		if (ballOrder == null) {
			return R.failed(null, "该用户未预约或抢购");
		}
		//查找球池中未激活的球
		List<BallPool> ballPools = ballPoolService.listByLevelAndStatus(level, 0);
		if (CollUtil.isEmpty(ballPools)) {
			return R.failed(null, "没有该等级能量合约或已分配完或已预约");
		}
		BallPool ballPool = ballPools.get(0);
		ballPool.setUsername(username);
		ballPool.setPayTime(System.currentTimeMillis() + 14400000);
		ballPool.setStatus(1);
		boolean f = ballPool.updateById();
		if (!f) {
			return R.failed(null, "操作失败");
		}
		AccountInfo accountInfo = accountInfoService.findByUserName(username);
		try {
			ZhuTongSmsTools.sendBall(accountInfo.getPhone());
		} catch (HZException e) {

		}
		ballOrder.setStatus(1);
		ballOrder.updateById();
		return R.ok("指定分配成功");
	}

	@Override
	public R randomBallOrder(String id, int level, int timeSlot) {
		List<BallOrder> ballOrders = ballOrderService.ListByLevelAndTime(level, timeSlot, 0);
		if (CollUtil.isEmpty(ballOrders)) {
			return R.failed(null, "该等级球没有预约或抢购");
		}
		Collections.shuffle(ballOrders);
		BallOrder ballOrder = ballOrders.get(0);
		BallPool ballPool = ballPoolService.getById(id);
		if (ballPool == null) {
			return R.failed(null, "没有该能量合约");
		}
		ballPool.setUsername(ballOrder.getUsername());
		boolean f = ballPool.updateById();
		if (!f) {
			return R.failed(null, "操作失败");
		}
		ballOrder.setStatus(1);
		ballOrder.updateById();
		return R.ok("随机分配成功");
	}

	@Override
	public R ballPoolData(Page page, BallPool ballPool) {
		IPage iPage = ballPoolService.findPage(page, ballPool);
		return R.ok(iPage);
	}

	@Override
	public R deleteBallPool(String id) {
		BallPool ballPool = ballPoolService.getById(id);
		if (ballPool == null) {
			return R.failed(null, "没有找到该能量合约");
		}
		boolean f = ballPool.deleteById();
		if (!f) {
			return R.failed(null, "删除失败");
		}
		return R.ok("删除成功");
	}

	@Override
	public R addBallPool(BallPool ballPool) {
		if (ballPool == null) {
			return R.failed(null, "更新失败");
		}
		if (ballPool.getId() == null) {
			Boolean success = ballPool.insert();
			if (success) {
				return R.ok("添加成功");
			} else {
				return R.failed(null, "添加失败");
			}
		}
		return R.failed(null, "不能添加已有的球");
	}


//	//预约球列表
//	@Override
//	public R ballOrderPage(Page page, BallOrder ballOrder) {
//		IPage orderByPage = ballOrderService.findOrderByPage(page, ballOrder);
//		return R.ok(orderByPage).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
//
//	}

	//能量合约支付凭证
	@Override
	public R payCheckData(Page page, PayCheck payCheck) {
		IPage iPage = payCheckService.PaymentVoucher(page, payCheck);
		return R.ok(iPage).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
	}
}
