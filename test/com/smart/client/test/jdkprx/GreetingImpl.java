package com.smart.client.test.jdkprx;


/** 
 * Filename:     Greeting.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月22日 下午3:01:23 
 */
public class GreetingImpl implements Greeting{
	@Override
	public void sayHello(String name){
		System.out.println("Hello : " + name);
	}
}

