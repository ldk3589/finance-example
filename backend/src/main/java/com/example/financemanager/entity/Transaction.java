package com.example.financemanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("transaction")
public class Transaction {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("account_id")
    private Long accountId;

    @TableField("category_id")
    private Long categoryId;

    private String type;

    private BigDecimal amount;

    private String remark;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String categoryName;
}