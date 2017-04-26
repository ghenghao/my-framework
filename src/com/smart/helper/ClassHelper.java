package com.smart.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.smart.annotation.Controller;
import com.smart.annotation.Service;
import com.smart.utils.ClassUtil;


/** 
 * Filename:     ClassHelper.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:	类助手操作类
 * @version:     1.0 
 * @Create at:   2015年11月3日 下午2:43:42 
 */
public final class ClassHelper {
	/**
	 * 定义类集合(存放所有加载的类)
	 */
	private static final Set<Class<?>> CLASS_SET;
	
	static{
		String basePackage = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassUtil.getClassSet(basePackage);
	}
	
	/**
	 * 获取应用包名下的所有类
	 */
	public static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	}
	
	/**
	 * 获取应用包名下所有Service类
	 */
	public static Set<Class<?>> getServiceClassSet(){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for( Class<?> cls : CLASS_SET){
			if(cls.isAnnotationPresent(Service.class)){
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取应用包名下所有Controller类
	 */
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for( Class<?> cls : CLASS_SET){
			if(cls.isAnnotationPresent(Controller.class)){
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取应用包名下所有Bean类(包括Service、Controller等)
	 */
	public static Set<Class<?>> getBeanClassSet(){
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getControllerClassSet());
		return beanClassSet;
	}
	
	/* 以下为Aop一章chapter4添加 */
	/**
	 * 获取应用包名下某父类(或接口)的所有子类(或实现类)
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls : CLASS_SET){
			if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取应用包下带有某注解的所有类
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls : CLASS_SET){
			if(cls.isAnnotationPresent(annotationClass)){
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		 Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
		 System.out.println(classSet);
	}
}

