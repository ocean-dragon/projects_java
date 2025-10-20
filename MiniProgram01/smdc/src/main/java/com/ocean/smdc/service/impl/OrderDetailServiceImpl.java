package com.ocean.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.smdc.entity.OrderDetail;
import com.ocean.smdc.mapper.OrderDetailMapper;
import com.ocean.smdc.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 订单明细服务实现类
 * 继承Mybatis-Plus ServiceImpl，实现OrderDetailService接口
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
    // 可扩展自定义业务方法
}