package com.yhwl.yhwl.esc.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhwl.yhwl.esc.api.entity.BlockHeightConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BlockHeightConfigMapper extends BaseMapper<BlockHeightConfig> {

    @Update("update block_height_config  set coin_per_block_by_bmj_fire =  (SELECT SUM(bmj_pledge_currency_use/200)* #{fire}/ 288 as a from mining_machine WHERE del_flag=0)")
    int updateFireProfit(double fire);

}
