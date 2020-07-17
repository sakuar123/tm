package com.sakura.tm.common.util;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by 李七夜 on 2020/5/8 17:47
 */
@Slf4j
public class JwtUtil {

    /** 失效时间,默认为1个小时 */
    private static final Date EXPIRATION_TIME = DateUtils.addHours(new Date(), +1);

    /** 生成签名的时候使用的秘钥secret */
    private static final String JWT_KEY = "NBMcTwQaqkJnPzs9zhwWusoRIiKiPaFIxO8m6heDIdvgRX4unbzNp0rM8XE3s6Er";

    /**
     * 生成jwt
     */
    public static String createJWT(JwtUser jwtUser) {
        return createJWT(EXPIRATION_TIME, jwtUser);
    }

    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param expirationDate jwt过期时间
     * @param user 登录成功的user对象
     */
    public static String createJWT(Date expirationDate, JwtUser user) {
        //指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //创建payload的私有声明
        Map<String, Object> claims = Maps.newHashMap();
        claims.put("id", user.getId());
        claims.put("user", JSON.toJSON(user));

        //生成签发人
        String subject = user.getName();
        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(new Date())
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, JWT_KEY)
                //失效时间
                .setExpiration(expirationDate);
        return builder.compact();
    }


    /**
     * Token的解密
     *
     * @param token 加密后的token
     */
    public static Claims parseJWT(String token) {
        //得到DefaultJwtParser
        try {
            return Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(JWT_KEY)
                    //设置需要解析的jwt
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("解密token时异常:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static JwtUser buildJwtUser(String id, String name) {
        return JwtUser.builder().id(id).name(name).build();
    }

    public static void main(String[] args) {
        JwtUser jwtUser = buildJwtUser(DESUtil.getSHA256(Arrays.toString(Base64.encodeBase64("123456".getBytes()))),
                "张三");
        String token = createJWT(jwtUser);
        log.info("生成的token:" + token);
        log.info("正常解密:" + parseJWT(token));
        log.info("异常解密的token:" + parseJWT(token + "123"));
    }

    @Data
    @Builder
    @Api("jwt专属用户类")
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JwtUser {

        private String id;
        private String name;
    }
}
