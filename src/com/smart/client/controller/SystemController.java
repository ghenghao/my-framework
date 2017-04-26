package com.smart.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.annotation.Action;
import com.smart.annotation.Controller;
import com.smart.bean.Param;
import com.smart.bean.View;
import com.smart.plugin.security.exception.AuthcException;
import com.smart.plugin.security.impl.SecurityHelper;


/** 
 * Filename:     SystemController.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:  处理系统请求
 * @version:     1.0 
 * @Create at:   2016年1月25日 下午5:41:21 
 */
@Controller
public class SystemController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);
	
	/**
	 * 首页
	 * @return
	 */
	@Action("get:/")
	public View index(){
		return new View("index.jsp");
	}
	
	/**
	 * 登录页
	 * @return
	 */
	@Action("get:/login")
	public View login(){
		return new View("login.jsp");
	}
	
	/**
	 * 登录表单
	 * @param param
	 * @return
	 */
	@Action("post:/login")
	public View loginSubmit(Param param){
		String username = param.getString("username");
		String password = param.getString("password");
		try {
			SecurityHelper.login(username, password);
		} catch (AuthcException e) {
			LOGGER.error("login failure, username:" + username, e);
			return new View("/login");
		}
		return new View("/customer");
	}
	
	/**
	 * 注销请求
	 * @return
	 */
	public View logout(){
		SecurityHelper.logout();
		return new View("/");
	}
}

