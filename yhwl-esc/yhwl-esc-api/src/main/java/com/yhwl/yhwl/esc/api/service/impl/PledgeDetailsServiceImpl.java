package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.common.tools.core.collection.CollUtil;
import com.yhwl.yhwl.esc.api.entity.PledgeDetails;
import com.yhwl.yhwl.esc.api.mapper.PledgeDetailsMapper;
import com.yhwl.yhwl.esc.api.service.PledgeDetailsService;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PledgeDetailsServiceImpl extends ServiceImpl<PledgeDetailsMapper, PledgeDetails> implements PledgeDetailsService {

	@Override
	public double sumMore(String username) {
		return baseMapper.sumMore(username);
	}

	@Override
    public List<PledgeDetails> listSettle() {
        return baseMapper.listPledgeDetails();
    }

    @Override
    public IPage<PledgeDetails> findByPage(Page page, String mac, String username, String status, String pledgeEnum, String coinName) {
        QueryWrapper<PledgeDetails> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotBlank(mac)){
            queryWrapper.eq("miner_id",mac);
        }
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("user_name",username.trim());
        }
        if(StringUtils.isNotBlank(status)){
            queryWrapper.eq("status",status);
        }
        if(StringUtils.isNotBlank(pledgeEnum)){
            queryWrapper.eq("pledgeEnum",pledgeEnum);
        }
        if(StringUtils.isNotBlank(coinName)){
            queryWrapper.eq("coin_name",coinName);
        }
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public long sumUnrelievedSizeByUserName(String username) {
        return baseMapper.sumUnrelievedSizeByUserName(username);
    }

    @Override
    public long sumUnrelievedSizeByUserNames(List<String> byUserToUserName) {
        if(CollUtil.isEmpty(byUserToUserName)){
            return 0l;
        }
        return baseMapper.sumUnrelievedSizeByUserNames(byUserToUserName);
    }

    @Override
    public double sumTodayProduceByUsername(String username) {
        return baseMapper.sumTodayProduceByUsername(username);
    }

    @Override
    public List<PledgeDetails> findByMinerId(String id) {
        QueryWrapper<PledgeDetails> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("miner_id",id);
        queryWrapper.eq("del_flag",0);
        queryWrapper.orderByDesc("create_date");
        queryWrapper.eq("relieved",0);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
	public double sumTodayProduceByMac(String username) {

        return baseMapper.sumTodayProduceByMac(username);
    }

    @Override
    public Double sumTodayProduceByUsernames(List<String> byUserToUserName) {
        if(CollUtil.isEmpty(byUserToUserName)){
            return 0.0d;
        }
        return baseMapper.sumTodayProduceByUsernames(byUserToUserName);
    }

    @Override
    public List<PledgeDetails> findByMac(String mac) {
        QueryWrapper<PledgeDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("unrelieved_size",0);
        queryWrapper.eq("mac",mac);
        return  baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public boolean resetTodayProduceByMac(String mac) {
        return baseMapper.resetTodayProduceByMac(mac);
    }

    @Override
    public Long sumSizeByMinerId(String minerId) {
        return baseMapper.sumSizeByMinerId(minerId);
    }

    @Override
    public double sumPledgeCurrency(String mac) {
        return baseMapper.sumPledgeCurrency(mac);
    }

    @Override
    public List<PledgeDetails> listUnzip(String userName) {
        QueryWrapper<PledgeDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("unrelieved_size",0);
        queryWrapper.eq("user_name",userName);
        queryWrapper.eq("relieved",false);
        queryWrapper.eq("coin_name", CoinEnum.BMJ.getValue());
        queryWrapper.ne("pledgeEnum","BORN");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public double sumTotalProduceByMinerid(String id) {
        return baseMapper.sumTotalProduceByMinerid(id);
    }

    @Override
    public double sumTodayProduceAll() {
        return baseMapper.sumTodayProduceAll();
    }

    @Override
    public double sumTotalProduceAll() {
        return baseMapper.sumTotalProduceAll();
    }

	@Override
	public boolean saveTask(double amount,String id) {

		return baseMapper.saveTask(amount,id);
	}

}
