package com.example.financemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.financemanager.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    @Update("""
        UPDATE account
        SET balance = balance + #{delta}
        WHERE id = #{id}
          AND balance + #{delta} >= 0
        """)
    int updateBalance(@Param("id") Long id, @Param("delta") BigDecimal delta);
}