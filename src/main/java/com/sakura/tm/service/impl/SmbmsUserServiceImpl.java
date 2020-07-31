package com.sakura.tm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageMethod;
import com.sakura.tm.common.entity.SmbmsUser;
import com.sakura.tm.common.entity.SmbmsUser.Column;
import com.sakura.tm.common.entity.example.SmbmsUserExample;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.dao.mapper.SmbmsUserGeneratorMapper;
import com.sakura.tm.service.SmbmsUserService;
import com.sakura.tm.web.query.BaseQuery;

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
    public PageResult<SmbmsUser> list(BaseQuery baseQuery) {
        PageMethod.startPage(baseQuery.getPageNum(), baseQuery.getPageSize());
        List<SmbmsUser> list = smbmsUserGeneratorMapper.selectByExample(
                new SmbmsUserExample().orderBy(Column.id.desc())
        );
        return PageResult.success(list);
    }
}
