package com.yhwl.yhwl.esc.api.inteface;

import com.yhwl.yhwl.common.core.constant.SecurityConstants;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * ESC APP 无需授权接口
 */

public interface EscAppUnauthControllerInterface {

    @ApiOperation(value = "注册", notes = "注册")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "string"),
                    @ApiImplicitParam(name = "password", value = "密码", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "transactionPassword", value = "交易密码", paramType = "query", required = true, dataType = "string"),
                    @ApiImplicitParam(name = "fromuser", value = "推荐人", paramType = "query", required = true, dataType = "string"),
                    @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", required = true, dataType = "string"),
                    @ApiImplicitParam(name = "code", value = "验证码", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "country", value = "国家", paramType = "query", required = true, dataType = "string"),
                    @ApiImplicitParam(name = "from", value = "标记", paramType = "query", required = true, dataType = "string"),
            }
    )
    @PostMapping("/register")
    @ResponseBody
    R register(@RequestParam(value = "username") String username,
			   @RequestParam(value = "password") String password,
			   @RequestParam(value = "transactionPassword") String transactionPassword,
			   @RequestParam(value = "fromuser") String fromuser,
			   @RequestParam(value = "phone") String phone,
			   @RequestParam(value = "code") String code,
			   @RequestParam(value = "country") String country,
			   @RequestHeader(SecurityConstants.FROM) String from) throws HZException;

    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "phone", value = "手机号(仅在注册时必传),请加上国家代码，比如中国86-13111111111", example = "phone", paramType = "query", required = false, dataType = "string"),
                    @ApiImplicitParam(name = "type", value = "类型<br/>忘记密码=FORGETPASSWORD，<br/>注册=REGISTER,转账=SEND<br/>TRANSACTIONPASSWORD=交易密码<br/>MODIFYPHONE=绑定手机号<br/>FORGETPASSWORD=忘记密码<br/>CHENAGEPASSWORD=修改密码", example = "type", paramType = "query", required = true, dataType = "string"),
                    @ApiImplicitParam(name = "from", value = "标记", paramType = "query", required = true, dataType = "string"),
            }
    )
    @ResponseBody
    @PostMapping("/sendSMSCode")
	R sendSMSCode(@RequestParam(value = "phone", required = false) String phone,
				  @RequestParam(value = "type", required = false) String type,
				  @RequestHeader(SecurityConstants.FROM) String from);

    @ApiOperation(value = "验证助记词", notes = "验证助记词<br/>code[101=钱包名存在,102=助记词不正确]")
    @GetMapping("/verificationMnemonic")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "word", value = "助记词，排好顺序后以逗号隔开", paramType = "query", required = true, dataType = "string"),
            @ApiImplicitParam(name = "from", value = "标记", paramType = "query", required = true, dataType = "string"),
    })
    @ResponseBody
    R verificationMnemonic(@RequestParam(value = "word") String word, @RequestHeader(SecurityConstants.FROM) String from);


    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "username", value = "用户名", example = "username", paramType = "query", required = true, dataType = "string"),
                    @ApiImplicitParam(name = "newpassword", value = "新密码", example = "newpassword", paramType = "query", required = true, dataType = "string"),
                    @ApiImplicitParam(name = "code", value = "验证码", example = "code", paramType = "query", required = true, dataType = "string"),
                    @ApiImplicitParam(name = "from", value = "标记", paramType = "query", required = true, dataType = "string"),
            }
    )
    @ResponseBody
    @PostMapping("/forgetPassword")
    R forgetPassword(@RequestParam(value = "username") String username,
					 @RequestParam(value = "code") String code,
					 @RequestParam(value = "newpassword") String newpassword,
					 @RequestHeader(SecurityConstants.FROM) String from);

    @ResponseBody
    @PostMapping("/getTran")
    R getTran(@RequestBody byte[] data);

}
