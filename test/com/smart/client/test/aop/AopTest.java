package com.smart.client.test.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.smart.proxy.AspectProxy;
import com.smart.proxy.Proxy;
import com.smart.proxy.ProxyManager;


/** 
 * Filename:     AopTest.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2016年3月1日 上午9:21:19 
 */
public class AopTest {
	@Test
	public void testAop(){
		List<Proxy> proxyList = new ArrayList<Proxy>();
		proxyList.add(new ProxyA());
		proxyList.add(new ProxyB());
		proxyList.add(new ProxyC());
		proxyList.add(new ProxyD());
		TargetClass proxyClass = ProxyManager.createProxy(TargetClass.class, proxyList);
		proxyClass.print();
	}
	
	
	static class TargetClass{
		public void print(){
			System.out.println("this is target class...");
		}
	}
	
	
	static class ProxyA extends AspectProxy{
		@Override
		public void before(Class<?> cls, Method method, Object[] params)
				throws Throwable {
			System.out.println("-- smart aop before A--");
			super.before(cls, method, params);
		}
		
		@Override
		public void after(Class<?> cls, Method method, Object[] params)
				throws Throwable {
			System.out.println("-- smart aop after A--");
			super.after(cls, method, params);
		}
	}
	
	static class ProxyC extends AspectProxy{
		@Override
		public void before(Class<?> cls, Method method, Object[] params)
				throws Throwable {
			System.out.println("-- smart aop before C--");
			super.before(cls, method, params);
		}
	}
	
	static class ProxyB extends AspectProxy{
		@Override
		public void after(Class<?> cls, Method method, Object[] params)
				throws Throwable {
			System.out.println("-- smart aop after B--");
			super.before(cls, method, params);
		}
	}
	
	static class ProxyD extends AspectProxy{
		@Override
		public void after(Class<?> cls, Method method, Object[] params)
				throws Throwable {
			System.out.println("-- smart aop after D--");
			super.before(cls, method, params);
		}
	}
}

