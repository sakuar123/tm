package com.sakura.tm.config.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 目标数据源注解，注解在方法上指定数据源的名称
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/16 10:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSource {

	//此处接收数据源名称
	String value();
}
