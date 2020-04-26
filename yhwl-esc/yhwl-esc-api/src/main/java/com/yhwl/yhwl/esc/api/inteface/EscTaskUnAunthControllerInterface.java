package com.yhwl.yhwl.esc.api.inteface;

import com.yhwl.yhwl.common.core.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface EscTaskUnAunthControllerInterface {

	@ApiOperation(value = "挖矿结算", notes = "添加矿机商城")
	@ResponseBody
	@GetMapping("/settle")
	R settle(@RequestParam(required = false) String from,@RequestParam("key") String key);


}
