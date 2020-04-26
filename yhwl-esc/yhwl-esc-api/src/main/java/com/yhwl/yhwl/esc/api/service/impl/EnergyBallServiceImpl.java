package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.EnergyBall;
import com.yhwl.yhwl.esc.api.mapper.EnergyBallMapper;
import com.yhwl.yhwl.esc.api.service.EnergyBallService;
import org.springframework.stereotype.Service;

@Service
public class EnergyBallServiceImpl extends ServiceImpl<EnergyBallMapper,EnergyBall> implements EnergyBallService {
	@Override
	public IPage findByPage(Page page, EnergyBall energyBall) {
		QueryWrapper<EnergyBall> queryWrapper=new QueryWrapper<>();

		queryWrapper.orderByAsc("level");
		return baseMapper.selectPage(page,queryWrapper);
	}
}
