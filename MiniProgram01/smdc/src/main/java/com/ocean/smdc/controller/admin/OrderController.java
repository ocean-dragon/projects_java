package com.ocean.smdc.controller.admin;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.Order;
import com.ocean.smdc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理后台-订单相关接口
 */
@RestController
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 订单分页查询（简单示例，实际可加分页参数）
     */
    @GetMapping("/page")
    public Result<List<Order>> page() {
        List<Order> list = orderService.list();
        return Result.success(list);
    }

    /**
     * 订单详情
     */
    @GetMapping("/detail/{id}")
    public Result<Order> detail(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return Result.success(order);
    }

    /**
     * 接单
     */
    @PostMapping("/accept")
    public Result<Boolean> accept(@RequestBody Order order) {
        order.setStatus(3); // 3=待上菜
        boolean updated = orderService.updateById(order);
        return Result.success(updated);
    }

    /**
     * 完成订单
     */
    @PostMapping("/complete")
    public Result<Boolean> complete(@RequestBody Order order) {
        order.setStatus(4); // 4=已完成
        boolean updated = orderService.updateById(order);
        return Result.success(updated);
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    public Result<Boolean> cancel(@RequestBody Order order) {
        order.setStatus(5); // 5=已取消
        boolean updated = orderService.updateById(order);
        return Result.success(updated);
    }
}