package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.MinerRental;

import java.util.Date;
import java.util.List;

public interface MinerRentalService extends IService<MinerRental> {

    List<MinerRental> findAll();

    List<MinerRental> findByUsername(String username);

    List<MinerRental> findByDate(Date date);

    IPage<MinerRental> findByPage(Page page, MinerRental minerRental);

    MinerRental findByMac(String mac);
}
