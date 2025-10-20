package com.ocean.smdc.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 菜品实体类，对应数据库dish表
 */
public class Dish implements Serializable {
    private Long id; // 主键
    private String name; // 菜品名称
    private Long categoryId; // 分类id
    private BigDecimal price; // 价格
    private String image; // 图片
    private String description; // 描述信息
    private Integer status; // 售卖状态 0:停售 1:起售
    private Integer sort; // 排序号
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

    // getter/setter 省略，可用IDE自动生成
}