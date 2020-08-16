package com.sakura.tm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.page.PageMethod;
import com.sakura.tm.common.entity.SmbmsUser;
import com.sakura.tm.common.entity.SmbmsUser.Column;
import com.sakura.tm.common.entity.example.SmbmsUserExample;
import com.sakura.tm.common.util.CommonsUtil;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.dao.mapper.SmbmsUserGeneratorMapper;
import com.sakura.tm.service.SmbmsUserService;
import com.sakura.tm.web.query.SmbmsUserQuery;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 李七夜
 * Created by 李七夜 on 2020/7/31 15:53
 */
@Slf4j
@Service
public class SmbmsUserServiceImpl implements SmbmsUserService {

    @Autowired
    private SmbmsUserGeneratorMapper smbmsUserGeneratorMapper;

    @Override
    public PageResult<SmbmsUser> list(SmbmsUserQuery smbmsUserQuery) {
        PageMethod.startPage(smbmsUserQuery.getPageNum(), smbmsUserQuery.getPageSize());
        SmbmsUserExample.Criteria criteria = new SmbmsUserExample()
                .orderBy(Column.id.desc()).or();
        if (CommonsUtil.isNotBlank(smbmsUserQuery.getUserName())) {
            criteria.andUsernameLike(StringUtils.join("%", smbmsUserQuery.getUserName(), "%"));
        }
        if (CommonsUtil.isNotBlank(smbmsUserQuery.getRegion())) {
            criteria.andGenderEqualTo(smbmsUserQuery.getRegion());
        }
        List<SmbmsUser> list = smbmsUserGeneratorMapper.selectByExample(criteria.example());
        return PageResult.success(list);
    }
}
