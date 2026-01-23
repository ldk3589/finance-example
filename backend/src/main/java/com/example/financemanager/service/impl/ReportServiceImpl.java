package com.example.financemanager.service.impl;

import com.example.financemanager.entity.Transaction;
import com.example.financemanager.mapper.TransactionMapper;
import com.example.financemanager.service.ReportService;
import com.example.financemanager.vo.ReportVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    // 注入 Mapper 以使用我们写的关联查询 SQL
    private final TransactionMapper transactionMapper;

    public ReportServiceImpl(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Override
    public ReportVO getStatistics(Long userId, String range) {
        // 1. 计算统计的开始时间
        LocalDateTime startTime = switch (range) {
            case "week" -> LocalDateTime.now().minusWeeks(1);
            case "month" -> LocalDateTime.now().minusMonths(1);
            default -> LocalDateTime.of(2000, 1, 1, 0, 0); // 全部历史
        };

        // 2. 调用自定义 Mapper 方法进行 JOIN 查询，获取带有 categoryName 的流水数据
        // 注意：此处 SQL 逻辑已在 TransactionMapper 中通过 @Select 定义
        List<Transaction> allData = transactionMapper.selectWithCategory(userId, startTime);

        ReportVO vo = new ReportVO();
        vo.setList(allData);

        // 3. 计算收支总额
        BigDecimal totalIncome = allData.stream()
                .filter(t -> "INCOME".equals(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = allData.stream()
                .filter(t -> "EXPENSE".equals(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        vo.setTotalIncome(totalIncome);
        vo.setTotalExpense(totalExpense);

        // 4. 组装饼图数据 (仅统计支出部分的分类占比)
        // 使用 t.getCategoryName() 获取 JOIN 出来的真实分类名称
        Map<String, BigDecimal> expenseGroupData = allData.stream()
                .filter(t -> "EXPENSE".equals(t.getType()))
                .collect(Collectors.groupingBy(
                        t -> t.getCategoryName() != null ? t.getCategoryName() : "未分类",
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ));

        List<Map<String, Object>> pieData = new ArrayList<>();
        expenseGroupData.forEach((name, value) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("value", value);
            pieData.add(map);
        });
        vo.setPieData(pieData);

        // 5. 组装柱状图数据 (简单收支对比)
        List<Map<String, Object>> barData = new ArrayList<>();
        barData.add(createBarItem("总收入", totalIncome));
        barData.add(createBarItem("总支出", totalExpense));
        vo.setBarData(barData);

        return vo;
    }

    // 辅助方法：快速创建图表对象
    private Map<String, Object> createBarItem(String name, BigDecimal value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}