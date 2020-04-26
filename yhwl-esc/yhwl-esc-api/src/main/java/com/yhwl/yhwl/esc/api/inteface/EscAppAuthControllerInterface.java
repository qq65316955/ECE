package com.yhwl.yhwl.esc.api.inteface;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.esc.api.entity.CurrencyBalance;
import com.yhwl.yhwl.esc.api.entity.TransactionRecord;
import com.yhwl.yhwl.esc.api.vo.AppIndexVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * ESC 授权接口
 */
public interface EscAppAuthControllerInterface {

	@ApiOperation(value = "设置交易密码", notes = "设置交易密码")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "password", value = "密码", example = "username", paramType = "query", required = true, dataType = "string"),
			}
	)

	@PostMapping("/setTransactionPassword")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_setTransactionPassword')")
	R setTransactionPassword(@RequestParam(value = "password") String password);

	@ApiOperation(value = "修改交易密码", notes = "修改交易密码")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "password", value = "密码", example = "username", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "code", value = "验证码", example = "username", paramType = "query", required = true, dataType = "string"),
			}
	)

	@PostMapping("/changeTransactionPassword")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_changeTransactionPassword')")
	R changeTransactionPassword(@RequestParam(value = "password") String password,
								@RequestParam(value = "code") String code);


	@ApiOperation(value = "主页", notes = "主页")
	@ApiImplicitParams(
			{
			}
	)

	@PostMapping("/appIndex")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_appIndex')")
	R<AppIndexVo> appIndex();

	@ApiOperation(value = "获取币种列表", notes = "获取币种列表")
	@ApiImplicitParams(
			{
			}
	)
	@PostMapping("/getCoinList")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_getCoinList')")
	R<List<CurrencyBalance>> getCoinList();

	@ApiOperation(value = "充值", notes = "充值")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "coin", value = "币名", example = "username", paramType = "query", required = true, dataType = "string"),
			}
	)
	@PostMapping("/recharge")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_recharge')")
	R<Map<String, Object>> recharge(@RequestParam(value = "coin") String coin);

	@ApiOperation(value = "转账", notes = "转账")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "coin", value = "币种", example = "coin", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "address", value = "接收方地址", example = "address", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "amount", value = "金额", example = "0.0", paramType = "query", required = true, dataType = "double"),
					@ApiImplicitParam(name = "password", value = "交易密码", example = "username", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "sourceTag", value = "sourceTag", example = "sourceTag", paramType = "query", dataType = "string"),
					@ApiImplicitParam(name = "smscode", value = "验证码", example = "smscode", paramType = "query", dataType = "string"),
			}
	)
	@PostMapping("/send")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_send')")
	R send(@RequestParam(value = "coin") String coin,
		   @RequestParam(value = "address") String address,
		   @RequestParam(value = "amount") int amount,
		   @RequestParam(value = "password") String password,
		   @RequestParam(value = "smscode") String smscode,
		   @RequestParam(value = "sourceTag", required = false) String sourceTag);

	@ApiOperation(value = "提现", notes = "提现")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "coin", value = "币种", example = "coin", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "address", value = "接收方地址", example = "address", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "amount", value = "金额", example = "0.0", paramType = "query", required = true, dataType = "double"),
					@ApiImplicitParam(name = "password", value = "交易密码", example = "username", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "sourceTag", value = "sourceTag", example = "sourceTag", paramType = "query", dataType = "string"),
					@ApiImplicitParam(name = "smscode", value = "验证码", example = "smscode", paramType = "query", dataType = "string"),
			}
	)
	@PostMapping("/sendOut")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_sendOut')")
	R sendOut(@RequestParam(value = "coin") String coin,
		   @RequestParam(value = "address") String address,
		   @RequestParam(value = "amount") int amount,
		   @RequestParam(value = "password") String password,
		   @RequestParam(value = "smscode") String smscode,
		   @RequestParam(value = "sourceTag", required = false) String sourceTag);


	@ApiOperation(value = "转账记录", notes = "转账记录")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "coin", value = "币名", example = "username", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "page", value = "页数", example = "0", paramType = "query", required = true, dataType = "int"),
					@ApiImplicitParam(name = "type", value = "类型<br/>IN,OUT,ALL", example = "type", paramType = "query", required = true, dataType = "string"),
			}
	)
	@PostMapping("/sendHistory")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_sendHistory')")
	R<IPage<TransactionRecord>> sendHistory(@RequestParam(value = "coin") String coin,
											@RequestParam(value = "page") int page,
											@RequestParam(value = "type") String type);

	@ApiOperation(value = "修改用户密码", notes = "修改用户密码")
	@ResponseBody
	@PostMapping("/changeLoginPasswd")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_changeLoginPasswd')")
	R changeLoginPasswd(String password, String code) throws HZException;


	@ApiOperation(value = "获取矿机商品列表", notes = "获取矿机商品列表")
	@ResponseBody
	@PostMapping("/getMinerShopList")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_getMinerShopList')")
	R getMinerShopList() throws HZException;

	@ApiOperation(value = "公告", notes = "公告")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "page", value = "页数", example = "0", paramType = "query", required = true, dataType = "int"),
			}
	)
	@ResponseBody
	@PostMapping("/information")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_information')")
	R information(int page);

	@ApiOperation(value = "商品详情", notes = "商品详情")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "商品ID", value = "shopId", example = "", paramType = "query", required = true, dataType = "String"),
			}
	)
	@ResponseBody
	@PostMapping("/shopInfo")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_shopInfo')")
	R shopInfo(String shopId);

	@ApiOperation(value = "个人中心", notes = "个人中心")
	@ApiImplicitParams(
			{
			}
	)
	@PostMapping("/personalCenter")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_personalCenter')")
	R<Map<String, Object>> personalCenter();

	@ApiOperation(value = "推广奖励", notes = "推广奖励")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "page", value = "int", example = "page", paramType = "query", required = true, dataType = "int"),
					@ApiImplicitParam(name = "type", value = "推广奖励=PUSH，领导奖=LEADER", example = "page", paramType = "query", required = true, dataType = "String"),

			}
	)
	@PostMapping("/recomeReward")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_recomeReward')")
	R recomeReward(int page,String type);

	@ApiOperation(value = "上传图片", notes = "上传图片")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "file", value = "file", example = "file", paramType = "query", required = true, dataType = "MultipartFile"),
					@ApiImplicitParam(name = "type", value = "类型", example = "WE=微信二维码，ID=身份证，ALI=支付宝二维码,CHECK=支付审核", paramType = "query", required = true, dataType = "MultipartFile"),
			}
	)
	@ResponseBody
	@PostMapping("/upLoadImage")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_upLoadImage')")
	R upLoadImage(MultipartFile file,String type);


	@ApiOperation(value = "上传身份证", notes = "上传身份证")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "idNumber", value = "身份证号", example = "username", paramType = "query", required = true, dataType = "string"),
					@ApiImplicitParam(name = "idCardFrontImg", value = "身份证正面", example = "String", paramType = "query", required = true, dataType = "file"),
					@ApiImplicitParam(name = "realName", value = "真实姓名", example = "String", paramType = "query", required = true, dataType = "file"),
					@ApiImplicitParam(name = "idCardBackImg", value = "身份证反面", example = "String", paramType = "query", required = true, dataType = "file"),
					@ApiImplicitParam(name = "accountInformationImg", value = "用户手持身份证", example = "file", paramType = "query", required = true, dataType = "file"),
			}
	)

	@ResponseBody
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_upLoadIDCARD')")
	@PostMapping("/uploadIDCARD")
	R uploadIDCARD(String idNumber ,String idCardFrontImg,String realName,String idCardBackImg,String accountInformationImg);

	@ApiOperation(value = "上传微信二维码", notes = "上传微信二维码")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "url", value = "二维码图片地址", example = "", paramType = "query", required = true, dataType = "String"),
			}
	)
	@ResponseBody
	@PostMapping("/wechatUpload")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_wechatUpload')")
	R wechatUpload(String url);

	@ApiOperation(value = "上传支付宝二维码", notes = "上传支付宝二维码")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "url", value = "二维码图片地址", example = "", paramType = "query", required = true, dataType = "String"),
			}
	)
	@ResponseBody
	@PostMapping("/aliPayUpload")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_aliPayUpload')")
	R aliPayUpload(String url);

	@ApiOperation(value = "我的团队", notes = "我的团队")
	@ResponseBody
	@PostMapping("/myTeam")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_myTeam')")
	R myTeam(int page);


	@ApiOperation(value = "等级说明", notes = "等级说明")
	@ResponseBody
	@PostMapping("/levelExplain")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_levelExplain')")
	R levelExplain();

	@ApiOperation(value = "团队统计", notes = "团队统计")
	@ResponseBody
	@PostMapping("/teamTotal")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_teamTotal')")
	R teamTotal();

	@ApiOperation(value = "手续费", notes = "手续费")
	@ResponseBody
	@PostMapping("/freeCharge")
	@PreAuthorize("@pms.hasPermission('sys_yhwl_esc_client_freeCharge')")
	R freeCharge();
}
