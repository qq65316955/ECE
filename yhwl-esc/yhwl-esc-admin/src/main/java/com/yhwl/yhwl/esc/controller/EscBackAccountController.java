package com.yhwl.yhwl.esc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhwl.yhwl.admin.api.feign.RemoteUserService;
import com.yhwl.yhwl.common.core.constant.SecurityConstants;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.tools.core.collection.CollectionUtil;
import com.yhwl.yhwl.common.tools.core.exceptions.HZException;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.FromUseEnum;
import com.yhwl.yhwl.esc.api.base.InOrOutEnum;
import com.yhwl.yhwl.esc.api.base.LanguageResultCodeEnum;
import com.yhwl.yhwl.esc.api.entity.*;
import com.yhwl.yhwl.esc.api.inteface.EscBackAccountControllerInterface;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "esc", tags = "esc 账户接口")
@RequestMapping("/escadmin/account")
@RestController
@Slf4j
@AllArgsConstructor
public class EscBackAccountController extends BaseController implements EscBackAccountControllerInterface {

    protected final RemoteUserService remoteUserService;



    @Override
    public R currencyData(Page page, CurrencyBalance currencyBalance) {

        IPage ipage = currencyBalanceService.findByPage(page,currencyBalance);

        return R.ok(ipage);
    }

    @Override
    public R detailData(Page page , AccountDetails accountDetails) {

        IPage ipage = accountDetailsService.findByPage(page,accountDetails);

        return R.ok(ipage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R charge(String id, String money) {
        CurrencyBalance currencyBalance = currencyBalanceService.getById(id);
        Double balence = currencyBalance.getBalance();
        Double charging = Double.parseDouble(money);
        currencyBalance.setBalance(balence+charging);
        try {
            boolean result = currencyBalance.updateById();
            if(!result) {
                throw new HZException("充值失败");
            }
            AccountDetails acountDetails = new AccountDetails();
            acountDetails.setAmount(Double.parseDouble(money));
            acountDetails.setCurrency(CoinEnum.valueOf(currencyBalance.getName()));
            acountDetails.setInOrOut(InOrOutEnum.CONDUCT);
            acountDetails.setFromuser(FromUseEnum.USER);
            acountDetails.setRemarks("人工充值----->" + balence + "------>" + currencyBalance.getBalance());
            acountDetails.setDelFlag(false);
            acountDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
            acountDetails.setUsername(currencyBalance.getUserName());
            boolean insert = acountDetails.insert();
            if(!insert) {
                throw new HZException("充值失败");
            }
            return R.ok(null,"充值成功");
        } catch (HZException e) {
            return R.failed(e.getMsg());
        }
    }

    @Override
    public R accounts(Page page , String username,String nickname,String phone) {
        IPage iPage=accountInfoService.findByPage(page,username,nickname,phone);
        return R.ok(iPage);
    }

    @Override
    public R changePwd(String id, String password)  {
        try {
            AccountInfo byUserName = accountInfoService.getById(id);
            if(byUserName==null){
                throw new HZException(LanguageResultCodeEnum.USER_NOT_EXISTS.getMessage(),LanguageResultCodeEnum.USER_NOT_EXISTS.getValue());
            }
            R r = remoteUserService.updatePassword(byUserName.getUsername(), password, SecurityConstants.FROM_IN);
            if(r.getCode()!=0){
                throw new HZException(LanguageResultCodeEnum.OPERATION_FAIL.getMessage(),LanguageResultCodeEnum.OPERATION_FAIL.getValue());
            }
            return R.ok(null,"修改密码成功");
        } catch (HZException e) {
            return R.failed("修改密码失败");
        }


    }

    @Override
    public R saveAccount(AccountInfo account) {
        if(account == null){
            return R.failed("更新失败");
        }
        if(account.getId()==null){
            if (account.getPasswordCode()!=null&&account.getPasswordCode()!="") {
                account.setTransactionPassword(account.getPasswordCode());
            }
            Boolean success = account.insert();
            if(success){
                return R.ok(null,"添加成功");
            }else{
                return R.failed("添加失败");
            }
        }else{
            if (account.getPasswordCode()!=null&&account.getPasswordCode()!="") {
                account.setTransactionPassword(account.getPasswordCode());
            }
            Boolean success = account.updateById();
            if(success){
                return R.ok(null,"更新成功");
            }else{
                return R.failed("更新失败");
            }
        }
    }

    @Override
    public R isCash(String id) {

        AccountInfo accountInfo=accountInfoService.getById(id);
        if(accountInfo.isIscash()) {
			accountInfo.setIscash(false);
			boolean ret=accountInfo.updateById();
			if (!ret){
				return R.failed("操作失败");
			}
			return R.ok(null,"禁止成功");
		}else if(!accountInfo.isIscash()){
        	accountInfo.setIscash(true);
			boolean ret=accountInfo.updateById();
			if (!ret){
				return R.failed("操作失败");
			}
			return R.ok(null,"解禁成功");
		}
        return R.failed("操作失败");

    }

	@Override
	public R idCardData(Page page, String realName,String idNumber,Integer status) {
    	IPage<IdCard> iPage=idCardService.findByPage(page,realName,idNumber,status);
		return R.ok(iPage);
	}

	@Override
	public R idCardPass(String id,int status) {
    	IdCard idCard=idCardService.getById(id);
    	if(idCard==null){
    		return R.failed(null,"审核失败");
		}
    	if(idCard.getStatus()!=0){
    		return R.failed(null,"不能重复审核");
		}
    	if(status==1) {
			idCard.setStatus(1);
			CurrencyBalance currencyBalance=currencyBalanceService.findByUserNameAndCoinName(idCard.getUsername(),"FIRE");
			if(currencyBalance==null){
				return R.failed(null,"该用户未创建钱包");
			}
			AccountInfo accountInfo=accountInfoService.findByUserName(idCard.getUsername());
			accountInfo.setAuth(true);
			accountInfo.updateById();
			currencyBalance.setBalance(currencyBalance.getBalance()+10);
			currencyBalance.updateById();
			boolean f=idCard.updateById();
			if(!f){
				return R.failed(null,"审核失败");
			}

			return R.ok("审核成功");
		}
    	if(status==2){
			boolean f=idCard.deleteById();
    		if(!f){
    			return R.failed(null,"拒绝审核失败");
			}
    		return R.ok("拒绝审核成功");
		}
		return R.failed("审核失败");
	}


	//冻结和解冻
	@Override
	public R freezeAccount(String id) {
		AccountInfo accountInfo=accountInfoService.getById(id);
		if(accountInfo==null){
			return R.failed(null,"没有该用户");
		}
		if(!accountInfo.isFreeze()) {
			accountInfo.setFreeze(true);
		}else {
			accountInfo.setFreeze(false);
		}
		boolean f=accountInfo.updateById();
		if(!f){
			return R.failed(null,"操作失败");
		}
		return R.ok("操作成功");
	}

	//ese用户关系
	@Override
	public R relation(@RequestParam String username, @RequestParam(required = false) String backUrl) {
		if (!username.contains("ese_")) {
			username = "ese_" + username;
		}
		List<UserLevel> levels = userLevelService.findAllUp(username);
		List<UserLevel> list = userLevelService.findDownUserByUsernameAndLevel(username, 1);
		//下级lines
		if (CollectionUtil.isEmpty(list)) {
			return R.failed(null, "用户无推荐下属");
		}
		for (UserLevel userLevel : list) {
			List<UserLevel> list1 = userLevelService.findDownUserByUsernameAndLevelGt(userLevel.getRightName(), 1);
			if (list1 != null) {
				userLevel.setDownNum(list1.size());
			} else {
				userLevel.setDownNum(0);
			}
		}
		// 上级line
		List<UserLevel> upUsers = userLevelService.findByUsernameOnUPOnlevel(username, 1);

		Map map = new HashMap<>();
		map.put("upLine", upUsers);
		map.put("downLines", list);
		map.put("num", levels.size());
//		model.addAttribute("upLine",upUsers);
//		model.addAttribute("downLines",levelLists);
//		model.addAttribute("appDiv","content");
//		if(StringUtils.isNotBlank(backUrl)) {
//			model.addAttribute("backUrl", backUrl);
//			model.addAttribute("appDiv","main");
//		}
		return R.ok(map, "获取成功");
	}

}
