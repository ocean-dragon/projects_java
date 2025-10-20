package com.ocean.smdc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺信息实体类，对应数据库shop_info表
 */
public class ShopInfo implements Serializable {
    private Long id; // 主键
    private String name; // 店铺名称
    private String slogan; // 店铺标语
    private String logo; // 店铺logo
    private String address; // 店铺地址
    private String phone; // 联系电话
    private String businessHours; // 营业时间
    private String longitude; // 经度
    private String latitude; // 纬度
    private Integer status; // 状态，0关闭，1营业中
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

    // getter/setter 省略，可用IDE自动生成
}