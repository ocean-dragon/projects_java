package com.ocean.smdc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工实体类，对应数据库employee表
 */
public class Employee implements Serializable {
    private Long id; // 主键
    private String username; // 用户名
    private String password; // 密码
    private String name; // 姓名
    private String phone; // 手机号
    private Integer role; // 角色，1管理员，2普通员工
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

    // getter/setter 省略，可用IDE自动生成
}