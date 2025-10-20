package com.ocean.smdc.controller.admin;

import com.ocean.smdc.common.result.Result;
import com.ocean.smdc.entity.Employee;
import com.ocean.smdc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理后台-员工相关接口
 */
@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     */
    @PostMapping("/login")
    public Result<Employee> login(@RequestBody Employee employee) {
        Employee dbEmp = employeeService.lambdaQuery()
                .eq(Employee::getUsername, employee.getUsername())
                .eq(Employee::getPassword, employee.getPassword())
                .one();
        if (dbEmp == null) {
            return Result.fail("用户名或密码错误");
        }
        return Result.success(dbEmp);
    }

    /**
     * 获取员工信息
     */
    @GetMapping("/info/{id}")
    public Result<Employee> getInfo(@PathVariable Long id) {
        Employee emp = employeeService.getById(id);
        return Result.success(emp);
    }

    /**
     * 员工分页查询（简单示例，实际可加分页参数）
     */
    @GetMapping("/page")
    public Result<List<Employee>> page() {
        List<Employee> list = employeeService.list();
        return Result.success(list);
    }

    /**
     * 添加员工
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody Employee employee) {
        boolean saved = employeeService.save(employee);
        return Result.success(saved);
    }

    /**
     * 更新员工信息
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody Employee employee) {
        boolean updated = employeeService.updateById(employee);
        return Result.success(updated);
    }
}