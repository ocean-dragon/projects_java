package com.ocean.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.smdc.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单明细表Mapper接口
 * 继承Mybatis-Plus BaseMapper，提供基础CRUD
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    // 可自定义扩展方法
}