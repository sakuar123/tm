package com.sakura.tm.common.emnu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/2 11:24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultMsgEnum implements IMessageConstant {

	USER_EXITS(135, "该手机号已经被注册了,请换个手机号注册");

	private int code;
	private String message;

}