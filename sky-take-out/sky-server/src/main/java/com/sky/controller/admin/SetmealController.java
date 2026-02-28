package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐管理相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 添加套餐
     *
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("添加套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("add setmeal = {}", setmealDTO);
        setmealService.saveWithDishes(setmealDTO);

        return Result.success();
    }

    /**
     * 套餐分页查询
     *
     * @param queryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO queryDTO) {
        log.info("套餐分页查询 = {}", queryDTO);
        PageResult page = setmealService.pageQuery(queryDTO);

        return Result.success(page);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐 = {}", ids);
        setmealService.deleteBatch(ids);

        return Result.success();
    }
}
