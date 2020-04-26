package com.yhwl.yhwl.esc.api.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import com.yhwl.yhwl.esc.api.base.PledgeEnum;
import com.yhwl.yhwl.esc.api.base.TransactionStatusEnum;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 质押记录
 */
@Data
@TableName("pledge_details")
public class PledgeDetails extends DataEntity<PledgeDetails> {

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String username="";

    /**
     * 申请质押空间
     */
    @TableField(value = "size")
    private Long size;

    /**
     * 状态
     */
    @TableField(value = "status")
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private TransactionStatusEnum status = TransactionStatusEnum.WAIT;

    /**
     * 矿机id
     */
    @TableField(value = "miner_id")
    private String minerId;

    /**
     * mac地址
     */
    @TableField(value = "mac")
    private String mac;

    /**
     * 币名
     */
    @TableField("coin_name")
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private CoinEnum coinName;

    /**
     * 质押类型
     */
    @TableField("pledgeEnum")
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private PledgeEnum pledgeEnum;


    /**
     * 是否已解压
     */
    @TableField("relieved")
    private Boolean relieved = false;

    /**
     * 解押时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("relieved_date")
    private Timestamp relievedDate;

    /**
     * 金额数量
     */
    @TableField("relieved_amount")
    private Double relievedAmount = 0D;

    /**
     * 今日产出
     */
    @TableField(value = "today_produce")
    private Double todayProduce=0.0d;

    /**
     * 总产出
     */
    @TableField(value = "total_produce")
    private Double totalProduce=0.0d;

    /**
     * 未解押空间
     */
    @TableField("unrelieved_size")
    private Long unrelievedSize = 0L;


}