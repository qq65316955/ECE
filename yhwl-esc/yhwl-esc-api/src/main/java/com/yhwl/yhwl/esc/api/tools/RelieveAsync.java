package com.yhwl.yhwl.esc.api.tools;


import com.yhwl.yhwl.esc.api.service.AccountProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelieveAsync {

    @Autowired
	AccountProfitService accountProfitService;

//    @Async
//    public void updateAccountProfit(RelieveDetails relieveDetails) {
//        accountProfitService.updatPledgeByUserName(relieveDetails.getUsername(),-(long) (relieveDetails.getNum() ));
//        accountProfitService.updatTotalPledgeByUserName(relieveDetails.getUsername(),-(long) (relieveDetails.getNum() ));
//    }
//    @Async
//    public void updateAccountProfit(PledgeDetails pledgeDetails) {
//        accountProfitService.updatPledgeByUserName(pledgeDetails.getUsername(),pledgeDetails.getSize()/1024/1024/1024);
//        accountProfitService.updatTotalPledgeByUserName(pledgeDetails.getUsername(),pledgeDetails.getSize()/1024/1024/1024);
//    }
}
