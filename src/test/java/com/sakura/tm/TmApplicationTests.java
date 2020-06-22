package com.sakura.tm;

import com.alibaba.fastjson.JSON;
import com.sakura.tm.common.entity.ProductTypeOrg;
import com.sakura.tm.common.service.CommonUserService;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.dao.mapper.ProductTypeOrgMapper;
import com.sakura.tm.dao1.smbms.SmbmsTestMapper;
import com.sakura.tm.dao1.tm.TmTestMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
class TmApplicationTests {

	@Autowired
	private CommonUserService commonUserService;
	@Autowired
	private ProductTypeOrgMapper productTypeOrgMapper;
	@Test
	void contextLoads() {
		System.out.println(JSON.toJSON(commonUserService.getIdentityInfoByIdCard("452123198911195219")));
	}

	@Test
	void t1() {
		PageData result = new PageData();
		List<ProductTypeOrg> productTypeOrgList = productTypeOrgMapper.selectAll();
		List<String> productTypeIList = productTypeOrgList.stream().filter(productTypeOrg -> productTypeOrg.getParentId().equals(0)).map(ProductTypeOrg::getName).collect(Collectors.toList());
		List<Integer> productIdIList = productTypeOrgList.stream().filter(productTypeOrg -> productTypeOrg.getParentId().equals(0)).map(ProductTypeOrg::getId).collect(Collectors.toList());
		Map<Integer, String> map = productTypeOrgList.stream().filter(productTypeOrg -> productIdIList.contains(productTypeOrg.getParentId())).collect(Collectors.toMap(ProductTypeOrg::getParentId,ProductTypeOrg::getName,(s1, s2) -> s1));
		result.put("I", JSON.toJSON(productTypeIList));
		result.put("II",JSON.toJSON(map));
	}

	@Autowired
	private SmbmsTestMapper smbmsTestMapper;
	@Autowired
	private TmTestMapper tmTestMapper;
	@Autowired
	@Qualifier("otherSqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Test
	void t2() {
		System.out.println(
				JSON.toJSON(sqlSessionTemplate.selectList("SmbmsTestMapper.query", null)));
	}

	@Test
	void t3(){
		System.out.println(JSON.toJSON(smbmsTestMapper.query()));
		System.out.println(JSON.toJSON(tmTestMapper.query()));
	}
}
