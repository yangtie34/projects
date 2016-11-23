package com.jhnu.syspermiss.permiss.service;

import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.permiss.entity.Role;

public interface RoleService {

	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
    public Role createRole(Role role);
    /**
     * 删除角色
     * @param roleId
     */
    public void deleteRole(Long roleId);
    /**
	 * 修改角色
	 */
	public void updateRole(Role role);
    /**
     * 根据条件查找角色
     * @param role
     * @return
     */
    public List<Role> getAllRole(Role role);
    /**
     * 查找角色
     * @param roleId 角色ID
     * @return
     */
    public Role findByRoleId(Long roleId);
    
    
    /**
     * 查询所有角色类型
     * @return
     */
    public List<Map<String,Object>> findRoleType();
    
    public List<Role> getRoles();
    
}
