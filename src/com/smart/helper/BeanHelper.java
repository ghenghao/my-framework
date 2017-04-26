package com.smart.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.smart.utils.ReflectionUtil;


/** 
 * Filename:     BeanHelper.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月8日 下午2:47:42 
 */
public final class BeanHelper {
	
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();
	
	static{
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
		for(Class<?> beanClass : beanClassSet){
			BEAN_MAP.put(beanClass, ReflectionUtil.newInstance(beanClass));
		}
	}
	
	/**
	 * 获取 Bean 映射
	 * @return
	 */
	public static Map<Class<?>, Object> getBeanMap(){
		return BEAN_MAP;
	}
	
	/**
	 * 获取Bean实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls){
		if(!BEAN_MAP.containsKey(cls)){
			throw new RuntimeException("can not get bean by class " + cls);
		}
		return (T)BEAN_MAP.get(cls);
	}
	
	/**
	 * 设置bean实例
	 * @param cls
	 * @param obj
	 */
	public static void setBean(Class<?> cls, Object obj){
		BEAN_MAP.put(cls, obj);
	}
}

