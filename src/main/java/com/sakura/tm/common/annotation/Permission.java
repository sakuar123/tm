package com.sakura.tm.common.annotation;

import com.sakura.tm.common.emnu.PermissionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 李七夜
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
	//是否需要登录
	boolean noLogin() default false;

	//权限校验
	PermissionEnum[] permissionEnums() default {};
}
