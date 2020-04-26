package com.yhwl.yhwl.esc.api.base;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@ApiModel
@Getter
@ToString
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FromUseEnum implements IEnum<String> {

    @ApiModelProperty("矿机")
    MINER(1,"矿机"),

    @ApiModelProperty("用户")
    USER(2,"用户");

    FromUseEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    private int code;
    @EnumValue
    private String descp;

    @Override
    public String getValue() {
        return this.descp;
    }
}