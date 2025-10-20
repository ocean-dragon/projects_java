package com.ocean.smdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.smdc.entity.Dish;

/**
 * 菜品服务接口
 * 继承Mybatis-Plus IService，提供基础服务能力
 */
public interface DishService extends IService<Dish> {
    // 可扩展自定义方法
}