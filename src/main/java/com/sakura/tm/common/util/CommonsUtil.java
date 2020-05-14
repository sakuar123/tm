package com.sakura.tm.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.*;

public class CommonsUtil {

    /**
     * 获取ticketID
     * @return ticketID
     */
    public static String getTicketid() {
        return UUID.randomUUID().toString().toLowerCase() + getRandomNumber(0, 999);
    }

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
}
