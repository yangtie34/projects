package com.jhkj.mosdc.framework.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


/**
 * System constant config 常量
 * @author tianfei
 * @version 1.0
 *
 */
public class SysConstants {
	/**
	 * po的包路径
	 */
	public static final String PO_PACKAGE="com.jhkj.mosdc.framework.po.";
	/**
	 * 返回的json成功的字符串{success:true}
	 */
	public static final String JSON_SUCCESS_TRUE="{success:true}";
	/**
	 * 返回的json失败的字符串{success:false}
	 */
	public static final String JSON_SUCCESS_FALSE="{success:false}";
	/**
	 * 系统请求service集合名称
	 */
	public static final String REQUEST_SERVICES = "requestServices";
	
	/**
	 * 请求的名称集合
	 */
	public static final String REQUEST_REQUESTS = "request";
	
	/**
	 * 系统请求名称
	 */
	public static final String REQUEST_NAME = "name";
	/**
	 * 系统请求所调用的服务名称
	 */
	public static final String REQUEST_SERVICE_NAME = "service";
	/**
	 * 系统请求服务调用的方法名称
	 */
	public static final String REQUEST_SERVICE_METHOD = "method";

	/**
	 * session中用户信息名称
	 */
	public static final String SESSION_USERINFO = "userInfo";
	
	/**
	 * session中用户信息名称
	 */
	public static final String SESSION_USER = "USER";
	
	/**
	 * session中菜单按钮功能权限名称
	 */
	public static final String SESSION_PERMISSION = "permission";
	
	/**
	 * entity.xml中包路径
	 */
	public static final String request_package = "package";
	
	/**
	 * entity.xml实体名称
	 */
	public static final String request_entityname = "entityName";
	
	/**
	 * entity.xml实体集
	 */
	public static final String request_entitys = "requestEntitys";
	/**
	 * 后台查询数据成功时返回的信息
	 * @param list　数据集
	 * @param count　记录数
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String successInfo(List list,int count){
		Map map = new HashMap<String,String>();
		map.put("success", true);
		map.put("data", list);
		map.put("count", count);
		return Struts2Utils.map2json(map);
	}
	/**
	 * 返回成功或失败时，带提示信息
	 * @param successFlag　成功或失败
	 * @param info　提示信息
	 * @return
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String successInfo(boolean successFlag,String info){
		JSONObject map = new JSONObject();
		map.put("success", successFlag);
		map.put("info", info);
		return map.toString();
	}
}
