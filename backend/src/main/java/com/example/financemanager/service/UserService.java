package com.example.financemanager.service;

import com.example.financemanager.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dk
 * @since 2026-01-22
 */
public interface UserService extends IService<User> {
    User login(String username, String password);
    boolean register(User user);
}
