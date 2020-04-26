package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.MiningMachine;

import java.util.List;
import java.util.Map;

public interface MiningMachineService extends IService<MiningMachine> {

    MiningMachine findByMac(String mac);

    MiningMachine findByMaxSize(String username);

    List<MiningMachine> ListOnTime();

    IPage<MiningMachine> findPageByType(Page page,String username,String type);

    double sumMinerSettleByUsers(List<String> usernames);

    void updateTodayProByUserName(List<String> usernames);

    IPage<MiningMachine> pageByUsername(Page<MiningMachine> miningMachinePage, String username);

    MiningMachine findByName(String name);

    List<String> findAllUserName();

    void resetMinierTodayOnline();

    void flushOnlineStatus(long currentTimeMillis);

    void flushOnlineTime(int i);

    List<MiningMachine> listOptionMiner();

    boolean updateMinerBalance();

    Long sumOfDiskByUsername(String username);

    Long sumOfDiskByUsernameS(List<String> byUserToUserName);

    List<String> findLabelByMacUserName(String minerId, String userName);

    int findTodayMachine();

    int findTotalMachine();

    List<MiningMachine> findByActive(long currentTime);

    List<Map<String, Object>> increaseData(Integer days);

	List<MiningMachine> findByUserName(String userName);

	MiningMachine getById(String id);

	IPage findByPage(Page page, String username, Integer state,String status,String coin);


	boolean cleanAll();

	boolean updateMinerBalanceByUserName();

	IPage findMachineListByPage(Page page, MiningMachine miningMachine);

}

