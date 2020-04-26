package com.yhwl.yhwl.esc.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhwl.yhwl.esc.api.entity.AccountProfit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AccountProfitMapper extends BaseMapper<AccountProfit> {


	@Select("select * from account_profit where leader_level=0 and is_level1 =1 and del_flag=0")
	List<AccountProfit> listByLevel1();

	@Select("select * from account_profit where leader_level >=2 and del_flag=0")
	List<AccountProfit> listByLevel2();

	@Select("select ifnull(sum(today_profit),0) from account_profit where del_flag =0")
	Double sumTodayProduceAll();

	@Select("select ifnull(sum(total_profit),0) from account_profit where del_flag=0")
	Double sumTotalProduceAll();

	@Update("update account_profit set references_number=references_number+1 where user_name=#{username} and del_flag=0")
	int updateReferenceNum(@Param("username") String username);

	@Update("update account_profit set leader_number = leader_number + 1 where user_name in(select left_name from user_level where right_name=#{username} and level_num<=3 and del_flag=0)")
	int updateLeaderNumber(@Param("username")String  username);

    @Update("update account_profit set disk = disk + #{disk} where del_flag=0 and user_name = #{username}")
    int updateDiskByUserName(@Param("username") String username, @Param("disk") Long disk);

    @Update("update account_profit set team_disk = team_disk + #{disk} where del_flag=0 and user_name in (select left_name from user_level where right_name=#{username} and del_flag=0)")
    int updateDiskByUserNameTeam(@Param("username") String username, @Param("disk") Long disk);

    @Select("select * from account_profit where user_name=#{username} and del_flag=0 order by create_date desc limit 1")
    AccountProfit findByUserName(@Param("username") String username);



    /**
     * 团队人数加1
     * @param username
     * @return
     */
    @Update("update account_profit set miner_number = miner_number + 1 where user_name=#{username} and del_flag=0")
    int updateminerNumberByUserName(@Param("username") String username);

    int updateUpperPledgeSize(@Param("upperUsers") List<String> upperUsers, @Param("size") long size);

    int updateUpperTeamNumber(@Param("upperUsers") List<String> upperUsers, @Param("number") int number);

    int updateUpperMinerDisk(@Param("upperUsers") List<String> upperUsers, @Param("size") long size);

    int updateUpperTodayProfit(@Param("upperUsers") List<String> upperUsers, @Param("profit") double profit);

    @Update("update account_profit set ")
    boolean testUpdateTodayProfit();




    /**
     * 更新用户质押空间
     * @param username
     * @param size
     * @return
     */
    @Update("update account_profit set pledge_size=pledge_size+#{size} where user_name = #{username}")
    int updatPledgeByUserName(@Param("username") String username, @Param("size") Double size);


    //修改上级有效算力
    @Update("update account_profit set total_pledge_size=total_pledge_size+#{size} where user_name in (select left_name from user_level where right_name=#{username} and level_num <=9 and del_flag=0)")
    int updatTotalPledgeByUserName(@Param("username") String username, @Param("size") Double size);
    int updateMinerNumber(@Param("upperUsers") List<String> upperUsers);

    @Update("update account_profit set today_leader=0 ,today_profit=0 where del_flag=0 ")
    boolean updateMeTodayProfit();

    @Select("select * from account_profit where sprofit>0")
    List<AccountProfit> listBySprofit();

    @Select("select user_name from account_profit where leader_level >=3 and del_flag=0")
    List<String> listByLeaderLevel();

    @Update("update account_profit set today_profit=today_profit+#{profit} ,total_profit=total_profit+#{profit} where user_name=#{username}")
    int updateProftByUsername(@Param("username") String username,@Param("profit") double profit);

}
