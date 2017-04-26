package com.smart.plugin.security.impl;

import com.smart.helper.ConfigHelper;
import com.smart.plugin.security.SecurityConstant;
import com.smart.plugin.security.SmartSecurity;
import com.smart.utils.ReflectionUtil;


/** 
 * Filename:     SecurityConfig.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年1月26日 上午10:03:28 
 */
public final class SecurityConfig {
	public static String getRealms(){
		return ConfigHelper.getString(SecurityConstant.REALMS);
	}
	
	public static SmartSecurity getSmartSecurity(){
		String className = ConfigHelper.getString(SecurityConstant.SMART_SECURITY);
		return (SmartSecurity) ReflectionUtil.newInstance(className);
	}
	
	public static String getJdbcAuthcQuery(){
		return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
	}
	
	public static String getJdbcRolesQuery(){
		return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
	}

	public static String getJdbcPermissionsQuery(){
		return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
	}
	
	public static boolean isCacheable(){
		return ConfigHelper.getBoolean(SecurityConstant.CACHEABLE);
	}
}

