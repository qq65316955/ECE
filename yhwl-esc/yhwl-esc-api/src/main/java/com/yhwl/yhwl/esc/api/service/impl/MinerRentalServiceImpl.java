package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.MinerRental;
import com.yhwl.yhwl.esc.api.mapper.MinerRentalMapper;
import com.yhwl.yhwl.esc.api.service.MinerRentalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MinerRentalServiceImpl extends ServiceImpl<MinerRentalMapper, MinerRental> implements MinerRentalService {

    @Override
    public List<MinerRental> findAll() {
        QueryWrapper<MinerRental> wrapper = new QueryWrapper<>();
        wrapper.eq("state",1);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<MinerRental> findByUsername(String username) {
        QueryWrapper<MinerRental> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        wrapper.ne("state",2);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<MinerRental> findByDate(Date date) {
        QueryWrapper<MinerRental> wrapper = new QueryWrapper<>();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        Date date1 = c.getTime();
        c.add(Calendar.DAY_OF_YEAR,1);
        Date date2 = c.getTime();
        wrapper.between("create_date",date1,date2);
        wrapper.ne("state",2);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public IPage<MinerRental> findByPage(Page page, MinerRental minerRental) {
        QueryWrapper<MinerRental> wrapper = new QueryWrapper<>();
        if(minerRental.getState()!=null) {
            wrapper.eq("state",minerRental.getState());
        }
        if(StringUtils.isNotBlank(minerRental.getUsername())) {
            wrapper.like("user_name",minerRental.getUsername().trim());
        }
        if(StringUtils.isNotBlank(minerRental.getMac())) {
            wrapper.eq("mac",minerRental.getMac());
        }
        wrapper.orderByDesc("create_date");
        return baseMapper.selectPage(page,wrapper);

    }

	@Override
	public MinerRental findByMac(String mac) {
    	QueryWrapper<MinerRental> queryWrapper=new QueryWrapper<>();
    	queryWrapper.eq("mac",mac);
    	queryWrapper.eq("state",0);
		return baseMapper.selectOne(queryWrapper);
	}
}
