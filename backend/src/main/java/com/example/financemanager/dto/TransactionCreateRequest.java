package com.example.financemanager.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionCreateRequest {

    @NotNull(message = "账户不能为空")
    private Long accountId;

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @NotBlank(message = "类型不能为空")
    private String type;

    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0.01", message = "金额必须大于 0")
    private BigDecimal amount;

    private String remark;
}