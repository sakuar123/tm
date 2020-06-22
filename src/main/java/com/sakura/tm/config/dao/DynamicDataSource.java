package com.sakura.tm.config.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/15 11:22
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

	//数据源路由，此方用于产生要选取的数据源逻辑名称
	@Override
	protected Object determineCurrentLookupKey() {
		//从线程共享中获取数据源名称
		return DynamicDataSourceHolder.getDataSource();
	}
}
