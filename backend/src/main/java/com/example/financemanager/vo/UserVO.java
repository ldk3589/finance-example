package com.example.financemanager.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserVO {
    private Long id;
    private String username;
    private Long userId;
    private String name;
    private BigDecimal balance;
    private String token; // 预留 Token 字段
}