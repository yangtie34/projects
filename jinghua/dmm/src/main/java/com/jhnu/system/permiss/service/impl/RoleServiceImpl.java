package com.jhnu.system.permiss.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.handle.HasOneException;
import com.jhnu.framework.exception.param.EmptyParamException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.dao.RoleDao;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.service.PermssionService;
import com.jhnu.system.permiss.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
    private RoleDao roleDao;
	@Autowired
	private PermssionService permssionService;
	
	private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);

	@Transactional
    public Role createRole(Role role) throws AddException,ParamException{
		logger.info("====新增角色开始====");
    	Role ro = null;
    	if(role != null){
    		if(StringUtils.isEmpty(role.getName_())){
    			logger.error("====新增角色角色名不能为空====");
    			throw new EmptyParamException("角色名不能为空");
    		}else{
    			Role r = new Role();
            	r.setName_(role.getName_());
            	List<Role> rl = roleDao.getAllRole(r);
            	if(rl!=null && rl.size()>0){
            		logger.error("====新增角色角色名不能重复====");
            		throw new HasOneException("角色名不能重复");
            	}
    		}
    		if(StringUtils.isEmpty(role.getDescription())){
    			logger.error("====新增角色角色描述不能为空====");
    			throw new EmptyParamException("角色描述不能为空");
    		}
    		if(StringUtils.isEmpty(role.getIstrue())){
    			logger.error("====新增角色是否可用不能为空====");
    			throw new EmptyParamException("是否可用不能为空");
    		}
    		ro = roleDao.createRole(role);
    		logger.info("====新增角色结束====");
    	}
        return ro;
    }
	@Transactional
    public void deleteRole(Long roleId) {
		logger.info("====删除角色开始====");
        deleteRoleUsers(roleId);
        deleteRolePermssion(roleId);
        roleDao.deleteRole(roleId);
        logger.info("====删除角色结束====");
    }

	@Override
	public List<Role> getAllRole(Role role) {
		logger.info("====根据条件查询角色开始====");
		List<Role> roleList = roleDao.getAllRole(role);
		logger.info("====根据条件查询角色结束====");
		return roleList;
	}

	@Override
	public Page getPageRole(int currentPage, int numPerPage,Role role) {
		logger.info("====根据条件查询角色分页开始====");
		Page page = roleDao.getPageRole(currentPage,numPerPage,role);
		logger.info("====根据条件查询角色分页结束====");
		return page;
	}

	@Override
	@Transactional
	public void updateRole(Role role) throws ParamException{
		logger.info("====修改角色开始====");
		if(role != null){
			Role ro = new Role();
			ro.setName_(role.getName_());
			List<Role> lr = roleDao.getAllRole(ro);
			if(lr != null && lr.size()>0){
				for(Role r : lr){
					if(!role.getId().equals(r.getId())){
						logger.error("====修改的角色名已存在====");
						throw new ParamException("修改的角色名已存在");
					}
				}
			}
			roleDao.updateRole(role);
			logger.info("====修改角色结束====");
		}
	}

	@Override
	public void addRoleUsers(Long roleId, Long... userIds) {
		logger.info("====添加角色用户开始====");
		roleDao.addUserRoles(roleId, userIds);
		logger.info("====添加角色用户结束====");
	}
	@Override
	public ResultBean updateRoleIstureAjax(Long roleId, int istrue) {
		ResultBean rb = new ResultBean();
		logger.info("====修改角色是否可用开始====");
		roleDao.updateRoleIsture(roleId,istrue);
		rb.setTrue(true);
		logger.info("====修改角色是否可用结束====");
		return rb;
	}
	@Override
	public ResultBean setRoleResourceAjax(Long roleId, Long resourcedId) {
		ResultBean rb = new ResultBean();
		if(resourcedId==null){
			rb.setTrue(false);
			rb.setName("资源不能为空");
			return rb;
		}
		if(roleId==null){
			rb.setTrue(false);
			rb.setName("角色不能为空");
			return rb;
		}
		logger.info("====修改角色资源页面开始====");
		roleDao.updateRoleResourceId(roleId,resourcedId);
		rb.setTrue(true);
		logger.info("====修改角色资源页面结束====");
		return rb;
	}
	@Override
	public Role findByRoleId(Long roleId) {
		logger.info("====根据角色ID查询角色开始====");
		Role role = roleDao.findByRoleId(roleId);
		logger.info("====根据角色ID查询角色结束====");
		return role;
	}
	@Override
	public ResultBean createRoleAjax(Role role) {
		ResultBean rb = new ResultBean();
		try {
			this.createRole(role);
			rb.setTrue(true);
		} catch (AddException e) {
			rb.setTrue(false);
			rb.setName(e.getMessage());
		} catch (ParamException e) {
			rb.setTrue(false);
			rb.setName(e.getMessage());
		}
		return rb;
	}
	@Override
	public ResultBean deleteRoleAjax(Long roleId) {
		ResultBean rb = new ResultBean();
		deleteRole(roleId);
		rb.setTrue(true);
		return rb;
	}
	@Override
	public ResultBean addUserRolesAjax(String roleIds, String userIds) {
		ResultBean rb = new ResultBean();
		String[] uids = userIds.split(",");
		Long[] ids = new Long[uids.length];
		for(int i=0;i<uids.length;i++){
			ids[i] = Long.parseLong(uids[i]);
		}
		addRoleUsers(Long.parseLong(roleIds), ids);
		rb.setTrue(true);
		return rb;
	}
	@Override
	public List<Map<String, Object>> findRoleType() {
		return roleDao.findRoleType();
	}
	@Override
	public void deleteRoleUsers(Long roleId) {
		logger.info("====删除角色用户开始====");
		roleDao.deleteRoleUsers(roleId);
		logger.info("====删除角色用户结束====");
	}
	@Override
	public List<Role> getRoles() {
		return getAllRole(new Role());
	}
	@Override
	public void deleteRolePermssion(Long roleId) {
		 logger.info("====删除角色权限开始====");
		permssionService.deleteRolePermssionByRoleId(roleId);
		 logger.info("====删除角色权限结束====");
	}

	
	
	

}
