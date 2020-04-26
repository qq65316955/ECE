package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.AccountInfo;
import com.yhwl.yhwl.esc.api.entity.UserLevel;
import com.yhwl.yhwl.esc.api.mapper.UserLevelMapper;
import com.yhwl.yhwl.esc.api.service.AccountProfitService;
import com.yhwl.yhwl.esc.api.service.UserLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserLevelServiceImpl extends ServiceImpl<UserLevelMapper, UserLevel> implements UserLevelService {

    @Autowired
    private AccountProfitService accountProfitService;

	@Override
	public List<UserLevel> findByUsernameOnUPAndLevel(String username, int i) {

		return baseMapper.findByUsernameOnUPAndLevel(username,i);
	}

	@Override
	public UserLevel findByLeftAndRight(String left, String right) {
		QueryWrapper<UserLevel> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("left_name",left);
		queryWrapper.eq("right_name",right);
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public List<String> findByUserUpString3(String username) {
		return baseMapper.findByUserUpString3(username);
	}

	@Override
    public List<UserLevel> findByUsernameOnUP(String username) {
        QueryWrapper<UserLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("right_name",username);
        return baseMapper.selectList(queryWrapper);
    }

	@Override
	public List<String> findByUserDown9(String username) {

		return baseMapper.findByUserDown9(username);
	}

	@Override
    public List<UserLevel> findByUsernameOnDown(String username) {
        QueryWrapper<UserLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("left_name",username);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<UserLevel> findByUsernameOnDownAndLevel(String username, int i) {
        QueryWrapper<UserLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("left_name",username);
        queryWrapper.eq("level_num",i);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<String> findByUserToUserName(String username) {
        return baseMapper.findByUserToUserName(username);
    }

    @Override
    public List<String> findOnLevelByUserToUserName(String name, int i) {
        return baseMapper.findOnLevelByUserToUserName(name,i);
    }

    @Override
    public List<UserLevel> findAllUp(String username) {
        QueryWrapper<UserLevel> wrapper = new QueryWrapper<>();
        wrapper.eq("right_name",username);
        wrapper.orderByAsc("level_num");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<String> findByUserUpString(String username) {

        return baseMapper.findByUserUpString(username);
    }

	@Override
	public List<String> findDownUserByUsernameAndLevelLe(String username, int i) {


		return baseMapper.findDownUserByUsernameAndLevelLe(username,i);
	}

	@Override
    public List<UserLevel> findDownUserByUsernameAndLevel(String username, int i) {
        QueryWrapper<UserLevel> wrapper = new QueryWrapper<>();
        wrapper.eq("left_name",username);
        wrapper.eq("level_num",i);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<UserLevel> findDownUserByUsernameAndLevelGt(String username, int i) {
        QueryWrapper<UserLevel> wrapper = new QueryWrapper<>();
        wrapper.eq("left_name",username);
        wrapper.gt("level_num",i);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Map findRank(String username) {
        return baseMapper.inviteRank(username);
    }

    @Override
    public boolean bindding(AccountInfo account, AccountInfo fromAccount) {
        try{
            List<UserLevel> userLevels = findByUsernameOnUP(fromAccount.getUsername());
            UserLevel userLevel = new UserLevel();
            userLevel.setRightId(account.getId());
            userLevel.setRightName(account.getUsername());
            userLevel.setLeftId(fromAccount.getId());
            userLevel.setLeftName(fromAccount.getUsername());
            userLevel.setLevelNum(1);
            userLevel.insert();
            if(userLevels.size()!=0){
                for(UserLevel userLevel1: userLevels){
                    UserLevel ul = new UserLevel();
                    ul.setRightId(account.getId());
                    ul.setRightName(account.getUsername());
                    ul.setLeftId(userLevel1.getLeftId());
                    ul.setLeftName(userLevel1.getLeftName());
                    ul.setLevelNum(userLevel1.getLevelNum()+1);
                    ul.insert();
                }
            }
            return true;
        }catch (Exception e){
            log.error("绑定用户出错",e);
            return false;
        }
    }

	@Override
	public List<String> findByUserDownAndLevelGeAndLe(String username, int down, int top) {

		return baseMapper.findByUserDownAndLevelGeAndLe(username,down,top);
	}

	@Override
	public List<UserLevel> findUpByNum(String username, int i) {
		QueryWrapper<UserLevel> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("right_name",username);
		queryWrapper.le("level_num",i);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<String> findByUserUpString9(String username) {
		return baseMapper.findByUserUpString9(username);
	}

	@Override
	public List<UserLevel> findByUsernameOnUPOnlevel(String username, int i) {
		QueryWrapper<UserLevel> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("right_name",username);
		queryWrapper.eq("level_num",i);
		return baseMapper.selectList(queryWrapper);
	}


}
