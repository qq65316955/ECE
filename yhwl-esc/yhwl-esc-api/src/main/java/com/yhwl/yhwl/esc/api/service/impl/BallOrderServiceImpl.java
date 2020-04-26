package com.yhwl.yhwl.esc.api.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.BallOrder;
import com.yhwl.yhwl.esc.api.mapper.BallOrderMapper;
import com.yhwl.yhwl.esc.api.service.BallOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BallOrderServiceImpl extends ServiceImpl<BallOrderMapper, BallOrder> implements BallOrderService {

	@Override
	public List<BallOrder> findByUsernameAndTime(String username, int time) {
		QueryWrapper<BallOrder> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_name",username);
		queryWrapper.eq("time_slot",time);
		return baseMapper.selectList(queryWrapper);
	}

	//抢购球与否
	@Override
	public IPage findByPage(Page page,BallOrder ballOrder) {

		QueryWrapper<BallOrder> queryWrapper=new QueryWrapper<>();
		if(ballOrder.getUsername()!=null&&!ballOrder.getUsername().equals("")){
			queryWrapper.eq("user_name",ballOrder.getUsername());
		}
		if(ballOrder.getStatus()!=null&&ballOrder.getStatus()!=3){
			queryWrapper.eq("status",ballOrder.getStatus());
		}
		if(ballOrder.getTimeSlot()!=null&&ballOrder.getTimeSlot()!=7){
			queryWrapper.eq("time_slot",ballOrder.getTimeSlot());
		}
		if (ballOrder.getState()!=null){
			queryWrapper.eq("state",ballOrder.getState());
		}
		queryWrapper.orderByAsc("time_slot");
		queryWrapper.orderByAsc("status");

		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public BallOrder findByUsernameAndLevelAndStatus(String username, int level, int status,int state) {
		QueryWrapper<BallOrder> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_name",username);
		queryWrapper.eq("status",status);
		queryWrapper.eq("ball_level",level);
		queryWrapper.eq("state",0);
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public List<BallOrder> ListByLevelAndTime(int level, int time,int status) {
		QueryWrapper<BallOrder> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("ball_level",level);
		queryWrapper.eq("time_slot",time);
		queryWrapper.eq("status",status);
		return baseMapper.selectList(queryWrapper);
	}

//	//预约球与否
//	@Override
//	public IPage findOrderByPage(Page page, BallOrder ballOrder) {
//		QueryWrapper<BallOrder> queryWrapper=new QueryWrapper<>();
//		if(ballOrder.getUsername()!=null&&!ballOrder.getUsername().equals("")){
//			queryWrapper.eq("user_name",ballOrder.getUsername());
//		}
//
//		if(ballOrder.getState()!=null&&ballOrder.getState()!=3){
//			queryWrapper.eq("state",ballOrder.getState());
//		}
//		if(ballOrder.getTimeSlot()!=null&&ballOrder.getTimeSlot()!=7){
//			queryWrapper.eq("time_slot",ballOrder.getTimeSlot());
//		}
//		queryWrapper.orderByDesc("create_date");
//		queryWrapper.orderByAsc("state");
//
//		return baseMapper.selectPage(page,queryWrapper);
//	}


}
