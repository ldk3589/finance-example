package com.example.financemanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financemanager.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService extends IService<Transaction> {

    List<Transaction> listUserTransactionsFrom(Long userId, LocalDateTime startTime);
}