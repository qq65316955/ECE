package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("")
@Data
@TableName("ball_order")
public class BallOrder extends DataEntity<BallOrder> {

	@TableField("ball_id")
	private String ballId;

	@TableField("user_name")
	private String username="";

	//抢球状态，0在抢或者预约，1已经分配
	//0已抢  1未抢
	@TableField("status")
	private Integer status;

	@TableField("bind_user")
	private String bindUser;

	//6个时间段，1为11:00-11:25,2为1:00-1:25 3为15:00-15:25 4为17:00-17:25 5为18:00-18:25,6为20:00-20:25
	@TableField("time_slot")
	private Integer timeSlot;

	@TableField("cost")
	private double cost;

	@TableField("ball_level")
	private int ballLevel;


	//预约状态  1已经预约 ,0未预约
	@TableField("state")
	private Integer state;

	//转换为时间段
	@TableField(exist = false)
	private String time;

}
