package com.ocean.smdc.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 规格实体类，对应数据库specification表
 */
public class Specification implements Serializable {
    private Long id; // 主键
    private Long dishId; // 菜品id
    private String name; // 规格名称
    private BigDecimal price; // 价格
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

    // getter/setter 省略，可用IDE自动生成
}