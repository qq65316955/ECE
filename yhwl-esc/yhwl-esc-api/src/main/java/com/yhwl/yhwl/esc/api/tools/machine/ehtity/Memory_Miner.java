package com.yhwl.yhwl.esc.api.tools.machine.ehtity;

import lombok.Data;

@Data
public class Memory_Miner {

    //内存总量
    private long total;

    //内存使用量
    private long used;

    //内存剩余量
    private long free;

    //交换区总量
    private long swap_total;

    //交换区使用量
    private long swap_used;

    //交换区剩余量
    private long swap_free;

}
