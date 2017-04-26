package com.smart.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.smart.annotation.Action;
import com.smart.bean.Handler;
import com.smart.bean.Request;
import com.smart.utils.ArrayUtil;
import com.smart.utils.CollectionUtil;


/** 
 * Filename:     ControllerHelper.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月8日 下午3:35:55 
 */
public final class ControllerHelper {
	
	private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();
	
	static{
		//获取所有Controller类
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		
		if(CollectionUtil.isNotEmpty(controllerClassSet)){
			//遍历所有Controller类
			for(Class<?> controllerClass : controllerClassSet){
				//获取Controller中定义的所有方法
				Method[] controllerMethods = controllerClass.getDeclaredMethods();
				if(ArrayUtil.isNotEmpty(controllerMethods)){
					//遍历所有方法
					for(Method controllerMethod : controllerMethods){
						//判断是否带有Action注解
						if(controllerMethod.isAnnotationPresent(Action.class)){
							//从Action注解获取URL映射规则
							Action action = controllerMethod.getAnnotation(Action.class);
							String mapping = action.value();
							//验证URL规则
							if(mapping.matches("\\w+:/\\w*")){
								String[] array = mapping.split(":");
								if(ArrayUtil.isNotEmpty(array) && array.length == 2){
									//获取请求方法与路径
									String requestMethod = array[0];
									String requestPath = array[1];
									Request request = new Request(requestMethod, requestPath);
									Handler handler = new Handler(controllerClass, controllerMethod);
									//初始化ACTION_MAP
									ACTION_MAP.put(request, handler);
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 获取Handler
	 */
	public static Handler getHandler(String requestMethod, String requestPath){
		Request request = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
}

