package com.jhnu.system.permiss.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.permiss.entity.DataServe;

public interface DataServeDao {

	/**
	 * 根据ID查询数据服务
	 * @return
	 */
	public DataServe findById(Long id);
    
	/**
	 * 获取数据服务范围
	 * @param username 用户登陆名
	 * @param shiroTag 权限通配符
	 * @return
	 */
	public List<DataServe> getDataServe(String username,String shiroTag);
	
	/**
	 * 查询所有数据服务
	 * @return
	 */
	public List<Map<String,Object>> findAll();
	
}
