package com.yhwl.yhwl.esc.api.inteface;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.esc.api.entity.BallPool;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface EscAppBallControllerInterface {

	@ApiOperation(value = "抢球", notes = "抢球")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "id", example = "id", paramType = "query", required = true, dataType = "string"),
			}
	)
	@ResponseBody
	@PostMapping("/grabBall")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_grabBall')")
	R grabBall(@RequestParam("id")String id);

	@ApiOperation(value = "球列表", notes = "球列表")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "page", value = "page", example = "page", paramType = "query", required = true, dataType = "int"),
			}
	)
	@ResponseBody
	@PostMapping("/ballData")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_ballData')")
	R<IPage<BallPool>> ballData(@RequestParam(value = "page") int page);

	@ApiOperation(value = "预约球", notes = "预约球")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "timeSlot", value = "file", example = "file", paramType = "query", required = true, dataType = "int"),
					@ApiImplicitParam(name = "timeSlot", value = "file", example = "file", paramType = "query", required = true, dataType = "int"),
			}
	)
	@ResponseBody
	@PostMapping("/orderBall")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_orderBall')")
	R orderBall(@RequestParam("timeSlot")int timeSlot,@RequestParam("ballLevel") int ballLevel);

	@ApiOperation(value = "兑换", notes = "兑换")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "leftCoin", value = "使用币种", example = "leftCoin", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "rightCoin", value = "兑换币种", example = "rightCoin", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "balance", value = "兑换金额", example = "balance", paramType = "query", required = true, dataType = "double"),
			}
	)
	@ResponseBody
	@PostMapping("/exchange")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_exchange')")
	R exchange(@RequestParam("leftCoin")String leftCoin, @RequestParam("rightCoin")String rightCoin,
			   @RequestParam("balance") double balance);


	@ApiOperation(value = "兑换手续费", notes = "兑换手续费")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "balance", value = "balance", example = "balance", paramType = "query", required = true, dataType = "double"),
			}
	)
	@ResponseBody
	@PostMapping("/exchangeCharge")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_exchangeCharge')")
	R exchangeCharge(@RequestParam("balance") double balance);

	@ApiOperation(value = "能量合约状态", notes = "能量合约状态")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "balance", value = "balance", example = "balance", paramType = "query", required = true, dataType = "double"),
			}
	)
	@ResponseBody
	@PostMapping("/ballStatus")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_ballStatus')")
	R ballStatus();

	@ApiOperation(value = "合约订单详情", notes = "合约订单详情")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "订单id", example = "id", paramType = "query", required = true, dataType = "string"),
			}
	)
	@ResponseBody
	@PostMapping("/ballOrderDetail")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_ballOrderDetail')")
	R ballOrderDetail(String id);

	@ApiOperation(value = "支付审核", notes = "支付审核")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "订单id", example = "id", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "url", value = "图片地址", example = "url", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "sellName", value = "卖方姓名", example = "sellName", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "payName", value = "买方姓名", example = "payName", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "sellPhone", value = "卖方手机", example = "sellPhone", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "payPhone", value = "买方手机", example = "payPhone", paramType = "query", required = true, dataType = "string"),
			}
	)
	@ResponseBody
	@PostMapping("/payCheck")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_payCheck')")
	R payCheck(String id,String url,String sellName,String payName,String sellPhone,String payPhone);
}
