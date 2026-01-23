package com.example.financemanager;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

public class CodeGenerator {

    public static void main(String[] args) {

        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:3306/finance_manager?useSSL=false&serverTimezone=UTC",
                        "root",
                        "123456"
                )
                .globalConfig(builder -> {
                    builder.author("dk")
                            .outputDir(System.getProperty("user.dir") + "/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.financemanager")
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl");
                })
                .strategyConfig(builder -> {
                    builder
                            .addInclude("user", "account", "category", "transaction")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl");
                })
                .execute();
    }
}
