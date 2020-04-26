package com.yhwl.yhwl.esc.api.inteface;

import com.yhwl.yhwl.common.core.util.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * esc miner接口
 */
public interface EscAppMinerControllerInterface {


	@ApiOperation(value = "矿机详情", notes = "矿机详情")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "id", example = "id", paramType = "query", required = true, dataType = "string"),
			}
	)
	@ResponseBody
	@PostMapping("/minerInfo")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_minerInfo')")
	R minerInfo(String id);

	@ApiOperation(value = "矿机列表", notes = "矿机列表")
	@ResponseBody
	@PostMapping("/minerList")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_minerList')")
	R minerList();

	@ApiOperation(value = "领取明细", notes = "领取明细")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "page", value = "page", example = "id", paramType = "query", required = true, dataType = "string"),
			}
	)
	@ResponseBody
	@PostMapping("/minerDetails")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_minerDetails')")
	R minerDetails(@RequestParam(value = "page") int page);

	@ApiOperation(value = "领取收益(对矿机操作)", notes = "领取收益(对矿机操作)")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "矿机ID", example = "id", paramType = "query", required = true, dataType = "string"),
			}
	)

	@PostMapping("/extract")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_extract')")
	R extract(@RequestParam(value = "id") String id);

	@ApiOperation(value = "购买矿机", notes = "购买矿机")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "矿机ID", example = "id", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "transactionPassword", value = "交易密码", example = "id", paramType = "query", required = true, dataType = "string"),
			}
	)

	@PostMapping("/buyMiner")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_buyMiner')")
	R buyMiner(@RequestParam(value = "id") String id, @RequestParam("transactionPassword") String transactionPassword);

	@ApiOperation(value = "质押明细", notes = "质押明细")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "page", value = "页数", example = "id", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "type", value = "类型", example = "id", paramType = "query", required = true, dataType = "string"),
			}
	)

	@PostMapping("/pledgeDetails")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_pledgeDetails')")
	R pledgeDetails(int page,String type);

}
