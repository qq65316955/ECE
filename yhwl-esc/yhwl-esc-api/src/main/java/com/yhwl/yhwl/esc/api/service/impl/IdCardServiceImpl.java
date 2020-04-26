package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.IdCard;
import com.yhwl.yhwl.esc.api.mapper.IdCardMapper;
import com.yhwl.yhwl.esc.api.service.IdCardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class IdCardServiceImpl extends ServiceImpl<IdCardMapper, IdCard>implements IdCardService {
	@Override
	public IPage<IdCard> findByPage(Page page, String realName,String idNumber,Integer status) {
		QueryWrapper<IdCard> queryWrapper=new QueryWrapper<>();
		if (StringUtils.isNotEmpty(realName)){
			queryWrapper.like("real_name",realName.trim());
		}
		if (StringUtils.isNotEmpty(idNumber)){
			queryWrapper.eq("id_number",idNumber);
		}
		if (status != null){
			queryWrapper.eq("status",status);
		}
		queryWrapper.orderByDesc("create_date");
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public IdCard findByUsername(String username) {
		QueryWrapper<IdCard> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_name",username);
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public IdCard findByNumber(String idnumber) {
		QueryWrapper<IdCard> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("id_number",idnumber);
		return baseMapper.selectOne(queryWrapper);
	}
}
