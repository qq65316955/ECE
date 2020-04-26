package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.UntyingMiner;
import com.yhwl.yhwl.esc.api.mapper.UntyingMinerMapper;
import com.yhwl.yhwl.esc.api.service.UntyingMinerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 
 */
@Service
public class UntyingMinerServiceImpl extends ServiceImpl<UntyingMinerMapper, UntyingMiner> implements UntyingMinerService {
    @Override
    public IPage<UntyingMiner> findByPage(Page page, UntyingMiner untyingMiner, Timestamp starttime,Timestamp endtime) {
        QueryWrapper<UntyingMiner> wrapper = new QueryWrapper<>();
//        if(untyingMiner.getUpdateDate()!=null){
//        	wrapper.ge("create_date",untyingMiner.getCreateDate());
//		}

        if(StringUtils.isNotBlank(untyingMiner.getUsername())) {
            wrapper.like("user_name",untyingMiner.getUsername().trim());
        }
        if(StringUtils.isNotBlank(untyingMiner.getMac())) {
            wrapper.eq("mac",untyingMiner.getMac());
        }
        wrapper.orderByDesc("create_date");
        return baseMapper.selectPage(page,wrapper);
    }
}
