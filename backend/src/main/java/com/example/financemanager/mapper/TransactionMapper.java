package com.example.financemanager.mapper;

import com.example.financemanager.entity.Transaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionMapper extends BaseMapper<Transaction> {

    @Select("SELECT t.*, c.name as categoryName FROM transaction t " +
            "LEFT JOIN category c ON t.category_id = c.id " +
            "WHERE t.user_id = #{userId} " +
            "AND t.create_time >= #{startTime}")
    List<Transaction> selectWithCategory(@Param("userId") Long userId,
                                         @Param("startTime") LocalDateTime startTime);
}