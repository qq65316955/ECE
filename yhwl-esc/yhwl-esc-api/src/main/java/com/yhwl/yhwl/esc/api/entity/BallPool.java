package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data
@TableName("ball_pool")
public class BallPool extends DataEntity<BallPool> {

	@TableField("price")
	private double price;

	@TableField("user_name")
	private String username="";

	@TableField("day")
	private int day=0;

	//1为太阳能，2为水能，3内能，4核能，5磁能，6光能
	@TableField("ball_level")
	private Integer ballLevel;

	@TableField("profit")
	private double profit;

	//能量球状态，0为未激活球，1为已激活未付款，2为已付款，卖家未确认,3为卖家已确认
	@TableField("status")
	private Integer status=0;

	@TableField("pay_time")
	private Long payTime;

	@TableField("confirm_time")
	private Long confirmTime;

	@TableField("sell_user")
	private String sellUser;
}
