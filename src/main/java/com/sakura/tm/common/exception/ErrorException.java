package com.sakura.tm.common.exception;


import com.sakura.tm.common.util.JsonResult;

/**
 * Created by 李七夜 on 2020/5/13 11:07
 * 自定义的异常类
 * @author 李七夜
 */
public class ErrorException extends RuntimeException {
	private static final long serialVersionUID = -7677230804556063870L;

	private JsonResult jsonResult;

	public ErrorException() {
		super();
	}

	public ErrorException(JsonResult jsonResult) {
		super(jsonResult.getMessage());
		this.jsonResult = jsonResult;
	}

	public ErrorException(String message) {
		super(message);
		this.jsonResult = JsonResult.fail(message);
	}
}
