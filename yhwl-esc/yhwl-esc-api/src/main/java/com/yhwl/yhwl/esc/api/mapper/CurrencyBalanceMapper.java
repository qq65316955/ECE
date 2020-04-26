package com.yhwl.yhwl.esc.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhwl.yhwl.esc.api.entity.CurrencyBalance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CurrencyBalanceMapper extends BaseMapper<CurrencyBalance> {
	@Update("update currency_balance set balance=balance+#{amount} where id =#{id}")
	boolean updateSettle(@Param("amount") double amount, @Param("id") String id);

}
