package com.smart.other.test.session;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CacheMap<K, V> extends AbstractMap<K, V> {

	private class CacheEntry implements Entry<K, V>{

		long time;
		V value;
		K key;
		
		CacheEntry(K key, V value){
			super();
			this.value = value;
			this.key = key;
			this.time = System.currentTimeMillis();
		}
		
		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			return this.value = value;
		}
		
		public void setTime(){
			time = System.currentTimeMillis();
		}
		
		public long getTime(){
			return time;
		}
	}
	

	private long cacheTimeout;
	private Map<K, CacheEntry> map = Collections.synchronizedMap(new HashMap<K, CacheEntry>());
	
	private class ClearThread extends Thread{
		ClearThread(){
			setName("clear cache thread");
		}
		
		@Override
		public void run() {
			while(true){
				try {
					long now = System.currentTimeMillis();
					Object[] keys = map.keySet().toArray();
					for(Object key : keys){
						CacheEntry entry = map.get(key);
						if(now - entry.time >= cacheTimeout){
							synchronized (map) {
								map.remove(key);
							}
						}
					}
					Thread.sleep(200);
					Thread.yield();
					Thread.yield();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	public CacheMap(long timeout){
		this.cacheTimeout = timeout;
		new ClearThread().start();
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entrySet = new HashSet<Map.Entry<K,V>>();
		Set<Entry<K, CacheEntry>> wrapEntrySet = map.entrySet();
		for(Entry<K, CacheEntry> entry : wrapEntrySet){
			entrySet.add(entry.getValue());
		}
		return entrySet;
	}
	
	public V get(Object key){
		CacheEntry entry = map.get(key);
		return entry==null ? null : entry.value;
	}
	
	public V put(K key, V value){
		CacheEntry entry = new CacheEntry(key, value);
		synchronized (map) {
			map.put(key, entry);
		}
		return value;
	}
	
	public void refreshStartTime(K key){
		CacheEntry entry = map.get(key);
		if(entry != null){
			synchronized (map) {
				entry.setTime();
			}
		}
	}
	
	public long getStartTime(K key){
		long time = -1l;
		CacheEntry entry = map.get(key);
		if(entry != null){
			synchronized (map) {
				time = entry.getTime();
			}
		}
		
		return time;
	}

}
