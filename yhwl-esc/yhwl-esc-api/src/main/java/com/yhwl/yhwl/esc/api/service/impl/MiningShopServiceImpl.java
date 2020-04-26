package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.MiningShop;
import com.yhwl.yhwl.esc.api.mapper.MiningShopMapper;
import com.yhwl.yhwl.esc.api.service.MiningShopService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者: yangkunhao
 * 时间: 2020/3/11 6:20 下午
 */
@Service
public class MiningShopServiceImpl extends ServiceImpl<MiningShopMapper, MiningShop> implements MiningShopService {
	@Override
	public List<MiningShop> getByUserName(String userName) {
		QueryWrapper<MiningShop> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_name", userName);
		return baseMapper.selectList(queryWrapper);
	}


	@Override
	public IPage findByPage(Page page, MiningShop miningShop) {
		QueryWrapper<MiningShop> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(miningShop.getName())){
			queryWrapper.eq("name",miningShop.getName());
		}
		queryWrapper.orderByDesc("create_date");
		return baseMapper.selectPage(page, queryWrapper);
	}

	@Override
	public MiningShop findByName(String name) {
		QueryWrapper<MiningShop> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", name);
		return baseMapper.selectOne(queryWrapper);
	}

}
