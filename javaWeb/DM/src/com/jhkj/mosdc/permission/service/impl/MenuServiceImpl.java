/**
 * @author:dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22  下午09:25:23
 *
 */
package com.jhkj.mosdc.permission.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.bean.CacheManager;
import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.dto.Cache;
import com.jhkj.mosdc.framework.util.EhcacheUtil;
import com.jhkj.mosdc.framework.util.JsonsUtils;
import com.jhkj.mosdc.framework.util.Page;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.output.po.Tree;
import com.jhkj.mosdc.permission.dao.MenuDao;
import com.jhkj.mosdc.permission.po.MenuNode;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.po.TsCdzy;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.service.MenuService;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

/**
 * @Comments: 菜单服务接口
 * Company: topMan 
 * Created by dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22
 * @TIME: 下午09:25:23
 */
public class MenuServiceImpl implements MenuService {
	private MenuDao menuDao;

	/**
	 * @return the menuDao
	 */
	public MenuDao getMenuDao() {
		return menuDao;
	}

	/**
	 * @param menuDao
	 *            the menuDao to set
	 */
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#addMenu(com.jhkj.mosdc.
	 * permission.po.TsCdzy)
	 */
	@Override
	public TsCdzy addMenu(TsCdzy menu) {
		return menuDao.addMenu(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#deleteMenu(com.jhkj.mosdc
	 * .permission.po.TsCdzy)
	 */
	@Override
	public String deleteMenu(String params) {
		Map<String,Object> mp = new HashMap<String, Object>(); 
		JSONObject json = JSONObject.fromObject(params);
		String[] menuIdArray = json.getString("ids").toString().split(",");
		for(int i = 0;i<menuIdArray.length;i++){
			if (this.menuDao.sumChildById(Long.valueOf(menuIdArray[i])) > 0) {
				mp.put("success",false);
				mp.put("info", "该菜单有下级菜单，不能删除。");
				break;
			}else{
				TsCdzy menu=this.getMenu(Long.valueOf(menuIdArray[i]));
				if(menu==null){
					mp.put("success",false);
					mp.put("info", "该菜单有下级菜单，不能删除。");
					break;
				}else{				
					menuDao.deleteMenu(menu);
					mp.put("success",true);
					mp.put("info", "删除成功！");
				}
			}
		}
		return Struts2Utils.map2json(mp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#editMenu(com.jhkj.mosdc
	 * .permission.po.TsCdzy)
	 */
	@Override
	public TsCdzy editMenu(TsCdzy menu) {
		return menuDao.editMenu(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#findMenuHqlPageById(java
	 * .lang.Integer, com.jhkj.mosdc.framework.bean.PageParam)
	 */
	@Override
	public Page findMenuHqlPageById(Long id, PageParam pageParam) {
		return menuDao.findMenuHqlPageById(id, pageParam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jhkj.mosdc.permission.service.MenuService#findRecordCount()
	 */
	@Override
	public int findRecordCount() {
		return menuDao.findRecordCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#findRecordCountByParentId
	 * (java.lang.Integer)
	 */
	@Override
	public int findRecordCountByParentId(Long id) {
		return menuDao.findRecordCountByParentId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#getButtonIdsByRoleId(java
	 * .lang.String)
	 */
	@Override
	public List<Map<String, Object>> getButtonIdsByRoleId(Long roleId) {
		return menuDao.getButtonIdsByRoleId(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#getMenu(java.lang.Integer)
	 */
	@Override
	public TsCdzy getMenu(Long id) {
		return menuDao.getMenu(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jhkj.mosdc.permission.service.MenuService#getMenuAllList()
	 */
	@Override
	public List<TsCdzy> getMenuAllList() throws Exception {
		return menuDao.getMenuAllList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#getMenuAllListByPage(int,
	 * int, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<TsCdzy> getMenuAllListByPage(int start, int limit, String sort,
			String dir, Integer parentId) {
		return menuDao.getMenuAllListByPage(start, limit, sort, dir, parentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#getMenuIdsByRoleId(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> getMenuIdsByRoleId(Long roleId, Long menuId) {
		return menuDao.getMenuIdsByRoleId(roleId, menuId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#getMenuRoleListBySql(java
	 * .lang.String)
	 */
	@Override
	public List<TsCdzy> getMenuRoleListBySql(String sql) {
		return menuDao.getMenuRoleListBySql(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#getMenuTreeByParentId(java
	 * .lang.Integer)
	 */
	@Override
	public String getMenuTreeByParentId(String params) {
		JSONObject json = JSONObject.fromObject(params);
		//判断fjdId的键是否为空；
		Long pId = Long.valueOf(json.containsKey("fjdId")?json.getString("fjdId"):"0");
		//获取菜单资源列表
		List<TsCdzy> menuList = menuDao.getMenuTreeByParentId(pId);
/*		TsCdzy tsCdzy = new TsCdzy();
		tsCdzy.setId(0L);
		tsCdzy.setFjdId(-1L);
		tsCdzy.setSfky(true);
		tsCdzy.setMc("  ");
		menuList.add(tsCdzy);*/
//		MenuTree menu = new MenuTree();
		StringBuffer jsonStr = new StringBuffer("");
		if (menuList.size() > 0) {
			boolean isFirst = true;
			jsonStr.append("[");
			for (TsCdzy obj : menuList) {
				if (!isFirst) {
					jsonStr.append(",");
				}
				isFirst = false;
				jsonStr.append(getTreeNodeJson(obj));
			}
			jsonStr.append("]");
		} else {
			jsonStr.append("[]");
		}
		return jsonStr.toString();
//		return Struts2Utils.list2json(menuList);
	}
	//--------------------------------------------------------2013-05-18-----------------------------------------------
	/**
	 * 获取菜单资源的所有节点
	 * @param params　
	 * @return　菜单树
	 * @throws Exception 
	 */
	@Override
	public String getAllMenuTree(String params) throws Exception{
		
		//获取菜单数据(缓存或数据库)
		MenuTree menu = (MenuTree) changeEhcache(false);
		//返回菜单JSON数据
		return Struts2Utils.objects2Json(menu.root);
	}
	/**
	 * 根据菜单资源的父节点ＩＤ获取下一级子节点
	 * @param params
	 * @return
	 */
	@Override
	public String getMenuTreeByPid(String params){
		//将参数转化为json对象
		JSONObject json = JSONObject.fromObject(params);
		//获取node值
		String pId =(json.containsKey("node")?json.get("node").toString():"0");
		//获取menuNodeList
		List<MenuNode> list = getChildMenuNode(pId);
		return Struts2Utils.list2json(list);
	}
	/**
	 * 根据菜单资源的父节点获取子节点(私用方法)
	 * @param pId
	 * @return
	 */
	private List<MenuNode> getChildMenuNode(String pId){
		//根据父节点获取下级菜单资源
		List<TsCdzy> list =menuDao.getMenuTreeByParentId(Long.valueOf(pId));
		//字义menuNodeList
		List<MenuNode> menuNodeList = new ArrayList<MenuNode>();
		//判断获取的下级菜单资源是否为空
		if(!list.isEmpty()){
			//定义node
			MenuNode node = new MenuNode();
			//遍历所有节点，并组装成树形结构
			for(TsCdzy tsCdzy:list){
				//把菜单资源记录转化为node节点
				node = new MenuNode(tsCdzy.getId(),tsCdzy.getFjdId(),false,tsCdzy.getMc(),((tsCdzy.getSfyzjd().equals("0") || tsCdzy.getSfyzjd()==null) ?false:true));
				//节点存到menu
				menuNodeList.add(node);
			}
		}
		return menuNodeList;
	}
	/**
	 * 判断缓存中是否存在nocheckMenu，如果不存在或者flag＝true时，查询数据到缓存
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	private Object changeEhcache(boolean flag) throws Exception{
		MenuTree menu = new MenuTree();
		//是否为true
		//获取无checked框的menu;
		Object obj =EhcacheUtil.getObjFromCache("system_menu", "nocheckMenu");
		if(flag== true || obj == null){
			//获取菜单数据
			menu = getMenu();
			//再次增加对应键
//			EhcacheUtil.putObjToCache("system_menu", "nocheckMenu", menu);
		}else{
				menu = (MenuTree) obj;
		}
		return menu;
	}
	/**
	 * 获取菜单树
	 * @return
	 * @throws Exception
	 */
	private MenuTree getMenu() throws Exception{
		MenuTree menu = new MenuTree();
		//获取所有菜单
		long a = System.currentTimeMillis();
		List<TsCdzy> list = menuDao.getMenuAllList();
		System.out.println((System.currentTimeMillis()-a)/1000+":::::::::::::::;;");
		//定义MenuTree
		//定义顶级节点并实例化node
		MenuNode node = new MenuNode(new Long(0),new Long(-1),true,"菜单资源",false);
		//把顶级节点加入到menuTree的root中
		menu.setRoot(node);
		//遍历所有节点，并组装成树形结构
		for(TsCdzy tsCdzy:list){
			//把菜单资源记录转化为node节点
			node = new MenuNode(tsCdzy.getId(),tsCdzy.getFjdId(),false,tsCdzy.getMc(),((tsCdzy.getSfyzjd().equals("0") || tsCdzy.getSfyzjd()==null) ?false:true));
			//节点存到menu
			menu.addNode(node);
		}
		return menu;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * 获取单个树结点的Json字符串
	 * 
	 * @param obj
	 *            菜单资源
	 * @return
	 */
	private String getTreeNodeJson(TsCdzy obj) {
		Boolean leaf = this.sumChildById(obj.getId()) > 0 ? false : true;
		String result = MessageFormat.format("id:{0}, text:{1}, leaf:{2}",
				Struts2Utils.quoteString(obj.getId()),
				Struts2Utils.quoteString(obj.getMc()),
				Struts2Utils.quoteString(leaf));

		return "{".concat(result).concat("}");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#sumChildById(java.lang.
	 * Integer)
	 */
	@Override
	public Integer sumChildById(Long id) {
		return this.menuDao.sumChildById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#sumZlnc_ROLE_MENUById(java
	 * .lang.Integer)
	 */
	@Override
	public Integer sumRoleByMenuId(Long id) {
		return this.menuDao.sumRoleByMenuId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.MenuService#saveRolePermission(java
	 * .lang.String, java.util.List)
	 */
	@Override
	public String saveRolePermission(String params) {
		
		Map<String,Object> mp = new HashMap<String, Object>(); 
		Long roleId;
		String menuIds;
		JSONObject json = JSONObject.fromObject(params);
		roleId = Long.valueOf(json.getString("roleId"));
		menuIds = String.valueOf(json.getString("menuIds"));
		try {
			this.menuDao.saveRolePermission(roleId, menuIds);
//			CacheManager.clearAll();
		} catch (Exception e) {
			e.printStackTrace();
			mp.put("success",false);
			String jsons = Struts2Utils.map2json(mp);
			return jsons;
		}		
		mp.put("success",true);
		mp.put("info", "角色权限设置成功！");
		String jsons = Struts2Utils.map2json(mp);
		return jsons;

	}
	
	/**
	 * 保存菜单资源对象,同时设置是否叶子节点
	 * @param params
	 * @return
	 */
	@Override
	public String saveMenu(String params) {
		try {
			UserInfo userInfo = UserPermissionUtil.getUserInfo();
			TsCdzy cdzy = (TsCdzy) JsonsUtils.stringToBean(params);
			if(cdzy.getAnlxId() !=null){
				cdzy.setSfyzjd("1");
			}else{
				cdzy.setSfyzjd("0");
			}
			if(cdzy.getFjdId() == null){
			   cdzy.setFjdId(0l);
			}
			menuDao.addMenu(cdzy);
//			changeEhcache(true);
			Cache cache = CacheManager.getCacheInfo(userInfo.getId().toString());
			if(cache !=null){
				CacheManager.clearAll();
			}
			return SysConstants.JSON_SUCCESS_TRUE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
		
	}

	/* 
	 * @see com.jhkj.mosdc.permission.service.MenuService#getMenuListByUserId(java.lang.Long)
	 */
	@Override
	public List<TsCdzy> getMenuListByUserId(Long userId) throws Exception {
		return menuDao.getMenuListByUserId(userId);
	}

	/* 
	 * @see com.jhkj.mosdc.permission.service.MenuService#getMenuListByUserIdAndMenuId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<TsCdzy> getMenuListByUserIdAndMenuId(Long userId, Long menuId)
			throws Exception {
		return menuDao.getMenuListByUserIdAndMenuId(userId, menuId);
	}
	@Override
	public List<TsCdzy> getMenuListByUserIdAndMenuId(Long userId)
			throws Exception {
		return menuDao.getMenuListByUserIdAndMenuId(userId);
	}
	/* 
	 * @see com.jhkj.mosdc.permission.service.MenuService#getAllMenuListByUserId(java.lang.Long)
	 */
	@Override
	public List<TsCdzy> getAllMenuListByUserId(Long userId) throws Exception {
		return menuDao.getAllMenuListByUserId(userId);
	}
	
	@Override
	public List<TsCdzy> getAllMenuListByUserId(Long userId,Boolean groupPermiss) throws Exception {
		String cdzyIds = "";
		if(groupPermiss == true){
			cdzyIds = "1000000000015895,1000000000015896,1000000000015897";
		}
		return menuDao.getAllMenuListByUserId(userId,cdzyIds);
	}

	/* 
	 * @see com.jhkj.mosdc.permission.service.MenuService#getAllButtonMenuListByUserId(java.lang.Long)
	 */
	@Override
	public List<TsCdzy> getAllButtonMenuListByUserId(Long userId)
			throws Exception {
		return menuDao.getAllButtonMenuListByUserId(userId);
	}
	
	@Override
	public String queryEntityDataPermiss(String params) throws Exception{
		
		return null;
	}
	

}
