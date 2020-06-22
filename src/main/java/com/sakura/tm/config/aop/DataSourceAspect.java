package com.sakura.tm.config.aop;

import com.sakura.tm.config.dao.DataSource;
import com.sakura.tm.config.dao.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/16 10:51
 */
@Component
@Aspect
@Slf4j
public class DataSourceAspect {

	//切入点在dao层的方法上，配置aop的切入点
	@Pointcut("execution( * com.sakura.tm.dao1..*(..))")
	public void dataSourcePointCut1() {
	}

	@Before("dataSourcePointCut1() ")
	public void before(JoinPoint joinPoint) {
		Object target = joinPoint.getTarget();
		String method = joinPoint.getSignature().getName();
		Class<?>[] clazz = target.getClass().getInterfaces();
		Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
		try {
			Method m = clazz[0].getMethod(method, parameterTypes);
			//如果方法上存在切换数据源的注解，则根据注解内容进行数据源切换
			if (m != null && m.isAnnotationPresent(DataSource.class)) {
				DataSource annotation = m.getAnnotation(DataSource.class);
				String dataSourceName = annotation.value();
				DynamicDataSourceHolder.putDataSouce(dataSourceName);
				log.debug("-----current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal-----");

			} else {
				log.debug("switch datasource fail, use default");
			}
		} catch (NoSuchMethodException e) {
			log.error("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error", e);
		}
	}


	//执行完切面后，清空线程共享中的数据源名称
	@After("dataSourcePointCut1()")
	public void after(JoinPoint joinPoint){
		DynamicDataSourceHolder.removeDataSource();
	}
}
