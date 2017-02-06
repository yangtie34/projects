package cn.gilight.dmm.business.util;

import com.alibaba.fastjson.JSONObject;

import cn.gilight.framework.page.Page;

/**
 * 分页工具
 * 
 * @author xuebl
 * @date 2016年8月1日 上午10:55:53
 */
public class PageUtils {

	
	/**
	 * 分页参数转换工具； JSON -> Class
	 * @param param
	 * @return Page
	 */
	public static Page converPage(String param){
		return JSONObject.parseObject(param, Page.class);
	}
	
	
}
