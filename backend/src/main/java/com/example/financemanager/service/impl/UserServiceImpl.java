package com.example.financemanager.service.impl;

import com.example.financemanager.entity.Account;
import com.example.financemanager.entity.Category;
import com.example.financemanager.entity.User;
import com.example.financemanager.exception.BusinessException;
import com.example.financemanager.mapper.UserMapper;
import com.example.financemanager.service.AccountService;
import com.example.financemanager.service.CategoryService;
import com.example.financemanager.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final AccountService accountService;
    private final CategoryService categoryService;

    public UserServiceImpl(AccountService accountService, CategoryService categoryService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(User user) {
        log.info("用户注册尝试：username={}", user.getUsername());

        long count = lambdaQuery().eq(User::getUsername, user.getUsername()).count();
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }

        boolean saved = save(user);
        if (!saved) return false;

        log.info("正在为新用户 {} 初始化默认账户和分类", user.getUsername());

        // 1. 初始化账户 (建议在 accountService.createDefaultAccount 内部也加上名为“其他”的账户)
        accountService.createDefaultAccount(user.getId());

        // 2. 初始化分类 (包含保底的“其他”选项)
        initDefaultCategories(user.getId());

        return true;
    }

    @Override
    public User login(String username, String password) {
        log.info("用户登录尝试：username={}", username);

        User user = lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getPassword, password)
                .one();

        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 登录时的补发逻辑也保持一致
        Account account = accountService.lambdaQuery()
                .eq(Account::getUserId, user.getId())
                .last("LIMIT 1").one();

        if (account == null) {
            accountService.createDefaultAccount(user.getId());
        }

        return user;
    }

    /**
     * 优化后的初始化分类方法
     * 增加了“其他”分类，确保用户不命名时有处可放
     */
    private void initDefaultCategories(Long userId) {
        // 预设支出分类 (末尾添加“其他”)
        List<String> expenseNames = new ArrayList<>(Arrays.asList("餐饮美食", "交通出行", "购物消费", "住房缴费", "休闲娱乐", "其他"));
        for (String name : expenseNames) {
            Category c = new Category();
            c.setUserId(userId);
            c.setName(name);
            c.setType("EXPENSE"); // 注意：请确保前端 Add.vue 对应的类型也是 "EXPENSE"
            categoryService.save(c);
        }

        // 预设收入分类 (末尾添加“其他”)
        List<String> incomeNames = new ArrayList<>(Arrays.asList("工资收入", "理财收益", "兼职外快", "其他"));
        for (String name : incomeNames) {
            Category c = new Category();
            c.setUserId(userId);
            c.setName(name);
            c.setType("INCOME");
            categoryService.save(c);
        }
    }
}