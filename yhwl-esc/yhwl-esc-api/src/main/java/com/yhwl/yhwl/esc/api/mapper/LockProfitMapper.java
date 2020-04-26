package com.yhwl.yhwl.esc.api.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhwl.yhwl.esc.api.entity.LockProfit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LockProfitMapper extends BaseMapper<LockProfit> {

    @Select("select sum(yestaday_profit) from lock_profit where user_name = #{username} and status = 0")
    double yesterdayProfit(String username);

    @Select("select sum(total_profit) from lock_profit where user_name = #{username} and status = 0")
    double totalProfit(String username);

    @Select("select sum(total_in) from lock_profit where user_name = #{username} and status in (0,1,2)")
    double totalMoney(String username);

}
