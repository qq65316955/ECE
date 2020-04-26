package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.LockProfit;

import java.util.List;

public interface LockProfitService extends IService<LockProfit> {

    List<LockProfit> listByUsername(String username, int type);

    double sumProfitByUserName(String userName);

    double yesterdayProfit(String userName);

    double totalMoney(String userName);

    IPage findByPage(Page page, LockProfit lockProfit);

}
