package com.yhwl.yhwl.esc.api.machine.ehtity;

import lombok.Data;

@Data
public class Cpu_Miner {

    //第几块CPU
    private int index;

    //cpu的总量mhz
    private int mhz;

    //cpu的卖主
    private String vendor;

    //cpu的类别
    private String model;

    //缓冲存储器数量
    private long cacheSize;

    //用户使用率
    private String use;

    //系统使用率
    private String sys;

    //等待率
    private String wait;

    //错误率
    private String nick;

    //空闲率
    private String idle;

    //总使用率
    private String combined;
}
