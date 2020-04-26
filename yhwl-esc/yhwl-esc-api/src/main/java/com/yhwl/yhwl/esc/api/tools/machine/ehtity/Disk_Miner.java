package com.yhwl.yhwl.esc.api.tools.machine.ehtity;

import lombok.Data;

@Data
public class Disk_Miner {

    //第几个分区
    private int index;

    //盘符名称
    private String devName;

    //盘符路径
    private String dirName;

    //盘符标志
    private long flags;

    //盘符类型,比如FAT32,NTFS
    private String sysTypeName;

    //文件系统类型名，比如本地硬盘，光驱，网络文件等
    private String typeName;

    //文件系统类型
    private int type;

    //总大小单位KB
    private long total;

    //剩余大小，单位KB
    private long free;

    //可用大小，单位KB
    private long avail;

    //已经使用量
    private long used;

    //资源利用率
    private String usePercent;

    //读出
    private long diskReads;

    //写入
    private long diskWrites;
}
