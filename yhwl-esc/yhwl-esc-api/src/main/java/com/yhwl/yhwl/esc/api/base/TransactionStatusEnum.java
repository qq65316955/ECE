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
public enum TransactionStatusEnum implements IEnum<String> {

    @ApiModelProperty("等待审核")
    WAIT(90,"等待审核"),

    @ApiModelProperty("审核拒绝")
    WAITFILE(91,"审核拒绝"),

    @ApiModelProperty("成功")
    SUCCESS(4,"成功"),

    @ApiModelProperty("失败")
    FAILE(5,"失败"),
    @ApiModelProperty("打包中")
    PACKAGEING(6,"打包中"),

    @ApiModelProperty("拨款中")
    PENDING(7,"拨款中"),

	@ApiModelProperty("已到期")
	OUT(7,"已到期")
    ;

    TransactionStatusEnum(int code, String descp) {
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
