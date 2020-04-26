package com.yhwl.yhwl.esc.controller;

import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.common.security.annotation.Inner;
import com.yhwl.yhwl.esc.api.inteface.EscTaskUnAunthControllerInterface;
import com.yhwl.yhwl.esc.settle.Settle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ese/task")
@RestController
public class EseTaskController implements EscTaskUnAunthControllerInterface {

	@Autowired
	private Settle settle;

	@Override
	@Inner(false)
	public R settle(String from,String key) {
		settle.settleminer(key);
		return R.ok();
	}
}
