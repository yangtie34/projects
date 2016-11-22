package com.jhkj.mosdc.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil {
	private static CacheManager cacheManager = null;
	private static Integer maxElementsInMemory = 20; // 内存中最多元素数目
	private static boolean overflowToDisk = true; // 超过最大数目，自动将缓存放到硬盘
	private static boolean eternal = false;
	private static int timeToLiveSeconds = 120;
	private static int timeToIdleSeconds = 120;
	static Object object=new Object();
	static {
		cacheManager = CacheManager.newInstance();
		// String path = getWebRootPath().replace("%20", " ")
		// + "\\WEB-INF\\classes\\ehcache\\ehcache.xml";
		// InputStream fis = null;
		// try {
		// fis = new FileInputStream(new File(path));
		// cacheManager = CacheManager.create(fis);
		// } catch (Throwable e) {
		// e.printStackTrace();
		// try {
		// fis.close();
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// }
	}
//
//	/**
//	 * 创建一个缓存，
//	 * 
//	 * @param cacheName
//	 * @param timeToLiveSeconds
//	 *            缓存的存活时间（秒）
//	 * @param timeToIdleSeconds
//	 *            多少时间不访问该缓存，系统自动清除该缓存
//	 * @return
//	 */
//	public static Cache createCache(String cacheName, long timeToLiveSeconds,
//			long timeToIdleSeconds) {
//		if (cacheManager.cacheExists(cacheName)) {
//			return cacheManager.getCache(cacheName);
//		}
//		Cache cache = new Cache(cacheName, maxElementsInMemory, overflowToDisk,
//				eternal, timeToLiveSeconds, timeToIdleSeconds);
//		cacheManager.addCache(cache);
//		return cache;
//	}
//
//	/**
//	 * 
//	 * @param cacheName
//	 * @param maxElementsInMemory
//	 * @param timeToLiveSeconds
//	 * @param timeToIdleSeconds
//	 * @return
//	 */
//	public static Cache createCache(String cacheName,
//			Integer maxElementsInMemory, long timeToLiveSeconds,
//			long timeToIdleSeconds) {
//		if (cacheManager.cacheExists(cacheName)) {
//			return cacheManager.getCache(cacheName);
//		}
//		Cache cache = new Cache(cacheName, maxElementsInMemory, overflowToDisk,
//				eternal, timeToLiveSeconds, timeToIdleSeconds);
//		cacheManager.addCache(cache);
//		return cache;
//	}
	
	public static boolean isCacheEmpty(String cacheName){
		synchronized (object) {
			if(!cacheManager.cacheExists(cacheName)){
				Cache cache = new Cache(cacheName, maxElementsInMemory,
						overflowToDisk, eternal, timeToLiveSeconds,
						timeToIdleSeconds);
				cacheManager.addCache(cache);
			}
			Cache cache = cacheManager.getCache(cacheName);
			if(cache.getKeys().size()==0){
				return true;
			}			
		}
		return false;
	}

	/**
	 * 向缓存为cacheName 的缓存中 增加 缓存对象<key,value>
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void putObjToCache(String cacheName, Object key, Object value) {
		synchronized (object) {
			if (!cacheManager.cacheExists(cacheName)) {
				Cache cache = new Cache(cacheName, maxElementsInMemory,
						overflowToDisk, eternal, timeToLiveSeconds,
						timeToIdleSeconds);
				cacheManager.addCache(cache);
			}
			Cache cache = cacheManager.getCache(cacheName);
			Element e = new Element(key, value);
			cache.put(e);
		}		
	}
	
	/**
	 * 删除缓存
	 * @param cacheName
	 */
	public static void dropCache(String cacheName){
		synchronized (object) {
			if (cacheManager.cacheExists(cacheName)) {
				 cacheManager.removeCache(cacheName);
			}
		}
		
	}
	
	/**
	 * 清空cacheName 缓存
	 * @param cacheName
	 */
	public static void clearCache(String cacheName){
		synchronized (object) {
			if (cacheManager.cacheExists(cacheName)) {
				Cache cache = cacheManager.getCache(cacheName);
				cache.removeAll();
			}
		}		
	}
	
	/**
	 * 删除缓存cacheName中的key元素
	 * @param cacheName
	 * @param key
	 */
	public static void removeObjFromCache(String cacheName, Object key){
		synchronized (object) {
			if (cacheManager.cacheExists(cacheName)) {
				Cache cache = cacheManager.getCache(cacheName);
				if(cache.get(key)!=null){
					cache.remove(key);
				}
			}
		}
		
	}

	/**
	 * 从缓存为cacheName 的缓存中获取 键值为key的对象
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object getObjFromCache(String cacheName, Object key) {	
		synchronized (object) {
			if (!cacheManager.cacheExists(cacheName)) {
				return null;
			}
			Cache cache = cacheManager.getCache(cacheName);
			Element e = cache.get(key);
			return e!=null?e.getObjectValue():null;
		}		
	}
	
	public static void evict(){		
		String cacheNames[]=cacheManager.getCacheNames();
		for(int i=0;i<cacheNames.length;i++){
			evict(cacheNames[i]);
		}
	}
	
	public static void evict(String cacheName){
		if (!cacheManager.cacheExists(cacheName)) {
			return ;
		}
		Cache cache = cacheManager.getCache(cacheName);
		cache.evictExpiredElements();
	}

	private static String getWebRootPath() {
		String LOCAL_CLASS_NAME_SHORT = EhcacheUtil.class.getSimpleName()
				+ ".class";
		String LOCAL_CLASS_NAME_LONG = EhcacheUtil.class.getName().replaceAll(
				"\\.", "/")
				+ ".class";
		String WEB_ROOT_PATH = EhcacheUtil.class
				.getResource(LOCAL_CLASS_NAME_SHORT).getPath()
				.replaceFirst("/WEB-INF/classes/" + LOCAL_CLASS_NAME_LONG, "/");
		return WEB_ROOT_PATH;
	}
}
