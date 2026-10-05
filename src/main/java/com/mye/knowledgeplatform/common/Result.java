package com.mye.knowledgeplatform.common;

import lombok.Data;

/**
 * 全局统一响应结果类
 * 作用：规范所有接口的返回格式，让前端能够统一处理成功和失败
 */
@Data
public class Result<T> {
    private Integer code; // 状态码：200成功，500失败
    private String msg;   // 提示信息
    private T data;       // 返回的具体数据

    // 成功时的通用返回（无数据）
    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = msg;
        return result;
    }

    // 成功时的通用返回（带数据）
    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = msg;
        result.data = data;
        return result;
    }

    // 失败时的通用返回
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.code = 500;
        result.msg = msg;
        return result;
    }
}