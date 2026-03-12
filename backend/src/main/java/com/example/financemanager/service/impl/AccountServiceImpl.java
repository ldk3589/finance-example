package com.example.financemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financemanager.entity.Account;
import com.example.financemanager.exception.BusinessException;
import com.example.financemanager.mapper.AccountMapper;
import com.example.financemanager.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public boolean updateBalance(Long id, BigDecimal delta) {
        log.info("更新账户余额: accountId={}, delta={}", id, delta);
        return baseMapper.updateBalance(id, delta) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDefaultAccount(Long userId) {
        log.info("正在为用户 {} 初始化默认账户", userId);

        Account cashAccount = new Account();
        cashAccount.setUserId(userId);
        cashAccount.setName("现金账户");
        cashAccount.setBalance(BigDecimal.ZERO);
        boolean cashSaved = this.save(cashAccount);

        Account otherAccount = new Account();
        otherAccount.setUserId(userId);
        otherAccount.setName("其他");
        otherAccount.setBalance(BigDecimal.ZERO);
        boolean otherSaved = this.save(otherAccount);

        if (!cashSaved || !otherSaved) {
            throw new BusinessException("初始化默认账户失败");
        }
    }

    @Override
    public List<Account> listCurrentUserAccounts(Long userId) {
        return lambdaQuery()
                .eq(Account::getUserId, userId)
                .orderByAsc(Account::getId)
                .list();
    }

    @Override
    public Account addAccount(Long userId, String name, BigDecimal balance) {
        long count = lambdaQuery()
                .eq(Account::getUserId, userId)
                .eq(Account::getName, name)
                .count();

        if (count > 0) {
            throw new BusinessException("账户名称已存在");
        }

        Account account = new Account();
        account.setUserId(userId);
        account.setCreateTime(java.time.LocalDateTime.now());
        account.setName(name);
        account.setBalance(balance == null ? BigDecimal.ZERO : balance);

        boolean saved = save(account);
        if (!saved) {
            throw new BusinessException("添加账户失败");
        }

        return account;
    }
}