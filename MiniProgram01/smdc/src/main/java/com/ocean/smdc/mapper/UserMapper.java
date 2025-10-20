package com.ocean.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.smdc.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表Mapper接口
 * 继承Mybatis-Plus BaseMapper，提供基础CRUD
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 可自定义扩展方法
}