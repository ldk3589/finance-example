package com.example.financemanager.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CategoryReportDTO {

    /** * 分类名称
     * 对应 SQL 中的 c.name
     */
    private String name;

    /** * 金额合计
     * 优化：使用 BigDecimal 确保报表金额精准
     * 对应 SQL 中的 SUM(t.amount)
     */
    private BigDecimal value;

    /**
     * 占比（0-1 之间，例如 0.25 表示 25%）
     * 可以在 Service 层计算填充，方便前端展示
     */
    private Double percentage;
}