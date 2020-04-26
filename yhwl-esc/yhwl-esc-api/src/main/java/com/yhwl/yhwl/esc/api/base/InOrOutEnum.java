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
public enum InOrOutEnum implements IEnum<String> {

    @ApiModelProperty("挖矿记录")
    MINER(1,"挖矿"),
    @ApiModelProperty("领取记录")
    EXTR(1,"领取"),
    @ApiModelProperty("解押记录")
    RELIEVE(1,"解押"),
    @ApiModelProperty("取出记录")
    EXTRPPP(1,"取出"),
    @ApiModelProperty("理财记录")
    CONDUCT(1,"理财"),
    @ApiModelProperty("团队收益")
    TEAM(2,"团队收益"),
    PAY(3,"支付"),
    PUSH(4,"推荐"),
    @ApiModelProperty("领导奖")
    LEADER(5,"领导奖");



    InOrOutEnum(int code, String descp) {
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