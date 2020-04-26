package com.yhwl.yhwl.esc.api.tools.machine;

import com.alibaba.fastjson.JSON;
import com.yhwl.yhwl.esc.api.entity.MiningMachine;
import com.yhwl.yhwl.esc.api.machine.ehtity.Disk_Miner;
import com.yhwl.yhwl.esc.api.machine.ehtity.Memory_Miner;
import com.yhwl.yhwl.esc.api.machine.ehtity.Net_Miner;
import com.yhwl.yhwl.esc.api.tools.machine.ehtity.Cpu_Miner;
import com.yhwl.yhwl.esc.api.tools.machine.ehtity.OS_Miner;
import com.yhwl.yhwl.esc.api.tools.machine.security.SafeTools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MachineTools {
    public static MiningMachine getAbstractMachine() {
        MiningMachine miningMachine = new MiningMachine();
        miningMachine.setId(UUID.randomUUID().toString().replaceAll("-",""));
        // 设置网卡
        miningMachine.setOnlineState(false);
        return miningMachine;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(getAbstractMachine()));
    }

    private static List<Net_Miner> getNet(String mac,String ip) {
        String broadCast = ip.substring(0,ip.lastIndexOf("."))+".255";
        String mac1 = SafeTools.getRandomMac();
        String mac2 = SafeTools.getRandomMac();
        List<Net_Miner> list = new ArrayList<>();
        list.add(new Net_Miner("0.0.0.0","0.0.0.0",mac1,"0.0.0.0","WAN Miniport (IPv6)","Ethernet"));
        list.add(new Net_Miner("0.0.0.0","0.0.0.0",mac1,"0.0.0.0","WAN Miniport (Network Monitor)","Ethernet"));
        list.add(new Net_Miner("0.0.0.0","0.0.0.0",mac,"0.0.0.0","Intel(R) I211 Gigabit Network Connection-QoS Packet Scheduler-0000","Ethernet"));
        list.add(new Net_Miner("0.0.0.0","0.0.0.0",mac,"0.0.0.0","Intel(R) I211 Gigabit Network Connection-WFP LightWeight Filter-0000","Ethernet"));
        list.add(new Net_Miner("0.0.0.0","0.0.0.0",mac1,"0.0.0.0","WAN Miniport (IP)","Ethernet"));
        list.add(new Net_Miner("0.0.0.0","0.0.0.0",mac1,"0.0.0.0","WAN Miniport (Network Monitor)-QoS Packet Scheduler-0000","Ethernet"));
        list.add(new Net_Miner("0.0.0.0","0.0.0.0",mac2,"0.0.0.0","Intel(R) 82583V Gigabit Network Connection","Ethernet"));
        list.add(new Net_Miner(ip,broadCast,mac,"255.255.255.0","Intel(R) I211 Gigabit Network Connection","Ethernet"));
        list.add(new Net_Miner("0.0.0.0","0.0.0.0",mac1,"0.0.0.0","WAN Miniport (IP)-QoS Packet Scheduler-0000","Ethernet"));
        list.add(new Net_Miner("0.0.0.0","0.0.0.0",mac1,"0.0.0.0","WAN Miniport (IPv6)-QoS Packet Scheduler-0000","Ethernet"));

        return list;
    }

    private static OS_Miner getOs() {
        OS_Miner os = new OS_Miner();
        os.setArch("x64");
        os.setCpuEndian("little");
        os.setDataModel("64");
        os.setDescription("Microsoft Windows 7");
        os.setVendorName("Windows 7");
        return os;
    }

    private static Memory_Miner getMemory() {
        Memory_Miner memory = new Memory_Miner();
        memory.setFree(2848476L);
        memory.setSwap_free(6525076L);
        memory.setSwap_total(8163568L);
        memory.setSwap_used(1638492L);
        memory.setTotal(4082732L);
        memory.setUsed(1234256L);
        return memory;
    }

    private static List<Disk_Miner> getDisk() {
        List<Disk_Miner> list = new ArrayList<>();
        Disk_Miner c = new Disk_Miner();
        c.setAvail(20670464L);
        c.setDevName("C:\\");
        c.setDirName("C:\\");
        c.setDiskReads(31924L);
        c.setDiskWrites(47568L);
        c.setFlags(0L);
        c.setFree(20670464L);
        c.setSysTypeName("NTFS");
        c.setTotal(41944060L);
        c.setType(2);
        c.setTypeName("local");
        c.setUsePercent("51.0%");
        c.setUsed(21273596L);
        list.add(c);
        Disk_Miner d = new Disk_Miner();
        d.setAvail(20474500L);
        d.setDevName("D:\\");
        d.setDirName("D:\\");
        d.setDiskReads(26L);
        d.setDiskWrites(75L);
        d.setFlags(0L);
        d.setFree(20474500L);
        d.setSysTypeName("NTFS");
        d.setTotal(20576252L);
        d.setType(2);
        d.setTypeName("local");
        d.setUsePercent("1.0%");
        d.setUsed(101752L);
        list.add(d);
        for(int i = 0;i<4;i++) {
            Disk_Miner disk = new Disk_Miner();
            disk.setAvail(3906368140L);
            disk.setDiskReads(61L);
            disk.setDiskWrites(7L);
            disk.setFlags(0L);
            disk.setFree(3906368140L);
            disk.setSysTypeName("NTFS");
            disk.setTotal(3906579268L);
            disk.setType(2);
            disk.setTypeName("local");
            disk.setUsePercent("1.0%");
            disk.setUsed(211128L);
            if(i == 0) {
                disk.setDevName("F:\\");
                disk.setDirName("F:\\");
            } else if(i == 1) {
                disk.setDevName("G:\\");
                disk.setDirName("G:\\");
            } else if(i == 2) {
                disk.setDevName("H:\\");
                disk.setDirName("H:\\");
            } else {
                disk.setDevName("I:\\");
                disk.setDirName("I:\\");
            }
        }
        return list;
    }

    private static List<Cpu_Miner> getCpuInfo() {
        List<Cpu_Miner> list = new ArrayList<>();
        for(int i = 0;i < 4;i++) {
            Cpu_Miner cpu = new Cpu_Miner();
            cpu.setCacheSize(-1L);
            cpu.setCombined("0.0%");
            cpu.setMhz(2000);
            cpu.setModel("Celeron");
            cpu.setNick("0.0%");
            cpu.setSys("0.0%");
            cpu.setUse("0.0%");
            cpu.setWait("0.0%");
            cpu.setVendor("Intel");
            list.add(cpu);
        }
        return list;
    }
}
