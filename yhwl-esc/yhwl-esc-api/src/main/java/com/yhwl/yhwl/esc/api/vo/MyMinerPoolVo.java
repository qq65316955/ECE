package com.yhwl.yhwl.esc.api.vo;


import com.yhwl.yhwl.esc.api.entity.AccountProfit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
/**
 * 我的矿池
 */
public class MyMinerPoolVo {

    @ApiModelProperty("BMJ总收益")
    private double totalBmj;

    @ApiModelProperty("总人数")
    private int totalNumber;

    @ApiModelProperty("总推荐人数")
    private int totalRecommendNum;

    @ApiModelProperty("激活人数")
    private int activeNumber;

    @ApiModelProperty("推荐人数")
    private int oneNumber;

    @ApiModelProperty("收益来源")
    private List<MinerPoolInfoVo> sourceOfRevenue;

    public  MyMinerPoolVo(AccountProfit accountProfit){
        this.totalBmj = accountProfit.getMeTotalProfit();
        this.totalNumber = accountProfit.getMinerNumber();
        this.activeNumber = accountProfit.getActiveNumber();
        this.totalRecommendNum = accountProfit.getReferencesNumber();
    }

}