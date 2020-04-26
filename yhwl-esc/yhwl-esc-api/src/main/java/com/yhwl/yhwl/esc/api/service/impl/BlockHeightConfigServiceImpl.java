package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.BlockHeightConfig;
import com.yhwl.yhwl.esc.api.mapper.BlockHeightConfigMapper;
import com.yhwl.yhwl.esc.api.service.BlockHeightConfigService;
import org.springframework.stereotype.Service;

@Service
public class BlockHeightConfigServiceImpl extends ServiceImpl<BlockHeightConfigMapper, BlockHeightConfig> implements BlockHeightConfigService {
    @Override
    public void updateFirBlock(double fire) {

    }
}
