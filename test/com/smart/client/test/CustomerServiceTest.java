package com.smart.client.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.smart.client.model.Customer;
import com.smart.client.service.CustomerService;
import com.smart.helper.DatabaseHelper;


/** 
 * Filename:     CustomerServiceTest.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年11月11日 上午10:08:07 
 */
public class CustomerServiceTest {
	
	private final CustomerService customerService;
	
	public CustomerServiceTest(){
		customerService = new CustomerService();
	}
	
	@Before
	public void init(){
		DatabaseHelper.executeSqlFile("customer_init.sql");
	}
	
	@After
	public void destroy(){
		DatabaseHelper.executeUpdate("TRUNCATE customer");
	}
	
	@Test
	public void testGetCustomerList() {
		List<Customer> customerList = customerService.getCustomerList();
		assertEquals(2, customerList.size());
	}

	@Test
	public void testGetCustomer() {
		long id = 1;
		Customer customer = customerService.getCustomer(id);
		assertNotNull(customer);
	}

	@Test
	public void testCreateCustomer() {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		fieldMap.put("name", "customer100");
		fieldMap.put("contact", "John");
		fieldMap.put("telephone", "1356323217");
		boolean result = customerService.createCustomer(fieldMap, null);
		assertTrue(result);
	}

	@Test
	public void testUpdateCustomer() {
		long id = 1;
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		fieldMap.put("contact", "Eric");
		boolean result = customerService.updateCustomer(id, fieldMap);
		assertTrue(result);
	}

	@Test
	public void testDeleteCustomer() {
		long id = 1;
		boolean result = customerService.deleteCustomer(id);
		assertTrue(result);
	}

}

