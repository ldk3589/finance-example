package com.example.financemanager.service.impl;

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
    public Transaction record(Transaction transaction) {
        // 1. 校验（建议抽取私有方法或使用 Validator）
        validateTransaction(transaction);
        var account = accountService.getById(transaction.getAccountId());
        if (account == null) {
            throw new BusinessException("记账失败：关联账户不存在");
        }
        transaction.setUserId(account.getUserId()); // 确保流水关联到正确用户
        // 2. 保存交易流水
        transactionService.save(transaction);

        // 3. 更新账户余额（核心优化点：原子更新）
        boolean updateSuccess;
        BigDecimal amount = transaction.getAmount();

        // 如果是支出，传负数给数据库进行累加
        if ("EXPENSE".equals(transaction.getType())) {
            // 增加余额检查逻辑：在 SQL 中判断 balance >= amount
            // 这一步建议在 accountService 中写一个专门的方法
            updateSuccess = accountService.updateBalance(
                    transaction.getAccountId(),
                    amount.negate() // 取负值
            );
        } else {
            updateSuccess = accountService.updateBalance(
                    transaction.getAccountId(),
                    amount
            );
        }

        if (!updateSuccess) {
            // 如果更新失败（比如账户不存在或余额不足），触发事务回滚
            throw new BusinessException("记账失败：账户不存在或余额不足");
        }

        log.info("记账完成: id={}, type={}", transaction.getId(), transaction.getType());
        return transaction;
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccountId() == null) {
            throw new BusinessException("交易数据非法");
        }
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("交易金额必须大于0");
        }
    }
}