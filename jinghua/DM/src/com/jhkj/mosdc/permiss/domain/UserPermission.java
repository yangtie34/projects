package com.jhkj.mosdc.permiss.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.util.Assert;

import com.jhkj.mosdc.framework.po.TcXxbzdmjg;
import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.newoutput.util.DateUtils;
import com.jhkj.mosdc.permiss.po.TpMenu;
import com.jhkj.mosdc.permiss.po.TpRole;
import com.jhkj.mosdc.permiss.po.TpUser;
import com.jhkj.mosdc.permiss.po.TpUsergroup;
import com.jhkj.mosdc.permiss.po.TpUsergroupDatapermiss;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserdatapermiss;
import com.jhkj.mosdc.permission.po.MenuNode;

/**
 * 用户权限模型
 * 
 * @author Administrator
 * 
 */
public class UserPermission {
	private Base base;
	private User user;// 用户信息
	private String roleIds;// 角色ID字符串，以逗号为分隔符
	private String roleLxIds;// 角色类型ID，以逗号为分隔符
	private List<UserGroupPermission> ugpList = new ArrayList<UserGroupPermission>();// 用户组权限信息集合
	private Node root = new Node();// 菜单树根节点,已经完成所有菜单挂接
	// 代理权限信息
	private User deProxyUser;// 被代理用户信息
	private Node deProxyUserRoot = new Node();// 被代理用户权限根节点,ID默认为用户（System.currentTimeMilions()）,其父节点需要被代理用户设定
	// 数据权限树，通过挂接用户数据权限，以及教学组织结构树以及班级信息生成
	private JxzzjgDataTreePermission jdtp;

	/**
	 * 完成用户的初始化工作
	 */
	public void initUserPermission(User user) {
		this.user = user;
		this.clear();// 开发模式设定
		this.initUserUserGroup();// 初始化用户-用户组信息

	}

	/**
	 * 初始化代理用户权限信息
	 * 
	 * @param proxyUser
	 *            我下放权限给与该用户
	 */
	public void initProxyUserPermiss(User proxyUser) {
		this.deProxyUser = proxyUser;
		this.initProxyMenus();
	}

	private void clear() {
		// TODO Auto-generated method stub
		ugpList = new ArrayList<UserGroupPermission>();
		// proxyUserPermission = new ArrayList<UserPermission>();
		root = new Node();
	}

	/**
	 * 初始化用户组权限---目前暂时不针对代理数据权限
	 */
	@SuppressWarnings("unchecked")
	private void initUserUserGroup() {
		// TODO Auto-generated method stub
		// 初始化用户自身数据权限
		Session session = base.getSession();
		//根据菜单查用户组sql
		String sql = "select distinct usergroup_id from TP_USERGROUP_MENU_USER tumu where tumu.user_id="
				+ user.getUserId();
		//根据角色查用户组sql
		String rsql = "select distinct usergroup_id from TP_USER_ROLE tur where tur.user_id = " + user.getUserId();
		List<Number> groupIds = session.createSQLQuery(sql).list();
		groupIds.addAll(session.createSQLQuery(rsql).list());
		
		session.close();
		for (Number usergroupId : groupIds) {
			if (usergroupId == null)
				continue;
			UserGroupPermission ugp = new UserGroupPermission(user.getUserId(),
					usergroupId.longValue(), base);
			ugpList.add(ugp);
		}
		// 针对顶级组权限处理
		UserGroupPermission ugp = new UserGroupPermission(user.getUserId(),
				null, base);
		ugpList.add(ugp);

		this.initMenuPermiss();
		this.initRoleIds();
	}

	/**
	 * 初始化ID字符串
	 */
	private void initRoleIds() {
		// TODO Auto-generated method stub
		List<Long> list = new ArrayList<Long>();
		Set<Long> roleLxSet = new HashSet<Long>();
		for (UserGroupPermission ugp : ugpList) {
			for (TpRole r : ugp.getRoles()) {
				list.add(r.getId());
				if(r.getJslxId() != null){
					roleLxSet.add(r.getJslxId());
				}
			}
		}

		roleIds = StringUtils.join(list, ",");
		roleLxIds = StringUtils.join(Arrays.asList(roleLxSet.toArray()), ",");
	}

	/**
	 * 初始化用户代理权限
	 */
	@SuppressWarnings("unchecked")
	private void initProxyMenus() {
		// 清理工作
		deProxyUserRoot = new Node();

		//
		String currentDate = DateUtils.getCurrentDate_DayString();// 当前日期
		Long deProxyUserId = deProxyUser.getUserId();
		String sql = "select distinct tm.* from TP_USER_MENU_PROXY tump"
				+ " inner join TP_USER_PROXY_MENU tupm on tump.id = tupm.xf_id"
				+ " inner join TP_MENU tm on tupm.menu_id = tm.id and tm.leaf = 1 and tump.startdate<='"
				+ currentDate + "' and tump.enddate>='" + currentDate
				+ "' and tump.OWNER_USER_ID = " + user.getUserId()
				+ " and tump.XF_USER_ID = " + deProxyUserId;
		List<TpMenu> list = base.getEntityListBySql(TpMenu.class, sql);
		// 挂接菜单节点(目的是为了把菜单下的功能和内容权限做挂接)
		Map<Long, Node> hash = Node.translateNodeHashForTpMenu(list);
		Node.generateNodeTree(hash);

		// 设定被代理的用户的菜单的根节点文本为用户名，ID为System.currentTimeMillis()
		Long proxyRootId = System.currentTimeMillis();
		deProxyUserRoot.setText(user.getUsername());
		deProxyUserRoot.setId(proxyRootId);

		Set<Entry<Long, Node>> set = hash.entrySet();
		Iterator<Entry<Long, Node>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<Long, Node> entry = iterator.next();
			Node node = entry.getValue();
			if (node.getLeaf()) {
				node.setProxyMenuId(node.getId());
				node.setProxyUserId(user.getUserId());
				node.setPid(proxyRootId);
				node.setId(++proxyRootId);
				deProxyUserRoot.getChildren().add(node);
			}
		}

	}

	private void initMenuPermiss() {
		// TODO Auto-generated method stub
		List<TpMenu> list = new ArrayList<TpMenu>();
		// 初始化用户自身权限
		for (UserGroupPermission ugp : ugpList) {
			list.addAll(ugp.getMenuList());
		}
		Map<Long, Node> hash = Node.translateNodeHashForTpMenu(list);
		
		Map<Long, Node> h = getOwnerLeafHash(hash, list);
		root.setId(0l);
		h.put(0l, root);
		Node.generateNodeTree(h);
		Node.sort(h);
	}
	private Map<Long,Node> getOwnerLeafHash(Map<Long, Node> hash,List<TpMenu> list){
		Map<Long,Node> h = new HashMap<Long, Node>();
		for(TpMenu tm : list){
			if(!"1".equals(tm.getCdssfl())){
				Node node = hash.get(tm.getId());
				h.put(node.getId(), node);
				Node pnode = hash.get(tm.getParentId());
				while(pnode!=null){
					h.put(pnode.getId(), pnode);
					pnode = hash.get(pnode.getPid());
				}
			}
		}
		return h;
	}
	/**
	 * 获取菜单树
	 * 
	 * @return
	 */
	public Node getMenuTree() {
		return root;
	}

	/**
	 * 获取角色ID字符串集合
	 * 
	 * @return
	 */
	public String getRoleIds() {
		return roleIds;
	}

	/**
	 * 获取角色类型ID集合
	 * 
	 * @return
	 */
	public String getRoleLxIds() {
		return roleLxIds;
	}

	/**
	 * 以SQL形式获取菜单对应的教学组织结构数据权限
	 * 
	 * @param menuId
	 * @return
	 */
	public String getSqlMenuDataPermiss(Long menuId) {
		// 目前没有走菜单-单独的数据权限

		// 获取用户的数据权限
		List<String> list = new ArrayList<String>();
		for (UserGroupPermission ugp : ugpList) {
			String ret = ugp.getSqlMenuDataPermiss(menuId);
			if (ret != null && StringUtils.trimAllWhitespace(ret).length() > 0) {
				list.add(ret);
			}
		}
		return StringUtils.join(list, " union ");
	}
	/**
	 * 以SQL形式获取菜单对应的行政组织结构数据权限
	 * 
	 * @param menuId
	 * @return
	 */
	public String getSqlMenuXzDataPermiss(Long menuId) {

		// 获取用户的行政数据权限
		List<String> list = new ArrayList<String>();
		for (UserGroupPermission ugp : ugpList) {
			String ret = ugp.getSqlMenuXzDataPermiss(menuId);
			if (ret != null && StringUtils.trimAllWhitespace(ret).length() > 0) {
				list.add(ret);
			}
		}
		return StringUtils.join(list, " union ");
	}
	/**
	 * 以HQL形式获取菜单对应的数据权限
	 * 
	 * @param menuId
	 * @return
	 */
	public String getHqlMenuDataPermiss(Long menuId) {
		// 目前没有走菜单-单独的数据权限
		String bjHql = "select t.bjId from TpUsergroupUserdatapermiss t,TbXxzyBjxxb bj where t.bjId = bj.id and bj.sfky = 1 and ";
		String zzjgHql = "select bj.id from TpUsergroupUserdatapermissLd tul,TbJxzzjg zzjg,TbJxzzjg zzjg1,TbXxzyBjxxb bj "
				+ " where tul.jxzzjgId = zzjg.id and zzjg.qxm = SUBSTRING(zzjg1.qxm,0,LENGTH(zzjg.qxm))  and zzjg1.id = bj.fjdId and  ";
		// 获取用户的数据权限
		List<String> list = new ArrayList<String>();
		
		for (UserGroupPermission ugp : ugpList) {
			String ret = ugp.getHqlMenuDataPermiss(menuId);
			if (ret != null && StringUtils.trimAllWhitespace(ret).length() > 0) {
				list.add(ret);
			}
		}
		
		if(ugpList.get(0).gdp.way){
			return zzjgHql + StringUtils.join(list, " or ");
		}else{
			return bjHql + StringUtils.join(list, " or ");
		}
		
	}
	/**
	 * 以HQL形式获取菜单对应的行政组织结构数据权限
	 * 
	 * @param menuId
	 * @return
	 */
	public String getHqlMenuXzDataPermiss(Long menuId) {
		// 目前没有走菜单-单独的数据权限
		String zzjgHql = "select jzg.id from TpUsergroupUserXzDatapermissLd tuxl,TbXzzzjg zzjg,TbXzzzjg zzjg1,TbJzgxx jzg "
				+ " where tuxl.xzzzjgId = zzjg.id and zzjg.qxm = SUBSTRING(zzjg1.qxm,0,LENGTH(zzjg.qxm))  and zzjg1.id = zg.ksId and";
		// 获取用户的数据权限
		List<String> list = new ArrayList<String>();
		
		for (UserGroupPermission ugp : ugpList) {
			String ret = ugp.getHqlMenuDataPermiss(menuId);
			if (ret != null && StringUtils.trimAllWhitespace(ret).length() > 0) {
				list.add(ret);
			}
		}
		
		return zzjgHql + StringUtils.join(list, " or ");
		
	}
	/**
	 * 以SQL的形式，根据人员ID、人员类型ID、菜单ID获取数据权限
	 * 
	 * @param ryId
	 * @param ryLxId
	 * @return
	 */
	public String getSqlMenuDataPermissByUserIdAndRyLxId(Long ryId, String dm,
			Long menuId) {
		// 获取当前人员类型ID
		TcXxbzdmjg tc = new TcXxbzdmjg();
		tc.setBzdm("XXDM-RYLB");
		tc.setDm(dm);
		tc = (TcXxbzdmjg) base.getEntity(tc);

		// 获取当前用户
		TpUser user = new TpUser();
		user.setRyId(ryId);
		user.setRylbId(tc.getId());
		user = (TpUser) base.getEntity(user);
		// 当前用户不能为空
		Assert.notNull(user);

		Long userId = user.getId();
		// 获取用户组权限
		// 获取用户拥有数据权限的用户组
		TpUsergroupUserdatapermiss tu = new TpUsergroupUserdatapermiss();
		tu.setUserId(userId);

		List<TpUsergroup> tuList = base .getEntityListBySql( TpUsergroup.class, "select distinct tu.* from TP_USERGROUP_USERDATAPERMISS t,Tp_Usergroup tu where t.usergroup_id=tu.id and  t.user_id=" + userId);
		List<UserGroupDataPermission> ugdList = new ArrayList<UserGroupDataPermission>();

		// 组装生成用户组权限信息
		for (TpUsergroup t : tuList) {
			UserGroupDataPermission udg = new UserGroupDataPermission(base,userId,
					t.getId());
			ugdList.add(udg);
		}
		UserGroupDataPermission udg = new UserGroupDataPermission(base,userId, null);
		ugdList.add(udg);

		List<String> list = new ArrayList<String>();
		for (UserGroupPermission ugp : ugpList) {
			String ret = ugp.getSqlMenuDataPermiss(menuId);
			if (ret != null && StringUtils.trimAllWhitespace(ret).length() > 0) {
				list.add(ret);
			}
		}

		return StringUtils.join(list, " union ");
	}

	/**
	 * 以SQL的形式，根据人员ID以及人员类型ID获取数据权限
	 * 
	 * @param ryId
	 * @param ryLxId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getHqlMenuDataPermissByUserIdAndRyLxId(Long ryId, String dm,
			Long menuId) {
		// 获取当前人员类型ID
		TcXxbzdmjg tc = new TcXxbzdmjg();
		tc.setBzdm("XXDM-RYLB");
		tc.setDm(dm);
		tc = (TcXxbzdmjg) base.getEntity(tc);

		TpUser user = new TpUser();
		user.setRyId(ryId);
		user.setRylbId(tc.getId());
		user = (TpUser) base.getEntity(user);

		Assert.notNull(user);

		Long userId = user.getId();
		// 获取用户拥有数据权限的用户组
		TpUsergroupUserdatapermiss tu = new TpUsergroupUserdatapermiss();
		tu.setUserId(userId);

		List<TpUsergroup> tuList = base
				.getEntityListBySql(
						TpUsergroup.class,
						"select distinct tu.* from TP_USERGROUP_USERDATAPERMISS t,Tp_Usergroup tu where t.usergroup_id=tu.id and  t.user_id="
								+ userId);
		List<UserGroupDataPermission> ugdList = new ArrayList<UserGroupDataPermission>();

		// 组装生成用户组权限信息
		for (TpUsergroup t : tuList) {
			UserGroupDataPermission udg = new UserGroupDataPermission(base,userId,
					t.getId());
			ugdList.add(udg);
		}
		UserGroupDataPermission udg = new UserGroupDataPermission(base,userId, null);
		ugdList.add(udg);
		// hql数据权限
		String baseHql = "select t.bjId from TpUsergroupUserdatapermiss t,TbXxzyBjxxb bj where t.bjId = bj.id and bj.sfky = 1 and";
		// 获取用户的数据权限
		List<String> list = new ArrayList<String>();
		for (UserGroupDataPermission ugp : ugdList) {
			String ret = ugp.getHqlMenuDataPermissCondition(menuId);
			if (ret != null && StringUtils.trimAllWhitespace(ret).length() > 0) {
				list.add(ret);
			}
		}
		return baseHql + StringUtils.join(list, " or ");
	}

	/**
	 * 生成完成教学组织结构树节点挂接以及班级挂接的-数据树
	 * 
	 * @return
	 */
	public TeachingOrganizationalStructureNode getJxzzjgDataTree() {
		return jdtp.getJxzzjgDataTree();
	}
	/**
	 * 生成教学组织结构数据树，并且移除班级节点
	 */
	public TeachingOrganizationalStructureNode getJxzzjgDataTreeWithOutBj() {
		return jdtp.getJxzzjgDataTreeWidthOutBj();
	}
	/**
	 * 生成完成教学组织结构树节点挂接以及班级挂接的-节点-ID集合
	 * 
	 * @return
	 */
	public String getJxzzjgIds() {
		return jdtp.getJxzzjgIds();
	}

	public User getUser() {
		return user;
	}

	public Node getProxyUserRoot() {
		return deProxyUserRoot;
	}

	public void setProxyUserRoot(Node proxyUserRoot) {
		this.deProxyUserRoot = proxyUserRoot;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public JxzzjgDataTreePermission getJdtp() {
		return jdtp;
	}

	public void setJdtp(JxzzjgDataTreePermission jdtp) {
		this.jdtp = jdtp;
	}

}
