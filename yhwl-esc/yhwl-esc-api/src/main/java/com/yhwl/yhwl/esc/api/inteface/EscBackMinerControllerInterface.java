package com.yhwl.yhwl.esc.api.inteface;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.esc.api.entity.MiningMachine;
import com.yhwl.yhwl.esc.api.entity.MiningShop;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public interface EscBackMinerControllerInterface {

	@ApiOperation(value = "矿机商城列表", notes = "矿机商城列表")
	@ResponseBody
	@PostMapping("/minerShopData")
	R minerShopData(Page page,MiningShop miningShop);


	@ApiOperation(value = "添加矿机商城", notes = "添加矿机商城")

	@ResponseBody
	@PostMapping("/saveMinerShop")
	R saveMinerShop(MiningShop miningShop);

	@ApiOperation(value = "删除矿机商城", notes = "删除矿机商城")
	@ResponseBody
	@PostMapping("/deleteMinerShop")
	R deleteMinerShop(String id);


	@ApiOperation(value = "修改矿机商城", notes = "修改矿机商城")
	@ResponseBody
	@PostMapping("/editMinerShop")
	R editMinerShop(MiningShop miningShop);

	@ApiOperation(value = "矿机列表", notes = "矿机列表")
	@ResponseBody
	@PostMapping("/miningMachineData")
	R miningMachineData(Page page,MiningMachine miningMachine);



	@ApiOperation(value = "删除矿机", notes = "删除矿机")
	@ResponseBody
	@PostMapping("/deleteMiningMachine")
	R deleteMiningMachine(String id);

}
