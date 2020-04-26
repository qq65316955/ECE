package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.CoinList;
import com.yhwl.yhwl.esc.api.entity.MiningMachine;
import com.yhwl.yhwl.esc.api.mapper.CoinListMapper;
import com.yhwl.yhwl.esc.api.service.CoinListService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class CoinListServiceImpl extends ServiceImpl<CoinListMapper, CoinList> implements CoinListService {
    @Override
    public IPage<CoinList> findByPage(Page page, CoinList coinList) {
        QueryWrapper<CoinList> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(coinList.getName())){
            queryWrapper.eq("name",coinList.getName());
        }
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(page,queryWrapper);
    }

    //今日新增币数量
	@Override
	public int findTodayCoinCount() {
		QueryWrapper<CoinList> queryWrapper = new QueryWrapper<>();
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
}
