package com.ocean.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.smdc.entity.Order;
import com.ocean.smdc.mapper.OrderMapper;
import com.ocean.smdc.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * 订单服务实现类
 * 继承Mybatis-Plus ServiceImpl，实现OrderService接口
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    // 可扩展自定义业务方法
}