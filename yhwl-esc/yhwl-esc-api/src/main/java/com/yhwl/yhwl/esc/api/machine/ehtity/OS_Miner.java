package com.yhwl.yhwl.esc.api.machine.ehtity;

import lombok.Data;

@Data
public class OS_Miner {

    //操作系统
    private String arch;

    //操作系统CpuEndian
    private String cpuEndian;

    //操作系统dataModel
    private String dataModel;

    //操作系统名称
    private String vendorName;

    //操作系统版本号
    private String version;

    //操作系统的描述
    private String description;
}
