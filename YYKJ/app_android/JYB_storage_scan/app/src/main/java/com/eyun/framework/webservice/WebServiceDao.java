package com.eyun.framework.webservice;

import com.eyun.framework.entity.ResultMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/** 
* @ClassName: BaseDao 
* @Description: TODO 持久层基础操作接口
* @author Sunwg  
* @date 2016年3月7日 22:38:37   
*/
public interface WebServiceDao {
	/*根据查询条件返回结果集*/
	public abstract List<Map<String, Object>> queryForList(String url, final String methodName,HashMap<String, Object> properties);
	/*根据查询条件返回结果集*/
	public abstract ResultMsg queryForResultMsg(String url, final String methodName,HashMap<String, Object> properties);
	/*根据查询条件返回结果集*/
	public abstract <T> List<T> queryForListBean(String url, final String methodName,HashMap<String, Object> properties ,Class<T> cls);

}
