package com.example.financemanager.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ReportVO {
    private BigDecimal totalIncome;    // 总收入
    private BigDecimal totalExpense;   // 总支出
    private List<Map<String, Object>> pieData; // 饼图数据: [{name: '餐饮', value: 100}, ...]
    private List<Map<String, Object>> barData; // 柱状图数据
    private List<?> list;              // 明细列表
}