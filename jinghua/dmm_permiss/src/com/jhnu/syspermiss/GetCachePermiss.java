package com.jhnu.syspermiss;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.jhnu.syspermiss.catche.PermissCache;
import com.jhnu.syspermiss.catche.PermissCatchEntity;
import com.jhnu.syspermiss.permiss.entity.DataServe;
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
import com.jhnu.syspermiss.school.service.DeptPermissionService;
import com.jhnu.syspermiss.school.service.impl.DeptPermissionServiceImpl;
import com.jhnu.syspermiss.util.ContextHolderUtils;

public class GetCachePermiss {
	private static UserService userService= UserServiceImpl.getInstance();
	private static RoleService roleService= RoleServiceImpl.getInstance();
	private static ResourcesService resourcesService= ResourcesServiceImpl.getInstance();
	private static PermssionService permssionService= PermssionServiceImpl.getInstance();
	private static OperateService operateService= OperateServiceImpl.getInstance();
	private static DataServeService dataServeService= DataServeServiceImpl.getInstance();
	private static DeptPermissionService deptPermissionService= DeptPermissionServiceImpl.getInstance();
    /**
     * 通过用户名获取在该系统路径下的所拥有的菜单
     * @param username
     * @param sys 系统路径
     * @return
     */
    public static List<Resources> getSysMenusByUserName(String username){
    	return getPermissCatchEntity(username).getSysMenus();
    }
    
    /**
     * 通过用户名在该权限标识下的获取所拥有的菜单
     * @param username 用户名
     * @param shiroTag 权限标识 格式为： ht:cs:*
     * @return
     */
    public static List<Resources> getMenusByUserNameShiroTag(String username,String shiroTag){
    	shiroTag=shiroTag.substring(0,shiroTag.length()-1);
    	List<Resources> l=getMenusByUserName(username);
    	List<Resources> list=new ArrayList<Resources>();
    	//TODO 目前只对资源有验证。没有对该资源，在这个动作下的菜单权限进行验证。即没有加:*,:add这个等这种验证
    	String shiroTagS=shiroTag.substring(0, shiroTag.lastIndexOf(":")+1).toLowerCase();
   // 	String shiroTagE=shiroTag.substring(shiroTag.lastIndexOf(":"),shiroTag.length());
    	for(Resources r:l){
    		if((r.getShiro_tag().toLowerCase()+":").indexOf(shiroTagS)==0){
    			list.add(r);
    		}
    	}
    	return list;
    }
	
	public static Operate createOperate(Operate operate) {
		return operateService.createOperate(operate);
	}

	public static void deleteOperate(Long operateId) {
		operateService.deleteOperate(operateId);
	}

	public static void updateOperate(Operate operate) {
		operateService.updateOperate(operate);
	}

	public static UserPermssion addUserPermssion(UserPermssion userPermssion) {
			return permssionService.addUserPermssion(userPermssion);
	}

	public static RolePermssion addRolePermssion(RolePermssion rolePermssion) {
			return permssionService.addRolePermssion(rolePermssion);
	}


	public static void updateRolePermssion(Operate operate) {
		permssionService.updateRolePermssion(operate);
	}

	public static void updateUserPermssion(Operate operate) {
		permssionService.updateUserPermssion(operate);
	}

	public static void updateRolePermssion(Resources resource) {
		permssionService.updateRolePermssion(resource);
	}

	public static void updateUserPermssion(Resources resource) {
		permssionService.updateUserPermssion(resource);
	}

	public static void deleteRolePermssionByRoleId(Long roleId) {
		permssionService.deleteRolePermssionByRoleId(roleId);
	}
	    public static Role createRole(final Role Role) {
	        return roleService.createRole(Role);
	    }
	    public static void deleteRole(Long roleId) {
	    	roleService.deleteRole(roleId);
	    }
		public static void updateRole(Role role) {
			roleService.updateRole(role);
		}
	public static User createUser(User user) {
		return userService.createUser(user);
	}

	public static void updateUser(User user) {
		userService.updateUser(user);
	}

	public static void deleteUserById(Long userId) {
		userService.deleteUserById(userId);
	}
	/**
     * 验证密码
     * @param userId
     * @param password
     */
    public static boolean checkPassword(Long userId, String password){
    	return userService.checkPassword(userId, password);
    };

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public static void changePassword(Long userId, String newPassword){
    	 userService.changePassword(userId, newPassword);
    };
	  /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public static User findByUsername(String username){
    	return getPermissCatchEntity(username).getUser();
    }

   /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */ 
    public static Set<String> findRoles(String username){
    	return getPermissCatchEntity(username).getRoles();
    }
    /**
     * 根据用户名查找其角色对象集合
     * @param username
     * @return
     */ 
    public static List<Role> findRolesList(String username){
    	return getPermissCatchEntity(username).getRoleList();
    }
    /**
     * 通过用户ID查找角色名集合
     * @param userId 用户ID
     * @return
     */
    public static Set<String> getRoleNamesByUserId(long userId){
    	return PermissCache.getCatcheEntityById(userId+"").getRoles();
    	   
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public static Set<String> findPermissions(String username){
    	return getPermissCatchEntity(username).getPermissions();
    }
    /**
     * 获取该用户的根本角色 即返回student，teacher，admin
     * @param username 用户名
     * @return
     */
    public static String getUserRootRole(String username){
    	return getPermissCatchEntity(username).getRootRole();
    }
    /**
     * 查找角色
     * @param roleId 角色ID
     * @return
     */
    public static Role findByRoleId(Long roleId){
    	return roleService.findByRoleId(roleId);
    }
    /**
     * 通过角色获取主页信息
     * @param role
     * @return
     */
    public static Resources getMainPageByRole(Role role){
    	return resourcesService.getMainPageByRole(role);
    }
    
    /**
     * 通过角色获取主页信息
     * @param user
     * @return
     */
    public static Resources getMainPageByUser(User user){
    	return PermissCache.getCatcheEntityById(user.getId().toString()).getMainPage();
    }
    
    /**
     * 通过用户名获取所拥有的菜单
     * @param username
     * @return
     */
    public static List<Resources> getMenusByUserName(String username){
    	return getPermissCatchEntity(username).getAllMenus();
    }    
    
    /**
     * 该用户名是否拥有该资源权限。
     * @param username
     * @param shiroTag
     * @return
     */
    public static boolean hasPermssion(String username,String shiroTag){
    	if("admin".equalsIgnoreCase(getUserRootRole(username))){
    		List<Resources> res=getMenusByUserName(username);
    		String star=shiroTag.substring(0,shiroTag.lastIndexOf(":"));
    		for (int i = 0; i < res.size(); i++) {
    			if(res.get(i).getShiro_tag().equals(star)&&res.get(i).getIstrue()==1){
    				return true;
    			}
			}
    		return false;
    	}
    	Set<String> set =findPermissions(username);
    	Iterator<String> it = set.iterator();  
    	while (it.hasNext()) {  
    	  String str = it.next();  
    	  if(str.endsWith("*")){
    		  str=str.substring(0,str.lastIndexOf(":"));
    		  String shiroTagE=shiroTag.substring(0,shiroTag.lastIndexOf(":"));
    		  if(shiroTagE.equalsIgnoreCase(str)){
    			  return true;
    		  }
    	  }else if(str.equalsIgnoreCase(shiroTag)){
    		  return true;
    	  }
    	} 
    	    return false;
    }
    /**
     * 该用户名是否拥有资源该数据权限。
     * @param username
     * @param shiroTag
     * @return
     */
    public static boolean hasPermssion(String username,String shiroTag,String dataId){
    	if("admin".equalsIgnoreCase(getUserRootRole(username))){
    		return true;
    	}
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
     * 获得用户权限关系
     * @param userPermssion
     * @return
     */
    public static List<UserPermssion> getUserPermssion(UserPermssion userPermssion){
    	return permssionService.getUserPermssion(userPermssion);
    }
    
    /**
     * 获得角色权限关系
     * @param userPermssion
     * @return
     */
    public static List<RolePermssion> getRolePermssion(RolePermssion rolePermssion){
    	return permssionService.getRolePermssion(rolePermssion);
    }
    /**
	 * 通过数据范围LIST获取数据范围对应的SQL语句
	 * 返回数据范围所对应类执行之后的SQL
	 * SQL的输入字段为：id，此值只能为班级ID或单位ID。即精确到组织机构中的最底层
	 * @param username 用户登陆名称
	 * @param shiroTag 权限通配符
	 * @return
	 */
	public static List<String> getDataServeSqlbyUserIdShrio(String username, String shiroTag){
		if("admin".equalsIgnoreCase(getUserRootRole(username))){
			List<String> list=new ArrayList<String>();
			list.add("'0'");list.add("''");list.add("''");list.add("''");list.add("''");
    		return list;
    	}
		return dataServeService.getDataServeSqlbyUserIdShrio(username, shiroTag);
	}
	
	/**
	 * 通过组织机构ID或教学组织机构ID和已有的全数据权限，获取该组织机构下的数据权限(标准格式)。
	 * @param type 值：dept,deptTeach
	 * @param deptId 
	 * @param datas 为已有的全数据权限List
	 * @return
	 */
	public static List<String> getDataByDeptAndData(String type,String deptId,List<String> datas){
		return dataServeService.getDataByDeptAndData(type, deptId, datas);
	}
	
	/**
	 * 通过组织机构ID或教学组织机构ID和已有的全数据权限，获取该组织机构下的数据权限SQL。
	 * @param type 值：dept,deptTeach
	 * @param deptId 
	 * @param datas 为已有的全数据权限List
	 * @return
	 */
	public static String getDataSqlByDeptAndData(String type,String deptId,List<String> datas){
		return dataServeService.getDataSqlByDeptAndData(type, deptId, datas);
	}
	
	/**
	 * 通过用户名和资源 获取教学组织机构 (系统自动获取相关参数)
	 * @param
	 * @return
	 */
	public static Object getDeptTeachByShiroTag(String shiroTag){
		return deptPermissionService.getDeptTeachByShiroTag(shiroTag);
	}
	/**
	 * 通过用户名和资源 获取教学组织机构 (系统自动获取相关参数)
	 * @param
	 * @return
	 */
	public static Object getDeptByShiroTag(String shiroTag){
		return deptPermissionService.getDeptByShiroTag(shiroTag);
	}
	/**
	 * 在已有的数据服务范围中，是否有该人的权限
	 * @param thisOneLoginName 被验证人员登陆账号
	 * @param username 验证这登陆账号
	 * @param shiroTag 权限代码
	 * @return
	 */
	public static boolean hasThisOnePerm(String thisOneLoginName,String username, String shiroTag){
		if("admin".equalsIgnoreCase(getUserRootRole(username))){
    		return true;
    	}
    	return dataServeService.hasThisOnePerm(thisOneLoginName,getPermissCatchEntity(username).getDataServeMap().get(shiroTag));
    }
	
	/**
	 * 登陆完成初始化角色，权限
	 * @param username 验证这登陆账号
	 * @return
	 */
	public static void init(String username,String sys){
		initEvery(username);
		GetCachePermiss.setSysRes(username, GetPermiss.getMenusByUserName(username, sys));	
	}
	public static void init(String username){
		if(PermissCache.getCatcheEntityByName(username)!=null)return;
		initEvery(username);
	}
	
	private static PermissCatchEntity getPermissCatchEntity(String username){
		if(PermissCache.getCatcheEntityByName(username)!=null){
			return PermissCache.getCatcheEntityByName(username);
		}else{
			HttpServletRequest httprequest=ContextHolderUtils.getRequest();
			String basePath = httprequest.getScheme()+"://"+httprequest.getServerName()+":"+
			httprequest.getServerPort()+httprequest.getContextPath();
			init(username,basePath);
			return getPermissCatchEntity(username);
		}
	}
	
	public static void initEvery(String username){
		User user=GetPermiss.findByUsername(username);
		Set<String> roleSet=GetPermiss.findRoles(username);
		String rootRole=GetPermiss.getUserRootRole(username);
		Resources mainPage=GetPermiss.getMainPageByUser(user);
		List<Resources> Menus=GetPermiss.getMenusByUserName(username);
		Set<String> permissionsSet=GetPermiss.findPermissions(username);
		
		Iterator<String> it = permissionsSet.iterator();
		Map<String,List<DataServe>> dataServeMap =new LinkedHashMap<>();
		for(;it.hasNext();){
			String shiroTag= (String) it.next();
			List<DataServe> list =dataServeService.getDataServe(username,shiroTag);
			dataServeMap.put(shiroTag, list);
			break;
		}
		List<Role> roleList=GetPermiss.findRolesList(username);
		PermissCache.setAll(new PermissCatchEntity(user, roleSet, rootRole, mainPage, Menus, permissionsSet,roleList, dataServeMap));
    } 
	public static void setSysRes(String userName,List<Resources> Menus){
		PermissCache.setSysRes(userName, Menus);
	}
}
