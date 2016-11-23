package com.jhkj.mosdc.permission.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.ListOrderedMap;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.po.TbJzgxx;
import com.jhkj.mosdc.framework.util.JsonsUtils;
import com.jhkj.mosdc.framework.util.Page;
import com.jhkj.mosdc.framework.util.SqlParamsChange;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.dao.RoleDao;
import com.jhkj.mosdc.permission.dao.UserDao;
import com.jhkj.mosdc.permission.po.MenuNode;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.po.TbLzBm;
import com.jhkj.mosdc.permission.po.Tree;
import com.jhkj.mosdc.permission.po.TreeNode;
import com.jhkj.mosdc.permission.po.TsJs;
import com.jhkj.mosdc.permission.po.TsUser;
import com.jhkj.mosdc.permission.po.TsUserCdzy;
import com.jhkj.mosdc.permission.po.TsUserDataPermiss;
import com.jhkj.mosdc.permission.po.TsUserJs;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.service.UserInfoService;
import com.jhkj.mosdc.permission.util.BidCreate;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;
import com.jhkj.mosdc.xggl.xjgl.po.TbXjdaXjxx;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;
import com.jhkj.mosdc.xxzy.po.TbXxzyJxzzjg;
import com.jhkj.mosdc.xxzy.po.TbXxzyXzzzjg;

/**
 * @Comments: 用户管理 Company: 河南精华科技有限公司 Created by gaodongjie(gaodongjie@126.com)
 * @DATE:2012-5-15
 * @TIME: 上午11:40:53
 */
public class UserInfoServiceImpl implements UserInfoService {
	private UserDao userDao;
	private BaseDao baseDao;
	private RoleDao roleDao;
	//缓存组织机构节点
	private Map<String,Object> jxzzjgMap = new HashMap<String,Object>();
	private Map<String,Object> xzzzjgMap = new HashMap<String,Object>();
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	private void getZzjgNoCHeckMap(){
		
		MenuNode root = new MenuNode(new Long(0), new Long(-1), "", "");
		//判断组织机构map是否存在数据
		if(jxzzjgMap.isEmpty()){
			//查询教学组织机构的所有节点
			
			List jxzzjgList = userDao.getJxzzjgNodes();
			//判断教学组织机构的是否为空
			if(jxzzjgList != null && jxzzjgList.size()>0){
				//遍历教学组织机构
				for(int i = 0;i<jxzzjgList.size();i++){
					TbXxzyJxzzjg obj = (TbXxzyJxzzjg) jxzzjgList.get(i);
					//数组元素转化为Obj
//					JSONArray obj = JSONArray.fromObject(jxzzjgList.get(i));
					MenuNode treeNode = new MenuNode(obj.getId(),obj.getFjdId(),
							false, obj.getMc(), (obj.getSfyzjd().toString().equals("0") ? false
									: true),(obj.getCc()==null?0L:obj.getCc()), true);
					//以ID为键node为值存入zzjgMap;
					jxzzjgMap.put(treeNode.getId().toString(), treeNode);
				}
			}
			
			//获取班级信息
			List<TbXxzyBjxxb> zbjList = baseDao.queryEntityList("TbXxzyBjxxb", " sfky = 1 ");
			if(zbjList != null && zbjList.size()>0){
				for(TbXxzyBjxxb tbXxzyBjxxb:zbjList){
					MenuNode treeNode = new MenuNode(tbXxzyBjxxb.getId(),tbXxzyBjxxb.getFjdId(),
							false, tbXxzyBjxxb.getMc(), true,4l, true);
					//以ID为键node为值存入zzjgMap;
					jxzzjgMap.put(treeNode.getId().toString(), treeNode);
				}
			}
			//增加顶级节点
			jxzzjgMap.put("0", root);
			
			//组装为树形结构
			Set<Entry<String, Object>>set = jxzzjgMap.entrySet();
			MenuNode pnode = new MenuNode();
			for(Entry<String, Object> entry : set){
				MenuNode node = (MenuNode) entry.getValue();
				if(node.getPid() == -1l){
					continue;
				}
				//获取当前节点的父节点
				pnode = (MenuNode) jxzzjgMap.get(node.getPid().toString());
				//如果父节点不为null
				if(pnode != null){
					pnode.children.add(node);
					pnode.setLeaf(false);
				}
			}
		}
		//判断组织机构map是否存在数据
		if(xzzzjgMap.isEmpty()){
			//查询行政组织机构的所有节点
			List xxzzjgList = userDao.getXzzzjgNodes();
			//判断行政组织机构是否为空
			MenuNode rootNode = (MenuNode) jxzzjgMap.get("0");
			if(rootNode.equals(null)){
				rootNode = new MenuNode(new Long(0), new Long(-1), "", ""); 
			}
			MenuNode rootChild =rootNode.children.get(0);
			if(xxzzjgList != null && xxzzjgList.size()>0){
				//遍历行政组织机构
				for(int i = 0;i<xxzzjgList.size();i++){
					//数组元素转化为Obj
					JSONArray obj = JSONArray.fromObject(xxzzjgList.get(i));
					Long pid = obj.getLong(1);
					//判断当前节点的父节点是为0
					if(obj.getLong(1) == 0){
						pid = rootChild.getId();
					}
					MenuNode treeNode = new MenuNode(obj.getLong(0), pid,
							false, obj.getString(2), (obj.getLong(3) == 0L ? false
									: true), (obj.getString(4).equals("null") || obj.get(4)==null? 0L:obj.getLong(4)), true);
					//以ID为键node为值存入zzjgMap;
					xzzzjgMap.put(treeNode.getId().toString(), treeNode);
				}
			}
			//增加顶级节点
//			xzzzjgMap.put("0", root);
			//增加学校节点
			xzzzjgMap.put(rootChild.getId().toString(), rootChild);
			//组装为树形结构
			Set<Entry<String, Object>>set = xzzzjgMap.entrySet();
			for(Entry<String, Object> entry : set){
				MenuNode node = (MenuNode) entry.getValue();
				MenuNode pnode = (MenuNode) xzzzjgMap.get(node.getPid().toString());
				//判断上级节点是否为null
				if(pnode != null)
					pnode.children.add(node);
				else 
					continue;
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public UserInfo queryUser(String userStr) throws Exception {
		// 获取用户登录信息
		// String userid = userStr[0].toString();
		JSONObject obj = JSONObject.fromObject(userStr);
		String passwd = obj.getString("password");
		String xxdm = null;
		// 调用相应的DAO数据校验
		// (UserInfo) object.readValue(userStr,UserInfo.class);
//		Map<String, Object> user = new HashMap<String, Object>();
//		user.put("userid", obj.containsValue("loginName")?obj.getString("loginName"):"");
//		user.put("password", passwd);
		obj.put("xxdm", xxdm);
		// 根据将前台的密码数据转化为SHA1密文格式
		UserInfo userInfo = userDao.checkLogin(obj);
		TbXxzyJxzzjg jxzzjg = null;
		TbXxzyXzzzjg xzzzjg = null;
		
		if(userInfo != null){
			//查询教学组织机构
			List<TbXxzyJxzzjg> jxzzjgList = (List<TbXxzyJxzzjg>) baseDao.queryEntityList("TbXxzyJxzzjg", " id = "+userInfo.getBmId());
			//查询行政组织机构
			List<TbXxzyXzzzjg> xzzzjgList = baseDao.queryEntityList("TbXxzyXzzzjg", " id = "+userInfo.getBmId());
			//判断是否存在教学组织机构
			if(jxzzjgList.size()>0){
				jxzzjg = jxzzjgList.get(0);
				userInfo.setBmmc(jxzzjg.getMc());
			}else if(xzzzjgList != null && xzzzjgList.size()>0){
				xzzzjg = xzzzjgList.get(0);
				userInfo.setBmmc(xzzzjg.getMc());
			}
			//判断是否在
			if (userInfo == null) {
				return null;
			}
			String rylbDmStr = "";
			if (userInfo.getRylbId() != null) {
				rylbDmStr = baseDao.queryBmById(userInfo.getRylbId(), "XXDM-RYLB");
			}
			if (rylbDmStr.equals("")) {
				return null;
			}
			Long rylbDm = Long.valueOf(rylbDmStr);
			// 用户为教师或职工
			if (rylbDm.equals(1L) || rylbDm.equals(2L)) {
				Long zgId = userInfo.getZgId();
				if (zgId != null) {
					TbJzgxx tbJzgxx = userDao.queryTbJzgxx(userInfo.getZgId());
					if (tbJzgxx != null && tbJzgxx.getId() != null) {
						//部门ID
	//					userInfo.setBmId(tbJzgxx.getYxId() == null ? tbJzgxx.getKsId() : tbJzgxx.getYxId());
	//					userInfo.setBmmc(tbJzgxx.getYxId() == null ?  queryBmmcById(tbJzgxx.getKsId()):queryBmmcById(tbJzgxx.getYxId()));
						userInfo.setJzsh(tbJzgxx.getZgh());
						userInfo.setUsername(tbJzgxx.getXm());
						userInfo.setLxdh(tbJzgxx.getLxdh() == null ? "" : tbJzgxx
								.getLxdh());
						userInfo.setYxId(tbJzgxx.getYxId());
					}
				}
			} else if (rylbDm.equals(3L)) {
				TbXjdaXjxx tbXjdaXjxx = userDao.queryTbXjdaXjxx(userInfo.getZgId());
				TbXxzyBjxxb bjxx = (TbXxzyBjxxb) baseDao.get(TbXxzyBjxxb.class, tbXjdaXjxx.getBjId());
				if (tbXjdaXjxx != null && tbXjdaXjxx.getId() != null) {
					userInfo.setZgId(tbXjdaXjxx.getId());// 学生ＩＤ
					userInfo.setYxId(tbXjdaXjxx.getYxId());// 院系ID
					userInfo.setZyId(tbXjdaXjxx.getZyId());// 专业ID
					userInfo.setBjId(tbXjdaXjxx.getBjId());// 班级ID
					userInfo.setBmmc(bjxx.getMc());//设置班级名称
					userInfo.setUsername(tbXjdaXjxx.getXm()); // 姓名
					userInfo.setJzsh(tbXjdaXjxx.getXh());// 学号
				}
			}
		}
		//判断登录名是否存在，如果不在则奖用户名赋给登录名;如果用户名不存在，可将登录名赋给用户名
		if(userInfo.getLoginName() == null){
			userInfo.setLoginName(userInfo.getUsername());
		}else  if(userInfo.getUsername() == null){
			userInfo.setUsername(userInfo.getLoginName());
		}
		
		// 转化后的密码与数据取出来的数据进行对比
		// 数据一致时，返回相应用户信息
		return userInfo;
	}

	private String queryBmmcById(Long id) {
		TbXxzyXzzzjg zzjg = (TbXxzyXzzzjg) baseDao.get(TbXxzyXzzzjg.class, id);
		TbXxzyJxzzjg jxzzjg = (TbXxzyJxzzjg) baseDao.get(TbXxzyJxzzjg.class, id);
		if (null != zzjg) {
			return zzjg.getMc();
		} else if(null != jxzzjg){
			return jxzzjg.getMc();
		}else{
			return null;
		}
	}
	

	@Override
	public String queryUserInfo(String userStr) throws Exception {
		// 查询根据用户ID获取用户信息
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		// 获取相应的部门或其它信息
		return Struts2Utils.bean2json(userInfo);
	}
	@Override
	public UserInfo queryUserPermissById(Long userId) throws Throwable {
		TsUser tsUser = (TsUser) this.baseDao.queryById(userId, TsUser.class.getName());
		UserInfo userInfo = new UserInfo();
		//userInfo.setBmId(tsUser.getBmId());
		List jsList = this.userDao.queryUserRolesByUserId(userId);
		StringBuffer roleIds = new StringBuffer();
		//获取用户的角色并放入session中
		if(jsList != null && jsList.size() >0 ){
			for(int i = 0;i<jsList.size();i++){
				Object[] obj = (Object[]) jsList.get(i);
				roleIds.append(obj[1].toString()+","); 
			}
			userInfo.setRoleIds(roleIds.substring(0, roleIds.length()-1));
		}
		//获取数据权限
		String permisssql = "select * from ts_userdatapermiss where user_id = "+userId;
		List permissList = this.baseDao.querySqlList(permisssql);
		String jxzzIds = "";
		String xzzzIds = "";
		if(permissList !=null && permissList.size()>0){
			for(int i = 0 ;i<permissList.size();i++){
				Map permissMap = (Map) permissList.get(i);
				if(permissMap.get("ZZJGLB").toString().equals("1")){
					jxzzIds += permissMap.get("ZZJG_ID")+",";
				}else if(permissMap.get("ZZJGLB").toString().equals("2")){
					xzzzIds += permissMap.get("ZZJG_ID")+",";
				}
			}
		}
		//判断当前教学组织机构是否为空
		if(jxzzIds.length()>0){
			userInfo.setPermissJxzzIds(jxzzIds.substring(0,jxzzIds.length()-1));
		}
		//判断当前行政组织机构是否为空
		if(xzzzIds.length()>0){
			userInfo.setPermissXzzzIds(xzzzIds.substring(0,xzzzIds.length()-1));
		}
		
		return userInfo;
	}

	@SuppressWarnings("rawtypes")
	public String queryUserTree(String userStr) {
		// 取出yhh
		List list = baseDao.queryMenuTree();
		Tree tree = new Tree();
		TreeNode root = new TreeNode(new Long(0), new Long(-1), "中职平台", "");
		tree.setRoot(root);
		for (int i = 0; i < list.size(); i++) {
			TreeNode node = (TreeNode) list.get(i);
			tree.addNode(node);
		}
		try {
			return Struts2Utils.objects2Json(tree.root);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {

		}

		// 从DAO中查询出相应权限的树结构信息
		// 然后从数据中取出按钮权限
		// 转化为前台所需要的JSON格式
	}

	/*
	 * @see
	 * com.jhkj.mosdc.permission.service.UserInfoService#queryLzBmTree(java.
	 * lang.String)
	 */
	@Override
	public String queryLzBmTree(String params) {

		JSONObject json = JSONObject.fromObject(params);
		String pId = json.getString("node");
		if ("000000".equals(pId)) {
			pId = "0";
		}
		List<TbLzBm> tbLzBmList = userDao.queryLzBmTree(pId);
		StringBuffer jsonStr = new StringBuffer("");
		if (tbLzBmList.size() > 0) {
			boolean isFirst = true;
			jsonStr.append("[");
			for (TbLzBm obj : tbLzBmList) {
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

	}

	@Override
	public String queryMenuTree(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String pId = json.getString("node");
		List<TbXxzyJxzzjg> jxzzList = userDao.queryJxzzjgTree(Long.valueOf(pId));
		List<TbXxzyXzzzjg> xzzzList = userDao.queryZzjgTree();
		Tree tree = new Tree();
		TreeNode root = new TreeNode(new Long(0), new Long(-1), "", "");
		tree.setRoot(root);
		TreeNode treeNode;

		for (int i = 0; i < xzzzList.size(); i++) {
			TbXxzyXzzzjg tsZzjg = xzzzList.get(i);
			treeNode = new TreeNode(tsZzjg.getId(), 0l, false, false,
					tsZzjg.getMc(), true);
			tree.addNode(treeNode);
		}
		//
		for (int i = 1; i < jxzzList.size(); i++) {
			TbXxzyJxzzjg tbJxzzjg = (TbXxzyJxzzjg) jxzzList.get(i);
			treeNode = new TreeNode(tbJxzzjg.getId(), tbJxzzjg.getFjdId(),
					false, false, tbJxzzjg.getMc(), (tbJxzzjg.getSfyzjd()
							.equals("0") ? false : true));
			if (tbJxzzjg.getCc() == 2) {
				treeNode = new TreeNode(tbJxzzjg.getId(), 0l, false, false,
						tbJxzzjg.getMc(),
						(tbJxzzjg.getSfyzjd().equals("0") ? false : true));
			}
			tree.addNode(treeNode);
		}
		return Struts2Utils.objects2Json(tree.root);

	}


	@Override
	public String queryZzjgTree(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String pId = json.getString("node");
		List<TbXxzyJxzzjg> jxzzList = userDao.queryJxzzjgTree(Long.valueOf(pId));
		List<TbXxzyXzzzjg> xzzzList = userDao.queryZzjgTree();
		Tree tree = new Tree();
		TreeNode root = new TreeNode(new Long(0), new Long(-1), "", "");
		tree.setRoot(root);
		TreeNode treeNode;
		// 教学组织机构
		for (int i = 0; i < jxzzList.size(); i++) {
			TbXxzyJxzzjg tbJxzzjg = (TbXxzyJxzzjg) jxzzList.get(i);
			treeNode = new TreeNode(tbJxzzjg.getId(), tbJxzzjg.getFjdId(),
					false, false, tbJxzzjg.getMc(), (tbJxzzjg.getSfyzjd()
							.equals("0") ? false : true));
			/*
			 * if(tbJxzzjg.getCc() == 2){ treeNode = new
			 * TreeNode(tbJxzzjg.getId(
			 * ),0l,false,false,tbJxzzjg.getMc(),(tbJxzzjg
			 * .getSfyzjd().equals("0") ? false:true)); }
			 */
			tree.addNode(treeNode);
		}
		// 行政组织机构
		for (int i = 0; i < xzzzList.size(); i++) {
			TbXxzyXzzzjg tsZzjg = xzzzList.get(i);
			Long fjdId = tsZzjg.getFjdId();
			if (tsZzjg.getFjdId() == 0) {// 行政组织机构的顶级节点为0时，改变父节点
				fjdId = tree.getRoot().children.get(0).getId();
			}
			treeNode = new TreeNode(tsZzjg.getId(), fjdId, false, false,
					tsZzjg.getMc(), tsZzjg.getSfyzjd().equals("0") ? false
							: true);
			tree.addNode(treeNode);
		}
		// MenuTree tree = UserPermissionUtil.getJxzzjgTree();
		return Struts2Utils.objects2Json(tree.root);

	}
	
	@Override
	public String queryZzjgTreeNoCheck(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String pId = json.getString("node");
		List<TbXxzyJxzzjg> jxzzList = userDao.queryJxzzjgTree(Long.valueOf(pId));
		List<TbXxzyXzzzjg> xzzzList = userDao.queryZzjgTree();
		MenuTree tree = new MenuTree();
		MenuNode root = new MenuNode(new Long(0), new Long(-1), "", "");
		tree.setRoot(root);
		MenuNode treeNode;
		// 教学组织机构JSON组装
		for (int i = 0; i < jxzzList.size(); i++) {
			TbXxzyJxzzjg tbJxzzjg = (TbXxzyJxzzjg) jxzzList.get(i);
			treeNode = new MenuNode(tbJxzzjg.getId(), tbJxzzjg.getFjdId(),
					false, tbJxzzjg.getMc(),
					(tbJxzzjg.getSfyzjd().equals("0") ? false : true));
			/*
			 * if(tbJxzzjg.getCc() == 2){ treeNode = new
			 * MenuNode(tbJxzzjg.getId(
			 * ),0l,false,tbJxzzjg.getMc(),(tbJxzzjg.getSfyzjd().equals("0") ?
			 * false:true)); }
			 */
			tree.addNode(treeNode);
		}
		// 行政组织机构json组装
		for (int i = 0; i < xzzzList.size(); i++) {
			TbXxzyXzzzjg tsZzjg = xzzzList.get(i);
			Long fjdId = tsZzjg.getFjdId();
			if (tsZzjg.getFjdId() == 0) {// 行政组织机构的顶级节点为0时，改变父节点
				fjdId = tree.getRoot().children.get(0).getId();
			}
			treeNode = new MenuNode(tsZzjg.getId(), fjdId, false,
					tsZzjg.getMc(), (tsZzjg.getSfyzjd().equals("0") ? false
							: true));
			tree.addNode(treeNode);
		}
		return Struts2Utils.objects2Json(tree.root);

	}

	/*
	 * private String getZZjgTreeJson(Long roleId, Long parentId) { // TODO
	 * 如果出错，检查传入的标识是不是 Long引起的 String sql = ""; List<TreeNode> list = null;
	 * StringBuffer sb = new StringBuffer(""); // 组sql语句 return null; }
	 */
	@Override
	public String queryJxzzjgTree(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		String roleIds = userInfo.getRoleIds();
		String bmId = userInfo.getBmId() == null? "":userInfo.getBmId().toString();
		String[] roleIdsArray = roleIds.split(",");
		// 判断当前用户是否为管理员角色
		boolean flag = false;
		Long jslxId =baseDao.getBzdmIdByDm("XXDM-QXJSLX", "1");
		if (roleIdsArray.length > 0) {
			for (int j = 0; j < roleIdsArray.length; j++) {
				if (roleIdsArray[j].endsWith(jslxId.toString())) {
					flag = true;
					break;
				}
			}
		}
		
		String pId = json.getString("fjdId");
		// 判断 当前的用户为非管理员权限，把当前用户部门ID作为顶级节点
		if (flag == false) {
			pId = bmId;
		}
		//判断缓存中的组织机构是否存在数据；
		if(jxzzjgMap.isEmpty() || xzzzjgMap.isEmpty()){
			getZzjgNoCHeckMap();
		}
		
		//初始化tree
		MenuTree tree = new MenuTree();
		MenuNode root = new MenuNode(new Long(0), new Long(-1), "", "");
		tree.setRoot(root);
		if(pId.equals("0")){
			//获取教学组织机构节点
			MenuNode node = (MenuNode) jxzzjgMap.get(pId);
			//获取行政组织机构节点
//			String xzpId = node.children.get(0).getId().toString();
//			MenuNode xznode = (MenuNode) xzzzjgMap.get(xzpId);
//			node.children.get(0).children.addAll(xznode.children);
			tree.setRoot(node);
		}else{
			//教学组织机构
			MenuNode node = null;
			node =(MenuNode) jxzzjgMap.get(pId);
			MenuNode pnode = null;
			if(node == null){
				node = (MenuNode) xzzzjgMap.get(pId);
				if(xzzzjgMap.get(node.getPid()) == null){
					MenuNode rootNode = (MenuNode) jxzzjgMap.get("0");
					if(node.getPid() == rootNode.children.get(0).getId()){
						pnode = cloneNode((MenuNode) rootNode.children.get(0));
					}
				}else{
					pnode = cloneNode((MenuNode) xzzzjgMap.get(node.getPid().toString()));
				}
				//获取上级节点
				while(pnode!=null){
					pnode.children.add(node);//把当前节点放到父节点中
					node = pnode;
					if(xzzzjgMap.get(node.getPid().toString()) == null){
						break;
					}
					pnode = cloneNode((MenuNode) xzzzjgMap.get(node.getPid().toString()));//
				}
				tree.setRoot(node);
			}else{
				if(jxzzjgMap.get(node.getPid().toString()) ==null){//父节点是否存在
					pnode = null;
				}else{
					//获取上级节点
					pnode = cloneNode((MenuNode) jxzzjgMap.get(node.getPid().toString()));
				}
				while(pnode!=null){
					pnode.children.add(node);//把当前节点放到父节点中
					node = pnode;
					if(jxzzjgMap.get(node.getPid().toString()) == null){
						break;
					}
					pnode = cloneNode((MenuNode) jxzzjgMap.get(node.getPid().toString()));//
				}
				tree.setRoot(node);
			}
//			tree.addNode(node);
		}
		return Struts2Utils.objects2Json(tree.root);
	}
	/**
	 * 把node节点copy到新对象中
	 * @param fn
	 * @return
	 */
	public MenuNode cloneNode(MenuNode fn){
		MenuNode node = new MenuNode(fn.getId(), fn.getPid(), false, fn.getText(), false);
		return node;
	}
	/*public String queryJxzzjgTree(String params) throws Exception {
		JSONObject json = JSONObject.fromObject(params);
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		String roleIds = userInfo.getRoleIds();
		String bmId = userInfo.getBmId().toString();
		String[] roleIdsArray = roleIds.split(",");
		// 判断当前用户是否为管理员角色
		boolean flag = false;
		if (roleIdsArray.length > 0) {
			for (int j = 0; j < roleIdsArray.length; j++) {
				if (roleIdsArray[j].endsWith("1000000000000300")) {
					flag = true;
					break;
				}
			}
		}

		String pId = json.getString("node");
		// 判断 当前的用户为非管理员权限，把当前用户部门ID作为顶级节点
		if (flag == false) {
			pId = bmId;
		}

		Map zzjgMap = userDao.getZzjgTreeNode(String.valueOf(pId));
		List jxzzjgList = (List) zzjgMap.get("jxzzjgList");
		List xzzzjgList = (List) zzjgMap.get("xzzzjgList");
		MenuTree tree = new MenuTree();
		MenuNode root = new MenuNode(new Long(0), new Long(-1), "", "");
		tree.setRoot(root);
		// 如果当前节点存在于教学组织机构中,查询当前节点的上级的所有节点，直到顶级节点
		if (jxzzjgList != null && jxzzjgList.size() > 0) {
			JSONArray obj = JSONArray.fromObject(jxzzjgList.get(0));
			MenuNode nowNode = new MenuNode(obj.getLong(0), obj.getLong(1),
					false, obj.getString(2), (obj.getLong(3) == 0 ? false
							: true), obj.getLong(4), true);
			getTreeParent(nowNode, tree, true);
			tree.addNode(nowNode);
			getTreeNodeJson(tree, String.valueOf(nowNode.getId()));
		}
		// 如果当前节点存在于行政组织机构中,查询当前节点的上级的所有节点，直到顶级节点
		if (xzzzjgList != null && xzzzjgList.size() > 0) {
			// 获取当前树的学校节点作为当前行政组织机构的顶级节点
			Long pid = null;
			if(tree.getRoot().children.size()>0){
				pid = tree.getRoot().children.get(0).getId();
			}else{
				List rootList = userDao.getJxzzjgRoot("0");
				JSONArray obj = JSONArray.fromObject(rootList.get(0));
				MenuNode nowNode = new MenuNode(obj.getLong(0), obj.getLong(1),
						false, obj.getString(2), (obj.getLong(3) == 0 ? false
								: true), obj.getLong(4), true);
				tree.addNode(nowNode);
				pid = nowNode.getId();
			}
			for (int i = 0; i < xzzzjgList.size(); i++) {
				JSONArray obj = JSONArray.fromObject(xzzzjgList.get(i));
				if (obj.getLong(1) == 0) {
					MenuNode nowNode = new MenuNode(obj.getLong(0), pid, false,
							obj.getString(2), (obj.getLong(3) == 0 ? false
									: true), obj.getLong(4), true);
					if (xzzzjgList.size() == 1 || i == 0) {// 当前层机只有一个节点时，或节点为第一个时执行查找父节点
						getTreeParent(nowNode, tree, false);
					}
					tree.addNode(nowNode);
					getTreeNodeJson(tree, String.valueOf(nowNode.getId()));

				}
			}
		}
		return Struts2Utils.objects2Json(tree.root);
	}
*/
	@SuppressWarnings("rawtypes")
	private MenuTree getTreeParent(MenuNode node, MenuTree tree, boolean flag)
			throws Exception {
		Long cc = node.getCc();
		Long parentId = node.getPid();
		List<MenuNode> nodeList = new ArrayList<MenuNode>();
		while (cc >= 1) {
			cc = cc - 1;
			List pNodeList = null;
			if (flag == true) {
				pNodeList = userDao.getJxzzjgNodeParents(String
						.valueOf(parentId));
			} else {
				pNodeList = userDao.getXzzzjgNodeParents(String
						.valueOf(parentId));
			}
			if (pNodeList != null && pNodeList.size() > 0) {
				JSONArray nodeArray = JSONArray.fromObject(pNodeList.get(0));
				MenuNode paentNode = new MenuNode(nodeArray.getLong(0),
						nodeArray.getLong(1), false, nodeArray.getString(2),
						(nodeArray.getLong(3) == 0 ? false : true),
						nodeArray.getLong(4), false);
				parentId = paentNode.getPid();
				nodeList.add(paentNode);
			}
		}
		for (int i = nodeList.size() - 1; i >= 0; i--) {
			MenuNode parentNode = nodeList.get(i);
			tree.addNode(parentNode);

		}
		return tree;
	}

	/**
	 * 
	 * 功能说明：传入一部门对象得到一个树串
	 * 
	 * @param obj
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-14 @TIME: 上午12:33:54
	 */
	private String getTreeNodeJson(TbLzBm obj) {
		Boolean leaf = userDao.sumChildById(obj.getDwdm()) > 0 ? false : true;
		String result = MessageFormat.format("id:{0}, text:{1}, leaf:{2}",
				Struts2Utils.quoteString(obj.getDwdm()),
				Struts2Utils.quoteString(obj.getDwmc()),
				Struts2Utils.quoteString(leaf));

		return "{".concat(result).concat("}");
	}

	/**
	 * 教学组织机构
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getTreeNodeJson(TbXxzyJxzzjg obj) {
		// Boolean leaf = userDao.sumChildById(obj.getDm()) > 0 ? false : true;
		Boolean leaf = obj.getSfyzjd().equals("0") ? false : true;
		String result = MessageFormat.format(
				"id:{0}, text:{1}, leaf:{2},cc:{3},cclx:{4},fjdId:{5}",
				Struts2Utils.quoteString(obj.getId()),
				Struts2Utils.quoteString(obj.getMc()),
				Struts2Utils.quoteString(leaf),
				Struts2Utils.quoteString(obj.getCc()),
				Struts2Utils.quoteString(obj.getCclx()),
				Struts2Utils.quoteString(obj.getFjdId()));

		return "{".concat(result).concat("}");
	}

	/**
	 * 获取组织机构子节点记录转化为树形JSON串
	 * 
	 * @param treeList
	 * @param userPermissList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private MenuTree getTreeNodeJson(MenuTree tree, String bmId)
			throws Exception {
		// 根据当前节点作为父节点，查询子节点
		List nodeChildList = userDao.getZzjgTreeChild(bmId);
		// 判断当前节点下是否存在子节点
		if (nodeChildList != null && nodeChildList.size() > 0) {
			for (int i = 0; i < nodeChildList.size(); i++) {
				JSONArray obj = JSONArray.fromObject(nodeChildList.get(i));
				MenuNode node = new MenuNode(obj.getLong(0), obj.getLong(1),
						false, obj.getString(2), (obj.getLong(3) == 0 ? false
								: true), obj.getLong(4), true);
				tree.addNode(node);
				getTreeNodeJson(tree, String.valueOf(node.getId()));
			}
		}
		return tree;
	}
	//----------------------------------------------------------------2013-05-24-------------------------------------------------
	
	//查询字段
	private String sqlFields="id,groupPermissId,rylbId,username,ztId,bmId,loginName";
	//对应实体字段
	private String entityFields ="id,groupPermiss,rylbId,username,ztId,bmId,loginName";
	/**
	 * 
	 * 功能说明：保存用户信息的方法
	 * 
	 * @param params
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @throws Exception
	 * @DATE:2012-6-12 @TIME: 下午11:39:29
	 */
	public String saveUserInfo(String params) throws Exception {
		//定义用户对象
		TsUser tsUser = null;
		//将查询字段和实体字段转化为map
		Map map =JsonsUtils.mappingEntityField(sqlFields, entityFields);
		//把传入参数转化为用户实体的对象
		tsUser = (TsUser) JsonsUtils.stringToBean(params, map, TsUser.class);
		//传入的参数转化为Object对象
		JSONObject json = JSONObject.fromObject(params);
		//设置当前用户的部门
		tsUser.setBmId(json.getString("bmId").equals("") ? 0 : Long
				.valueOf(json.getString("bmId")));
		//加入人员类别为管理员
		Long rylbId =baseDao.getBzdmIdByDm("XXDM-RYLB", "4");
		//部门ID
		tsUser.setPassword(BidCreate.Encrypt("1").toUpperCase());
		tsUser.setRylbId(rylbId);
		
		//如果角色ID为多个情况下需要
//		List roleIdList = json.getJSONArray("jsId");
		JSONArray roleArr = JSONArray.fromObject(json.getString("jsId"));
		String roleIds = "";
		//遍历角色ID
		/*for(int i = 0;i<roleIdList.size();i++){
			roleIds =roleIds.concat(roleIdList.get(i).toString());
			if(i != roleIdList.size()-1){
				roleIds =roleIds.concat(",");
			}
		}*/
		for(int i=0;i<roleArr.size();i++){
			roleIds.concat(roleArr.get(i).toString());
			if((i+1)!=roleArr.size()){
				roleIds.concat(",");
			}
		}
//		roleIds = roleId;
		String permissBmIds = json.containsKey("permissBmIds")?json.getString("permissBmIds"):"";
		//返回的信息
		String info = "用户保存成功！";
		
		boolean succFlag = true;
		try {
			if (userDao.checkUserNameIsExists(null,
					tsUser.getUsername())) {
				info = "保存的用户名：" + tsUser.getUsername() + "已经存在!";
				succFlag =false;
			} else {
				tsUser = userDao.addTsUser(tsUser);
//				String roldId =baseDao.getRoleId(userId, jslxId);
				UserInfo userInfo = UserPermissionUtil.getUserInfo();
				userDao.saveUserRelaRole(tsUser.getId(), roleIds,userInfo.getId());
			}
			//增加或删除组管理员权限
			addOrDeleteMenuPermiss(tsUser);
			//增加或修改当前用户数据权限
			addDataPermiss(tsUser,permissBmIds);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
		return SysConstants.successInfo(succFlag, info);
	}
	/**
	 * 更新用户信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateUserInfo(String params) throws Exception {
		//定义用户对象
		TsUser tsUser = null;
		JSONObject json = JSONObject.fromObject(params);
		//用户ID
		if(json.containsKey("id")){
			tsUser = userDao.getUserById(json.getLong("id"));
		}
		//状态ID
		if(json.containsKey("ztId")){
			tsUser.setZtId(json.getLong("ztId"));
		}
		//组权限
		if(json.containsKey("groupPermissId")){
			tsUser.setGroupPermiss(json.getInt("groupPermissId")== 0 ? false:true);
		}
		/*//将查询字段和实体字段转化为map
		Map map =JsonsUtils.mappingEntityField(sqlFields, entityFields);
		//把传入参数转化为用户实体的对象
		tsUser = (TsUser) JsonsUtils.stringToBean(params, map, TsUser.class);*/
		//传入的参数转化为Object对象
//		JSONObject json = JSONObject.fromObject(params);
		//设置当前用户的部门
		tsUser.setBmId(json.containsKey("bmId") ? Long
				.valueOf(json.getString("bmId")) : null);

		//部门ID
//		tsUser.setPassword(BidCreate.Encrypt("1").toUpperCase());
		
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		//如果角色ID为多个情况下需要
		List roleIdList = json.getJSONArray("jsId");
		String jslxIds = "";
		String jsIds ="";
		if(!roleIdList.isEmpty()){
			//遍历角色ID
			for(int i = 0;i<roleIdList.size();i++){
				jslxIds =jslxIds.concat(roleIdList.get(i).toString());
				if(i != roleIdList.size()-1){
					jslxIds =jslxIds.concat(",");
				}
			}
//		Long jslxId = json.getLong("jsId");
			
			List tsJsList = baseDao.getRoleIds(userInfo.getId(), jslxIds);
			for(int i = 0;i<tsJsList.size();i++){
				TsJs js = (TsJs) tsJsList.get(i);
				jsIds = jsIds.concat(js.getId().toString());
				if(i+1 != tsJsList.size()){
					jsIds = jsIds.concat(",");
				}
			}
			
		}
		String permissBmIds = json.containsKey("zzjgId")?json.getString("zzjgId"):"";
		//获取数据权限信息
		boolean successFlag =true;
		String info = "";
		try{
			//根据ID查询用户信息
			TsUser hasTsUses = userDao.getUserById(tsUser.getId());
			//判断数据库中是否存在用户信息
			if(hasTsUses == null){
				info = tsUser.getUsername()+",用户信息不存在";
				successFlag = false;
				return SysConstants.successInfo(successFlag, info);
			}else{
				
				//更新用户信息
				tsUser =userDao.updateTsUser(tsUser, jsIds);
				//增加或删除用户的菜单管理权限
				addOrDeleteMenuPermiss(tsUser);
				//增加或修改数据权限
				addDataPermiss(tsUser, permissBmIds);
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
		//返回成功或失败的信息
		return SysConstants.successInfo(successFlag, info);
	}
	/**
	 * 增加或删除用户和角色菜单管理权限
	 * @param cdzys
	 * @param tsUser
	 * @throws Exception
	 */
	private void addOrDeleteMenuPermiss(TsUser tsUser)throws Exception{
		//权限管理、角色管理、用户管理的ID
		String cdzys = "1000000000015895,1000000000015896,1000000000015897";
		// 增加或删除角色权限
		if (tsUser.getGroupPermiss() == true) {
			List<TsUserCdzy> list = roleDao.queryUserCdzy(cdzys,
					tsUser.getId());
			if (list != null && list.size() > 0) {
				roleDao.deleteUserCdzys(list);
			}
			saveUserJsMenuPermiss(cdzys, tsUser.getId());
		} else {
			List<TsUserCdzy> list = roleDao.queryUserCdzy(cdzys,
					tsUser.getId());
			if (list != null && list.size() > 0) {
				roleDao.deleteUserCdzys(list);
			}
		}
	}
	/**
	 * 增加或修改数据权限
	 * @param tsUser
	 * @param permissBmIds　
	 * @throws Exception
	 */
	public void addDataPermiss(TsUser tsUser,String permissBmIds) throws Exception{
		// 增加数据权限
		//查询当前用户的数据权限
		List<TsUserDataPermiss> hasDataPermissList = userDao
				.queryDataPermissEntity(tsUser.getId());
		//如果存在数据权限删除
		if (hasDataPermissList != null && hasDataPermissList.size() > 0) { // 1代表删除，0代表新增
			userDao.deleteDataPermiss(hasDataPermissList);
		}
		//数据权限ID
		if (permissBmIds.length() > 0) {
			String[] dataPermissArray = permissBmIds.split(",");
			for (int i = 0; i < dataPermissArray.length; i++) {
				// 查询当用户是否存在此数据权限
				TsUserDataPermiss dataPermiss = new TsUserDataPermiss();
				dataPermiss.setUserId(tsUser.getId());
				dataPermiss.setZzjgId(Long.valueOf(dataPermissArray[i]));
				dataPermiss.setZwId(0L);
				Long zzjglb = userDao.hasTbJxzzjg(Long
						.valueOf(dataPermissArray[i]));
				dataPermiss.setZzjglb(zzjglb);
				userDao.saveUserPermiss(dataPermiss);
			}
		}
	}
	/**
	 * 批量删除记录
	 * @param params
	 * @return
	 */
	@Override
	public String deleteTsuser(String params) {
		JSONObject json = JSONObject.fromObject(params);
		//获取删除的IDs
		String ids = json.getString("ids");
		//查询要删除的记录
		List<TsUser> list = userDao.queryTsUserList(ids);
		//判断要删除的记录是否存在
		if(list.isEmpty()){
			return SysConstants.successInfo(false, "对不起,您要删除的数据已不存在！");
		}
		try {
			for(TsUser tsUser:list){
				//删除数据权限
				addDataPermiss(tsUser,"");
				//删除菜单管理权限
				addOrDeleteMenuPermiss(tsUser);
				//删除用户信息
				baseDao.delete(tsUser);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 从session中获取用户信息返回到前台
	 * @return userInfo
	 */
	@Override
	public String getUserInfo(String params){
		//获取session中用户的信息
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		return Struts2Utils.bean2json(userInfo);
	}
	//--------------------------------------------------------------------------------------------------------------------
	private void saveUserJsMenuPermiss(String cdzys, Long userId)
			throws Exception {
		String[] cdzyIds = cdzys.split(",");
		TsUserCdzy cd1 = new TsUserCdzy();
		cd1.setCdzyId(Long.valueOf(cdzyIds[0]));
		cd1.setUserId(userId);
		roleDao.saveUserMenuPermiss(cd1);
		TsUserCdzy cd2 = new TsUserCdzy();
		cd2.setCdzyId(Long.valueOf(cdzyIds[1]));
		cd2.setUserId(userId);
		roleDao.saveUserMenuPermiss(cd2);
		TsUserCdzy cd3 = new TsUserCdzy();
		cd3.setCdzyId(Long.valueOf(cdzyIds[2]));
		cd3.setUserId(userId);
		roleDao.saveUserMenuPermiss(cd3);
	}

	/*
	 * @see
	 * com.jhkj.mosdc.permission.service.UserInfoService#queryUserRoles(java
	 * .lang.Long)
	 */
	@Override
	public String queryUserRoles(String params) {
		StringBuffer jsonStr = null;
		// TODO:从session里面取
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		Long userId = userInfo.getId();
		try {
			List<Object[]> Objs = userDao.queryUserRoles(userId);

			/*********************************/
			jsonStr = new StringBuffer("");

			jsonStr.append("[");
			boolean isFirst = true;
			for (Object[] obj : Objs) {
				if (isFirst) {
					isFirst = false;
				} else {
					jsonStr.append(",");
				}
				jsonStr.append("{")
						.append("id:")
						.append(obj[0] != null ? Struts2Utils
								.quoteString(String.valueOf(obj[0])) : "''")
						.append(",name:")
						.append(obj[1] != null ? Struts2Utils
								.quoteString(String.valueOf(obj[1])) : "''")
						.append("}");
			}
			jsonStr.append("]");
			/*********************************/

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr.toString();
	}

	/*
	 * @see
	 * com.jhkj.mosdc.permission.service.UserInfoService#(java
	 * .lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryTsUserList(String params) throws Exception {
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		JSONObject  jsonObj = JSONObject.fromObject(params);
		String bmId ="";
		//判断节点ID是否存在
		if(jsonObj.containsKey("bmId")){
			bmId = (jsonObj.get("bmId") == null || jsonObj.get("bmId").toString().equals("")) ? ""
					: jsonObj.getString("bmId");
		}else if(jsonObj.containsKey("node")){
			bmId = (jsonObj.get("node") == null || jsonObj.get("node").toString().equals("")) ? ""
					: jsonObj.get("node").toString();
		}
		if(jsonObj.containsKey("bmId")){
			jsonObj.remove("bmId");
		}else if(jsonObj.containsKey("node")){
			jsonObj.remove("node");
		}
		
		Map paramMap = SqlParamsChange.getSQLParams(jsonObj,true);
		//角色IDs
		String roleIds = userInfo.getRoleIds();
		String[] roleIdsArray = roleIds.split(",");
		// 判断当前用户是否为管理员角色
		boolean flag = false;
		Long jslxId = baseDao.getBzdmIdByDm("XXDM-QXJSLX", "1");
		String bmIds = "";
		if (roleIdsArray.length > 0) {
			for (int j = 0; j < roleIdsArray.length; j++) {
				if (roleIdsArray[j].endsWith(jslxId.toString())) {
					flag = true;
					bmId = String.valueOf(userInfo.getBmId());
					break;
				}
			}
		}
		//管理员
//		bmId = userInfo.getBmId().toString();
		if(flag == true){
			if(bmId.equals("0") || bmId.equals("")){
				bmIds = bmId;
			}else{
				bmIds = getBmIds(bmId, bmIds);
				bmIds = bmIds.concat(bmId).concat(",");
				bmIds =userInfo.getPermissJxzzIds()==null?bmIds:bmIds.concat(userInfo.getPermissJxzzIds()).concat(",");
				bmIds =userInfo.getPermissXzzzIds()==null?bmIds:bmIds.concat(userInfo.getPermissXzzzIds()).concat(",");
			}
		}
		/*if ((!bmId.equals("0") && !bmId.equals("")) && flag == true) {
			bmIds = bmIds.concat(bmId+",");
			bmIds = getBmIds(bmId, bmIds);
		}*/
		// 判断当前用户 非管理员角色，并且前台传来的部门ID为0，取当前用户所在部门和当前部门以后所有子部门ID
//		if (flag == true && (bmId.equals("0") || bmId.equals(""))) {
		// 判断当前部门下面是否有子部门，如果存在获取子节点的ID
		if (bmIds.length() > 0) {
			bmId =bmIds = bmIds.substring(0, bmIds.length() - 1);
//			bmId = bmIds;

		}/* else { // 如果当前节点不存在子节点，把当前用户的部门作为顶级节点来查询
			bmId = String.valueOf(userInfo.getBmId());
		}*/
//		}

		if (!bmIds.equals("") && !bmId.equals("")) {
			String[] bmIdsArray = bmIds.split(",");
			boolean hasBmId = false;
			for (int i = 0; i < bmIdsArray.length; i++) {
				if (bmId.equals(bmIdsArray[i])) {
					hasBmId = true;
					break;
				}
			}
			if (hasBmId == false && flag == false) {
				return Struts2Utils
						.string2json("{success:true,data:[],count:0}");
			}

		}
		
		String sql="select t.id as \"id\","+
			       " t.login_name as \"loginName\","+
			       " t.zg_id    as \"zgId\", "+
			       " (case when zg.xm is null then xs.xm else zg.xm end)  as \"username\","+
			       " t.bm_id    as \"bmId\","+
			       " (nvl(nvl(bm.mc,z.mc),bj.mc))  as \"bmMc\","+
			       " t.zt_id    as \"ztId\","+
			       " c.mc       as \"ztMc\","+
			       " t.rylb_id  as \"rylbId\","+
			       " tc.mc      as \"rylbMc\"," +
			       " ''      as \"zzjgId\"," +
			       " ''      as \"zzjgMc\"," +
			       "(case when t.grouppermiss = '0' then '无'　 else '有' end) as \"groupPermiss\"," +
			       " t.grouppermiss as \"groupPermissId\" " +
			       " from TS_USER t"+
			       " left join tc_xxbzdmjg tc"+
			       "  on t.rylb_id = tc.id"+
			       "  left join tc_xxbzdmjg c"+
			       "  on t.zt_id = c.id"+
			       " left join tb_jzgxx zg"+
			       "  on t.zg_id = zg.id"+
			       "  left join tb_jxzzjg bm"+
			       " on t.bm_id = bm.id"+
			       " left join tb_xjda_xjxx xs"+
			       " on t.zg_id = xs.id "+
			       " left join tb_xzzzjg z on t.bm_id = z.id left join tb_xxzy_bjzbjzzjg bj on t.bm_id = bj.id  where 1=1 ";
		
		if(!bmId.equals("")){
			sql=sql+ " and t.bm_id in ("+bmId+") ";
		}
		System.out.println("sql:"+sql);
		paramMap.put("order", " order by \"bmId\" desc,\"loginName\" asc,\"zgId\" ");
		Map retMap =  baseDao.queryTableContentBySql("select rw.* from ("+sql+") rw where 1=1 ", paramMap);
		List records =(List) retMap.get("queryList");
		Map map = null;
		List<Object[]> Objs = null;
		StringBuffer roleNamesStr = null;

		for (Object object : records) {
			map = (Map) object;
			Long userId = Long.valueOf(map.get("id").toString());
			Objs = userDao.queryUserRolesByUserId(userId);
			roleNamesStr = new StringBuffer("");
			List dataPermissList = getUserDataPermiss(userId);
			if (dataPermissList != null && dataPermissList.size() > 0) {
				map.put("zzjgId", dataPermissList.get(0));
				map.put("zzjgMc", dataPermissList.get(1));
			}
			boolean isFirst = true;
			List<String> roleIdsStr = new ArrayList<String>();
			for (Object[] obj : Objs) {

				if (isFirst) {
					isFirst = false;
				} else {
					roleNamesStr.append(",");
				}
				roleIdsStr.add(obj[1] != null ? String.valueOf(obj[1]) : "");
				roleNamesStr.append(obj[2] != null ? String.valueOf(obj[2])
						: "");

			}

			map.put("jsId", roleIdsStr.equals("") ? "" : roleIdsStr.toString());
			map.put("jsMc",
					roleNamesStr.equals("") ? "" : roleNamesStr.toString());

		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("count", retMap.get("count"));
		mp.put("data", records);
		return Struts2Utils.map2json(mp);

	}

	@SuppressWarnings("unused")
	private String getBmIds(String bmId, String bmIds)
			throws Exception {
		MenuNode node = (MenuNode) jxzzjgMap.get(bmId);
		List<MenuNode> nodeList =null;
		if(node != null){
			nodeList = node.children;
		}
		if(nodeList != null && nodeList.size()>0){
			for(int i = 0;i<nodeList.size();i++){
				MenuNode nowNode = nodeList.get(i);
				//组装部门
				bmIds = bmIds.concat(nowNode.getId().toString()+",");
				//判断当前i是否等于nodeList.size();
				getBmIds(nowNode.getId().toString(), bmIds);
			}
		}
		// 非管理员角色只能看到当前所在部门下的用户
		/*while (true) {
			List list = userDao.getZzjgTreeChild(String.valueOf(userBmId));
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					JSONArray obj = JSONArray.fromObject(list.get(i));
					bmIds = bmIds.concat(obj.getString(0).toString() + ",");
					getBmIds(bmId, obj.getString(0), bmIds);
				}
			} else {
				break;
			}
		}*/
		return bmIds;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<String> getUserDataPermiss(Long userId) throws Exception {
		List list = userDao.getDataPermiss(userId);
		StringBuffer zzjgIds = new StringBuffer();
		StringBuffer zzjgmcs = new StringBuffer();
		List retList = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				JSONObject objectMapper = JSONObject.fromObject(list.get(i));
				zzjgIds.append(objectMapper.getString("ZZJGID") + ",");
				zzjgmcs.append(objectMapper.getString("ZZJGMC") + ",");
			}
			retList.add(zzjgIds.toString().substring(0, zzjgIds.length() - 1));
			retList.add(zzjgmcs.toString().substring(0, zzjgmcs.length() - 1));
		}
		return retList;
	}

	/*
	 * @see
	 * com.jhkj.mosdc.permission.service.UserInfoService#resetPassword(java.
	 * lang.String)
	 */
	@Override
	public String updateResetPassword(String params) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		String tmpPassword = "123456";//userDao.queryDefaultPassword("cshmm");
//		if (tmpPassword == null) {
//			tmpPassword = "1";
//		}
		// 密码赋予初值
		String defaultPassword = BidCreate.Encrypt(tmpPassword).toUpperCase();
		JSONObject json = JSONObject.fromObject(params);
		// 得到传过来的用户标识
		Long userId = Long.valueOf(json.getString("ids"));
		// 重置密码
		userDao.resetPassword(userId, defaultPassword);
		mp.put("success", true);
		mp.put("info", "密码重置成功！");
		return Struts2Utils.map2json(mp);
	}

	/***
	 * 修改密码
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String updatePassword(String params) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(params);
		// 密码赋予初值
		String oldpassword = BidCreate.Encrypt(json.getString("oldpassword"))
				.toUpperCase();
		String newpassword = BidCreate.Encrypt(json.getString("newpassword"))
				.toUpperCase();

		// 得到传过来的用户标识
		Long userId = Long.valueOf(UserPermissionUtil.getUserInfo().getId());
		// 检验密码的正确性
		boolean b = userDao.queryAndCheckOldPassword(userId.toString(), oldpassword);
		if (b) {
			// 重置密码
			userDao.resetPassword(userId, newpassword);
			mp.put("success", true);
			mp.put("info", "密码修改成功！");
		}else{
			mp.put("success", false);
			mp.put("info", "密码修改失败！");
		}
		return Struts2Utils.map2json(mp);
	}

	@Override
	public String updateResetUserZt(String params) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();

		JSONObject json = JSONObject.fromObject(params);
		// 得到传过来的用户标识
		Long userId = Long.valueOf(json.getString("ids"));
		// 重置密码
		userDao.updateResetUserZt(userId);
		mp.put("success", true);
		mp.put("info", "删除用户成功！");
		return Struts2Utils.map2json(mp);
	}

	@Override
	public String queryUserByType(String params) throws Exception {
		JSONObject jsonObj = JSONObject.fromObject(params);
		// 分派类别ID
		Long fplbId = Long.valueOf(jsonObj.getString("fplbId"));
		int start = Integer.valueOf(jsonObj.getString("start"));
		int limit = Integer.valueOf(jsonObj.getString("limit"));
		// 获取分派类别代码
		String fplbdm = baseDao.queryBmById(fplbId, "XXDM-FPLB");
		// 人员类别ID
		Long rylbId = -1L;
		Long groupId = -1L;
		// 分派类别(1)代表系统管理员 (2)代表老师
		if (fplbdm.equals("1")) {
			// 获取人员类别ID通过人员类别代码(4)系统管理 员
			rylbId = baseDao.queryIdByBm("4", "XXDM-RYLB");
			groupId = baseDao.queryIdByBm("1", "XXDM-SFBZ");
		} else if (fplbdm.equals("2")) {
			// 人员类别为(1)教师的人员类别ID
			rylbId = baseDao.queryIdByBm("1", "XXDM-RYLB");
		}

		Long jxzzjgId = (jsonObj.getString("jxzzjgId") == null || jsonObj
				.getString("jxzzjgId").equals("")) ? null : Long
				.valueOf(jsonObj.getString("jxzzjgId"));
		PageParam pageParam = new PageParam(start, limit);
		Page page = userDao.queryTsUserList(pageParam, jxzzjgId, rylbId,groupId);

		ListOrderedMap map = null;
		List<Object[]> Objs = null;
		StringBuffer roleIdsStr = null;
		StringBuffer roleNamesStr = null;

		for (Object object : page.getRows()) {
			map = (ListOrderedMap) object;
			Long userId = Long.valueOf(map.get("id").toString());
			Objs = userDao.queryUserRolesByUserId(userId);
			roleIdsStr = new StringBuffer("");
			roleNamesStr = new StringBuffer("");
			boolean isFirst = true;
			for (Object[] obj : Objs) {

				if (isFirst) {
					isFirst = false;
				} else {
					roleIdsStr.append(",");
					roleNamesStr.append(",");
				}
				roleIdsStr.append(obj[0] != null ? String.valueOf(obj[0]) : "");
				roleNamesStr.append(obj[1] != null ? String.valueOf(obj[1])
						: "");

			}
			map.put("JSID", roleIdsStr.equals("") ? "" : roleIdsStr.toString());
			map.put("JSMC",
					roleNamesStr.equals("") ? "" : roleNamesStr.toString());
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("count", page.getTotal());
		mp.put("data", page.getRows());
		return Struts2Utils.map2json(mp);
	}
	@Override
	public boolean saveTsUser(Long id,Long rylbId) throws Exception{
		//判断传入的参数是否为空
		if(id == null || rylbId == null){
			return false;
		}
		//根据人员类别ID获取代码
		String dm = baseDao.queryBmById(rylbId, "XXDM-RYLB");
		//根据代码获取用户状态ID
		Long ztId = baseDao.getBzdmIdByDm("XXDM-USERZT", "1");
		//判断在TsUser中是否存在当前用户ZG_ID
		int count = baseDao.queryEntityCount("TsUser", " zgId = "+id);
		//实例化用户对象
		TsUser tsUser = new TsUser();
		//设置密码
		tsUser.setPassword(BidCreate.Encrypt("1").toUpperCase());
		//设置人员类别ID
		tsUser.setRylbId(rylbId);
		//设置用户状态
		tsUser.setZtId(ztId);
		//设置用户对应的对应表ID
		tsUser.setZgId(id);
		//判断是否在用户表中存在记录存在，返回false;
		if(count>0){
			return false;
		}
		TsUserJs tsUserJs = new TsUserJs();
		//判断人员类别代码是否为3代表学生
		if(dm.equals("3")){
			//获取学生信息
			TbXjdaXjxx tbXsjbxx = (TbXjdaXjxx) baseDao.get(TbXjdaXjxx.class, id);
			//判断当前学生是否存在，不存在直接返回false;
			if(tbXsjbxx == null){
				return false;
			}
			//把当前学生的班级ID存入到部门ID
			tsUser.setBmId(tbXsjbxx.getBjId());
			//把学号作为用户名存入用户信息
			tsUser.setUsername(tbXsjbxx.getXh());
			tsUserJs.setId(baseDao.getId());
			tsUserJs.setUserId(tsUser.getId());
			tsUserJs.setJsId(new Long("1000000000000302"));
			//保存用户信息
			userDao.saveTsUser(tsUser);
			//判断人员类别不为4(管理员)并且不为3(学生)时查询教职工信息表
		}else if(!dm.equals("4") && !dm.equals("3")){
			//查询教职工信息
			TbJzgxx tbJzgxx = (TbJzgxx) baseDao.get(TbJzgxx.class, id);
			//判断当前的教职工信息是否存在,不存在直接返回false
			if(tbJzgxx == null){
				return false;
			}
			//判断当前教职工的科室是否存在，不存在把院系ID存入到用户信息的部门字段
			tsUser.setBmId(tbJzgxx.getKsId() == null ? tbJzgxx.getYxId():tbJzgxx.getKsId());
			//把职工号作为用户名存入到用户信息
			tsUser.setUsername(tbJzgxx.getZgh());
			tsUserJs.setId(baseDao.getId());
			tsUserJs.setUserId(tsUser.getId());
			tsUserJs.setJsId(new Long("1000000000000301"));
			//保存用户信息
			userDao.saveTsUser(tsUser);
		}
		return true;
	}

	@Override
	public UserInfo queryUserByLoginName(String loginName) throws Throwable {
		// TODO Auto-generated method stub\
		TsUser user = new TsUser();
		user.setLoginName(loginName);
		TsUser getUser = (TsUser) baseDao.loadFirstEqual(user);
		String password = getUser.getPassword().toUpperCase();
		String str = "{loginName : '"+loginName +"',password:'"+password+"',isOuterSystem : true}";
		return queryUser(str);
	}

	
}
