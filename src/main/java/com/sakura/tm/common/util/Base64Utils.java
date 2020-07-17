package com.sakura.tm.common.util;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by 李七夜 on 2020/5/22 10:06
 *
 * @author 李七夜
 */
@Slf4j
public class Base64Utils {

    /**
     * 双重base64解密
     *
     * @return String
     */
    public static String getDecodeDouble(String s) {
        byte[] decode = Base64.decodeBase64(Base64.decodeBase64(s));
        return new String(decode, StandardCharsets.UTF_8);
    }

    /**
     * base64解密一次
     *
     * @return String
     */
    public static String getDecode(String s) {
        byte[] decode = Base64.decodeBase64(s);
        return new String(decode, StandardCharsets.UTF_8);
    }

    /**
     * 双重base64加密
     *
     * @return String
     */
    static String getEncode(String s) {
        byte[] encode = Base64.encodeBase64(Base64.encodeBase64(s.getBytes()));
        return new String(encode, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String str = "1";
        log.info(MD5Util.getMd5(str).substring(0, 5));
    }
}
