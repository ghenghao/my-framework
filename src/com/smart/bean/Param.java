package com.smart.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smart.utils.CastUtil;
import com.smart.utils.CollectionUtil;
import com.smart.utils.StringUtil;

/**
 * 封装请求参数
 *
 * @author 
 * @since 2.2
 */
public class Param {

	private List<FormParam> formParamList;
	
	private List<FileParam> fileParamList;
	

	public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
		super();
		this.formParamList = formParamList;
		this.fileParamList = fileParamList;
	}


	public Param(List<FormParam> formParamList) {
		super();
		this.formParamList = formParamList;
	}
	
	/**
	 * 获取请求参数映射
	 * @return
	 */
	public Map<String, Object> getFieldMap(){
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		if(CollectionUtil.isNotEmpty(formParamList)){
			for(FormParam formParam : formParamList){
				String fieldName = formParam.getFieldName();
				Object fieldValue = formParam.getFieldValue();
				if(fieldMap.containsKey(fieldName)){
					fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
				}
				fieldMap.put(fieldName, fieldValue);
			}
		}
		
		return fieldMap;
	}
	
	/**
	 * 获取上传文件映射
	 * @return
	 */
	public Map<String, List<FileParam>> getFileMap() {
		Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
		if(CollectionUtil.isNotEmpty(fileParamList)){
			for(FileParam fileParam : fileParamList){
				String fieldName = fileParam.getFieldName();
				List<FileParam> fileParams;
				if(fileMap.containsKey(fieldName)) {
					fileParams = fileMap.get(fieldName);
				}else{
					fileParams = new ArrayList<FileParam>();
				}
				fileParams.add(fileParam);
				fileMap.put(fieldName, fileParams);
			}
		}
		return fileMap;
	}
	
	/**
	 * 获取所有上传文件
	 */
	public List<FileParam> getFileList(String fieldName){
		return getFileMap().get(fieldName);
	}
	
	/**
	 * 获取唯一上传文件
	 */
    public FileParam getFile(String fieldName){
    	List<FileParam> fileParamList = getFileList(fieldName);
    	if(CollectionUtil.isNotEmpty(fileParamList)){
    		if(fileParamList.size()==1){
    			return fileParamList.get(0);
    		}else{
    			throw new RuntimeException("the upload files number is more than 1");
    		}
    	}else{
    		return null;
    	}
    }
    
    /**
     * 验证参数是否为空
     * @return
     */
    public boolean isEmpty(){
    	return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamList);
    }
    
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }

    public double getDouble(String name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    public long getLong(String name) {
        return CastUtil.castLong(getFieldMap().get(name));
    }

    public int getInt(String name) {
        return CastUtil.castInt(getFieldMap().get(name));
    }
    
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }
}
