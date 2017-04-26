package com.smart.client.controller;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.smart.annotation.Action;
import com.smart.annotation.Controller;
import com.smart.bean.Data;
import com.smart.bean.Param;
import com.smart.other.test.session.CacheMap;
import com.smart.other.test.session.CacheMapFactory;
import com.smart.utils.StringUtil;


/** 
 * Filename:     FakeSessionController.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月22日 下午8:06:05 
 */
@Controller
public class FakeSessionController {

	@Action("get:/fakeSession")
	public Data fakeSession(Param param){
		String result = "ERROR";
		CacheMap<String, String> tokenMap = CacheMapFactory.getCacheMapInstance(1000*10);
		if(tokenMap == null){
			tokenMap = CacheMapFactory.newCacheMapInstance(1000*10);
		}
		
		Map<String, Object> fieldMap = param.getFieldMap();
		String username = (String) fieldMap.get("username");
		String token = (String)fieldMap.get("token");
		if(StringUtil.isEmpty(token)){
			//没有token
			if(StringUtil.isEmpty(username)){
				result = "非法用户";
			}else{
				//生成token并赋值
				String uuid = UUID.randomUUID().toString();
				tokenMap.put(uuid, username);
				result = "用户登入:" + username + " , " + uuid;
			}
		}else{
			//token
			Object obj = tokenMap.get(token);
			if(obj == null){
				result = "用户失效";
			}else{
				String uname = (String) obj;
				tokenMap.refreshStartTime(token);
				result = uname + ":" + token + "--已更新," + new Date(tokenMap.getStartTime(token));
			}
		}
		
		return new Data(result);
	}
}

