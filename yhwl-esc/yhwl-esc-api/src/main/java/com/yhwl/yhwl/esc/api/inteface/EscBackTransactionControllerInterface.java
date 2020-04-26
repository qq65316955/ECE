package com.yhwl.yhwl.esc.api.inteface;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.common.core.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



public interface EscBackTransactionControllerInterface {

    @ApiOperation(value = "交易列表",notes = "交易列表")
    @ResponseBody
    @PostMapping("/transactionRecordData")
	R transactionRecordData(Page page, @RequestParam(value = "sendUser" ,required = false) String sendUser, @RequestParam(value = "getUser" ,required = false) String getUser, @RequestParam(value = "coin",required = false) String coin,
							@RequestParam(value = "status",required = false) String status);

    @ApiOperation(value = "转入手续费",notes = "转入手续费")
    @ResponseBody
    @PostMapping("/transferCharges")
    @PreAuthorize("@pms.hasPermission('sys_yhwl_bmj_back_transferCharges')")
    R transferCharges(@RequestParam("id") String id);

    @ApiOperation(value = "通过转账",notes = "通过转账")
    @ResponseBody
    @PostMapping("/sendTransaction")
    @PreAuthorize("@pms.hasPermission('sys_yhwl_bmj_back_sendTransaction')")
    R sendTransaction(@RequestParam("id") String id) throws InterruptedException;

    @ApiOperation(value = "拒绝转账",notes = "拒绝转账")
    @ResponseBody
    @PostMapping("/refuseTransaction")
    @PreAuthorize("@pms.hasPermission('sys_yhwl_bmj_back_refuseTransaction')")
    R refuseTransaction(@RequestParam("id") String id);



}
