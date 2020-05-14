package com.sakura.tm.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sakura.tm.common.annotation.Permission;
import com.sakura.tm.common.util.Assert;
import com.sakura.tm.common.util.CommonConstant;
import com.sakura.tm.common.util.CommonsUtil;
import com.sakura.tm.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by 李七夜 on 2020/5/14 15:49
 * 登录校验拦截器
 * @author 李七夜
 */
public class TicketInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
			//不需要登录,直接放行
			if (permission.noLogin()) {
				return true;
			}
			//登录所携带的token
			String token = request.getHeader("token");
			Assert.isTrue(CommonsUtil.isNotBlank(token), "请登录!");
			Claims claims = JwtUtil.parseJWT(token);
			Assert.isTrue(CommonsUtil.isNotBlank(claims), "非法加密的token,请重新登录!");
			Date expirationDate = claims.getExpiration();
			//判断该token有无失效
			Assert.isTrue(expirationDate.getTime() >= System.currentTimeMillis(), "会话已过期,请重新登录");
			JSONObject jsonObject = JSONObject.parseObject(JSON.toJSON(claims).toString());
			Assert.isTrue(CommonsUtil.isNotBlank(redisTemplate.opsForHash().get(CommonConstant.REDIS_USER_KEY, jsonObject.getString("id"))), "账号异常,请重新登录");
			//从缓存里将userId拿出来
			String userId = redisTemplate.opsForHash().get(CommonConstant.REDIS_USER_KEY, jsonObject.getString("id")).toString();
			request.setAttribute("user_id", userId);
		}
		return true;
	}
}
