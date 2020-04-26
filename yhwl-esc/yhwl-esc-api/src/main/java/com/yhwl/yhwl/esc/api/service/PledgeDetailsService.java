package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.PledgeDetails;

import java.util.List;

public interface PledgeDetailsService extends IService<PledgeDetails> {

	double sumMore(String username);

    List<PledgeDetails> listSettle();

    IPage<PledgeDetails> findByPage(Page page, String mac, String username, String status, String pledgeEnum, String coinName);

    long sumUnrelievedSizeByUserName(String username);

    long sumUnrelievedSizeByUserNames(List<String> byUserToUserName);

    double sumTodayProduceByMac(String username);

    double sumTodayProduceByUsername(String username);

    List<PledgeDetails> findByMinerId(String id);

    Double sumTodayProduceByUsernames(List<String> byUserToUserName);

    List<PledgeDetails> findByMac(String mac);
    
    boolean resetTodayProduceByMac(String mac);

    Long sumSizeByMinerId(String minerId);

    double sumPledgeCurrency(String mac);

    List<PledgeDetails> listUnzip(String userName);

    double sumTotalProduceByMinerid(String id);

    double sumTodayProduceAll();

    double sumTotalProduceAll();

    boolean saveTask(double amount, String id);


}
