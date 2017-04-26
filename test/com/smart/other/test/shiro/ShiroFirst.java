//package com.smart.other.test.shiro;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.config.IniSecurityManagerFactory;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.util.Factory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
///** 
// * Filename:     ShiroFirst.java 
// * @Copyright:   Copyright (c)2015 
// * @Company:     chixun 
// * @author:     Cooley
// * @function:
// * @version:     1.0 
// * @Create at:   2016年1月25日 上午9:04:41 
// */
//public class ShiroFirst {
//	private static final Logger LOGGER = LoggerFactory.getLogger(ShiroFirst.class);
//	
//	public static void main(String[] args) {
//		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//		SecurityManager securityManager = factory.getInstance();
//		SecurityUtils.setSecurityManager(securityManager);
//		
//		Subject subject = SecurityUtils.getSubject();
//		
//		//登录
//		UsernamePasswordToken token = new UsernamePasswordToken("shiro", "201312");
//		try {
//			subject.login(token);
//		} catch (AuthenticationException e) {
//			LOGGER.info("登录失败！");
//			return;
//		}
//		
//		
//		LOGGER.info("登录成功！" + subject.getPrincipal());
//		
//		subject.logout();
//	}
//}
//
