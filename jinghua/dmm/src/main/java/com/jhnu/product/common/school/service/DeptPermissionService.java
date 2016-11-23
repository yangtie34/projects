package com.jhnu.product.common.school.service;

import java.util.List;

import com.jhnu.product.common.school.entity.Dept;
import com.jhnu.product.common.school.entity.DeptTeach;
import com.jhnu.system.permiss.entity.DataServe;

public interface DeptPermissionService {

	/**
	 * 重置 系统角色-权限-行政组织机构
	 * @param roleId 角色ID
	 * @param depts 行政组织机构IDs
	 */
	public void resetRoleDept(long roleId,String [] depts);
	
	/**
	 * 重置 特定用户-权限-行政组织机构
	 * @param userId 用户ID
	 * @param depes 行政组织机构IDs
	 */
	public void resetUserDept(long userId,String [] depts);
	
	/**
	 * 重置 系统角色-权限-教学组织机构
	 * @param roleId 角色ID
	 * @param deptTeachs 教学组织机构IDs
	 */
	public void resetRoleDeptTeach(long roleId,String [] deptTeachs);
	
	/**
	 * 重置 特定用户-权限-教学组织机构
	 * @param userId 用户ID
	 * @param deptTeachs 教学组织机构IDs
	 */
	public void resetUserDeptTeach(long userId,String [] deptTeachs);
	
	/**
	 * 获取教学组织机构权限集合
	 * @return 教学组织机构集合
	 */
	public List<DeptTeach> getAllDeptTeach();
	
	/**
	 * 获取行政组织机构权限集合
	 * @return 行政组织机构集合
	 */
	public List<Dept> getAllDept();
	
	/**
	 * 通过用户权限ID获取行政组织机构
	 * @param userPermId 用户权限ID
	 * @return
	 */
	public List<Dept> getDeptByUserPermsId(long userPermId);
	
	/**
	 * 通过用户权限ID获取教学组织机构
	 * @param userPermId 用户权限ID
	 * @return
	 */
	public List<DeptTeach> getDeptTeachByUserPermsId(long userPermId);
	
	/**
	 * 通过角色权限ID获取行政组织机构
	 * @param userPermId 角色权限ID
	 * @return
	 */
	public List<Dept> getDeptByRolePermsId(long rolePermId);
	
	/**
	 * 通过角色权限ID获取教学组织机构
	 * @param userPermId 角色权限ID
	 * @return
	 */
	public List<DeptTeach> getDeptTeachByRolePermsId(long rolePermId);
	
	/**
	 * 通过数据服务查询出组织机构ID的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getDeptIdSqlbyDataServe(DataServe data);
	
	/**
	 * 通过数据服务查询出教学机构ID的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getDeptTeachIdSqlbyDataServe(DataServe data);
	
}
