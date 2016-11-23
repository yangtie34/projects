package com.jhkj.mosdc.permiss.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.domain.Node;
import com.jhkj.mosdc.permiss.po.TpMenu;
import com.jhkj.mosdc.permiss.po.TpUsergroupMenu;
import com.jhkj.mosdc.permiss.service.MenuService;
import com.jhkj.mosdc.permiss.util.JSONUtil;
import com.jhkj.mosdc.permiss.util.SqlUtils;
import com.opensymphony.xwork2.ActionContext;

public class MenuServiceImpl implements MenuService {
	private BaseDao baseDao;
	private BaseService baseService;

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public String addMenu(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		TpMenu menu = (TpMenu) JSONUtil.stringToBean(params, TpMenu.class);
		TpMenu pmenu = (TpMenu) baseDao.get(TpMenu.class, menu.getParentId());// 获取父节点对象
		pmenu = pmenu != null ? pmenu : new TpMenu();
		// System.out.println(this.queryNextQxm(menu.getCdssfl(),pmenu.getId(),pmenu.getQxm())+"=----------全息码");
		menu.setId(baseDao.getId());
		menu.setDeep(getDeep(pmenu.getDeep()));// 设置树的深度
		menu.setQxm(this.queryNextQxm(menu.getDeep(),
				pmenu.getId() == null ? 0l : pmenu.getId(), pmenu.getQxm()));
		baseDao.save(menu);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String deleteMenu(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long menuId = JSONUtil.getLong(json, "menuId");

		TpMenu menu = (TpMenu) baseDao.get(TpMenu.class, menuId);
		
		String qxmMenus = "select id from tp_menu where qxm like '" + menu.getQxm() + "%'";
		
		//删除角色菜单关联表中的菜单sql
		String deleteRoleMenu = "delete from TP_ROLE_MENU t where t.menu_id in ("+qxmMenus+")";
		//删除用户组菜单资源关联表中的菜单
		String deleteGroupMenu = "delete from TP_USERGROUP_MENU t where t.menu_id in ("+qxmMenus+")";
		//删除用户组-用户菜单资源关联表中的菜单
		String deleteGroupUserMenu = "delete from TP_USERGROUP_MENU_USER t where t.menu_id in ("+qxmMenus+")";
		//删除权限委托-菜单资源关联表中的菜单
		String deleteProxyUserMenu = "delete from TP_USER_PROXY_MENU t where t.menu_id in ("+qxmMenus+")";
		//删除权限委托-菜单-数据权限排除表
		String deleteProxyUserExceptDataMenu = "delete from TP_USER_PROXY_DATA_EXCEPT t where t.menu_id in ("+qxmMenus+")";
		//删除用户组-菜单-数据权限排除表
		String deleteUserGroupExceptDataMenu = "delete from TP_USERGROUP_USERDATA_EXCEPT t where t.menu_id in ("+qxmMenus+")";
		
		baseDao.updateSqlExec(deleteRoleMenu);
		baseDao.updateSqlExec(deleteGroupMenu);
		baseDao.updateSqlExec(deleteGroupUserMenu);
		baseDao.updateSqlExec(deleteProxyUserMenu);
		baseDao.updateSqlExec(deleteProxyUserExceptDataMenu);
		baseDao.updateSqlExec(deleteUserGroupExceptDataMenu);
		
		//删除菜单树中的菜单
		String deleteSql = "delete from tp_menu t where t.qxm like '"
				+ menu.getQxm() + "%'";
		baseDao.updateSqlExec(deleteSql);

		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String updateMenu(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		TpMenu menu = (TpMenu) JSONUtil.stringToBean(params, TpMenu.class);
		baseDao.update(menu);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String getMenuById(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long menuId = JSONUtil.getLong(json, "menuId");
		TpMenu menu = new TpMenu();
		menu.setId(menuId);
		menu = (TpMenu) baseDao.loadFirstEqual(menu);
		return Struts2Utils.bean2json(menu);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryAllMenus(String params) {
		// TODO Auto-generated method stub
		List<TpMenu> list = baseDao.loadAll(new TpMenu());
		// 创建根节点
		Node root = new Node();
		root.setId(0l);
		root.setText("菜单功能");
		// 创建节点Hash表，并把根节点放入Hash表中
		Map<Long, Node> nodeHash = new HashMap<Long, Node>();
		nodeHash.put(0l, root);
		if (params.contains("all")) {
			for (TpMenu tm : list) {
				Node node = new Node();
				node.setId(tm.getId());
				node.setPxh(tm.getSortnum());// 排序号
				node.setPid(tm.getParentId());// 父节点ID
				node.setFunDesc(tm.getFundesc());
				node.setFunTag(tm.getFuntype());
				node.setText(tm.getMc());
				if (tm.getCdssfl() == 2) {
					node.setLeaf(true);
				} else {
					node.setLeaf(false);
				}
				nodeHash.put(node.getId(), node);
			}
		} else {
			for (TpMenu tm : list) {
				if (tm.getCdssfl() <= 2) {// 菜单所属分类(1 非叶子节点菜单 2 叶子节点菜单 3 功能 4
											// 内容)
					Node node = new Node();
					node.setId(tm.getId());
					node.setPxh(tm.getSortnum());// 排序号
					node.setPid(tm.getParentId());// 父节点ID
					node.setText(tm.getMc());
					node.setFunDesc(tm.getFundesc());
					node.setFunTag(tm.getFuntype());
					if (tm.getCdssfl() == 2) {
						node.setLeaf(true);
					} else {
						node.setLeaf(false);
					}
					nodeHash.put(node.getId(), node);
				}
			}
		}
		Node.generateNodeTree(nodeHash);
		Node.sort(nodeHash);// 对节点进行排序
		// 返回菜单树
		return Struts2Utils.bean2json(root);
	}

	@Override
	public String queryChildrenByMenuId(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		// Long menuId = JSONUtil.getLong(json, "menuId");
		String[] columnArray = "id, mc, cdssfl, sortnum, parent_id, url, leaf, funtype, fundesc,qxm,deep"
				.split(",");
		String[] aliasArray = "id, mc, cdssfl, sortnum, parentId, url, leaf, funtype, fundesc,qxm,deep"
				.split(",");

		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray,
				"(select * from tp_menu order by sortnum)");
		String result = baseService.queryTableDataBySqlWithAlias(sql, params);
		return result;
	}

	@Override
	public String queryUsergroupMenus(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");

		String sql = "select tm.* from tp_usergroup_menu tum "
				+ " inner join tp_menu tm on tum.menu_id=tm.id and tum.usergroup_id = "+usergroupId;
		List<TpMenu> list = baseDao.querySqlEntityList(sql, TpMenu.class);

		// 创建根节点
		Node root = new Node();
		root.setId(0l);
		root.setText("菜单功能");
		// 创建节点Hash表，并把根节点放入Hash表中
		Map<Long, Node> nodeHash = new HashMap<Long, Node>();
		nodeHash.put(0l, root);
		if (params.contains("all")) {
			for (TpMenu tm : list) {
				Node node = new Node();
				node.setId(tm.getId());
				node.setPxh(tm.getSortnum());// 排序号
				node.setPid(tm.getParentId());// 父节点ID
				node.setFunDesc(tm.getFundesc());
				node.setFunTag(tm.getFuntype());
				node.setText(tm.getMc());
				if (tm.getCdssfl() == 2) {
					node.setLeaf(true);
				} else {
					node.setLeaf(false);
				}
				nodeHash.put(node.getId(), node);
			}
		} else {
			for (TpMenu tm : list) {
				if (tm.getCdssfl() <= 2) {// 菜单所属分类(1 非叶子节点菜单 2 叶子节点菜单 3 功能 4
											// 内容)
					Node node = new Node();
					node.setId(tm.getId());
					node.setPxh(tm.getSortnum());// 排序号
					node.setPid(tm.getParentId());// 父节点ID
					node.setText(tm.getMc());
					node.setFunDesc(tm.getFundesc());
					node.setFunTag(tm.getFuntype());
					if (tm.getCdssfl() == 2) {
						node.setLeaf(true);
					} else {
						node.setLeaf(false);
					}
					nodeHash.put(node.getId(), node);
				}
			}
		}

		Node.sort(nodeHash);// 对节点进行排序
		Node.generateNodeTree(nodeHash);//生成树
		// 返回菜单树
		return Struts2Utils.bean2json(root);
	}
	@Override
	public String queryUsergroupMenuPermiss(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		
		TpUsergroupMenu tm = new TpUsergroupMenu();
		tm.setUsergroupId(usergroupId);
		List menuList = baseDao.loadEqual(tm);
		return Struts2Utils.list2json(menuList);
	}
	@Override
	public String updateUsergroupMenuPermiss(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		List menuList = Arrays.asList(JSONUtil.getString(json, "menuIds").split(","));
		
		//移除之前的用户组菜单权限
		TpUsergroupMenu tmExample = new TpUsergroupMenu();
		tmExample.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tmExample);
		
		//添加新的用户组菜单权限
		for(Object menuId : menuList){
			TpUsergroupMenu tm  = new TpUsergroupMenu();
			tm.setUsergroupId(usergroupId);
			tm.setMenuId(Long.valueOf(menuId.toString()));
			tm.setId(baseDao.getId());
			baseDao.save(tm);
		}
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String deleteUserOldMenuPermissInUserGroup(String params)
			throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = getUserGroupCondition(JSONUtil.getString(json, "usergroupId"));
		
		//删除当前用户组组员中的，被赋予的已经被删除的权限
		String sql = "delete from TP_USERGROUP_MENU_USER tumu where tumu.usergroup_id {0} "
				+ " and  (tumu.menu_id,tumu.usergroup_id) not in "
				+ " (select tum.menu_id,tumu.usergroup_id from tp_usergroup_menu tum where tum.usergroup_id {1})";
		
		sql = StringUtils.format(sql,usergroupId,usergroupId);
		baseDao.updateSqlExec(sql);
		
		//删除具体的菜单的数据权限
		String deleteMenuDataSetSql = "delete from TP_USERGROUP_DATA_EXCEPT tude where tude.usergroup_id  {0} and"
				+ " tude.menu_id not in  (select tum.menu_id from Tp_Usergroup_Menu tum where tum.usergroup_id  {1}) ";
		deleteMenuDataSetSql = StringUtils.format(deleteMenuDataSetSql,usergroupId,usergroupId);
		baseDao.updateSqlExec(deleteMenuDataSetSql);
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 获取节点深度
	 * 
	 * @param pdeep
	 * @return
	 */
	private int getDeep(Integer pdeep) {
		int deep = 1;
		if (pdeep != null)
			deep = pdeep + 1;
		return deep;
	}

	/**
	 * 查询全息码
	 * 
	 * @param deep
	 * @param pid
	 * @param parentQxm
	 * @return
	 */
	private String queryNextQxm(Integer deep, Long pid, String parentQxm) {
		String sql = "select max(qxm) qxm from tp_menu t where t.parent_id = '"
				+ pid + "' and deep= '" + deep + "'";
		if(parentQxm == null)parentQxm = "";
		List<Map> list = baseDao.queryListMapBySQL(sql);
		Map map = list.get(0);
		String maxQxm =  (map.get("QXM") == null ? null:map.get("QXM").toString()) ;
		String ret = "";
		if (maxQxm == null || maxQxm.equals("0")) {
			return parentQxm + "00001";
		}
		String baseQxm = maxQxm.substring(0,maxQxm.length()-5);
		String nowNum = maxQxm.substring(maxQxm.length()-5,maxQxm.length());
		int base = Integer.parseInt(nowNum);
		ret = String.valueOf(base + 1);
		int len = ret.length();
		ret = StringUtils.repeat("0", 5 - (len % 5)) + ret;
		return baseQxm+ret;
	}
	/**
	 * 获取用户组Id拼接条件
	 * @param usergroupId
	 * @return
	 */
	private String getUserGroupCondition(String usergroupId){
		if(usergroupId == null)usergroupId = " is null "; else usergroupId = "="+usergroupId;
		return usergroupId;
	}

	public static void main(String args[]) {
		String ret = "1200001";
		int len = ret.length();
		ret = StringUtils.repeat("0", 5 - (len % 5)) + ret;
		System.out.println(ret);
	}
}
