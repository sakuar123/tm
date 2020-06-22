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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/12 17:43
 */
//@Configuration
@MapperScan(basePackages = "com.sakura.tm.dao1.smbms", sqlSessionFactoryRef = "otherSqlSessionFactory")
public class DataOtherSourceConfig {

	@Bean(name = "dataOtherSource")
	@ConfigurationProperties(prefix = "spring.datasource.other")
	public DataSource getDataOtherSourceConfig() {
		return DataSourceBuilder.create().build();
	}
	@Bean(name = "otherSqlSessionFactory")
	public SqlSessionFactory otherSqlSessionFactory(@Qualifier("dataOtherSource") DataSource datasource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(datasource);
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/*.xml"));
		return bean.getObject();
	}

	@Bean("otherSqlSessionTemplate")
	public SqlSessionTemplate otherSqlSessionTemplate(@Qualifier("otherSqlSessionFactory") SqlSessionFactory sessionfactory) {
		return new SqlSessionTemplate(sessionfactory);
	}
}
