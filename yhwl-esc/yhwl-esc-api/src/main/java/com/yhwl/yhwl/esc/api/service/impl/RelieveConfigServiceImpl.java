package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.RelieveConfig;
import com.yhwl.yhwl.esc.api.mapper.RelieveConfigMapper;
import com.yhwl.yhwl.esc.api.service.RelieveConfigService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyi
 */
@Service
public class RelieveConfigServiceImpl extends ServiceImpl<RelieveConfigMapper, RelieveConfig> implements RelieveConfigService {
    @Override
    public RelieveConfig findMostPriority(Timestamp createDate) {
        QueryWrapper<RelieveConfig> wrapper = new QueryWrapper<>();
        wrapper.lt("date_start",createDate);
        wrapper.gt("date_end",createDate);
        wrapper.eq("status",1);
        wrapper.orderByDesc("priority");
        List<RelieveConfig> list = baseMapper.selectList(wrapper);
        if(list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public RelieveConfig getDefault() {
        QueryWrapper<RelieveConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("name","default");
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public IPage<RelieveConfig> findByPage(IPage<RelieveConfig> page, Map<String, String> paramMap) {
        QueryWrapper<RelieveConfig> wrapper = new QueryWrapper<>();
        return baseMapper.selectPage(page,wrapper);
    }
}
