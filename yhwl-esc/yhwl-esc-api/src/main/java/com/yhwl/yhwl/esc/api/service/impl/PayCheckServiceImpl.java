package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.PayCheck;
import com.yhwl.yhwl.esc.api.mapper.PayCheckMapper;
import com.yhwl.yhwl.esc.api.service.PayCheckService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 */
@Service
public class PayCheckServiceImpl extends ServiceImpl<PayCheckMapper, PayCheck> implements PayCheckService {


	@Override
	public IPage<PayCheck> findByPage(Page page, PayCheck payCheck) {
		QueryWrapper<PayCheck> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("create_date");
		if (!StringUtils.isEmpty(payCheck.getSellUsername())) {
			queryWrapper.eq("sell_username", payCheck.getSellUsername());
		}
		if (!StringUtils.isEmpty(payCheck.getSellPhone())) {
			queryWrapper.eq("sell_phone", payCheck.getSellPhone());
		}
		if (!StringUtils.isEmpty(payCheck.getPayUsername())) {
			queryWrapper.eq("pay_username", payCheck.getPayUsername());
		}
		if (!StringUtils.isEmpty(payCheck.getPayPhone())) {
			queryWrapper.eq("pay_Phone", payCheck.getPayPhone());
		}
		return baseMapper.selectPage(page, queryWrapper);
	}

	//支付凭证
	@Override
	public IPage PaymentVoucher(Page page, PayCheck payCheck) {
		QueryWrapper<PayCheck> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(payCheck.getPayPhone())) {
			queryWrapper.eq("pay_phone",payCheck.getPayPhone());
		}
		if (!StringUtils.isEmpty(payCheck.getSellPhone())) {
			queryWrapper.eq("sell_phone",payCheck.getSellPhone());
		}
		if (!StringUtils.isEmpty(payCheck.getPayUsername())) {
			queryWrapper.like("pay_username",payCheck.getPayUsername().trim());
		}
		if (!StringUtils.isEmpty(payCheck.getSellUsername())){
			queryWrapper.like("sell_username",payCheck.getSellUsername().trim());
		}
		queryWrapper.orderByDesc("create_date");
		return baseMapper.selectPage(page, queryWrapper);
	}
}
