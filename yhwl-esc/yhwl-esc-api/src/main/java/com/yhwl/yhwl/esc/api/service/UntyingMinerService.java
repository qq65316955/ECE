package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.UntyingMiner;

import java.sql.Timestamp;


public interface UntyingMinerService extends IService<UntyingMiner> {
    IPage<UntyingMiner> findByPage(Page page, UntyingMiner untyingMiner, Timestamp starttime, Timestamp endtime);
}
