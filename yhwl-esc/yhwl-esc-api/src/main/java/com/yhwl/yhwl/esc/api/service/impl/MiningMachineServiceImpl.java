package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.common.tools.core.collection.CollUtil;
import com.yhwl.yhwl.esc.api.entity.MiningMachine;
import com.yhwl.yhwl.esc.api.mapper.MiningMachineMapper;
import com.yhwl.yhwl.esc.api.service.MiningMachineService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MiningMachineServiceImpl extends ServiceImpl<MiningMachineMapper, MiningMachine> implements MiningMachineService {

    @Override
    public MiningMachine findByMac(String mac) {
        QueryWrapper<MiningMachine> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("mac",mac);
        return baseMapper.selectOne(queryWrapper);
    }

	@Override
	public MiningMachine findByMaxSize(String username) {
		return baseMapper.findByMaxSize(username);
	}

	@Override
	public List<MiningMachine> ListOnTime() {
    	QueryWrapper<MiningMachine> queryWrapper=new QueryWrapper<>();
    	queryWrapper.le("remain_time",System.currentTimeMillis());
    	queryWrapper.eq("is_settle",0);
		return baseMapper.selectList(queryWrapper);
	}


	@Override
	public IPage<MiningMachine> findPageByType(Page page, String username, String type) {
    	QueryWrapper<MiningMachine> queryWrapper=new QueryWrapper<>();
    	queryWrapper.eq("user_name",username);
    	queryWrapper.orderByDesc("create_date");
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public double sumMinerSettleByUsers(List<String> usernames) {
		return baseMapper.sumMinerSettleByUsers(usernames);
	}

	@Override
	public void updateTodayProByUserName(List<String> usernames) {
		baseMapper.updateTodayProByUserName(usernames);
	}

	@Override
    public IPage<MiningMachine> pageByUsername(Page<MiningMachine> miningMachinePage, String username) {
        QueryWrapper<MiningMachine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        return baseMapper.selectPage(miningMachinePage,queryWrapper);
    }

    @Override
    public MiningMachine findByName(String name) {
        QueryWrapper<MiningMachine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",name);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> findAllUserName() {
        return baseMapper.findAllUserName();
    }

    @Override
    public void resetMinierTodayOnline() {
        baseMapper.updateTodayOnline();
    }

    @Override
    public void flushOnlineStatus(long currentTimeMillis) {
        baseMapper.flushOnlineStatus(currentTimeMillis);
    }

    @Override
    public void flushOnlineTime(int i) {
        baseMapper.flushOnlineTime(i);
    }

    @Override
    public List<MiningMachine> listOptionMiner() {
        QueryWrapper<MiningMachine> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("user_name");
        queryWrapper.gt("pledge_size",0);
        return baseMapper.selectList(queryWrapper);
    }

//    @Override
//    public IPage findByPage(Page page, String username, Integer state,String status,String coin) {
//        return baseMapper.findByPage(page,username,state,status,coin);
//    }

    @Override
    public boolean updateMinerBalance() {
        return baseMapper.updateMinerBalance();
    }

	@Override
    public Long sumOfDiskByUsername(String username) {
        Long integer = baseMapper.sumOfDiskByUsername(username);
        return integer;
    }

    @Override
    public Long sumOfDiskByUsernameS(List<String> byUserToUserName) {
        if(CollUtil.isEmpty(byUserToUserName)){
            return 0L;
        }
        long integer = baseMapper.sumOfDiskByUsernameS(byUserToUserName);
        return integer;
    }

    @Override
    public List<String> findLabelByMacUserName(String minerId, String userName) {

        return baseMapper.findLabelByMacUserName(minerId, userName);
    }

    @Override
    public int findTodayMachine() {
        QueryWrapper<MiningMachine> queryWrapper = new QueryWrapper<>();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        Date tomorrow = calendar.getTime();
        queryWrapper.between("create_date",zero,tomorrow);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int findTotalMachine() {
        QueryWrapper<MiningMachine> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("user_name");
        queryWrapper.ne("user_name","");
        queryWrapper.ge("remain_time",System.currentTimeMillis());
        return baseMapper.selectCount(queryWrapper);
    }

	@Override
	public List<MiningMachine> findByActive(long currentTime) {

		return baseMapper.findByActive(currentTime);
	}

	@Override
    public List<Map<String, Object>> increaseData(Integer days) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR,0-days);
        Date before = calendar.getTime();
        QueryWrapper<MiningMachine> wrapper = new QueryWrapper<>();
        wrapper.between("create_date",before,date);
        wrapper.orderByAsc("create_date");
        List<MiningMachine> machines = baseMapper.selectList(wrapper);
        List<Map<String,Object>> list= new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        for(int i = 0;i < days;i++) {
            Map<String,Object> map = new HashMap<>();
            Date dis = calendar.getTime();
            map.put("name",sdf.format(dis));
            int sum = 0;
            for (MiningMachine machine : machines) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(machine.getCreateDate());
                if(calendar.get(Calendar.DAY_OF_YEAR) == c1.get(Calendar.DAY_OF_YEAR)) {
                    sum ++;
                }
            }
            map.put("value",sum);
            list.add(map);
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        return list;
    }

	@Override
	public List<MiningMachine> findByUserName(String userName) {
		QueryWrapper<MiningMachine> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_name",userName);
		queryWrapper.ge("remain_time",System.currentTimeMillis());
		queryWrapper.orderByAsc("create_date");
		return baseMapper.selectList(queryWrapper);
	}
	@Override
	public MiningMachine getById(String id) {
		QueryWrapper<MiningMachine> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",id);
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public IPage findByPage(Page page, String username, Integer state, String status, String coin) {
		return null;
	}

	@Override
	public IPage findMachineListByPage(Page page, MiningMachine miningMachine) {
		QueryWrapper<MiningMachine> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(miningMachine.getUserName())) {
			queryWrapper.like("user_name", miningMachine.getUserName().trim());
		}
		if (StringUtils.isNotBlank(miningMachine.getName())) {
			queryWrapper.eq("name", miningMachine.getName());
		}
		queryWrapper.orderByDesc("create_date");
		return baseMapper.selectPage(page, queryWrapper);
	}

	@Override
	public boolean cleanAll() {
		return baseMapper.cleanAll();
	}

	@Override
	public boolean updateMinerBalanceByUserName() {
		return baseMapper.updateMinerBalanceByUserName();
	}
}
