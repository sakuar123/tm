package com.sakura.tm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/17 14:21
 * 拦截器内置参数
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EnumInterceptorDefineParams {
	USER_ID("1", "userId"),
	;
	private String cold;
	private String name;
}
