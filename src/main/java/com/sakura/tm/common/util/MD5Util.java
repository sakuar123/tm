package com.sakura.tm.common.util;

import org.springframework.util.DigestUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by 李七夜 on 2020/5/8 19:02
 * @author 李七夜
 */
@Slf4j
public class MD5Util {

	/**
	 * 生成md5,不可逆操作
	 * @param plainText
	 * @return
	 */
	public static String getMd5(String plainText) {
		////生成从ASCII 32到126组成的随机字符串 （包括符号）
//		String salt = RandomStringUtils.randomAscii(12);
		String base = Base64Utils.getEncode(plainText);
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}

	/**
	 * 可带入盐的MD5加密
	 * @param plainText
	 * @param salt
	 * @return
	 */
	public static String getMd5(String plainText, String salt) {
		String base = Base64Utils.getEncode(plainText + salt);
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}
}
