package com.sakura.tm.common.util;


import com.alibaba.fastjson.JSON;
import com.sakura.tm.common.enums.EnumJsonResultMsg;
import com.sakura.tm.common.enums.base.IMessageConstant;
import com.sakura.tm.common.exception.ErrorException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 返回给前端的Json容器工具
 *
 * @author 李七夜
 */
@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T> {

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static <T> JsonResult<T> success() {
        return of(EnumJsonResultMsg.OK.getCode(), "success", null);
    }

    public static <T> JsonResult<T> success(T o) {
        return of(EnumJsonResultMsg.OK.getCode(), "success", o);
    }

    public static <T> JsonResult<T> fail() {
        return of(EnumJsonResultMsg.ERROR.getCode(), EnumJsonResultMsg.ERROR.getMessage(), null);
    }

    public static <T> JsonResult<T> fail(String message) {
        return of(EnumJsonResultMsg.ERROR.getCode(), message, null);
    }

    public static <T> JsonResult<T> fail(Integer code, String message) {
        return of(code, message, null);
    }

    public static <T> JsonResult<T> of(ErrorException e) {
        return of(e.getCode(), e.getMsg(), null);
    }

    public static <T> JsonResult<T> of(IMessageConstant e) {
        return of(e.getCode(), e.getMessage(), null);
    }

    public static <T> JsonResult<T> of(Integer code, String msg) {
        return of(code, msg, null);
    }

    private static <T> JsonResult<T> of(Integer code, String msg, T data) {
        return JsonResult.<T>builder().code(code).message(msg).data(data).build();
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
