package com.example.financemanager.service;

import com.example.financemanager.entity.Transaction;

public interface TransactionBizService {

    Transaction record(Transaction transaction, Long currentUserId);
}