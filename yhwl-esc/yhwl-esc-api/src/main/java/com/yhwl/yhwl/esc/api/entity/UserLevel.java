package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import lombok.Data;

/**
 * leftId为自己，则是查下级
 * rightId为自己，则是查上级
 */
@Data
@TableName("user_level")
public class UserLevel extends DataEntity<UserLevel> {

    @TableField(value = "left_id")
    private String leftId;

    @TableField(value = "right_id")
    private String rightId;

    @TableField(value = "left_name")
    private String leftName;

    @TableField(value = "right_name")
    private String rightName;

    @TableField(value = "level_num")
    private int levelNum;

    @TableField(value = "pool")
    private boolean pool = false;

	@TableField(exist = false)
	private int downNum;

}