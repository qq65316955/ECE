package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.BallPool;

import java.util.List;

public interface BallPoolService extends IService<BallPool> {
	IPage<BallPool> findByPage(Page page);

	List<BallPool> listByLevelAndStatus(int level,int status);

	IPage<BallPool> findPage(Page page,BallPool ballPool);

	List<BallPool>  findByUsername(String username);
}
