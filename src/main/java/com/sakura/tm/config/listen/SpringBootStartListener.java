package com.sakura.tm.config.listen;

import com.sakura.tm.common.util.CommonsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * SpringBoot全局监听器:监听到SpringBoot启动完成的时候
 * @author 李七夜
 */
@Slf4j
@Component
public class SpringBootStartListener implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		checkRedisConnection();
		ConfigurableApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String serverPort = environment.getProperty("server.port");
		String contextPath = environment.getProperty("server.servlet.context-path");
		if (CommonsUtil.isBlank(contextPath)) {
			contextPath = "/";
		}
		log.info("SpringBoot启动成功.....");
		log.info("\n----------------------------------------------------------\n\t" +
						"Application 'tm' is running! Access URLs:\n\t" +
						"Local: \t\thttp://localhost:{}{}\n\t" +
						"{}" +
						"\n----------------------------------------------------------",
				serverPort,
				contextPath,
				"Swagger: \t\thttp://localhost:" + serverPort + contextPath
						+ ("/".equals(contextPath) ? "" : "/")
						+ "swagger-ui.html");
	}

	private void checkRedisConnection() {
		try {
			RedisConnectionFactory redisConnectionFactory = stringRedisTemplate.getConnectionFactory();
			RedisConnection redisConnection = redisConnectionFactory.getConnection();
			if (redisConnection.isClosed()) {
				throw new RuntimeException("Redis Connection is Closed.....");
			}
		} catch (Exception e) {
			log.error("Redis Connection Fail.....");
			throw new RuntimeException("Redis Connection Fail.....");
		}
	}
}
