package com.example.financemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financemanager.entity.Transaction;
import com.example.financemanager.mapper.TransactionMapper;
import com.example.financemanager.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    @Override
    public List<Transaction> listUserTransactionsFrom(Long userId, LocalDateTime startTime) {
        return baseMapper.selectWithCategory(userId, startTime);
    }
}