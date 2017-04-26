package com.smart.other.test.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CacheMapFactory {
	
	private static final Map<Long, CacheMap> factory = new HashMap<Long, CacheMap>(); 
	
	public static CacheMap newCacheMapInstance(long timeout){
		CacheMap cacheMap = new CacheMap(timeout);
		factory.put(timeout, cacheMap);
		return cacheMap;
	}
	
	public static CacheMap getCacheMapInstance(long timeout){
		Set<Long> keyset = factory.keySet();
		if( keyset.contains(timeout) ){
			return factory.get(timeout);
		} else{
			return null;
		}
	}
}
