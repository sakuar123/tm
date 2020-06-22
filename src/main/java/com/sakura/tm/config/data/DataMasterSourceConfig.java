package com.sakura.tm.config.data;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/12 17:35
 */
//@Configuration
@MapperScan(basePackages = "com.sakura.tm.dao1.tm", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class DataMasterSourceConfig {

	@Primary
	@Bean(name = "dataMasterSource")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource getDataMasterSourceConfig() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "masterSqlSessionFactory")
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("dataMasterSource") DataSource datasource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(datasource);
		bean.setMapperLocations(
				// 设置mybatis的xml所在位置
				new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/*.xml"));
		return bean.getObject();
	}

	@Primary
	@Bean("masterSqlSessionTemplate")
	public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sessionfactory) {
		return new SqlSessionTemplate(sessionfactory);
	}
}
