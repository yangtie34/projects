package com.jhkj.mosdc.newoutput.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.util.HtmlUtils;

public class Struts2Utils {

    /**
     * @param object 任意对象
     * @deprecated
     * @return java.lang.String
     */
    public static String object2json(Object obj) {
    	StringBuilder json = new StringBuilder();
        if (obj==null || obj.toString().equals("null")) {
            json.append("\"\"");
        } else if (obj instanceof String || obj instanceof Integer  
        		|| obj instanceof Character
                || obj instanceof Float || obj instanceof Boolean
                || obj instanceof Short || obj instanceof Double
                || obj instanceof Long || obj instanceof BigDecimal
                || obj instanceof BigInteger || obj instanceof Byte) {
            json.append("\"").append(string2json(obj.toString())).append("\"");
        } else if (obj instanceof Object[]) {
            json.append(array2json((Object[]) obj));
        } else if (obj instanceof List) {
            json.append(list2json((List<?>) obj));
        } else if (obj instanceof Map) {
            json.append(map2json((Map<?, ?>) obj));
        } else if (obj instanceof Set) {
            json.append(set2json((Set<?>) obj));
        } else {
            json.append(bean2json(obj));
        }
        return json.toString();
    }

    /**
     * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串
     *
     * @param bean bean对象
     * @return String
     */
    public static String bean2json(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)
                    .getPropertyDescriptors();
        } catch (IntrospectionException e) {
        }
        if (props != null) {
            for (int i = 0; i < props.length; i++) {
                try {
                    String name = object2json(props[i].getName());
                    String value = object2json(props[i].getReadMethod().invoke(
                            bean));
                    json.append(name);
                    json.append(":");
                    json.append(value);
                    json.append(",");
                } catch (Exception e) {
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }



    /**
     * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串
     *
     * @param list 列表对象
     * @return java.lang.String
     */
    public static String list2json(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * list输出json对象,objectName作为key
     *
     * @param list
     * @param objectName
     * @return
     */
    public static String list2json(List<?> list, String objectName) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append("{ ");
                json.append("\"" + objectName + "\":");
                json.append(object2json(obj));
                json.append("} ");
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * list输出json对象,sz对应的记录名作为key
     *
     * @param list
     * @param objectName
     * @return
     */
    public static String listSZ2json(List<?> list, String[] sz) {

        StringBuilder json = new StringBuilder();
        if (null != sz && sz.length > 0) {
            json.append("[");
            if (list != null && list.size() > 0) {
                for (Object obj : list) {
                    Object[] tmp = (Object[]) obj;
                    json.append("{ ");
                    try{
                    	for (int i = 0; i < tmp.length; i++) {
                            String name = sz[i];
                            json.append("\"" + name + "\":");
                            json.append(object2json(tmp[i]));
                            json.append(",");
                        }
                    }catch(Exception e){
//                    	e.printStackTrace();
                    }
                    json.append("},");
                }
                json.setCharAt(json.length() - 1, ']');
            } else {
                json.append("]");
            }

        } else {
            json.append("[");
            if (list != null && list.size() > 0) {
                for (Object obj : list) {
                    json.append(object2json(obj));
                    json.append(",");
                }
                json.setCharAt(json.length() - 1, ']');
            } else {
                json.append("]");
            }
        }
        return json.toString();
    }

    /**
     * 数组输出json
     *
     * @param array
     * @return
     */
    public static String array2json(Object[] array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array != null && array.length > 0) {
            for (Object obj : array) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * map输出json
     *
     * @param map
     * @return
     */
    public static String map2json(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                json.append(object2json(key));
                json.append(":");
                json.append(object2json(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        String jsonStr = json.toString().replaceAll("\"\\[", "[").replaceAll("\\]\"", "]").replaceAll("\"\\{", "{").replaceAll("\\}\"", "}");
        return jsonStr;
    }

    /**
     * map输出json
     *
     * @param int t的意思是：对map的key值实行大小写转换。1为小写，2为大写，其他为不做转变。
     * @return
     */
    public static String Map2json(Map<?, ?> map, int t) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                switch (t) {
                    case 1:
                        json.append(object2json(key).toString().toLowerCase());
                        break;
                    case 2:
                        json.append(object2json(key).toString().toUpperCase());
                        break;
                    default:
                        json.append(object2json(key));
                        break;
                }
                json.append(":");
                if(map.get(key)==null){
                	json.append("\"\"");
                }else{
                	json.append(object2json(map.get(key)).toString());
                }
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    /**
     * set输出json
     *
     * @param set
     * @return
     */
    public static String set2json(Set<?> set) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (set != null && set.size() > 0) {
            for (Object obj : set) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * string输出json
     *
     * @param s
     * @return
     */
    public static String string2json(String s) {
        if (s == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }
    
    /**
     * 过滤request中的参数
     * @param requestParams
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public static String requestParamFilter(Map requestParams) throws Exception{
    	StringBuffer jsonObject = new StringBuffer();
		Set<String> key = requestParams.keySet();
        Iterator<String> iter = key.iterator();
        while(iter.hasNext()){
        	String field = iter.next();
        	String[] value = null;
        	if(!field.equals("_dc") && !field.equals("components")){
	            for (int i = 0; i < requestParams.size(); i++) {
	            	value = (String[]) requestParams.get(field);
				}
	            if(value[0] == null || value[0].equals("null") || field.equals("seniorQuery"))
	            	jsonObject.append("\""+field+"\":"+value[0]+",");
	            else
	            	jsonObject.append("\""+field+"\":\""+value[0]+"\",");
            } 
        }
    	return jsonObject.toString();
    }

    /**
     * requestMap返回entityName对象的属性和属性值，
     *
     * @param requestMap
     * @param entityName
     * @return list 包含数组
     */

    public static List requestMapToList(Map requestMap, String entityName) {
        Set set = requestMap.keySet();
        List list = new ArrayList();
        for (Iterator it = set.iterator(); it.hasNext();) {
            String tmp = (String) it.next();
            if (null != tmp && !"".equals(tmp.trim())) {
                if (tmp.trim().startsWith(entityName)) {
                    String[] name = tmp.trim().split("\\.");
                    if (null != name && name.length >= 2) {
                        String[] value = (String[]) requestMap.get(tmp);
                        String sx = tmp.substring(entityName.length() + 1, tmp
                                .length());
                        String[] tmp2 = new String[]{sx, value[0]};
                        list.add(tmp2);
                    }
                }
            }
        }
        return list;
    }
    
  

    public static String isoToUTF8(String value) throws Exception{
//		return new  String(value.getBytes("ISO-8859-1"),"UTF-8"); //UTF-8
    	return value;
	}
    public static String isoToGBK(String value) throws Exception{
//		return new  String(value.getBytes("ISO-8859-1"),"GBK"); //UTF-8
    	return value;
	}
	public static void main(String args []){
/*		TbXjyd xjyd = new TbXjyd();
		xjyd.setId(new Long ("1111"));
		TcBzdmjg bm = new TcBzdmjg();
		bm.setId(new Long("222"));
		bm.setMc("移动");
		xjyd.setYdlb(bm);
		xjyd.setXm("张三");
		xjyd.setCjr("里斯");
		System.out.println(bean2json(xjyd, "TbXjyd"));*/
		
	}
	
	public static String quoteString(String str) {
		if (str != null)
			return "'".concat(HtmlUtils.htmlEscape(str).replace("\\", "\\\\").replace("'", "\\'")).concat("'");
		else
			return "null";
	}

	public static String quoteString(Long l) {
		if (l != null)
			return quoteString(String.valueOf(l));
		else
			return "null";
	}

	public static String quoteString(Integer l) {
		if (l != null)
			return quoteString(l.toString());
		else
			return "null";
	}

	public static String quoteString(Double l) {
		if (l != null)
			return quoteString(l.toString());
		else
			return "null";
	}

	public static String quoteString(Boolean l) {
		if (l != null)
			return l.booleanValue() ? "true" : "false";
		else
			return "null";
	}

	@SuppressWarnings("unchecked")
	public static String quoteString(Enum l) {
		if (l != null)
			return quoteString(l.toString());
		else
			return "null";
	}

}
        
