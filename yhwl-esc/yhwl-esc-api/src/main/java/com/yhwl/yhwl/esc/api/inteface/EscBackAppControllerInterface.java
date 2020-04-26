package com.yhwl.yhwl.esc.api.inteface;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.esc.api.entity.AppGlobalVariable;
import com.yhwl.yhwl.esc.api.entity.CoinList;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public interface EscBackAppControllerInterface {

	@ApiOperation(value = "币种列表",notes = "币种列表")
	@ResponseBody
	@PostMapping("/coinList")
	R coinList(Page page, CoinList coinList);

	@ApiOperation(value = "编辑币种",notes = "编辑币种")
	@ResponseBody
	@PostMapping("/saveCoinList")
	R saveCoinList(CoinList coinList);

	@ApiOperation(value = "全局变量",notes = "全局变量")
	@ResponseBody
	@PostMapping("/appGlobal")
	R appGlobal(Page page);

	@ApiOperation(value = "编辑变量",notes = "编辑变量")
	@ResponseBody
	@PostMapping("/savAppGlobal")
	R savAppGlobal(AppGlobalVariable appGlobalVariable);
}
