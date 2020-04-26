package com.yhwl.yhwl.esc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.entity.AppGlobalVariable;
import com.yhwl.yhwl.esc.api.entity.CoinList;
import com.yhwl.yhwl.esc.api.inteface.EscBackAppControllerInterface;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "esc", tags = "esc 应用管理接口")
@RequestMapping("/escadmin/app")
@RestController
@Slf4j
public class EscBackAppController extends BaseController implements EscBackAppControllerInterface {
	@Override
	public R coinList(Page page, CoinList coinList) {
		IPage iPage=coinListService.findByPage(page,coinList);
		return R.ok(iPage);
	}

	@Override
	public R saveCoinList(CoinList coinList) {
		if(coinList == null){
			return R.failed("更新失败");
		}
		if(coinList.getId()==null){
			Boolean success = coinList.insert();
			if(success){
				return R.ok(null,"添加成功");
			}else{
				return R.failed("添加失败");
			}
		}else{
			Boolean success = coinList.updateById();
			if(success){
				return R.ok(null,"更新成功");
			}else{
				return R.failed("更新失败");
			}
		}
	}

	@Override
	public R appGlobal(Page page) {
		IPage<AppGlobalVariable> iPage=appGlobalVariableService.page(page);
		return R.ok(iPage);
	}

	@Override
	public R savAppGlobal(AppGlobalVariable appGlobalVariable) {
		AppGlobalVariable app=appGlobalVariableService.getById(appConfigID);
		if(app==null){
			return R.failed("更新失败");
		}else{
			Boolean success = appGlobalVariable.updateById();
			if(success){
				return R.ok(null,"更新成功");
			}else{
				return R.failed("更新失败");
			}
		}
	}
}
