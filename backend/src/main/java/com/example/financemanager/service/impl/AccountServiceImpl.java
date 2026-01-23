package com.example.financemanager.service.impl;

import com.example.financemanager.entity.Account;
import com.example.financemanager.mapper.AccountMapper;
import com.example.financemanager.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Slf4j
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    /**
     * 更新账户余额
     * 使用 SQL 层面的原子更新：balance = balance + delta
     */
    @Override
    public boolean updateBalance(Long id, BigDecimal delta) {
        log.info("更新账户余额: accountId={}, delta={}", id, delta);
        return baseMapper.updateBalance(id, delta) > 0;
    }

    /**
     * 为新用户创建默认账户
     * 修改点：增加“其他”账户的初始化
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 确保原子性
    public void createDefaultAccount(Long userId) {
        log.info("正在为用户 ID: {} 初始化默认账户（现金+其他）", userId);

        // 1. 创建“现金账户”
        Account cashAccount = new Account();
        cashAccount.setUserId(userId);
        cashAccount.setName("现金账户");
        cashAccount.setBalance(BigDecimal.ZERO);
        this.save(cashAccount);

        // 2. 💡 创建保底的“其他”账户
        // 这样当用户在前端点击“其他”且不命名时，可以直接关联到这个 ID
        Account otherAccount = new Account();
        otherAccount.setUserId(userId);
        otherAccount.setName("其他");
        otherAccount.setBalance(BigDecimal.ZERO);

        boolean saved = this.save(otherAccount);

        if (!saved) {
            log.error("用户 {} 默认账户创建失败", userId);
            throw new RuntimeException("初始化默认账户失败");
        }

        log.info("用户 {} 默认账户（现金&其他）初始化成功", userId);
    }
}