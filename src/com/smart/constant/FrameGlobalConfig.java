package com.smart.constant;

import java.util.HashMap;
import java.util.Map;

import com.smart.plugin.security.exception.AuthcException;
import com.smart.plugin.security.exception.AuthzException;


/** 
 * Filename:     GlobalExceptionMap.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:	全局异常信息,用在DispatcherServlet.java
 * @version:     1.0 
 * @Create at:   2016年1月28日 下午1:43:14 
 */
public class FrameGlobalConfig {
	public static final Map<Class<? extends Throwable>, String> GlobalExceptionMap 	= new HashMap<Class<? extends Throwable>, String>();
	
	static{
		GlobalExceptionMap.put(AuthzException.class, "login.jsp");
		GlobalExceptionMap.put(AuthcException.class, "login.jsp");
	}
	
}

