package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import lombok.Data;

/**
 * @ClassName UntyingMiner
 * @Description 解绑矿机记录
 * @Author cl
 * @Data 2019/8/8 10:19
 * @Version V1.0
 **/
@Data
@TableName("untying_miner_log")
public class UntyingMiner extends DataEntity<UntyingMiner> {

    /** 用户名称.*/
    @TableField("user_name")
    private String username;

    /** 矿机地址.*/
    @TableField("mac")
    private String mac;

    /** 矿机总产出.*/
    @TableField("total_bmj")
    private Double totalBMJ;

}
