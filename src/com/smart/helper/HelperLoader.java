package com.smart.helper;

import com.smart.utils.ClassUtil;


/** 
 * Filename:     HelperLoader.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月8日 下午3:54:46 
 */
public final class HelperLoader {
	
	public static void init(){
		Class<?>[] classList = {
			ClassHelper.class,
			BeanHelper.class,
			AopHelper.class,
			IocHelper.class,
			ControllerHelper.class
		};
		for(Class<?> cls : classList){
			ClassUtil.loadClass(cls.getName(), true);
		}
	}
}

