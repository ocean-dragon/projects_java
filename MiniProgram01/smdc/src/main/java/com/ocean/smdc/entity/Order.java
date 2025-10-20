package com.ocean.smdc.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 订单实体类，对应数据库order表
 */
public class Order implements Serializable {
    private Long id; // 主键
    private String number; // 订单号
    private Long userId; // 用户id
    private Long tableId; // 桌位id
    private BigDecimal amount; // 总金额
    private Integer status; // 订单状态 1:待付款 2:待接单 3:待上菜 4:已完成 5:已取消
    private Integer payMethod; // 支付方式 1:微信支付 2:支付宝支付
    private Integer payStatus; // 支付状态 0:未支付 1:已支付
    private String remark; // 备注
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

    // getter/setter 省略，可用IDE自动生成
}