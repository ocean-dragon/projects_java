package com.ocean.smdc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.smdc.entity.ShopInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 店铺信息表Mapper接口
 * 继承Mybatis-Plus BaseMapper，提供基础CRUD
 */
@Mapper
public interface ShopInfoMapper extends BaseMapper<ShopInfo> {
    // 可自定义扩展方法
}