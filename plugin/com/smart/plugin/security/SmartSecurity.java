package com.smart.plugin.security;

import java.util.Set;


/** 
 * Filename:     SmartSecurity.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年1月25日 下午5:02:28 
 */
public interface SmartSecurity {
	/**
	 * 根据用户名获取密码
	 * @param userName
	 * @return
	 */
	String getPassword(String userName);
	
	/**
	 * 根据用户名获取角色名集合
	 * @param userName
	 * @return
	 */
	Set<String> getRoleNameSet(String userName);
	
	/**
	 * 根据角色名获取权限名集合
	 * @param roleName
	 * @return
	 */
	Set<String> getPermissionNameSet(String roleName);
}

