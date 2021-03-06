package com.smart.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/** 
 * Filename:     Inject.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:	依赖注入注解
 * @version:     1.0 
 * @Create at:   2015年11月3日 下午2:42:39 
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
	
}

