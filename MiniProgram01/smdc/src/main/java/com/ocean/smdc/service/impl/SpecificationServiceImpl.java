package com.ocean.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.smdc.entity.Specification;
import com.ocean.smdc.mapper.SpecificationMapper;
import com.ocean.smdc.service.SpecificationService;
import org.springframework.stereotype.Service;

/**
 * 规格服务实现类
 * 继承Mybatis-Plus ServiceImpl，实现SpecificationService接口
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper, Specification> implements SpecificationService {
    // 可扩展自定义业务方法
}