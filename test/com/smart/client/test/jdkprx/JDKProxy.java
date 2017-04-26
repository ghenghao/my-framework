package com.smart.client.test.jdkprx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/** 
 * Filename:     JDKProxy.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月22日 下午3:36:33 
 */
public class JDKProxy implements InvocationHandler{

	private Object target;

	public JDKProxy(Object target){
		this.target = target;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(){
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), 
				this);
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		before();
		Object result = method.invoke(target, args);
		after();
		return result;
	}
	
	public void after() {
		System.out.println("jdkprx-------after");
	}

	public void before() {
		System.out.println("jdkprx-------before");
	}

}

