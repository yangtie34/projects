package com.jhkj.mosdc.permiss.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;











































import org.apache.commons.collections.ListUtils;

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
import com.jhkj.mosdc.permiss.domain.Node;
import com.jhkj.mosdc.permiss.domain.TeachingOrganizationalStructureNode;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.po.TpMenu;
import com.jhkj.mosdc.permiss.po.TpUserRole;
import com.jhkj.mosdc.permiss.po.TpUsergroup;
import com.jhkj.mosdc.permiss.po.TpUsergroupDataExcept;
import com.jhkj.mosdc.permiss.po.TpUsergroupDatapermiss;
import com.jhkj.mosdc.permiss.po.TpUsergroupDatapermissLd;
import com.jhkj.mosdc.permiss.po.TpUsergroupManager;
import com.jhkj.mosdc.permiss.po.TpUsergroupMenu;
import com.jhkj.mosdc.permiss.po.TpUsergroupMenuUser;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserXzDatapermissLd;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserdataExcept;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserdatapermiss;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserdatapermissLd;
import com.jhkj.mosdc.permiss.po.TpUsergroupXzdatapermissLd;
import com.jhkj.mosdc.permiss.service.UsergroupService;
import com.jhkj.mosdc.permiss.util.JSONUtil;
import com.jhkj.mosdc.permiss.util.SqlUtils;
import com.jhkj.mosdc.permiss.util.UserPermiss;

public class UsergroupServiceImpl implements UsergroupService {
	private BaseDao baseDao;
	
	private BaseService baseService;

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public String addUsergroup(String params) throws Exception {
		// TODO Auto-generated method stub
		TpUsergroup t = (TpUsergroup) JSONUtil.stringToBean(params, TpUsergroup.class);
		t.setId(baseDao.getId());
		baseDao.save(t);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String updateUsergroup(String params) throws Exception {
		// TODO Auto-generated method stub
		TpUsergroup t = (TpUsergroup) JSONUtil.stringToBean(params, TpUsergroup.class);
		baseDao.update(t);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String deleteUsergroup(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		//删除当前用户组信息
		TpUsergroup t = new TpUsergroup();
		t.setId(usergroupId);
		baseDao.deleteEqual(t);
		
		//删除用户组所拥有的菜单权限
		TpUsergroupMenu tm = new TpUsergroupMenu();
		tm.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tm);
		//删除用户组赋予用户的菜单权限
		TpUsergroupMenuUser tmu = new TpUsergroupMenuUser();
		tmu.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tmu);
		//删除用户组拥有的数据权限
		TpUsergroupDatapermiss td = new TpUsergroupDatapermiss();
		td.setUsergroupId(usergroupId);
		baseDao.deleteEqual(td);
		TpUsergroupDataExcept tde = new TpUsergroupDataExcept();
		tde.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tde);
		//删除用户组赋予用户的数据权限
		TpUsergroupUserdatapermiss tu = new TpUsergroupUserdatapermiss();
		tu.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tu);
		TpUsergroupUserdataExcept tue = new TpUsergroupUserdataExcept();
		tue.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tue);
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String queryChildrenUsergroup(String params) throws Exception {
		// TODO Auto-generated method stub
		Long pgroupId = JSONUtil.getLong(JSONObject.fromObject(params), "pgroupId");
		
		String retSql = "tp_usergroup";
		if(pgroupId == null)retSql = "(select * from tp_usergroup t where t.pgroup_id is null )";
		
		
		String[] columnArray = "id, groupname, ms, pgroup_id"
				.split(",");
		String[] aliasArray = "id, groupname, ms, pgroupId"
				.split(",");
			
		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray, retSql);
		String result = baseService.queryTableDataBySqlWithAlias(sql, params);
		
		//对数据做处理，提取查询结果集中的用户组信息，并查询用户管理员信息
		JSONObject json = JSONObject.fromObject(result);
		JSONArray list = JSONUtil.getJSONArray(json, "data");
		
		List ret = new ArrayList();
		String managerSql = "select tu.username,tu.ghxh,tu.id,tu.bmmc,tu.loginname from TP_USERGROUP_MANAGER tum  "
				+ " inner join TP_USER tu on tum.group_manager_id = tu.id and tum.usergroup_id = {0} "
				+ " inner join TP_USERGROUP tug on tum.usergroup_id = tug.id ";
		String replace = "";
		for(Object object : list){
			JSONObject ug = JSONObject.fromObject(object);
			Long groupId = JSONUtil.getLong(ug, "id");
			//查询用户组管理员信息
			List mlist = baseDao.queryListMapInLowerKeyBySql(StringUtils.format(managerSql, groupId));
			ug.put("managers", mlist);
			
			ret.add(ug);
		}
		
		json.put("data", ret);
		
		return json.toString();
	}
	@Override
	public String queryUsersByUsergroup(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		boolean student = JSONUtil.getBoolean(json, "student");//学生
		boolean teacher = JSONUtil.getBoolean(json, "teacher");//教职工
		boolean others = JSONUtil.getBoolean(json, "others");//其他
		String dxgx = JSONUtil.getString(json, "dxgx");//登陆名、姓名、工号、学号
		json.remove("usergroupId");
		json.remove("student");
		json.remove("teacher");
		json.remove("others");
		json.remove("dxgx");
		
		//处理人员类别范围查询
		TcXxbzdmjg tc = new TcXxbzdmjg();
		tc.setBzdm("XXDM-RYLB");
		List<TcXxbzdmjg> rylblist = baseDao.loadEqual(tc);
		Long tId = getRylbId(rylblist, "1");
		Long sId = getRylbId(rylblist, "3");
		Long zgId = getRylbId(rylblist, "2");
		Long glId = getRylbId(rylblist, "4");
		
		List<String> rylbRet = new ArrayList<String>();
		rylbRet.add("1");
		if(student)rylbRet.add(sId.toString());
		if(teacher)rylbRet.add(tId.toString());
		if(others) {
			rylbRet.add(zgId.toString());
			rylbRet.add(glId.toString());
		}
		//处理工号、学号、姓名、登录名查询
		String gxxl = "t.loginname like {0} or t.username like {1} or t.ghxh like {2} ";
		if(dxgx != null && dxgx.length()!=0){
			dxgx = "'%"+dxgx+"%'";
			gxxl = " and " +StringUtils.format(gxxl, dxgx,dxgx,dxgx);
		}else{
			gxxl = "";
		}
		
		
		String resultSql = "(select * from tp_user t where t.rylb_id in ("+StringUtils.join(rylbRet, ",")+") "+gxxl+")";
		System.out.println(resultSql);
		
		
		//首先查询用户
		String[] columnArray = "id, ry_id, username, loginname, password, bm_id, rylb_id, xxdm, ghxh, bmmc, sfky,yhly"
				.split(",");
		String[] aliasArray = "id, ryId, username, loginname, password, bmId, rylbId, xxdm, ghxh, bmmc, sfky,yhly"
				.split(",");

		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray,resultSql );
		String result = baseService.queryTableDataBySqlWithAlias(sql, json.toString());
		
		JSONObject retJSON = JSONObject.fromObject(result);
		List list = retJSON.getJSONArray("data");
		
		//查询并设置角色名称
		List userList = new ArrayList();
		String querySql = "select distinct tr.jsmc,tr.id from TP_USER_ROLE tur inner join TP_USER tu "
				+ " on tu.id = tur.user_id inner join Tp_Role tr on tur.role_id = tr.id  and tu.id = {0} "
				+ " and tr.usergroup_id {1} and tur.usergroup_id {2}";
		for(Object object : list){
			JSONObject ojson = JSONObject.fromObject(object);
			Long userId = JSONUtil.getLong(ojson, "id");
			List<String> jsmcs = new ArrayList<String>();
			List<String> jsIds = new ArrayList<String>();
			List<Map> roles = null;
			if(usergroupId == null){
				String asql = StringUtils.format(querySql, userId," is null"," is null");
				roles = baseDao.queryListMapBySQL(asql);
				for(Map map : roles){
					jsmcs.add(map.get("JSMC").toString());
					jsIds.add(map.get("ID").toString());
				}
				ojson.put("jsmcs", jsmcs);
				ojson.put("jsIds", jsIds);
			}
			userList.add(ojson);
		}
		retJSON.put("data", userList);
		
		return retJSON.toString();
	}
	/**
	 * 查询用户组组员,供组管理员使用
	 * @throws Exception 
	 */
	public String queryUsersByUsergroupManager(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		String dxgx = JSONUtil.getString(json, "dxgx");//登陆名、姓名、工号、学号
		Integer queryType = JSONUtil.getInteger(json, "queryType");//查询类型：1 全部 2 已经赋权 3 未赋权
		
		json.remove("usergroupId");
		json.remove("dxgx");
		json.remove("queryType");
		
		
		//处理人员是否赋权查询范围
		StringBuilder queryTypeRange = new StringBuilder();
		if(queryType == 1){
		}else if(queryType == 2){
			queryTypeRange.append(" and t.id  in (");
			queryTypeRange.append("select distinct user_id from TP_USERGROUP_MENU_USER tumu where tumu.usergroup_id='").append(usergroupId).append("' ");
			queryTypeRange.append(" union");
			queryTypeRange.append(" select distinct user_id from TP_USER_ROLE tur where tur.usergroup_id='").append(usergroupId).append("' ");
			queryTypeRange.append(" union");
			queryTypeRange.append(" select distinct USER_ID from TP_USERGROUP_UDPERMISS_LD tuul where tuul.usergroup_id='").append(usergroupId).append("' ");
			queryTypeRange.append(" )");
		}else if(queryType == 3){
			queryTypeRange.append(" and t.id not in (");
			queryTypeRange.append("select distinct user_id from TP_USERGROUP_MENU_USER tumu where tumu.usergroup_id='").append(usergroupId).append("' ");
			queryTypeRange.append(" union");
			queryTypeRange.append(" select distinct user_id from TP_USER_ROLE tur where tur.usergroup_id='").append(usergroupId).append("' ");
			queryTypeRange.append(" )");
		}
		//处理工号、学号、姓名、登录名查询
		String gxxl = " (t.loginname like {0} or t.username like {1} or t.ghxh like {2} )";
		if(dxgx != null){
			dxgx = "'%"+dxgx+"%'";
			gxxl = " and " +StringUtils.format(gxxl, dxgx,dxgx,dxgx);
		}else{
			gxxl = "";
		}
		
		
		String resultSql = "(select * from tp_user t where 1=1  "+gxxl+queryTypeRange.toString()+")";
		System.out.println(resultSql);
		
		
		//首先查询用户
		String[] columnArray = "id, ry_id, username, loginname, password, bm_id, rylb_id, xxdm, ghxh, bmmc, sfky,yhly"
				.split(",");
		String[] aliasArray = "id, ryId, username, loginname, password, bmId, rylbId, xxdm, ghxh, bmmc, sfky,yhly"
				.split(",");

		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray,resultSql );
		String result = baseService.queryTableDataBySqlWithAlias(sql, json.toString());
		
		JSONObject retJSON = JSONObject.fromObject(result);
		List list = retJSON.getJSONArray("data");
		
		//查询并设置角色名称
		List userList = new ArrayList();
		String querySql = "select distinct tr.jsmc,tr.id from TP_USER_ROLE tur inner join TP_USER tu "
				+ " on tu.id = tur.user_id inner join Tp_Role tr on tur.role_id = tr.id  and tu.id = {0} "
				+ " and tr.usergroup_id {1} and tur.usergroup_id {2}";
		for(Object object : list){
			JSONObject ojson = JSONObject.fromObject(object);
			Long userId = JSONUtil.getLong(ojson, "id");
			List<String> jsmcs = new ArrayList<String>();
			List<String> jsIds = new ArrayList<String>();
			List<Map> roles = null;
			String asql = null;
			if(usergroupId == null){
				asql = StringUtils.format(querySql, userId," is null"," is null");
			}else{
				asql = StringUtils.format(querySql, userId," = "+usergroupId," = "+usergroupId);
			}
			roles = baseDao.queryListMapBySQL(asql);
			for(Map map : roles){
				jsmcs.add(map.get("JSMC").toString());
				jsIds.add(map.get("ID").toString());
			}
			ojson.put("jsmcs", jsmcs);
			ojson.put("jsIds", jsIds);	
			
			userList.add(ojson);
		}
		retJSON.put("data", userList);
		
		return retJSON.toString();
	}
	/**
	 * 根据代码获取人员类别ID
	 * @param rylblist
	 * @param dm
	 * @return
	 */
	private Long getRylbId(List<TcXxbzdmjg> rylblist,String dm){
		for(TcXxbzdmjg tc : rylblist){
			if(tc.getDm().equals(dm))return tc.getId();
		}
		return null;
	}
	@Override
	public String updateUsergroupManager(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		List usermanangerlist = JSONUtil.getJSONArray(json, "usermanagers");
		
		//删除历史组管理员
		TpUsergroupManager tum1 = new TpUsergroupManager();
		tum1.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tum1);
		
		for(Object manager : usermanangerlist){
			TpUsergroupManager tum = new TpUsergroupManager();
			tum.setUsergroupId(usergroupId);
			tum.setId(baseDao.getId());
			tum.setGroupManagerId(Long.valueOf(manager.toString()));
			baseDao.save(tum);
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String queryUsergroupManagerByMine(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		
		User user = UserPermiss.getUser();
		String loginName = user.getCurrentLoginname();
		Long userId = user.getCurrentUserId();//目前暂时设置为空
		
		
		TpUsergroupManager tum = new TpUsergroupManager();
		tum.setGroupManagerId(userId);
		List<TpUsergroupManager> tumlist = baseDao.loadEqual(tum);
		String usergroupIds = "";
		for(int i=0;i<tumlist.size();i++){
			TpUsergroupManager t = tumlist.get(i);
			usergroupIds += t.getUsergroupId();
			if(i != tumlist.size()-1){
				usergroupIds += ",";
			}
		}
		
//		List list = null;
//		if(!loginName.equals("admin")){
//			list = baseDao.queryHql("from TpUsergroup t where t.id in ("+usergroupIds+")");
//		}else{
//			list = baseDao.queryHql("from TpUsergroup t where t.pgroupId is null ");
//		}
		//首先查询用户
		String[] columnArray = "id, groupname, ms, pgroup_id".split(",");
		String[] aliasArray = "id, groupname, ms, pgroupId".split(",");
		String retSql = "";
//		if(!loginName.equals("admin")){
//			retSql = "(select * from tp_usergroup t where t.id in ("+usergroupIds+"))";
//		}else{
//			retSql = "(select * from tp_usergroup t where t.pgroup_id is null )";
//		}
		if(usergroupIds.length() == 0){
			retSql = "(select * from tp_usergroup t where t.id is null)";
		}else {
			retSql = "(select * from tp_usergroup t where t.id in ("+usergroupIds+"))";
		}
		
		String sql = SqlUtils.generateAliasSql(columnArray, aliasArray,retSql);
		String result = baseService.queryTableDataBySqlWithAlias(sql, json.toString());
		
		return result;
	}
	
	
	/**
	 * @param params {usergroupId : ''}
	 * @return 
	 *    {
	 	  	 usergroupId : ''
	 	  	 allDataPermiss : [1,2,3,4,5,6,7],
	 	  	 exceptDataPermiss : [
	 	  	      {menuId : 1243,except : []}
	 	  	 ]
	 	  }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryUsergroupDataPermiss(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		String usergroupIdCondition = getUserGroupCondition(JSONUtil.getString(json, "usergroupId"));
		
		Map ret = new HashMap();
		ret.put("usergroupId", JSONUtil.getLong(json, "usergroupId"));
		
		String sql = " select BJ_ID from TP_USERGROUP_DATAPERMISS t,tb_xxzy_bjxxb bj where t.bj_id = bj.id and bj.sfky = 1 and t.usergroup_id {0}";
		sql = StringUtils.format(sql, usergroupIdCondition);
		List alllist = baseDao.querySqlList(sql);
		ret.put("allDataPermiss", alllist);
		
		String exceptsql = "select MENU_ID,EXCEPT_BJ_ID from TP_USERGROUP_DATA_EXCEPT t where t.usergroup_id {0} order by menu_id";
		exceptsql = StringUtils.format(exceptsql, usergroupIdCondition);
		List<Map> exceptlist = baseDao.querySqlList(exceptsql);
		
		List menuList = new ArrayList();
		Map menu = new HashMap();
		for(Map map : exceptlist){
			Object menuId = map.get("menu_id");
			Object bjId = map.get("EXCEPT_BJ_ID");
			if(menu.get("menuId")==null || !menu.get("menuId").equals(menuId)){
			   menu.put("menuId", menuId);
			   menu.put("except", new ArrayList());
			   menuList.add(menu);
			}else{
			   menu = new HashMap();
			   menu.put("menuId", menuId);
			   menu.put("except", new ArrayList());
			   menuList.add(menu);
			}
		}
		
		ret.put("exceptDataPermiss", menuList);
		
		return Struts2Utils.map2json(ret);
	}
	/***
	 * 查询用户组的数据权限，并且和教学组织结构进行绑定并返回
	 * @param params {usergroupId : ''}
	 */
	public String queryUsergroupDataPermissWithZzjg(String params){
		return null;
	}
	/**
	 * 设置用户组数据权限
	 	  {
	 	  	 usergroupId : ''
	 	  	 allDataPermiss : [1,2,3,4,5,6,7],
	 	  	 exceptDataPermiss : [
	 	  	      {menuIds : [1,2,3,4],except : [1,2,3,4,5]},
	 	  	      {menuIds : [4,5,6,7],except : [7,8,9,10,11]}
	 	  	 ]
	 	  }
	 * @throws Exception 
	 */
	@Override
	public String updateUsergroupDataPermiss(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");
		List exceptDataPermissList = JSONUtil.getJSONArray(json, "exceptDataPermiss");
		
		//删除之前的数据权限
		TpUsergroupDatapermiss td = new TpUsergroupDatapermiss();
		td.setUsergroupId(usergroupId);
		baseDao.deleteEqual(td);
		TpUsergroupDataExcept tde = new TpUsergroupDataExcept();
		tde.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tde);
		//插入新的数据权限
		for(Object obj : allDataPermissList){
			TpUsergroupDatapermiss t = new TpUsergroupDatapermiss();
			t.setUsergroupId(usergroupId);
			t.setBjId(Long.valueOf(obj.toString()));
			t.setId(baseDao.getId());
			baseDao.save(t);
		}
		for(Object obj : exceptDataPermissList){
			JSONObject menujson = JSONObject.fromObject(obj);
			JSONArray menuIds = menujson.getJSONArray("menuIds");
			JSONArray exceptIds = menujson.getJSONArray("except"); 
			for(Object menuId : menuIds){
				for(Object exceptId : exceptIds){
					TpUsergroupDataExcept t = new TpUsergroupDataExcept();
					t.setId(baseDao.getId());
					t.setMenuId(Long.valueOf(menuId.toString()));
					t.setExceptBjId(Long.valueOf(exceptId.toString()));
					t.setUsergroupId(usergroupId);
					baseDao.save(t);
				}
			}
		}
				
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 按组织结构查询用户数据权限(粒度到组织结构的任何一个节点)-查询结果是List的形式，本结果是用来绑定到查询的教学组织结构树上的
	 * @param params {usergroupId : ''}
	 */
	public String queryUsergroupDataPermissByJxzzjg(String params){
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		String sql = "select zzjg.id,zzjg.mc as text from TP_USERGROUP_DATAPERMISS_LD ld inner join tb_jxzzjg zzjg on ld.jxzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(usergroupId);
		List list = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(list);
	}
	/**
	 * 查询用户组的数据权限（教学组织结构）并同时绑定--生成教学组织结构树
	 */
	public String queryUsergroupDataPermissBindJxzzjg(String params){
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		String sql = "select zzjg.id,zzjg.mc as text from TP_USERGROUP_DATAPERMISS_LD ld inner join tb_jxzzjg zzjg on ld.jxzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(usergroupId);
		
		List<Map> uplist = baseDao.queryListMapInLowerKeyBySql(sql);
		
		TbJxzzjg zzjg = new TbJxzzjg();
		zzjg.setSfky("1");
		List<TbJxzzjg> list = baseDao.loadEqual(zzjg);
		//生成Hash表
		Map<Long, TeachingOrganizationalStructureNode> hash = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjg(list);
		//生成备份Hash表-用以放置子节点
		Map<Long, TeachingOrganizationalStructureNode> hash_bak = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjg(list);
		TeachingOrganizationalStructureNode.generateNodeTree(hash_bak);
		//挂接教学组织结构节点
		Map<Long, TeachingOrganizationalStructureNode> ghash = new HashMap<Long, TeachingOrganizationalStructureNode>();
		for(Map map : uplist){
			TeachingOrganizationalStructureNode tosn = hash_bak.get(Long.valueOf(map.get("id").toString()));
			TeachingOrganizationalStructureNode posn = hash.get(tosn.getPid());
			//放置当前节点
			ghash.put(tosn.getId(), tosn);
			//放置子节点
			while(posn != null){
				ghash.put(posn.getId(), posn);
				posn = hash.get(posn.getPid());
			}
		}
		TeachingOrganizationalStructureNode.generateNodeTree(ghash);
		TeachingOrganizationalStructureNode root = TeachingOrganizationalStructureNode.getRoot(ghash);
		
		return Struts2Utils.list2json(TeachingOrganizationalStructureNode.toList(root));
	}
	/**
	 * 按组织结构设置用户数据权限(粒度到组织结构的任何一个节点)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String updateUsergroupDataPermissByJxzzjg(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");

		// 删除之前的数据权限
		TpUsergroupDatapermissLd tu = new TpUsergroupDatapermissLd();
		tu.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tu);
		// 插入新的数据权限
		for (Object obj : allDataPermissList) {
			TpUsergroupDatapermissLd t = new TpUsergroupDatapermissLd();
			t.setUsergroupId(usergroupId);
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
	public String updateUsergroupDataPermissAndSaveWidthExactValue(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		String pusergroupId = JSONUtil.getString(json, "pgroupId");
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");
		String permiss = StringUtils.join(allDataPermissList, ",");
		List<Long> addList = new ArrayList<Long>();
		
		String sql = "select zzjg.* from TP_USERGROUP_DATAPERMISS_LD ld inner join tb_jxzzjg zzjg on ld.jxzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(pusergroupId);
		
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
				}
			}
		}
		//删除以前的数据
		TpUsergroupDatapermissLd tul = new TpUsergroupDatapermissLd();
		tul.setUsergroupId(Long.valueOf(usergroupId));
		baseDao.deleteEqual(tul);
		//新增现在的数据
		for(Long zzjgId : addList){
			tul = new TpUsergroupDatapermissLd();
			tul.setUsergroupId(Long.valueOf(usergroupId));
			tul.setJxzzjgId(zzjgId);
			baseDao.save(tul);
		}
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 按组织结构查询用户组数据权限(粒度到行政组织结构的任何一个节点)
	 * @param params {usergroupId : ''}
	 */
	public String queryUsergroupDataPermissByXzzzjg(String params){
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		String sql = "select zzjg.id,zzjg.mc as text from TP_USERGROUP_XZDATAPERMISS_LD ld inner join tb_xzzzjg zzjg on ld.xzzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(usergroupId);
		List list = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(list);
	}
	
	/**
	 * 查询用户组的数据权限（行政组织结构）并同时绑定行政组织结构树
	 */
	public String queryUsergroupDataPermissBindXzzzjg(String params){
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		String sql = "select zzjg.id,zzjg.mc as text from TP_USERGROUP_XZDATAPERMISS_LD ld inner join tb_xzzzjg zzjg on ld.xzzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(usergroupId);
		
		List<Map> uplist = baseDao.queryListMapInLowerKeyBySql(sql);
		
		TbXzzzjg zzjg = new TbXzzzjg();
		zzjg.setSfky("1");
		List<TbXzzzjg> list = baseDao.loadEqual(zzjg);
		//生成Hash表
		Map<Long, TeachingOrganizationalStructureNode> hash = TeachingOrganizationalStructureNode.translateNodeHashForTbXzzzjg(list);
		//生成备份Hash表-用以放置子节点
		Map<Long, TeachingOrganizationalStructureNode> hash_bak = TeachingOrganizationalStructureNode.translateNodeHashForTbXzzzjg(list);
		TeachingOrganizationalStructureNode.generateNodeTree(hash_bak);
		//挂接教学组织结构节点
		Map<Long, TeachingOrganizationalStructureNode> ghash = new HashMap<Long, TeachingOrganizationalStructureNode>();
		for(Map map : uplist){
			TeachingOrganizationalStructureNode tosn = hash_bak.get(Long.valueOf(map.get("id").toString()));
			TeachingOrganizationalStructureNode posn = hash.get(tosn.getPid());
			//放置当前节点
			ghash.put(tosn.getId(), tosn);
			//放置子节点
			while(posn != null){
				ghash.put(posn.getId(), posn);
				posn = hash.get(posn.getPid());
			}
		}
		TeachingOrganizationalStructureNode.generateNodeTree(ghash);
		TeachingOrganizationalStructureNode root = TeachingOrganizationalStructureNode.getRoot(ghash);
		
		return Struts2Utils.list2json(TeachingOrganizationalStructureNode.toList(root));
	}
	/**
	 * 按组织结构设置用户数据权限(粒度到行政组织结构的任何一个节点)
	 * @param params {usergroupId : ''}
	 * @throws Exception 
	 */
	public String updateUsergroupDataPermissByXzzzjg(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");

		// 删除之前的数据权限
		TpUsergroupXzdatapermissLd tu = new TpUsergroupXzdatapermissLd();
		tu.setUsergroupId(usergroupId);
		baseDao.deleteEqual(tu);
		// 插入新的数据权限
		for (Object obj : allDataPermissList) {
			TpUsergroupXzdatapermissLd t = new TpUsergroupXzdatapermissLd();
			t.setUsergroupId(usergroupId);
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
	public String updateUsergroupXzDataPermissAndSaveWidthExactValue(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		String pusergroupId = JSONUtil.getString(json, "pgroupId");
		String usergroupId = JSONUtil.getString(json, "usergroupId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");
		String permiss = StringUtils.join(allDataPermissList, ",");
		List<Long> addList = new ArrayList<Long>();
		
		String sql = "select zzjg.* from TP_USERGROUP_XZDATAPERMISS_LD ld inner join tb_xzzzjg zzjg on ld.xzzzjg_id = zzjg.id and "
				+ " ld.USERGROUP_ID "+getUserGroupCondition(pusergroupId);
		
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
				if(node.getChildren().size() == 0){//如果该节点是子节点，将其添加到新增列表里
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
		baseDao.deleteEqual(tul);
		//新增现在的数据
		for(Long zzjgId : addList){
			tul = new TpUsergroupUserXzDatapermissLd();
			tul.setUsergroupId(Long.valueOf(usergroupId));
			tul.setXzzzjgId(zzjgId);
			baseDao.save(tul);
		}
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	@Override
	public String deleteUserOldDataPermissInUserGroup(String params)
			throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		String usergroupId = getUserGroupCondition(JSONUtil.getString(json, "usergroupId"));
		
		//首先移除已经不存在的总的数据权限
		String deleteAllSql = "delete from TP_USERGROUP_USERDATAPERMISS tuu where tuu.usergroup_id {0} and"
				+ "  (tuu.usergroup_id,tuu.bj_id) not in   "
				+ "  (select tud.usergroup_id,tud.bj_id from TP_USERGROUP_DATAPERMISS tud where tud.usergroup_id  {1})";
		deleteAllSql = StringUtils.format(deleteAllSql, usergroupId,usergroupId);
		baseDao.updateSqlExec(deleteAllSql);
		//删除排除表里的数据
		String deleteExceptSql = "delete from TP_USERGROUP_DATA_EXCEPT tude where tuu.usergroup_id {0} and"
				+ " (tude.usergroup_id,tude.except_bj_id) not in  "
				+ " (select tud.usergroup_id,tud.bj_id from TP_USERGROUP_DATAPERMISS tud where tud.usergroup_id {1})";
		deleteExceptSql = StringUtils.format(deleteExceptSql, usergroupId,usergroupId);
		baseDao.updateSqlExec(deleteExceptSql);
		
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String updateGrouperMenuPermiss(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		JSONArray menuList = JSONUtil.getJSONArray(json, "menuIds");
		for(Object object : menuList){
			TpUsergroupMenuUser tmu = new TpUsergroupMenuUser();
			tmu.setUsergroupId(usergroupId);
			tmu.setUserId(userId);
			tmu.setMenuId(Long.valueOf(object.toString()));
			tmu.setId(baseDao.getId());
			baseDao.save(tmu);
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String updateGrouperRole(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		JSONArray menuList = JSONUtil.getJSONArray(json, "roleIds");
		for(Object object : menuList){
			TpUserRole tr = new TpUserRole();
			tr.setUsergroupId(usergroupId);
			tr.setUserId(userId);
			tr.setRoleId(Long.valueOf(object.toString()));
			tr.setId(baseDao.getId());
			baseDao.save(tr);
		}
		return null;
	}

	@Override
	public String updateGrouperDataPermiss(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Long usergroupId = JSONUtil.getLong(json, "usergroupId");
		Long userId = JSONUtil.getLong(json, "userId");
		List allDataPermissList = JSONUtil.getJSONArray(json, "allDataPermiss");
		List exceptDataPermissList = JSONUtil.getJSONArray(json, "exceptDataPermiss");
		
		//删除之前的数据权限
		TpUsergroupUserdatapermiss td = new TpUsergroupUserdatapermiss();
		td.setUsergroupId(usergroupId);
		td.setUserId(userId);
		baseDao.deleteEqual(td);
		TpUsergroupUserdataExcept tde = new TpUsergroupUserdataExcept();
		tde.setUsergroupId(usergroupId);
		tde.setUserId(userId);
		baseDao.deleteEqual(tde);
		
		//插入新的数据权限
		for(Object obj : allDataPermissList){
			TpUsergroupUserdatapermiss t = new TpUsergroupUserdatapermiss();
			t.setUsergroupId(usergroupId);
			t.setUserId(userId);
			t.setBjId(Long.valueOf(obj.toString()));
			t.setId(baseDao.getId());
			baseDao.save(t);
		}
		for(Object obj : exceptDataPermissList){
			JSONObject menujson = JSONObject.fromObject(obj);
			JSONArray menuIds = menujson.getJSONArray("menuIds");
			JSONArray exceptIds = menujson.getJSONArray("except"); 
			for(Object menuId : menuIds){
				for(Object exceptId : exceptIds){
					TpUsergroupUserdataExcept t = new TpUsergroupUserdataExcept();
					t.setId(baseDao.getId());
					t.setUserId(userId);
					t.setMenuId(Long.valueOf(menuId.toString()));
					t.setExceptBjId(Long.valueOf(exceptId.toString()));
					t.setUsergroupId(usergroupId);
					baseDao.save(t);
				}
			}
		}
		return SysConstants.JSON_SUCCESS_TRUE;
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
   public static void main(String args[]){
	   String sql ="abc={0}{1}";
	   System.err.println(StringUtils.format(sql, "''",1));
   }
}
