package com.jhnu.syspermiss;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jhnu.syspermiss.permiss.entity.Operate;
import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.entity.RolePermssion;
import com.jhnu.syspermiss.permiss.entity.User;
import com.jhnu.syspermiss.permiss.entity.UserPermssion;
import com.jhnu.syspermiss.permiss.service.DataServeService;
import com.jhnu.syspermiss.permiss.service.OperateService;
import com.jhnu.syspermiss.permiss.service.PermssionService;
import com.jhnu.syspermiss.permiss.service.ResourcesService;
import com.jhnu.syspermiss.permiss.service.RoleService;
import com.jhnu.syspermiss.permiss.service.UserService;
import com.jhnu.syspermiss.permiss.service.impl.DataServeServiceImpl;
import com.jhnu.syspermiss.permiss.service.impl.OperateServiceImpl;
import com.jhnu.syspermiss.permiss.service.impl.PermssionServiceImpl;
import com.jhnu.syspermiss.permiss.service.impl.ResourcesServiceImpl;
import com.jhnu.syspermiss.permiss.service.impl.RoleServiceImpl;
import com.jhnu.syspermiss.permiss.service.impl.UserServiceImpl;

public class GetPermiss {
	private static UserService userService= UserServiceImpl.getInstance();
	private static RoleService roleService= RoleServiceImpl.getInstance();
	private static ResourcesService resourcesService= ResourcesServiceImpl.getInstance();
	private static PermssionService permssionService= PermssionServiceImpl.getInstance();
	private static OperateService operateService= OperateServiceImpl.getInstance();
	private static DataServeService dataServeService= DataServeServiceImpl.getInstance();
    /**
     * 通过用户名获取在该系统路径下的所拥有的菜单
     * @param username
     * @param sys 系统路径
     * @return
     */
    protected static List<Resources> getMenusByUserName(String username,String sys){
    	return resourcesService.getMenusByUserName(username, sys);
    }
    
    /**
     * 通过用户名在该权限标识下的获取所拥有的菜单
     * @param username 用户名
     * @param shiroTag 权限标识
     * @return
     */
    protected static List<Resources> getMenusByUserNameShiroTag(String username,String shiroTag){
    	return resourcesService.getMenusByUserNameShiroTag(username, shiroTag);
    }
	
	protected static Operate createOperate(Operate operate) {
		return operateService.createOperate(operate);
	}

	protected static void deleteOperate(Long operateId) {
		operateService.deleteOperate(operateId);
		
	}

	protected static void updateOperate(Operate operate) {
		operateService.updateOperate(operate);
	}

	protected static UserPermssion addUserPermssion(UserPermssion userPermssion) {
			return permssionService.addUserPermssion(userPermssion);
	}

	protected static RolePermssion addRolePermssion(RolePermssion rolePermssion) {
			return permssionService.addRolePermssion(rolePermssion);
	}


	protected static void updateRolePermssion(Operate operate) {
		permssionService.updateRolePermssion(operate);
	}

	protected static void updateUserPermssion(Operate operate) {
		permssionService.updateUserPermssion(operate);
	}

	protected static void updateRolePermssion(Resources resource) {
		permssionService.updateRolePermssion(resource);
	}

	protected static void updateUserPermssion(Resources resource) {
		permssionService.updateUserPermssion(resource);
	}


	protected static void deleteRolePermssionByRoleId(Long roleId) {
		permssionService.deleteRolePermssionByRoleId(roleId);
	}
	    protected static Role createRole(final Role Role) {
	        return roleService.createRole(Role);
	    }
	    protected static void deleteRole(Long roleId) {
	    	roleService.deleteRole(roleId);
	    }
		protected static void updateRole(Role role) {
			roleService.updateRole(role);
		}
	protected static User createUser(User user) {
		return userService.createUser(user);
	}

	protected static void updateUser(User user) {
		userService.updateUser(user);
	}

	protected static void deleteUserById(Long userId) {
		userService.deleteUserById(userId);
	}
	/**
     * 验证密码
     * @param userId
     * @param password
     */
    public boolean checkPassword(Long userId, String password){
    	return userService.checkPassword(userId, password);
    };

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword){
    	 userService.changePassword(userId, newPassword);
    };
	  /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    protected static User findByUsername(String username){
    	return userService.findByUsername(username);
    }

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    protected static Set<String> findRoles(String username){
    	return userService.findRoles(username);
    }
    /**
     * 根据条件查询角色对象集合
     * @param role
     * @return
     */
	protected static List<Role> findRolesList(String username){
		return userService.findRolesList(username);
	}
    /**
     * 通过用户ID查找角色名集合
     * @param userId 用户ID
     * @return
     */
    protected static List<String> getRoleNamesByUserId(long userId){
    	return userService.getRoleNamesByUserId(userId);
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    protected static Set<String> findPermissions(String username){
    	return userService.findPermissions(username);
    }
    /**
     * 获取该用户的根本角色 即返回student，teacher，admin
     * @param username 用户名
     * @return
     */
    protected static String getUserRootRole(String username){
    	return userService.getUserRootRole(username);
    }
    /**
     * 查找角色
     * @param roleId 角色ID
     * @return
     */
    protected static Role findByRoleId(Long roleId){
    	return roleService.findByRoleId(roleId);
    }
    /**
     * 通过角色获取主页信息
     * @param role
     * @return
     */
    protected static Resources getMainPageByRole(Role role){
    	return resourcesService.getMainPageByRole(role);
    }
    
    /**
     * 通过角色获取主页信息
     * @param user
     * @return
     */
    protected static Resources getMainPageByUser(User user){
    	return resourcesService.getMainPageByUser(user);
    }
    
    /**
     * 通过用户名获取所拥有的菜单
     * @param username
     * @return
     */
    protected static List<Resources> getMenusByUserName(String username){
    	return resourcesService.getMenusByUserName(username);
    }
    
    /**
     * 获取该用户名所有拥有的权限
     * @param username
     * @return
     */
    protected static List<String> getAllPermssionByUserName(String username){
    	return resourcesService.getAllPermssionByUserName(username);
    }
    
    /**
     * 该用户名是否拥有该权限。
     * @param username
     * @param shiroTag
     * @return
     */
    protected static boolean hasPermssion(String username,String shiroTag){
    	return resourcesService.hasPermssion(username,shiroTag);
    }
    /**
     * 获得用户权限关系
     * @param userPermssion
     * @return
     */
    protected static List<UserPermssion> getUserPermssion(UserPermssion userPermssion){
    	return permssionService.getUserPermssion(userPermssion);
    }
    
    /**
     * 获得角色权限关系
     * @param userPermssion
     * @return
     */
    protected static List<RolePermssion> getRolePermssion(RolePermssion rolePermssion){
    	return permssionService.getRolePermssion(rolePermssion);
    }
    /**
	 * 获取数据服务范围
	 * @param username 用户登陆名
	 * @param shiroTag 权限通配符
	 * @return
	 */
	/*protected static List<DataServe> getDataServe(String username,String shiroTag){
    	return dataServeService.getDataServe(username, shiroTag);
    }*/
	/**
	 * 在已有的数据服务范围中，是否有该人的权限
	 * @param thisOneId
	 * @param datas
	 * @return
	 */
/*	protected static boolean hasThisOnePerm(String thisOneLoginName,List<DataServe> datas){
    	return dataServeService.hasThisOnePerm(thisOneLoginName, datas);
    }*/
    /**
	 * 通过数据范围LIST获取数据范围对应的SQL语句
	 * 返回数据范围所对应类执行之后的SQL
	 * SQL的输入字段为：id，此值只能为班级ID或单位ID。即精确到组织机构中的最底层
	 * @param username 用户登陆名称
	 * @param shiroTag 权限通配符
	 * @return
	 */
	protected static List<String> getDataServeSqlbyUserIdShrio(String username, String shiroTag){
		if("admin".equalsIgnoreCase(getUserRootRole(username))){
			List<String> list=new ArrayList<String>();
			list.add("'0'");list.add("''");list.add("''");list.add("''");
    		return list;
    	}
		return dataServeService.getDataServeSqlbyUserIdShrio(username, shiroTag);
	}
	 /**
     * 该用户名是否拥有资源该数据权限。
     * @param username
     * @param shiroTag
     * @return
     */
    protected static boolean hasPermssion(String username,String shiroTag,String dataId){
    	List<String> daIds=GetPermiss.getDataServeSqlbyUserIdShrio(username,shiroTag);
    	for(int i=0;i<daIds.size();i++){
    		String ids[]=daIds.get(i).replaceAll("'","").split(",");
    		for(int j=0;j<ids.length;j++){
    			if(dataId.equalsIgnoreCase(ids[j])){
    				return true;
    			}
    		}
    	}
    	return false;
    }
	/**
	 * 在已有的数据服务范围中，是否有该人的权限
	 * @param thisOneLoginName 被验证人员登陆账号
	 * @param username 验证这登陆账号
	 * @param shiroTag 权限代码
	 * @return
	 */
	protected static boolean hasThisOnePerm(String thisOneLoginName,String username, String shiroTag){
		if("admin".equalsIgnoreCase(getUserRootRole(username))){
    		return true;
    	}
    	return dataServeService.hasThisOnePerm(thisOneLoginName, username, shiroTag);
    }
    
}
