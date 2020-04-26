package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import lombok.Data;

import java.util.Calendar;

@TableName(value = "lock_profit")
@Data
public class LockProfit extends DataEntity<LockProfit> {

    //用户名
    @TableField(value = "user_name")
    private String username;

    //昨日收益
    @TableField(value = "yestaday_profit")
    private double yestadayProfit = 0.0d;

    //总收益
    @TableField(value = "total_profit")
    private double totalProfit = 0.0d;

    //理财投入
    @TableField(value = "total_in")
    private double totalIn =0.0d;

    //当前锁定时间
    @TableField(value = "now_lock_day")
    private int nowLockDay = 0;

    //锁定时间
    @TableField(value = "lock_day")
    private int lockDay = 0;

    //状态 0正常 1完成 2释放中 3取消
    @TableField(value = "status")
    private int status =0;

    /**
     * 当前释放天数
     */
    @TableField(value = "release_day")
    private int releaseDay;
    /**
     * 释放数量
     */
    @TableField(value = "release_profit")
    private double releaseProfit;
    /**
     * 当前释放数量
     */
    @TableField(value = "release_profit_now")
    private double releaseProfitNow;

    @TableField(exist = false)
    private Long timerLong;

    public Long getTimerLong(){
        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTimeInMillis();
        calendar.setTimeInMillis(createDate.getTime());
        calendar.add(Calendar.DAY_OF_YEAR,lockDay);
        return calendar.getTimeInMillis() - start;
    }

    @JsonIgnore
    public double getBit(){
        double result = 0;
        do {
            result = lockDay == 90 ? (totalIn + totalIn*0.4)/90 :
                    lockDay == 180 ? (totalIn + totalIn*0.5)/180 :
                            lockDay == 365 ? (totalIn + totalIn*1.2)/365 : 0;
        }while (false);
        return result;
    }

    public double getPrepare(){
        double result = 0;
        do {
            result = lockDay == 90 ? (totalIn + totalIn*0.4) :
                    lockDay == 180 ? (totalIn + totalIn*0.5) :
                            lockDay == 365 ? (totalIn + totalIn*1.2) : 0;
        }while (false);
        return result;
    }

    @JsonIgnore
    public double getBitProfit(){
        return lockDay == 90 ? 0.4 : lockDay == 180 ? 0.5 : lockDay == 356 ? 1.2 : 0;
    }

}
