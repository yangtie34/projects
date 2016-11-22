/**
 * @author:dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22  下午09:23:18
 *
 */
package com.jhkj.mosdc.permission.service;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.util.Page;
import com.jhkj.mosdc.permission.po.TsCdzy;

/**
 * @Comments: 菜单服务接口
 * Company: topMan 
 * Created by dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22
 * @TIME: 下午09:23:18
 */
public interface MenuService {


	/**
	 * 新增菜单
	 * @param unit
	 */
	public TsCdzy addMenu(TsCdzy menu);
	/**
	 * 删除菜单
	 * @param Menu
	 * @return
	 */
	public String deleteMenu(String params);
	/**
	 * 得到所有菜单列表
	 * @return
	 * @throws Exception 
	 */
	public List<TsCdzy> getMenuAllList() throws Exception;
	/**
	 * 编辑菜单
	 */
	public  TsCdzy editMenu(TsCdzy menu);
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
	public List<TsCdzy> getMenuAllListByPage(int start, int limit, String sort,String dir,Integer parentId);
	/**
	 * 得到一个菜单信息
	 * Created by dangdong(mrdangdong@163.com) 
	 * @param Menu
	 * @return
	 */
	public TsCdzy getMenu(Long id);
	/**
	 * 根据父节点查询所有子节点个数
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public Integer sumChildById(Long id);
	/**
	 * 根据菜单ID查询所有关联的角色个数
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer sumRoleByMenuId(Long id);
	/**
	 * 得子菜单列表
	 * Created by dangdong(mrdangdong@163.com) 
	 * @return
	 */
	public String getMenuTreeByParentId(String parentId);
	/**
	 * 根据父菜单标识　返回分页记录总条数
	 * Created by dangdong(mrdangdong@163.com) 
	 * @return
	 */
	public int findRecordCountByParentId(Long id);
	/**
	 * 得到分页排序列表
	 * Created by dangdong(mrdangdong@163.com) 
	 * @return
	 */
	public Page findMenuHqlPageById(Long id,PageParam pageParam);
	/**
	 * 根据SQL语句获取权限菜单列表
	 * @return
	 */
	public List<TsCdzy> getMenuRoleListBySql(String sql);
	/**
	 * 保存、修改角色权限
	 * Created by dangdong(mrdangdong@163.com) 
	 * @param roleid
	 * @param menuIds
	 * @return
	 */
	public String saveRolePermission(String params);
	/**
	 * 根据角色ID得到菜单ID列表
	 * Created by dangdong(mrdangdong@163.com) 
	 * @param roleId
	 * @return
	 */
	public List<Map<String,Object>>  getMenuIdsByRoleId(Long roleId,Long menuId);
	/**
	 * 根据角色ID得到按钮ID列表
	 * @param roleId
	 * @return
	 */
	public List<Map<String, Object>> getButtonIdsByRoleId(Long roleId);
	/**
	 * 
	 * 功能说明：根据用户标识得到菜单资源列表不包括按钮
	 * @param userId
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-17 @TIME: 下午8:04:11
	 */
	public List<TsCdzy> getMenuListByUserId(Long userId) throws Exception;
	/**
	 * 
	 * 功能说明：根据用户标识和菜单标识得到按钮的标识
	 * @param userId
	 * @param menuId
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-17 @TIME: 下午8:05:07
	 */
	public  List<TsCdzy> getMenuListByUserIdAndMenuId(Long userId,Long menuId) throws Exception;
	/**
	 * 根据用户把所有的按钮记录取出
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<TsCdzy> getMenuListByUserIdAndMenuId(Long userId) throws Exception;
	/**
	 * 
	 * 功能说明：根据用户标识返回所有菜单集合：包括分组，模块，菜单，按钮
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-18 @TIME: 上午9:00:20
	 */
	public List<TsCdzy> getAllMenuListByUserId(Long userId) throws Exception;
	/**
	 * 
	 * 功能说明：根据用户标识返回所有按钮集合：仅包含按钮
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-18 @TIME: 上午9:00:24
	 */
	public List<TsCdzy> getAllButtonMenuListByUserId(Long userId) throws Exception;
	/**
	 * 获取用户菜单权限
	 * @param userId
	 * @param groupPermiss
	 * @return
	 * @throws Exception
	 */
	public List<TsCdzy> getAllMenuListByUserId(Long userId, Boolean groupPermiss)
			throws Exception;
	/**
	 * 根据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String queryEntityDataPermiss(String params) throws Exception;
	
	//-------------------------------------------2013-05-18---------------------------------------------
	/**
	 * 获取菜单资源的所有节点
	 * @param params　
	 * @return　菜单树
	 * @throws Exception 
	 */
	public String getAllMenuTree(String params) throws Exception;
	/**
	 * 保存菜单资源对象,同时设置是否叶子节点
	 * @param params
	 * @return
	 */
	public String saveMenu(String params);
	/**
	 * 根据菜单父节点获取菜单资源的下一级所有节点
	 * @param params　
	 * @return　菜单树
	 * @throws Exception 
	 */
	public String getMenuTreeByPid(String params);


}
