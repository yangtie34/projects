/**
 * @author:dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22  下午08:48:35
 *
 */
package com.jhkj.mosdc.permission.dao;

import java.util.List;

import com.jhkj.mosdc.permission.po.TsCdzy;
import com.jhkj.mosdc.permission.po.TsJs;
import com.jhkj.mosdc.permission.po.TsJsCdzy;
import com.jhkj.mosdc.permission.po.TsUserCdzy;

/**
 * @Comments: 角色Dao接口
 * Company: topMan
 * Created by dangdong(mrdangdong@163.com) 
 * @DATE:2012-5-22
 * @TIME: 下午08:48:35
 */
public interface RoleDao {
	
	/**
	 * 获取当前用户的角色信息
	 * @param userId
	 * @return
	 */
	public List<TsJs> queryJsxx(Long userId);

	/**
	 * 新增角色
	 * @param unit
	 */
	public TsJs saveRole(TsJs role);
	/**
	 * 删除角色
	 * @param Role
	 * @return
	 */
	public void deleteRole(TsJs role);
	/**
	 * 批量删除角色
	 * @param tsJsList
	 * @return
	 */
	public void deleteRoles(List<TsJs> tsJsList);
	/**
	 * 得到所有角色列表
	 * @return
	 */
	public List<TsJs> getRoleAllList();
	/**
	 * 编辑角色
	 */
	public  void updateRole(TsJs role);
	/**
	 * 返回分页记录总条数
	 * Created by dangdong(mrdangdong@163.com) 
	 * @return
	 */
	public int findRecordCount();
	/**
	 * 得到分页排序列表
	 * Created by dangdong(mrdangdong@163.com) 
	 * @return
	 */
	public List<TsJs> getRoleAllListByPage(int start, int limit, String sort,String dir);
	/**
	 * 得到一个角色信息
	 * Created by dangdong(mrdangdong@163.com) 
	 * @param Role
	 * @return
	 */
	public TsJs getRole(Long id);
	/**
	 * 获取要删除的角色
	 * @param ids
	 * @return
	 */
	public List<TsJs> getRoles(String ids);
	/**
	 * 根据角色编号删除其下的所有菜单权限
	 * @param roleId
	 * @return
	 */
	public int deleteRoleMenusByRoleId(Long roleId);
	/**
	 * 保存角色相对应的菜单资源信息
	 * @param roleid
	 * @param perms
	 */
	public void saveRoleMenus(Long roleId, List<String> perms);
	/**
	 * 查看名称是否重复
	 * Created by dangdong(mrdangdong@163.com) 
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isNameExist(Long id,String name);
	/**
	 * 根据角色ID查找其它表对它的引用
	 * Created by dangdong(mrdangdong@163.com) 
	 * @param id
	 * @return
	 */
	public int getRoleReferenceRecordNum(Long id);
	
	/**
	 * 保存用户权限分配的菜单
	 * @param tsJsCdzy
	 * @throws Exception
	 */
	public void saveUserMenuPermiss(TsUserCdzy tsUserCdzy) throws Exception;
	/**
	 * 删除已经存在的菜单权限
	 * @param tsJsCdzyList
	 */
	public void deleteUserCdzys(List<TsUserCdzy> tsUserCdzyList);
	/**
	 * 获取当前用户的菜单权限
	 * @param cdzyIds
	 * @param userId
	 * @return
	 */
	public List<TsUserCdzy> queryUserCdzy(String cdzyIds,Long userId);
	/**
	 * 查询是否有重名的角色
	 * @param roleMc
	 * @return
	 */
	public TsJs getRole(Long userId,Long jslxId);

	List<TsJsCdzy> queryTsJsCdzys(String ids);
	/**
	 * 获取当前角色的所拥有的tab页
	 * @param rolesId
	 * @return
	 */
	public List queryJsTabs(String rolesId,Long rylb);
	/**
	 * 将角色类型ＩＤ改为角色ＩＤ
	 * @param roleTypeIds
	 * @return
	 */
	public String getRoleIds(String roleTypeIds);
	/**
	 * 根据角色ID，查询其对应的菜单资源
	 */
	public List<TsCdzy> queryTsCdzysByRoleId(Long roleId);

}
