package com.sakura.tm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sakura.tm.common.entity.User;
import com.sakura.tm.common.util.*;
import com.sakura.tm.dao.generator.UserGeneratorMapper;
import com.sakura.tm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
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
	public JsonResult list() {
		String userList = redisTemplate.opsForValue().get("userList");
		if (CommonsUtil.isNotBlank(userList)) {
			return JsonResult.success(JSONObject.parseArray(userList, User.class));
		}
		List<User> list = userGeneratorMapper.selectAll();
		redisTemplate.opsForValue().set("userList", JSON.toJSON(list).toString());
		return JsonResult.success(list);
	}

	@Override
	public JsonResult login(String userName, String password) {
		Map<String, String> result = Maps.newHashMap();
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userCode", userName);
		User user = userGeneratorMapper.selectOneByExample(example);
		Assert.isTrue(CommonsUtil.isNotBlank(user), "用户不存在");
		String userKey = DESUtil.getSHA256(user.getUserName());
		redisTemplate.opsForHash().put(CommonConstant.REDIS_USER_KEY, userKey, user.getId().toString());
		JwtUtil.JwtUser jwtUser = new JwtUtil.JwtUser();
		jwtUser.setId(userKey);
		jwtUser.setName(user.getId().toString());
		result.put("token", JwtUtil.createJWT(jwtUser));
		return JsonResult.success(result);
	}
}
