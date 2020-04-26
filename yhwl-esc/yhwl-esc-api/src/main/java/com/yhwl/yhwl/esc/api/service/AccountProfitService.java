package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.AccountProfit;

import java.util.List;

public interface AccountProfitService extends IService<AccountProfit> {
	List<AccountProfit> listByLevel1();

	int updateLeaderNumber(String users);

	Double sumTodayProduceAll();

	Double sumTotalProduceAll();

	int updateReferenceNum(String username);

	List<AccountProfit> listByLevel2();

	IPage<AccountProfit> PageByUsername(Page page,List<String> users);

    int updateDiskByUserName(String username, Long disk);

    AccountProfit findByUserName(String username);

    /**
     * @MethodName: updateminerNumberByUserName
     * @escription: 团队人数加1
     * @Param: userName 推荐人
     * @Return: int
     * @Time: 2-25 第5周
     * @Author: jianpòlan
     */
    int updateminerNumberByUserName(String userName);

    /**
     * @Dscription: 修改上级的团队质押大小
     * @MethodName: updateUpperPledgeSize
     * @Param: upperUsers 包含自己
     * @Param: size
     * @Return: int
     * @Time: 2-25 第5周
     * @Author: jianpòlan
     */
    int updateUpperPledgeSize(List<String> upperUsers, long size);

    /**
     * @Dscription: 修改上级团队人数
     * @MethodName: updateUpperTeamNumber
     * @Param: upperUsers 包含自己
     * @Param: number
     * @Return: int
     * @Time: 2-25 第5周
     * @Author: jianpòlan
     */
    int updateUpperTeamNumber(List<String> upperUsers, int number);

    /**
     * @Dscription: 修改上级团队总算力
     * @MethodName: updateUpperMinerDisk
     * @Param: upperUsers 包含自己
     * @Param: size
     * @Return:
     * @Time: 2-25 第5周
     * @Author: jianpòlan
     */
    int updateUpperMinerDisk(List<String> upperUsers, long size);

    /**
     * @Dscription: 修改上级用户收益（包含个人和团队的今日收益、总收益）
     * @MethodName: updateUpperTodayProfit
     * @Param: upperUsers 包含自己
     * @Param: profit
     * @Return: int
     * @Time: 2-25 第5周
     * @Author: jianpòlan
     */
    int updateUpperTodayProfit(List<String> upperUsers, double profit);

    boolean testUpdateTodayProfit();

    int updatPledgeByUserName(String username, Double size);

    int updatTotalPledgeByUserName(String username, Double size);

    int updateMinerNumber(List<String> upperUsers);

    List<AccountProfit> findByMac(String mac);

    int updateDiskByUserNameTeam(String username, long disk);

    boolean updateMeTodayProfit();

    List<AccountProfit> listBySprofit();

    List<String> listByLeaderLevel();

    int updateProftByUsername(String username,double profit);



}
