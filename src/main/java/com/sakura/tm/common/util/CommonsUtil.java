package com.sakura.tm.common.util;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class CommonsUtil {


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

	/**
	 * 判断对象是否为空,为空返回true,非空为false
	 * @param o
	 * @return
	 */
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

    /**
     * 判断对象是否不为空,为空返回false,不为空返回true
	 * @param o
     * @return
     */
	public static boolean isNotBlank(Object o) {
		return !isBlank(o);
	}

	public static void main(String[] args) {

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
		byte[] ipAddr = Objects.requireNonNull(addr).getAddress();
		StringBuilder ipAddrStr = new StringBuilder();
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr.append(".");
			}
			ipAddrStr.append(ipAddr[i] & 0xFF);
		}
		return ipAddrStr.toString();
	}

	/**
	 * 通过身份证号获取身份信息
	 * 出生日期,性别
	 * @param certificateNo
	 * @return
	 */
	public static PageData getIdentityInfoByIdCard(String certificateNo) {
		String birthday = "";
		String age = "";
		String sexCode = "";

		int year = Calendar.getInstance().get(Calendar.YEAR);
		char[] number = certificateNo.toCharArray();
		boolean flag = true;
		if (number.length == 15) {
			for (int x = 0; x < number.length; x++) {
				if (!flag) return new PageData();
				flag = Character.isDigit(number[x]);
			}
		} else if (number.length == 18) {
			for (int x = 0; x < number.length - 1; x++) {
				if (!flag) return new PageData();
				flag = Character.isDigit(number[x]);
			}
		}
		if (flag && certificateNo.length() == 15) {
			birthday = "19" + certificateNo.substring(6, 8) + "-"
					+ certificateNo.substring(8, 10) + "-"
					+ certificateNo.substring(10, 12);
			sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "F" : "M";
			age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
		} else if (flag && certificateNo.length() == 18) {
			birthday = certificateNo.substring(6, 10) + "-"
					+ certificateNo.substring(10, 12) + "-"
					+ certificateNo.substring(12, 14);
			sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "F" : "M";
			age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
		}
		PageData pd = new PageData();
		pd.put("birthday", birthday);
		pd.put("age", age);
		pd.put("sexCode", sexCode);
		return pd;
	}

	public static String parseString(String src) {
		if (src == null || "null".equalsIgnoreCase(src)) {
			return "";
		} else {
			return src.trim();
		}
	}

	public static int parseInt(String s) {
		return parseInt(s, 0);
	}

	public static int parseInt(String s, int defaultValue) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static long parseLong(String s) {
		return parseLong(s, -1l);
	}

	public static long parseLong(String s, long defaultValue) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Double parseDouble(String value) {
		return parseDouble(value, 0.0);
	}

	public static double parseDouble(String s, Double defaultValue) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

    /**
     * 获取一段随机字符串
	 * @return
     */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
