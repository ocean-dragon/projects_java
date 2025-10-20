package com.ocean.smdc.controller.admin;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.ShopInfo;
import com.ocean.smdc.service.ShopInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理后台-店铺信息相关接口
 */
@RestController
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    private ShopInfoService shopInfoService;

    /**
     * 获取店铺信息（默认取第一个）
     */
    @GetMapping("/info")
    public Result<ShopInfo> getInfo() {
        ShopInfo shop = shopInfoService.lambdaQuery().last("limit 1").one();
        return Result.success(shop);
    }

    /**
     * 更新店铺信息
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody ShopInfo shopInfo) {
        boolean updated = shopInfoService.updateById(shopInfo);
        return Result.success(updated);
    }
}