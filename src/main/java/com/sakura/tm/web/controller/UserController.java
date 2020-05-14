package com.sakura.tm.web.controller;

import com.sakura.tm.common.annotation.Permission;
import com.sakura.tm.common.util.JsonResult;
import com.sakura.tm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 李七夜 on 2020/5/14 15:14
 */
@Api("用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Permission()
	@ApiOperation("用户集合")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JsonResult list() {
		return userService.list();
	}

	@Permission(noLogin = true)
	@ApiOperation("登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JsonResult login(String userName, String password) {
		return userService.login(userName, password);
	}
}
