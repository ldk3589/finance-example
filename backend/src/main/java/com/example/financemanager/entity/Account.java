package com.example.financemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * <p>
 * 
 * </p>
 *
 * @author dk
 * @since 2026-01-22
 */
@Data
@TableName("account")
public class Account {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 显式指定数据库中的字段名为 user_id
    @com.baomidou.mybatisplus.annotation.TableField("user_id")
    private Long userId;

    private String name;

    private BigDecimal balance;

    // 显式指定数据库中的字段名为 create_time
    @com.baomidou.mybatisplus.annotation.TableField("create_time")
    private LocalDateTime createTime;
}
