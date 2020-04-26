package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.PledgeLog;


public interface PledgeLogService extends IService<PledgeLog> {

    IPage<PledgeLog> findByPage(Page page, PledgeLog pledgeLog);

}
