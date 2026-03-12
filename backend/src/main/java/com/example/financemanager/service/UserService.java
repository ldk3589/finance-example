package com.example.financemanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financemanager.entity.User;

public interface UserService extends IService<User> {

    User login(String username, String password);

    boolean register(User user);
}