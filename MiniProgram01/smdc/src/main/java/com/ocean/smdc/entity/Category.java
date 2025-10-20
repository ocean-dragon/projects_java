package com.ocean.smdc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜品分类实体类，对应数据库category表
 */
public class Category implements Serializable {
    private Long id; // 主键
    private String name; // 分类名称
    private Integer sort; // 排序号
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

    // getter/setter 省略，可用IDE自动生成
}