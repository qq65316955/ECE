package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 矿机出租
 */
@ApiModel
@Data
@TableName("miner_rental")
public class MinerRental extends DataEntity<MinerRental> {
    @ApiModelProperty("用户名")
    @TableField("user_name")
    private String username;

    @ApiModelProperty("金额")
    @TableField("amount")
    private Double amount;

    @ApiModelProperty("空间")
    @TableField("size")
    private Long size;

    @ApiModelProperty("mac地址")
    @TableField("mac")
    private String mac;

    @ApiModelProperty("矿机id")
    @TableField("miner_id")
    private String minerId;

    @ApiModelProperty("审核状态，0未审核，1审核通过 ")
    @TableField("state")
    private Integer state;
}
