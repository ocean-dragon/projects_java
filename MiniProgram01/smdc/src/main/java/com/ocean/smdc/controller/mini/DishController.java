package com.ocean.smdc.controller.mini;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.Dish;
import com.ocean.smdc.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序端-菜品相关接口
 */
@RestController
@RequestMapping("/mini/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 根据分类ID获取菜品列表（路径参数）
     */
    @GetMapping("/list/{categoryId}")
    public Result<List<Dish>> listByCategory(@PathVariable Long categoryId) {
        List<Dish> list = dishService.lambdaQuery().eq(Dish::getCategoryId, categoryId).list();
        return Result.success(list);
    }

    /**
     * 根据分类ID获取菜品列表（请求参数方式）
     */
    @GetMapping("/list")
    public Result<List<Dish>> listByCategoryParam(@RequestParam Long categoryId) {
        List<Dish> list = dishService.lambdaQuery().eq(Dish::getCategoryId, categoryId).list();
        return Result.success(list);
    }

    /**
     * 获取菜品详情
     */
    @GetMapping("/detail/{id}")
    public Result<Dish> detail(@PathVariable Long id) {
        Dish dish = dishService.getById(id);
        return Result.success(dish);
    }
}