package com.ocean.smdc.controller.mini;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.Category;
import com.ocean.smdc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序端-分类相关接口
 */
@RestController
@RequestMapping("/mini/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表
     */
    @GetMapping("/list")
    public Result<List<Category>> list() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }
}