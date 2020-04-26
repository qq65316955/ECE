package com.yhwl.yhwl.esc.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yhwl.yhwl.common.tools.crypto.EncryptUtil;
import com.yhwl.yhwl.common.tools.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @ClassName AccountInfo
 * @Description: TODO
 * @Author alang
 * @Date 2-18 第4周
 * @Version V1.0
 **/
@Data
@TableName("account")
@ApiModel
public class AccountInfo  extends DataEntity<AccountInfo> {

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String username="";

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickname="";

    /**
     * 邀请码
     */
    @TableField(value = "invitation_code")
    private String invitationCode="";

    @TableField(value = "transaction_password")
    private String transactionPassword="";

    @TableField(value = "is_Super")
    private boolean isSuper = false;

    // 0.禁止提现
    @TableField("is_cash")
    private boolean iscash=true;

    // 1.禁止给他人矿机质押
    @TableField("pledged")
    @JsonIgnore
    private boolean pledged = false;

    //由于用户表内已经有手机号绑定了，这里是安全手机号
    @TableField("phone")
    private String phone;

    @TableField("ali_url")
	private String aliUrl;

    @TableField("wechat_url")
	private String wechatUrl;

    @TableField("country")
	private String country;

    @TableField("is_auth")
	private  boolean isAuth;

    @TableField(exist = false)
    private String passwordCode;

    @TableField("is_freeze")
	private boolean isFreeze=false;

    @TableField("is_transaction")
	private Integer istransaction;

    public void setTransactionPassword(String transactionPassword){
        this.transactionPassword = EncryptUtil.encode(transactionPassword);
    }

    public String getTransactionPassword(){
        return EncryptUtil.decode(transactionPassword);
    }


}
