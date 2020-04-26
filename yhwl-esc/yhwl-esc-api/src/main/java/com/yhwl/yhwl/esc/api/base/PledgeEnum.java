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
public enum PledgeEnum implements IEnum<String> {

    @ApiModelProperty("COIN")
    COIN(3,"COIN"),

    @ApiModelProperty("BMJ")
    BMJ(5,"BMJ"),

    @ApiModelProperty("BURN")
    BURN(2,"BURN");

    PledgeEnum(int code, String descp) {
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

    public static PledgeEnum returnEnum(String name){
        if(name.equals("COIN")){
            return COIN;
        }else if(name.equals("BMJ")){
            return BMJ;
        }else if(name.equals("BURN")){
            return BURN;
        }
        return null;
    }

}