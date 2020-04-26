package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.AccountDetails;

import java.util.List;
import java.util.Map;

public interface AccountDetailsService extends IService<AccountDetails> {

	double sumtodayProfit(String username);

    IPage<AccountDetails> pageByUsernameOrMac(Page<AccountDetails> accountDetailsPage, String username, String mac);

    IPage<AccountDetails> pageByUsernameOnType(Page<AccountDetails> accountDetailsPage, String username, String descp);

	IPage<AccountDetails> pageByUsernameOnTypeMac(Page<AccountDetails> accountDetailsPage, String username, String mac,String descp);

    Double sumByUserOnAllIn(String rightName);

    Double sumByUserReferenceAndRemarks(String username,String rename);

    /**
     * 今日矿池收益
     * @param username
     * @return
     */
    Double sumTodayPoolProfit(String username, String coinName);

    /**
     * 矿池总收益
     * @param username
     * @return
     */
    Double sumTotalPoolProfit(String username, String coinName);

    IPage<AccountDetails> findByPage(IPage<AccountDetails> page, AccountDetails accountDetails);

    IPage<AccountDetails> pageByUsernameOrTeam(Page<AccountDetails> accountDetailsPage, String userName);

    Double todayIncom();

    Double totalIncom();

    Double sumByUserReference(String username);

    List<Map<String, Object>> increaseData(Integer days);

    List<AccountDetails> listByTest();

    AccountDetails findByUsernameAndCreateDate(String username);

    Double sumByUserOnTeam(String username);

    Double findByUsernameAndGet(String mac);

    List<AccountDetails> findByCreateDate();

    Double sumProfitGetByUsername(String mac);

    List<AccountDetails> findBymacAnd2(String mac);

    List<AccountDetails> findBymacAnd3(String mac);

    List<AccountDetails> listByTest1();
}
