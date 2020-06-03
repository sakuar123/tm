package com.sakura.tm.common.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by 李七夜 on 2020/5/22 10:06
 */
public class Base64Utils {
	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * 双重base64解密
	 *
	 * @return String
	 */
	public static String getDecodeDouble(String s) {
		try {
			byte[] decode = Base64.decodeBase64(Base64.decodeBase64(s));
			return new String(decode, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * base64解密一次
	 *
	 * @return String
	 */
	public static String getDecode(String s) {
		try {
			byte[] decode = Base64.decodeBase64(s);
			return new String(decode, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 双重base64加密
	 *
	 * @return String
	 */
	public static String getEncode(String s) {
		try {
			byte[] encode = Base64.encodeBase64(Base64.encodeBase64(s.getBytes()));
			return new String(encode, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static void main(String[] args) {
		String str = "1";
		char [] charArray = "1".toCharArray();
		System.out.println(MD5Util.getMd5(str).substring(0,5));
//		System.out.println(("1"));
	}
}
