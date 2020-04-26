package com.yhwl.yhwl.esc.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhwl.yhwl.esc.api.entity.UserLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserLevelMapper extends BaseMapper<UserLevel> {

	@Select("select * from user_level where right_name=#{username} and level_num <#{i} and del_flag=0 order by level_num desc")
	List<UserLevel> findByUsernameOnUPAndLevel(@Param("username") String username,@Param("i") int i);

	@Select("select left_name from user_level where right_name=#{username} and level_num <=3 and del_flag=0")
	List<String> findByUserUpString3(@Param("username")String username);

	@Select("Select right_name from user_level where left_name=#{username} and level_num <=9 and del_flag =0")
	List<String> findByUserDown9(@Param("username")String username);

    List<String> findByUserToUserName(@Param("username") String username);

    List<String> findByUserUpString(@Param("username") String username);

    List<String> findOnLevelByUserToUserName(@Param("name") String name, @Param("i") int i);

    @Select("select right_name from user_level where left_name=#{username} and level_num<=#{i} and del_flag=0")
    List<String> findDownUserByUsernameAndLevelLe(@Param("username")String username,@Param("i") int i);

    @Select("select right_name from user_level  where left_name=#{name} and level_num>=#{down} and level_num<=#{top}")
    List<String> findByUserDownAndLevelGeAndLe(@Param("name")String name,@Param("down") int down,@Param("top")int top);

    @Select("SELECT\n" +
            "\tt.num,\n" +
            "\tt.NAME,\n" +
            "\tt.rank \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\t_table.num,\n" +
            "\t_table.NAME,\n" +
            "\t@rank := @rank + 1,\n" +
            "\t@last_rank :=\n" +
            "CASE\n" +
            "\t\n" +
            "\tWHEN @last_num = _table.num THEN\n" +
            "\t@last_rank \n" +
            "\tWHEN @last_num := _table.num THEN\n" +
            "\t@rank \n" +
            "\tEND AS rank \n" +
            "FROM\n" +
            "\t( SELECT count( 1 ) AS num, left_name AS NAME FROM user_level WHERE del_flag = 0 GROUP BY left_name ORDER BY num DESC ) AS _table,\n" +
            "\t( SELECT @rank := 0, @last_num := NULL, @last_rank := 0 ) AS r \n" +
            "\t) AS t " +
            "where name = #{username}")
    Map inviteRank(@Param("username") String username);

    @Select("select left_name from user_level where right_name=#{username} and level_num <=9 and del_flag=0")
    List<String> findByUserUpString9(@Param("username") String username);

}
