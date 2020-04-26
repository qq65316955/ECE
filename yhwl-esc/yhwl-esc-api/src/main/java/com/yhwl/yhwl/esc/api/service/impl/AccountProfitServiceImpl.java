package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.common.tools.core.collection.CollUtil;
import com.yhwl.yhwl.esc.api.entity.AccountProfit;
import com.yhwl.yhwl.esc.api.mapper.AccountProfitMapper;
import com.yhwl.yhwl.esc.api.service.AccountProfitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountProfitServiceImpl extends ServiceImpl<AccountProfitMapper, AccountProfit> implements AccountProfitService {


	@Override
	public List<AccountProfit> listByLevel1() {
		return baseMapper.listByLevel1();
	}

	@Override
	public int updateLeaderNumber(String users) {

		return baseMapper.updateLeaderNumber(users);
	}

	@Override
	public Double sumTodayProduceAll() {
		return baseMapper.sumTodayProduceAll();
	}

	@Override
	public Double sumTotalProduceAll() {
		return baseMapper.sumTotalProduceAll();
	}

	@Override
	public int updateReferenceNum(String username) {
		return baseMapper.updateReferenceNum(username);
	}

	@Override
	public List<AccountProfit> listByLevel2() {
		return baseMapper.listByLevel2();
	}

	@Override
	public IPage<AccountProfit> PageByUsername(Page page,List<String> users) {
		QueryWrapper<AccountProfit> queryWrapper=new QueryWrapper<>();
		if(CollUtil.isEmpty(users)){
			return null;
		}
		queryWrapper.in("user_name",users);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
    public int updateDiskByUserName(String username, Long disk) {
        return baseMapper.updateDiskByUserName(username, disk);
    }

    @Override
    public AccountProfit findByUserName(String username) {
        return baseMapper.findByUserName(username);
    }

    @Override
    public int updateminerNumberByUserName(String username) {
        return baseMapper.updateminerNumberByUserName(username);
    }

    @Override
    public int updateUpperPledgeSize(List<String> upperUsers, long size) {
        if(CollUtil.isEmpty(upperUsers)){
            return 0;
        }
        return baseMapper.updateUpperPledgeSize(upperUsers,size);
    }

    @Override
    public int updateUpperTeamNumber(List<String> upperUsers, int number) {
        if(CollUtil.isEmpty(upperUsers)){
            return 0;
        }
        return baseMapper.updateUpperTeamNumber(upperUsers,number);
    }

    @Override
    public int updateUpperMinerDisk(List<String> upperUsers, long size) {
        if(CollUtil.isEmpty(upperUsers)){
            return 0;
        }
        return baseMapper.updateUpperMinerDisk(upperUsers,size);
    }

    @Override
    public int updateUpperTodayProfit(List<String> upperUsers, double profit) {
        if(CollUtil.isEmpty(upperUsers)){
            return 0;
        }
        return baseMapper.updateUpperTodayProfit(upperUsers,profit);
    }

	@Override
	public boolean testUpdateTodayProfit() {
		return baseMapper.testUpdateTodayProfit();
	}

	@Override
    public int updatPledgeByUserName(String username, Double size) {
        return baseMapper.updatPledgeByUserName(username, size);
    }

    @Override
    public int updatTotalPledgeByUserName(String username, Double size) {
        return baseMapper.updatTotalPledgeByUserName(username, size);
    }

    @Override
    public int updateMinerNumber(List<String> upperUsers) {
        if(CollUtil.isEmpty(upperUsers)){
            return 0;
        }
        return baseMapper.updateMinerNumber(upperUsers);
    }

    @Override
    public List<AccountProfit> findByMac(String mac) {
        QueryWrapper<AccountProfit> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mac",mac);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public int updateDiskByUserNameTeam(String username, long disk) {
        return baseMapper.updateDiskByUserNameTeam(username, disk);
    }

	@Override
	public boolean updateMeTodayProfit() {

		return baseMapper.updateMeTodayProfit();
	}

	@Override
	public List<AccountProfit> listBySprofit() {

		return baseMapper.listBySprofit();
	}

	@Override
	public List<String> listByLeaderLevel() {
		return baseMapper.listByLeaderLevel();
	}

	@Override
	public int updateProftByUsername(String username,double profit) {
		return baseMapper.updateProftByUsername(username,profit);
	}

}
