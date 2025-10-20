package com.ocean.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.smdc.entity.Dish;
import com.ocean.smdc.mapper.DishMapper;
import com.ocean.smdc.service.DishService;
import org.springframework.stereotype.Service;

/**
 * 菜品服务实现类
 * 继承Mybatis-Plus ServiceImpl，实现DishService接口
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    // 可扩展自定义业务方法
}