package com.example.financemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financemanager.entity.Category;
import com.example.financemanager.exception.BusinessException;
import com.example.financemanager.mapper.CategoryMapper;
import com.example.financemanager.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<Category> listCurrentUserCategories(Long userId) {
        return lambdaQuery()
                .eq(Category::getUserId, userId)
                .orderByAsc(Category::getId)
                .list();
    }

    @Override
    public Category addCategory(Long userId, String name, String type) {
        String upperType = type == null ? null : type.trim().toUpperCase();

        if (!"EXPENSE".equals(upperType) && !"INCOME".equals(upperType)) {
            throw new BusinessException("分类类型只能是 EXPENSE 或 INCOME");
        }

        long count = lambdaQuery()
                .eq(Category::getUserId, userId)
                .eq(Category::getName, name)
                .eq(Category::getType, upperType)
                .count();

        if (count > 0) {
            throw new BusinessException("该分类已存在");
        }

        Category category = new Category();
        category.setUserId(userId);
        category.setCreateTime(java.time.LocalDateTime.now());
        category.setName(name);
        category.setType(upperType);

        boolean saved = save(category);
        if (!saved) {
            throw new BusinessException("添加分类失败");
        }

        return category;
    }
}