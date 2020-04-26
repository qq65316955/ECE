package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data
@TableName("message")
public class Message extends DataEntity<Message> {

	@TableField("user_name")
	private String username;

	@TableField("to_message")
	private String toMessage;

	@TableField("get_message")
	private String getMessage;
}
