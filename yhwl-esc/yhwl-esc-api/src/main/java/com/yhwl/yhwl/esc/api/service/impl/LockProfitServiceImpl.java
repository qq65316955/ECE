package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.LockProfit;
import com.yhwl.yhwl.esc.api.mapper.LockProfitMapper;
import com.yhwl.yhwl.esc.api.service.LockProfitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockProfitServiceImpl extends ServiceImpl<LockProfitMapper, LockProfit> implements LockProfitService {

    @Override
    public List<LockProfit> listByUsername(String username, int type) {
        QueryWrapper<LockProfit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        if(type != -1){
            queryWrapper.eq("status", type);
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public double sumProfitByUserName(String userName) {
        return baseMapper.totalProfit(userName);
    }

    public double yesterdayProfit(String userName){
        return baseMapper.yesterdayProfit(userName);
    }

    @Override
    public double totalMoney(String userName) {
        return baseMapper.totalMoney(userName);
    }

    @Override
    public IPage findByPage(Page page, LockProfit lockProfit) {
        QueryWrapper<LockProfit> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(lockProfit.getUsername())){
            queryWrapper.like("user_name",lockProfit.getUsername().trim());
        }
        if(lockProfit.getStatus()!=4){
            queryWrapper.eq("status",lockProfit.getStatus());
        }
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(page,queryWrapper);
    }

}
