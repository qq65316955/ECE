package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import lombok.Data;

@TableName(value = "lock_profit_log")
@Data
public class LockProfitLog extends DataEntity<LockProfitLog> {

    //用户名
    @TableField(value = "user_name")
    private String username;

    //昨日收益
    @TableField(value = "amount")
    private double amount = 0.0d;


}
