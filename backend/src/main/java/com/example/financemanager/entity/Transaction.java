package com.example.financemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
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
@TableName("transaction")
public class Transaction {

    @TableId(type = IdType.AUTO)

    private Long id;

    private Long userId;

    private Long accountId;

    private Long categoryId;

    private BigDecimal amount;

    private String type;

    private String remark;

    private LocalDateTime createTime;

    @TableField(exist = false) // 表示这不是数据库表里的字段，仅用于业务封装
    private String categoryName;
}
