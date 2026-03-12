package com.example.financemanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financemanager.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService extends IService<Account> {

    boolean updateBalance(Long id, BigDecimal delta);

    void createDefaultAccount(Long userId);

    List<Account> listCurrentUserAccounts(Long userId);

    Account addAccount(Long userId, String name, BigDecimal balance);
}