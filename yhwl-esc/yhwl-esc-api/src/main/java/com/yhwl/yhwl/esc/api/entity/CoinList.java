package com.yhwl.yhwl.esc.api.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
@TableName("coin_list")
public class CoinList extends DataEntity<CoinList> {

    @ApiModelProperty("币名")
    @TableField(value = "name")
    public String name="";

    @ApiModelProperty("价格")
    @TableField(value = "price")
    public Double price=0.0d;

    @ApiModelProperty("主链")
    @TableField(value = "line")
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    public CoinEnum line;

    @ApiModelProperty("图标地址")
    @TableField(value = "ico_url")
    private String icoUrl="";

    @ApiModelProperty("是否创建到用户列表")
    @TableField(value = "create_new")
    private boolean createNew=false;

    @ApiModelProperty("是否开启价格同步")
    @TableField(value = "synchronization_price")
    public boolean synchronizationPrice = false;

}