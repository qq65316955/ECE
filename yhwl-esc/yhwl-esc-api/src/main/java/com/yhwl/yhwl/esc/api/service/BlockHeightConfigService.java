package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.BlockHeightConfig;


public interface BlockHeightConfigService extends IService<BlockHeightConfig> {

    void updateFirBlock(double fire);

}
