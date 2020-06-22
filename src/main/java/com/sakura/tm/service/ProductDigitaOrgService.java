package com.sakura.tm.service;

import com.sakura.tm.common.util.JsonResult;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.common.util.PageResult;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/11 15:54
 */
public interface ProductDigitaOrgService {

	PageResult getProductInfo(PageData pageData);

	JsonResult getProductDetail(Integer id);

	JsonResult addShoppingTrolley(PageData pageData);

	PageResult getUserShoppingTrolley(PageData pageData);
}
