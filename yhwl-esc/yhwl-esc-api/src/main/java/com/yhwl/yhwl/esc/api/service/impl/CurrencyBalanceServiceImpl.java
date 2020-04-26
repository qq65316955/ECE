package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.CurrencyBalance;
import com.yhwl.yhwl.esc.api.mapper.CurrencyBalanceMapper;
import com.yhwl.yhwl.esc.api.service.CurrencyBalanceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyBalanceServiceImpl extends ServiceImpl<CurrencyBalanceMapper, CurrencyBalance> implements CurrencyBalanceService {

    @Override
    public CurrencyBalance findByUserNameAndCoinName(String username, String name) {
        QueryWrapper<CurrencyBalance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        queryWrapper.eq("name",name);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public CurrencyBalance findByAddressAndCoinName(String address, String coin) {
        QueryWrapper<CurrencyBalance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("address",address);
        queryWrapper.eq("name",coin);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<CurrencyBalance> findByUserName(String username) {
        QueryWrapper<CurrencyBalance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<CurrencyBalance> findByPage(Page<CurrencyBalance> page, CurrencyBalance currencyBalance) {
        QueryWrapper<CurrencyBalance> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(currencyBalance.getUserName())) {
            wrapper.like("user_name", currencyBalance.getUserName().trim());
        }
        if (StringUtils.isNotBlank(currencyBalance.getAddress())){
            wrapper.eq("address",currencyBalance.getAddress());
        }
        if(StringUtils.isNotBlank(currencyBalance.getName())){
            wrapper.eq("name",currencyBalance.getName());
        }
        if(currencyBalance.getLine()!=null){
            wrapper.eq("line",currencyBalance.getLine().getValue());
        }
        wrapper.orderByDesc("create_date");
        return  baseMapper.selectPage(page,wrapper);
    }

	@Override
	public boolean updateSettle(double amount, String id) {
		return baseMapper.updateSettle(amount,id);
	}

}
