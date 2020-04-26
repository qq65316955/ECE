package com.yhwl.yhwl.esc.tools;


import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.common.tools.zhutong.SMSTools;

public class ZhuTongSmsTools {

    public final static String username ="BMJ";//内容
    public final static String password ="O7yTBfrU";//密码
    public final static String name ="BMJ";//内容
    public final static String int_username ="GJshymgjhy";//内容
    public final static String int_password ="R5l8nq";//密码
    public final static String int_xh ="";//没有
    public final static String productid_code ="676767";//验证码

    private static void send(String mobile, String content) throws HZException {
        if(mobile.indexOf("-") != -1){
            String[] phone = mobile.split("-");
            if(phone[0].equals("86")){
                SMSTools.sendSMS(username,password,content,productid_code,mobile);
            }else{
                SMSTools.sendSMS(int_username,int_password,content,productid_code,mobile);
            }
        }else {
            SMSTools.sendSMS(username,password,content,productid_code,mobile);
        }
    }

    public static void getResigerCode(String phone,String code) throws HZException {
        send(phone,String.format("【ECE】您正在使用%s,验证码[%s]","注册短信",code));
    }
    public static void getTransferCode(String phone,String code) throws HZException {
        send(phone,String.format("【ECE】您正在使用%s,验证码[%s]","转账交易",code));
    }
    public static void setTransferPassword(String phone,String code) throws HZException {
        send(phone,String.format("【ECE】您正在使用%s,验证码[%s]","设置交易密码",code));
    }
    public static void forgetPassowrd(String phone,String code) throws HZException {
        send(phone,String.format("【ECE】您正在使用%s,验证码[%s]","找回密码",code));
    }
    public static void changePassword(String phone,String code) throws HZException {
        send(phone,String.format("【ECE】您正在使用%s,验证码[%s]","修改密码",code));
    }
    public static void bindPhone(String phone,String code) throws HZException {
        send(phone,String.format("【ECE】您正在使用%s,验证码[%s]","绑定手机",code));
    }
	public static void sellBall(String phone,String payName) throws HZException {
		send(phone,String.format("【ECE】用户%s已为能量合约支付，请查收，并在5小时内确认收款，否则您的【ESE】账户将被冻结",payName));
	}
}
