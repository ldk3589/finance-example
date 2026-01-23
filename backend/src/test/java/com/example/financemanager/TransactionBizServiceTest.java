package com.example.financemanager;

import com.example.financemanager.entity.Transaction;
import com.example.financemanager.service.TransactionBizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class TransactionBizServiceTest {

    @Autowired
    private TransactionBizService transactionBizService;

    @Test
    void record_success() {
        Transaction t = new Transaction();
        t.setAccountId(1L);
        t.setAmount(new BigDecimal("50"));
        t.setType("EXPENSE");
        t.setCategoryId(1L);

        transactionBizService.record(t);
    }
}
