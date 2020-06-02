package com.sakura.tm.config.listen;

import com.sakura.tm.common.util.CommonsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;


/**
 * SpringBoot全局监听器:监听到SpringBoot启动完成的时候
 */
@Slf4j
@Component
public class SpringBootStartListener implements ApplicationListener<ApplicationReadyEvent> {


	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		log.info("SpringBoot启动成功.....");
		ConfigurableApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String serverPort = environment.getProperty("server.port");
		String contextPath = environment.getProperty("server.servlet.context-path");
		if (CommonsUtil.isBlank(contextPath)) {
			contextPath = "/";
		}
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
}
