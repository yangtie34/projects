package com.jhkj.mosdc.permission.util;

import org.junit.Test;

/**
 * ��װ����ת�������࣬ͬʱҲ֧�����String������ת��
 * @Comments: 
 * Created by wangyongtai(490091105@.com)
 * @DATE:2013-8-2
 * @TIME: ����10:31:13
 */
public class TypeConvert {

	/**
	 * ֧�ְ�doubleת����ΪDouble��Ҳ֧�ְ�String����ת����ΪDouble��
	 * Ҳ֧��StringBuffer�Լ�StringBuilder�ȵ���toString�����ܹ��õ�һ����ȷ��double�ַ��Object
	 * @param obj
	 * @return
	 */
	public static Double getDouble(Object obj){
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
	 * ֧�ְ�intת����ΪInteger��Ҳ֧�ְ�String����ת����ΪInteger��
	 * Ҳ֧��StringBuffer�Լ�StringBuilder�ȵ���toString�����ܹ��õ�һ����ȷ��int�ַ��Object
	 * @param obj
	 * @return
	 */
	public static Integer getInteger(Object obj){
	       if(obj == null){
	    	  return null;
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
	 * ֧�ְ�intת����ΪLong��Ҳ֧�ְ�String����ת����ΪLong��
	 * Ҳ֧��StringBuffer�Լ�StringBuilder�ȵ���toString�����ܹ��õ�һ����ȷ��long�ַ��Object
	 * @param obj
	 * @return
	 */
	public static Long getLong(Object obj){
	       if(obj == null){
	    	  return null;
	       }else{
	    	  if(obj.getClass() == Integer.class){
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
	 * ֧�ְ�intת����ΪLong��Ҳ֧�ְ�String����ת����ΪLong��
	 * Ҳ֧��StringBuffer�Լ�StringBuilder�ȵ���toString�����ܹ��õ�һ����ȷ��boolean�ַ��Object
	 * @param obj
	 * @return
	 */
	public static Boolean getBoolean(Object obj){
	       if(obj == null){
	    	  return null;
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
	 * ֧�ְѸ��ֻ����ͻ��߰�װ���ͣ�ת����ΪString����
	 * Ҳ֧��StringBuffer�Լ�StringBuilder�ȵ���toString����
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj){
		if(obj == null){
	       return null;
	    }else{
	       return obj.toString();
	    }
	}
	@Test
	public void testConvert(){
		Double s = TypeConvert.getDouble(true);
		System.out.println(s);
	}
}
