package com.smart.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
 * Filename:     ProxyAspect.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月9日 下午3:11:24 
 */
public abstract class AspectProxy implements Proxy {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);
	
	@Override
	public final Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		
		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		
		begin();
		try {
			if(intercept(cls, method, params)){
				before(cls, method, params);
				result = proxyChain.doProxyChain();
				after(cls, method, params);
			}else{
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			LOGGER.error("proxy failure", e);
			error(cls, method, params);
			throw e;
		} finally{
			end();
		}
		
		return result;
	}

	public void begin() { }
	
	public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable{
		return true;
	}
	
	
	public void before(Class<?> cls, Method method, Object[] params) throws Throwable{
		
	}
	
	public void after(Class<?> cls, Method method, Object[] params) throws Throwable{
		
	}
	
	public void error(Class<?> cls, Method method, Object[] params) throws Throwable{
		
	}
	
	public void end() { }
}

