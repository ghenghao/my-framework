package com.smart.plugin.security.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.smart.plugin.security.SecurityConstant;
import com.smart.plugin.security.SmartSecurity;
import com.smart.plugin.security.password.Md5CredentialMatcher;


/** 
 * Filename:     SmartCustomRealm.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年1月26日 上午11:05:29 
 */
public class SmartCustomRealm extends AuthorizingRealm{

	private final SmartSecurity smartSecurity;
	
	public SmartCustomRealm(SmartSecurity smartSecurity){
		this.smartSecurity = smartSecurity;
		super.setName(SecurityConstant.REALMS_CUSTOM);
		super.setCredentialsMatcher(new Md5CredentialMatcher());	//MD5加密算法
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(principals == null){
			throw new AuthenticationException("parameter principals is null");
		}
		
		//获取已认证的用户名
		String userName = (String) super.getAvailablePrincipal(principals);
		
		//通过smartSecurity根据用户名获得角色集合
		Set<String> roleNameSet = smartSecurity.getRoleNameSet(userName);
		
		//通过smartSecurity根据角色名获取与其对应的权限名集合
		Set<String> permissionNameSet = new HashSet<String>();
		if(roleNameSet != null && roleNameSet.size()>0){
			for(String roleName : roleNameSet){
				Set<String> currentPermissionNameSet = smartSecurity.getPermissionNameSet(roleName);
				permissionNameSet.addAll(currentPermissionNameSet);
			}
		}
		
		//将角色名集合与权限名集合放入对象中，便于后续的授权操作
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roleNameSet);
		authorizationInfo.setStringPermissions(permissionNameSet);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		if(token == null){
			throw new AuthenticationException("parameter token is null");
		}
		
		//通过AuthenticationToken对象获取从表单中提交过来的用户名
		String userName = ((UsernamePasswordToken)token).getUsername();
		
		//获取存放于数据库中的密码
		String password = smartSecurity.getPassword(userName);
		
		//用户名密码放入AuthenticationInfo对象，便于后续的认证
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
		authenticationInfo.setPrincipals(new SimplePrincipalCollection(userName, super.getName()));
		authenticationInfo.setCredentials(password);
		
		return authenticationInfo;
	}

}

