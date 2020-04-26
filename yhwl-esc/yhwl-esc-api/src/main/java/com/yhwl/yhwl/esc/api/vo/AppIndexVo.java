package com.yhwl.yhwl.esc.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.NumberFormat;

@Data
public class AppIndexVo {

    @ApiModelProperty("个人算力")
    private long personalCalculation;

    @ApiModelProperty("爆块算力")
    private long explosiveCalculation;

    @ApiModelProperty("矿池算力")
    private long minerPoolCalculation;

    @ApiModelProperty("矿池有效算力")
    private long minerPoolCalculationUse;

    @ApiModelProperty("矿工团队")
    private int minerPoolNum;

    @ApiModelProperty("个人算力Show")
    private String personalCalculationShow;

    @ApiModelProperty("爆块算力Show")
    private String explosiveCalculationShow;

    @ApiModelProperty("矿池算力Show")
    private String minerPoolCalculationShow;

    @ApiModelProperty("矿池有效算力Show")
    private String minerPoolCalculationUseShow;

    @ApiModelProperty("是否实名认证")
    private boolean isAuth;

    public void parseString(){
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMaximumFractionDigits(3);
        this.personalCalculationShow=instance.format(this.personalCalculation/1073741824.0)+" T";
        this.explosiveCalculationShow = instance.format(this.explosiveCalculation/1073741824.0)+" T";
        this.minerPoolCalculationShow = instance.format((this.minerPoolCalculation+this.minerPoolCalculationUse)/1073741824.0)+" T";
        this.minerPoolCalculationUseShow = instance.format(this.minerPoolCalculationUse)+" T";
    }


}