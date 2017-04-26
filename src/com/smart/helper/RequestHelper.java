package com.smart.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.smart.bean.FormParam;
import com.smart.bean.Param;
import com.smart.utils.ArrayUtil;
import com.smart.utils.CodecUtil;
import com.smart.utils.StreamUtil;
import com.smart.utils.StringUtil;


/** 
 * Filename:     RequestHelper.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月18日 下午2:30:33 
 */
public class RequestHelper {
	
	/**
	 * 创建请求对象
	 * @return
	 * @throws IOException 
	 */
	public static Param createParam(HttpServletRequest request) throws IOException{
		
		List<FormParam> formParamList = new ArrayList<FormParam>();
		formParamList.addAll(parseParameterNames(request));
		formParamList.addAll(parseInputStream(request));
		return new Param(formParamList);
	}


	private static List<FormParam> parseParameterNames(
			HttpServletRequest request) {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()){
			String fieldName = paramNames.nextElement();
			String[] fieldValues = request.getParameterValues(fieldName);
			if(ArrayUtil.isNotEmpty(fieldValues)){
				Object fieldValue;
				if(fieldValues.length == 1){
					fieldValue = fieldValues[0];
				}else{
					fieldValue = StringUtil.arr2Str(fieldValues, StringUtil.SEPARATOR);
				}
				formParamList.add(new FormParam(fieldName, fieldValue));
			}
		}
		return formParamList;
	}
	
	
	private static List<FormParam> parseInputStream(
			HttpServletRequest request) throws IOException {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
		if(StringUtil.isNotEmpty(body)){
			String[] kvs = StringUtil.splitString(body, "&");
			if(ArrayUtil.isNotEmpty(kvs)){
				for(String kv : kvs){
					String[] array = StringUtil.splitString(kv, "=");
					if(ArrayUtil.isNotEmpty(array) && array.length == 2){
						String fieldName = array[0];
						String fieldValue = array[1];
						formParamList.add(new FormParam(fieldName, fieldValue));
					}
				}
			}
		}
		return formParamList;
	}
}

