package com.sakura.tm.common.service;

import com.google.common.collect.Lists;
import com.sakura.tm.common.entity.SysApoAreacode;
import com.sakura.tm.common.entity.UserDigitaOrg;
import com.sakura.tm.common.entity.example.SysApoAreacodeExample;
import com.sakura.tm.common.entity.example.UserDigitaOrgExample;
import com.sakura.tm.common.util.CommonsUtil;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.dao.mapper.SysApoAreacodeMapper;
import com.sakura.tm.dao.mapper.UserDigitaOrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/2 14:23
 */
@Component
public class CommonUserService {

	@Autowired
	private SysApoAreacodeMapper sysApoAreacodeMapper;
	@Autowired
	private UserDigitaOrgMapper userDigitaOrgMapper;

	public PageData getIdentityInfoByIdCard(String certificateNo) {
		PageData pageData = CommonsUtil.getIdentityInfoByIdCard(certificateNo);
		if (CommonsUtil.isBlank(pageData)) {
			return null;
		}
		String zone = certificateNo.substring(0, 6);
		SysApoAreacode sysApoAreacode = sysApoAreacodeMapper.selectOneByExample(
				new SysApoAreacodeExample().or().andZoneEqualTo(CommonsUtil.parseInt(zone)).example()
		);
		if (CommonsUtil.isBlank(sysApoAreacode)) {
			return null;
		}
		pageData.put("zone", sysApoAreacode.getDescs());
		return pageData;
	}

	public List<UserDigitaOrg> getUserListByPd(PageData pageData) {
		List<UserDigitaOrg> result = Lists.newArrayList();
		UserDigitaOrgExample.Criteria criteria = new UserDigitaOrgExample().createCriteria();
		if (CommonsUtil.isNotBlank(pageData.getString("userId"))) {
			criteria.andUserIdEqualTo(pageData.getIntegerVal("userId"));
		}
		if (CommonsUtil.isNotBlank(pageData.getString("phone"))) {
			criteria.andPhoneEqualTo(pageData.getString("phone"));
		}
		if (CommonsUtil.isNotBlank(pageData.getString("userName"))) {
			criteria.andUserNameLike("%" + pageData.getString("userName") + "%");
		}
		result = userDigitaOrgMapper.selectByExample(criteria.example());
		return result;
	}

	public UserDigitaOrg getUserByPd(PageData pageData) {
		List<UserDigitaOrg> userDigitaOrgList = getUserListByPd(pageData);
		return CommonsUtil.isNotBlank(userDigitaOrgList) ? userDigitaOrgList.get(0) : null;
	}
}
