// SmdcApplication.java
// 项目启动入口，Spring Boot主类
// 扫码点餐小程序后端服务

package com.ocean.smdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类
 * @author ocean
 */
@SpringBootApplication
public class SmdcApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmdcApplication.class, args);
    }
}
