package com.jhkj.mosdc.permission.dao;

import java.util.List;

/**
 * @Comments: 学校标准字典代码结构DAO
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-5-24
 * @TIME: 上午9:50:26
 */
public interface ZdDMDao {
	/**
	 * 获取字典代码分类数据
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public List queryZdDMType(String param) throws Exception;
	
}
