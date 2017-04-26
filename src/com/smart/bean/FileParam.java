package com.smart.bean;

import java.io.InputStream;


/** 
 * Filename:     FileParam.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月16日 上午10:54:12 
 */
public class FileParam {
	private String fieldName;	//表单字段名
	private String fileName;		//上传文件的文件名
	private long fileSize;			//上传文件的文件大小
	private String contentType;	
	private InputStream inputStream;
	
	
	public String getFieldName() {
		return fieldName;
	}
	public String getFileName() {
		return fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public String getContentType() {
		return contentType;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	
	
	public FileParam(String fieldName, String fileName, long fileSize,
			String contentType, InputStream inputStream) {
		super();
		this.fieldName = fieldName;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.inputStream = inputStream;
	}
}

