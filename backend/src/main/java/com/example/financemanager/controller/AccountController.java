package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.entity.Account;
import com.example.financemanager.service.AccountService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/user/{userId}")
    public Result<List<Account>> getByUserId(@PathVariable Long userId) {
        List<Account> list = accountService.lambdaQuery()
                .eq(Account::getUserId, userId)
                .list();
        return Result.success(list);
    }

    @PostMapping
    public Result<Account> addAccount(@RequestBody Account account) {
        // 1. 安全校验：确保有关联的用户ID
        if (account.getUserId() == null) {
            return Result.error(400, "用户ID不能为空");
        }

        // 2. 默认值填充：如果没有设置初始余额，默认为 0
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }

        // 3. 执行保存
        boolean saved = accountService.save(account);

        // 4. 返回结果：成功则返回带 ID 的完整对象
        if (saved) {
            return Result.success(account);
        } else {
            return Result.error(500, "添加账户失败");
        }
    }
}