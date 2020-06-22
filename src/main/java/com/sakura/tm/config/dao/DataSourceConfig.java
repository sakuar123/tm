package com.sakura.tm.config.dao;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/16 10:48
 */
@Slf4j
@Configuration
@EnableScheduling
public class DataSourceConfig {
	@Autowired
	private DBProperties dbProperties;

	/**
	 * 设置动态数据源，通过@Primary 来确定主DataSource
	 *
	 */
	@Bean(name = "dataSource")
	public DynamicDataSource dataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		//1.设置默认数据源
		dynamicDataSource.setDefaultTargetDataSource(dbProperties.getMaster());
		//2.配置多数据源
		Map<Object, Object> map = Maps.newHashMap();
		map.put("master", dbProperties.getMaster());
		map.put("other", dbProperties.getOther());
		//3.存放数据源集
		dynamicDataSource.setTargetDataSources(map);
		return dynamicDataSource;
	}
}
