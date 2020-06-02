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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
