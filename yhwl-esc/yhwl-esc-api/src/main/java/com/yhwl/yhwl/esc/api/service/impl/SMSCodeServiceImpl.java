package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.SMSCode;
import com.yhwl.yhwl.esc.api.mapper.SMSCodeMapper;
import com.yhwl.yhwl.esc.api.service.SMSCodeService;
import org.springframework.stereotype.Service;

/**
 * 作者: yangkunhao
 * 时间: 2020/2/24 2:44 下午
 */
@Service
public class SMSCodeServiceImpl extends ServiceImpl<SMSCodeMapper, SMSCode> implements SMSCodeService {
    @Override
    public SMSCode findByPhoneOnType(String phone,String type) {
        QueryWrapper<SMSCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        queryWrapper.eq("type",type);
        return baseMapper.selectOne(queryWrapper);
    }
}
