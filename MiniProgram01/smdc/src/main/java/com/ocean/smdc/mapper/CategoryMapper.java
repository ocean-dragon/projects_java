package com.ocean.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.smdc.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品分类表Mapper接口
 * 继承Mybatis-Plus BaseMapper，提供基础CRUD
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    // 可自定义扩展方法
}