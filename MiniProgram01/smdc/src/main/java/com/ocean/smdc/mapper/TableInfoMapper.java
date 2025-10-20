package com.ocean.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.smdc.entity.TableInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 桌位表Mapper接口
 * 继承Mybatis-Plus BaseMapper，提供基础CRUD
 */
@Mapper
public interface TableInfoMapper extends BaseMapper<TableInfo> {
    // 可自定义扩展方法
}