package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.BaseEntity;
import lombok.Data;

/**
 * @ClassName BmjProduct
 * @Description: BMJ币况（质押审核通过和解押审核通过都需要修改pledge值）（天亮了）
 * @Author jianpòlan
 * @Date 2-27 第5周
 * @Version V1.0
 **/
@Data
@TableName("bmj_product")
public class BmjProduct extends BaseEntity<BmjProduct> {

    //总发行量
    @TableField(value = "total_issue")
    private double totalIssue;

    //已产出
    @TableField(value = "production")
    private double production;

    //已质押，单位BMJ
    @TableField(value = "pledge")
    private double pledge;

    //今日销毁
    @TableField(value = "today_destroy")
    private double todayDestroy;

    //总销毁
    @TableField(value = "total_destroy")
    private double totalDestroy;

}
