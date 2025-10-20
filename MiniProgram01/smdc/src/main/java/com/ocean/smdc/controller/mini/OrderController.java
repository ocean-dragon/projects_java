package com.ocean.smdc.controller.mini;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.Order;
import com.ocean.smdc.entity.TableInfo;
import com.ocean.smdc.service.OrderService;
import com.ocean.smdc.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序端-订单相关接口
 */
@RestController
@RequestMapping("/mini/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private TableInfoService tableInfoService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<Boolean> createOrder(@RequestBody Order order) {
        boolean saved = orderService.save(order);
        return Result.success(saved);
    }

    /**
     * 支付订单（示例，实际需对接支付平台）
     */
    @PostMapping("/pay")
    public Result<Boolean> payOrder(@RequestBody Order order) {
        // 示例：直接修改支付状态
        order.setPayStatus(1);
        boolean updated = orderService.updateById(order);
        return Result.success(updated);
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    public Result<Boolean> cancelOrder(@RequestBody Order order) {
        order.setStatus(5); // 5=已取消
        boolean updated = orderService.updateById(order);
        return Result.success(updated);
    }

    /**
     * 订单列表（可根据用户ID查询）
     */
    @GetMapping("/list")
    public Result<List<Order>> list(@RequestParam Long userId) {
        List<Order> list = orderService.lambdaQuery().eq(Order::getUserId, userId).list();
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
     * 扫码获取桌位信息
     */
    @GetMapping("/table/{code}")
    public Result<TableInfo> getTableByCode(@PathVariable String code) {
        TableInfo table = tableInfoService.lambdaQuery().eq(TableInfo::getCode, code).one();
        return Result.success(table);
    }
}