package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.BallPool;
import com.yhwl.yhwl.esc.api.mapper.BallPoolMapper;
import com.yhwl.yhwl.esc.api.service.BallPoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BallPoolServiceImpl extends ServiceImpl<BallPoolMapper, BallPool> implements BallPoolService {
	@Override
	public IPage<BallPool> findByPage(Page page) {
		QueryWrapper queryWrapper=new QueryWrapper();
		queryWrapper.orderByDesc("create_date");
		queryWrapper.eq("user_name","");
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public List<BallPool> listByLevelAndStatus(int level, int status) {
		QueryWrapper<BallPool> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("ball_level",level);
		queryWrapper.eq("user_name","");
		queryWrapper.eq("status",status);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public IPage<BallPool> findPage(Page page, BallPool ballPool) {
		QueryWrapper<BallPool> queryWrapper =new QueryWrapper<>();
		if(ballPool.getStatus()!=null){
			queryWrapper.eq("status",ballPool.getStatus());
		}
		if(ballPool.getUsername()!=null &&!ballPool.getUsername().equals("")){
			queryWrapper.eq("user_name",ballPool.getUsername());
		}
		if(ballPool.getBallLevel()!=null){
			queryWrapper.eq("ball_level",ballPool.getBallLevel());
		}
		queryWrapper.orderByDesc("create_date");
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public List<BallPool> findByUsername(String username) {
		QueryWrapper<BallPool> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_name",username);
		return baseMapper.selectList(queryWrapper);
	}
}
