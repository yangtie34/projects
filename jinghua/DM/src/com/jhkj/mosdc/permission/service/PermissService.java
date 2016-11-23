package com.jhkj.mosdc.permission.service;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.permission.po.MenuNode;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.po.TreeNode;

/**
 * @Comments: 权限数据查询接口
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-7-9
 * @TIME: 上午11:20:31
 */
public interface PermissService {
	/**
	 * 获取有权限的菜单按钮
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public MenuTree queryPermissCdzy(String param) throws Exception;
	/**
	 * 校验是否存在权限
	 * @param treeNode
	 * @param id
	 * @return
	 */
	public MenuNode checkChildNode(MenuNode treeNode, Long id);
	/**
	 * 获取用户的数据权限
	 * @param userId
	 * @return
	 */
	public Map<String, MenuTree> queryDataPermiss(String userId);


}
