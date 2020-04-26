package com.yhwl.yhwl.esc.api.base;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@ApiModel
@Getter
@ToString
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TeamLevelEnum implements IEnum<Integer> {

    @ApiModelProperty("无特权")
    NO(0,"无特权","无特权",0,0,0,0),
    @ApiModelProperty("普通矿工")
    NORMAL(1,"普通矿工","10%/天矿工收益",30,0.1,0,0),
    @ApiModelProperty("城市节点")
    CITY(2,"城市节点","10%/天矿工收益&3%/天爆块奖励",500,0.1,0.03,0),
    @ApiModelProperty("超级节点")
    SUPER(3,"超级节点","10%/天矿工收益&5%/爆块奖励&1%/天全网收益",2048,0.1,0.03,0.01),
    GOLD(4,"金牌节点","竞选合格可获得全网算力1%月奖励",10240,0.1,0.03,0.01)

            ;

    TeamLevelEnum(int code, String name, String descp, int condition, double directPush, double teamPush, double allPush){
        this.code=code;
        this.name=name;
        this.descp=descp;
        this.directPush=directPush;
        this.teamPush=teamPush;
        this.allPush=allPush;
        this.condition=condition;
    }

    @ApiModelProperty("编号")
    private int code;
    @ApiModelProperty("条件")
    private int condition;
    @ApiModelProperty("名称")

    private String name;
    @ApiModelProperty("描述")
    @JsonIgnore
    private String descp;
    @ApiModelProperty("直推收益")
    @JsonIgnore
    private double directPush;
    @ApiModelProperty("团队收益")
    @JsonIgnore
    private double teamPush;
    @ApiModelProperty("全网收益")
    @JsonIgnore
    private double allPush;

    @Override
    @JsonIgnore
    public Integer getValue() {
        return this.code;
    }
}