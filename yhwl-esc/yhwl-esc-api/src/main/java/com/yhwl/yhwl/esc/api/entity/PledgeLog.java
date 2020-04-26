package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName PledgeLog
 * @Description 质押记录
 * @Author cl
 * @Data 2019/8/6 16:41
 * @Version V1.0
 **/
@Data
@ApiModel
@TableName("pledge_log")
public class PledgeLog extends DataEntity<PledgeLog> {

    @ApiModelProperty("用户名称(单位KB)")
    @TableField("user_name")
    private String username;

    @ApiModelProperty("矿机地址")
    @TableField("mac")
    private String mac;

    @ApiModelProperty("转入前用户余额")
    @TableField("balance")
    private double balance;

    @ApiModelProperty("转入币数量")
    @TableField("num")
    private double num;

    @ApiModelProperty("转入前质押币余额")
    @TableField("pledge_currency")
    private double pledgeCurrency;

    @ApiModelProperty("币种")
    @TableField("coin_name")
    private String coinName="BMJ";

}
