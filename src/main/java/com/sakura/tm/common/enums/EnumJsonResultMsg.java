package com.sakura.tm.common.enums;

import com.sakura.tm.common.enums.base.IMessageConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 李七夜
 * JsonResult出去的状态码
 */
@Getter
@AllArgsConstructor
public enum EnumJsonResultMsg implements IMessageConstant {

    //正常响应的状态码
    OK(200, "ok"),
    //发生异常响应的状态码
    ERROR(500, "error"),

    //==================代表用户行为的状态码======================//
    USER_NOT_LOGIN(101, "请先登录!"),
    USER_NAME_OR_PWD_ERROR(102, "用户名或密码错误!"),
    USET_NO_AUTHOR(103, "无权限!"),
    USER_BAD(105, "用户不存在或已被冻结"),
    USER_EXITS(106, "该手机号已经被注册了,请换个手机号注册"),
    USER_NOT_EXITS(107, "未注册，请先注册"),
    USER_TOKEN_ERR(108, "非法的token,请重新登录!"),
    ;
    private int code;
    private String message;
}
