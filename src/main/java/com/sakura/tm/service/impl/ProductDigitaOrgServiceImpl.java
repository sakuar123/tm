package com.sakura.tm.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.sakura.tm.common.entity.ProductOrg;
import com.sakura.tm.common.entity.ProductTypeOrg;
import com.sakura.tm.common.entity.example.ProductOrgExample;
import com.sakura.tm.common.util.CommonsUtil;
import com.sakura.tm.common.util.JsonResult;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.dao.mapper.ProductOrgMapper;
import com.sakura.tm.dao.mapper.ProductTypeOrgMapper;
import com.sakura.tm.service.ProductDigitaOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private ProductTypeOrgMapper productTypeOrgMapper;
	@Autowired
	private ProductOrgMapper productOrgMapper;

	@Override
	public JsonResult getProductTypeInfo() {
		PageData result = new PageData();
		List<ProductTypeOrg> productTypeOrgList = productTypeOrgMapper.selectAll();
		List<String> productTypeIList = productTypeOrgList.stream().filter(productTypeOrg -> productTypeOrg.getParentId().equals(0)).map(ProductTypeOrg::getName).collect(Collectors.toList());
		List<Integer> productIdIList = productTypeOrgList.stream().filter(productTypeOrg -> productTypeOrg.getParentId().equals(0)).map(ProductTypeOrg::getId).collect(Collectors.toList());
		Map<Integer, String> map = productTypeOrgList.stream().filter(productTypeOrg -> productIdIList.contains(productTypeOrg.getParentId())).collect(Collectors.toMap(ProductTypeOrg::getParentId, ProductTypeOrg::getName, (s1, s2) -> s1 + "," + s2));
		result.put("I", JSON.toJSON(productTypeIList));
		result.put("II", JSON.toJSON(map));
		return JsonResult.success(result);
	}

	@Override
	public JsonResult getProductInfo() {
		List<PageData> result = productOrgMapper.query();
		return JsonResult.success(result);
	}

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


}
