package com.yhwl.yhwl.esc.api.inteface;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.esc.api.entity.AccountDetails;
import com.yhwl.yhwl.esc.api.entity.AccountInfo;
import com.yhwl.yhwl.esc.api.entity.CurrencyBalance;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface EscBackAccountControllerInterface {

    @ApiOperation(value = "钱包列表",notes = "钱包列表")
    @ResponseBody
    @PostMapping("/currencyData")
	R currencyData(Page page, CurrencyBalance currencyBalance);


    @ApiOperation(value = "账户明细",notes = "账户明细")
    @ResponseBody
    @PostMapping("/detailData")
    R detailData(Page page, AccountDetails accountDetails);


    @ApiOperation(value = "充值",notes = "充值")
    @ResponseBody
    @PostMapping("/charge")
    R charge(@RequestParam("id") String id, @RequestParam("money") String money);


    @ApiOperation(value = "账户列表",notes = "账户列表")
    @ResponseBody
    @PostMapping("/accounts")
    R accounts(Page page,@RequestParam(value = "username" ,required = false) String username,@RequestParam(value = "nickname" ,required = false)String nickname,@RequestParam(value = "phone" ,required = false)String phone);


    @ApiOperation(value = "修改密码",notes = "修改密码")
    @ResponseBody
    @PostMapping("/changePwd")
    R changePwd(@RequestParam("id") String id, @RequestParam("password") String password);


    @ApiOperation(value = "编辑账户",notes = "编辑账户")
    @ResponseBody
    @PostMapping("/saveAccount")
    R saveAccount(AccountInfo account);

    @ApiOperation(value = "禁止提现",notes = "禁止提现")
    @ResponseBody
    @PostMapping("/isCash")
    R isCash(@RequestParam("id") String id);

    //身份证审核
	@ApiOperation(value = "身份证列表",notes = "身份证列表")
	@ResponseBody
	@PostMapping("/idCardData")
	R idCardData(Page page, @RequestParam(value = "realName" ,required = false)String realName,@RequestParam(value = "idNumber" ,required = false)String idNumber,@RequestParam(value = "status" ,required = false)Integer status);

	@ApiOperation(value = "身份证审核",notes = "身份证审核")
	@ResponseBody
	@PostMapping("/idCardPass")
	R idCardPass(@RequestParam("id") String id,@RequestParam("status") int status);

	@ApiOperation(value = "冻结账号",notes = "冻结账号")
	@ResponseBody
	@PostMapping("/freezeAccount")
	R freezeAccount(@RequestParam("id") String id);


	@ApiOperation(value = "用户关系",notes = "用户关系")
	@ResponseBody
	@PostMapping("/relation")
	R relation(@RequestParam("username") String username,@RequestParam(required = false) String backUrl);



//	@ApiOperation(value = "解冻账号",notes = "解冻账号")
//	@ResponseBody
//	@PostMapping("/thawAccount")
//	R thawAccount(@RequestParam("id") String id);

//	@ApiOperation(value = "每天注册人数",notes = "每天注册人数")
//	@ResponseBody
//	@PostMapping("/numberOfRegitorEveryday")
//	R numberOfRegitorEveryday();

}
