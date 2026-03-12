package com.example.financemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financemanager.entity.Account;
import com.example.financemanager.entity.Category;
import com.example.financemanager.entity.User;
import com.example.financemanager.exception.BusinessException;
import com.example.financemanager.mapper.UserMapper;
import com.example.financemanager.service.AccountService;
import com.example.financemanager.service.CategoryService;
import com.example.financemanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(AccountService accountService,
                           CategoryService categoryService,
                           PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(User user) {
        log.info("用户注册尝试：username={}", user.getUsername());

        long count = lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .count();

        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean saved = save(user);
        if (!saved) {
            return false;
        }

        log.info("正在为新用户 {} 初始化默认账户和分类", user.getUsername());
        accountService.createDefaultAccount(user.getId());
        initDefaultCategories(user.getId());
        return true;
    }

    @Override
    public User login(String username, String password) {
        log.info("用户登录尝试：username={}", username);

        User user = lambdaQuery()
                .eq(User::getUsername, username)
                .one();

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        Account account = accountService.lambdaQuery()
                .eq(Account::getUserId, user.getId())
                .last("LIMIT 1")
                .one();

        if (account == null) {
            accountService.createDefaultAccount(user.getId());
        }

        return user;
    }

    private void initDefaultCategories(Long userId) {
        List<String> expenseNames = new ArrayList<>(Arrays.asList(
                "餐饮美食", "交通出行", "购物消费", "住房缴费", "休闲娱乐", "其他"
        ));
        for (String name : expenseNames) {
            Category category = new Category();
            category.setUserId(userId);
            category.setName(name);
            category.setType("EXPENSE");
            categoryService.save(category);
        }

        List<String> incomeNames = new ArrayList<>(Arrays.asList(
                "工资收入", "理财收益", "兼职外快", "其他"
        ));
        for (String name : incomeNames) {
            Category category = new Category();
            category.setUserId(userId);
            category.setName(name);
            category.setType("INCOME");
            categoryService.save(category);
        }
    }
}