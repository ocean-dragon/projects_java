package com.ocean.smdc.common.exception;

import com.ocean.smdc.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，统一处理所有异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理所有异常，返回统一响应结构
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 可根据需要打印日志
        return Result.fail(e.getMessage());
    }
}