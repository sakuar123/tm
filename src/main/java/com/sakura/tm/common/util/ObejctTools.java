package com.sakura.tm.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 李七夜
 * Created by 李七夜 on 2020/7/17 17:25
 */
public class ObejctTools {

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
