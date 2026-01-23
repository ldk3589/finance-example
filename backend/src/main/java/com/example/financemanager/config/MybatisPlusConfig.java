package com.example.financemanager.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.financemanager.mapper")
public class MybatisPlusConfig {
}
