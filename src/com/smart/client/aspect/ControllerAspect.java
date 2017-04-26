package com.smart.client.aspect;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.annotation.Aspect;
import com.smart.annotation.Controller;
import com.smart.proxy.AspectProxy;


/** 
 * Filename:     ControllerAspect.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月9日 下午3:34:35 
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
	
	private long begin;
	
	@Override
	public void before(Class<?> cls, Method method, Object[] params)
			throws Throwable {
		LOGGER.debug("--------------begin-----------------");
		LOGGER.debug(String.format("class: %s", cls.getName()));
		LOGGER.debug(String.format("method: %s", method.getName()));
		begin = System.currentTimeMillis();
	}
	
	@Override
	public void after(Class<?> cls, Method method, Object[] params)
			throws Throwable {
		LOGGER.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
		LOGGER.debug("------------------ end ---------------");
	}
}

