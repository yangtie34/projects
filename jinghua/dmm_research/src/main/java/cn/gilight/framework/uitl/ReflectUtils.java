package cn.gilight.framework.uitl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import cn.gilight.framework.uitl.common.StringUtils;

/**
 * 反射工具类
 * @Title: ReflectUtils.java 
 * @Package cn.gilight.commons 
 * @Description: (该文件做什么) 
 * @author wangyt
 * @date 2015年3月10日 下午4:24:11 
 * @version V1.0
 */
public class ReflectUtils {
	/**
	 * 通过属性名调用Po的Getter方法
	 * 
	 * @param obj
	 * @param fieldname
	 * @return
	 */
	public static Object invokeGetter(Object obj, String fieldname) {
		return ReflectUtils.invokeGetter(obj, fieldname, new Object[]{});
	}
	/**
	 * 通过属性名调用Po的Getter方法
	 * 
	 * @param obj
	 * @param fieldname
	 * @param objects 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object invokeGetter(Object obj, String fieldname,Object ...objects) {
		Class cls = obj.getClass();
		String getMethodName = "get" + StringUtils.capitalize(fieldname);
		Method method = ReflectionUtils.findMethod(cls, getMethodName);
		return ReflectionUtils.invokeMethod(method, obj,objects);
	}
	/**
	 * 通过属性名调用Po的setter方法
	 * 
	 * @param obj
	 * @param fieldname
	 * @return
	 */
	public static void invokeSetter(Object object,String fieldname){
		ReflectUtils.invokeSetter(object, fieldname, new Object[]{});
	}
	/**
	 * 通过属性名调用Po的setter方法
	 * 
	 * @param obj
	 * @param fieldname
	 * @param objects 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static void invokeSetter(Object object,String fieldname,Object...objects){
		Class cls = object.getClass();
		String setMethodName = "set" + StringUtils.capitalize(fieldname);
		Class[] clss = new Class[objects.length];
		for(int i=0;i<objects.length;i++){
			if(objects[i] != null){clss[i] = objects[i].getClass();}
			else{clss[i] = Object.class;}
		}
		Method method = ReflectionUtils.findMethod(cls, setMethodName,clss);
		ReflectionUtils.invokeMethod(method, object, objects);
	}
	
	/**
	 * object是否包含fieldname属性
	 * @param object
	 * @param fieldname
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static boolean isContain(Object object,String fieldname) throws SecurityException, NoSuchFieldException{
		return isContain(object.getClass(),fieldname);
	}
	
	/**
	 * 类klass是否包含fieldname属性
	 * @param klass
	 * @param fieldname
	 * @return
	 */
	public static <T> boolean isContain(Class<T> klass,String fieldname){
		Field[] fields = klass.getDeclaredFields();
		boolean contain = false;
		for(Field field : fields){
			if(field.getName().equals(fieldname)){ contain = true; break; }
		}
		return contain;
	}
	
}
