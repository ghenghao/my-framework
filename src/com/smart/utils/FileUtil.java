package com.smart.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
 * Filename:     FileUtil.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月16日 下午2:40:09 
 */
public final class FileUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * 获取真实文件名
	 */
	public static String getRealFileName(String fileName){
		return FilenameUtils.getName(fileName);
	}
	
	/**
	 * 创建文件
	 */
	public static File createFile(String filePath){
		File file;
		try {
			file = new File(filePath);
			File parentDir = file.getParentFile();
			if(!parentDir.exists()){
				FileUtils.forceMkdir(parentDir);
			}
		} catch (Exception e) {
			LOGGER.error("create file failure", e);
			throw new RuntimeException(e);
		}
		return file;
	}
	
}

