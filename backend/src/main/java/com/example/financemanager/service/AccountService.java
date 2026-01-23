package com.example.financemanager.service;

import com.example.financemanager.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dk
 * @since 2026-01-22
 */

public interface AccountService extends IService<Account> {

    boolean updateBalance(Long id, BigDecimal delta);

    /**
     * 为新用户创建默认账户（如：现金账户）
     * @param userId 用户ID
     */
    void createDefaultAccount(Long userId);
}