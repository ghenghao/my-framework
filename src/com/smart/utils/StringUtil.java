package com.smart.utils;

import org.apache.commons.lang.StringUtils;


/** 
 * Filename:     StringUtil.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015��11��2�� ����2:41:50 
 */
public final class StringUtil {
	
	public static final String SEPARATOR = String.valueOf((char) 29);
	
	public static boolean isEmpty(String str) {
		if(str != null){
			str = str.trim();
		}
		return StringUtils.isEmpty(str);
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	  /**
     * 分割固定格式的字符串
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }
    
    public static String arr2Str(Object[] objArr, String seperator){
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i<objArr.length; i++){
    		sb.append(objArr[i]);
    		if(i != objArr.length - 1){
    			sb.append(seperator);
    		}
    	}
    	return sb.toString();
    }
}

