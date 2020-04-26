package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@TableName("energy_ball")
@Slf4j
@ApiModel("能量球配置")
public class EnergyBall extends DataEntity<EnergyBall> {

	@TableField("name")
	private String name;

	@TableField("round_day")
	private int roundDay;

	@TableField("min_exp")
	private double minPrice;

	@TableField("max_exp")
	private double maxPrice;

	@TableField("reward")
	private int reward;

	@TableField("profit")
	private double profit;

	//1为太阳能，2为水能，3内能，4核能，5磁能，6光能
	@TableField("level")
	private int level;



}
