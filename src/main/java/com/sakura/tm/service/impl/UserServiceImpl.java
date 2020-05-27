package com.sakura.tm.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.sakura.tm.common.emnu.CommonsMessageEnum;
import com.sakura.tm.common.entity.LoanUser;
import com.sakura.tm.common.util.*;
import com.sakura.tm.dao.generator.UserGeneratorMapper;
import com.sakura.tm.service.UserService;
import com.sakura.tm.web.query.BaseQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by 李七夜 on 2020/5/14 15:12
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserGeneratorMapper userGeneratorMapper;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public PageResult list(BaseQuery baseQuery) {
		PageHelper.startPage(baseQuery.getPageNum(),baseQuery.getPageSize());
		List<LoanUser> list = userGeneratorMapper.selectAll();
		return PageResult.success(list);
	}

	@Override
	public JsonResult login(String userName, String password) {
		Map<String, String> result = Maps.newHashMap();
		Example example = new Example(LoanUser.class);
		example.or().andEqualTo("userCode", userName);
		LoanUser user = userGeneratorMapper.selectOneByExample(example);
		Assert.isTrue(CommonsUtil.isNotBlank(user), CommonsMessageEnum.FAILURE);
		String userKey = DESUtil.getSHA256(user.getUserName());
		redisTemplate.opsForHash().put(CommonConstant.REDIS_USER_KEY, userKey, user.getUserId().toString());
		JwtUtil.JwtUser jwtUser = new JwtUtil.JwtUser();
		jwtUser.setId(userKey);
		jwtUser.setName(user.getUserId().toString());
		result.put("token", JwtUtil.createJWT(jwtUser));
		return JsonResult.success(result);
	}
}
