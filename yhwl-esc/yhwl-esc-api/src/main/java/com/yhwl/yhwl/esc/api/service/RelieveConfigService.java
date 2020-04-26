package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.RelieveConfig;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author zhangyi
 */
public interface RelieveConfigService extends IService<RelieveConfig> {
    RelieveConfig findMostPriority(Timestamp createDate);

    RelieveConfig getDefault();

    IPage<RelieveConfig> findByPage(IPage<RelieveConfig> page, Map<String, String> paramMap);
}
