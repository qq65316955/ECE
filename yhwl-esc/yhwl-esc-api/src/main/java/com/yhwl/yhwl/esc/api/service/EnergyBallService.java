package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.EnergyBall;

public interface EnergyBallService extends IService<EnergyBall> {
	IPage findByPage(Page page,EnergyBall energyBall);



	//int updateEnergyBall(EnergyBall energyBall);

}
