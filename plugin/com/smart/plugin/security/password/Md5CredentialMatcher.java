package com.smart.plugin.security.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import com.smart.utils.CodecUtil;


/** 
 * Filename:     Md5CredentialMatcher.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年1月26日 上午10:58:43 
 */
public class Md5CredentialMatcher implements CredentialsMatcher{

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		//获取表单提交的密码明文
		String submitted = String.valueOf( ((UsernamePasswordToken)token).getPassword() );
		String encryted = String.valueOf((char[])info.getCredentials());
		return CodecUtil.encryptMD5(submitted).equals(encryted);
	}
}

