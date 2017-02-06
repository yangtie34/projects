package cn.gilight.dmm.business.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品级开发工具类
 * 
 * @author xuebl
 * @date 2016年4月20日 下午4:38:12
 */
public class DevUtils {
	
	/**
	 * Ajax 常用对象 Map
	 * {"status":true}
	 */
	public static Map<String, Object> MAP(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		return map;
	}
	
	/**
	 * copy
	 * @param obj
	 * @return Object
	 * @deprecated
	 */
	public static Object deepClone(Object obj) {// 将对象写到流里
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = null;
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = null;
		try {
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj); // 从流里读出来
			oi = new ObjectInputStream(bi);
			return (oi.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 测试 替换 System.out.println()
	 * @param str
	 * @Test
	 */
	public static void p(String str){
		System.out.println("/**********/ " +str);
	}
	/**
	 * 测试 替换 System.out.println()
	 * @param obj
	 * @Test
	 */
	public static void p(Object o){
		if(null != o){ System.out.println("/**********/ " +o.toString()); }
	}
}
