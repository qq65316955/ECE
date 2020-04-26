package com.yhwl.yhwl.esc.api.inteface;

import com.yhwl.yhwl.common.core.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface EscBackDashBoardControllerInterface {

    @ApiOperation(value = "今日新账户",notes = "今日新账户")
    @ResponseBody
    @GetMapping("/board")
	R board();

    @ApiOperation(value = "近7天新注册用户",notes = "近7天新注册用户")
    @ResponseBody
    @GetMapping("/userIncrease")
    R userIncrease(@RequestParam("days") Integer days);

    @ApiOperation(value = "近7天新激活矿机",notes = "近7天新激活矿机")
    @ResponseBody
    @GetMapping("/machineIncrease")
    R machineIncrease(@RequestParam("days") Integer days);

    @ApiOperation(value = "近7天团队收益",notes = "近7天团队收益")
    @ResponseBody
    @GetMapping("/incomIncrease")
    R incomIncrease(@RequestParam("days") Integer days);



}
