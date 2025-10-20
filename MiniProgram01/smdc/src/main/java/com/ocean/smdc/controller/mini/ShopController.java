package com.ocean.smdc.controller.mini;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.ShopInfo;
import com.ocean.smdc.service.ShopInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序端-店铺信息相关接口
 */
@RestController
@RequestMapping("/mini/shop")
public class ShopController {
    @Autowired
    private ShopInfoService shopInfoService;

    /**
     * 获取店铺信息（默认取第一个）
     */
    @PostMapping("/info")
    public Result<ShopInfo> getShopInfo() {
        ShopInfo shop = shopInfoService.lambdaQuery().last("limit 1").one();
        return Result.success(shop);
    }
}