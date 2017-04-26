package com.smart.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
 * Filename:     ServletHelper.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月21日 上午10:58:09 
 */
public final class ServletHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);
	
	/**
	 * 每个线程维护自己的ServletHelper
	 */
	private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER
		= new ThreadLocal<ServletHelper>();
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ServletHelper(HttpServletRequest request,
			HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	
	/**
	 * 初始化
	 */
	public static void init(HttpServletRequest request,
			HttpServletResponse response){
		SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
	}

	/**
	 * 销毁
	 * destroy
	 */
	public static void destroy(){
		SERVLET_HELPER_HOLDER.remove();
	}
	
	/**
	 * 获取request对象
	 */
	public static HttpServletRequest getRequest(){
		return SERVLET_HELPER_HOLDER.get().request;
	}

	/**
	 * 获取response对象
	 */
	public static HttpServletResponse getResponse(){
		return SERVLET_HELPER_HOLDER.get().response;
	}

	
	/**
	 * 获取session对象
	 */
	public static HttpSession getSession(){
		return getRequest().getSession();
	}
	
	
	/**
	 * 获取ServletContext对象
	 */
	//TODO 
	public static ServletContext getServletContext(){
		return getRequest().getServletContext();
	}
	
	
}

