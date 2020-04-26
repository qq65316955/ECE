package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.BaseEntity;
import lombok.Data;

@Data
@TableName(value = "block_height_config")
public class BlockHeightConfig extends BaseEntity<BlockHeightConfig> {

    @TableField("bmj_percent")
    private Double bmjPercent;

    @TableField("bmj_coin_percent")
    private Double bmjCoinPercent;

    @TableField("coin_per_day_by_bmj_coin")
    private Double coinPerDayByBmjCoin;

    @TableField("coin_per_block_by_bmj_coin")
    private Double coinPerBlockByBmjCoin;

    @TableField("coin_per_day_by_bmj")
    private Double coinPerDayByBmj;

    @TableField("coin_per_block_by_bmj")
    private Double coinPerBlockByBmj;

    @TableField("coin_per_day_by_bmj_fire")
    private Double coinPerDayByBmjFire;

    @TableField("coin_per_block_by_bmj_fire")
    private Double coinPerBlockByBmjFire;
}
