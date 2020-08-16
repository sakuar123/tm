package com.sakura.tm.config.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.sakura.tm.common.annotation.Permission;
import com.sakura.tm.common.enums.EnumInterceptorDefineParams;
import com.sakura.tm.common.enums.EnumPermission;
import com.sakura.tm.common.util.CommonConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by 李七夜 on 2020/5/14 16:28
 * 权限校验拦截器
 * @author 李七夜
 */
@Slf4j
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 在请求接口之前,登录拦截器之后
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (handler instanceof HandlerMethod) {

			HandlerMethod handlerMethod = (HandlerMethod) handler;

			Permission permission = handlerMethod.getMethodAnnotation(Permission.class);

			//不需要登录的或者头部没加权限注解的,统一放过
			if (permission == null || permission.noLogin()) {
				return true;
			}

			//用户id,会从登录拦截器放入
			String userId = request.getAttribute(EnumInterceptorDefineParams.USER_ID.getName()).toString();

			//该用户所拥有的权限signs
			String [] permissionSigns = redisTemplate.opsForHash().get(CommonConstant.REDIS_PERMISSION_KEY, userId)
					.toString().split(",");

			//权限校验,判断该用户是否可以访问该接口
			List<String> permissionSignList = new ArrayList<>(Arrays.asList(permissionSigns));

			for (EnumPermission enumPermission : permission.permissionEnums()) {
				if (!permissionSignList.contains(enumPermission.getSign())) {
					returnNoPermission(enumPermission.name(), response);
					return false;
				}
			}

		}
		return true;
	}

	private void returnNoPermission(String permissionName, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("result", 403);
		result.put("msg", "无权限");
		result.put("data", permissionName);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try (PrintWriter writer = response.getWriter()) {
			writer.print(result.toJSONString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
