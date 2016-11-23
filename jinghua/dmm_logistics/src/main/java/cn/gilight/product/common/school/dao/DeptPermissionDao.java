package cn.gilight.product.common.school.dao;

import java.util.List;

import cn.gilight.product.common.school.entity.Dept;
import cn.gilight.product.common.school.entity.DeptTeach;


public interface DeptPermissionDao {

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
	 * 通过用户权限ID获取行政组织机构的SQL
	 * @param userPermId 用户权限ID
	 * @return
	 */
	public String getDeptIdSqlByUserPermsId(long userPermId);
	
	/**
	 * 通过用户权限ID获取教学组织机构的SQL
	 * @param userPermId 用户权限ID
	 * @return
	 */
	public String getDeptTeachIdSqlByUserPermsId(long userPermId);
	
	/**
	 * 通过角色权限ID获取行政组织机构的SQL
	 * @param userPermId 角色权限ID
	 * @return
	 */
	public String getDeptIdSqlByRolePermsId(long rolePermId);
	
	/**
	 * 通过角色权限ID获取教学组织机构的SQL
	 * @param userPermId 角色权限ID
	 * @return
	 */
	public String getDeptTeachIdSqlByRolePermsId(long rolePermId);
	
	
	public List<DeptTeach> getAllDeptTeachPerms();
	
	public List<Dept> getAllDeptPerms();
	
}
