package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;

public interface DishService {

    /**
     * 新增菜品与口味
     *
     * @param dishDTO
     */
    public void saveWishFlavor(DishDTO dishDTO);
}
