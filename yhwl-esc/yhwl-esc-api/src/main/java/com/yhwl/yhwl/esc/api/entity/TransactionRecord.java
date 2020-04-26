package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import com.yhwl.yhwl.esc.api.base.TransactionStatusEnum;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@TableName("transaction_record")
@ApiModel
public class TransactionRecord extends DataEntity<TransactionRecord> {

    @ApiModelProperty("发送方地址")
    @TableField(value = "send_address")
    private String sendAddress;

    @ApiModelProperty("接收方用户名")
    @TableField(value = "send_user")
    private String sendUser;

    @ApiModelProperty(value = "接收方地址")
    @TableField(value = "get_address")
    private String getAddress;

    @ApiModelProperty(value = "接收方用户名")
    @TableField(value = "get_user")
    private String getUser;

    @ApiModelProperty("发送方金额")
    @TableField(value = "samount")
    private double samount;

    @ApiModelProperty("接收方金额")
    @TableField(value = "gamount")
    private double gamount;

    @ApiModelProperty("币种")
    @TableField(value = "coin")
    private CoinEnum coin;

    @ApiModelProperty("交易状态")
    @TableField(value = "status")
    private TransactionStatusEnum status;

    @ApiModelProperty("交易hash")
    @TableField(value = "hash")
    private String hash;

    @ApiModelProperty("BMJ转账标签")
    @TableField(value = "source_tag")
    private String sourceTag;

}