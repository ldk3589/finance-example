package com.example.financemanager.service;

import com.example.financemanager.vo.ReportVO;

public interface ReportService {

    ReportVO getStatistics(Long userId, String range);
}