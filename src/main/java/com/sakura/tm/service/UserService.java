package com.sakura.tm.service;

import com.sakura.tm.common.util.JsonResult;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.web.query.BaseQuery;

/**
 * Created by 李七夜 on 2020/5/14 15:12
 */
public interface UserService {

	PageResult list(BaseQuery baseQuery);

	JsonResult login(String userName,String password);
}
