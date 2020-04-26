package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作者: yangkunhao
 * 时间: 2020/3/11 5:13 下午
 * 矿机商店
 */
@Data
@TableName(value = "mining_shop")
@ApiModel
public class MiningShop extends DataEntity<MiningShop> {

	@ApiModelProperty("矿机规格")
	@TableField("model")
	private int model;

	@ApiModelProperty("商品名")
	@TableField(value = "name")
	private String name = "";

	@ApiModelProperty("算力")
	@TableField(value = "t_count")
	private double tCount;

	@ApiModelProperty("周期")
	@TableField("round")
	private int round;


	@ApiModelProperty("起购数")
	@TableField(value = "buy_count")
	private int buyCount=0;

	@ApiModelProperty("单价/USDT")
	@TableField(value = "price")
	private double price=0.0d;

	@ApiModelProperty("币种")
	@TableField(value = "coin")
	private CoinEnum coin;

	@ApiModelProperty("最小回报比例(天)")
	@TableField(value = "min_out")
	private double minOut = 0.0d;

	@ApiModelProperty("最大回报比例(天)")
	@TableField(value = "max_out")
	private double maxOut = 0.0d;

	@ApiModelProperty("回报比例(天)")
	@TableField(value = "out_day")
	private double outDay;

	@ApiModelProperty("产量")
	@TableField("produce")
	private double produce;

	@ApiModelProperty("销售截止时间")
	@TableField(value = "sell_day")
	private int sellDay = 0;

	@ApiModelProperty("是否可售")
	@TableField(value = "can_sell")
	private int canSell;

	@ApiModelProperty("可使用天数,-1为永久")
	@TableField(value = "use_day")
	private int useDay = -1;

	@ApiModelProperty("扣款周期")
	@TableField(value = "max_day")
	private double maxDay = 0.0d;

	@ApiModelProperty("维护比例")
	@TableField(value = "maintain")
	private double maintain = 0.0d;
}
