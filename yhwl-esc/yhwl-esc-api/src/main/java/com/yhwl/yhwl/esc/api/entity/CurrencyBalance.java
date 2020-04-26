package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@TableName("currency_balance")
@Slf4j
@ApiModel
public class CurrencyBalance extends DataEntity<CurrencyBalance> {

    @ApiModelProperty("用户名")
    @TableField(value = "user_name")
    public String userName;

    @ApiModelProperty("地址")
    @TableField(value = "address")
    public String address;

    @ApiModelProperty("余额")
    @TableField(value = "balance")
    public Double balance = 0.0d;

    @ApiModelProperty("矿池产出")
	@TableField("miner_balance")
	public Double minerBalance;

    @ApiModelProperty("币名")
    @TableField(value = "name")
    public String name;

    @ApiModelProperty("冻结余额")
    @TableField(value = "freeze_money")
    public Double freezeMoney;

    @ApiModelProperty("主链")
    @TableField(value = "line")
    public CoinEnum line;

    @ApiModelProperty("图标地址")
    @TableField(value = "ico_url")
    public String icoUrl;

    @ApiModelProperty("二维码")
    @TableField(value = "qc_code")
    private String qrCode;

    @ApiModelProperty("排序")
    @TableField(value = "sort")
    private Integer sort;

    @TableField(exist = false)
    private double price;

}