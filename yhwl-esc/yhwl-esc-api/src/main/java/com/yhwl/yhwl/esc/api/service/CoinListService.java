package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.CoinList;


public interface CoinListService extends IService<CoinList> {

    IPage<CoinList> findByPage(Page page, CoinList coinList);

	int  findTodayCoinCount();
}
