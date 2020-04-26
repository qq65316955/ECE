package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.RelieveDetails;
import com.yhwl.yhwl.esc.api.mapper.RelieveDetailsMapper;
import com.yhwl.yhwl.esc.api.service.RelieveDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class RelieveDetailsServiceImpl extends ServiceImpl<RelieveDetailsMapper, RelieveDetails> implements RelieveDetailsService {
    @Override
    public RelieveDetails findByPledgeId(String id) {
        QueryWrapper<RelieveDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pledge_id",id);
        queryWrapper.eq("status", "等待审核");
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public RelieveDetails findByminerId(String id) {
        QueryWrapper<RelieveDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("miner_id",id);
        queryWrapper.eq("status", "等待审核");
        return baseMapper.selectOne(queryWrapper);
    }


    @Override
    public IPage<RelieveDetails> findByPage(Page page, String username, String status, String minerId, String coinName) {
        QueryWrapper<RelieveDetails> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)) {
            wrapper.like("user_name", username.trim());
        }
        if(StringUtils.isNotBlank(status)){
            wrapper.eq("status",status);
        }
        if(StringUtils.isNotBlank(minerId)){
            wrapper.eq("minerId",minerId);
        }
        if(StringUtils.isNotBlank(coinName)){
            wrapper.eq("coin_name",coinName);
        }
        wrapper.orderByDesc("create_date");
        return  baseMapper.selectPage(page,wrapper);
    }
}
