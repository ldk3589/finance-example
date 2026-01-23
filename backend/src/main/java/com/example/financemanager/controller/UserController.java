package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.dto.LoginDTO;
import com.example.financemanager.entity.Account;
import com.example.financemanager.entity.User;
import com.example.financemanager.service.AccountService;
import com.example.financemanager.service.UserService;
import com.example.financemanager.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody LoginDTO dto) {
        // 1. 调用 Service 获取 User 实体
        User user = userService.login(dto.getUsername(), dto.getPassword());

        if (user == null) {
            return Result.error(1, "用户名或密码错误");
        }

        // 2. 关联查询账户信息（方案二：登录即获取余额）
        // 使用 LambdaQuery 查找该用户的第一个账户
        Account account = accountService.lambdaQuery()
                .eq(Account::getUserId, user.getId())
                .last("LIMIT 1")
                .one();

        // 3. 转换并封装 UserVO
        UserVO userVO = new UserVO();

        // 拷贝同名属性（如 username, nickname 等）
        BeanUtils.copyProperties(user, userVO);

        // 手动映射不一致的字段名：将 User 的 id 赋给 UserVO 的 userId
        userVO.setUserId(user.getId());

        // 填充账户相关信息
        if (account != null) {
            userVO.setName(account.getName());
            userVO.setBalance(account.getBalance());
        }

        // 4. 生成 Token
        String token = UUID.randomUUID().toString();
        userVO.setToken(token);

        return Result.success(userVO);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error(1, "用户名或密码不能为空");
        }

        boolean success = userService.register(user);
        if (success) {
            return Result.success("注册成功");
        } else {
            return Result.error(1, "注册失败，用户名可能已存在");
        }
    }
}