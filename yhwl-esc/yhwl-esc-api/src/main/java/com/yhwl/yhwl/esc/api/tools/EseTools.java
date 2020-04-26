package com.yhwl.yhwl.esc.api.tools;

import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EseTools extends BaseController {

	@Autowired
	private AccountInfoService accountInfoService;

	public Integer getAccountLevelOfTeam(String rightName, double referencesSize){
		return 0;
	}
}
