package com.yhwl.yhwl.esc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.entity.MiningMachine;
import com.yhwl.yhwl.esc.api.entity.MiningShop;
import com.yhwl.yhwl.esc.api.inteface.EscBackMinerControllerInterface;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "esc", tags = "esc 矿机接口")
@RequestMapping("/escadmin/miner")
@RestController
@AllArgsConstructor
@Slf4j
public class EscBackMinerController extends BaseController implements EscBackMinerControllerInterface {

	@Override
	public R minerShopData(Page page, MiningShop miningShop) {
		try {
			if (miningShop == null) {
				return R.failed(LanguageResultCodeEnum.PARAM_ERROR.getMessage()).setCode(LanguageResultCodeEnum.PARAM_ERROR.getValue());
			}
			IPage iPage = miningShopService.findByPage(page, miningShop);
			return R.ok(iPage).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		} catch (Exception e) {
			return R.failed(LanguageResultCodeEnum.SERVER_ERROR.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R saveMinerShop(MiningShop miningShop) {
		if (miningShop == null) {
			return R.failed("添加失败");
		}
		return miningShop.insert() ? R.ok("添加成功") : R.failed("添加失败");
	}

	@Override
	public R deleteMinerShop(String id) {
		MiningShop miningShop = miningShopService.getById(id);
		if (miningShop == null) {
			return R.failed("删除失败");
		}
		return miningShop.deleteById() ? R.ok("删除成功") : R.failed("删除失败");
	}

	@Override
	public R editMinerShop(MiningShop miningShop) {
		if (miningShop == null) {
			return R.failed("更新失败");
		}
		return miningShop.updateById() ? R.ok("更新成功") : R.failed("更新失败");
	}

	//矿机列表
	@Override
	public R miningMachineData(Page page, MiningMachine miningMachine) {
		IPage ipage = miningMachineService.findMachineListByPage(page, miningMachine);
		return R.ok(ipage).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
	}

	@Override
	public R deleteMiningMachine(String id) {
		MiningMachine miningMachine = miningMachineService.getById(id);
		if (miningMachine == null) {
			return R.failed("删除失败");
		}
		return miningMachine.deleteById() ? R.ok("删除成功") : R.failed("删除失败");
	}
}