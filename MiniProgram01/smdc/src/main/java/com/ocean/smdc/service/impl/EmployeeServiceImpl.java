package com.ocean.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.smdc.entity.Employee;
import com.ocean.smdc.mapper.EmployeeMapper;
import com.ocean.smdc.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * 员工服务实现类
 * 继承Mybatis-Plus ServiceImpl，实现EmployeeService接口
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    // 可扩展自定义业务方法
}