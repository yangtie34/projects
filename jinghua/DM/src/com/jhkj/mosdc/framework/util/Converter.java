package com.jhkj.mosdc.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.nobject.common.bean.BeanUtils;
import org.nobject.common.exception.AmbiguousException;
import org.nobject.common.exception.InvokeException;
import org.nobject.common.exception.NoFoundException;
import org.nobject.common.lang.ClassUtils;
import org.nobject.common.lang.ListUtils;
import org.nobject.common.lang.MapUtils;

/**
 * 各种类型转换需要的静态方法
 * 
 * @author BirdYfaN
 * @version 1.0
 */
public class Converter {

	// Using for AJAX
	public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	public static final String ATTRIBUTE_BLANK = "<";
	public static final String ATTRIBUTE_SLASH = "</";
	public static final String ATTRIBUTE_ENDER = ">";
	public static final String RESPONSE_HEADER = "<response>";
	public static final String RESPONSE_ENDER = "</response>";

	public static boolean isBaseJavaType(String className) {
		if (className.equalsIgnoreCase("class java.lang.String")
				|| className.equalsIgnoreCase("class java.lang.Double")
				|| className.equalsIgnoreCase("class java.lang.Integer")
				|| className.equalsIgnoreCase("class java.lang.Short")
				|| className.equalsIgnoreCase("class java.lang.Byte")
				|| className.equalsIgnoreCase("class java.lang.Boolean")
				|| className.equalsIgnoreCase("class java.lang.Character")
				|| className.equalsIgnoreCase("class java.lang.Float")) {
			return true;
		}
		return false;
	}

	/**
	 * 利用反射机制获得XML接点名, version1仅支持一层对象转换(对象类型为基本类型或简单类型)
	 */
	private static String beanXMLEncoding(String beanNode, Object bean) {
		Class beanClass = bean.getClass();

		String beanHeader = ATTRIBUTE_BLANK + beanNode + ATTRIBUTE_ENDER;
		String beanEnder = ATTRIBUTE_SLASH + beanNode + ATTRIBUTE_ENDER;
		StringBuffer sb = new StringBuffer();

		try {
			Field fields[] = beanClass.getDeclaredFields();
			Method methods[] = beanClass.getDeclaredMethods();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (isBaseJavaType(field.getType().toString()) == true) {
					sb.append(ATTRIBUTE_BLANK + field.getName()
							+ ATTRIBUTE_ENDER);
					for (int j = 0; j < methods.length; j++) {
						Method method = methods[j];
						if ((method.getName()).equalsIgnoreCase("get"
								+ field.getName())) {

							Object value = method.invoke(bean, null);
							if (value != null) {
								sb.append(value.toString());
							}
							break;
						}
					}
					sb.append(ATTRIBUTE_SLASH + field.getName()
							+ ATTRIBUTE_ENDER);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return beanHeader + sb.toString() + beanEnder;
	}

	/**
	 * 该方法将List解析成XML字符串。其中同级一级子节点下各个子节点的节点名称和参数list中 object中的各个属性
	 * 
	 * @param beanNode：一级子节点名称
	 * @param list：传入的List
	 * @return
	 */
	public static String listXMLEncoding(String beanNode, List list) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append(beanXMLEncoding(beanNode, list.get(i)));
		}
		return XML_HEADER + RESPONSE_HEADER + sb.toString() + RESPONSE_ENDER;
	}

	public static String[] spliteString2Array(String complexString,
			String splitString) {
		return complexString.split(splitString);
	}

	/**
	 * 将15位身份证号码转为18位身份证号码
	 * 
	 * @param IDCard
	 * @return
	 */
	public static String parseIDCard(String IDCard) {
		String s = IDCard, ns;
		try {
			if (s.length() != 15) {
				// Err_Msg="身份证号长度有误！"+e.getMessage();
				ns = "";
			} else {
				ns = s.substring(0, 6) + "19" + s.substring(6, 15);
				int wi[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
						2, 1 };
				char map[] = { '1', '0', 'x', '9', '8', '7', '6', '5', '4',
						'3', '2' };
				int ai[] = new int[17];
				int sum = 0, j = 0;

				for (int i = 0; i < 17; i++) {
					ai[i] = ns.charAt(i) - 48;
					sum += ai[i] * wi[i];
				}

				ns += String.valueOf(map[sum % 11]);
			}
		} catch (Exception e) {
			// Err_Msg="方法parseIDCard发生异常,"+e.getMessage();
			ns = "";
		}
		return ns;
	}

	private static String htmlEncode(int i) {
		if (i == '&')
			return "&amp;";
		else if (i == '<')
			return "&lt;";
		else if (i == '>')
			return "&gt;";
		else if (i == '"')
			return "&quot;";
		else
			return "" + (char) i;
	}

	public static String htmlEncode(String st) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < st.length(); i++) {
			buf.append(htmlEncode(st.charAt(i)));
		}
		return buf.toString();
	}
	
	public static String columnToField(String field) {
		   StringBuffer sb = new StringBuffer();
		   boolean flag=false;
		   for (int i = 0; i < field.length(); i++) {
		    char cur = field.charAt(i);
		    if (cur=='_') {
		     flag=true;
		    
		    } else {
		     if(flag){
		      sb.append(Character.toUpperCase(cur));
		      flag=false;
		     }else{
		      sb.append(Character.toLowerCase(cur));
		     }
		    }
		   }
		   //System.out.println(sb);
		   return sb.toString();
		}
		public static String fieldToColumn(String column) {
		   StringBuffer sb = new StringBuffer();
		   for (int i = 0; i < column.length(); i++) {
		    char cur = column.charAt(i);
		    if (Character.isUpperCase(cur)) {
		     sb.append("_");
		     sb.append(cur);
		    } else {
		     sb.append(cur);
		    }
		   }
		   return sb.toString().toUpperCase();
		}
		
		public static List changListClomn2Field(List list) {
			List tmpList = new ArrayList();
			if(null!=list&&list.size()>0){
				for(int i = 0;i<list.size();i++){
					Map newMap = new HashMap();
					Map tmp = (Map) list.get(i);
					Set keySet = tmp.keySet();
					for(Iterator it = keySet.iterator();it.hasNext();){
						Object key = it.next();
						Object value = tmp.get(key);
						String keyField= Converter.columnToField(key.toString());
						newMap.put(keyField, value);
					}
					tmpList.add(newMap);
				}
			}
			return tmpList;
		}
		
		/**
		 * 将Map的键转换为驼峰形式
		 * 
		 * @param map
		 * @return
		 * @author guoqing
		 */
		public static Map<String, Object> changeKeyStyle2Camel(Map<String, Object> map) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (map == null) {
				return resultMap;
			}
			
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for(Entry<String, Object> entry : entrySet){
				String key = entry.getKey();
				String newKey = Converter.columnToField(key);
				resultMap.put(newKey , entry.getValue());
			}
			
			return resultMap;
		}
		
		public static String entityToTable(String entity) {
			   StringBuffer sb = new StringBuffer();
			   for (int i = 0; i < entity.length(); i++) {
			    char cur = entity.charAt(i);
			    if (Character.isUpperCase(cur)) {
			    	if(i==0){
					     sb.append(cur);
			    	}else{
			   	     sb.append("_");
				     sb.append(cur);
			    	}
			    } else {
			     sb.append(cur);
			    }
			   }
			   return sb.toString().toUpperCase();
			}
		public static String objectToString(Object obj) {
			if (null != obj) {
				return StringUtils.trimToNull(obj.toString());
			} else {
				return null;
			}
		}

		public static Long objectToLong(Object obj) {
			if (null != obj) {
				String tmp = StringUtils.trimToNull(obj.toString());
				if (null != tmp) {
					return new Long(tmp);
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		
		/**
		 * 根据字段名和字段值获取对应的记录Map
		 * @param list
		 * @param field字段
		 * @param obj 字段值
		 * @return
		 */
		public static Object getRecordByList(List list,String field,Object obj){
			//定义map对象
			Object retObj = null;
			Object recObj = null;
			try {
				for(int i = 0;i<list.size();i++){
					recObj =  list.get(i);
					//判断字段值是否与传入的值一致
					if(BeanUtils.getProperty(recObj, field).equals(obj)){
						retObj = recObj;
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return retObj;
		}
		
	public static void main(String[] args) {
		System.out.println(columnToField("XXXX_BBBB_CCC"));
	}
}
