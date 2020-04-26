package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 云矿机
 */
@Data
@TableName(value = "mining_machine")
@ApiModel
public class MiningMachine extends DataEntity<MiningMachine> {


    @ApiModelProperty("绑定用户名")
    @TableField(value = "user_name")
    private String userName="";

    @ApiModelProperty("用户id")
	@TableField("user_id")
	private String userId;

    @ApiModelProperty("在线状态")
    @TableField(value = "online_state")
    private boolean onlineState=true;

	@ApiModelProperty("今日产值")
	@TableField(value = "today_pro")
	private double todayPro;


	@ApiModelProperty("总产值")
	@TableField(value = "total_pro")
	private double totalPro;

	@ApiModelProperty("当前天数")
	@TableField(value = "now_day")
	private int nowDay = 0;

	@ApiModelProperty("商品名")
	@TableField(value = "name")
	private String name;

	@ApiModelProperty("规格")
	@TableField("model")
	private int model;

	@ApiModelProperty("每日产值")
	@TableField("profit_perday")
	private  double profitPerday;

	@ApiModelProperty("算力")
	@TableField("power")
	private double power;

	@ApiModelProperty("剩余时间")
	@TableField("remain_time")
	private long remainTime;

	@ApiModelProperty("待领取收益")
	@TableField("balance")
	private double balance;

	@ApiModelProperty("质押币")
	@TableField("coin")
	private String coin;

	@ApiModelProperty("质押币数量")
	@TableField("pledge_num")
	private  double pledgeNum;

	@ApiModelProperty("质押状态")
	@TableField("status")
	private String status;

	@ApiModelProperty("是否赠送")
	@TableField("is_free")
	private boolean isFree;

	@ApiModelProperty("是否扣除算力")
	@TableField("is_settle")
	private boolean isSettle;


}

