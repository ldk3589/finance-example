package com.example.financemanager.mapper;

import com.example.financemanager.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    int updateBalance(@Param("id") Long id, @Param("delta") BigDecimal delta);

}