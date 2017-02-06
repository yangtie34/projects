package com.glite.flink.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.glite.flink.utils.handle.RedisResultHandler;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Transaction;

public class RedisClientUtils {
	private static JedisPool jedisPool = null;
	private static JedisPoolConfig config = null;
	static {
		Properties p = new Properties();
		try {
			p.load(RedisClientUtils.class
					.getResourceAsStream("/redis.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		config = new JedisPoolConfig();
		config.setMaxTotal(Integer.valueOf(p.getProperty("redis.maxTotal")));
		config.setTestOnBorrow(Boolean.valueOf(p.getProperty("redis.testOnBorrow")));
		// config.setTestOnReturn(true);
		config.setMaxWaitMillis(Long.valueOf(p.getProperty("redis.maxWaitMillis")));
		config.setMaxIdle(Integer.valueOf(p.getProperty("redis.maxIdle")));
		
		// config.setTestOnCreate(true);
		jedisPool = new JedisPool(config, p.getProperty("redis.host"),
				Integer.valueOf(p.getProperty("redis.port")), Integer.valueOf(p
						.getProperty("redis.timeout")), p.getProperty("redis.password"),Integer.valueOf(p.getProperty("redis.database")));
	}

	private static Jedis getJedis() {
		return jedisPool.getResource();
	}

	private static void releaseJedis(Jedis jedis) {
		jedis.close();
//		jedisPool.returnResource(jedis);
//		 jedisPool.returnBrokenResource(jedis);
	}

	public static String addString(String key, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.set(key, value);
		} finally {
			releaseJedis(jedis);
		}

	}

	public static String addString(String key, String value, long aliveTimes) {
		Jedis jedis = getJedis();
		try {
			return jedis.set(key, value, null, null, aliveTimes);
		} finally {
			releaseJedis(jedis);
		}
	}

	public static long addOneElementsLeft(String key, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.lpush(key, value);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static long addList(String key, List<String> list) {
		Jedis jedis = getJedis();
		try {
			return jedis.lpush(key,
					(String[]) list.toArray(new String[list.size()]));
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static long addListLeft(String key, List<String> list) {
		Jedis jedis = getJedis();
		try {
			return jedis.lpush(key,
					(String[]) list.toArray(new String[list.size()]));
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static long addOneElementRight(String key, String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.rpush(key, value);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static String changeOneElementLeft(String key, long index,
			String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.lset(key, index, value);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static long addListRight(String key, List<String> list) {
		Jedis jedis = getJedis();
		try {
			return jedis.rpush(key,
					(String[]) list.toArray(new String[list.size()]));
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static String addMap(String key, Map<String, String> map) {
		Jedis jedis = getJedis();
		try {
			return jedis.hmset(key, map);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static long changeOneFieldOfMap(String key, String field,
			String value) {
		Jedis jedis = getJedis();
		try {
			return jedis.hset(key, field, value);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static String getString(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.get(key);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static Long getLengthOfList(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.llen(key);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static List getListLeft(String key, long start, long end) {
		Jedis jedis = getJedis();
		try {
			return jedis.lrange(key, start, end);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	

	public static String getMap(String key, String field) {
		Jedis jedis = getJedis();
		try {
			return jedis.hget(key, field);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static Map getAllMap(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.hgetAll(key);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static Long removeKeys(String... keys) {
		Jedis jedis = getJedis();
		try {
			return jedis.del(keys);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static String popOneElementLeft(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.lpop(key);
		} finally {
			releaseJedis(jedis);
		}
		
	}

	public static String popOneElementRight(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.rpop(key);
		} finally {
			releaseJedis(jedis);
		}
		
	}
	
	public static List<String> popElementsLeft(String key,long num){
		Jedis jedis = getJedis();
		try {
			List<String> l=new ArrayList<String>();
			while(num-->0){
				l.add(jedis.lpop(key));
			}	
			return l;
		} finally {
			releaseJedis(jedis);
		}
	}
	
	public static List<String> popElementsRight(String key,long num){
		Jedis jedis = getJedis();
		try {
			List<String> l=new ArrayList<String>();
			while(num-->0){
				l.add(jedis.rpop(key));
			}	
			return l;
		} finally {
			releaseJedis(jedis);
		}
	}
	
	public static long hscan(String key,String pattern,RedisResultHandler handler){
		Jedis jedis = getJedis();
		long num=0;
		try {
			ScanParams parm=new ScanParams();
			if(pattern!=null && pattern.length()>0){
				parm.match(pattern.getBytes());
			}
			ScanResult r=jedis.hscan(key, "0",parm);
			while(!"0".equals(r.getStringCursor())){
				List<java.util.AbstractMap.SimpleEntry<String, Object>> l= r.getResult();
				if(l.size()>0){
					List newList=new ArrayList();
					for(java.util.AbstractMap.SimpleEntry<String, Object> m:l){
						Map newM=new  HashMap();
						newM.put(m.getKey(), m.getValue());
						newList.add(newM);
					}
					handler.handle(newList);
					num+=l.size();
				}				
				r=jedis.hscan(key, r.getStringCursor(),new ScanParams().match(pattern.getBytes()));
			}
		} finally {
			releaseJedis(jedis);
		}
		return num;
	}
	
	public static long removeKeyOfMap(String key, String field){
		Jedis jedis = getJedis();
		try {
			return jedis.hdel(key, field);
		} finally {
			releaseJedis(jedis);
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		hscan("T_CARD", "*6*", new RedisResultHandler() {
			@Override
			public   void handle(List l) {
				if(l.size()>0){
					Iterator i=l.iterator();
					while(i.hasNext()){
						Map m=(Map)i.next();
						System.out.println(m);
						
					}
				}
				
			}
		});
		
	}

}
