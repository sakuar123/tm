package com.sakura.tm.config.handler;

import com.sakura.tm.common.exception.ErrorException;
import com.sakura.tm.common.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理全局的异常,目前只支持Exception和Throwable
 * 以后可能会加
 * 新增支持ErrorException
 * @author 李七夜
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理所有Exception异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JsonResult<Object> handleException(Exception e) {
		log.error(e.getMessage());
		return JsonResult.fail("系统异常");
	}

	/**
	 * 处理所有Throwable异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public JsonResult<Object> handleException(Throwable e) {
		log.error(e.getMessage());
		return JsonResult.fail("系统异常");
	}

	@ExceptionHandler(ErrorException.class)
	@ResponseBody
	public JsonResult<Object> errorException(ErrorException e) {
		log.error(e.getMessage());
		return JsonResult.of(e.getCode(), e.getMsg());
	}
}
