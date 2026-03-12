package com.example.financemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.financemanager.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {

    @Select("""
        SELECT
            t.id,
            t.user_id,
            t.account_id,
            t.category_id,
            t.type,
            t.amount,
            t.remark,
            t.create_time,
            c.name AS category_name
        FROM transaction t
        LEFT JOIN category c ON t.category_id = c.id
        WHERE t.user_id = #{userId}
          AND t.create_time >= #{startTime}
        ORDER BY t.create_time DESC
        """)
    List<Transaction> selectWithCategory(@Param("userId") Long userId,
                                         @Param("startTime") LocalDateTime startTime);
}