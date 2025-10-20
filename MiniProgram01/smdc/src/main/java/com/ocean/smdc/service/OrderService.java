package com.ocean.smdc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.smdc.entity.Order;

/**
 * 订单服务接口
 * 继承Mybatis-Plus IService，提供基础服务能力
 */
public interface OrderService extends IService<Order> {
    // 可扩展自定义方法
}