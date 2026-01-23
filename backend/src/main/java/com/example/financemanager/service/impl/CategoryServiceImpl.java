package com.example.financemanager.service.impl;

import com.example.financemanager.entity.Category;
import com.example.financemanager.mapper.CategoryMapper;
import com.example.financemanager.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dk
 * @since 2026-01-22
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
