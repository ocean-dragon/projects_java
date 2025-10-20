package com.ocean.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.smdc.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品表Mapper接口
 * 继承Mybatis-Plus BaseMapper，提供基础CRUD
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    // 可自定义扩展方法
}