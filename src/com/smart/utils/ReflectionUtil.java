package com.smart.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.smart.helper.HelperLoader;

/** 
 * Filename:     ReflectionUtil.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年11月3日 下午12:40:06 
 */
public final class ReflectionUtil {
	
	/**
	 * 创建实例
	 */
	public static Object newInstance(Class<?> cls){
		Object instance;
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("new instance failure", e);
		}
		return instance;
	}
	
	/**
	 * 创建实例
	 */
	public static Object newInstance(String className){
		Class<?> cls = ClassUtil.loadClass(className);
		return newInstance(cls);
	}
	
	/**
	 * 调用方法
	 */
	public static Object invokeMethod(Object obj, Method method, Object...args){
		Object result;
		try {
			method.setAccessible(true);
			result = method.invoke(obj, args);
		} catch (Exception e) {
			throw new RuntimeException("invoke method failure", e);
		}
		return result;
	}
	
	/**
	 * 设置成员变量的值
	 */
	public static void setField(Object obj, Field field, Object value){
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException("set field failure", e);
		}
	}
}

