package com.jhkj.mosdc.output.util;

import java.lang.reflect.Method;

/**
 * 反射调用类。
 * 	提供根据参数列表做不同反射的功能.
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-30
 * @TIME: 下午02:10:11
 */
public class ReflectInvoke {
	/**
	 * 反射调用方法。
	 * @param obj 反射对象。
	 * @param methodName 方法名。
	 * @param methodArgs 方法参数。
	 * @return
	 */
	public static Object reflectInvokeInitData(Object obj,String methodName,Object[] methodArgs){
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
	
}
