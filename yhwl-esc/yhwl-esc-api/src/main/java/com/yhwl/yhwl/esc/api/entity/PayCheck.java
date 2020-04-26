package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import lombok.Data;

@Data
@TableName("pay_check")
public class PayCheck extends DataEntity<PayCheck> {

	//卖方
	@TableField("sell_username")
	private String sellUsername;

	@TableField("sell_phone")
	private String sellPhone;

	//买方
	@TableField("pay_username")
	private String payUsername;

	@TableField("pay_Phone")
	private String payPhone;


	@TableField("check_url")
	private String checkUrl;
}
