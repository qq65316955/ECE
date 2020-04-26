package com.yhwl.yhwl.esc.api.tools;

import com.alibaba.fastjson.JSON;
import com.yhwl.yhwl.common.core.constant.SecurityConstants;
import com.yhwl.yhwl.common.core.util.R;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import com.yhwl.yhwl.wallet.api.base.RequestInfo;
import com.yhwl.yhwl.wallet.api.base.RequestParam;
import com.yhwl.yhwl.wallet.api.base.RequestTypeEnum;
import com.yhwl.yhwl.wallet.api.feign.RemoteWalletService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WalletService {
    /**
     * 项目ID
     */
    private String projectId = "4";

    /**
     * 项目名称
     */
    private String projectName = "ECE";

    /**
     * 项目token
     */
    private String projectToken = "ECE";

    /**
     * 项目密码
     */
    private String projectPassword = "ECE";

    public R sendCoin(RemoteWalletService remoteWalletService, String send, String get, String coin, double amount, String type, String tag){
        try{
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setMethod(RequestTypeEnum.SEND);
            RequestParam requestParam = new RequestParam();
            requestParam.setProjectId(projectId);
            requestParam.setProjectName(projectName);
            requestParam.setProjectToken(projectToken);
            requestParam.setProjectPassword(projectPassword);
            requestParam.setSendAddress(send);
            requestParam.setGetAddress(get);
            requestParam.setCoinName(CoinEnum.valueOf(coin));
            requestParam.setAmount(amount);
            requestParam.setLineType(type);
            requestParam.setTag(tag);
            requestInfo.setParams(requestParam);
            return remoteWalletService.doPost(JSON.toJSONString(requestInfo), SecurityConstants.FROM_IN);
        }catch (Exception e){
            e.printStackTrace();
            return R.failed();
        }
    }

    public R createAddress(RemoteWalletService remoteWalletService,String username,String type){
        try{
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setMethod(RequestTypeEnum.CREATE);
            RequestParam requestParam = new RequestParam();
            requestParam.setProjectId(projectId);
            requestParam.setProjectName(projectName);
            requestParam.setProjectToken(projectToken);
            requestParam.setWalletName(username);
            requestParam.setProjectPassword(projectPassword);
            requestParam.setLineType(type);
            requestInfo.setParams(requestParam);
            return remoteWalletService.doPost(JSON.toJSONString(requestInfo), SecurityConstants.FROM_IN);
        }catch (Exception e){
            e.printStackTrace();
            return R.failed();
        }
    }

    public R getBalance(RemoteWalletService remoteWalletService,String address,String coin,String type){
        try{
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setMethod(RequestTypeEnum.BALANCE);
            RequestParam requestParam = new RequestParam();
            requestParam.setProjectId(projectId);
            requestParam.setProjectName(projectName);
            requestParam.setProjectToken(projectToken);
            requestParam.setProjectPassword(projectPassword);
            requestParam.setAddress(address);
            requestParam.setCoinName(CoinEnum.valueOf(coin));
            requestParam.setLineType(type);
            requestInfo.setParams(requestParam);
            return remoteWalletService.doPost(JSON.toJSONString(requestInfo), SecurityConstants.FROM_IN);
        }catch (Exception e){
            e.printStackTrace();
            return R.failed();
        }
    }

    public R heightSendCoin(RemoteWalletService remoteWalletService,String send,String get,String coin,double amount,String type,double gwai,double gas){
        try{
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setMethod(RequestTypeEnum.SENDWITHPRIVATE);
            RequestParam requestParam = new RequestParam();
            requestParam.setProjectId(projectId);
            requestParam.setProjectName(projectName);
            requestParam.setProjectToken(projectToken);
            requestParam.setProjectPassword(projectPassword);
            requestParam.setSendAddress(send);
            requestParam.setGetAddress(get);
            requestParam.setCoinName(CoinEnum.valueOf(coin));
            requestParam.setAmount(amount);
            requestParam.setLineType(type);
            requestParam.setGwei(gwai);
            requestParam.setGas(gas);
            requestInfo.setParams(requestParam);
            return remoteWalletService.doPost(JSON.toJSONString(requestInfo), SecurityConstants.FROM_IN);
        }catch (Exception e){
            e.printStackTrace();
            return R.failed();
        }
    }


    public R sendCenter(RemoteWalletService remoteWalletService, String get, String coin, String type, String tag, double amount){
        try{
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setMethod(RequestTypeEnum.SENDCENTER);
            RequestParam requestParam = new RequestParam();
            requestParam.setProjectId(projectId);
            requestParam.setProjectName(projectName);
            requestParam.setProjectToken(projectToken);
            requestParam.setProjectPassword(projectPassword);
            requestParam.setGetAddress(get);
            requestParam.setCoinName(CoinEnum.valueOf(coin));
            requestParam.setAmount(amount);
            requestParam.setLineType(type);
            requestParam.setTag(tag);
            requestInfo.setParams(requestParam);
            return remoteWalletService.doPost(JSON.toJSONString(requestInfo), SecurityConstants.FROM_IN);
        }catch (Exception e){
            e.printStackTrace();
            return R.failed();
        }
    }

    public R trust$active(RemoteWalletService remoteWalletService, String address){
        try{
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setMethod(RequestTypeEnum.TRUST$ACTIVE);
            RequestParam requestParam = new RequestParam();
            requestParam.setProjectId(projectId);
            requestParam.setProjectName(projectName);
            requestParam.setProjectToken(projectToken);
            requestParam.setProjectPassword(projectPassword);
            requestParam.setAddress(address);
            requestInfo.setParams(requestParam);
            return remoteWalletService.doPost(JSON.toJSONString(requestInfo), SecurityConstants.FROM_IN);
        }catch (Exception e){
            e.printStackTrace();
            return R.failed();
        }
    }

}