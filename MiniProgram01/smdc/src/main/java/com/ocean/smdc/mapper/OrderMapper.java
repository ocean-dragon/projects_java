package com.ocean.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.smdc.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表Mapper接口
 * 继承Mybatis-Plus BaseMapper，提供基础CRUD
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    // 可自定义扩展方法
}