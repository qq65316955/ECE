package com.yhwl.yhwl.esc.api.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("id_card")
public class IdCard extends DataEntity<IdCard> {

	@TableField("user_name")
	private String username;

	@TableField("user_id")
	private String user_id;
	@ApiModelProperty("身份证正面地址")
	@TableField("front_url")
	private String frontUrl;

	@ApiModelProperty("身份证反面地址")
	@TableField("back_url")
	private String backUrl;

	@TableField("people_url")
	private String peopleUrl;

	@TableField("id_number")
	private String idNumber;

	@TableField("real_name")
	private String realName;

	//0审核中，1审核通过，2审核未通过
	@TableField("status")
	private Integer status=0;

}
