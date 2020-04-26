package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.base.InOrOutEnum;
import com.yhwl.yhwl.esc.api.entity.AccountDetails;
import com.yhwl.yhwl.esc.api.mapper.AccountDetailsMapper;
import com.yhwl.yhwl.esc.api.service.AccountDetailsService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountDetailsServiceImpl extends ServiceImpl<AccountDetailsMapper, AccountDetails> implements AccountDetailsService {


	@Override
	public double sumtodayProfit(String username) {
		return baseMapper.sumtodayProfit(username);
	}

	@Override
    public IPage<AccountDetails> pageByUsernameOrMac(Page<AccountDetails> accountDetailsPage, String username, String mac) {
        QueryWrapper<AccountDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        if(!mac.equals("ALL")){
            queryWrapper.like("remarks",mac);
        }
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(accountDetailsPage,queryWrapper);
    }
	@Override
	public IPage<AccountDetails> pageByUsernameOnTypeMac(Page<AccountDetails> accountDetailsPage, String username,String mac, String descp) {
		QueryWrapper<AccountDetails> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotBlank(mac)){
			queryWrapper.eq("remarks",mac);
		}
		queryWrapper.eq("user_name",username);
		queryWrapper.eq("in_or_out",descp);
		queryWrapper.orderByDesc("create_date");
		return baseMapper.selectPage(accountDetailsPage,queryWrapper);
	}

    @Override
    public IPage<AccountDetails> pageByUsernameOnType(Page<AccountDetails> accountDetailsPage, String username, String descp) {
        QueryWrapper<AccountDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        queryWrapper.eq("in_or_out",descp);
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(accountDetailsPage,queryWrapper);
    }


    @Override
	public Double sumByUserOnAllIn(String username) {
        Double aDouble = baseMapper.sumByUserOnInOuts(username,"BMJcoin", InOrOutEnum.TEAM.getValue());
        return aDouble.doubleValue();
    }

	@Override
	public Double sumByUserReferenceAndRemarks(String username, String rename) {

		return baseMapper.sumByUserReferenceAndRemarks(username,rename);
	}

	@Override
    public Double sumTodayPoolProfit(String username, String coinName) {
        return baseMapper.sumTodayPoolProfit(username, coinName);
    }

    @Override
    public Double sumTotalPoolProfit(String username, String coinName) {
        return baseMapper.sumTotalPoolProfit(username, coinName);
    }

    @Override
    public IPage<AccountDetails> findByPage(IPage<AccountDetails> page, AccountDetails accountDetails) {
        QueryWrapper<AccountDetails> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(accountDetails.getUsername())) {
			queryWrapper.like("user_name", accountDetails.getUsername().trim());
		}
        if(accountDetails.getInOrOut()!=null){
            queryWrapper.eq("in_or_out",accountDetails.getInOrOut().getValue());
        }
        if(accountDetails.getFromuser()!=null) {
            queryWrapper.like("from_user", accountDetails.getFromuser().getValue());
        }
		if (StringUtils.isNotBlank(accountDetails.getRemarks())) {
			queryWrapper.eq("remarks",accountDetails.getRemarks());
		}
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public IPage<AccountDetails> pageByUsernameOrTeam(Page<AccountDetails> accountDetailsPage, String userName) {
        QueryWrapper<AccountDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        queryWrapper.eq("in_or_out","团队收益");
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(accountDetailsPage,queryWrapper);
    }

    @Override
    public Double todayIncom() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        Date tomorrow = calendar.getTime();
        return baseMapper.findTodayIncome(zero,tomorrow);
    }

    @Override
    public Double totalIncom() {
        return baseMapper.findTotalIncome();
    }

	@Override
	public Double sumByUserReference(String username) {
		return baseMapper.sumByUserReference(username);
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
        QueryWrapper<AccountDetails> wrapper = new QueryWrapper<>();
        wrapper.between("create_date",before,date);
        wrapper.eq("in_or_out","团队收益");
        wrapper.orderByAsc("create_date");
        List<AccountDetails> accounts = baseMapper.selectList(wrapper);
        List<Map<String,Object>> list= new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        for(int i = 0;i < days;i++) {
            Map<String,Object> map = new HashMap<>();
            Date dis = calendar.getTime();
            map.put("name",sdf.format(dis));
            double sum = 0;
            for (AccountDetails account : accounts) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(account.getCreateDate());
                if(calendar.get(Calendar.DAY_OF_YEAR) == c1.get(Calendar.DAY_OF_YEAR)) {
                    sum += account.getAmount();
                }
            }
            map.put("value",sum);
            list.add(map);
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        return list;
    }

	@Override
	public List<AccountDetails> listByTest() {
		return baseMapper.listByTest();
	}

	@Override
	public AccountDetails findByUsernameAndCreateDate(String username){
    	return baseMapper.findByUsernameAndCreateDate(username);
	}

	@Override
	public Double sumByUserOnTeam(String username) {
		return baseMapper.sumByUserOnTeam(username);
	}

	@Override
	public Double findByUsernameAndGet(String mac) {
		return baseMapper.findByUsernameAndGet(mac);
	}

	@Override
	public List<AccountDetails> findByCreateDate() {
		return baseMapper.findByCreateDate();
	}

	@Override
	public Double sumProfitGetByUsername(String mac) {
		return baseMapper.sumProfitGetByUsername(mac);
	}
	@Override
	public List<AccountDetails> findBymacAnd2(String mac){
    	return baseMapper.findBymacAnd2(mac);
	}
	@Override
	public List<AccountDetails> findBymacAnd3(String mac){
    	return baseMapper.findBymacAnd3(mac);
	}

	@Override
	public List<AccountDetails> listByTest1(){
    	return baseMapper.listByTest1();
	}
}
