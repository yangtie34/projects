package com.jhnu.product.common.school.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.framework.entity.NodeTree;
import com.jhnu.product.common.school.dao.DeptPermissionDao;
import com.jhnu.product.common.school.entity.Dept;
import com.jhnu.product.common.school.entity.DeptTeach;
import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.system.permiss.entity.DataServe;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.common.UserUtil;

@Service("deptPermissionService")
public class deptPermissionServiceImpl implements DeptPermissionService{
	@Autowired
	private DeptPermissionDao deptPermissionDao;
	@Autowired
	private UserService userService;

	@Override
	public void resetRoleDept(long roleId, List<Map> depts) {
		deptPermissionDao.resetRoleDept(roleId, depts);
		
	}

	@Override
	public void resetUserDept(long userId, List<Map> depts) {
		deptPermissionDao.resetUserDept(userId, depts);
		
	}

	@Override
	public void resetRoleDeptTeach(long roleId, List<Map> deptTeachs) {
		deptPermissionDao.resetRoleDeptTeach(roleId, deptTeachs);
		
	}

	@Override
	public void resetUserDeptTeach(long userId, List<Map> deptTeachs) {
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

	@Override
	public String getDeptClassIdSqlbyDataServe(DataServe data) {
		
		return deptPermissionDao.getDeptClassIdSqlbyUserName(data.getUsername());
	}

	@Override
	public List<NodeAngularTree> getAllDeptPermsAgl() {
		return deptPermissionDao.getAllDeptPermsAgl();
	}
	@Override
	public List<NodeTree> getAllDeptPermsJL() {
		return deptPermissionDao.getAllDeptPermsJL();
	}
	@Override
	public List<NodeAngularTree> getAllDeptTeachAgl() {
		return deptPermissionDao.getAllDeptTeachAgl();
	}
	@Override
	public List<NodeTree> getAllDeptTeachJL() {
		return deptPermissionDao.getAllDeptTeachJL();
	}

	@Override
	public String getMyDeptSqlbyDataServe(DataServe data) {
		return deptPermissionDao.getMyDeptSqlbyUsername(data.getUsername());
	}
	@Override
	public Object getDeptByShiroTag(String shiroTag) {
		String userName=UserUtil.getCasLoginName();//获取用户名
		
		List<Dept> deptTeachList1=new ArrayList<Dept>();
		if(userService.getUserRootRole(userName).equalsIgnoreCase("admin")){
			deptTeachList1= deptPermissionDao.getAllDeptPerms();
			for(Dept dept:deptTeachList1){
				dept.setIstrue(1);
			}
		}else{
		deptTeachList1=deptPermissionDao.getDeptByUserNameAndShiroTag(userName, shiroTag);
		}
		Map<String,Dept> deptMap1=new HashMap<String,Dept>();
		Dept root=null;
		for(Dept dept:deptTeachList1){
			deptMap1.put(dept.getId(), dept);
		}
		for(Dept dept:deptTeachList1){
			if(dept.getLevel_().intValue()==0){
				root=dept;
			}else{
				if(deptMap1.get(dept.getPid())!=null)
					deptMap1.get(dept.getPid()).getChildren().add(dept);
			}
			
		}
		if(root!=null){
			return root;
		}
		return null;
	}
	@Override
	public Object getDeptTeachByShiroTag(String shiroTag) {
		String userName=UserUtil.getCasLoginName();//获取用户名
		
		List<DeptTeach> deptTeachList1=new ArrayList<DeptTeach>();
		if(userService.getUserRootRole(userName).equalsIgnoreCase("admin")){
			deptTeachList1= deptPermissionDao.getAllDeptTeachPerms();
			for(DeptTeach dept:deptTeachList1){
				dept.setIstrue(1);
			}
		}else{
		deptTeachList1=deptPermissionDao.getDeptTeachByUserNameAndShiroTag(userName, shiroTag);
		}
		Map<String,DeptTeach> deptMap1=new HashMap<String,DeptTeach>();
		DeptTeach root=null;
		for(DeptTeach dept:deptTeachList1){
			deptMap1.put(dept.getId(), dept);
		}
		for(DeptTeach dept:deptTeachList1){
			if(dept.getLevel_().intValue()==0){
				root=dept;
			}else{
				if(deptMap1.get(dept.getPid())!=null)
					deptMap1.get(dept.getPid()).getChildren().add(dept);
			}
			
		}
		if(root!=null){
			return root;
		}
		return null;
	}
}
