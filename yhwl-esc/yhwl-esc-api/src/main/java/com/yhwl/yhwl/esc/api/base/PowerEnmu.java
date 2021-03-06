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
public enum  PowerEnmu implements IEnum<String> {
	@ApiModelProperty("0.012kb/s")
	A(3,"0.012kb/s"),

	@ApiModelProperty("0.13kb/s")
	B(5,"0.13kb/s"),

	@ApiModelProperty("1.4kb/s")
	C(2,"1.4kb/s"),

	@ApiModelProperty("16kb/s")
	E(2,"16kb/s");

	PowerEnmu(int code, String descp) {
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
