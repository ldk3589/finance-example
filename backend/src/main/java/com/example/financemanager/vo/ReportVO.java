package com.example.financemanager.vo;

import com.example.financemanager.entity.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReportVO {

    private BigDecimal totalIncome = BigDecimal.ZERO;

    private BigDecimal totalExpense = BigDecimal.ZERO;

    private List<PieItem> pieData = new ArrayList<>();

    private List<BarItem> barData = new ArrayList<>();

    private List<Transaction> list = new ArrayList<>();

    @Data
    public static class PieItem {
        private String name;
        private BigDecimal value;
    }

    @Data
    public static class BarItem {
        private String name;
        private BigDecimal income = BigDecimal.ZERO;
        private BigDecimal expense = BigDecimal.ZERO;
    }
}