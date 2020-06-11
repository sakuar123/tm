package com.sakura.tm.service.impl;

import com.alibaba.fastjson.JSON;
import com.sakura.tm.common.entity.ProductTypeOrg;
import com.sakura.tm.common.entity.example.ProductTypeOrgExample;
import com.sakura.tm.common.util.JsonResult;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.dao.mapper.ProductTypeOrgMapper;
import com.sakura.tm.service.ProductDigitaOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
