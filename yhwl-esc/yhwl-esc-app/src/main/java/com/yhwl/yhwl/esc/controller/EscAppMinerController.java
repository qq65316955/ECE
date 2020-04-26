package com.yhwl.yhwl.esc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.entity.AccountDetails;
import com.yhwl.yhwl.esc.api.entity.MiningMachine;
import com.yhwl.yhwl.esc.api.inteface.EscAppMinerControllerInterface;
import com.yhwl.yhwl.esc.controller.impl.EscAppMinerControllerImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "ese", tags = "ese(无需授权)云矿机")
@RequestMapping("/ese/app/client/miner")
@RestController
@Slf4j
public class EscAppMinerController extends BaseController implements EscAppMinerControllerInterface {
	@Autowired
	EscAppMinerControllerImpl escAppMinerController;

	@Override
	public R minerInfo(String id) {
		try{
			MiningMachine miningMachine = escAppMinerController.minerInfo(id);
			return R.ok(miningMachine).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R minerList() {
		try{
			List<MiningMachine> list = escAppMinerController.minerList();
			return R.ok(list).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R minerDetails(int page) {
		try{
			IPage<AccountDetails> page1 = escAppMinerController.minerDetails(page);
			return R.ok(page1).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R extract(String id) {
		try{
			escAppMinerController.extract(id);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R buyMiner(String id,  String transactionPassword) {
		try{
			escAppMinerController.buyMiner(id,transactionPassword);
			return R.ok().setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}

	@Override
	public R pledgeDetails(int page, String type) {
		try{
			IPage<MiningMachine> page1= escAppMinerController.pledgeDetails(page,type);
			return R.ok(page1).setCode(LanguageResultCodeEnum.SUCCESSFUL.getValue());
		}catch (HZException e){
			return R.failed(e.getMessage()).setCode(e.getCode());
		}catch (Exception e){
			return R.failed(e.getMessage()).setCode(LanguageResultCodeEnum.SERVER_ERROR.getValue());
		}
	}
}
