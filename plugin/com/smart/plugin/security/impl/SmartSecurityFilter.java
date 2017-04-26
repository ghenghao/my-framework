package com.smart.plugin.security.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import com.smart.plugin.security.SecurityConstant;
import com.smart.plugin.security.SmartSecurity;
import com.smart.plugin.security.realm.SmartCustomRealm;
import com.smart.plugin.security.realm.SmartJdbcRealm;


/** 
 * Filename:     SmartSecurityFilter.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年1月26日 上午9:36:42 
 */
public class SmartSecurityFilter extends ShiroFilter{
	@Override
	public void init() throws Exception {
		super.init();
		WebSecurityManager webSecurityManager = super.getSecurityManager();
		//设置Realm,可同时支持多个Realm，按照先后顺序用逗号分隔
		setRealms(webSecurityManager);
		//设置Cache
		setCache(webSecurityManager);
	}
	
	
	private void setRealms(WebSecurityManager webSecurityManager){
		//读取smart.plugin.security.realm配置项
		String securityRealms = SecurityConfig.getRealms();
		if(securityRealms != null){
			//根据逗号拆分
			String[] securityRealmArray = securityRealms.split(",");
			if(securityRealmArray.length > 0){
				//使Realm具有唯一性与顺序性
				Set<Realm> realms = new LinkedHashSet<Realm>();
				for(String securityRealm : securityRealmArray){
					if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)){
						//添加基于JDBC的Realm,需要配置相关SQL查询语句
						addJdbcRealm(realms);
					}else if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)){
						addCustomRealm(realms);
					}
				}
				RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
				realmSecurityManager.setRealms(realms);
			}
		}else{
			throw new RuntimeException("未指定realm");
		}
	}
	
	
	private void addJdbcRealm(Set<Realm> realms){
		//添加自己实现的基于JDBC的realm
		SmartJdbcRealm smartJdbcRealm = new SmartJdbcRealm();
		realms.add(smartJdbcRealm);
	}
	
	private void addCustomRealm(Set<Realm> realms){
		//读取smart.plugin.security.custom.class配置项
		SmartSecurity smartSecurity = SecurityConfig.getSmartSecurity();
		//添加自己实现的realm
		SmartCustomRealm smartCustomRealm = new SmartCustomRealm(smartSecurity);
		realms.add(smartCustomRealm);
	}
		
	private void setCache(WebSecurityManager webSecurityManager){
		//读取smart.plugin.security.cache配置项
		//TODO
		if(SecurityConfig.isCacheable()){
			CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
			//使用基于内存的CacheManager
			CacheManager cacheManager = new MemoryConstrainedCacheManager();
			cachingSecurityManager.setCacheManager(cacheManager);
		}
	}
}

