package com.sakura.tm.service;

import com.sakura.tm.common.util.JsonResult;

/**
 * Created by 李七夜 on 2020/5/14 15:12
 */
public interface UserService {

	JsonResult list();

	JsonResult login(String userName,String password);
}
