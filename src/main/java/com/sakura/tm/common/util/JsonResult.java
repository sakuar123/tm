package com.sakura.tm.common.util;


import com.alibaba.fastjson.JSON;
import com.sakura.tm.common.emnu.CommonsCodeEnum;
import com.sakura.tm.common.emnu.IMessageConstant;
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

    public static JsonResult success() {
        return of(CommonsCodeEnum.success.getCode(), CommonsCodeEnum.success.getName(), true);
    }

    public static <T> JsonResult success(T data) {
        return of(CommonsCodeEnum.success.getCode(), CommonsCodeEnum.success.getName(), data);
    }

    public static JsonResult fail(Exception e) {
        log.error(e.getMessage());
        return of(CommonsCodeEnum.fail.getCode(), "系统异常");
    }

    public static JsonResult result(IMessageConstant iMessageConstant) {
        return of(iMessageConstant.getCode(), iMessageConstant.getMessage());
    }

    public static JsonResult fail(String message) {
        return of(CommonsCodeEnum.fail.getCode(), message);
    }

    public static JsonResult fail(Throwable e) {
        log.error(e.getMessage());
        return of(CommonsCodeEnum.fail.getCode(), "系统异常");
    }

    public static JsonResult fail(ErrorException e) {
        return of(e.getCode(), e.getMessage());
    }

    public static JsonResult of(Integer code, String message) {
        return of(code, message, null);
    }

    public static JsonResult of(Integer code, String message, Object data) {
        return JsonResult.builder().data(data).message(message).code(code).build();
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}
