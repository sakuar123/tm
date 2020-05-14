package com.sakura.tm.common.emnu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 李七夜
 */
@Getter
@AllArgsConstructor
public enum CommonsCodeEnum {
    success(200, "succes"),
    fail(999, "fail");
    private Integer code;
    private String name;
}
