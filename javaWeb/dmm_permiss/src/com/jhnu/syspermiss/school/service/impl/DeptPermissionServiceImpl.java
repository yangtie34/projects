package com.jhnu.syspermiss.school.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.permiss.entity.DataServe;
import com.jhnu.syspermiss.permiss.service.UserService;
import com.jhnu.syspermiss.permiss.service.impl.UserServiceImpl;
import com.jhnu.syspermiss.school.dao.DeptPermissionDao;
import com.jhnu.syspermiss.school.dao.impl.DeptPermissionDaoImpl;
import com.jhnu.syspermiss.school.entity.Dept;
import com.jhnu.syspermiss.school.entity.DeptTeach;
import com.jhnu.syspermiss.school.service.DeptPermissionService;
import com.jhnu.syspermiss.util.UserUtil;

public class DeptPermissionServiceImpl implements DeptPermissionService{
	private DeptPermissionServiceImpl() {
		
	}  
    private static DeptPermissionServiceImpl DeptPermissionServiceImpl=null;
	
	public static DeptPermissionServiceImpl getInstance() {
		if (DeptPermissionServiceImpl == null){
			synchronized (new String()) {
				if (DeptPermissionServiceImpl == null){
					DeptPermissionServiceImpl = new DeptPermissionServiceImpl();
				}
			}
		}
		return DeptPermissionServiceImpl;
	}
	private DeptPermissionDao deptPermissionDao= DeptPermissionDaoImpl.getInstance();
	UserService userService= UserServiceImpl.getInstance();

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
	public Object getDeptByShiroTag(String shiroTag) {
		String userName=UserUtil.getCasLoginName();//获取用户名
		
		List<Dept> deptList1=new ArrayList<Dept>();
		if(userService.getUserRootRole(userName).equalsIgnoreCase("admin")){
			deptList1= deptPermissionDao.getAllDeptPerms();
			for(Dept dept:deptList1){
				dept.setIstrue(1);
			}
		}else{
		deptList1=deptPermissionDao.getDeptByUserNameAndShiroTag(userName, shiroTag);
		}
		Map<String,Dept> deptMap1=new HashMap<String,Dept>();
		Dept root=null;
		for(Dept dept:deptList1){
			deptMap1.put(dept.getId(), dept);
		}
		for(Dept dept:deptList1){
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

	@Override
	public String getMyDeptSqlbyDataServe(DataServe data) {
		return deptPermissionDao.getMyDeptSqlbyUsername(data.getUsername());
	}
}
