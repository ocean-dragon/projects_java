package com.ocean.smdc.controller.admin;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.Dish;
import com.ocean.smdc.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理后台-菜品相关接口
 */
@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 菜品分页查询（简单示例，实际可加分页参数）
     */
    @GetMapping("/page")
    public Result<List<Dish>> page() {
        List<Dish> list = dishService.list();
        return Result.success(list);
    }

    /**
     * 获取菜品信息
     */
    @GetMapping("/info/{id}")
    public Result<Dish> info(@PathVariable Long id) {
        Dish dish = dishService.getById(id);
        return Result.success(dish);
    }

    /**
     * 添加菜品
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody Dish dish) {
        boolean saved = dishService.save(dish);
        return Result.success(saved);
    }

    /**
     * 更新菜品
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Dish dish) {
        boolean updated = dishService.updateById(dish);
        return Result.success(updated);
    }

    /**
     * 删除菜品
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody Dish dish) {
        boolean removed = dishService.removeById(dish.getId());
        return Result.success(removed);
    }
}