package com.smart.plugin.security.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.annotation.Aspect;
import com.smart.annotation.Controller;
import com.smart.plugin.security.annotation.User;
import com.smart.plugin.security.exception.AuthzException;
import com.smart.proxy.AspectProxy;


/** 
 * Filename:     Aspect.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:	授权注解切面
 * @version:     1.0 
 * @Create at:   2016年1月28日 上午10:53:19 
 */
@Aspect(Controller.class)
public class AuthzAnnotationAspect extends AspectProxy{
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthzAnnotationAspect.class);
	
	private static final Class[] ANNOTATION_CLASS_ARRAY = {
		User.class
	};
	
	@Override
	public void before(Class<?> cls, Method method, Object[] params)
			throws Throwable {
		Annotation annotation = getAnnotation(cls, method);
		if(annotation != null){
			Class<?> annotationType = annotation.annotationType();
			if(annotationType.equals(User.class)){
				handleUser();
			}
		}
	}
	
	private void handleUser() throws AuthzException {
		Subject currentUser = SecurityUtils.getSubject();
		PrincipalCollection principals = currentUser.getPrincipals();
		if(principals == null || principals.isEmpty()){
			throw new AuthzException("当前用户未登录,currentUser:" + currentUser);
		}
	}

	@SuppressWarnings("unchecked")
	private Annotation getAnnotation(Class<?> cls, Method method){
		//遍历所有授权注解
		for(Class<? extends Annotation> annotationClass : ANNOTATION_CLASS_ARRAY){
			//判断目标方法是否带授权注解
			if(method.isAnnotationPresent(annotationClass)){
				return method.getAnnotation(annotationClass);
			}
			
			//判断目标类是否带授权注解
			if(cls.isAnnotationPresent(annotationClass)){
				return cls.getAnnotation(annotationClass);
			}
		}
		
		//均没有授权注解,返回空
		return null;
	}
	
	
	
}

