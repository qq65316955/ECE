package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.PledgeLog;
import com.yhwl.yhwl.esc.api.mapper.PledgeLogMapper;
import com.yhwl.yhwl.esc.api.service.PledgeLogService;
import org.springframework.stereotype.Service;

/**
 * @ClassName PledgeLogServiceImpl
 * @Description
 * @Author clTODO
 * @Data 2019/8/6 17:29
 * @Version V1.0
 **/
@Service
public class PledgeLogServiceImpl extends ServiceImpl<PledgeLogMapper, PledgeLog> implements PledgeLogService {
    @Override
    public IPage<PledgeLog> findByPage(Page page, PledgeLog pledgeLog) {
        QueryWrapper<PledgeLog> wrapper = new QueryWrapper<>();
        wrapper.like("user_name",pledgeLog.getUsername().trim());
        wrapper.orderByDesc("create_date");
        return  baseMapper.selectPage(page,wrapper);
    }
}
