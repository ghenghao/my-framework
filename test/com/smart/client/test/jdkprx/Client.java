package com.smart.client.test.jdkprx;


/** 
 * Filename:     Client.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月22日 下午3:51:45 
 */
public class Client {
	public static void main(String[] args) {
		JDKProxy jdkProxy = new JDKProxy(new GreetingImpl());
		Greeting greeting = jdkProxy.getProxy();
		greeting.sayHello("JACK");
	}
}

