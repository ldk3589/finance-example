package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.dto.LoginRequest;
import com.example.financemanager.dto.RegisterRequest;
import com.example.financemanager.entity.Account;
import com.example.financemanager.entity.User;
import com.example.financemanager.security.JwtTokenProvider;
import com.example.financemanager.service.AccountService;
import com.example.financemanager.service.UserService;
import com.example.financemanager.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService,
                          AccountService accountService,
                          JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public Result<UserVO> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());

        Account account = accountService.lambdaQuery()
                .eq(Account::getUserId, user.getId())
                .last("LIMIT 1")
                .one();

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setUserId(user.getId());

        if (account != null) {
            userVO.setName(account.getName());
            userVO.setBalance(account.getBalance());
        }

        String token = jwtTokenProvider.createToken(user.getId(), user.getUsername());
        userVO.setToken(token);

        return Result.success(userVO);
    }

    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        boolean success = userService.register(user);
        if (success) {
            return Result.success("注册成功");
        }
        return Result.error(1, "注册失败");
    }
}