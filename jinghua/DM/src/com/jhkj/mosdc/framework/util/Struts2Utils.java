package com.jhkj.mosdc.framework.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.util.HtmlUtils;

/**
 * @Comments: Struts2工具类. 实现获取Request/Response/Session与绕过jsp/freemaker直接输出文本的简化函数.
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-8-29
 * @TIME: 下午3:43:38
 */
public class Struts2Utils {

    // -- header 常量定义 --//
    private static final String HEADER_ENCODING = "encoding";

    private static final String HEADER_NOCACHE = "no-cache";

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final boolean DEFAULT_NOCACHE = true;

    private static ObjectMapper mapper = new ObjectMapper();

    // -- 取得Request/Response/Session的简化函数 --//
    /**
     * 取得HttpSession的简化函数.
     */
    public static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    /**
     * 取得HttpSession的简化函数.
     */
    public static HttpSession getSession(boolean isNew) {
        return ServletActionContext.getRequest().getSession(isNew);
    }

    /**
     * 取得HttpSession中Attribute的简化函数.
     */
    public static Object getSessionAttribute(String name) {
        HttpSession session = getSession(false);
        return (session != null ? session.getAttribute(name) : null);
    }

    /**
     * 取得HttpRequest的简化函数.
     */
    public static HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 取得HttpRequest中Parameter的简化方法.
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 取得HttpResponse的简化函数.
     */
    public static HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    // -- 绕过jsp/freemaker直接输出文本的函数 --//
    /**
     * 直接输出内容的简便函数. eg. render("text/plain", "hello", "encoding:GBK");
     * render("text/plain", "hello", "no-cache:false"); render("text/plain",
     * "hello", "encoding:GBK", "no-cache:false");
     *
     * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为GBK和true.
     */
    public static void render(final String contentType, final String content,
                              final String... headers) {
        HttpServletResponse response = initResponseHeader(contentType, headers);
        try {
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 直接输出文本.
     *
     * @see #render(String, String, String...)
     */
    public static void renderText(final String text, final String... headers) {
        render(ServletUtils.TEXT_TYPE, text, headers);
    }

    /**
     * 直接输出HTML.
     *
     * @see #render(String, String, String...)
     */
    public static void renderHtml(final String html, final String... headers) {
        render(ServletUtils.HTML_TYPE, html, headers);
    }

    /**
     * 直接输出XML.
     *
     * @see #render(String, String, String...)
     */
    public static void renderXml(final String xml, final String... headers) {
        render(ServletUtils.XML_TYPE, xml, headers);
    }

    /**
     * 直接输出JSON.
     *
     * @param jsonString json字符串
     * @param headers    头信息: "encoding:GBK", "no-cache:false"
     * @see #render(String, String, String...)
     */
    public static void renderJson(final String jsonString,
                                  final String... headers) {
        render(ServletUtils.JSON_TYPE, jsonString, headers);
    }

    /**
     * 直接输出JSON.
     *
     * @param jsonString json字符串
     * @see #render(String, String, String...)
     */
    public static void renderJson(final String jsonString) {
        render(ServletUtils.JSON_TYPE, jsonString, "no-cache:true");
    }
    
    /**
     * 直接输出JSON,使用Jackson转换Java对象.
     *
     * @param data    可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
     * @param headers 头信息: "encoding:GBK", "no-cache:false"
     * @see #render(String, String, String...)
     */
    public static void renderJson(final Object data, final String... headers) {
        HttpServletResponse response = initResponseHeader(
                ServletUtils.JSON_TYPE, headers);
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
    public static void renderGZip(final Object data) throws Exception{
    	HttpServletResponse response = ServletActionContext.getResponse();
        try {
            byte[] bytes = GZipUtil.gZip(data.toString());
        	response.addHeader("Content-Encoding", "gzip");   
        	response.setContentLength(bytes.length);  
        	response.getOutputStream().write(bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 直接输出JSON,使用Jackson转换Java对象.
     *
     * @param data    可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
     * @param headers 头信息: "encoding:GBK", "no-cache:false"
     * @see #render(String, String, String...)
     */
    public static void renderJson(final Object data) {
        HttpServletResponse response = initResponseHeader(
                ServletUtils.JSON_TYPE, "no-cache:true");
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 直接输出JSON,使用Jackson转换Java对象.
     */
    public static String objects2Json(Object data) {
        try {
            // mapper.writeValue(response.getWriter(), data);
            return mapper.writeValueAsString(data);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 直接输出JSON,使用Jackson转换Java对象.对象前加上对象名
     */
    public static String object2Json(Object data, String entityName) {
        try {
            // mapper.writeValue(response.getWriter(), data);
            return "{" + entityName + ":" + mapper.writeValueAsString(data) + "}";
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 直接输出JSON到页面,使用Jackson转换Java对象.
     */
    public static void renderJsonObject(final Object data) {
        HttpServletResponse response = initResponseHeader(
                ServletUtils.JSON_TYPE, "no-cache:true");
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 根据sql查询出的数据转成json格式的数据。
     * @param list
     * @return
     */
    public static String mapInList2Json(List<Map> list) {
        StringBuffer buffer = new StringBuffer("[");
        try {
            for (int i = 0; i < list.size(); i++) {
                buffer.append(Struts2Utils.Map2json(list.get(i), 1));
                if (i != list.size() - 1) {
                    buffer.append(",");
                }
            }
            buffer.append("]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    /**
     * 直接输出支持跨域Mashup的JSONP.
     *
     * @param callbackName callback函数名.
     * @param object       Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
     */
    public static void renderJsonp(final String callbackName,
                                   final Object object, final String... headers) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        String result = new StringBuilder().append(callbackName).append("(")
                .append(jsonString).append(");").toString();

        // 渲染Content-Type为javascript的返回内容,输出结果为javascript语句,
        // 如callback197("{html:'Hello World!!!'}");
        render(ServletUtils.JS_TYPE, result, headers);
    }

    /**
     * 分析并设置contentType与headers.
     */
    private static HttpServletResponse initResponseHeader(
            final String contentType, final String... headers) {
        // 分析headers参数
        String encoding = DEFAULT_ENCODING;
        boolean noCache = DEFAULT_NOCACHE;
        for (String header : headers) {
            String headerName = StringUtils.substringBefore(header, ":");
            String headerValue = StringUtils.substringAfter(header, ":");

            if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
                encoding = headerValue;
            } else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
                noCache = Boolean.parseBoolean(headerValue);
            } else {
                throw new IllegalArgumentException(headerName
                        + "不是一个合法的header类型");
            }
        }

        HttpServletResponse response = ServletActionContext.getResponse();

        // 设置headers参数
        String fullContentType = contentType + ";charset=" + encoding;
        response.setContentType(fullContentType);
        if (noCache) {
            ServletUtils.setNoCacheHeader(response);
        }

        return response;
    }

    /**
     * 根据Response输出Excel到客户端
     *
     * @param HSSFWorkbook
     * @param fileName     文件名
     * @see #render(String, String, String...)
     */
    public static void renderExcel(final HSSFWorkbook hssfworkbook,
                                   String fileName) {

        HttpServletResponse response = getResponse();

        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
        OutputStream fileWrite = new DataOutputStream(bos);
        try {
            hssfworkbook.write(fileWrite);
            byte[] buf = bos.toByteArray();

            response.reset();

            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8") + ".xls");

            response.getOutputStream().write(buf, 0, buf.length);
            fileWrite.flush();
            fileWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载Excel
     *
     * @param inputStream
     * @param fileName
     */
    public static void downLoadExcel(final InputStream inputStream,
                                     String fileName) {

        HttpServletResponse response = getResponse();

        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
        OutputStream fileWrite = new DataOutputStream(bos);
        try {
            // hssfworkbook.write(fileWrite);
            byte[] buf = bos.toByteArray();
            inputStream.read(buf);
            response.reset();

            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8") + ".xls");

            response.getOutputStream().write(buf, 0, buf.length);
            fileWrite.flush();
            fileWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param object 任意对象
     * @deprecated
     * @return java.lang.String
     */
    @SuppressWarnings("unchecked")
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
     * 输出json对象，对象名加属性名作为bean的key
     *
     * @param bean
     * @param entityName
     * @return
     */
    public static String bean2json(Object bean, String entityName) {
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
                    String name = props[i].getName();
                    String value = objects2Json(props[i].getReadMethod().invoke(
                            bean));
                    json.append("\"" + entityName + "." + name + "\"");
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
        	if(field.equals("proxyUserId") || field.equals("proxyMenuId")){
        		continue;
        	}
        	if(!field.equals("_dc") && !field.equals("components")){
	            for (int i = 0; i < requestParams.size(); i++) {
	            	value = (String[]) requestParams.get(field);
				}
	            if(value[0] == null || value[0].equals("null") || field.equals("seniorQuery"))
	            	jsonObject.append("\""+field+"\":"+value[0]+",");
	            else
	            	jsonObject.append("\""+field+"\":\""+value[0]+"\",");
	            /*if(value != null){
	        	}else{
	        		return "";
	        	}*/
            } 
        }
    	return jsonObject.toString();
    }
    
    
    
/*    public static Map requestMapToList(Map maps,String entityName){
    	Map map = new HashMap();
    	Set<String> key = maps.keySet();
        Iterator<String> iter = key.iterator();
        List
        while(iter.hasNext()){
//        	String field = iter.next();
//        	String[] paramFields   = field.split(".");
//        	 int begin=field.indexOf(".");
//             int end=field.length();
//            for (int i = 0; i < maps.size()-1; i++) {
//				 maps.get(field);
//			}
//        	System.out.println(field);
        	
        	String tmp = (String) iter.next();
            if (null != tmp && !"".equals(tmp.trim())) {
                if (tmp.trim().startsWith(entityName)) {
                    String[] name = tmp.trim().split("\\.");
                    if (null != name && name.length >= 2) {
                        String[] value = (String[]) maps.get(tmp);
                        String sx = tmp.substring(entityName.length() + 1, tmp
                                .length());
                        String[] tmp2 = new String[]{sx, value[0]};
                        list.add(tmp2);
                    }
                }
            }
        }
    	return list;
    }*/
    
    /**
     * param解析entityName对象的属性和属性值
     *
     * @param param
     * @return list 包含数组
     * @throws Exception
     */
    public static List requestJsonToLists(String param) throws Exception {
        try {
            JSONObject object = JSONObject.fromObject(param);
            Set set = object.keySet();
            List list = new ArrayList();
            for (Iterator it = set.iterator(); it.hasNext();) {
                String key = (String) it.next();
                if (!"entityName".equals(key)) {
                    String value = object.getString(key);
                        String[] tmp = new String[]{key, value};
                        list.add(tmp);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("转换错误");
        }

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

    /**
     * param解析entityName对象的属性和属性值
     *
     * @param param
     * @return list 包含数组
     * @throws Exception
     */
    public static List requestJsonToList(String param) throws Exception {

        try {
            JSONObject object = JSONObject.fromObject(param);
            String entityName = object.getString("entityName");
            Set set = object.keySet();
            List list = new ArrayList();
            for (Iterator it = set.iterator(); it.hasNext();) {
                String key = (String) it.next();
                if (key.startsWith(entityName + ".")) {
                    String value = object.getString(key);
                    String[] name = key.trim().split("\\.");
                    if (null != name && name.length >= 2) {
                        String sx = key.substring(entityName.length() + 1, key
                                .length());
                        String[] tmp = new String[]{sx, value};
                        list.add(tmp);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("转换错误");
        }

    }
    
    /**
     * param解析entityName对象的属性和属性值
     *
     * @param param
     * @return list 包含数组
     * @throws Exception
     */
    public static Map jsonToMap(String param) throws Exception {

        try {
            JSONObject object = JSONObject.fromObject(param);
            Set set = object.keySet();
            Map map = new HashMap();
            for (Iterator it = set.iterator(); it.hasNext();) {
                String key = (String) it.next();
                    String value = object.getString(key);
                    map.put(key, value);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("转换错误");
        }

    }
    /**
     * param解析entityName对象的属性和属性值
     *
     * @param param
     * @return list 包含数组
     * @throws Exception
     */
    public static void jsonCopyPropertiesToBean(Object obj,String param) throws Exception {
    		JSONObject object = JSONObject.fromObject(param);
    		Set set = object.keySet();
    		Map map = new HashMap();
    		Class targetClz = obj.getClass();
    		for (Iterator it = set.iterator(); it.hasNext();) {
    			String fieldName = (String) it.next();
    			String value = object.getString(fieldName);
                Field targetField  = null;
                try {
                       //得到targetClz对象所表征的类的名为fieldName的属性，不存在就进入下次循环
                        targetField = targetClz.getDeclaredField(fieldName);
                } catch (SecurityException e) {
                       e.printStackTrace();
                       break;
                } catch (NoSuchFieldException e) {
                       continue;
                }
               //由属性名字得到对应get和set方法的名字
               String setMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
              Class clazz =  targetField.getType();
              //转换类型
              String className = clazz.getName();

               //由方法的名字得到get和set方法的Method对象
               try {
                      Method setMethod = targetClz.getDeclaredMethod(setMethodName, targetField.getType());
                      //调用target对象的setMethod方法
                      Object aaa = null;
                      if (className.equalsIgnoreCase("java.lang.String")) {
                          aaa = value;
                      } else if (className.equalsIgnoreCase("java.lang.Long")) {
                          aaa = new Long("".equals(value) ? "0":value);
                      } else if (className.equalsIgnoreCase("java.lang.Byte")) {
                          aaa = new Byte(value);
                      } else if (className.equalsIgnoreCase("java.lang.Double")) {
                          aaa = new Double(value);
                      } else if (className.equalsIgnoreCase("java.lang.Integer")) {
                          aaa = new Integer(value);
                      } else if (className.equalsIgnoreCase("java.lang.Boolean")) {
                          aaa = new Boolean(!value.equals("0")? true:false);
                      } else if (className.equalsIgnoreCase("java.lang.Float")) {
                          aaa = new Float(value);
                      } else if (className.equalsIgnoreCase("java.lang.Short")) {
                          aaa = new Short(value);
                      }else if (className.equalsIgnoreCase("java.math.BigDecimal")) {
                          aaa = new BigDecimal(value);
                      }
                      setMethod.invoke(obj, aaa);
               } catch (Exception e) {
                      e.printStackTrace();
               } 
         }
    	
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
	
	
	/*public static String listToString(List list){
		String str = "";
		for(int i = 0;i<list.size();i++){
			JSONObject obj = (JSONObject) list.get(i);
			for(int j = 0;j<obj.size();j++){
//				str =str.concat(str)
			}
			 str =str.concat(list.get(i).toString());
		}
		if(str.length()>0){
			return str;
		}else{
			return null;
		}
	}*/
	public static String getParams(JSONObject maps) throws Exception{
    	Set<String> key = maps.keySet();
        Iterator<String> iter = key.iterator();
//        String pageStr = "";
        StringBuffer paramsStr = new StringBuffer();
        while(iter.hasNext()){
        	String field = iter.next();
        	//目前可确定的以下几种类型不是查询条件
//            if("menuId".equals(field)){
//    			continue;
//    		}else if("buttonId".equals(field)){
//    			continue;
//    		}else 
    		if("requestType".equals(field)){
    			continue;
    		}else{
    			String value = "";
    			for (int i = 0; i < maps.size(); i++) {
    				value = String.valueOf(maps.getString(field));
    				if(!"".equals(value)){
    					break;
    				}
    			}
    			if(value == null || value == "null"){
    				paramsStr.append("\""+field+"\":"+value+",");
    			}else{
    				paramsStr.append("\""+field+"\":\""+value+"\",");
    			}
    		}
        }
        String retParams = paramsStr.toString().replace("\"{", "{").replace("}\"", "}").replace("\"[", "[").replace("]\"", "]");
        if(retParams.length()>0){
        	retParams = retParams.substring(0, retParams.length()-1);
        }
        return "{"+retParams+" }";
	}
}
        
