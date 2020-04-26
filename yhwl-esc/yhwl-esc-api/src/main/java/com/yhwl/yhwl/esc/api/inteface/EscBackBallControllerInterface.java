package com.yhwl.yhwl.esc.api.inteface;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.esc.api.entity.BallOrder;
import com.yhwl.yhwl.esc.api.entity.BallPool;
import com.yhwl.yhwl.esc.api.entity.EnergyBall;
import com.yhwl.yhwl.esc.api.entity.PayCheck;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public interface EscBackBallControllerInterface {
	@ApiOperation(value = "能量合约", notes = "能量合约")
	@ResponseBody
	@PostMapping("/energyBallData")
	R energyBallData(Page page, EnergyBall energyBall);

	@ApiOperation(value = "编辑能量合约", notes = "编辑能量合约")
	@ResponseBody
	@PostMapping("/editEnergyBall")
	R editEnergyBall(EnergyBall energyBall);

	@ApiOperation(value = "新增能量合约", notes = "新增能量合约")
	@ResponseBody
	@PostMapping("/addEnergyBall")
	R addEnergyBall(EnergyBall energyBall);

	@ApiOperation(value = "删除能量合约", notes = "删除能量合约")
	@ResponseBody
	@PostMapping("/deleteEnergyBall")
	R deleteEnergyBall(String id);


	@ApiOperation(value = "球预约列表", notes = "球预约列表")
	@ResponseBody
	@PostMapping("/ballOrderData")
	R ballOrderData(Page page, BallOrder ballOrder);

	@ApiOperation(value = "指定用户抢到球", notes = "指定用户抢到球")
	@ResponseBody
	@PostMapping("/appointBallOrder")
	R appointBallOrder(int level,int timeSlot, String username) throws HZException;

	@ApiOperation(value = "随机分球", notes = "随机分球")
	@ResponseBody
	@PostMapping("/randomBallOrder")
	R randomBallOrder(String id, int level, int timeSlot);

	@ApiOperation(value = "球列表", notes = "球列表")
	@ResponseBody
	@PostMapping("/ballPoolData")
	R ballPoolData(Page page, BallPool ballPool);

	@ApiOperation(value = "删除球", notes = "删除球")
	@ResponseBody
	@PostMapping("/deleteBallPool")
	R deleteBallPool(String id);

	@ApiOperation(value = "新增球", notes = "新增球")
	@ResponseBody
	@PostMapping("/addBallPool")
	R addBallPool(BallPool ballPool);


//	@ApiOperation(value = "预约球列表",notes = "预约球列表")
//	@ResponseBody
//	@PostMapping("/ballOrderPage")
//	R ballOrderPage(Page page, BallOrder ballOrder);


	@ApiOperation(value = "能量合约支付凭证", notes = "能量合约支付凭证")
	@ResponseBody
	@PostMapping("/payCheckData")
	R payCheckData(Page page, PayCheck payCheck);


}
