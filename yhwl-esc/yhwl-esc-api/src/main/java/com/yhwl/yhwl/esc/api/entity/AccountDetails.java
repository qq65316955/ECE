package com.yhwl.yhwl.esc.api.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import com.yhwl.yhwl.esc.api.base.FromUseEnum;
import com.yhwl.yhwl.esc.api.base.InOrOutEnum;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.NumberFormat;

/**
 * 账户详细
 * 记录所有用户的收入明细
 * 记录所有用户的支出明细
 * 通过这些记录可以校对用户的余额是否正确
 */
@Data
@TableName("account_details")
@ApiModel
public class AccountDetails extends DataEntity<AccountDetails> {

    @ApiModelProperty("用户名")
    @TableField(value = "user_name")
    private String username;

    @ApiModelProperty("金额")
    @TableField(value = "amount")
    private double amount =0.0;

    @TableField(exist = false)
    private String amountShow="";

    @ApiModelProperty("使用币种")
    @TableField(value = "currency")
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private CoinEnum currency;

    @ApiModelProperty("进出标识")
    @TableField(value = "in_or_out")
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private InOrOutEnum inOrOut;

    @ApiModelProperty("来源说明")
    @TableField(value = "from_user")
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private FromUseEnum fromuser;

	@ApiModelProperty("标识")
	@TableField(value = "remarks")
	private String remarks;


    public void parseString(){
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMaximumFractionDigits(6);
        setAmountShow(instance.format(getAmount()));
    }
}



