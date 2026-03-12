package com.example.financemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.financemanager.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}