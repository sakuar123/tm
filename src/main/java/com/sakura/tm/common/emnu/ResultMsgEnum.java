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

	//=============================用户相关============================//
	USER_EXITS(135, "该手机号已经被注册了,请换个手机号注册"),
	USER_NOT_EXITS(136, "未注册，请先注册"),
	PHONE_NOT_EXITS(137, "手机号错误,请重新输入"),
	USER_NO_LOGIN(138, "请先登录"),
	USER_PWD_ERROR(139, "密码错误！"),
	USER_TOKEN_ERROR(140, "非法加密的token,请重新登录!"),
	;

	private int code;
	private String message;

}
