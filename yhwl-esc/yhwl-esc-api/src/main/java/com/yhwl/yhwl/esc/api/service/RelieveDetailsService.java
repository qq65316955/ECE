package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.RelieveDetails;


public interface RelieveDetailsService extends IService<RelieveDetails> {
    RelieveDetails findByPledgeId(String id);

    RelieveDetails findByminerId(String id);

    IPage<RelieveDetails> findByPage(Page page, String username, String status, String minerId, String coinName);
}
