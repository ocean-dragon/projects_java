package com.ocean.smdc.controller.mini;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.User;
import com.ocean.smdc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 小程序端-用户相关接口
 */
@RestController
@RequestMapping("/mini/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 微信登录（示例，实际需对接微信API）
     */
    @PostMapping("/login")
    public Result<User> wxLogin(@RequestBody User user) {
        // 这里只做示例，实际应校验微信code并注册/登录
        User dbUser = userService.getById(user.getId());
        return Result.success(dbUser);
    }

    /**
     * 账号密码登录
     */
    @PostMapping("/account/login")
    public Result<User> accountLogin(@RequestBody User user) {
        // 示例：根据用户名密码查询
        User dbUser = userService.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword())
                .one();
        if (dbUser == null) {
            return Result.fail("用户名或密码错误");
        }
        return Result.success(dbUser);
    }

    /**
     * 获取当前用户信息（需登录）
     */
    @PostMapping("/info")
    public Result<User> getUserInfo(@RequestBody User user) {
        User dbUser = userService.getById(user.getId());
        return Result.success(dbUser);
    }

    /**
     * 根据ID获取用户信息（管理员权限）
     */
    @GetMapping("/info/{id}")
    public Result<User> getUserInfoById(@PathVariable Long id) {
        User dbUser = userService.getById(id);
        return Result.success(dbUser);
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    public Result<Boolean> updateUser(@RequestBody User user) {
        boolean updated = userService.updateById(user);
        return Result.success(updated);
    }
}