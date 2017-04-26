package com.smart.plugin.security.realm;

import org.apache.shiro.realm.jdbc.JdbcRealm;

import com.smart.helper.DatabaseHelper;
import com.smart.plugin.security.impl.SecurityConfig;
import com.smart.plugin.security.password.Md5CredentialMatcher;


/** 
 * Filename:     SmartJdbcRealm.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年1月26日 上午10:54:19 
 */
public class SmartJdbcRealm extends JdbcRealm{
	public SmartJdbcRealm(){
		super.setDataSource(DatabaseHelper.getDataSource());
		super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
		super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
		super.setPermissionsQuery(SecurityConfig.getJdbcPermissionsQuery());
		super.setPermissionsLookupEnabled(true);
		super.setCredentialsMatcher(new Md5CredentialMatcher());	//使用MD5加密算法
	}
}

