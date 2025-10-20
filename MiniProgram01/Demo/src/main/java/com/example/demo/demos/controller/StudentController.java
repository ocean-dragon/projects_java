package com.example.demo.demos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StudentController {
    // 模拟数据库
    private static final Map<String, String> STUDENT_DB = new HashMap<>();
    static {
        STUDENT_DB.put("20230001", "张三");
        STUDENT_DB.put("20230002", "李四");
        STUDENT_DB.put("20230003", "王五");
    }
    @GetMapping("/api/student/name")
    public Map<String, String> getStudentName(@RequestParam String studentId) {
        String name = STUDENT_DB.get(studentId);
        Map<String, String> result = new HashMap<>();
        if (name != null) {
            result.put("name", name);
        }
        return result;
    }
} 