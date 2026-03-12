package com.example.financemanager.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreateRequest {

    @NotBlank(message = "账户名称不能为空")
    private String name;

    private BigDecimal balance;
}