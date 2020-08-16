package com.sakura.tm.common.util;

import com.sakura.tm.common.enums.base.IMessageConstant;
import com.sakura.tm.common.exception.ErrorException;

/**
 * Created by 李七夜 on 2020/5/13 11:22
 * @author 李七夜
 */
public class Assert {

	public static void isTrue(Boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new ErrorException(message);
		}
	}

	public static void isTrue(boolean expression, IMessageConstant iMessageConstant) {
		if (!expression) {
			throw new ErrorException(iMessageConstant);
		}
	}
}
