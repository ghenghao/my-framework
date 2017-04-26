package com.smart.client.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.annotation.Action;
import com.smart.annotation.Controller;
import com.smart.annotation.Inject;
import com.smart.bean.FileParam;
import com.smart.bean.Param;
import com.smart.bean.View;
import com.smart.client.model.Customer;
import com.smart.client.service.CustomerService;
import com.smart.plugin.security.annotation.User;


/** 
 * Filename:     CustomerController.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月8日 下午2:18:52 
 */
@Controller
public class CustomerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	@Inject
	private CustomerService customerService;
	
	/**
	 * 进入 客户列表 界面
	 * @return
	 */
	@Action("get:/customer")
	public View index(){
		List<Customer> customerList = customerService.getCustomerList();
		return new View("customer.jsp").addModel("customerList", customerList);
	}
	
	/**
	 * 进入创建用户界面
	 * @return
	 */
	@Action("get:/customer_create")
	public View createCustomer(){
		return new View("customer_create.jsp");
	}
	
	/**
	 * 处理创建客户请求
	 * @param param
	 * @return
	 */
	@Action("post:/customer_create")
	@User
	public View createSubmit(Param param){
		Map<String, Object> fieldMap = param.getFieldMap();
		FileParam fileParam = param.getFile("photo");
/*		fieldMap = new HashMap<String, Object>();
		fieldMap.put("name", "customerController");
		fieldMap.put("contact", "Controller");
		fieldMap.put("telephone", "1356323217");*/
		boolean result = customerService.createCustomer(fieldMap, fileParam);
		if(result){
			return index();
		}else{
			LOGGER.error("添加失败");
			return new View("error.jsp").addModel("errMsg", "添加用户");
		}
//		return new Data(result);
	}
	
	/**
	 * 删除客户
	 * @param param
	 */
	@Action("get:/customer_delete")
	@User
	public View deleteCustomer(Param param){
		long id = param.getLong("id");
		boolean result = customerService.deleteCustomer(id);
		if(result){
			return index();
		}else{
			LOGGER.error("删除失败,id:" + id);
			return new View("error.jsp").addModel("errMsg", "删除用户");
		}
	}
}

