package com.sakura.tm.common.util;

import static java.util.regex.Pattern.compile;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by 李七夜 on 2020/5/20 15:00
 * 日期工具类
 *
 * @author 李七夜
 */
@Slf4j
public class DateUtils {

    private static final String EOORE_MESSAGE = "格式化时间异常";

    /** 锁对象 */
    private static final Object lockObj = new Object();

    /** 存放不同的日期模板格式的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = Maps.newHashMap();

    public static final String DAY_DATE_PATTERN = "yyyy-MM-dd";
    public static final String HOUR_SECOND_MM = "HH:mm:ss";
    public static final String YEAR_MOTHON = "yyyy-MM";
    public static final String SECOND_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String SECONDDATEPATTERN = "yyyyMMddHHmmss";
    public static final String YEARMOTHONDAY = "yyyyMMdd";
    public static final String YEAR_MOTHON_DAY = "yyyy/MM/dd";
    public static final String E_M_D_H_M_S_Z_Y = "EEE MMM dd hh:mm:ss z yyyy";

	private static SimpleDateFormat getSdf(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
		if (tl == null) {
			synchronized (lockObj) {
				tl = sdfMap.getOrDefault(pattern, null);
				if (tl == null) {
					// 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
					log.warn(StringUtils.join("put new sdf of pattern ", pattern, " to map"));
					// 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
					tl = ThreadLocal.withInitial(() -> {
						log.warn(StringUtils.join("thread: ", Thread.currentThread(), " init pattern: ", pattern));
						return new SimpleDateFormat(pattern);
					});
					sdfMap.put(pattern, tl);
				}
			}
		}
		return tl.get();
	}

    public static void main(String[] args) {
    	log.error(parseDate(new Date()));
    }

    /**
     * 获取当前时间
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getNow() {
        return getSdf(SECOND_DATE_PATTERN).format(new Date());
    }

    public static String getDay() {
        return getSdf(DAY_DATE_PATTERN).format(new Date());
    }

    private static Date parseDate(Long timstamp) {
        return new Date(timstamp);
    }

    /**
     * 将 yyyyMMdd/yyyy/MM/dd这种String格式的日期,统一转换成yyyy-MM-dd格式
     */
    public static String parseStringDate(String date) {
        long timstamp = transDateToTimeMillis(date);
        if (CommonsUtil.isNotBlank(timstamp) && timstamp <= 0) {
            try {
                timstamp = getSdf(YEARMOTHONDAY).parse(date, new ParsePosition(0)).getTime();
            } catch (Exception exception) {
                try {
                    timstamp = getSdf(YEAR_MOTHON_DAY).parse(date, new ParsePosition(0)).getTime();
                } catch (Exception e) {
                    timstamp = System.currentTimeMillis();
                }
            }
        }
        return getSdf(DAY_DATE_PATTERN).format(parseDate(timstamp));
    }

    public static String getDays() {
        return getSdf(YEARMOTHONDAY).format(new Date());
    }

    /**
     * 获取时间,格式为yyyy-MM-dd
     */
    public static String parseDate(Date date) {
        try {
            return getSdf(DAY_DATE_PATTERN).format(date);
        } catch (Exception e) {
            log.error(StringUtils.join(EOORE_MESSAGE, e.getMessage()));
            return null;
        }
    }

    /**
     * 获取时间,格式为yyyy-MM-dd
     */
    public static Date parseDate(String date) {
        try {
            return getSdf(DAY_DATE_PATTERN).parse(date);
        } catch (Exception e) {
            log.error(StringUtils.join(EOORE_MESSAGE, e.getMessage()));
            return null;
        }
    }

    public static String formatDate(String date) {
        try {
            SimpleDateFormat sf1 = new SimpleDateFormat(E_M_D_H_M_S_Z_Y, Locale.ENGLISH);
            Date date1 = sf1.parse(date);
            return parseDate(date1);
        } catch (Exception e) {
            log.error(StringUtils.join(EOORE_MESSAGE, e.getMessage()));
            return null;
        }
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     */
    public static String getTime() {
        return getSdf(SECOND_DATE_PATTERN).format(new Date());
    }

    public static String getTime(long timestamp) {
        return getSdf(SECOND_DATE_PATTERN).format(new Date(timestamp));
    }

    /**
     * @return boolean
     */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return Objects.requireNonNull(parseDate(s)).getTime() >= Objects.requireNonNull(parseDate(e)).getTime();
	}

    /**
     * 校验日期是否合法
     */
    public static boolean isValidDate(String s) {
        Pattern pattern = compile(
                "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[13579][26])00))-02-29)");
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

    private static long transDateToTimeMillis(String date) {
        try {
            return getSdf(SECOND_DATE_PATTERN).parse(date, new ParsePosition(0)).getTime();
        } catch (Exception e) {
            try {
                return getSdf(DAY_DATE_PATTERN).parse(date, new ParsePosition(0)).getTime();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
        return 0;
    }


    public static Date getDayBegin() {
        return getDayBegin(new Date());
    }

    /**
     * 获取指定日期当天开始时间
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
