package com.sakura.tm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/2 11:31
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EnumState {
	USE("1", "启用"),
	NOTUSED("-1", "禁用"),
	;
	private String cold;
	private String desc;

	public Integer getIntValue() {
		return Integer.parseInt(cold);
	}
}
