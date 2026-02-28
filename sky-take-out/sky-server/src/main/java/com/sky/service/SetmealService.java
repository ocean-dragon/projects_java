package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealService {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param setmealDTO
     */
    public void saveWithDishes(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     *
     * @param queryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO queryDTO);

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
