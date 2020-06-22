package com.sakura.tm.dao.mapper;

import com.sakura.tm.common.entity.UserShoppingTrolley;
import com.sakura.tm.common.entity.example.UserShoppingTrolleyExample;
import com.sakura.tm.common.util.PageData;
import com.sakura.tm.dao.generator.BaseGeneratorMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Created by Mybatis Generator on 2020/06/17
*/
@Mapper
public interface UserShoppingTrolleyMapper extends BaseGeneratorMapper<UserShoppingTrolley> {
    long countByExample(UserShoppingTrolleyExample example);

    List<PageData> getUserShoppingTrolley(PageData pageData);
}