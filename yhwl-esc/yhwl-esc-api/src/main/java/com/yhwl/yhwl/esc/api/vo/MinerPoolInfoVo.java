package com.yhwl.yhwl.esc.api.vo;


import com.yhwl.yhwl.esc.api.entity.AccountProfit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 矿池收益来源简价
 */
@ApiModel
@Data
public class MinerPoolInfoVo {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("贡献产值")
    private double contributionIncome;

    @ApiModelProperty("Bmj总资产")
    private double totalBmj;

    @ApiModelProperty("Bmjcoin总资产")
    private double totalBmjcoin;

    @ApiModelProperty("totalFICD总资产")
    private double totalFICD;

    public MinerPoolInfoVo(AccountProfit byUserName) {
        this.username = byUserName.getUsername();
        this.contributionIncome = byUserName.getTotalProfit();
    }
}