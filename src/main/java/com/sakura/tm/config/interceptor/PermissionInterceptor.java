package com.sakura.tm.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.sakura.tm.common.annotation.Permission;
import com.sakura.tm.common.emnu.PermissionEnum;
import com.sakura.tm.common.util.CommonConstant;
import com.sakura.tm.common.util.CommonsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 李七夜 on 2020/5/14 16:28
 * 权限校验拦截器
 * @author 李七夜
 */
@Slf4j
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
			if (CommonsUtil.isBlank(permission)) {
				return true;
			}
			if (permission.noLogin()) {
				return true;
			}
			String userId = request.getAttribute("user_id").toString();
			if (CommonsUtil.isBlank(redisTemplate.opsForHash().get(CommonConstant.REDIS_PERMISSION_KEY, userId))) {
				returnNoPermission("用户无权限", response);
				return false;
			}
			String userSigns = redisTemplate.opsForHash().get(CommonConstant.REDIS_PERMISSION_KEY, userId).toString();
			PermissionEnum[] permissionEnums = permission.permissionEnums();
			List<String> list = Arrays.asList(userSigns.split(","));
			for (PermissionEnum permissionEnum : permissionEnums) {
				if (!list.contains(permissionEnum.getSign())) {
					returnNoPermission(permissionEnum.getDesc(), response);
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
