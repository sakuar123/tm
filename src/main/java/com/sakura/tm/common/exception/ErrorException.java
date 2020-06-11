package com.sakura.tm.common.exception;


import com.sakura.tm.common.emnu.IMessageConstant;
import com.sakura.tm.common.emnu.ResultMsgEnum;
import com.sakura.tm.common.util.JsonResult;
import lombok.Data;

/**
 * Created by 李七夜 on 2020/5/13 11:07
 * 自定义的异常类
 * @author 李七夜
 */
@Data
public class ErrorException extends RuntimeException {
	private static final long serialVersionUID = -7677230804556063870L;

	private Integer code;

	private String msg;

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

	public ErrorException(IMessageConstant iMessageConstant) {
		this.code = iMessageConstant.getCode();
		this.msg = iMessageConstant.getMessage();
	}

	public ErrorException(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
