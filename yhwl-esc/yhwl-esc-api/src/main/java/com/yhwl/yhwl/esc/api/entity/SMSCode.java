package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 手机验证码
 */
@ApiModel
@Data
@TableName("sms_code")
public class SMSCode extends DataEntity<SMSCode> {

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 验证码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 验证错误次数
     */
    @TableField(value = "faile_count")
    private int faileCount=0;


    
}
