package com.sakura.tm.web.controller;

import com.sakura.tm.common.annotation.Permission;
import com.sakura.tm.common.util.JsonResult;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.service.ProductDigitaOrgService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@ApiOperation("商品列表")
	@Permission(noLogin = true)
	@RequestMapping("/getProductTypeList")
	public PageResult getProductTypeInfo(HttpServletRequest request) {
		return productDigitaOrgService.getProductInfo(new PageData(request));
	}

	@Permission(noLogin = true)
	@ApiOperation("获取商品详情")
	@RequestMapping(value = "/getProductDetail", method = RequestMethod.GET)
	public JsonResult getProductDetail(@RequestParam(required = true) Integer id) {
		return productDigitaOrgService.getProductDetail(id);
	}

	@Permission
	@ApiOperation("加入购物车")
	@RequestMapping(value = "/addShoppingTrolley", method = RequestMethod.POST)
	public JsonResult addShoppingTrolley(HttpServletRequest request) {
		return productDigitaOrgService.addShoppingTrolley(new PageData(request));
	}
}
