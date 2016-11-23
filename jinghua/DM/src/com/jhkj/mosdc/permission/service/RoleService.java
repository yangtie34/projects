/**
 * @author:dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22  下午09:15:52
 *
 */
package com.jhkj.mosdc.permission.service;

import java.util.List;

import com.jhkj.mosdc.permission.po.TsJs;

/**
 * @Comments: 角色服务接口
 * Company: topMan
 * Created by dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22
 * @TIME: 下午09:15:52
 */
public interface RoleService {
	
	/**
	 * 获取用户的角色信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String queryUserJs(String params) throws Exception;

	/**
	 * 新增角色
	 * 
	 * @param unit
	 * @throws Exception 
	 */
	public String saveRole(String params) throws Exception;

	/**
	 * 删除角色
	 * 
	 * @param Role
	 * @return
	 */
	public String deleteRole(String params);

	/**
	 * 得到所有角色列表
	 * 
	 * @return
	 */
	public List<TsJs> getRoleAllList();

	/**
	 * 编辑角色
	 * @throws Exception 
	 */
	public String updateRole(String params) throws Exception;

	/**
	 * 返回分页记录总条数
	 *  Created by dangdong(mrdangdong@163.com)
	 * 
	 * @return
	 */
	public int findRecordCount();

	/**
	 * 得到分页排序列表 
	 * Created by dangdong(mrdangdong@163.com)
	 * 
	 * @return
	 */
	public List<TsJs> getRoleAllListByPage(int start, int limit, String sort,
			String dir);

	/**
	 * 得到一个角色信息
	 *  Created by dangdong(mrdangdong@163.com)
	 * 
	 * @param Role
	 * @return
	 */
	public TsJs getRole(Long id);

	/**
	 * 根据角色编号删除其下的所有菜单权限
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteRoleMenusByRoleId(Long roleId);

	/**
	 * 保存角色相对应的菜单资源信息
	 * 
	 * @param roleid
	 * @param perms
	 */
	public void saveRoleMenus(Long roleId, List<String> perms);

	/**
	 * 查看名称是否重复
	 *  Created by dangdong(mrdangdong@163.com)
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isNameExist(Long id, String name);

	/**
	 * 根据角色ID查找其它表对它的引用
	 *  Created by dangdong(mrdangdong@163.com)
	 * @param id
	 * @return
	 */
	public int getRoleReferenceRecordNum(Long id);
	/**
	 * 
	 * 功能说明：根据角色标识，上级菜单标识去取菜单资源
	 * @param roleId
	 * @param parentId
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @throws Exception 
	 * @DATE:2012-6-10 @TIME: 上午7:33:43
	 */
	public String getPermissionJson(String params) throws Exception;
	/**
	 * 功能说明：根据角色标识，获取角色对应的菜单信息
	 */
	public String getPermissionJsonByRoleId(String params) throws Exception;
	/**
	 * 获取用户角色tab权限
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String queryJsTabs(String params) throws Exception;
}
