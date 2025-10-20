package com.ocean.smdc.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 订单明细实体类，对应数据库order_detail表
 */
public class OrderDetail implements Serializable {
    private Long id; // 主键
    private Long orderId; // 订单id
    private Long dishId; // 菜品id
    private Long specificationId; // 规格id
    private Integer number; // 数量
    private BigDecimal amount; // 金额
    private Date createTime; // 创建时间

    // getter/setter 省略，可用IDE自动生成
}