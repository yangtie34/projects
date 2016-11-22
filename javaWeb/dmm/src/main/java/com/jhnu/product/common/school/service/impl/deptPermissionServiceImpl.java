package com.jhnu.product.common.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.common.school.dao.DeptPermissionDao;
import com.jhnu.product.common.school.entity.Dept;
import com.jhnu.product.common.school.entity.DeptTeach;
import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.system.permiss.entity.DataServe;
import com.jhnu.system.permiss.service.UserService;

@Service("deptPermissionService")
public class deptPermissionServiceImpl implements DeptPermissionService{
	@Autowired
	private DeptPermissionDao deptPermissionDao;
	@Autowired
	private UserService userService;

	@Override
	public void resetRoleDept(long roleId, String[] depts) {
		deptPermissionDao.resetRoleDept(roleId, depts);
		
	}

	@Override
	public void resetUserDept(long userId, String[] depts) {
		deptPermissionDao.resetUserDept(userId, depts);
		
	}

	@Override
	public void resetRoleDeptTeach(long roleId, String[] deptTeachs) {
		deptPermissionDao.resetRoleDeptTeach(roleId, deptTeachs);
		
	}

	@Override
	public void resetUserDeptTeach(long userId, String[] deptTeachs) {
		deptPermissionDao.resetUserDeptTeach(userId, deptTeachs);
		
	}

	@Override
	public List<DeptTeach> getAllDeptTeach() {
		return deptPermissionDao.getAllDeptTeachPerms();
	}

	@Override
	public List<Dept> getAllDept() {
		return deptPermissionDao.getAllDeptPerms();
	}

	@Override
	public List<Dept> getDeptByUserPermsId(long userPermId) {
		return deptPermissionDao.getDeptByUserPermsId(userPermId);
	}

	@Override
	public List<DeptTeach> getDeptTeachByUserPermsId(long userPermId) {
		return deptPermissionDao.getDeptTeachByUserPermsId(userPermId);
	}

	@Override
	public List<Dept> getDeptByRolePermsId(long rolePermId) {
		return deptPermissionDao.getDeptByRolePermsId(rolePermId);
	}

	@Override
	public List<DeptTeach> getDeptTeachByRolePermsId(long rolePermId) {
		return deptPermissionDao.getDeptTeachByRolePermsId(rolePermId);
	}

	@Override
	public String getDeptIdSqlbyDataServe(DataServe data) {
		if(data!=null){
			if("user".equals(data.getPerm_type())){
				return deptPermissionDao.getDeptIdSqlByUserPermsId(data.getPerm_id());
			}else if("role".equals(data.getPerm_type())){
				return deptPermissionDao.getDeptIdSqlByRolePermsId(data.getPerm_id());
			}
		}
		return null;
	}
	
	@Override
	public String getDeptTeachIdSqlbyDataServe(DataServe data) {
		if(data!=null){
			if("user".equals(data.getPerm_type())){
				return deptPermissionDao.getDeptTeachIdSqlByUserPermsId(data.getPerm_id());
			}else if("role".equals(data.getPerm_type())){
				return deptPermissionDao.getDeptTeachIdSqlByRolePermsId(data.getPerm_id());
			}
		}
		return null;
	}

}
