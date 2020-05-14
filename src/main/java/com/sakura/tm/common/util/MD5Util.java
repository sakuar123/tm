package com.sakura.tm.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 李七夜 on 2020/5/8 19:02
 */
@Slf4j
public class MD5Util {
	//盐，用于混交md5
	private static final String slat = "&%5123***&&%%$$#@";

	/**
	 * 生成md5
	 * @param plainText
	 * @return
	 */
	public static String getMD5(String plainText) {
		String base = plainText + "/" + slat;
		StringBuffer result = new StringBuffer();
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(base.getBytes("UTF8"));
			byte s[]=m.digest();
			for (int i = 0; i < s.length; i++) {
				result.append(Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6));
			}
			return result.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("MD5加密时异常:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error("MD5加密时异常:" + e.getMessage());
		}
		Assert.isNull(result, "MD5加密异常");
		return null;
	}
}
