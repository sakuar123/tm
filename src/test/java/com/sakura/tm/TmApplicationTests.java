package com.sakura.tm;

import com.alibaba.fastjson.JSON;
import com.sakura.tm.common.service.CommonUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TmApplicationTests {

	@Autowired
	private CommonUserService commonUserService;

	@Test
	void contextLoads() {
		System.out.println(JSON.toJSON(commonUserService.getIdentityInfoByIdCard("452123198911195219")));
	}

}
