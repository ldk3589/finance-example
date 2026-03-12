package com.example.financemanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financemanager.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<Category> listCurrentUserCategories(Long userId);

    Category addCategory(Long userId, String name, String type);
}