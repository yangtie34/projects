package com.jhnu.system.permiss.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jhnu.enums.DataServeEnum;
import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.param.EmptyParamException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.dao.PermssionDao;
import com.jhnu.system.permiss.entity.DataServe;
import com.jhnu.system.permiss.entity.Operate;
import com.jhnu.system.permiss.entity.Resources;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.entity.RolePermssion;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserPermssion;
import com.jhnu.system.permiss.service.DataServeService;
import com.jhnu.system.permiss.service.OperateService;
import com.jhnu.system.permiss.service.PermssionService;
import com.jhnu.system.permiss.service.ResourcesService;
import com.jhnu.system.permiss.service.RoleService;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.common.MapUtils;

@Service("permssionService")
public class PermssionServiceImpl implements PermssionService{

	
	@Autowired
	private PermssionDao permssionDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private UserService userService;
	@Autowired
	private OperateService operateService;
	@Autowired
	private DataServeService dataServeService;
	@Autowired
	private DeptPermissionService deptPermissionService;
	
	private static final Logger logger = Logger.getLogger(PermssionServiceImpl.class);
	
	@Override
	public void addUserPermssion(List<UserPermssion> userPermssions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRolePermssion(List<RolePermssion> rolePermssion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserPermssion> getUserPermssion(UserPermssion userPermssion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RolePermssion> getRolePermssion(RolePermssion rolePermssion) {
		return permssionDao.getRolePermssion(rolePermssion);
	}

	@Override
	@Transactional
	public UserPermssion addUserPermssion(UserPermssion userPermssion) throws AddException,ParamException{
		if(userPermssion != null){
			if(userPermssion.getUser() == null || StringUtils.isEmpty(userPermssion.getUser().getId())){
				throw new EmptyParamException("添加用户权限的用户或用户ID不能为空");
			}
			if(userPermssion.getResource() == null || StringUtils.isEmpty(userPermssion.getResource().getId())){
				throw new EmptyParamException("添加用户权限的资源或资源ID不能为空");
			}
			if(userPermssion.getOperate() == null || StringUtils.isEmpty(userPermssion.getOperate().getId())){
				throw new EmptyParamException("添加用户权限的操作或操作ID不能为空");
			}
			if(userPermssion.getDataServe() == null || StringUtils.isEmpty(userPermssion.getDataServe().getId())){
				throw new EmptyParamException("添加用户权限的数据服务或数据服务ID不能为空");
			}
			if(StringUtils.isEmpty(userPermssion.getResource().getShiro_tag()) || StringUtils.isEmpty(userPermssion.getOperate().getName_())){
				throw new EmptyParamException("添加用户权限的shiro权限通配符（shiro资源标识符:操作名称）");
			}
			User user = new User();
			user.setId(userPermssion.getUser().getId());
			List<User> users = userService.getAllUsers(user);
			if(users != null && users.size()>0){
				user = users.get(0);
			}
			if(user == null){
				throw new ParamException("添加用户权限的用户不存在");
			}
			Resources rs = resourcesService.findById(userPermssion.getResource().getId());
			if(rs == null){
				throw new ParamException("添加用户权限的资源不存在");
			}
			Operate op = operateService.findById(userPermssion.getOperate().getId());
			if(op == null){
				throw new ParamException("添加用户权限的操作不存在");
			}
			DataServe ds = dataServeService.findDataServeById(userPermssion.getDataServe().getId());
			if(ds == null){
				throw new ParamException("添加用户权限的数据服务不存在");
			}
			return permssionDao.addUserPermssion(userPermssion);
		}
		return userPermssion;
	}

	@Override
	@Transactional
	public RolePermssion addRolePermssion(RolePermssion rolePermssion) throws AddException,ParamException{
		if(rolePermssion != null){
			if(rolePermssion.getRole() == null || StringUtils.isEmpty(rolePermssion.getRole().getId())){
				throw new EmptyParamException("添加角色权限的角色或角色ID不能为空");
			}
			if(rolePermssion.getResource() == null || StringUtils.isEmpty(rolePermssion.getResource().getId())){
				throw new EmptyParamException("添加角色权限的资源或资源ID不能为空");
			}
			if(rolePermssion.getOperate() == null || StringUtils.isEmpty(rolePermssion.getOperate().getId())){
				throw new EmptyParamException("添加角色权限的操作或操作ID不能为空");
			}
			if(rolePermssion.getDataServe() == null || StringUtils.isEmpty(rolePermssion.getDataServe().getId())){
				throw new EmptyParamException("添加角色权限的数据服务或数据服务ID不能为空");
			}
			if(StringUtils.isEmpty(rolePermssion.getResource().getShiro_tag()) || StringUtils.isEmpty(rolePermssion.getOperate().getName_())){
				throw new EmptyParamException("添加角色权限的shiro权限通配符（shiro资源标识符:操作名称）");
			}
			Role role = roleService.findByRoleId(rolePermssion.getRole().getId());
			if(role == null){
				throw new ParamException("添加角色权限的角色不存在");
			}
			Resources rs = resourcesService.findById(rolePermssion.getResource().getId());
			if(rs == null){
				throw new ParamException("添加角色权限的资源不存在");
			}
			Operate op = operateService.findById(rolePermssion.getOperate().getId());
			if(op == null){
				throw new ParamException("添加角色权限的操作不存在");
			}
			DataServe ds = dataServeService.findDataServeById(rolePermssion.getDataServe().getId());
			if(ds == null){
				throw new ParamException("添加角色权限的数据服务不存在");
			}
			return permssionDao.addRolePermssion(rolePermssion);
		}
		return rolePermssion;
	}

	@Override
	public Page getRolePermssionPage(int currentPage, int numPerPage,
			RolePermssion rolePermssion) {
		return permssionDao.getRolePermssionPage(currentPage, numPerPage, rolePermssion);
	}
	
	@Override
	public Page getUserPermssionPage(int currentPage, int numPerPage,
			UserPermssion userPermssion) {
		return permssionDao.getUserPermssionPage(currentPage, numPerPage, userPermssion);
	}
	@Override
	public ResultBean addPermssionsAjax(Map<String, String> map,List<Map> list) {
		ResultBean rb = new ResultBean();
		for(int i=0;i<list.size();i++){
			map.put("resName",(String) list.get(i).get("mc"));
			map.put("resId",(String) list.get(i).get("id"));
			rb=addPermssionAjax(map);
		}
		return rb;
	}
	@Override
	public ResultBean addPermssionAjax(Map<String, String> map) {
		ResultBean rb = new ResultBean();
		String peopleType=MapUtils.getString(map, "peopleType");
		String peopleId= MapUtils.getString(map, "peopleId");
		String resId= MapUtils.getString(map, "resId");
		String dataId= MapUtils.getString(map,"dataId");
		String operId= MapUtils.getString(map,"operId");
		String jgIds= MapUtils.getString(map,"jgIds");
		if(StringUtils.isEmpty(peopleType)){
			rb.setTrue(false);
			rb.setName("人员类型不能为空");
			return rb;
		}
		if(StringUtils.isEmpty(peopleId)){
			rb.setTrue(false);
			rb.setName("人员ID不能为空");
			return rb;
		}
		if(StringUtils.isEmpty(resId)){
			rb.setTrue(false);
			rb.setName("资源ID不能为空");
			return rb;
		}
		if(StringUtils.isEmpty(operId)){
			rb.setTrue(false);
			rb.setName("操作ID不能为空");
			return rb;
		}
		if(StringUtils.isEmpty(dataId)){
			rb.setTrue(false);
			rb.setName("数据范围ID不能为空");
			return rb;
		}else if(DataServeEnum.JXJG.getCode().equals(dataId)){
			if(StringUtils.isEmpty(jgIds)){
				rb.setTrue(false);
				rb.setName("组织机构不能为空");
				return rb;
			}	
		}else if(DataServeEnum.XZJG.getCode().equals(dataId)){
			if(StringUtils.isEmpty(jgIds)){
				rb.setTrue(false);
				rb.setName("组织机构不能为空");
				return rb;
			}	
		}
		if("user".equals(peopleType)){
			UserPermssion up=new UserPermssion();
			User user=new User();
			user.setId(Long.parseLong(peopleId));
			Resources res=resourcesService.findById(Long.parseLong(resId));
			Operate oper= operateService.findById(Long.parseLong(operId));
			DataServe data=new DataServe();
			data.setId(Long.parseLong(dataId));
			up.setOperate(oper);
			up.setDataServe(data);
			up.setResource(res);
			up.setUser(user);
			try {
				up=addUserPermssion(up);
				rb.setTrue(true);
				rb.setObject(up);
			} catch (AddException e) {
				rb.setTrue(false);
				rb.setName(e.getMessage());
			} catch (ParamException e) {
				rb.setTrue(false);
				rb.setName(e.getMessage());
			}
			if(DataServeEnum.JXJG.getCode().equals(dataId)){ //教学
				String [] jgids = jgIds.split(",");
				deptPermissionService.resetUserDeptTeach(up.getId(), jgids);
			}else if(DataServeEnum.XZJG.getCode().equals(dataId)){ //组织
				String[] jgids = jgIds.split(",");
				deptPermissionService.resetUserDept(up.getId(), jgids);
			}
			
		}else{
			RolePermssion rp=new RolePermssion();
			Role role=new Role();
			role.setId(Long.parseLong(peopleId));
			Resources res=resourcesService.findById(Long.parseLong(resId));
			Operate oper= operateService.findById(Long.parseLong(operId));
			DataServe data=new DataServe();
			data.setId(Long.parseLong(dataId));
			rp.setOperate(oper);
			rp.setDataServe(data);
			rp.setResource(res);
			rp.setRole(role);
			try {
				rp = addRolePermssion(rp);
				rb.setTrue(true);
				rb.setObject(rp);
			} catch (AddException e) {
				rb.setTrue(false);
				rb.setName(e.getMessage());
			} catch (ParamException e) {
				rb.setTrue(false);
				rb.setName(e.getMessage());
			}
			if(DataServeEnum.JXJG.getCode().equals(dataId)){ //教学
				String [] jgids = jgIds.split(",");
				deptPermissionService.resetRoleDeptTeach(rp.getId(), jgids);
			}else if(DataServeEnum.XZJG.getCode().equals(dataId)){ //组织
				String[] jgids = jgIds.split(",");
				deptPermissionService.resetRoleDept(rp.getId(), jgids);
			}
			
		}
		return rb;
	}

	@Override
	@Transactional
	public ResultBean deleteRolePermssion(Long rolePermssionId) {
		ResultBean rb = new ResultBean();
		String strs[] = new String[0] ;
		permssionDao.depeteRolePermssion(rolePermssionId);
		deptPermissionService.resetRoleDept(rolePermssionId, strs);
		deptPermissionService.resetRoleDeptTeach(rolePermssionId, strs);
		rb.setTrue(true);
		return rb;
	}

	@Override
	@Transactional
	public ResultBean deleteUserPermssion(Long userPermssionId) {
		ResultBean rb = new ResultBean();
		String strs[] = new String[0] ;
		permssionDao.depeteUserPermssion(userPermssionId);
		deptPermissionService.resetUserDept(userPermssionId, strs);
		deptPermissionService.resetUserDeptTeach(userPermssionId, strs);
		rb.setTrue(true);
		return rb;
	}

	@Override
	public void updateRolePermssion(Operate operate) {
		logger.info("====开始根据操作的name_修改角色权限shiro通配符====");
		permssionDao.updateRolePermssion(operate);
		logger.info("====根据操作的name_修改角色权限shiro通配符结束====");
	}

	@Override
	public void updateUserPermssion(Operate operate) {
		logger.info("====开始根据操作的name_修改用户权限shiro通配符====");
		permssionDao.updateUserPermssion(operate);
		logger.info("====根据操作的name_修改用户权限shiro通配符结束====");
	}

	@Override
	public void updateRolePermssion(Resources resource) {
		logger.info("====开始根据资源的shiro资源标识符修改角色权限shiro通配符====");
		permssionDao.updateRolePermssion(resource);
		logger.info("====根据资源的shiro资源标识符修改角色权限shiro通配符结束====");
	}

	@Override
	public void updateUserPermssion(Resources resource) {
		logger.info("====开始根据资源的shiro资源标识符修改用户权限shiro通配符====");
		permssionDao.updateUserPermssion(resource);
		logger.info("====根据资源的shiro资源标识符修改用户权限shiro通配符结束====");
	}

	@Override
	public ResultBean resetZzOrJxjg(Map<String, String> map) {
		ResultBean rb = new ResultBean();
		String peopleType=MapUtils.getString(map, "peopleType");
		String jgType=MapUtils.getString(map, "jgType");
		Long permId=MapUtils.getLong(map, "permId");
		String jgIds=MapUtils.getString(map, "jgIds");
		String [] jgid=jgIds.split(",");
		rb.setTrue(false);
		rb.setName("更新失败");
		if("role".equals(peopleType)){
			if("jxjg".equals(jgType)){
				deptPermissionService.resetRoleDeptTeach(permId, jgid);
				rb.setTrue(true);
			}else if("zzjg".equals(jgType)){
				deptPermissionService.resetRoleDept(permId, jgid);
				rb.setTrue(true);
			}
		}else if("user".equals(peopleType)){
			if("jxjg".equals(jgType)){
				deptPermissionService.resetUserDeptTeach(permId, jgid);
				rb.setTrue(true);
			}else if("zzjg".equals(jgType)){
				deptPermissionService.resetUserDept(permId, jgid);
				rb.setTrue(true);
			}
		}
		return rb;
	}

	@Override
	public void deleteRolePermssionByRoleId(Long roleId) {
		permssionDao.deleteRolePermssionByRoleId(roleId);
	}
	
}

