package com.example.financemanager.service.impl;

import com.example.financemanager.entity.Account;
import com.example.financemanager.entity.Transaction;
import com.example.financemanager.exception.BusinessException;
import com.example.financemanager.service.AccountService;
import com.example.financemanager.service.TransactionBizService;
import com.example.financemanager.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class TransactionBizServiceImpl implements TransactionBizService {

    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionBizServiceImpl(TransactionService transactionService,
                                     AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Transaction record(Transaction transaction, Long currentUserId) {
        validateTransaction(transaction);

        Account account = accountService.getById(transaction.getAccountId());
        if (account == null) {
            throw new BusinessException("记账失败：关联账户不存在");
        }

        if (!account.getUserId().equals(currentUserId)) {
            throw new BusinessException(403, "无权操作该账户");
        }

        transaction.setUserId(currentUserId);
        transaction.setCreateTime(java.time.LocalDateTime.now());

        boolean updateSuccess;
        BigDecimal amount = transaction.getAmount();

        if ("EXPENSE".equalsIgnoreCase(transaction.getType())) {
            updateSuccess = accountService.updateBalance(
                    transaction.getAccountId(),
                    amount.negate()
            );
        } else if ("INCOME".equalsIgnoreCase(transaction.getType())) {
            updateSuccess = accountService.updateBalance(
                    transaction.getAccountId(),
                    amount
            );
        } else {
            throw new BusinessException("交易类型非法");
        }

        if (!updateSuccess) {
            throw new BusinessException("记账失败：账户不存在或余额不足");
        }

        boolean saved = transactionService.save(transaction);
        if (!saved) {
            throw new BusinessException("记账失败：保存流水失败");
        }

        log.info("记账完成: userId={}, accountId={}, type={}, amount={}",
                currentUserId, transaction.getAccountId(), transaction.getType(), transaction.getAmount());

        return transaction;
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccountId() == null) {
            throw new BusinessException("交易数据非法");
        }
        if (transaction.getCategoryId() == null) {
            throw new BusinessException("分类不能为空");
        }
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("交易金额必须大于 0");
        }
        if (transaction.getType() == null || transaction.getType().isBlank()) {
            throw new BusinessException("交易类型不能为空");
        }
    }
}