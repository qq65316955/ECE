package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.TransactionRecord;
import com.yhwl.yhwl.esc.api.mapper.TransactionRecordMapper;
import com.yhwl.yhwl.esc.api.service.TransactionRecordService;
import org.springframework.stereotype.Service;

@Service
public class TransactionRecordServiceImpl extends ServiceImpl<TransactionRecordMapper, TransactionRecord> implements TransactionRecordService {

	@Override
    public IPage<TransactionRecord> pageByAddressAndCoin(Page<TransactionRecord> transactionRecordPage, String address, String userName, String coin, String type) {
        QueryWrapper<TransactionRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coin",coin);
		queryWrapper.and(i->i.eq("send_user",userName).or().eq("get_user",userName));
        if(type.equals("OUT")){
            queryWrapper.eq("send_address",address);
        }else if(type.equals("IN")){
            queryWrapper.eq("get_address",address);
        }else if(type.equals("ALL")){
            queryWrapper.and(i->i.eq("send_address",address).or().eq("get_address",address));
        }
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(transactionRecordPage,queryWrapper);
    }

    @Override
    public TransactionRecord findByHash(String hash) {
        QueryWrapper<TransactionRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("hash",hash);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<TransactionRecord> findByPage(Page page, String sendUser, String getUser, String coin, String status) {
        QueryWrapper<TransactionRecord> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(sendUser)) {
            queryWrapper.like("send_user", sendUser.trim());
        }
        if(StringUtils.isNotBlank(getUser)) {
            queryWrapper.like("get_user",getUser.trim());
        }
        if(StringUtils.isNotBlank(coin)){
            queryWrapper.eq("coin",coin);
        }
        if (StringUtils.isNotBlank(status)){
            queryWrapper.eq("status",status);
        }
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(page,queryWrapper);
    }
}
