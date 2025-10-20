package com.ocean.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.smdc.entity.Specification;
import org.apache.ibatis.annotations.Mapper;

/**
 * 规格表Mapper接口
 * 继承Mybatis-Plus BaseMapper，提供基础CRUD
 */
@Mapper
public interface SpecificationMapper extends BaseMapper<Specification> {
    // 可自定义扩展方法
}