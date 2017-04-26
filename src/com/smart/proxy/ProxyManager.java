package com.smart.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


/** 
 * Filename:     ProxyManager.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月9日 下午3:00:33 
 */
@SuppressWarnings("unchecked")
public class ProxyManager {
	
	public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
					MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(targetClass, targetObject, targetMethod,
						methodProxy, methodParams, proxyList)
						.doProxyChain();
			}
		});
	}
}

