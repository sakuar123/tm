package com.sakura.tm.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class CommonsUtil {

	/**
	 * 获取ticketID
	 * @return ticketID
	 */
	public static String getTicketid() {
		return UUID.randomUUID().toString().toLowerCase() + getRandomNumber(0, 999);
	}

	/**
	 * 获取一个随机数
	 * @param min
	 * @param max
	 * @return
	 */
	private static int getRandomNumber(int min, int max) {
		Random rd = new Random();
		int ret = rd.nextInt(max);
		while (ret < min)
			ret += rd.nextInt(max - min);
		return ret;
	}

	public static boolean isBlank(Object o) {
		if (o == null) {
			return true;
		}
		if (o instanceof Optional) {
			return !((Optional) o).isPresent();
		}
		// 字符串
		if (o instanceof CharSequence) {
			return StringUtils.isBlank((CharSequence) o);
		}
		// 数组
		if (o.getClass().isArray()) {
			return Array.getLength(o) == 0;
		}
		// 集合
		if (o instanceof Collection) {
			return ((Collection) o).isEmpty();
		}
		// Map
		if (o instanceof Map) {
			return ((Map) o).isEmpty();
		}
		return false;
	}

	public static boolean isNotBlank(Object o) {
		return !isBlank(o);
	}

	public static void main(String[] args) {
		System.out.println(RandomStringUtils.random(15, false, true));
	}

	/**
	 * 获取指定长度的纯随机数
	 * @param length
	 * @return
	 */
	public static Integer getRandomNumber(int length) {
		return Integer.parseInt(RandomStringUtils.random(length, false, true));
	}

	/**
	 * 获取一个随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		return RandomStringUtils.random(length, true, true);
	}

	/**
	 * 获取ip地址
	 * @return
	 */
	public static String getLocalIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}

}
