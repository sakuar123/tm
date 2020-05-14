package com.sakura.tm.dao.generator;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by 李七夜 on 2020/5/14 15:11
 */
public interface BaseGeneratorMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
