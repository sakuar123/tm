package com.sakura.tm.config.dao;

/**
 * @author 李七夜
 * @version 1.0
 * Created by 李七夜 on 2020/6/16 10:39
 */
public class DynamicDataSourceHolder {

	//本地线程共享对象
	private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

	public static void putDataSouce(String name){
		THREAD_LOCAL.set(name);
	}

	public static String getDataSource(){
		return THREAD_LOCAL.get();
	}

	public static void removeDataSource(){
		THREAD_LOCAL.remove();
	}
}
