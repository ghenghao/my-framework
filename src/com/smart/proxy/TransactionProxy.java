package com.smart.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.annotation.Transaction;
import com.smart.helper.DatabaseHelper;


/** 
 * Filename:     TransactionProxy.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月10日 下午4:40:21 
 */
public class TransactionProxy implements Proxy {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
	
	private static final ThreadLocal<Boolean> FLAG_HOLDER 
		= new ThreadLocal<Boolean>(){
		protected Boolean initialValue() {
			return false;
		};
	};
	
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;
		Boolean flag = FLAG_HOLDER.get();
		Method method = proxyChain.getTargetMethod();
		
		if(!flag && method.isAnnotationPresent(Transaction.class)){
			FLAG_HOLDER.set(true);
			try {
				DatabaseHelper.beginTransaction();
				LOGGER.debug("--- begin transaction ---");
				result = proxyChain.doProxyChain();
				DatabaseHelper.commitTransaction();
				LOGGER.debug("--- commit transaction ---");
			} catch (Exception e) {
				DatabaseHelper.rollbackTransaction();
				LOGGER.debug("--- rollback transaction ---");
				throw e;
			}finally{
				FLAG_HOLDER.remove();
			}
		}else{
			result = proxyChain.doProxyChain();
		}
		return result;
		
	}

}

