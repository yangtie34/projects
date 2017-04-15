package cn.gilight.product.common.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.common.school.dao.impl.DeptPermissionDaoImpl;
import cn.gilight.product.common.school.entity.Dept;
import cn.gilight.product.common.school.entity.DeptTeach;
import cn.gilight.product.common.school.service.DeptPermissionService;


@Service("deptPermissionService")
public class deptPermissionServiceImpl implements DeptPermissionService{
	@Autowired
	private DeptPermissionDaoImpl deptPermissionDao;

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

}
