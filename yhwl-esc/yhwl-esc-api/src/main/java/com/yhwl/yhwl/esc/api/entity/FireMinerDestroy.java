package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import lombok.Data;

/**
 * @ClassName FireMinerDestroy
 * @Description: 燃烧挖矿销毁记录（好困 不想写了（小声BB））
 * @Author jianpòlan
 * @Date 2-26 第5周
 * @Version V1.0
 **/
@Data
@TableName("fire_miner_destroy")
public class FireMinerDestroy extends DataEntity<FireMinerDestroy> {

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "amount")
    private double amount;

    @TableField(value = "mac")
    private String mac;

}
