package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.entity.Category;
import com.example.financemanager.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category") // 保持路径风格统一
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取指定用户的分类列表
     * @param userId 用户ID
     */
    @GetMapping("/user/{userId}")
    public Result<List<Category>> list(@PathVariable Long userId) {
        // 过滤用户ID，确保数据隔离
        List<Category> categories = categoryService.lambdaQuery()
                .eq(Category::getUserId, userId)
                .list();
        return Result.success(categories);
    }

    /**
     * 新增分类
     * 修改点：返回 Category 对象，方便前端“新增即选中”
     */
    @PostMapping
    public Result<Category> addCategory(@RequestBody Category category) {
        // 1. 基本校验
        if (category.getUserId() == null || category.getName() == null) {
            return Result.error(400, "用户ID和分类名称不能为空");
        }
        if (category.getType() == null) {
            return Result.error(400, "分类类型（支出/收入）不能为空");
        }

        // 2. 保存
        boolean saved = categoryService.save(category);

        // 3. 返回结果
        if (saved) {
            return Result.success(category);
        } else {
            return Result.error(500, "添加分类失败");
        }
    }
}