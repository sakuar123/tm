package com.sakura.tm.common.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 李七夜
 */
public class DESUtil {
	private static Key key;
	//设置秘钥key
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";

	static {
		try {
			//生成DES算法对象
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			//运用SHA1安全策略
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			//设置上密钥种子
			secureRandom.setSeed(KEY_STR.getBytes());
			//初始化基于SHA1的算法对象
			generator.init(secureRandom);
			//生成密钥对象
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取加密的信息
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		//基于BASE64编码，接收byte[]并转换成String
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try {
			// 按UTF8编码
			byte[] bytes = str.getBytes(CHARSETNAME);
			// 获取加密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 初始化密码信息
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 加密
			byte[] doFinal = cipher.doFinal(bytes);
			// byte[]to encode好的String并返回
			return base64Encoder.encode(doFinal);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取解密之后的信息
	 * @param str
	 * @return
	 */
	public static String decrypt(String str) {
		// 基于BASE64编码，接收byte[]并转换成String
		BASE64Decoder base64decoder = new BASE64Decoder();
		try {
			// 将字符串decode成byte[]
			byte[] bytes = base64decoder.decodeBuffer(str);
			// 获取解密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 初始化解密信息
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 解密
			byte[] doFinal = cipher.doFinal(bytes);
			// 返回解密之后的信息
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getSHA256(String str) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			encodeStr = byte2Hex(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodeStr;
	}

	private static String byte2Hex(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		String temp = null;
		for (int i = 0; i < bytes.length; i++) {
			temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length() == 1) {
				//1得到一位的进行补0操作
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}

	public static void main(String[] args) {
		String str = "张三" + new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(new Date());
		System.out.println("加密的字符串:" + str);
		String encrypt = getSHA256(str);
		//aa1b825ac253cfd620b407d1992c4428f8ccf1210cf05be4638e574a0b0ab462
		System.out.println("加密后:" + encrypt);
//		String decrypt = decrypt(encrypt);
//		System.out.println("解密后:" + decrypt);
//        System.out.println(Math.round(Math.random()*1000000));
	}
}
