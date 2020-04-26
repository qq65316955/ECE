package com.yhwl.yhwl.esc.api.base;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum LanguageResultCodeEnum implements IEnum<Integer> {

	USERNAME_ERROR(99,"注册地址错误"),
    PARAM_ERROR(101,"参数格式错误"),
    WALLET_NOT_FOUND(102,"钱包地址不存在"),
    NICKNAME_EXISTS(103,"昵称已存在"),
    REFERRER_NOT_EXISTS(104,"推荐码不存在"),
    PASSWORD_ERROR(105,"密码不正确"),
    FORBIDDEN_LOGIN(106,"该帐号被禁止登陆"),
    MNEMONIC_INCORRECT(107,"助记词不正确"),
    VERIFICATION_INCORRECT(108,"验证码不正确"),
    USER_NOT_EXISTS(109, "用户不存在"),
    VERIFICATION_SEND_FAIL(110,"发送验证码失败"),
    MNEMONIC_CREATE_FAIL(112,"创建助记词失败"),
    MINER_NOT_FOUND(113,"矿机不存在，或未激活"),
    MINER_ALREADY_BIND(114,"该矿机已被绑定"),
    UNPRIVILEGED_OPERATION(115,"无权限操作"),
    UNBIND_FAIL(116,"解绑失败"),
    QRCODE_ERROR(117,"生成二维码失败"),
    OTHER_TRANSCATION(118,"不是自己的交易"),
    COIN_NOT_CORRECT(119,"币种信息不吻合"),
    SYNC_FAIL(120,"同步失败"),
    TRAN_PASSWORD_INCORRECT(121,"交易密码不正确"),
    BALANCE_NOT_ENOUGH(122,"余额不足"),
    SEND_ADDRESS_ERROR(123,"转账地址错误"),
    SEND_ERROR(124,"转账失败"),
    NOT_OPEN(125,"暂未开放"),
    PLEDGE_FAIL(126,"质押失败"),
    SEND_IN_ERROR(127,"转入失败"),
    ILLEGAL_OPERATION(128,"非法操作"),
    EXTRACT_FAIL(129,"提取失败"),
    OPERATION_FAIL(130,"操作失败"),
    INSUFFICIENT_SPACE(131,"可操作空间不足"),
    MINER_PROCESSING(132,"该矿机正在处理中"),
    PHONE_NOT_CURRENT_BIND(133,"当前手机号不是用户绑定的手机号"),
    BIND_ERROR(134,"绑定用户出错"),
    PHONE_FORMAT_ERROR(135,"手机号格式有误"),
    PHONE_NOT_BIND(136,"手机号未绑定"),
    PHONE_ALREADY_BIND(137,"手机号已被绑定"),
    SEND_INNER_ERROR(138,"该币种不允许内部APP内部转账"),
    USER_EXISTS(139, "用户已存在"),
    SMSCODE_FOUND(140,"验证码已发送，请稍后再试"),
    COIN_NOT_SUPPORT(145,"暂不支持该币种"),
	RENTAL_IS_WAIT(146,"租赁矿机正在审核中"),
	ACCOUNT_FREEZE(147,"账号存在恶意行为，已被冻结"),
	BALL_NOT_PAY(148,"有能量合约未付款"),
	ID_NOT_AUTH(149,"真实身份未认证"),
	SELL_NOT_AUTH(150,"卖家未身份未认证"),
	ID_NUMBER_EXSIT(151,"身份证号已认证"),
	MACHINE_NOT_SELL(152,"矿机不可售"),
	MONEY_NUM_ERROR(153,"金额数量错误"),
	NOT_ALLOW_DOWN(154,"不允许降级购买"),
    /****************************************************************/
    SUCCESSFUL(200,"操作成功"),
    REGISTER_FAILED(201, "注册失败"),
    REGISTER_SUCCESSFUL(202, "注册成功"),
    PASSWORD_STRENGTH_INSUFFICIENT(203, "密码强度不够"),
    MINER_PASSWD_ERROR(204, "矿机密码错误"),
    TRAN_PASSWORD_NOTSET(205,"交易密码未设置"),
    RENT_MACHINE_NOT_OPERATION(206, "禁止操作租赁机器"),
    /****************************************************************/
    SERVER_ERROR(500,"服务器错误"),
    /****************************************************************/
    MINER_ALREADY_NOT_BIND(1141,"该矿机未被绑定"),
    NOT_UNZIP(1150,"请先解押"),
    NOT_EXTRACT(1151,"请提取质押币余额"),
    RENT_MAX(1152,"已达用户租赁上限"),
    RENT_MAX_TODAY(1153,"已达今日租赁上限"),
    UNZIP_FAILED(1154,"解押失败"),
    BALANCE_ERROR(1231, "最低转账额度"),
    /****************************************************************/
    OPERATION_FORBIDDEN(8121, "禁止操作"),
    GETPRICE_ERROR(8122, "获取实时价格异常"),
	INVALID_COMMODITY(1300,"无效商品"),
	BUY_FAIL(1301,"购买失败"),
	BUY_FAIL_SIZE(1302,"数量不能低于起购数"),
	IDCARD_UPLOAD(1303,"身份证已上传"),
	IDCARD_ERROR(1304,"身份证号错误"),
	GRAP_TIME_OUT(1305,"未到抢购时间"),
	BALL_FULL(1306,"一个时间段最多只能抢3次球")
    ;

    private Integer code;
    private String message;

    LanguageResultCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    public String getMessage(){
        return message;
    }
}
