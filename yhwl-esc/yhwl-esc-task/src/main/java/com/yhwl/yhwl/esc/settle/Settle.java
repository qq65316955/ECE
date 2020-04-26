package com.yhwl.yhwl.esc.settle;

import com.google.common.collect.Lists;
import com.mayabot.nlp.common.Maps;
import com.yhwl.yhwl.common.tools.core.collection.CollUtil;
import com.yhwl.yhwl.esc.api.base.BaseController;
import com.yhwl.yhwl.esc.api.base.FromUseEnum;
import com.yhwl.yhwl.esc.api.base.InOrOutEnum;
import com.yhwl.yhwl.esc.api.entity.*;
import com.yhwl.yhwl.wallet.api.base.CoinEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class Settle extends BaseController {

	private List<String> accountInfoList;

	private List<MiningMachine> miningMachineList;

	private List<AccountProfit> level1;

	private List<AccountProfit> level2;

	private List<AccountProfit> level3;

	private List<AccountProfit> level4;



	@Async
	public void settleminer(String key){
		if(!"9PTrHkXDpjhMX98K0zQc8obPZLjU7R".equals(key)){
			log.info("非法调用");
			return;
		}
		log.info("扣除到期矿机算力");

		clearPower();

		log.info("等级结算开始");
		try {
			cacheList();
			isLevel1();
			isLevel2();
			isLevel3();
			isLevel4();
			isLevel5();
		}catch (Exception e){
			e.printStackTrace();
		}
		log.info("开始挖矿结算");
		try {
			clearLocalCache();

			cleanAll();

			cache();

			miner();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("挖矿结算结束");
		//领导奖结算
		log.info("领导奖结算开始");
		try {
			double levelProfit=0.0d;
			//一级账户领导奖
			List<AccountProfit> listByLevel1=accountProfitService.listByLevel1();
			for(AccountProfit accountProfit:listByLevel1){
				AccountInfo accountInfo=accountInfoService.findByUserName(accountProfit.getUsername());
				//身份证审核才能有收益
				if(!accountInfo.isAuth()){
					continue;
				}
				double profit =leaderLevel1(accountProfit,0,0);
				accountProfit.setTodayLeader(profit);
				accountProfit.setTotalLeader(accountProfit.getTotalLeader()+profit);
				accountProfit.updateById();
			}
			//二级以上账户领导奖
			List<AccountProfit> listByLevel2=accountProfitService.listByLevel2();
			if (CollUtil.isNotEmpty(listByLevel2)) {
				for (AccountProfit accountProfit : listByLevel2) {
					AccountInfo accountInfo=accountInfoService.findByUserName(accountProfit.getUsername());
					//身份证审核才能有收益
					if(!accountInfo.isAuth()){
						continue;
					}
					List<AccountProfit> profitList = new ArrayList<AccountProfit>();
					//传入初始值，Topaccount是目标账户，accountprofit是最大账户
					//返回的是每条线等级最高的用户
					List<UserLevel> userLevels=userLevelService.findByUsernameOnDownAndLevel(accountProfit.getUsername(),1);
					for(UserLevel userLevel:userLevels) {
						AccountProfit accountProfit1 = accountProfitService.findByUserName(userLevel.getRightName());
						List<AccountProfit> profitList1 = new ArrayList<AccountProfit>();
						List<AccountProfit> accountProfitList = leaderSettle(profitList1, accountProfit, accountProfit1, accountProfit1, 1);
						profitList.addAll(accountProfitList);
					}
					double profit = 0;

					List<AccountProfit> listWithoutDuplicates = profitList.stream().distinct().collect(Collectors.toList());

					Map<String ,String> map= Maps.newHashMap();
					for (AccountProfit accountProfit1 : listWithoutDuplicates) {
						//向上处理部分
						profit += leaderUpProfit(accountProfit1, accountProfit.getUsername(),map);
						//向下部分
						profit += leaderdownProfit(accountProfit1, accountProfit.getUsername(),listWithoutDuplicates);
					}
					if(profit<=0){
						continue;
					}
					accountProfit.setTodayLeader(profit);
					accountProfit.setTotalLeader(accountProfit.getTotalLeader()+profit);
					accountProfit.updateById();
				}
			}
			List<String> list=accountProfitService.listByLeaderLevel();
			//三级以上用户平级奖
			if (CollUtil.isNotEmpty(list)) {
				for (String s : list) {
					AccountInfo accountInfo=accountInfoService.findByUserName(s);
					//身份证审核才能有收益
					if(!accountInfo.isAuth()){
						continue;
					}
					List<AccountProfit> profitList = new ArrayList<AccountProfit>();
					//传入初始值，Topaccount是目标账户，accountprofit是最大账户
					//返回的是每条线等级最高的用户
					List<UserLevel> userLevels=userLevelService.findByUsernameOnDownAndLevel(s,1);
					AccountProfit accountProfit=accountProfitService.findByUserName(s);
					for(UserLevel userLevel:userLevels) {
						AccountProfit accountProfit1 = accountProfitService.findByUserName(userLevel.getRightName());
						List<AccountProfit> profitList1 = new ArrayList<AccountProfit>();
						List<AccountProfit> accountProfitList = leaderSettle(profitList1, accountProfit, accountProfit1, accountProfit, 0);
						profitList.addAll(accountProfitList);
					}
					List<AccountProfit> listWithoutDuplicates = profitList.stream().distinct().collect(Collectors.toList());
					levelProfit = sameLevel(listWithoutDuplicates,accountProfit);
					accountProfit.setTodayLeader(accountProfit.getTodayLeader() + levelProfit);
					accountProfit.setTotalLeader(accountProfit.getTotalLeader()+levelProfit);
					accountProfit.updateById();
				}
			}
			for(String s:accountInfoList){
				AccountProfit accountProfit=accountProfitService.findByUserName(s);
				if(accountProfit.getTodayLeader()>0){
					CurrencyBalance currencyBalance=currencyBalanceService.findByUserNameAndCoinName(s,"ECE");
					currencyBalance.setBalance(currencyBalance.getBalance()+accountProfit.getTodayLeader());
					AccountDetails accountDetails=new AccountDetails();
					accountDetails.setUsername(s);
					accountDetails.setAmount(accountProfit.getTodayLeader());
					accountDetails.setCurrency(CoinEnum.ECE);
					accountDetails.setInOrOut(InOrOutEnum.LEADER);
					boolean f=accountDetails.insert();
					boolean f1=currencyBalance.updateById();
					if(!f||!f1){
						log.info("{}领导奖结算失败",s);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		log.info("领导奖结算结束");

	}

	private void clearPower(){
		List<MiningMachine> miningMachines=miningMachineService.ListOnTime();
		for(MiningMachine miningMachine:miningMachines){
			try {
				accountProfitService.updatPledgeByUserName(miningMachine.getUserName(),-miningMachine.getPower());
				accountProfitService.updatTotalPledgeByUserName(miningMachine.getUserName(),-miningMachine.getPower());
				miningMachine.setSettle(true);
				miningMachine.updateById();
			} catch (Exception e) {
				log.error("清除算力失败");
			}
		}
	}

	private void cacheList(){
		level1=new ArrayList<>();
		level2=new ArrayList<>();
		level3=new ArrayList<>();
		level4=new ArrayList<>();
	}
	private void clearLocalCache() {
		miningMachineList = Lists.newArrayList();
		accountInfoList =Lists.newArrayList();

	}
	/**
	 * 1. 缓存所有矿机和用户
	 */
	private void cache() {

		accountInfoList =accountInfoService.listByActive();
		miningMachineList=miningMachineService.findByActive(System.currentTimeMillis());
	}

	/**
	 * 重置今日产值
	 */
	private void cleanAll(){
		miningMachineService.cleanAll();
		accountProfitService.updateMeTodayProfit();
	}

	/**
	 * 静态收益
	 */
	private void miner(){
		for(MiningMachine miningMachine:miningMachineList){
			try {
				AccountInfo accountInfo=accountInfoService.findByUserName(miningMachine.getUserName());
				//身份证审核才能有收益
				if(!accountInfo.isAuth()){
					continue;
				}
				//更新今日产值
				miningMachine.setTodayPro(miningMachine.getProfitPerday());
				//更新总产值
				miningMachine.setTotalPro(miningMachine.getTotalPro()+miningMachine.getProfitPerday());
				//更新待领取收益
				miningMachine.setBalance(miningMachine.getBalance()+miningMachine.getProfitPerday());
				boolean f=miningMachine.updateById();
				if(!f){
					log.error("{}挖矿失败",miningMachine.getUserName());
				}
				AccountDetails accountDetails=new AccountDetails();
				accountDetails.setUsername(miningMachine.getUserName());
				accountDetails.setAmount(miningMachine.getProfitPerday());
				accountDetails.setInOrOut(InOrOutEnum.MINER);
				accountDetails.setCurrency(CoinEnum.valueOf("ECE"));
				accountDetails.setRemarks(miningMachine.getId());
				accountDetails.setFromuser(FromUseEnum.MINER);
				accountDetails.insert();
				accountProfitService.updateProftByUsername(miningMachine.getUserName(),miningMachine.getProfitPerday());
			} catch (Exception e) {
				log.error("{}挖矿失败",miningMachine.getUserName());
			}
		}
	}
	//一级用户领导奖
	private double leaderLevel1(AccountProfit accountProfit,int i,double profit){
		AppGlobalVariable app=appGlobalVariableService.getById(appConfigID);
		//直推用户
		try {
			List<UserLevel> levels=userLevelService.findByUsernameOnDownAndLevel(accountProfit.getUsername(),1);
			//代数
			i++;
			if(CollUtil.isEmpty(levels)){
				return profit;
			}
			if(i==7){
				return profit;
			}
			for(UserLevel userLevel:levels){
				AccountProfit accountProfit1=accountProfitService.findByUserName(userLevel.getRightName());
				if(!accountProfit1.isLevel1()&&accountProfit1.getLeaderLevel()==0){
					profit+=accountProfit1.getTodayProfit()*app.getLeaderlv1();
					profit=leaderLevel1(accountProfit1,i,profit);
				}
			}
		} catch (Exception e) {
			log.error("{}一级用户出错",accountProfit.getUsername());
		}
		return profit;

	}
	//查找最大的账户
	private List<AccountProfit> leaderSettle(List<AccountProfit> list,AccountProfit topAccount,AccountProfit maxAccount,AccountProfit rightAccount,int i){
		//直推用户
		//代数
		try {
			i++;
			switch (topAccount.getLeaderLevel()){
				case 5:if(i>29){
					if(!list.contains(maxAccount)) {
						list.add(maxAccount);
					}
					return list;
				}break;
				case 4:if(i>20){
					if(!list.contains(maxAccount)) {
						list.add(maxAccount);
					}
					return list;
				}break;
				case 3:if(i>15){
					if(!list.contains(maxAccount)) {
						list.add(maxAccount);
					}
					return list;
				}break;
				case 2:if(i>9){
					if(!list.contains(maxAccount)) {
						list.add(maxAccount);
					}
					return list;
				}break;
				default:break;
			}
			List<UserLevel> levels=userLevelService.findByUsernameOnDownAndLevel(rightAccount.getUsername(),1);

			AccountProfit accountProfit1=null;
			if(CollUtil.isNotEmpty(levels)) {
				for (UserLevel userLevel : levels) {
					accountProfit1 = accountProfitService.findByUserName(userLevel.getRightName());
					if (accountProfit1.getLeaderLevel() >= topAccount.getLeaderLevel()) {
						if (!list.contains(accountProfit1)) {
							list.add(accountProfit1);
						}
					}
					if (maxAccount == null) {
						maxAccount = accountProfit1;
					}
					if (accountProfit1.getLeaderLevel() > maxAccount.getLeaderLevel()) {
						maxAccount = accountProfit1;
						list = leaderSettle(list, topAccount, maxAccount, accountProfit1, i);
					} else if (accountProfit1.isLevel1() && !maxAccount.isLevel1() && accountProfit1.getLeaderLevel() == 0 && maxAccount.getLeaderLevel() == 0) {
						maxAccount = accountProfit1;
						list = leaderSettle(list, topAccount, maxAccount, accountProfit1, i);
					} else {
						list = leaderSettle(list, topAccount, maxAccount, accountProfit1, i);
					}
				}
			}
			if(CollUtil.isEmpty(levels)){
				if(!list.contains(maxAccount)) {
					list.add(maxAccount);
				}
			}
		} catch (Exception e) {
			log.error("{}查找最大等级账户错误",topAccount.getUsername());
		}
		return list;

	}
	//向上处理
	private double leaderUpProfit(AccountProfit accountProfit,String username,Map<String,String> map){
		AppGlobalVariable app=appGlobalVariableService.getById(appConfigID);
		double profit=0;

		AccountProfit topAccount=accountProfitService.findByUserName(username);
		try {
			UserLevel userLevel=userLevelService.findByLeftAndRight(username,accountProfit.getUsername());
			//上级达到目标用户之间的上级用户,从目标用户向下排序
			List<UserLevel> levels=userLevelService.findByUsernameOnUPAndLevel(accountProfit.getUsername(),userLevel.getLevelNum());
			int i=0;
			for(UserLevel userLevel1:levels){
				AccountProfit accountProfit1 =accountProfitService.findByUserName(userLevel1.getLeftName());
				if(accountProfit1.isLevel1()&&accountProfit1.getLeaderLevel()==0){
					if(i==0) {
						i = 1;
					}
				}
				if(accountProfit1.getLeaderLevel()>1&&accountProfit1.getLeaderLevel()>i){
					i=accountProfit1.getLeaderLevel();
				}
				switch (topAccount.getLeaderLevel()){
					case 5:switch (i){
						case 4:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv5() - app.getLeaderlv4());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						}
						break;
						case 3:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv5() - app.getLeaderlv3());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						};break;
						case 2:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv5() - app.getLeaderlv2());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						};break;
						case 1:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv5() - app.getLeaderlv1());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						};break;
					}break;
					case 4:switch (i){
						case 3:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv4() - app.getLeaderlv3());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						};break;
						case 2:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv4() - app.getLeaderlv2());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						};break;
						case 1:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv4() - app.getLeaderlv1());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						};break;
					}break;
					case 3:switch (i){
						case 2:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv3() - app.getLeaderlv2());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						};break;
						case 1:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv3() - app.getLeaderlv1());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						};break;
					}break;
					case 2:switch (i){
						case 1:if(map.get(accountProfit1.getUsername())==null) {
							profit += accountProfit1.getTodayProfit() * (app.getLeaderlv2() - app.getLeaderlv1());
							map.put(accountProfit1.getUsername(), String.valueOf(accountProfit1.getTodayProfit()));
						};break;
					}break;
				}

			}
		} catch (Exception e) {
			log.error("{}向上处理错误",username);
		}
		return profit;
	}
	//向下部分
	private double leaderdownProfit(AccountProfit accountProfit,String username,List<AccountProfit> list){
		AppGlobalVariable app=appGlobalVariableService.getById(appConfigID);
		AccountProfit accountProfit1=accountProfitService.findByUserName(username);
		double profit=0;
		//结算账户等级小于目标账户等级
		try {
			if(accountProfit1.getLeaderLevel()<=accountProfit.getLeaderLevel()){
				return 0;
			}
			//目标用户伞下总静态收益
			double total=0;
			List<UserLevel> userLevels=userLevelService.findByUsernameOnDown(accountProfit.getUsername());
			if(CollUtil.isEmpty(userLevels)){
				return 0;
			}
			for(UserLevel userLevel:userLevels){
				AccountProfit accountProfit2=accountProfitService.findByUserName(userLevel.getRightName());
				total+=accountProfit2.getTodayProfit();
			}
			total+=accountProfit.getTodayProfit();
			double total1=0;
			for(AccountProfit accountProfit2:list){
				UserLevel userLevel=userLevelService.findByLeftAndRight(accountProfit.getUsername(),accountProfit2.getUsername());
				if(userLevel!=null&&userLevels.contains(userLevel)){
					List<UserLevel> userLevels1=userLevelService.findByUsernameOnDown(userLevel.getRightName());
					for(UserLevel userLevel1:userLevels1){
						AccountProfit accountProfit3=accountProfitService.findByUserName(userLevel1.getRightName());
						total1+=accountProfit3.getTodayProfit();
					}
					total1+=accountProfit2.getTodayProfit();
				}
			}
			total=total-total1;
			switch (accountProfit1.getLeaderLevel()){
				case 5:switch (accountProfit.getLeaderLevel()){
					case 4:profit+=total*(app.getLeaderlv5()-app.getLeaderlv4());break;
					case 3:profit+=total*(app.getLeaderlv5()-app.getLeaderlv3());break;
					case 2:profit+=total*(app.getLeaderlv5()-app.getLeaderlv2());break;
					case 0:if(accountProfit.isLevel1()){
						profit+=total*(app.getLeaderlv5()-app.getLeaderlv1());
					}else {
						profit+=total*app.getLeaderlv5();
					};break;
					default:break;
				}break;
				case 4:switch (accountProfit.getLeaderLevel()){
					case 3:profit+=total*(app.getLeaderlv4()-app.getLeaderlv3());break;
					case 2:profit+=total*(app.getLeaderlv4()-app.getLeaderlv2());break;
					case 0:if(accountProfit.isLevel1()){
						profit+=total*(app.getLeaderlv4()-app.getLeaderlv1());
					}else {
						profit+=total*app.getLeaderlv4();
					};break;
					default:break;
				}break;
				case 3:switch (accountProfit.getLeaderLevel()){
					case 2:profit+=total*(app.getLeaderlv3()-app.getLeaderlv2());break;
					case 0:if(accountProfit.isLevel1()){
						profit+=total*(app.getLeaderlv3()-app.getLeaderlv1());
					}else {
						profit+=total*app.getLeaderlv3();
					};break;
					default:break;
				}break;
				case 2:switch (accountProfit.getLeaderLevel()){
					case 0:if(accountProfit.isLevel1()){
						profit+=total*(app.getLeaderlv2()-app.getLeaderlv1());
					}else {
						profit+=total*app.getLeaderlv2();
					};break;
					default:break;
				}break;
				default:break;
			}
		} catch (Exception e) {
			log.error("{}向下部分错误",username);
		}
		return profit;

	}
	//领导奖，平级部分
	private double sameLevel(List<AccountProfit> list,AccountProfit accountProfit1){
		double profit=0;
		for(AccountProfit accountProfit:list){
			if(accountProfit.getLeaderLevel()==accountProfit1.getLeaderLevel()){
				profit+=accountProfit.getTodayLeader()*0.1;
			}
		}
		return profit;
	}
	private void isLevel1(){
		List<AccountInfo> list=accountInfoService.list();
		for(AccountInfo accountInfo:list){
			try {
				AccountProfit accountProfit=accountProfitService.findByUserName(accountInfo.getUsername());
				if(accountProfit!=null&&accountProfit.getLeaderNumber()>=30){
					List<UserLevel> levels=userLevelService.findByUsernameOnDownAndLevel(accountProfit.getUsername(),1);
					if(CollUtil.isNotEmpty(levels)&&levels.size()>=6){
						if(!accountProfit.isLevel1()){
							accountProfit.setLevel1(true);
							accountProfit.updateById();
						}
						level1.add(accountProfit);
					}
				}
			} catch (Exception e) {
				log.error("{}1级错误",accountInfo.getUsername());
			}
		}
		log.info("{}",level1.size());
	}
	private void isLevel2(){
		for(AccountProfit accountProfit:level1){
			try {
				List<UserLevel> levels=userLevelService.findUpByNum(accountProfit.getUsername(),9);
				AccountProfit accountProfit1=null;
				for(UserLevel userLevel:levels){
					accountProfit1=accountProfitService.findByUserName(userLevel.getLeftName());
					//9代用户有累计50KB并且是1级用户
					int f=0;
					int f2=0;
					if(accountProfit1.getTotalPledgeSize()>=50&&accountProfit1.isLevel1()){
						//直推用户
						List<UserLevel >levels1=userLevelService.findByUsernameOnDownAndLevel(userLevel.getLeftName(),1);

						for(UserLevel userLevel1:levels1){
							int count=0;
							AccountProfit accountProfit2=accountProfitService.findByUserName(userLevel1.getRightName());
							//直推用户是否是1星
							if(accountProfit2.isLevel1()){
								count++;
							}
							//直推的下级用户
							List<UserLevel> levels2=userLevelService.findByUsernameOnDown(userLevel1.getRightName());
							for(UserLevel userLevel2:levels2) {
								AccountProfit accountProfit3 = accountProfitService.findByUserName(userLevel2.getRightName());
								if (accountProfit3.isLevel1()) {
									count++;
								}
							}
							//一条线达到2个以上v1
							if(count>=2){
								f2++;
								continue;
							}
							//一条线达到1个以上v1;
							if(count>=1){
								f++;
								continue;
							}
						}
					}
					if(f2>=2||(f2==1&&f==1)){
						accountProfit1.setLeaderLevel(2);
						accountProfit1.updateById();
						if(!level2.contains(accountProfit1)) {
							level2.add(accountProfit1);
						}
					}

				}
			} catch (Exception e) {
				log.error("{}2级错误",accountProfit.getUsername());
			}


		}
	}
	private void isLevel3(){
		for(AccountProfit accountProfit:level2){

			try {
				AccountProfit accountProfit1=null;
				List<UserLevel> levels1=userLevelService.findAllUp(accountProfit.getUsername());
				for(UserLevel userLevel:levels1){
					accountProfit1=accountProfitService.findByUserName(userLevel.getLeftName());
					//9代用户有累计300KB并且是2级用户
					if(accountProfit1.getTotalPledgeSize()>=300&&accountProfit1.getLeaderLevel()==2){
						//直推用户
						List<UserLevel >levels2=userLevelService.findByUsernameOnDownAndLevel(userLevel.getLeftName(),1);

						int b=0;
						int b2=0;
						for(UserLevel userLevel1:levels2){
							int count=0;
							AccountProfit accountProfit2=accountProfitService.findByUserName(userLevel1.getRightName());
							//直推用户是否是2星
							if(accountProfit2.getLeaderLevel()==2){
								count++;
							}
							//直推的下级用户
							List<UserLevel> levels3=userLevelService.findByUsernameOnDown(userLevel1.getRightName());
							for(UserLevel userLevel2:levels3) {
								AccountProfit accountProfit3 = accountProfitService.findByUserName(userLevel2.getRightName());
								//直推用户所有下级是否是2星
								if (accountProfit3.getLeaderLevel()==2) {
									count++;
								}
							}
							//一条线达到2个以上v1
							if(count>=2){
								b2++;
								continue;
							}
							//一条线达到1个以上v1;
							if(count>=1){
								b++;
								continue;
							}
						}
						if(b2>=2||(b2==1&&b==1)){
							accountProfit1.setLeaderLevel(3);
							accountProfit1.updateById();
							if(!level3.contains(accountProfit1)) {
								level3.add(accountProfit1);
							}
						}
					}

				}
			} catch (Exception e) {
				log.error("{}3级错误",accountProfit.getUsername());
			}

		}
	}
	private void isLevel4(){
		for(AccountProfit accountProfit:level3){

			try {
				List<UserLevel> levels1=userLevelService.findAllUp(accountProfit.getUsername());
				AccountProfit accountProfit1=null;
				for(UserLevel userLevel:levels1){
					accountProfit1=accountProfitService.findByUserName(userLevel.getLeftName());
					//9代用户有累计1000KB并且是3级用户
					if(accountProfit1.getTotalPledgeSize()>=1000&&accountProfit1.getLeaderLevel()==3){
						//直推用户
						List<UserLevel >levels2=userLevelService.findByUsernameOnDownAndLevel(userLevel.getLeftName(),1);
						int b=0;
						for(UserLevel userLevel1:levels2){
							int count=0;
							AccountProfit accountProfit2=accountProfitService.findByUserName(userLevel1.getRightName());
							//直推用户是否是2星
							if(accountProfit2.getLeaderLevel()==3){
								count++;
							}
							//直推的下级用户
							List<UserLevel> levels3=userLevelService.findByUsernameOnDown(userLevel1.getRightName());
							for(UserLevel userLevel2:levels3) {
								AccountProfit accountProfit3 = accountProfitService.findByUserName(userLevel2.getRightName());
								//直推用户所有下级是否是2星
								if (accountProfit3.getLeaderLevel()==3) {
									count++;
								}
							}
							//一条线达到1个以上v3
							if(count>=1){
								b++;
								continue;
							}
						}
						if(b>=3){
							accountProfit1.setLeaderLevel(4);
							accountProfit1.updateById();
							if(!level4.contains(accountProfit1)) {
								level4.add(accountProfit1);
							}
						}
					}

				}
			} catch (Exception e) {
				log.error("{}4级错误",accountProfit.getUsername());
			}

		}
	}
	private void isLevel5() {
		for (AccountProfit accountProfit : level4) {
			try {
				List<UserLevel> levels1 = userLevelService.findAllUp(accountProfit.getUsername());
				for (UserLevel userLevel : levels1) {
					AccountProfit accountProfit1 = accountProfitService.findByUserName(userLevel.getLeftName());
					//9代用户有累计1000KB
					if (accountProfit1.getTotalPledgeSize() >= 3000) {
						//直推用户
						List<UserLevel> levels2 = userLevelService.findByUsernameOnDownAndLevel(userLevel.getLeftName(), 1);
						int b = 0;
						for (UserLevel userLevel1 : levels2) {
							int count = 0;
							AccountProfit accountProfit2 = accountProfitService.findByUserName(userLevel1.getRightName());
							//直推用户是否是2星
							if (accountProfit2.getLeaderLevel() == 4) {
								count++;
							}
							//直推的下级用户
							List<UserLevel> levels3 = userLevelService.findByUsernameOnDown(userLevel1.getRightName());
							for (UserLevel userLevel2 : levels3) {
								AccountProfit accountProfit3 = accountProfitService.findByUserName(userLevel2.getRightName());
								//直推用户所有下级是否是2星
								if (accountProfit3.getLeaderLevel() == 4) {
									count++;
								}
							}
							//一条线达到1个以上v4;
							if (count >= 1) {
								b++;
								continue;
							}
						}
						if (b >= 3) {
							accountProfit1.setLeaderLevel(5);
							accountProfit1.updateById();
						}


					}
				}
			} catch (Exception e) {
				log.error("{}4级错误",accountProfit.getUsername());
			}
		}
	}
}
