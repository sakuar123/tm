package com.sakura.tm.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.sakura.tm.common.entity.ProductDetailOrg;
import com.sakura.tm.common.entity.ProductOrg;
import com.sakura.tm.common.entity.ProductTypeOrg;
import com.sakura.tm.common.entity.UserShoppingTrolley;
import com.sakura.tm.common.entity.example.ProductDetailOrgExample;
import com.sakura.tm.common.entity.example.ProductOrgExample;
import com.sakura.tm.common.util.CommonsUtil;
import com.sakura.tm.common.util.JsonResult;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.dao.mapper.ProductDetailOrgMapper;
import com.sakura.tm.dao.mapper.ProductOrgMapper;
import com.sakura.tm.dao.mapper.ProductTypeOrgMapper;
import com.sakura.tm.dao.mapper.UserShoppingTrolleyMapper;
import com.sakura.tm.service.ProductDigitaOrgService;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/11 15:54
 */
@Slf4j
@Service
public class ProductDigitaOrgServiceImpl implements ProductDigitaOrgService {
	@Autowired
	private ProductOrgMapper productOrgMapper;
	@Autowired
	private ProductDetailOrgMapper productDetailOrgMapper;
	@Autowired
	private UserShoppingTrolleyMapper userShoppingTrolleyMapper;

	@Override
	public PageResult getProductInfo(PageData pageData) {
		PageHelper.startPage(pageData.getIntegerVal("pageNum"), pageData.getIntegerVal("pageSize"));
		ProductOrgExample.Criteria criteria = new ProductOrgExample().createCriteria();
		if (CommonsUtil.isNotBlank(pageData.getIntegerVal("id"))) {
			criteria.andIdEqualTo(pageData.getIntegerVal("id"));
		}
		if (CommonsUtil.isNotBlank(pageData.getIntegerVal("type"))) {
			criteria.andProductTypeEqualTo(pageData.getIntegerVal("type"));
		}
		List<ProductOrg> result = productOrgMapper.selectByExample(criteria.example());
		return PageResult.success(result);
	}

	/**
	 * 商品详情
	 * @param id
	 * @return
	 */
	@Override
	public JsonResult getProductDetail(Integer id) {
		ProductDetailOrg productDetailOrg = productDetailOrgMapper.selectOneByExample(new ProductDetailOrgExample().or().andIdEqualTo(id).example());
		return JsonResult.success(productDetailOrg);
	}

	/**
	 * 添加购物车
	 * @param pageData
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JsonResult addShoppingTrolley(PageData pageData) {
		Integer userId = pageData.getIntegerVal("userId");
		Integer productId = pageData.getIntegerVal("productId");
		try {
			userShoppingTrolleyMapper.ignoreInsertSelective(UserShoppingTrolley.builder().userId(userId).productId(productId).build());
			return JsonResult.success();
		} catch (Exception e) {
			log.error("保存购物车时异常:" + e.getMessage());
			return JsonResult.fail("加入购物车失败!");
		}
	}

	/**
	 * 购物车展示
	 * @param pageData
	 * @return
	 */
	@Override
	public PageResult getUserShoppingTrolley(PageData pageData) {
		return null;
	}
}
