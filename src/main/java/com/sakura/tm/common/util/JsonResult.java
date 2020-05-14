package com.sakura.tm.common.util;


import com.sakura.tm.common.emnu.CommonsCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

/**
 * 返回给前端的Json容器工具
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	public static <T> JsonResult success() {
		return JsonResult
				.builder()
				.code(CommonsCodeEnum.success.getCode())
				.message(CommonsCodeEnum.success.getName())
				.data(true)
				.build();
	}

	public static <T> JsonResult success(T data) {
		return JsonResult
				.builder()
				.code(CommonsCodeEnum.success.getCode())
				.message(CommonsCodeEnum.success.getName())
				.data(data)
				.build();
	}

	public static JsonResult fail(Exception e) {
		return JsonResult
				.builder()
				.code(CommonsCodeEnum.fail.getCode())
				.message("系统异常")
				.data(Collections.EMPTY_MAP)
				.build();
	}

	public static JsonResult fail(String message) {
		return JsonResult
				.builder()
				.code(CommonsCodeEnum.fail.getCode())
				.message(message)
				.data(Collections.EMPTY_MAP)
				.build();
	}

	public static JsonResult fail(Throwable e) {
		return JsonResult
				.builder()
				.code(CommonsCodeEnum.fail.getCode())
				.message("系统异常")
				.data(Collections.EMPTY_MAP)
				.build();
	}
}
