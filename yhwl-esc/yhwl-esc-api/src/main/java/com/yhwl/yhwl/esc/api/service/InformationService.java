package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.Information;


public interface InformationService extends IService<Information> {

    IPage<Information> listByPage(Page<Information> informationPage);

	IPage<Information> findByPage(Page page);


}
