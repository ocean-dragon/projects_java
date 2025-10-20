package com.ocean.smdc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 桌位实体类，对应数据库table_info表
 */
public class TableInfo implements Serializable {
    private Long id; // 主键
    private String name; // 桌位名称
    private String code; // 桌位二维码
    private Integer status; // 状态 0:空闲 1:使用中
    private Integer capacity; // 容纳人数
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

    // getter/setter 省略，可用IDE自动生成
}