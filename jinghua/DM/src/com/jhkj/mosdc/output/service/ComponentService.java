package com.jhkj.mosdc.output.service;

import java.util.List;

/**
 * @comments:组件service接口
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-6
 * @time:上午11:04:23
 * @version :
 */
public interface ComponentService {

	/**
	 * 通过pageId得到组件集
	 * 
	 * @param pageId页面ID
	 * @return
	 */
	public List<Object> findComponentsById(String pageId);
}
