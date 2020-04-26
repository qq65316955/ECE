package com.yhwl.yhwl.esc.api.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.AccountInfo;
import com.yhwl.yhwl.esc.api.entity.UserLevel;

import java.util.List;
import java.util.Map;

public interface UserLevelService extends IService<UserLevel> {
	List<UserLevel> findByUsernameOnUPAndLevel(String username,int i);

	UserLevel findByLeftAndRight(String left,String right);

	List<String> findByUserUpString3(String username);

    List<UserLevel> findByUsernameOnUP(String username);

    List<String> findByUserDown9(String username);

    List<UserLevel> findByUsernameOnDown(String username);

    List<UserLevel> findByUsernameOnDownAndLevel(String username, int i);

    List<String> findByUserToUserName(String username);

    List<String> findOnLevelByUserToUserName(String name, int i);

    List<UserLevel> findAllUp(String username);

    List<String> findByUserUpString(String username);

    List<String> findDownUserByUsernameAndLevelLe(String username,int i);

    List<UserLevel> findDownUserByUsernameAndLevel(String username, int i);

    List<UserLevel> findDownUserByUsernameAndLevelGt(String username, int i);

    Map findRank(String username);

    boolean bindding(AccountInfo username, AccountInfo byInvitationCode);

    List<String> findByUserDownAndLevelGeAndLe(String username,int down,int top);

    List<UserLevel> findUpByNum(String username,int i);

    List<String> findByUserUpString9(String username);

	List<UserLevel> findByUsernameOnUPOnlevel(String username, int i);
}
