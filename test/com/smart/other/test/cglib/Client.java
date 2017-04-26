package com.smart.other.test.cglib;


/** 
 * Filename:     Client.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月22日 下午2:55:52 
 */
public class Client {
	public static void main(String[] args) {
		GreetImpl instance = CGLibDynamicProxy.getInstance().getProxy(GreetImpl.class);
		instance.sayHello("Kalc");
	}
}

