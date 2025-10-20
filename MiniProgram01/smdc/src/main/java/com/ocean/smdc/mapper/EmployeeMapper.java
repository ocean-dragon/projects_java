package com.ocean.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.smdc.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工表Mapper接口
 * 继承Mybatis-Plus BaseMapper，提供基础CRUD
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    // 可自定义扩展方法
}