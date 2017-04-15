package com.eyun.framework.util.common;


/**
 * 类型转换工具类
 * @Comments: 
 * Created by wangyongtai(490091105@.com)
 * @DATE:2013-8-2
 * @TIME: 上午10:31:13
 * u hank(isaidiwillgoon@gmail.com) 修改类名
 */
public class TypeConvert {
	/**
	 * 支持把double转换成为Double，也支持把String类型转换成为Double，
	 * 也支持StringBuffer以及StringBuilder等调用toString方法能够得到一个正确的double字符串的Object
	 * @param obj
	 * @return 
	 */
	public static Double toDouble(Object obj){
	       if(obj == null){
	    	  return null;
	       }else{
	    	  if(obj.getClass() == Double.class){
	    		 return (Double) obj;
	    	  }else if(obj.getClass() == String.class){
	    		 return Double.valueOf((String) obj);
	    	  }else{
	    		 String s = obj.toString();
	    		 return Double.valueOf(s);
	    	  }
	       }
	}
	/**
	 * 支持把int转换成为Integer，也支持把String类型转换成为Integer，
	 * 也支持StringBuffer以及StringBuilder等调用toString方法能够得到一个正确的int字符串的Object
	 * @param obj
	 * @return 
	 */
	public static Integer toInteger(Object obj){
	       if(obj == null){
	    	  return 0;
	       }else{
	    	  if(obj.getClass() == Integer.class){
	    		 return (Integer) obj;
	    	  }else if(obj.getClass() == String.class){
	    		 return Integer.valueOf((String) obj);
	    	  }else{
	    		 String s = obj.toString();
	    		 return Integer.valueOf(s);
	    	  }
	       }
	}
	/**
	 * 支持把int转换成为Long，也支持把String类型转换成为Long，
	 * 也支持StringBuffer以及StringBuilder等调用toString方法能够得到一个正确的long字符串的Object
	 * @param obj
	 * @return 
	 */
	public static Long toLong(Object obj){
	       if(obj == null){
	    	  return null;
	       }else{
	    	  if(obj.getClass() == Long.class){
	    		 return (Long) obj;
	    	  }else if(obj.getClass() == String.class){
	    		 return Long.valueOf((String) obj);
	    	  }else{
	    		 String s = obj.toString();
	    		 return Long.valueOf(s);
	    	  }
	       }
	}
	/**
	 * 支持把int转换成为Long，也支持把String类型转换成为Long，
	 * 也支持StringBuffer以及StringBuilder等调用toString方法能够得到一个正确的boolean字符串的Object
	 * @param obj
	 * @return
	 */
	public static Boolean toBoolean(Object obj){
	       if(obj == null){
	    	  return false;
	       }else{
	    	  if(obj.getClass() == Boolean.class){
	    		 return (Boolean) obj;
	    	  }else if(obj.getClass() == String.class){
	    		 return Boolean.valueOf((String) obj);
	    	  }else{
	    		 String s = obj.toString();
	    		 return Boolean.valueOf(s);
	    	  }
	       }
	}
	/**
	 * 支持把各种基本类型或者包装类型，转换成为String类型
	 * 也支持StringBuffer以及StringBuilder等调用toString方法的Object类型
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj){
		if(obj == null){
	       return null;
	    }else{
	       return obj.toString();
	    }
	}
}
