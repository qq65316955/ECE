package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.PayCheck;

/**
 * @author Administrator
 */
public interface PayCheckService extends IService<PayCheck> {

	IPage<PayCheck> findByPage(Page page, PayCheck payCheck);

	//支付凭证
	IPage PaymentVoucher(Page page, PayCheck payCheck);

}
