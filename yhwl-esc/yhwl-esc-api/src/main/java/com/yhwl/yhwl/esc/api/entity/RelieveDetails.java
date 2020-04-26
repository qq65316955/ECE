package com.yhwl.yhwl.esc.api.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import com.yhwl.yhwl.esc.api.base.TransactionStatusEnum;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import lombok.Data;

/**
 * 解押记录
 */
@Data
@TableName("relieve_details")
public class RelieveDetails extends DataEntity<RelieveDetails> {

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String username="";

    /**
     * 质押ID
     */
    @TableField(value = "pledge_id")
    private String pledgeId="";

    @TableField("miner_id")
    private String minerId="";


    /**
     * 数量
     */
    @TableField(value = "num")
    private double num=0.0d;

    @TableField(value = "status")
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private TransactionStatusEnum status = TransactionStatusEnum.WAIT;

    /**
     * 币种
     */
    @TableField(value = "coin_name")
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private CoinEnum coinName;


    /**
     * mac地址
     */
    @TableField(value = "mac")
    private String mac;
}
