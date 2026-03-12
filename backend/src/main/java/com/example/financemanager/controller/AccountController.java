package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.dto.AccountCreateRequest;
import com.example.financemanager.entity.Account;
import com.example.financemanager.security.SecurityUtils;
import com.example.financemanager.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Result<List<Account>> list() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<Account> list = accountService.listCurrentUserAccounts(currentUserId);
        return Result.success(list);
    }

    @PostMapping
    public Result<Account> addAccount(@Valid @RequestBody AccountCreateRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Account account = accountService.addAccount(
                currentUserId,
                request.getName(),
                request.getBalance()
        );
        return Result.success(account);
    }
}