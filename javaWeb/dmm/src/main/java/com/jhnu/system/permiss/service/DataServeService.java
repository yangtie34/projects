package com.jhnu.system.permiss.service;

import java.util.List;
import java.util.Map;

import com.jhnu.system.permiss.entity.DataServe;

public interface DataServeService {
	
	/**
	 * 获取数据服务范围
	 * @param username 用户登陆名
	 * @param shiroTag 权限通配符
	 * @return
	 */
	public List<DataServe> getDataServe(String username,String shiroTag);
	
	/**
	 * 通过ID获取数据服务范围
	 * @param id
	 * @return
	 */
	public DataServe findDataServeById(Long id);
	
	/**
	 * 通过数据范围LIST获取数据范围对应的SQL语句
	 * 返回数据范围所对应类执行之后的SQL
	 * SQL的输入字段为：id，此值只能为班级ID或单位ID。即精确到组织机构中的最底层
	 * @param username 用户登陆名称
	 * @param shiroTag 权限通配符
	 * @return
	 */
	public String getDataServeSqlbyUserIdShrio(String username, String shiroTag);
	
	/**
	 * 通过数据范围LIST获取数据范围对应的SQL语句
	 * 返回数据范围所对应类执行之后的SQL
	 * SQL的输入字段为：id，此值只能为班级ID或单位ID。即精确到组织机构中的最底层
	 * @param datas
	 * @return
	 */
	public String getSqlbyDataServe(List<DataServe> datas);
	
	/**
	 * 在已有的数据服务范围中，是否有该人的权限
	 * @param thisOneId
	 * @param datas
	 * @return
	 */
	public boolean hasThisOnePerm(String thisOneLoginName,List<DataServe> datas);
	
	/**
	 * 在已有的数据服务范围中，是否有该人的权限
	 * @param thisOneLoginName 被验证人员登陆账号
	 * @param username 验证这登陆账号
	 * @param shiroTag 权限代码
	 * @return
	 */
	public boolean hasThisOnePerm(String thisOneLoginName,String username, String shiroTag);
	
	/**
	 * 获取所有数据服务范围
	 * @return
	 */
	public List<Map<String,Object>> findAll();
	
	
}
