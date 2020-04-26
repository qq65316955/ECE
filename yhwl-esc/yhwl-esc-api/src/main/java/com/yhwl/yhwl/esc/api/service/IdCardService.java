package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.IdCard;

public interface IdCardService extends IService<IdCard> {
	IPage<IdCard> findByPage(Page page,String realName,String idNumber,Integer status);

	IdCard findByUsername(String username);

	IdCard findByNumber(String idnumber);
}
