package com.jhnu.syspermiss.permiss.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.permiss.entity.DataServe;

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

	boolean isInClassForStudent(String stuId, List<?> list);

	boolean isInDeptForTeacher(String teaId, List<?> list);
	
	/**
	 * 通过组织机构ID和已有的全数据权限，获取该组织机构下的数据权限。
	 * @param type 值：dept,dept_teach
	 * @param deptId 
	 * @param datas 为已有的全数据权限字符串
	 * @return
	 */
	public List<Map<String,Object>> getDeptDataByDeptAndData(String deptId,String datas);
	
	/**
	 * 通过教学组织机构ID和已有的全数据权限，获取该组织机构下的数据权限。
	 * @param type 值：dept,dept_teach
	 * @param deptId 
	 * @param datas 为已有的全数据权限字符串
	 * @return
	 */
	public List<Map<String,Object>> getDeptTeachDataByDeptAndData(String deptId,String datas);
	
}
