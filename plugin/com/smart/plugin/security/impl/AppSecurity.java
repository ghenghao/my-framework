package com.smart.plugin.security.impl;

import java.util.HashSet;
import java.util.Set;

import com.smart.helper.DatabaseHelper;
import com.smart.plugin.security.SmartSecurity;


/** 
 * Filename:     AppSecurity.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年1月25日 下午5:05:40 
 */
public class AppSecurity implements SmartSecurity{

	@Override
	public String getPassword(String userName) {
		String sql = "select password from user where username=?";
		return DatabaseHelper.queryEntity(String.class, sql, userName);
	}

	@Override
	public Set<String> getRoleNameSet(String userName) {
		String sql = "select r.role_name from user u, user_role ur, role r where u.id=ur.user_id and r.id=ur.role_id and u.username=?";
		return DatabaseHelper.queryEntity(HashSet.class, sql, userName);
	}

	@Override
	public Set<String> getPermissionNameSet(String roleName) {
		String sql = "select p.permission_name from role r, role_permission rp, permission p where r.id=rp.role_id and p.id=rp.permission_id and r.role_name=?";
		return DatabaseHelper.queryEntity(HashSet.class, sql, roleName);
	}
	
}

