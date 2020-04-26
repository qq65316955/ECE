package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import lombok.Data;

@Data
@TableName("broad_cast")
public class BroadCast extends DataEntity<BroadCast> {
    @TableField(exist = false)
    public static final String GET_FICD = "恭喜{username}获得{num}个FICD";

    @TableField(value = "content")
    private String content;

    @TableField(value = "username")
    private String username;

    /**
     * 1:邀请，2:奖币，0:普通通知
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 1:置顶
     */
    @TableField(value = "top")
    private Integer top;

    @TableField(value = "num")
    private Integer num;


    public String getDisplay() {
        if(this.type == 1 || this.type == 2) {
            String ret = this.content;
            ret = ret.replace("{username}",this.username).replace("{num}",num+"");
            return ret;
        } else {
            return this.content;
        }
    }
    public String getKo() {
        if(this.type == 1 || this.type == 2) {
            String ret = this.content;
            ret = ret.replace("{username}",this.username).replace("{num}",num+"");
            ret = ret.replace("恭喜","축하합니다 ").replace("获得"," 입수 ").replace("个","");
            return ret;
        } else {
            return this.content;
        }
    }
    public String getJa() {
        if(this.type == 1 || this.type == 2) {
            String ret = this.content;
            ret = ret.replace("{username}",this.username).replace("{num}",num+"");
            ret = ret.replace("恭喜","おめでとうございます ").replace("获得","入手する").replace("个","");
            return ret;
        } else {
            return this.content;
        }
    }
    public String getKm() {
        if(this.type == 1 || this.type == 2) {
            String ret = this.content;
            ret = ret.replace("{username}",this.username).replace("{num}",num+"");
            ret = ret.replace("恭喜","សូមអបអរសាទរ។").replace("获得","ទទួលបាន។").replace("个","");
            return ret;
        } else {
            return this.content;
        }
    }
    public String getMs() {
        if(this.type == 1 || this.type == 2) {
            String ret = this.content;
            ret = ret.replace("{username}",this.username).replace("{num}",num+"");
            ret = ret.replace("恭喜","Tahniah ").replace("获得"," dapatkan ").replace("个","");
            return ret;
        } else {
            return this.content;
        }
    }
    public String getEn() {
        if(this.type == 1 || this.type == 2) {
            String ret = this.content;
            ret = ret.replace("{username}",this.username).replace("{num}",num+"");
            ret = ret.replace("恭喜","Congratulations ").replace("获得"," receive ").replace("个","");
            return ret;
        } else {
            return this.content;
        }
    }
    public String getVi() {
        if(this.type == 1 || this.type == 2) {
            String ret = this.content;
            ret = ret.replace("{username}",this.username).replace("{num}",num+"");
            ret = ret.replace("恭喜","Xin chúc mừng ").replace("获得"," có được").replace("个","");
            return ret;
        } else {
            return this.content;
        }
    }
    public String getTh() {
        if(this.type == 1 || this.type == 2) {
            String ret = this.content;
            ret = ret.replace("{username}",this.username).replace("{num}",num+"");
            ret = ret.replace("恭喜","การแสดงความยินดี").replace("获得","ได้รับ").replace("个","");
            return ret;
        } else {
            return this.content;
        }
    }
    public String getTw() {
        if(this.type == 1 || this.type == 2) {
            String ret = this.content;
            ret = ret.replace("{username}",this.username).replace("{num}",num+"");
            ret = ret.replace("恭喜","恭喜").replace("获得","獲得").replace("个","個");
            return ret;
        } else {
            return this.content;
        }
    }

    public BroadCast() {
    }

    public BroadCast(String content, String username, int type, int top, int num) {
        this.content = content;
        this.username = username;
        this.type = type;
        this.top = top;
        this.num = num;
    }
}
