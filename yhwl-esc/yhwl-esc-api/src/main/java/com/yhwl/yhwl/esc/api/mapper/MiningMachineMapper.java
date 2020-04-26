package com.yhwl.yhwl.esc.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhwl.yhwl.esc.api.entity.MiningMachine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MiningMachineMapper extends BaseMapper<MiningMachine> {

    @Select("SELECT USER_NAME FROM MINING_MACHINE WHERE DEL_FLAG=0 AND USER_NAME <> '' AND USER_NAME IS NOT NULL GROUP BY USER_NAME")
    List<String> findAllUserName();

    @Select("select * from mining_machine where user_name = #{username} order by pledge_num desc limit 1")
    MiningMachine findByMaxSize(@Param("username") String username);

    @Update("update mining_machine set today_online = 0")
    void updateTodayOnline();

    double sumMinerSettleByUsers(@Param("item") List<String> usernames);

	void updateTodayProByUserName(@Param("item") List<String> usernames);

    @Update("update mining_machine set online_state=0 where #{currentTimeMillis}-last_flush_time>120000 and miner_type <> 2")
    void flushOnlineStatus(@Param("currentTimeMillis") long currentTimeMillis);

    @Update("update mining_machine set today_online=today_online+#{i},total_online=total_online+#{i} where online_state=1")
    void flushOnlineTime(@Param("i") int i);
    
    @Update("update mining_machine set balance = balance + today_pro")
    boolean updateMinerBalance();

    Long sumOfDiskByUsername(@Param("username") String username);

    Long sumOfDiskByUsernameS(@Param("item") List<String> item);

    @Select("select pledgeEnum from pledge_details where user_name = #{userName} and miner_id = #{minerId} GROUP BY pledgeEnum")
    List<String> findLabelByMacUserName(@Param("minerId") String minerId, @Param("userName") String userName);

    @Select("select * from mining_machine where remain_time>#{currentTime} and del_flag=0 and status='成功'")
    List<MiningMachine> findByActive(@Param("currentTime") long currentTime);

    @Update("update mining_machine set today_pro=0 where del_flag=0")
    boolean cleanAll();

    @Update("update mining_machine set balance=balance+today_pro where del_flag=0")
	boolean updateMinerBalanceByUserName();
}
