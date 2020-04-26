package com.yhwl.yhwl.esc.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhwl.yhwl.esc.api.entity.AccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {
    @Select("select count(*) from account")
     int  findAllUserCount();

    @Select("select * from account where user_name not in (select user_name from account_profit)")
    List<AccountInfo> listOther();

    @Select(value = "select * from account where user_name = #{userName}")
    AccountInfo findByUserName(String userName);


    @Select("select user_name from account where del_flag=0 ")
    List<String> listByActive();
}
