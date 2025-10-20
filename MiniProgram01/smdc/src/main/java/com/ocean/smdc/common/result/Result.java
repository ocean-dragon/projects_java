package com.ocean.smdc.common.result;

/**
 * 统一响应结果封装
 * @param <T> 响应数据类型
 */
public class Result<T> {
    private int code; // 1=成功，0=失败
    private String msg; // 提示信息
    private T data; // 响应数据

    // 构造方法
    public Result() {}
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 静态工厂方法
    public static <T> Result<T> success(T data) {
        return new Result<>(1, "success", data);
    }
    public static <T> Result<T> fail(String msg) {
        return new Result<>(0, msg, null);
    }

    // getter/setter
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}