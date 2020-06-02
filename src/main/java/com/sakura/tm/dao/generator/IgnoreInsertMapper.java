package com.sakura.tm.dao.generator;

import com.sakura.tm.dao.generator.provider.IgnoreInsertProvider;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/2 11:55
 */
@RegisterMapper
public interface IgnoreInsertMapper<T> {

	/**
	 * ignore 插入
	 */
	@InsertProvider(type = IgnoreInsertProvider.class, method = "dynamicSQL")
	int ignoreInsert(T record);

	@InsertProvider(type = IgnoreInsertProvider.class, method = "dynamicSQL")
	int ignoreInsertSelective(T record);

	@InsertProvider(type = IgnoreInsertProvider.class, method = "dynamicSQL")
	int ignoreInsertList(List<T> recordList);
}
