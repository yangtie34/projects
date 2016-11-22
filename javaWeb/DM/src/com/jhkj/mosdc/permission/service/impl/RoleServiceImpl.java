/**
 * @author:dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22  下午09:16:52
 *
 */
package com.jhkj.mosdc.permission.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.EntityUtil;
import com.jhkj.mosdc.framework.util.JsonsUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.dao.MenuDao;
import com.jhkj.mosdc.permission.dao.RoleDao;
import com.jhkj.mosdc.permission.po.MenuNode;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.po.Tree;
import com.jhkj.mosdc.permission.po.TreeNode;
import com.jhkj.mosdc.permission.po.TsCdzy;
import com.jhkj.mosdc.permission.po.TsJs;
import com.jhkj.mosdc.permission.po.TsJsCdzy;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.service.RoleService;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

/**
 * @Comments: 角色服务接口实现类 Company: topMan Created by dangdong(mrdangdong@163.com)
 * @DATE:2012-5-22
 * @TIME: 下午09:16:52
 */
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	private RoleDao roleDao;
	private BaseDao baseDao;
	private MenuDao menuDao;
	
	private ObjectMapper objectMap = new ObjectMapper();

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public String saveRole(String params) throws Exception {
		TsJs role = getTsJsObj(params);
		//获取当前用户的ID
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
//		Long groupId = userInfo.getGroupPermiss();
//		String dm = baseDao.queryBmById(groupId, "XXDM-SFBZ");
		TsJs tsJs =roleDao.getRole(userInfo.getId(),role.getJslxId());
		if(tsJs != null){
			return SysConstants.JSON_SUCCESS_FALSE;
		}
		/*role.setGroupFlag(false);
		if(dm.equals("1")){
			role.setGroupFlag(true);
		}*/
		//角色中保存用户的ID
		role.setUserId(userInfo.getId());
		//调用保存角色方法
		roleDao.saveRole(role);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.RoleService#deleteRole(com.jhkj.mosdc
	 * .permission.po.TsJs)
	 */
	@Override
	public String deleteRole(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		String ids = obj.getString("ids");
		List<TsJsCdzy> tsJsCdzy =roleDao.queryTsJsCdzys(ids);
		if(tsJsCdzy != null && tsJsCdzy.size()>0){
			return SysConstants.JSON_SUCCESS_FALSE;
		}
		List<TsJs> tsRoleList = roleDao.getRoles(ids);
		if(tsRoleList != null && tsRoleList.size()>0){
			roleDao.deleteRoles(tsRoleList);
			return SysConstants.JSON_SUCCESS_TRUE;
		}
//		this.roleDao.deleteRole(role);
		return SysConstants.JSON_SUCCESS_FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.RoleService#deleteRoleMenusByRoleId
	 * (java.lang.Long)
	 */
	@Override
	public int deleteRoleMenusByRoleId(Long roleId) {
		return this.roleDao.deleteRoleMenusByRoleId(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.RoleService#editRole(com.jhkj.mosdc
	 * .permission.po.TsJs)
	 */
	@Override
	public String updateRole(String params) throws Exception {
		TsJs role = getTsJsObj(params);
		role.setUserId(UserPermissionUtil.getUserInfo().getId());
		//更新角色信息
		roleDao.updateRole(role);
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 根据参数将数据转化为对象
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private TsJs getTsJsObj(String params) throws Exception{
		//将前台传入的参数转化为对象
		TsJs role = (TsJs) JsonsUtils.stringToBean(params);;
		return role;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jhkj.mosdc.permission.service.RoleService#findRecordCount()
	 */
	@Override
	public int findRecordCount() {
		return this.roleDao.findRecordCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.RoleService#getRole(java.lang.Long)
	 */
	@Override
	public TsJs getRole(Long id) {
		return this.roleDao.getRole(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jhkj.mosdc.permission.service.RoleService#getRoleAllList()
	 */
	@Override
	public List<TsJs> getRoleAllList() {
		return this.roleDao.getRoleAllList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.RoleService#getRoleAllListByPage(int,
	 * int, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TsJs> getRoleAllListByPage(int start, int limit, String sort,
			String dir) {
		return this.roleDao.getRoleAllListByPage(start, limit, sort, dir);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.RoleService#getRoleReferenceRecordNum
	 * (java.lang.Long)
	 */
	@Override
	public int getRoleReferenceRecordNum(Long id) {
		return this.roleDao.getRoleReferenceRecordNum(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.RoleService#isNameExist(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public boolean isNameExist(Long id, String name) {
		return this.roleDao.isNameExist(id, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jhkj.mosdc.permission.service.RoleService#saveRoleMenus(java.lang
	 * .Long, java.util.List)
	 */
	@Override
	public void saveRoleMenus(Long roleId, List<String> perms) {
		
		this.roleDao.saveRoleMenus(roleId, perms);
	}

	/*
	 * @see
	 * com.jhkj.mosdc.permission.service.RoleService#getPermissionJson(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public String getPermissionJson(String params) throws Exception {
//		Long a = System.currentTimeMillis();
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		Long bmId = userInfo.getBmId();
		//角色ID;
		Long roleId;
		//角色IDS
		String roleIds;
		Long parentId = null;
		JSONObject json = JSONObject.fromObject(params);
		String roleTypeIds = userInfo.getRoleIds();
		if(json.containsKey("node")){
			parentId = Long.valueOf(json.getString("node"));
		}
		String[] roleIdsArray = roleTypeIds.split(",");
		boolean flag = false;
		if(roleIdsArray.length>0){
			for(int j = 0;j<roleIdsArray.length;j++){
				String dm = baseDao.queryBmById(Long.valueOf(roleIdsArray[j]), "XXDM-QXJSLX");
				if(dm.equals("1")){
					flag = true;
					break;
				}
			}
		}
		roleId = json.getLong("roleId");
		roleIds = roleDao.getRoleIds(roleTypeIds);
//		System.out.println("YYYYYY:::"+(System.currentTimeMillis()-a));
		return getJsonByRoleIdAddMenuId(roleIds,roleId, parentId,flag);
		
	}
	/**
	 * 功能说明：根据角色标识，获取角色对应的菜单信息
	 */
	public String getPermissionJsonByRoleId(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		String roleId = json.getString("roleId");
		List<TsCdzy> list = roleDao.queryTsCdzysByRoleId(Long.parseLong(roleId));
		
		//存储树结构
		Map<Long,TreeNode> nodeHash = new HashMap<Long,TreeNode>();
		TreeNode root = new TreeNode(new Long(0), new Long(-1), "", "");
        nodeHash.put(root.getId(), root);
        putNode(nodeHash,root);
		
		TreeNode treeNode = null;
		
		for(TsCdzy tsCdzy : list){
			treeNode = new TreeNode(tsCdzy.getId(),tsCdzy.getFjdId(),tsCdzy.getMc(),"");
			treeNode.setChecked(tsCdzy.getChecked());
			putNode(nodeHash,treeNode);
		}
		generateTree(nodeHash);
		
		return Struts2Utils.objects2Json(root);
	}
	public void generateTree(Map<Long,TreeNode> nodeHash){
		Set<Entry<Long, TreeNode>> set = nodeHash.entrySet();
		for(Entry<Long, TreeNode> entry : set){
			if(entry.getValue().getPid() == null){
				entry.getValue().setPid(0l);
			}
			TreeNode parent = nodeHash.get(entry.getValue().getPid());
			if(parent != null){
				parent.getChildren().add(entry.getValue());
			}
		}
	}
	public void putNode(Map<Long,TreeNode> nodeHash,TreeNode node){
		if(null==node.getPid()){
			node.setPid(new Long(0));
		}
		if(!(nodeHash.get(node.getId())!=null && nodeHash.get(node.getId()).getPid() == 0l)){
			nodeHash.put(node.getId(), node);
		}
	}
	//------------------------------------------------------2013-05-20------------------------------------------------------------------
	/**
	 * 根据角色用户角色IDS,当前角色的ID，当前菜单的fjdId　是否管理员　　(高东杰--新增)
	 * @param roleIds 当前用户的角色ＩＤ
	 * @param roleId 角色ID
	 * @param parentId　父节点ID
	 * @param flag　是否管理员
	 * @return　返回树形结构的数据
	 */
	public String getJsonByRoleIdAddMenuId(String roleIds,Long roleId,Long parentId,boolean flag){
		Long a = System.currentTimeMillis();
		//定义变量
		String sql = "";
		List<TsCdzy> list = null;
		//判断flag是否为true
		if(flag == true){
			sql = this.dealRoleSql(roleId, parentId,roleIds);
		}else{
			sql = this.getRoleMenuSql(roleId, parentId,roleIds);
		}
		//查询菜单数据
		list = menuDao.getMenuRoleListBySql(sql);
		//定义一个树对象
		Tree tree = new Tree();
		//设置顶级节点
		TreeNode node = new TreeNode(new Long(0),new Long(-1),true,"",false);
		//获取节点类型
//		Long bzdmId = baseDao.getBzdmIdByDm("", "");
		//设置顶级节点
		tree.setRoot(node);
		//判断查询菜单数据是否为空
		if(list != null && list.size() >0){
			for(TsCdzy c:list){
				/*if(c.getCdssflId() ==bzdmId){
					continue;
				}*/
				//将菜单数据转化为树的节点
				node = new TreeNode(c.getId(),c.getFjdId(),false,c.getChecked(),c.getMc(),(c.getSfyzjd().equals("true")?true:false));
				//将节点加到树上
				tree.addNode(node);
			}
		}
		/*String s =Struts2Utils.bean2json(tree.getRoot());
		Long b=System.currentTimeMillis();
		System.out.println(b-a+"::::::::::::::::::::;");*/
		String s = Struts2Utils.objects2Json(tree.getRoot());
		System.out.println(System.currentTimeMillis()-a+"::::::::::::::::::::;");
		return s;
	}

/*	public String getPermissionJsonByRoleIdAndMenuId(String roleIds,String roleId, Long parentId,boolean flag) {
//		Long a =System.currentTimeMillis();
		// TODO 如果出错，检查传入的标识是不是 Long引起的
		String sql = "";
		List<TsCdzy> list = null;

		StringBuffer sb = new StringBuffer("");
		// 组sql语句
		if(flag == true){
			sql = this.dealRoleSql(Long.valueOf(roleId), parentId,roleIds);
		}else{
			sql = this.getRoleMenuSql(Long.valueOf(roleId), parentId,roleIds);
		}
		list = this.menuDao.getMenuRoleListBySql(sql);

		if (list != null && list.size() > 0) {
			sb.append("[");
			boolean isFirst = true;
			for (TsCdzy m : list) {
				if (!isFirst)
				sb.append(",");
				// 加载自己
				sb.append("{");
				sb.append(this.getMenuJson(m));

				// 加载子结点(只有不是button才取)
				if (m.getCdssflId().intValue() != 4) {
					if(flag == true){
						sql = this.dealRoleSql(roleId, m.getId(),roleIds);
					}else{
						sql = this.getRoleMenuSql(roleId, m.getId(),roleIds);
					}
					List<TsCdzy> subList = this.menuDao
							.getMenuRoleListBySql(sql);
					if (subList != null && subList.size() > 0) {
						sb.append(", children:[");
						boolean isFirst2 = true;
						for (TsCdzy subm : subList) {
							if (!isFirst2)
								sb.append(",");
							sb.append("{");
							sb.append(this.getMenuJson(subm));
							// 递归
							sb.append(", children:");
							sb.append(this.getPermissionJsonByRoleIdAndMenuId(
									roleIds,roleId, subm.getId(),flag));
							sb.append("}");
							isFirst2 = false;
						}
						sb.append("]");
					}
				}

				isFirst = false;
				sb.append("}");
			}
			sb.append("]");
//			System.out.println("OOOOOOOOOOOOO::::::"+(System.currentTimeMillis()-a));
			return sb.toString();
		} else {
			return "[]";
		}
	}*/

	private String dealRoleSql(Long roleId, Long parentId,String roleIds) {
		String sql = "";
		sql = "Select  m.id,m.MC, m.CDSSFL_ID, m.FJD_ID,m.SFYZJD,(Case When jc.JS_ID is not null Then 'True' Else 'False' End) checked "
				.concat(" from TS_CDZY m left join TS_JS_CDZY r on m.Id=r.CDZY_ID ");
		sql = sql.concat(" and r.JS_ID in (" + roleIds+") ");
		sql = sql.concat("  left join ts_js_cdzy jc on r.cdzy_id = jc.cdzy_id and jc.js_id = "+roleId);
//		sql = sql.concat(" ");
		// deal with ParentId
		Long pId = 0l;
		if (parentId != null) {
			pId = parentId;
		}
		sql = sql.concat(" where m.sfky = 1 ");
//		sql = sql.concat(" where m.FJD_ID = " + pId);
		sql = sql.concat(" Order By m.FJD_ID,m.PXH");

		return sql;
	}
	
	private String getRoleMenuSql(Long roleId, Long parentId,String roleIds) {
		String sql = "select t.id,t.MC,t.cdssfl_id,t.FJD_ID,t.SFYZJD,t.isCheck from " +
				" (Select distinct m.id,m.pxh,m.MC, m.CDSSFL_ID, m.FJD_ID,m.SFYZJD,(Case When j.js_id is not null Then 'True' Else 'False' End) checked "
				.concat(" from TS_CDZY m , TS_JS_CDZY r left join (select jc.* from ts_js_cdzy jc where jc.js_id ="+roleId+") j on r.cdzy_id = j.cdzy_id" +
						" where m.Id=r.CDZY_ID ");
		if(roleIds.length()>2){
			sql = sql.concat(" and r.JS_ID in ( " + roleIds+") ");
		}
		// deal with ParentId
		Long pId = 0l;
		if (parentId != null) {
			pId = parentId;
		}
		sql = sql.concat(" where m.sfky = 1 ");
//		sql = sql.concat(" and m.FJD_ID = " + pId);
		sql = sql.concat(") t Order By m.FJD_ID,t.PXH ");

		return sql;
	}

	/**
	 * 组建Df_menus的Json数据 只要是button的都为Leaf,否则反之
	 * 
	 * @param menu
	 * @return
	 */
	private String getMenuJson(TsCdzy menu) {
		// 判断一下菜单资源的类型
		boolean leaf = menu.getCdssflId().intValue() == 4 ? true : false;
		String result = MessageFormat.format(
				"id:{0}, text:{1}, leaf:{2}, checked:{3}",
				String.valueOf(menu.getId()),
				Struts2Utils.quoteString(menu.getMc()), leaf,
				menu.getSfky());
		return result;
	}
	
	@Override
	public String queryUserJs(String params) throws Exception{
		Map objMap = objectMap.readValue(params.toString(), Map.class);
		//获取查询参数
		Map paramMap = this.getSQLParams(objMap);
		List paramList =(List) paramMap.get("list");
		// 获取用户信息
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		List<TsJs> list = roleDao.queryJsxx(userInfo.getId());
		if(list != null && list.size() >0 ){
			Map<String,Object> mp = new HashMap<String, Object>(); 
			mp.put("success", SysConstants.JSON_SUCCESS_TRUE);
			mp.put("count", list.size());
			mp.put("data", list);
			return Struts2Utils.map2json(mp);
		}
		return null;
	}
	@Override
	public String queryJsTabs(String params) throws Exception{
//		long l = System.currentTimeMillis();
		JSONObject obj = JSONObject.fromObject(params);
		// 获取用户信息
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		Long rylb = obj.getLong("rylb");
		List list = roleDao.queryJsTabs(userInfo.getRoleIds(), rylb);
		if(list != null && list.size()>0){
//			System.out.println("\r<br>执行耗时 : "+(System.currentTimeMillis()-l)/1000f+" 秒 ");
			return Struts2Utils.list2json(list);
		}
		return Struts2Utils.string2json("success:false,data:[]");
	}

}
