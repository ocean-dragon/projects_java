package com.ocean.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.smdc.entity.ShopInfo;
import com.ocean.smdc.mapper.ShopInfoMapper;
import com.ocean.smdc.service.ShopInfoService;
import org.springframework.stereotype.Service;

/**
 * 店铺信息服务实现类
 * 继承Mybatis-Plus ServiceImpl，实现ShopInfoService接口
 */
@Service
public class ShopInfoServiceImpl extends ServiceImpl<ShopInfoMapper, ShopInfo> implements ShopInfoService {
    // 可扩展自定义业务方法
}