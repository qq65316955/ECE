package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.CurrencyBalance;

import java.util.List;

public interface CurrencyBalanceService extends IService<CurrencyBalance> {

    CurrencyBalance findByUserNameAndCoinName(String username, String name);

    CurrencyBalance findByAddressAndCoinName(String username, String name);

    List<CurrencyBalance> findByUserName(String username);

    IPage<CurrencyBalance> findByPage(Page<CurrencyBalance> objectPage, CurrencyBalance currencyBalance);

    boolean updateSettle(double amount, String id);

}
