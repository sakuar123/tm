package com.sakura.tm.service;

import com.sakura.tm.common.entity.SmbmsUser;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.web.query.BaseQuery;

/**
 * @author 李七夜
 * Created by 李七夜 on 2020/7/31 15:53
 */
public interface SmbmsUserService {

    PageResult<SmbmsUser> list(BaseQuery baseQuery);

}
