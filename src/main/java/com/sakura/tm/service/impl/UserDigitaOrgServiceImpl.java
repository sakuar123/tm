package com.sakura.tm.service.impl;

import com.sakura.tm.common.emnu.EnumState;
import com.sakura.tm.common.emnu.ResultMsgEnum;
import com.sakura.tm.common.entity.UserDigitaOrg;
import com.sakura.tm.common.entity.example.UserDigitaOrgExample;
import com.sakura.tm.common.service.CommonUserService;
import com.sakura.tm.common.util.*;
import com.sakura.tm.dao.mapper.UserDigitaOrgMapper;
import com.sakura.tm.service.UserDigitaOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/2 10:46
 */
@Slf4j
@Service
public class UserDigitaOrgServiceImpl implements UserDigitaOrgService {

	@Autowired
	private UserDigitaOrgMapper userDigitaOrgMapper;
	@Autowired
	private CommonUserService commonUserService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 用户注册
	 * @param pageData
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JsonResult register(PageData pageData) {
		try {
			UserDigitaOrg userDigitaOrg = userDigitaOrgMapper.selectOneByExample(new UserDigitaOrgExample().or().andPhoneEqualTo(pageData.getString("phone")).example());
			Assert.isTrue(CommonsUtil.isBlank(userDigitaOrg), ResultMsgEnum.USER_EXITS);
			userDigitaOrgMapper.ignoreInsertSelective(buildUser(pageData));
			return JsonResult.success("注册成功");
		} catch (Exception e) {
			log.error("注册用户时失败:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 用户登录
	 * @param pageData
	 * @return
	 */
	@Override
	public JsonResult login(PageData pageData) {
		//手机号
		String phone = pageData.getString("phone");
		//密码
		String password = pageData.getString("password");
		//验证码
		String captcha = pageData.getString("captcha");
		UserDigitaOrg userDigitaOrg = commonUserService.getUserByPd(pageData);
		Assert.isTrue(CommonsUtil.isNotBlank(userDigitaOrg), ResultMsgEnum.USER_NOT_EXITS);
		String md5Password = MD5Util.getMd5(password + userDigitaOrg.getPwdSlt());
		Assert.isTrue(md5Password.equals(userDigitaOrg.getPassword()), ResultMsgEnum.USER_PWD_ERROR);
		Assert.isTrue(redisTemplate.opsForHash().hasKey(CommonConstant.REDIS_CAPTCHA_KEY, phone), "验证码已失效,请重新获取!");
		Assert.isTrue(captcha.equals(redisTemplate.opsForHash().get(CommonConstant.REDIS_CAPTCHA_KEY, phone)), "验证码错误!");
		redisTemplate.opsForHash().delete(CommonConstant.REDIS_CAPTCHA_KEY, phone);
		String redisKey = DESUtil.getSHA256(userDigitaOrg.getUserId().toString());
		String token = JwtUtil.createJWT(org.apache.commons.lang3.time.DateUtils.addHours(new Date(), +2),
				JwtUtil.buildJwtUser(redisKey, userDigitaOrg.getUserName()));
		redisTemplate.opsForHash().put(CommonConstant.REDIS_USER_KEY, redisKey, userDigitaOrg.getUserId().toString());
		PageData reuslt = new PageData();
		reuslt.put("token", token);
		reuslt.put("name", userDigitaOrg.getUserName());
		return JsonResult.success(reuslt);
	}

	/**
	 * 发送验证码
	 * 不会真的发送验证码,接口直接返回
	 * 资金不足,资金充足在加入三方发送验证码API
	 * @param pageData
	 * @return
	 */
	@Override
	public JsonResult sendCaptcha(PageData pageData) {
		String phone = pageData.getString("phone");
		UserDigitaOrg userDigitaOrg = commonUserService.getUserByPd(pageData);
		Assert.isTrue(CommonsUtil.isNotBlank(userDigitaOrg), ResultMsgEnum.PHONE_NOT_EXITS);
		Integer captcha = CommonsUtil.getRandomNumber(6);
		redisTemplate.opsForHash().put(CommonConstant.REDIS_CAPTCHA_KEY, phone, captcha.toString());
		redisTemplate.expire(CommonConstant.REDIS_CAPTCHA_KEY, 60, TimeUnit.SECONDS);
		PageData result = new PageData();
		result.put("captcha", captcha);
		return JsonResult.success(result);
	}

	private UserDigitaOrg buildUser(PageData pageData) {
		UserDigitaOrg userDigitaOrg = new UserDigitaOrg();
		userDigitaOrg.setUserName(pageData.getString("userName"));
		userDigitaOrg.setPwdSlt(CommonsUtil.getRandomString(10));
		userDigitaOrg.setPassword(MD5Util.getMd5(pageData.getString("password") + userDigitaOrg.getPwdSlt()));
		userDigitaOrg.setPhone(pageData.getString("phone"));
		userDigitaOrg.setState(EnumState.USE.getIntValue());
		userDigitaOrg.setIsRealNameAuthentication(-1);
		userDigitaOrg.setIdCard(pageData.getString("idCard"));
		PageData entityInfo = commonUserService.getIdentityInfoByIdCard(pageData.getString("idCard"));
		Assert.isTrue(CommonsUtil.isNotBlank(entityInfo), "身份证校验失败!");
		userDigitaOrg.setBirthday(DateUtils.parseDate(entityInfo.getString("birthday")));
		userDigitaOrg.setSex("M".equals(entityInfo.getString("sexCode")) ? 1 : 0);
		userDigitaOrg.setZone(entityInfo.getString("zone"));
		return userDigitaOrg;
	}
}
