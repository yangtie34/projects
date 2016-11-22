package com.jhnu.product.common.school.service;

import java.util.List;
import java.util.Map;

import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.framework.entity.NodeTree;
import com.jhnu.product.common.school.entity.Dept;
import com.jhnu.product.common.school.entity.DeptTeach;
import com.jhnu.system.permiss.entity.DataServe;

public interface DeptPermissionService {

	/**
	 * 重置 系统角色-权限-行政组织机构
	 * @param roleId 角色ID
	 * @param depts 行政组织机构IDs
	 */
	public void resetRoleDept(long roleId,List<Map> depts);
	
	/**
	 * 重置 特定用户-权限-行政组织机构
	 * @param userId 用户ID
	 * @param depes 行政组织机构IDs
	 */
	public void resetUserDept(long userId,List<Map> depts);
	
	/**
	 * 重置 系统角色-权限-教学组织机构
	 * @param roleId 角色ID
	 * @param deptTeachs 教学组织机构IDs
	 */
	public void resetRoleDeptTeach(long roleId,List<Map> deptTeachs);
	
	/**
	 * 重置 特定用户-权限-教学组织机构
	 * @param userId 用户ID
	 * @param deptTeachs 教学组织机构IDs
	 */
	public void resetUserDeptTeach(long userId,List<Map> deptTeachs);
	
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
	/**
	 * 通过数据服务查询出辅导员辅导ID的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getDeptClassIdSqlbyDataServe(DataServe data);
	
	/**
	 * 通过数据服务查询出教职工所在院系的SQL
	 * @param data 数据服务
	 * @return
	 */
	public String getMyDeptSqlbyDataServe(DataServe data);

	public List<NodeAngularTree> getAllDeptPermsAgl();
	public List<NodeTree> getAllDeptPermsJL();

	public List<NodeAngularTree> getAllDeptTeachAgl();

	public List<NodeTree> getAllDeptTeachJL();
	/**
	 * 通过资源 获取教学组织机构 (系统自动获取相关参数)
	 * @param
	 * @return
	 */
	public Object getDeptTeachByShiroTag(String shiroTag);
	/**
	 * 通过资源 获取组织机构 (系统自动获取相关参数)
	 * @param
	 * @return
	 */
	public Object getDeptByShiroTag(String shiroTag);
}
