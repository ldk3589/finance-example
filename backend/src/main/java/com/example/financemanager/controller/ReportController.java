package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.security.SecurityUtils;
import com.example.financemanager.service.ReportService;
import com.example.financemanager.vo.ReportVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/stats")
    public Result<ReportVO> getStats(@RequestParam(defaultValue = "month") String range) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        ReportVO reportVO = reportService.getStatistics(currentUserId, range);
        return Result.success(reportVO);
    }
}