package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公告
 */
@Data
@TableName("infomation")
@ApiModel
public class Information extends DataEntity<Information> {

    @ApiModelProperty("标题")
    @TableField("title")
    private String title="";

    @ApiModelProperty("内容")
    @TableField("content")
    private String content="";

    @ApiModelProperty("标题")
    @TableField("title_en")
    private String titleEn;

    @ApiModelProperty("内容")
    @TableField("content_en")
    private String contentEn;

    @ApiModelProperty("标题")
    @TableField("title_kr")
    private String titleKr;

    @ApiModelProperty("内容")
    @TableField("content_kr")
    private String contentKr;

    @ApiModelProperty("标题")
    @TableField("title_jp")
    private String titleJp;

    @ApiModelProperty("内容")
    @TableField("content_jp")
    private String contentJp;

    @ApiModelProperty("繁中标题")
    @TableField("title_zh_tw")
    private String titleZhTw;

    @ApiModelProperty("繁中内容")
    @TableField("content_zh_tw")
    private String contentZhTw;

    @ApiModelProperty("马来标题")
    @TableField("title_my")
    private String titleMy;

    @ApiModelProperty("马来内容")
    @TableField("content_my")
    private String contentMy;

    @ApiModelProperty("泰国标题")
    @TableField("title_th")
    private String titleTh;

    @ApiModelProperty("泰国内容")
    @TableField("content_th")
    private String contentTh;

    @ApiModelProperty("越南标题")
    @TableField("title_vn")
    private String titleVn;

    @ApiModelProperty("越南内容")
    @TableField("content_vn")
    private String contentVn;

    @ApiModelProperty("柬埔寨标题")
    @TableField("title_kh")
    private String titleKh;

    @ApiModelProperty("柬埔寨内容")
    @TableField("content_kh")
    private String contentKh;

}
