package com.ocean.smdc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.smdc.entity.TableInfo;
import com.ocean.smdc.mapper.TableInfoMapper;
import com.ocean.smdc.service.TableInfoService;
import org.springframework.stereotype.Service;

/**
 * 桌位服务实现类
 * 继承Mybatis-Plus ServiceImpl，实现TableInfoService接口
 */
@Service
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper, TableInfo> implements TableInfoService {
    // 可扩展自定义业务方法
}