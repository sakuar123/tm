package com.sakura.tm.dao.mapper;

import com.sakura.tm.common.entity.SmbmsUser;
import com.sakura.tm.common.entity.example.SmbmsUserExample;
import com.sakura.tm.dao.generator.BaseGeneratorMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* Created by Mybatis Generator on 2020/07/31
*/
@Mapper
public interface SmbmsUserGeneratorMapper extends BaseGeneratorMapper<SmbmsUser> {
    long countByExample(SmbmsUserExample example);
}