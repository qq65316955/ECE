package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.BallOrder;

import java.util.List;

public interface BallOrderService extends IService<BallOrder> {

	List<BallOrder> findByUsernameAndTime(String username,int time);

	//已经抢和未抢
	IPage findByPage(Page page,BallOrder ballOrder);

	BallOrder findByUsernameAndLevelAndStatus(String username,int level,int status,int state);

	List<BallOrder> ListByLevelAndTime(int level,int time,int status);

//	//预约和未预约
//	IPage findOrderByPage(Page page,BallOrder ballOrder);

}
