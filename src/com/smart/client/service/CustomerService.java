package com.smart.client.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.annotation.Service;
import com.smart.annotation.Transaction;
import com.smart.bean.FileParam;
import com.smart.client.model.Customer;
import com.smart.constant.ConfigConstant;
import com.smart.helper.DatabaseHelper;
import com.smart.helper.UploadHelper;


/** 
 * Filename:     CustomerService.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年11月11日 上午10:01:26 
 */
@Service
public class CustomerService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	/**
	 * 获取客户列表
	 */
	public List<Customer> getCustomerList(String...keyword){
		String sql = "SELECT * FROM customer";
		return  DatabaseHelper.queryEntityList(Customer.class, sql);
	}
	
	/**
	 * 获取客户
	 */
	public Customer getCustomer(long id){
		String sql = "SELECT * FROM customer WHERE id=?";
		return DatabaseHelper.queryEntity(Customer.class, sql, id);
	}
	
	/**
	 * 创建客户
	 * @return
	 */
	@Transaction
	public boolean createCustomer(Map<String, Object> fieldMap, FileParam fileParam){
		boolean result = DatabaseHelper.insertEntity(Customer.class, fieldMap);
		if(result){
			UploadHelper.uploadFile("/tmp/upload/", fileParam);
		}
		return result;
	}
	
	/**
	 * 更新客户
	 */
	@Transaction
	public boolean updateCustomer(long id, Map<String, Object> fieldMap){
		return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
	}
	
	/**
	 * 删除客户
	 */
	@Transaction
	public boolean deleteCustomer(long id){
		return DatabaseHelper.deleteEntity(Customer.class, id);
	}
}

