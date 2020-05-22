package com.sakura.tm.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Created by 李七夜 on 2020/5/20 15:00
 * 日期工具类
 * @author 李七夜
 */
@Slf4j
public class DateUtils {

	public static final String DAY_DATE_PATTERN = "yyyy-MM-dd";
	public static final String YEAR_MOTHON = "yyyy-MM";
	public static final String SECOND_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String YEARMOTHONDAY = "yyyyMMdd";

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat(DAY_DATE_PATTERN);
	private final static SimpleDateFormat sdfDaytwo = new SimpleDateFormat(DAY_DATE_PATTERN);
	private final static SimpleDateFormat sdfYearMonthtwo = new SimpleDateFormat(YEAR_MOTHON);
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat(YEARMOTHONDAY);
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat(SECOND_DATE_PATTERN);
	private final static SimpleDateFormat fullTime = new SimpleDateFormat("yyyyMMddHHmmss");
	private final static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");


	/**
	 * 获取当前时间
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNow() {
		return new SimpleDateFormat(SECOND_DATE_PATTERN).format(new Date());
	}

	public static String getDay() {
		return new SimpleDateFormat(DAY_DATE_PATTERN).format(new Date());
	}

	public static Date parseDate(Long timstamp) {
		return new Date(timstamp);
	}

	/**
	 * 将 yyyyMMdd/yyyy/MM/dd这种String格式的日期,统一转换成yyyy-MM-dd格式
	 * @return
	 */
	public static String parseStringDate(String date) {
		Long timstamp = transDateToTimeMillis(date);
		if (CommonsUtil.isNotBlank(timstamp) && timstamp <= 0) {
			try {
				timstamp = (new SimpleDateFormat("yyyyMMdd")).parse(date, new ParsePosition(0)).getTime();
			} catch (Exception exception) {
				try {
					timstamp = (new SimpleDateFormat("yyyy/MM/dd")).parse(date, new ParsePosition(0)).getTime();
				} catch (Exception e) {
					timstamp = System.currentTimeMillis();
				}
			}
		}
		return new SimpleDateFormat(DAY_DATE_PATTERN).format(parseDate(timstamp));
	}

	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取时间,格式为yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String parseDate(Date date) {
		try {
			return sdfDay.format(date);
		} catch (Exception e) {
			log.error("格式化时间异常:" + e.getMessage());
			return null;
		}
	}

	/**
	 * 获取时间,格式为yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		try {
			return sdfDay.parse(date);
		} catch (Exception e) {
			log.error("格式化时间异常:" + e.getMessage());
			return null;
		}
	}

	public static String formatDate(String date) {
		try {
			SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
			Date date1 = sf1.parse(date);
			return parseDate(date1);
		} catch (Exception e) {
			log.error("格式化时间异常:" + e.getMessage());
			return null;
		}
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	public static String getTime(long timestamp) {
		return sdfTime.format(new Date(timestamp));
	}

	/**
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 校验日期是否合法
	 * @return
	 */
	public static boolean isValidDate(String s) {
		Pattern pattern = compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[13579][26])00))-02-29)");
		Matcher matcher = pattern.matcher(s);
		try {
			if (matcher.find()) {
				return transDateToTimeMillis(s) <= System.currentTimeMillis();
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static long transDateToTimeMillis(String date) {
		try {
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(date, new ParsePosition(0)).getTime();
		} catch (Exception e) {
			try {
				return (new SimpleDateFormat("yyyy-MM-dd")).parse(date, new ParsePosition(0)).getTime();
			} catch (Exception ex) {
			}
		}
		return 0;
	}


	public static Date getDayBegin() {
		return getDayBegin(new Date());
	}

	/**
	 * 获取指定日期当天开始时间
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		Date tempDate = date;
		tempDate = org.apache.commons.lang3.time.DateUtils.setHours(tempDate, 0);
		tempDate = org.apache.commons.lang3.time.DateUtils.setMinutes(tempDate, 0);
		tempDate = org.apache.commons.lang3.time.DateUtils.setSeconds(tempDate, 0);
		tempDate = org.apache.commons.lang3.time.DateUtils.setMilliseconds(tempDate, 0);
		return tempDate;
	}

	public static Date getNowYearBegin(Date date) {
		Date tempDate = getNowMonthBegin(date);
		tempDate = org.apache.commons.lang3.time.DateUtils.setMonths(tempDate, 0);
		return tempDate;
	}

	public static Date getNowYearBegin() {
		return getNowYearBegin(new Date());
	}

	public static Date getNowYearEnd() {
		return getNowYearEnd(new Date());
	}

	public static Date getNowYearEnd(Date date) {
		Date tempDate = getNowYearBegin(date);
		tempDate = org.apache.commons.lang3.time.DateUtils.addYears(tempDate, 1);
		tempDate = org.apache.commons.lang3.time.DateUtils.addDays(tempDate, -1);
		return tempDate;
	}

	public static Date getNowMonthBegin() {
		return getNowMonthBegin(new Date());
	}

	public static Date getNowMonthBegin(Date date) {
		Date tempDate = getDayBegin(date);
		tempDate = org.apache.commons.lang3.time.DateUtils.setDays(tempDate, 1);
		return tempDate;
	}

	public static Date getMonthEndDay(Date date) {
		Date tempDate = getNowMonthBegin(date);
		tempDate = org.apache.commons.lang3.time.DateUtils.addMonths(tempDate, 1);
		tempDate = org.apache.commons.lang3.time.DateUtils.addDays(tempDate, -1);
		return tempDate;
	}

	public static Date getMonthEndDay() {
		return getMonthEndDay(new Date());
	}

	public static boolean isWorkDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return day != Calendar.SATURDAY && day != Calendar.SUNDAY;
	}

	public static LocalDateTime toLocalDateTime(Date date) {
		if (date instanceof java.sql.Date) {
			date = new Date(date.getTime());
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}


}
