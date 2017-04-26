package com.smart.other.test.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


/** 
 * Filename:     CglibProxyTest.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月22日 下午2:32:11 
 */
public class CGLibDynamicProxy implements MethodInterceptor{

	private static CGLibDynamicProxy instance = new CGLibDynamicProxy();
	
	private CGLibDynamicProxy(){
		
	}
	
	public static CGLibDynamicProxy getInstance(){
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> cls){
		return (T) Enhancer.create(cls, this);
	}
	
	
	@Override
	public Object intercept(Object target, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		before();
		Object result = proxy.invokeSuper(target, args);
		after();
		return result;
	}

	public void after() {
		System.out.println("cg-------after");
	}

	public void before() {
		System.out.println("cg-------before");
	}
	
}

