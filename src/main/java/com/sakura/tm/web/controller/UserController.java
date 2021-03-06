package com.sakura.tm.web.controller;

import com.sakura.tm.common.annotation.Permission;
import com.sakura.tm.common.util.JsonResult;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.service.UserDigitaOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/2 12:05
 */
@RestController
@Api("用户控制器")
@RequestMapping("/user/digital")
public class UserController {
	@Autowired
	private UserDigitaOrgService userDigitaOrgService;

	@ApiOperation("注册用户")
	@Permission(noLogin = true)
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public JsonResult register(HttpServletRequest request) {
		return userDigitaOrgService.register(new PageData(request));
	}

	@ApiOperation("用户登录")
	@Permission(noLogin = true)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JsonResult login(HttpServletRequest request) {
		return userDigitaOrgService.login(new PageData(request));
	}

	@ApiOperation("发送验证码")
	@Permission(noLogin = true)
	@RequestMapping(value = "/sendCaptcha", method = RequestMethod.GET)
	public JsonResult sendCaptcha(HttpServletRequest request) {
		return userDigitaOrgService.sendCaptcha(new PageData(request));
	}
}
