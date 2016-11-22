package com.jhkj.mosdc.framework.util;

import java.lang.reflect.Method;

/**
 * 提供不同反射调用功能的工具类。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-12-4
 * @TIME: 上午11:28:13
 */
public class ReflectInvoker {
	/**
	 * 通用反射调用方法。
	 * @param obj 反射对象。
	 * @param methodName 方法名。
	 * @param methodArgs 方法参数。
	 * @return
	 */
	public static Object commonReflectInvoke(Object obj,String methodName,Object[] methodArgs){
		// 反射调用这个对象的initData方法。
		Class cl =  obj.getClass();
		// 方法参数列表中的对象类型。
		Class[] args = new Class[methodArgs.length];
		Object result = new Object();
		try{
			// 获取参数对象集合中的对象Class对象，保存到args中。
			for(int i=0 ;i<methodArgs.length;i++){
				args[i] = methodArgs[i].getClass();
			}
			// 根据方法名和参数列表，反射方法。
			Method method = cl.getMethod(methodName, args);
			// 调用指定对象方法，并传递给该方法响应的参数列表。
			result = method.invoke(obj, methodArgs);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 通用反射调用方法。
	 * @param obj 反射对象。
	 * @param method 方法。
	 * @param methodArgs 方法参数。
	 * @return
	 */
	public static Object commonReflectInvoke(Object obj,Method method,Object[] methodArgs){
		// 反射调用这个对象的initData方法。
		Class cl =  obj.getClass();
		// 方法参数列表中的对象类型。
		Class[] args = new Class[methodArgs.length];
		Object result = new Object();
		try{
			// 获取参数对象集合中的对象Class对象，保存到args中。
			for(int i=0 ;i<methodArgs.length;i++){
				args[i] = methodArgs[i].getClass();
			}
			// 调用指定对象方法，并传递给该方法响应的参数列表。
			result = method.invoke(obj, methodArgs);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 通用反射调用方法。
	 * @param obj 反射对象。
	 * @param methodName 方法名。
	 * @return
	 */
	public static Method reflectMethod(Object obj,String methodName,Object[] methodArgs){
		// 反射调用这个对象的initData方法。
		Class cl =  obj.getClass();
		// 方法参数列表中的对象类型。
		Class[] args = new Class[methodArgs.length];
		Method method  = null;
		try{
			// 获取参数对象集合中的对象Class对象，保存到args中。
			for(int i=0 ;i<methodArgs.length;i++){
				args[i] = methodArgs[i].getClass();
			}
			method = cl.getMethod(methodName, args);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return method;
	}
	/**
	 * 判断给定参数列表是否与制定的方法的形参类型匹配。
	 * @param method
	 * @param objs
	 * @return
	 */
	public static boolean isMatchParameterType(Method method,Object[] methodArgs){
		boolean isMatch = true;
		Class[] methodPar = method.getParameterTypes();
		if(methodPar.length == methodArgs.length){
			for(int i=0 ;i<methodArgs.length;i++){
				for(int j=0 ;j<methodPar.length;j++){
					String argName = methodArgs[i].getClass().getName();
					String name = methodPar[j].getName();
					if(!name.equals(argName)){
						return false;
					}
				}
			}
		}else{
			return false;
		}
		return isMatch;
	}
}
