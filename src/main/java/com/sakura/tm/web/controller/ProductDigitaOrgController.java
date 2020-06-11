package com.sakura.tm.web.controller;

import com.sakura.tm.common.annotation.Permission;
import com.sakura.tm.common.util.JsonResult;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.service.ProductDigitaOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/11 16:56
 */
@RestController
@RequestMapping("/product/digita")
public class ProductDigitaOrgController {
	@Autowired
	private ProductDigitaOrgService productDigitaOrgService;

	@Permission
	@RequestMapping("/getProductTypeInfo")
	public JsonResult getProductTypeInfo() {
		return productDigitaOrgService.getProductTypeInfo();
	}

	@Permission
	@RequestMapping("/getProductTypeList")
	public PageResult getProductTypeInfo(HttpServletRequest request) {
		return productDigitaOrgService.getProductInfo(new PageData(request));
	}
}
