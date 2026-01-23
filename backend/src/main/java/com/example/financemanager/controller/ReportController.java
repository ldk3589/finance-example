package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.service.ReportService;
import com.example.financemanager.vo.ReportVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/stats")
    public Result<ReportVO> getStats(@RequestParam Long userId, @RequestParam String range) {
        ReportVO reportVO = reportService.getStatistics(userId, range);
        return Result.success(reportVO);
    }
}