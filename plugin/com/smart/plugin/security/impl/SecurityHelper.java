package com.smart.plugin.security.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.plugin.security.exception.AuthcException;


/** 
 * Filename:     SecurityHelper.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年1月25日 下午5:50:45 
 */
public final class SecurityHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 */
	public static void login(String username, String password) throws AuthcException{
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser != null){
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			try {
				currentUser.login(token);
			} catch (AuthenticationException e) {
				LOGGER.error("login failure! username:"+ username, e);
				throw new AuthcException(e);
			}
		}
	}

	/**
	 * 注销
	 */
	public static void logout(){
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser != null){
			currentUser.logout();
		}
	}
}

