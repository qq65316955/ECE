package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.AccountInfo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AccountInfoService
 * @Description: TODO
 * @Author alang
 * @Date 2-18 第4周
 * @Version V1.0
 **/
public interface AccountInfoService extends IService<AccountInfo> {

    AccountInfo findByUserName(String userName);

    AccountInfo findByNickName(String nickname);

    AccountInfo findByInvitationCode(String fromuser);

    AccountInfo findByBackups(String word);

    IPage findByPage(Page page, String usrname,String nickname,String phone);

    int findTodayUser();

    int findAllUserCount();

    List<Map<String, Object>> increaseData(Integer days);

    List<AccountInfo> listOther();

    List<String> listByActive();
}
