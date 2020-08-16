package com.sakura.tm.common.enums;

import lombok.Getter;

/**
 * Created by 李七夜 on 2020/5/14 15:43
 */
@Getter
public enum EnumPermission {
	;

	/**
	 * 编号
	 */
	private Integer cold;
	/**
	 * 权限签名
	 */
	private String sign;
	/**
	 * 权限描述
	 */
	private String desc;
}
