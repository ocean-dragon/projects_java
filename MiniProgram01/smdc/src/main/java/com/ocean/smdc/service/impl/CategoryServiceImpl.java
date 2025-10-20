package com.ocean.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.smdc.entity.Category;
import com.ocean.smdc.mapper.CategoryMapper;
import com.ocean.smdc.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 菜品分类服务实现类
 * 继承Mybatis-Plus ServiceImpl，实现CategoryService接口
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    // 可扩展自定义业务方法
}