package com.ocean.smdc.controller.admin;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.TableInfo;
import com.ocean.smdc.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理后台-桌位相关接口
 */
@RestController
@RequestMapping("/admin/table")
public class TableController {
    @Autowired
    private TableInfoService tableInfoService;

    /**
     * 桌位分页查询（简单示例，实际可加分页参数）
     */
    @GetMapping("/page")
    public Result<List<TableInfo>> page() {
        List<TableInfo> list = tableInfoService.list();
        return Result.success(list);
    }

    /**
     * 添加桌位
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody TableInfo table) {
        boolean saved = tableInfoService.save(table);
        return Result.success(saved);
    }

    /**
     * 更新桌位
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody TableInfo table) {
        boolean updated = tableInfoService.updateById(table);
        return Result.success(updated);
    }

    /**
     * 删除桌位
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody TableInfo table) {
        boolean removed = tableInfoService.removeById(table.getId());
        return Result.success(removed);
    }

    /**
     * 生成桌位二维码（示例，实际应生成二维码图片）
     */
    @GetMapping("/qrcode/{id}")
    public Result<String> qrcode(@PathVariable Long id) {
        // 示例：返回二维码内容字符串
        TableInfo table = tableInfoService.getById(id);
        if (table == null) {
            return Result.fail("桌位不存在");
        }
        String qrContent = "table_code:" + table.getCode();
        return Result.success(qrContent);
    }
}