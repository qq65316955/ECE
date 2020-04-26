package com.yhwl.yhwl.esc.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhwl.yhwl.esc.api.entity.AccountDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface AccountDetailsMapper extends BaseMapper<AccountDetails> {

	@Select("select ifnull(sum(amount),0) from account_details where user_name=#{username} and create_date >'2020-03-18 6:0:0' and in_or_out='领取'")
	double sumtodayProfit(@Param("username") String username);

    Double sumByUserOnInOuts(@Param("username") String username, @Param("currency") String currency, @Param("item") String... item);

    @Select("Select ifnull(SUM(amount),0) from account_details where user_name = #{username} and in_or_out='团队收益' and currency = #{coinName} and to_days(create_date) = to_days(now()) and del_flag=0")
    Double sumTodayPoolProfit(@Param("username") String username, @Param("coinName") String coinName);

    @Select("Select ifnull(SUM(amount),0) from account_details where user_name = #{username} and in_or_out='团队收益' and currency = #{coinName} and del_flag=0")
    Double sumTotalPoolProfit(@Param("username") String username, @Param("coinName") String coinName);

    @Select("select ifnull(sum(amount),0) from account_details where user_name=#{username} and remarks = #{rename} and in_or_out ='推荐'")
    Double sumByUserReferenceAndRemarks(@Param("username") String username, @Param("rename") String rename);

    Double findTodayIncome(@Param("begin") Date begin, @Param("end") Date end);

    @Select("select ifnull(sum(amount),0) from account_details where user_name =#{username} and in_or_out ='推荐' and currency='ECE'")
    Double sumByUserReference(@Param("username")String username);

    @Select("Select ifnull(SUM(amount),0) from account_details where in_or_out = '领导奖'")
    Double findTotalIncome();

    @Select("select * from account_details where in_or_out='团队收益' and create_date>'2020-03-16 0:0:0' and create_date<'2020-03-17 0:0:0'")
    List<AccountDetails> listByTest();

    @Select("select * from account_details where  user_name=#{username} and create_date>'2020-03-15 0:0:0' and create_date<'2020-03-16 0:0:0' and in_or_out='团队收益'")
	AccountDetails findByUsernameAndCreateDate(@Param("username") String username);

    @Select("select ifnull(sum(amount),0) from account_details where user_name=#{username} and in_or_out='团队收益' and create_date>'2020-03-08 0:0:0'")
    Double sumByUserOnTeam(@Param("username") String username);

    @Select("select ifnull(sum(amount),0) from account_details where in_or_out='领取' and create_date>'2020-03-16 0:0:0' and remarks=#{mac}")
    Double findByUsernameAndGet(@Param("mac") String mac);

    @Select("select * from account_details where in_or_out='领取' and create_date >'2020-03-17 3:20:0'")
    List<AccountDetails> findByCreateDate();

	@Select("select ifnull(sum(amount),0) from account_details where user_name=#{mac} and in_or_out='领取' and create_date>'2020-03-08 00:00:00'")
	Double sumProfitGetByUsername(@Param("mac") String mac);

	@Select("select * from account_details where remarks=#{mac} and in_or_out='领取' and create_date>'2020-03-08 00:00:00'")
	List<AccountDetails> findBymacAnd2(@Param("mac") String mac);

	@Select("select * from account_details where remarks=#{mac} and in_or_out='团队收益'and create_date>'2020-03-08 00:00:00'")
	List<AccountDetails> findBymacAnd3(@Param("mac") String mac);

	@Select("select * from account_details a WHERE a.user_name not in (select s.user_name from account_details s where  s.in_or_out='领取' and s.create_date>'2020-03-08 00:00:00' GROUP BY s.user_name) and a.in_or_out='团队收益' and a.create_date >'2020-03-08 0:0:0' GROUP BY a.user_name ")
	List<AccountDetails> listByTest1();

}
