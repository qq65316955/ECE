package com.yhwl.yhwl.esc.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhwl.yhwl.esc.api.entity.PledgeDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PledgeDetailsMapper extends BaseMapper<PledgeDetails> {

	@Select("SELECT sum(total_produce)-sum(today_produce) from pledge_details WHERE user_name=#{username}")
	double sumMore(@Param("username") String username);

    @Select("select * from pledge_details p where p.mac in (select mac from mining_machine m where p.user_name = m.user_name and m.online_state = 1 or (m.miner_type = 2 and m.last_flush_time <> -1) ) and p.`status` = '成功'")
    List<PledgeDetails> listPledgeDetails();

    @Select("select IFNULL(sum(unrelieved_size),0) from pledge_details where user_name=#{username}")
    long sumUnrelievedSizeByUserName(@Param("username") String username);

    long sumUnrelievedSizeByUserNames(@Param("byUserToUserName") List<String> byUserToUserName);

    @Select("select IFNULL(sum(today_produce),0) from pledge_details where user_name=#{username}")
    double sumTodayProduceByUsername(@Param("username") String username);


    double sumTodayProduceByUsernames(@Param("byUserToUserName") List<String> byUserToUserName);

    @Select("select IFNULL(sum(unrelieved_size),0) from pledge_details where mac=#{miner_id}")
    long sumUnrelievedSizeByMac(String miner_id);

    @Select("select IFNULL(sum(today_produce),0) from pledge_details where mac=#{mac}")
    double sumTodayProduceByMac(@Param("mac") String mac);
    
    @Update("update pledge_details set today_produce = 0 where mac = #{mac}")
    boolean resetTodayProduceByMac(@Param("mac") String mac);

    @Select("select IFNULL(sum(size),0) from pledge_details where miner_id=#{minerId}")
    Long sumSizeByMinerId(@Param("minerId") String minerId);

    @Select("select IFNULL(sum(relieved_amount),0) from pledge_details where mac=#{mac}")
    double sumPledgeCurrency(@Param("mac") String mac);

    @Select("select IFNULL(sum(total_produce),0) from pledge_details where miner_id=#{id}")
    double sumTotalProduceByMinerid(@Param("id") String id);

    @Select("select IFNULL(sum(today_produce),0) from pledge_details")
    double sumTodayProduceAll();
    @Select("select IFNULL(sum(total_produce),0) from pledge_details")
    double sumTotalProduceAll();

    @Update("update pledge_details set today_produce=today_produce+#{amount} , total_produce=total_produce+#{amount} where id=#{id}")
    boolean saveTask(@Param("amount") double amount, @Param("id") String id);
}
