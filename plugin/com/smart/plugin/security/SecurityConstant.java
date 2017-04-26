package com.smart.plugin.security;


/** 
 * Filename:     SecurityConstant.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年1月26日 上午10:10:22 
 */
public interface SecurityConstant {
	
	String REALMS = "smart.plugin.security.realms";
	String REALMS_JDBC = "jdbc";
	String REALMS_CUSTOM = "custom";
	
	String SMART_SECURITY = "smart.plugin.security.custom.class";
	
	String JDBC_AUTHC_QUERY = "smart.plugin.security.jdbc.authc_query";
	String JDBC_ROLES_QUERY = "smart.plugin.security.jdbc.roles_query";
	String JDBC_PERMISSIONS_QUERY = "smart.plugin.security.jdbc.permissions_query";
	
	String CACHEABLE = "smart.plugin.security.cache";
	
}

