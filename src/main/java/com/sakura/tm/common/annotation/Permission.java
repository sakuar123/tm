package com.sakura.tm.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sakura.tm.common.enums.EnumPermission;

/**
 * @author 李七夜
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    //是否需要登录
    boolean noLogin() default false;

    //权限校验
    EnumPermission[] permissionEnums() default {};
}
