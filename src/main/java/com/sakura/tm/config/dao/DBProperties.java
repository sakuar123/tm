package com.sakura.tm.config.dao;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/16 10:46
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DBProperties {

	private HikariDataSource master;
	private HikariDataSource other;
}
