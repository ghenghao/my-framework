package com.smart.plugin.security.exception;


/** 
 * Filename:     AuthzException.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:	授权异常（无权限时抛出）
 * @version:     1.0 
 * @Create at:   2016年1月26日 上午8:57:57 
 */
public class AuthzException extends Exception {

	public AuthzException() {
		super();
	}

	public AuthzException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthzException(String message) {
		super(message);
	}

	public AuthzException(Throwable cause) {
		super(cause);
	}
	
}

