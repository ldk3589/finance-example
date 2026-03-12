package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.dto.TransactionCreateRequest;
import com.example.financemanager.entity.Transaction;
import com.example.financemanager.security.SecurityUtils;
import com.example.financemanager.service.TransactionBizService;
import com.example.financemanager.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionBizService transactionBizService;
    private final TransactionService transactionService;

    public TransactionController(TransactionBizService transactionBizService,
                                 TransactionService transactionService) {
        this.transactionBizService = transactionBizService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public Result<Transaction> record(@Valid @RequestBody TransactionCreateRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Transaction transaction = new Transaction();
        transaction.setAccountId(request.getAccountId());
        transaction.setCategoryId(request.getCategoryId());
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setRemark(request.getRemark());

        Transaction result = transactionBizService.record(transaction, currentUserId);
        return Result.success(result);
    }

    @GetMapping
    public Result<List<Transaction>> list(@RequestParam(defaultValue = "month") String range) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        LocalDateTime startTime = resolveStartTime(range);
        List<Transaction> list = transactionService.listUserTransactionsFrom(currentUserId, startTime);
        return Result.success(list);
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
}