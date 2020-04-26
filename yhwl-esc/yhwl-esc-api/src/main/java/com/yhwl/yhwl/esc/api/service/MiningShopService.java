package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.MiningMachine;
import com.yhwl.yhwl.esc.api.entity.MiningShop;

import java.util.List;

public interface MiningShopService extends IService<MiningShop> {
    List<MiningShop> getByUserName(String userName);

    IPage findByPage(Page page,MiningShop miningShop);

    MiningShop findByName(String name);

}
