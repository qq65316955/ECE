package com.yhwl.yhwl.esc.controller;


import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.inteface.EscBackDashBoardControllerInterface;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "esc", tags = "esc 中控板接口")
@RequestMapping("/escadmin/dashBoard")
@RestController
@Slf4j
public class EscBackDashBoardController extends BaseController implements EscBackDashBoardControllerInterface {
    @Override
    public R board() {
    	//新增用户
        int todayUser = accountInfoService.findTodayUser();
        //新增总账户数
        int allUserCount = accountInfoService.findAllUserCount();
        //新增今日矿机
        int todayMachine = miningMachineService.findTodayMachine();
        //矿机总数
        int totalMachine = miningMachineService.findTotalMachine();
        //今日矿机产量
        double todayProduceAll = accountProfitService.sumTodayProduceAll();
        //矿机总产量
        double totalProduceAll = accountProfitService.sumTotalProduceAll();
        //总收入
        Double totalIncom = accountDetailsService.totalIncom();
        //今日收入
        Double todayIncom = accountDetailsService.todayIncom();

		List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map1= new HashMap<>();
        Map<String,Object> map2= new HashMap<>();
        Map<String,Object> map3= new HashMap<>();
        Map<String,Object> map4= new HashMap<>();
        Map<String,Object> map5= new HashMap<>();
        Map<String,Object> map6= new HashMap<>();
        Map<String,Object> map7= new HashMap<>();
        Map<String,Object> map8= new HashMap<>();
        map1.put("name","今日新账户");
        map1.put("value",todayUser);
        map2.put("name","总账户数");
        map2.put("value",allUserCount);
        map3.put("name","今日新增矿机");
        map3.put("value",todayMachine);
        map4.put("name","总矿机数");
        map4.put("value",totalMachine);
        map5.put("name","今日矿机产量");
        map5.put("value",todayProduceAll);
        map6.put("name","矿机总产量");
        map6.put("value",totalProduceAll);
        map7.put("name","今日团队收益");
        map7.put("value",todayIncom);
        map8.put("name","团队总收益");
        map8.put("value",totalIncom);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        list.add(map7);
        list.add(map8);
        return R.ok(list);
    }

    @Override
    public R userIncrease(Integer days) {
        List<Map<String,Object>> list = accountInfoService.increaseData(days);
        return R.ok(list);
    }

    @Override
    public R machineIncrease(Integer days) {
        List<Map<String,Object>> list = miningMachineService.increaseData(days);
        return R.ok(list);
    }

    @Override
    public R incomIncrease(Integer days) {
        List<Map<String,Object>> list = accountDetailsService.increaseData(days);
        return R.ok(list);
    }
}
