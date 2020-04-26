package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.Information;
import com.yhwl.yhwl.esc.api.mapper.InformationMapper;
import com.yhwl.yhwl.esc.api.service.InformationService;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements InformationService {

    @Override
    public IPage<Information> listByPage(Page<Information> informationPage) {
            QueryWrapper<Information> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("create_date");
            return baseMapper.selectPage(informationPage,queryWrapper);
    }

	@Override
	public IPage<Information> findByPage(Page page){
		QueryWrapper<Information> queryWrapper=new QueryWrapper<>();
		queryWrapper.orderByDesc("create_date");
		return baseMapper.selectPage(page, queryWrapper);
	}

}
