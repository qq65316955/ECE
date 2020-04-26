package com.yhwl.yhwl.esc.api.machine.ehtity;

import lombok.Data;

@Data
public class Net_Miner {

    //IP地址
    private String address;

    //网关广播地址
    private String broadcast;

    //MAC地址
    private String mac;

    //子网掩码
    private String netMask;

    //网卡描述
    private String description;

    //网卡类型

    private String type;

    public Net_Miner(String address, String broadcast, String mac, String netMask, String description, String type) {
        this.address = address;
        this.broadcast = broadcast;
        this.mac = mac;
        this.netMask = netMask;
        this.description = description;
        this.type = type;
    }


}
