package com.smart.plugin.security.impl;

import java.util.Set;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.shiro.web.env.EnvironmentLoaderListener;


/** 
 * Filename:     SmartSecurityPlugin.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:	SmartSecurity在Servlet初始化时加入的参数,见META-INF/services/javax.servlet.ServletContainerInitializer
 * @version:     1.0 
 * @Create at:   2016年1月26日 上午9:23:04 
 */
public class SmartSecurityPlugin implements ServletContainerInitializer{

	@Override
	public void onStartup(Set<Class<?>> handlesType, ServletContext servletContext)
			throws ServletException {
		//初始化参数
		servletContext.setInitParameter("shiroConfigLocations", "classpath:smart-security.ini");
		//注册Listener
		servletContext.addListener(EnvironmentLoaderListener.class);
		//注册Filter
		Dynamic smartSecurityFilter = servletContext.addFilter("SmartSecurityFilter", SmartSecurityFilter.class);
		smartSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
	}
	
}

