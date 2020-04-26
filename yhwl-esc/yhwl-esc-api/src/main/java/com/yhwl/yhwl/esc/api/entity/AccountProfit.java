package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import lombok.Data;

/**
 * 用户收益统计
 */
@TableName("account_profit")
@Data
public class AccountProfit extends DataEntity<AccountProfit> {

    @TableField("user_name")
    private String username;

    //今日收益，包含下级
    @TableField("today_profit")
    private double todayProfit=0.0d;

    @TableField("today_leader")
	private double todayLeader=0.0d;

	@TableField("total_leader")
	private double totalLeader=0.0d;

    //总收益 包含下级和自己
    @TableField("total_profit")
    private double totalProfit=0.0d;

    @TableField("leader_number")
	private Integer leaderNumber=0;

    // 团队人数
    @TableField("miner_number")
    private Integer minerNumber=0;

    //推荐人数
    @TableField("references_number")
    private Integer referencesNumber = 0;

    //激活人数
    @TableField("active_number")
    private Integer activeNumber = 0;

    // 用户下级质押大小
    @TableField("total_pledge_size")
    private Double totalPledgeSize=0.0;

    // 用户团队总矿池算力
    @TableField("is_level1")
    private boolean isLevel1;

	@TableField("leader_level")
	private int leaderLevel=0;

    // 用户总矿池算力
    @TableField("disk")
    private Long disk=0l;

    // 用户质押大小
    @TableField("pledge_size")
    private Double pledgeSize=0.0;

    // 自己的今日收益
    @TableField("me_today_profit")
    private double meTodayProfit=0.0d;

    // 自己的总收益
    @TableField("me_total_profit")
    private double meTotalProfit = 0d;

    @TableField("sprofit")
    private double sprofit=0d;

    @TableField("more_transaction")
	private double moreTransaction;

}
