package com.example.financemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.financemanager.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}