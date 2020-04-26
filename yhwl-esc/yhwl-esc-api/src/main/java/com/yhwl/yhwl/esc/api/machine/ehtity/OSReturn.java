package com.yhwl.yhwl.esc.api.machine.ehtity;

import lombok.Data;

import java.util.List;

@Data
public class OSReturn {

    private List<Cpu_Miner> cpuInfo;

    private List<Disk_Miner> diskInfo;

    private Memory_Miner memoryInfo;

    private OS_Miner osInfo;

    private List<Net_Miner> netInfo;

    private String ip;

    private String mac;

}
