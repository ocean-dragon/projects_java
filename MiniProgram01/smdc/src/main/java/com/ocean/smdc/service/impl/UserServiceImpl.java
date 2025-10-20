package com.ocean.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.smdc.entity.User;
import com.ocean.smdc.mapper.UserMapper;
import com.ocean.smdc.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * 继承Mybatis-Plus ServiceImpl，实现UserService接口
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 可扩展自定义业务方法
}