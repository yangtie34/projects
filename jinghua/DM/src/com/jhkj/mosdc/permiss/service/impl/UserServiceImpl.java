package com.jhkj.mosdc.permiss.service.impl;

import java.beans.Encoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.po.TbJxzzjg;
import com.jhkj.mosdc.framework.po.TbXzzzjg;
import com.jhkj.mosdc.framework.po.TcXxbzdmjg;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.domain.TeachingOrganizationalStructureNode;
import com.jhkj.mosdc.permiss.po.TpRole;
import com.jhkj.mosdc.permiss.po.TpUser;
import com.jhkj.mosdc.permiss.po.TpUserRole;
import com.jhkj.mosdc.permiss.po.TpUserUsergroup;
import com.jhkj.mosdc.permiss.po.TpUsergroupDataExcept;
import com.jhkj.mosdc.permiss.po.TpUsergroupDatapermiss;
import com.jhkj.mosdc.permiss.po.TpUsergroupMenu;
import com.jhkj.mosdc.permiss.po.TpUsergroupMenuUser;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserXzDatapermissLd;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserdatapermissLd;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserdataExcept;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserdatapermiss;
import com.jhkj.mosdc.permiss.service.UserService;
import com.jhkj.mosdc.permiss.util.JSONUtil;
import com.jhkj.mosdc.permiss.util.SqlUtils;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.permission.util.BidCreate;

public class UserServiceImpl implements UserService {
	private BaseDao baseDao;

	private BaseService baseService;

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public String addUser(String params) throws Exception {
		// TODO Auto-generated method stub
		TpUser user = (TpUser) JSONUtil.stringToBean(params, TpUser.class);
		user.setId(baseDao.getId());
		user.setSfky(true);
		user.setYhly(1);// 1手动添加 2 程序同步
		user.setPassword(BidCreate.Encrypt(user.getPassword()).toUpperCase());

		TcXxbzdmjg tc = new TcXxbzdmjg();
		tc.setBzdm("XXDM-RYLB");
		List<TcXxbzdmjg> rylblist = baseDao.loadEqual(tc);
		Long tId = getRylbId(rylblist, "2");

		user.setRylbId(tId);

		baseDao.save(user);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	/**
	 * 根据代码获取人员类别ID
	 * 
	 * @param rylblist
	 * @param dm
	 * @return
	 */
	private Long getRylbId(List<TcXxbzdmjg> rylblist, String dm) {
		for (TcXxbzdmjg tc : rylblist) {
			if (tc.getDm().equals(dm))
				return tc.getId();
		}
		return null;
	}

	@Override
	public String updateUser(String params) throws Exception {
		// TODO Auto-generated method stub
		TpUser user = (TpUser) JSONUtil.stringToBean(params, TpUser.class);
		
		TpUser og = (TpUser) baseDao.get(TpUser.class, user.getId());
		og.setLoginname(user.getLoginname());
		if(!og.getPassword().equals(user.getPassword())){
			og.setPassword(BidCreate.Encrypt(user.getPassword()).toUpperCase());
		}
		baseDao.update(og);
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 修改用户的密码
	 */
	public String updateUserPassWord(String params){
		String password = JSONUtil.getString(JSONObject.fromObject(params), "newpassword");
		String oldpassword = JSONUtil.getString(JSONObject.fromObject(params), "oldpassword");
		Long userId = UserPermiss.getUser().getCurrentUserId();
		TpUser user = (TpUser) baseDao.get(TpUser.class, userId);
		if(user.getPassword().equals(BidCreate.Encrypt(oldpassword).toUpperCase())){
			user.setPassword(BidCreate.Encrypt(password).toUpperCase());
			baseDao.update(user);
			return SysConstants.JSON_SUCCESS_TRUE;
		}else{
			return "{info : '旧密码输入错误，请重新输入!'}";
		}
		
	}

	@Override
	public String updateUsergroupRoles(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		List<String> roleList = JSONUtil.getJSONArray(json, "roleIds");

		// 删除之前用户的角色
		if(usergroupId != null){
			TpUserRole tr = new TpUserRole();
			tr.setUsergroupId(usergroupId);
			tr.setUserId(userId);
			baseDao.deleteEqual(tr);
		}else{
			String dsql =  "delete from tp_user_role tur where tur.user_id="+userId+" and tur.usergroup_id is null";
	        baseDao.updateSqlExec(dsql);
		}
		
		// 设置当前用户的角色
		for (String roleId : roleList) {
			TpUserRole tur = new TpUserRole();
			tur.setUserId(userId);
			tur.setUsergroupId(usergroupId);
			tur.setRoleId(Long.valueOf(roleId));
			baseDao.save(tur);
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 查询用户-用户组菜单权限
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String queryUserUsergroupMenuPermiss(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		json.put("start", 0);
		json.put("limit", 100000);
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		
		// Long menuId = JSONUtil.getLong(json, "menuId");
		String[] columnArray = "id, usergroup_id, menu_id, user_id"
				.split(",");
		String[] aliasArray = "id, usergroupId, menuId, userId"
				.split(",");

		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray,
				"(select * from tp_usergroup_menu_user where usergroup_id"+this.getUserGroupCondition(usergroupId)+")");
		String result = baseService.queryTableDataBySqlWithAlias(sql, json.toString());
		return result;
	}

	@Override
	public String updateUserUsergroupMenuPermiss(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long userId = JSONUtil.getLong(json, "userId");
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		String[] menuIds = JSONUtil.getString(json, "menuIds").split(",");
		//删除之前的用户菜单权限
		TpUsergroupMenuUser tm = new TpUsergroupMenuUser();
		tm.setUsergroupId(usergroupId);
		tm.setUserId(userId);
		baseDao.deleteEqual(tm);
		//添加重新设置的菜单权限
		for(String object : menuIds){
			if(!object.equals("")){
				tm = new TpUsergroupMenuUser();
				tm.setUsergroupId(usergroupId);
				tm.setUserId(userId);
				tm.setMenuId(Long.valueOf(object));
				baseDao.save(tm);
			}
		}
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	/***
	 * 查询用户组的数据权限
	 * 
	 * @param params
	 *            {usergroupId : ''}
	 */
	public String queryUserUsergroupDataPermiss(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		String userId = JSONUtil.getString(json, "userId");
		String usergroupIdCondition = getUserGroupCondition(JSONUtil.getString(
				json, "usergroupId"));

		Map ret = new HashMap();
		ret.put("usergroupId", JSONUtil.getLong(json, "usergroupId"));

		String sql = " select BJ_ID from TP_USERGROUP_USERDATAPERMISS t where t.usergroup_id {0} and t.user_id = {1}";
		sql = StringUtils.format(sql, usergroupIdCondition, userId);
		List alllist = baseDao.querySqlList(sql);
		ret.put("allDataPermiss", alllist);

		String exceptsql = "select MENU_ID,EXCEPT_BJ_ID from TP_USERGROUP_USERDATA_EXCEPT t"
				+ "  where t.usergroup_id {0} and t.user_id={1} order by menu_id";
		exceptsql = StringUtils.format(exceptsql, usergroupIdCondition, userId);
		List<Map> exceptlist = baseDao.querySqlList(exceptsql);

		List menuList = new ArrayList();
		Map menu = new HashMap();
		for (Map map : exceptlist) {
			Object menuId = map.get("menu_id");
			Object bjId = map.get("EXCEPT_BJ_ID");
			if (menu.get("menuId") == null
					|| !menu.get("menuId").equals(menuId)) {
				menu.put("menuId", menuId);
				menu.put("except", new ArrayList());
				menuList.add(menu);
			} else {
				menu = new HashMap();
				menu.put("menuId", menuId);
				menu.put("except", new ArrayList());
				menuList.add(menu);
			}
		}

		ret.put("exceptDataPermiss", menuList);

		return Struts2Utils.map2json(ret);
	}

	@Override
	public String updateUserUsergroupDataPermiss(String params)
			throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");
		List exceptDataPermissList = JSONUtil.getJSONArray(json,
				"exceptDataPermiss");

		// 删除之前的数据权限
		TpUsergroupUserdatapermiss td = new TpUsergroupUserdatapermiss();
		td.setUsergroupId(usergroupId);
		td.setUserId(userId);
		baseDao.deleteEqual(td);
		TpUsergroupUserdataExcept tde = new TpUsergroupUserdataExcept();
		tde.setUsergroupId(usergroupId);
		tde.setUserId(userId);
		baseDao.deleteEqual(tde);
		// 插入新的数据权限
		for (Object obj : allDataPermissList) {
			TpUsergroupUserdatapermiss t = new TpUsergroupUserdatapermiss();
			t.setUsergroupId(usergroupId);
			t.setUserId(userId);
			t.setBjId(Long.valueOf(obj.toString()));
			t.setId(baseDao.getId());
			baseDao.save(t);
		}
		for (Object obj : exceptDataPermissList) {
			JSONObject menujson = JSONObject.fromObject(obj);
			JSONArray menuIds = menujson.getJSONArray("menuIds");
			JSONArray exceptIds = menujson.getJSONArray("except");
			for (Object menuId : menuIds) {
				for (Object exceptId : exceptIds) {
					TpUsergroupUserdataExcept t = new TpUsergroupUserdataExcept();
					t.setId(baseDao.getId());
					t.setMenuId(Long.valueOf(menuId.toString()));
					t.setUserId(userId);
					t.setExceptBjId(Long.valueOf(exceptId.toString()));
					t.setUsergroupId(usergroupId);
					baseDao.save(t);
				}
			}
		}

		return SysConstants.JSON_SUCCESS_TRUE;
	}

	/***
	 * 查询用户的数据权限(按组织结构)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public String queryUserUsergroupDataPermissByJxzzjg(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		String sql = "select zzjg.id,zzjg.mc as text from TP_USERGROUP_UDPERMISS_LD ld inner join tb_jxzzjg zzjg on ld.jxzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(usergroupId)+" and ld.user_id = " + userId;
		List list = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(list);
	}
	/***
	 * 查询用户的数据权限(按组织结构)(以树的形式)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String queryUserUsergroupDataPermissByJxzzjgAsTree(String params) throws Exception{
		
		return null;
	}
	/**
	 * 修改用户的数据权限(按组织结构)
	 * @throws Exception 
	 */
	public String updateUserUsergroupDataPermissByJxzzjg(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");

		// 删除之前的数据权限
		TpUsergroupUserdatapermissLd tu = new TpUsergroupUserdatapermissLd();
		tu.setUsergroupId(usergroupId);
		tu.setUserId(userId);
		baseDao.deleteEqual(tu);
		// 插入新的数据权限
		for (Object obj : allDataPermissList) {
			TpUsergroupUserdatapermissLd t = new TpUsergroupUserdatapermissLd();
			t.setUsergroupId(usergroupId);
			t.setUserId(userId);
			t.setJxzzjgId(Long.valueOf(obj.toString()));
			t.setId(baseDao.getId());
			baseDao.save(t);
		}

		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 修改用户数据权限（按组织结构）-而且需要根据实际上用户所在组的数据权限生成实际的数据权限
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String updateUserUsergroupDataPermissAndSaveWidthExactValue(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");
		String permiss = StringUtils.join(allDataPermissList, ",");
		List<Long> addList = new ArrayList<Long>();
		
		String sql = "select zzjg.* from TP_USERGROUP_DATAPERMISS_LD ld inner join tb_jxzzjg zzjg on ld.jxzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(usergroupId);
		
		List<TbJxzzjg> glist = baseDao.querySqlEntityList(sql, TbJxzzjg.class);
		
		
		TbJxzzjg zzjg = new TbJxzzjg();
		zzjg.setSfky("1");
		List<TbJxzzjg> list = baseDao.loadEqual(zzjg);
		//生成Hash表
		Map<Long, TeachingOrganizationalStructureNode> hash = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjg(list);
		TeachingOrganizationalStructureNode.generateNodeTree(hash);
		Map<Long, TeachingOrganizationalStructureNode> ghash = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjg(glist);
		
		for(Object obj : allDataPermissList){
			Long oid = Long.valueOf(obj.toString());
			if(ghash.containsKey(oid)){//如果ID直接存在组权限里，将其添加到新增列表里
				addList.add(oid);
			}else{
				TeachingOrganizationalStructureNode node = hash.get(oid);//在组织结构树上查找-该节点
				if(node.getCclx().equals("ZY")){//如果该节点是专业，将其添加到新增列表里
					addList.add(oid);
				}else{//该节点是父节点，拥有子节点
					List<TeachingOrganizationalStructureNode> children = node.children;
					//遍历子孙节点，如果子孙节点中的权限存在于用户组的数据权限中，那么将其加入新增列表中
					List<TeachingOrganizationalStructureNode> itlist = children;
					List<TeachingOrganizationalStructureNode> baklist = new ArrayList<TeachingOrganizationalStructureNode>();
					while(itlist.size()!=0){
						for(TeachingOrganizationalStructureNode c : itlist){
							if(ghash.containsKey(c.getId())){
								addList.add(c.getId());
							}else{
								//当前节点的父节点存在于组权限中，但是当前权限的父节点又不存在于用户设置的权限中，那么将该
//								if(ghash.get(c.getPid())!=null && permiss.indexOf(c.getPid().toString())<0){
//									addList.add(c.getId());
//									baklist.addAll(hash.get(c.getId()).children);
//								}
							}
							baklist.addAll(hash.get(c.getId()).children);
						}
						itlist = baklist;
						baklist = new ArrayList();
					}
					TeachingOrganizationalStructureNode pnode = hash.get(node.getPid());//获取当前节点的父节点
					while(pnode != null){
						if(ghash.containsKey(pnode.getId())){
							addList.add(node.getId());
							break;
						}
						pnode = hash.get(pnode.getPid());
					}
				}
			}
		}
		//删除以前的数据
		TpUsergroupUserdatapermissLd tul = new TpUsergroupUserdatapermissLd();
		tul.setUsergroupId(Long.valueOf(usergroupId));
		tul.setUserId(userId);
		baseDao.deleteEqual(tul);
		//新增现在的数据
		for(Long zzjgId : addList){
			tul = new TpUsergroupUserdatapermissLd();
			tul.setUsergroupId(Long.valueOf(usergroupId));
			tul.setUserId(userId);
			tul.setJxzzjgId(zzjgId);
			baseDao.save(tul);
		}
		
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/***
	 * 查询用户的数据权限(按行政组织结构)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String queryUserUsergroupDataPermissByXzzzjg(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		String sql = "select zzjg.id,zzjg.mc as text from TP_USERGROUP_XZ_UDPERMISS_LD ld inner join tb_xzzzjg zzjg on ld.xzzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(usergroupId)+" and ld.user_id = " + userId;
		List list = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(list);
	}
	/***
	 * 查询用户的数据权限(按行政组织结构)(以树的形式)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String queryUserUsergroupDataPermissByXzzzjgAsTree(String params) throws Exception{
		return null;
	}
	/**
	 * 修改用户的数据权限(按行政组织结构)
	 * @throws Exception 
	 */
	public String updateUserUsergroupDataPermissByXzzzjg(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");

		// 删除之前的数据权限
		TpUsergroupUserXzDatapermissLd tu = new TpUsergroupUserXzDatapermissLd();
		tu.setUsergroupId(usergroupId);
		tu.setUserId(userId);
		baseDao.deleteEqual(tu);
		// 插入新的数据权限
		for (Object obj : allDataPermissList) {
			TpUsergroupUserXzDatapermissLd t = new TpUsergroupUserXzDatapermissLd();
			t.setUsergroupId(usergroupId);
			t.setUserId(userId);
			t.setXzzzjgId(Long.valueOf(obj.toString()));
			t.setId(baseDao.getId());
			baseDao.save(t);
		}

		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 修改用户数据权限（按行政组织结构）-而且需要根据实际上用户所在组的数据权限生成实际的数据权限
	 * @throws Exception 
	 */
	public String updateUserUsergroupXzDataPermissAndSaveWidthExactValue(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");
		String permiss = StringUtils.join(allDataPermissList, ",");
		List<Long> addList = new ArrayList<Long>();
		
		String sql = "select zzjg.* from TP_USERGROUP_XZDATAPERMISS_LD ld inner join tb_xzzzjg zzjg on ld.xzzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(usergroupId);
		
		List<TbXzzzjg> glist = baseDao.querySqlEntityList(sql, TbXzzzjg.class);
		
		
		TbXzzzjg zzjg = new TbXzzzjg();
		zzjg.setSfky("1");
		List<TbXzzzjg> list = baseDao.loadEqual(zzjg);
		//生成Hash表
		Map<Long, TeachingOrganizationalStructureNode> hash = TeachingOrganizationalStructureNode.translateNodeHashForTbXzzzjg(list);
		TeachingOrganizationalStructureNode.generateNodeTree(hash);
		Map<Long, TeachingOrganizationalStructureNode> ghash = TeachingOrganizationalStructureNode.translateNodeHashForTbXzzzjg(glist);
		
		for(Object obj : allDataPermissList){
			Long oid = Long.valueOf(obj.toString());
			if(ghash.containsKey(oid)){//如果ID直接存在组权限里，将其添加到新增列表里
				addList.add(oid);
			}else{
				TeachingOrganizationalStructureNode node = hash.get(oid);//在组织结构树上查找-该节点
				if(node == null){
					continue;
				}
				if(node.getChildren().size()==0){//如果该节点是子节点，将其添加到新增列表里
					addList.add(oid);
				}else{//该节点是父节点，拥有子节点
					List<TeachingOrganizationalStructureNode> children = node.children;
					//遍历子孙节点，如果子孙节点中的权限存在于用户组的数据权限中，那么将其加入新增列表中
					List<TeachingOrganizationalStructureNode> itlist = children;
					List<TeachingOrganizationalStructureNode> baklist = new ArrayList<TeachingOrganizationalStructureNode>();
					while(itlist.size()!=0){
						for(TeachingOrganizationalStructureNode c : itlist){
							if(ghash.containsKey(c.getId())){
								addList.add(c.getId());
							}else{
								//当前节点的父节点存在于组权限中，但是当前权限的父节点又不存在于用户设置的权限中，那么将该
//								if(ghash.get(c.getPid())!=null && permiss.indexOf(c.getPid().toString())<0){
//									addList.add(c.getId());
//									baklist.addAll(hash.get(c.getId()).children);
//								}
							}
							baklist.addAll(hash.get(c.getId()).children);
						}
						itlist = baklist;
						baklist = new ArrayList();
					}
				}
			}
		}
		//删除以前的数据
		TpUsergroupUserXzDatapermissLd tul = new TpUsergroupUserXzDatapermissLd();
		tul.setUsergroupId(Long.valueOf(usergroupId));
		tul.setUserId(userId);
		baseDao.deleteEqual(tul);
		//新增现在的数据
		for(Long zzjgId : addList){
			tul = new TpUsergroupUserXzDatapermissLd();
			tul.setUsergroupId(Long.valueOf(usergroupId));
			tul.setUserId(userId);
			tul.setXzzzjgId(zzjgId);
			baseDao.save(tul);
		}
		
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	
	@Override
	public String deleteUser(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long userId = json.getLong("userId");
		TpUser user = (TpUser) baseDao.get(TpUser.class, userId);
		if (user.getYhly() == 1) {
			// 删除角色
			baseDao.delete(user);
			// 删除用户用户组表
			TpUserUsergroup tu1 = new TpUserUsergroup();
			tu1.setUserId(userId);
			baseDao.deleteEqual(tu1);
			// 删除用户角色表
			TpUserRole tur = new TpUserRole();
			tur.setUserId(userId);
			baseDao.deleteEqual(tur);
			// 删除用户菜单权限表
			TpUsergroupMenuUser tmu = new TpUsergroupMenuUser();
			tmu.setUserId(userId);
			baseDao.deleteEqual(tmu);
			// 删除用户数据权限表
			TpUsergroupUserdatapermiss tu = new TpUsergroupUserdatapermiss();
			tu.setUserId(userId);
			baseDao.deleteEqual(tu);
			// 删除用户数据排除表
			TpUsergroupUserdataExcept tue = new TpUsergroupUserdataExcept();
			tue.setUserId(userId);
			baseDao.deleteEqual(tue);
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 禁用用户
	 */
	public String updateDisableUser(String params){
		JSONObject json = JSONObject.fromObject(params);
		List userIds = json.getJSONArray("userIds");
		for(Object obj : userIds){
			TpUser user = (TpUser) baseDao.get(TpUser.class, Long.valueOf(obj.toString()));
			user.setSfky(false);
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 启用用户
	 */
	public String updateEnableUser(String params){
		JSONObject json = JSONObject.fromObject(params);
		List userIds = json.getJSONArray("userIds");
		for(Object obj : userIds){
			TpUser user = (TpUser) baseDao.get(TpUser.class, Long.valueOf(obj.toString()));
			user.setSfky(true);
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String queryUserByLoginName(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		String loginname = JSONUtil.getString(json, "loginname");
		TpUser tu = new TpUser();
		tu.setLoginname(loginname);
		Long num = baseDao.countEqual(tu);
		if (num > 1) {
			return SysConstants.JSON_SUCCESS_FALSE;
		} else {
			return SysConstants.JSON_SUCCESS_TRUE;
		}
	}

	@Override
	public String queryUserExceptUserIds(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		List userIds = JSONUtil.getJSONArray(json, "userIds");
		json.remove("userIds");
//		json.put("start", 0);
//		json.put("limit", 100000);
		
		String replace = userIds.size() == 0 ? "1" : StringUtils.join(userIds,
				",");
		String tableSql = "(select tu.loginname,tu.username, tu.id, tu.bmmc,tu.ghxh "
				+ " from tp_user tu where tu.id not in (" + replace + "))";

		String[] columnArray = "loginname,id,ghxh,username,bmmc".split(",");
		String[] aliasArray = "loginname,id,ghxh,username,bmmc".split(",");

		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray,
				tableSql);
		String result = baseService.queryTableDataBySqlWithAlias(sql,
				json.toString());

		return result;
	}

	/**
	 * 获取用户组Id拼接条件
	 * 
	 * @param usergroupId
	 * @return
	 */
	private String getUserGroupCondition(String usergroupId) {
		if (usergroupId == null)
			usergroupId = " is null ";
		else
			usergroupId = "=" + usergroupId;
		return usergroupId;
	}

	@Override
	public boolean queryUserExsit(String loginname, String password) {
		// TODO Auto-generated method stub
		TpUser tu = new TpUser();
		tu.setLoginname(loginname);
		tu.setSfky(true);//设置必须为可用的用户才能够登录
		if(password != null)
		   tu.setPassword(BidCreate.Encrypt(password).toUpperCase());
		Long num = baseDao.countEqual(tu);
		if (num == 1) {
			return true;
		} else {
			return false;
		}
	}
}
