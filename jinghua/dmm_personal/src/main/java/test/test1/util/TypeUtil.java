package test.test1.util;

import java.lang.reflect.Type;

/**
 * Type相关工具类
 *
 * <br/>
 * E-mail: IsaidIwillgoon@gmail.com <br/>
 * Date: 2015-3-12 <br/>
 * Time: 上午9:51:59 <br/>
 * @author hank
 * @version 1.0
 */
public class TypeUtil {
	/**
	 * 是否基础类型
	 */
	public static boolean isBaseType(Type type){
		boolean flag = false;
		String[] types = {"int","double","long"};
		for(String str : types){
			if(str.equals(type.toString())){ flag = true; break;}
		}
		return flag;
	}
	
	/**
	 * 是否封装类型
	 * @param type
	 * @return
	 */
	public static boolean isPackageType(Type type){
		boolean flag = false;
		//带扩展
		String[] types = {"String","Integer","Boolean","Double","Long","Float"};
		for(String str : types){
			if(type.toString().equals("class java.lang." + str)){
				flag = true;break;
			}
		}
		return flag;
	}
	/**
	 * 是否String类型
	 * @param type
	 * @return
	 */
	public static boolean isString(Type type){
		return type.toString().equals("class java.lang.String");
	}
	/**
	 * 是否Boolean类型
	 * @param type
	 * @return
	 */
	public static boolean isBoolean(Type type){
		return type.toString().equals("class java.lang.Boolean")||type.toString().equals("boolean");
	}
	/**
	 * 是否整形
	 * @param type
	 * @return
	 */
	public static boolean isInt(Type type){
		return type.toString().equals("class java.lang.Integer")||type.toString().equals("int");
	}
	
	/**
	 * 是否long型
	 * @param type
	 * @return
	 */
	public static boolean isLong(Type type){
		return type.toString().equals("class java.lang.Long")||type.toString().equals("long");
	}
	
	/**
	 * 是否double
	 * @param type
	 * @return
	 */
	public static boolean isDouble(Type type){
		return type.toString().equals("class java.lang.Double")||type.toString().equals("double");
	}
	/**
	 * 是否Object
	 * @param type
	 * @return
	 */
	public static boolean isObject(Type type){
		return type.toString().equals("class java.lang.Object");
	}
	
}
