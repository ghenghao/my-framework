package com.smart.proxy;


/** 
 * Filename:     Proxy.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月9日 下午1:49:05 
 */
public interface Proxy {
	Object doProxy(ProxyChain proxyChain) throws Throwable;
}

