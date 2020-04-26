package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@TableName("exchange_details")
@Slf4j
@ApiModel("兑换记录")
public class ExchangeDetails extends DataEntity<ExchangeDetails> {

	@ApiModelProperty("兑换币种")
	@TableField("coin")
	private String coin;

	@TableField("amount")
	private double amount;

	@TableField("user_name")
	private String username;

	@TableField("cost_esc")
	private double costEsc;
}
