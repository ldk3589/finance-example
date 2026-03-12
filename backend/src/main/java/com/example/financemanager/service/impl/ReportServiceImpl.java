package com.example.financemanager.service.impl;

import com.example.financemanager.entity.Transaction;
import com.example.financemanager.service.ReportService;
import com.example.financemanager.service.TransactionService;
import com.example.financemanager.vo.ReportVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    private final TransactionService transactionService;

    public ReportServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public ReportVO getStatistics(Long userId, String range) {
        LocalDateTime startTime = resolveStartTime(range);
        List<Transaction> transactions = transactionService.listUserTransactionsFrom(userId, startTime);

        ReportVO reportVO = new ReportVO();
        reportVO.setList(transactions);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        Map<String, BigDecimal> pieMap = new LinkedHashMap<>();
        Map<String, ReportVO.BarItem> barMap = new LinkedHashMap<>();

        for (Transaction transaction : transactions) {
            BigDecimal amount = transaction.getAmount() == null ? BigDecimal.ZERO : transaction.getAmount();

            if ("INCOME".equalsIgnoreCase(transaction.getType())) {
                totalIncome = totalIncome.add(amount);
            } else if ("EXPENSE".equalsIgnoreCase(transaction.getType())) {
                totalExpense = totalExpense.add(amount);

                String categoryName = transaction.getCategoryName() == null ? "未分类" : transaction.getCategoryName();
                pieMap.put(categoryName, pieMap.getOrDefault(categoryName, BigDecimal.ZERO).add(amount));
            }

            String dayKey = formatDay(transaction.getCreateTime());
            ReportVO.BarItem barItem = barMap.get(dayKey);
            if (barItem == null) {
                barItem = new ReportVO.BarItem();
                barItem.setName(dayKey);
                barMap.put(dayKey, barItem);
            }

            if ("INCOME".equalsIgnoreCase(transaction.getType())) {
                barItem.setIncome(barItem.getIncome().add(amount));
            } else if ("EXPENSE".equalsIgnoreCase(transaction.getType())) {
                barItem.setExpense(barItem.getExpense().add(amount));
            }
        }

        reportVO.setTotalIncome(totalIncome);
        reportVO.setTotalExpense(totalExpense);

        List<ReportVO.PieItem> pieData = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : pieMap.entrySet()) {
            ReportVO.PieItem item = new ReportVO.PieItem();
            item.setName(entry.getKey());
            item.setValue(entry.getValue());
            pieData.add(item);
        }
        reportVO.setPieData(pieData);

        reportVO.setBarData(new ArrayList<>(barMap.values()));

        return reportVO;
    }

    private LocalDateTime resolveStartTime(String range) {
        LocalDate today = LocalDate.now();

        if ("week".equalsIgnoreCase(range)) {
            return today.minusDays(6).atStartOfDay();
        }

        if ("year".equalsIgnoreCase(range)) {
            return LocalDate.of(today.getYear(), 1, 1).atStartOfDay();
        }

        return LocalDate.of(today.getYear(), today.getMonth(), 1).atStartOfDay();
    }

    private String formatDay(LocalDateTime time) {
        if (time == null) {
            return "";
        }
        LocalDate date = time.toLocalDate();
        return date.toString();
    }
}