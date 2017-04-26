package com.smart.bean;


/** 
 * Filename:     FormParam.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月16日 上午10:57:29 
 */
public class FormParam {
	private String fieldName;
	private Object fieldValue;
	
	public FormParam(String fieldName, Object fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	
	
	
	
}

