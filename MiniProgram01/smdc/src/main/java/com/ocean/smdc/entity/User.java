package com.ocean.smdc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类，对应数据库user表
 */
public class User implements Serializable {
    private Long id; // 主键
    private String openId; // 微信openid
    private String sessionKey; // 会话密钥
    private String nickName; // 昵称
    private String username; // 用户名
    private String password; // 密码
    private String avatarUrl; // 头像
    private Integer gender; // 性别 0-未知 1-男 2-女
    private String city; // 城市
    private String province; // 省份
    private String country; // 国家
    private String phone; // 手机号
    private Date lastLoginTime; // 最后登录时间
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

    // getter/setter 省略，可用IDE自动生成
}