package com.smart.helper;

import java.lang.reflect.Field;
import java.util.Map;

import com.smart.annotation.Inject;
import com.smart.utils.ArrayUtil;
import com.smart.utils.CollectionUtil;
import com.smart.utils.ReflectionUtil;


/** 
 * Filename:     IocHelper.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:  
 * @author:     Cooley
 * @function:	依赖注入
 * @version:     1.0 
 * @Create at:   2015年12月8日 下午2:55:38 
 */
public final class IocHelper {
	
	static{
		//获取所有Bean类与Bean实例之间映射关系(即BeanMap)
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
		if(CollectionUtil.isNotEmpty(beanMap)){
			//遍历BeanMap
			for(Map.Entry<Class<?>, Object>beanEntry : beanMap.entrySet()){
				//从BeanMap获取Bean类与Bean实例
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				//获取Bean定义的所有成员变量
				Field[] beanFields = beanClass.getDeclaredFields();
				if(ArrayUtil.isNotEmpty(beanFields)){
					//遍历Bean Field
					for(Field beanField : beanFields){
						//判断当前Bean Field是否带有Inject注解
						if(beanField.isAnnotationPresent(Inject.class)){
							//在Bean Map中获取Bean Field对应的实例
							Class<?> beanFieldClass = beanField.getType();
							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if(beanFieldInstance != null){
								//通过反射初始化BeanField的值，完成注入
								ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
	
}

