package com.eyun.framework.util.common;

import java.util.Arrays;

/**
 * 对象操作工具类(Copy from Spring)
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2013-8-8
 * @TIME: 下午11:16:45
 */
public class ObjectUtils {

	/**
	 * Return whether  the given throwable is a checked exception:
	 * that is, neither a RuntimeException nor an Error.
	 * 返回异常是否是检查的异常,即不是一个运行时异常或者错误
	 * @param ex 检查的异常
	 * @return 是否是一个检查的异常(即从java.lang.Exception继承的异常)
	 * @see Exception
	 * @see RuntimeException
	 * @see Error
	 */
	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException || ex instanceof Error);
	}

	/**
	 * Check whether the given exception is compatible with the exceptions
	 * declared in a throws clause.
	 * 判断给定的异常是否是throws子句中声明的异常
	 * 检查异常,是不是throws子句中声明的异常
	 * @param ex 被检查的异常
	 * @param declaredExceptions 在throws子句中声明的异常
	 * @return 给定的异常是否兼容
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isCompatibleWithThrowsClause(Throwable ex, Class[] declaredExceptions) {
		if (!isCheckedException(ex)) {
			return true;
		}
		if (declaredExceptions != null) {
			int i = 0;
			while (i < declaredExceptions.length) {
				if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
					return true;
				}
				i++;
			}
		}
		return false;
	}

	/**
	 * 判断给定的对象是否是一个数组：包括对象数组和基本类型数组
	 * @param obj 被检测的对象
	 */
	public static boolean isArray(Object obj) {
		return (obj != null && obj.getClass().isArray());
	}

	/**
	 * 判断给定的数组是否是空的:包含两种状况 : 1 数组长度为0 2 数组对象是Null
	 * @param array 被检测的对象数组
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * 判断给定的数组中,是否包含给定的元素.如果数组对象为空,则将一直返回false。
	 * @param element 被检测的元素
	 * @return whether 需要被查找的数组
	 */
	public static boolean containsElement(Object[] array, Object element) {
		if (array == null) {
			return false;
		}
		for (Object arrayEle : array) {
			if (nullSafeEquals(arrayEle, element)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Determine if the given objects are equal, returning <code>true</code>
	 * if both are <code>null</code> or <code>false</code> if only one is
	 * <code>null</code>.
	 * <p>Compares arrays with <code>Arrays.equals</code>, performing an equality
	 * check based on the array elements rather than the array reference.
	 * 
	 * 比较两个对象是equal的，返回true,如果两个对象都是Null或者任意一个对象是Null的返回false
	 * 对Array的比较采用的是Arrays.equals方法
	 * 
	 * @param o1  第一个比较的对象
	 * @param o2  第二个比较的对象
	 * @return 给定的对象是否equal
	 * @see Arrays#equals
	 */
	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
		}
		return false;
	}
	
}
