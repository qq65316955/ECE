package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import lombok.Data;

import java.util.Date;


@Data
@TableName("relieve_config")
public class RelieveConfig extends DataEntity<RelieveConfig> {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("date_start")
    private Date dateStart;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("date_end")
    private Date dateEnd;

    @TableField("rate")
    private double rate;

    @TableField("status")
    private int status;

    @TableField("name")
    private String name;

    @TableField("priority")
    private int priority;
}
