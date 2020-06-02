package com.sakura.tm.common.service;

import com.sakura.tm.common.entity.SysApoAreacode;
import com.sakura.tm.common.entity.example.SysApoAreacodeExample;
import com.sakura.tm.common.util.CommonsUtil;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.dao.mapper.SysApoAreacodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/2 14:23
 */
@Component
public class CommonUserService {

	@Autowired
	private SysApoAreacodeMapper sysApoAreacodeMapper;

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
}
