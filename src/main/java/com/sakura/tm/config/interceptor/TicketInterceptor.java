package com.sakura.tm.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sakura.tm.common.annotation.Permission;
import com.sakura.tm.common.enums.EnumInterceptorDefineParams;
import com.sakura.tm.common.enums.EnumJsonResultMsg;
import com.sakura.tm.common.util.Assert;
import com.sakura.tm.common.util.JwtUtil;
import com.sakura.tm.common.util.JwtUtil.JwtUser;
import com.sakura.tm.common.util.ObejctTools;

/**
 * Created by 李七夜 on 2020/5/14 15:49
 * 登录校验拦截器
 *
 * @author 李七夜
 */
public class TicketInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            //获取头部的Permission注解标签
            Permission permission = handlerMethod.getMethodAnnotation(Permission.class);

            //不需要登录,直接放行
            if (permission != null && permission.noLogin()) {
                return true;
            }

            //从请求中获取登录的token
            String authToken = request.getHeader("authToken");

            //如果没有则直接报错,让用户必须登录
            Assert.isTrue(ObejctTools.isNotBlank(authToken), EnumJsonResultMsg.USER_NOT_LOGIN);

            //解析登录的Token
            JwtUser jwtUser = JwtUtil.parseJwt(authToken);
            Assert.isTrue(ObejctTools.isNotBlank(authToken), EnumJsonResultMsg.USER_TOKEN_ERR);

            request.setAttribute(EnumInterceptorDefineParams.USER_ID.getName(), jwtUser.getUserId());

        }
        return true;
    }
}
