package com.smart.plugin.security.exception;


/** 
 * Filename:     AuthcException.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:	认证异常（非法访问时抛出）
 * @version:     1.0 
 * @Create at:   2016年1月26日 上午8:54:40 
 */
public class AuthcException extends Exception{
	public AuthcException(){
		super();
	}
	
	public AuthcException(String message){
		super(message);
	}
	
	public AuthcException(String message, Throwable cause){
		super(message, cause);
	}
	
	public AuthcException(Throwable cause){
		super(cause);
	}
	
}

