package com.smart.bean;

import java.lang.reflect.Method;


/** 
 * Filename:     Handler.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:	封装Action信息
 * @version:     1.0 
 * @Create at:   2015年12月8日 下午3:33:23 
 */
public class Handler {
	/**
	 * Controller类
	 */
	private Class<?> controllerClass;
	
	/**
	 * Action方法
	 */
	private Method actionMethod;

	public Handler(Class<?> controllerClass, Method actionMethod) {
		super();
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
	}

	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public Method getActionMethod() {
		return actionMethod;
	}
	
}

