package com.sakura.tm.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 李七夜 on 2020/5/8 19:02
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
		String salt = RandomStringUtils.randomAscii(12);
		String base = Base64Utils.getEncode(plainText + salt);
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}
}
