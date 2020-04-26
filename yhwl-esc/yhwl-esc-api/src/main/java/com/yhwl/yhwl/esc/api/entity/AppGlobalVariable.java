package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
@TableName("app_global_variable")
public class AppGlobalVariable extends DataEntity<AppGlobalVariable> {

    @ApiModelProperty("1T折合KB数")
    @TableField("t_to_kb")
    private long tToKb = 1073741824;

    @ApiModelProperty("挖矿间隔时间（秒）")
    @TableField("miner_time")
    private long minerTime = 30;

    @TableField("max_pledge")
    @ApiModelProperty("质押空间限制")
    private int maxPledge;

    @ApiModelProperty("是否可以挖矿")
    @TableField("miner_option")
    private boolean minerOption=true;

    @TableField("withdrawal")
    @ApiModelProperty("全局禁止提现")
    private boolean withdrawal = false;

    @ApiModelProperty("普通矿机是否可以挖矿")
    @TableField("common_miner")
    private Boolean commonMiner=false;

    @TableField("free_amount")
	private double freeAmount=0.1;

    @TableField("free_machine")
	private int freeMachine;

    @ApiModelProperty("esc兑换燃料比例")
    @TableField("esc_to_fire")
	private double escToFire;

	@ApiModelProperty("静态1代收益")
	@TableField("level1pro")
	private double level1pro;

	@ApiModelProperty("静态2代收益")
	@TableField("level2pro")
	private double level2pro;

	@ApiModelProperty("静态3代收益")
	@TableField("level3pro")
	private double level3pro;

	@ApiModelProperty("静态4-9代收益")
	@TableField("level49pro")
	private double level49pro;

	@TableField("leaderlv5")
	private double leaderlv5;

	@TableField("leaderlv4")
	private double leaderlv4;

	@TableField("leaderlv3")
	private double leaderlv3;

	@TableField("leaderlv2")
	private double leaderlv2;

	@TableField("leaderlv1")
	private double leaderlv1;


}