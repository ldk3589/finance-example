package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.dto.CategoryCreateRequest;
import com.example.financemanager.entity.Category;
import com.example.financemanager.security.SecurityUtils;
import com.example.financemanager.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Result<List<Category>> list() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<Category> categories = categoryService.listCurrentUserCategories(currentUserId);
        return Result.success(categories);
    }

    @PostMapping
    public Result<Category> addCategory(@Valid @RequestBody CategoryCreateRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Category category = categoryService.addCategory(
                currentUserId,
                request.getName(),
                request.getType()
        );
        return Result.success(category);
    }
}