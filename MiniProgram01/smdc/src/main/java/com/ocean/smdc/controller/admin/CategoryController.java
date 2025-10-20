package com.ocean.smdc.controller.admin;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.Category;
import com.ocean.smdc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理后台-分类相关接口
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分类分页查询（简单示例，实际可加分页参数）
     */
    @GetMapping("/page")
    public Result<List<Category>> page() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    /**
     * 分类列表查询
     */
    @GetMapping("/list")
    public Result<List<Category>> list() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    /**
     * 添加分类
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody Category category) {
        boolean saved = categoryService.save(category);
        return Result.success(saved);
    }

    /**
     * 更新分类
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Category category) {
        boolean updated = categoryService.updateById(category);
        return Result.success(updated);
    }

    /**
     * 删除分类
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody Category category) {
        boolean removed = categoryService.removeById(category.getId());
        return Result.success(removed);
    }
}