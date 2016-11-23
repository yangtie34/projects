package com.jhkj.mosdc.permiss.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ApplicationAware;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jhkj.mosdc.framework.po.TbJzgxx;
import com.jhkj.mosdc.pano.po.TbXsJbxxb;
import com.jhkj.mosdc.permiss.po.TpUser;
import com.jhkj.mosdc.permiss.po.TpUserMenuProxy;
import com.jhkj.mosdc.permiss.util.CurrentUserMenuUtil;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.xggl.xjgl.po.TbXjdaXjxx;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;

public class User{
	private Base base;
	/************** 权限信息 ******************/
	private UserPermission userpermission;
	/**************** 登录信息 *****************/
	private List<User> proxyUsers = new ArrayList<User>();// 代理委托用户权限

	private Long id;// 职工ID/或学生ID

	protected Long userId;// TP_USER.ID

	private String username;// 用户名

	private String loginname;// 登录名

	private Long bmId;// 部门ID

	private String bmmc;// 部门名称

	/**************** 用户信息 *****************/

	private Long bjId;// 班级ID

	private Long yxId;// 院系ID

	private Long zyId;// 专业ID

	private String zgh;// 职工号

	private String xh;// 学号

	private Boolean student = false;// 是否为学生

	private Boolean teacher = false;// 是否为教师

	private Boolean others = false;// 其他

	/***************** 兼容处理 ************************/
	private Long rylbId;// 人员类别ID
	
	private ClassTeacherDataPermiss classTeacherDataPermiss;//班主任数据权限
	
	/**
	 * 清空
	 */
	public void clear() {
		proxyUsers = new ArrayList<User>();
	}

	/**
	 * 根据用户名-初始化用户
	 * 
	 * @param loginname
	 */
	public void initUser(String loginname) {
		// 获取当前用户
		TpUser user = new TpUser();
		user.setLoginname(loginname);
		user = (TpUser) base.getEntity(user);
		// 设置用户登录信息
		username = user.getUsername();
		this.loginname = user.getLoginname();// 登录名
		bmId = user.getBmId();// 部门ID
		bmmc = user.getBmmc();// 部门名称
		userId = user.getId();// 用户表ID
		id = user.getRyId();// 人员ID
		rylbId = user.getRylbId();// 人员类别ID
		// 获取教职工信息
		int dm = getRylb(user.getRylbId());
		if (dm == 1) {// 教职工
			TbJzgxx jzg = (TbJzgxx) base.getEntityById(id, TbJzgxx.class);
			if (jzg != null) {
				yxId = jzg.getYxId();
				zyId = jzg.getZyId();
				zgh = jzg.getZgh();
				teacher = true;
			}
		} else if (dm == 3) {// 学生
			TbXjdaXjxx xs = new TbXjdaXjxx();
			xs.setId(id);
			xs = (TbXjdaXjxx) base.getEntityById(id,TbXjdaXjxx.class);
			if (xs != null) {
				zyId = xs.getZyId();
				bjId = xs.getBjId();
				xh = xs.getXh();
				student = true;
			}
		}
		// 初始化班主任信息
		initClassTeacher();

		userpermission.initUserPermission(this);
	}

	/**
	 * 初始化代理用户权限
	 */
	@SuppressWarnings("unchecked")
	public void initProxyUser() {
		// TODO Auto-generated method stub
		TpUserMenuProxy tump = new TpUserMenuProxy();
		tump.setXfUserId(this.getUserId());
		Session session = base.getSession();
		SQLQuery query = session
				.createSQLQuery("select distinct OWNER_USER_ID as ownerId from TP_USER_MENU_PROXY t where t.XF_USER_ID="
						+ this.getUserId());
		List<Number> list = query.list();
		session.close();// 关闭会话

		for (Number num : list) {
			TpUser tpuser = (TpUser) base.getEntityById(num.longValue(),
					TpUser.class);
			User user = (User) base.getBean("userDomain");
			user.initProxyUser(tpuser);
			user.getUserpermission().initProxyUserPermiss(this);// 是使用用户的代理权限
			proxyUsers.add(user);
		}
	}
	/**
	 * 根据用户对象初始化代理用户
	 * @param user
	 */
	private void initProxyUser(TpUser user) {
		// TODO Auto-generated method stub
		// 获取当前用户
		// 设置用户登录信息
		username = user.getUsername();
		this.loginname = user.getLoginname();// 登录名
		bmId = user.getBmId();// 部门ID
		bmmc = user.getBmmc();// 部门名称
		userId = user.getId();// 用户表ID
		id = user.getRyId();// 人员ID
		rylbId = user.getRylbId();// 人员类别ID
		// 获取教职工信息
		int dm = getRylb(user.getRylbId());
		if (dm == 1) {// 教职工
			TbJzgxx jzg = (TbJzgxx) base.getEntityById(id, TbJzgxx.class);
			if (jzg != null) {
				yxId = jzg.getYxId();
				zyId = jzg.getZyId();
				zgh = jzg.getZgh();
				teacher = true;
			}
		} else if (dm == 3) {// 学生
			TbXjdaXjxx xs = new TbXjdaXjxx();
			xs.setId(id);
			xs = (TbXjdaXjxx) base.getEntityById(id,TbXjdaXjxx.class);
			if (xs != null) {
				zyId = xs.getZyId();
				bjId = xs.getBjId();
				xh = xs.getXh();
				student = true;
			}
		}
		// 初始化班主任信息
		initClassTeacher();
		userpermission.initUserPermission(this);
	}

	/**
	 * 获取人员类别代码
	 * 
	 * @param rylbId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private int getRylb(Long rylbId) {
		if (rylbId != null) {
			Session session = base.getSession();
			SQLQuery query = session
					.createSQLQuery("select dm from tc_xxbzdmjg t where  t.id="
							+ rylbId);
			List list = query.list();
			session.close();
			int dm = Integer.parseInt(list.get(0).toString());
			return dm;
		} else {
			return 0;
		}

	}

	/**
	 * 初始化班主任信息
	 */
	private void initClassTeacher() {
		classTeacherDataPermiss.initDataPermiss(id);
	}

	/**
	 * 获取用户菜单权限（包括自身权限和代理权限）
	 * 
	 * @return
	 */
	public Node getUserMenuTree() {
		// 清理之前的权限，保证没有缓存
		this.clear();
		initProxyUser();

		// 初始化用户自身权限
		userpermission.initUserPermission(this);// 刷新菜单
		Node root = userpermission.getMenuTree();// 初始化用户自身菜单权限

		// 创建委托权限节点
		Node proxyRoot = new Node();
		Long proxyId = System.currentTimeMillis();
		proxyRoot.setText("您被委托的权限");
		proxyRoot.setId(proxyId);
		proxyRoot.setPid(0l);
//		root.getChildren().add(proxyRoot);

		/*// 初始化用户代理权限
		for (User user : proxyUsers) {
			Node proxyUserRoot = user.getUserpermission().getProxyUserRoot();
			proxyUserRoot.setPid(proxyId);
			proxyRoot.getChildren().add(proxyUserRoot);
		}*/
		return root;
	}

	public Node getMenuTree() {
		// 初始化用户自身权限
		userpermission.initUserPermission(this);// 刷新菜单
		Node root = userpermission.getMenuTree();// 初始化用户自身菜单权限
		return root;
	}

	/**
	 * 获取数据权限
	 * 
	 * @param entity
	 * @return
	 */
	public String getCurrentSqlDataPermiss() {
		UserMenu um = CurrentUserMenuUtil.getThreadUserMenu();
		if (um.getUserId() == null) {
			return userpermission.getSqlMenuDataPermiss(um.getMenuId());
		} else {
			for (User user : proxyUsers) {
				if (um.getUserId().equals(user.getUserId())) {
					return user.getUserpermission().getSqlMenuDataPermiss(
							um.getMenuId());
				}
			}
		}
		return null;
	}
	/**
	 * 获取数据权限
	 * 
	 * @param entity
	 * @return
	 */
	public String getCurrentHqlDataPermiss() {
		UserMenu um = CurrentUserMenuUtil.getThreadUserMenu();
		if (um.getUserId() == null) {
			return userpermission.getHqlMenuDataPermiss(um.getMenuId());
		} else {
			for (User user : proxyUsers) {
				if (um.getUserId().equals(user.getUserId())) {
					return user.getUserpermission().getHqlMenuDataPermiss(
							um.getMenuId());
				}
			}
		}
		return null;
	}
	/**
	 * 以sql的形式，根据人员ID、人员类型ID获取用户数据权限
	 * @param ryId
	 * @param rylxId
	 * @return
	 */
	public String getCurrentSqlDataPermissByRyIdAndRylx(Long ryId,String rylxdm){
		UserMenu um = CurrentUserMenuUtil.getThreadUserMenu();
		if (um.getUserId() == null) {
			return userpermission.getSqlMenuDataPermissByUserIdAndRyLxId(ryId, rylxdm, um.getMenuId());
		} else {
			for (User user : proxyUsers) {
				if (um.getUserId().equals(user.getUserId())) {
					return user.userpermission.getSqlMenuDataPermissByUserIdAndRyLxId(ryId, rylxdm, um.getMenuId());
				}
			}
		}
		return null;
	}
	/**
	 * 以Hql的形式，根据人员ID、人员类型ID获取用户数据权限
	 * @param ryId
	 * @param rylxId
	 * @return
	 */
	public String getCurrentHqlDataPermissByRyIdAndRylx(Long ryId,String rylxdm){
		UserMenu um = CurrentUserMenuUtil.getThreadUserMenu();
		if (um.getUserId() == null) {
			return userpermission.getHqlMenuDataPermissByUserIdAndRyLxId(ryId, rylxdm, um.getMenuId());
		} else {
			for (User user : proxyUsers) {
				if (um.getUserId().equals(user.getUserId())) {
					return user.userpermission.getHqlMenuDataPermissByUserIdAndRyLxId(ryId, rylxdm, um.getMenuId());
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取班主任的sql数据权限
	 * @return
	 */
	public String getCurrentSqlClassTeacherDataPermiss() {
		UserMenu um = CurrentUserMenuUtil.getThreadUserMenu();
		if (um.getUserId() == null) {
			return classTeacherDataPermiss.getSqlDataPermiss();
		} else {
			for (User user : proxyUsers) {
				if (um.getUserId().equals(user.getUserId())) {
					return user.classTeacherDataPermiss.getSqlDataPermiss();
				}
			}
		}
		return null;
	}
	/**
	 * 获取班主任的hql数据权限
	 * @return
	 */
	public String getCurrentHqlClassTeacherDataPermiss() {
		UserMenu um = CurrentUserMenuUtil.getThreadUserMenu();
		if (um.getUserId() == null) {
			return classTeacherDataPermiss.getHqlDataPermiss();
		} else {
			for (User user : proxyUsers) {
				if (um.getUserId().equals(user.getUserId())) {
					return user.classTeacherDataPermiss.getHqlDataPermiss();
				}
			}
		}
		return null;
	}

	/**
	 * 获取当前用户
	 * 
	 * @return
	 */
	public User getCurrentUser() {
		UserMenu um = CurrentUserMenuUtil.getThreadUserMenu();
		if (um != null) {
			if (um.getUserId() == null) {
				return this;
			} else {
				for (User user : proxyUsers) {
					if (um.getUserId().equals(user.getUserId())) {
						return user;
					}
				}
			}
		} else {
			return this;
		}
		return this;
	}

	/**
	 * 获取用户在职工表/学生表中的ID
	 * @return
	 */
	public Long getCurrentId() {
		return getCurrentUser().id;
	}
	/**
	 * 获取用户表TP_USER.ID
	 * @return
	 */
	public Long getCurrentUserId() {
		return getCurrentUser().userId;
	}
	/**
	 * 获取用户姓名
	 * @return
	 */
	public String getCurrentUsername() {
		return getCurrentUser().username;
	}
	/**
	 * 获取登录名
	 * @return
	 */
	public String getCurrentLoginname() {
		return getCurrentUser().loginname;
	}
	/**
	 * 获取 部门ID
	 * @return
	 */
	public Long getCurrentBmId() {
		return getCurrentUser().bmId;
	}
	/**
	 * 获取 班级名称
	 * @return
	 */
	public String getCurrentBmmc() {
		return getCurrentUser().bmmc;
	}
	/**
	 * 获取 班级ID
	 * @return
	 */
	public Long getCurrentBjId() {
		return getCurrentUser().bjId;
	}
	/**
	 * 获取 院系ID
	 * @return
	 */
	public Long getCurrentYxId() {
		return getCurrentUser().yxId;
	}
	/**
	 * 获取专业ID
	 * @return
	 */
	public Long getCurrentZyId() {
		return getCurrentUser().zyId;
	}
	/**
	 * 获取职工号
	 * @return
	 */
	public String getCurrentZgh() {
		return getCurrentUser().zgh;
	}
	/**
	 * 获取学号
	 * @return
	 */
	public String getCurrentXh() {
		return getCurrentUser().xh;
	}
	/**
	 * 获取角色类型ID集合以逗号为分隔符
	 * @return
	 */
	public String getCurrentRoleLxIds(){
		return getCurrentUser().userpermission.getRoleLxIds();
	}
	/**
	 * 获取根据教学组织机构以及用户拥有的班级的数据权限，综合生成的树
	 */
	public TeachingOrganizationalStructureNode getCurrentJxzzjgDataPermissTree(){
		return getCurrentUser().userpermission.getJxzzjgDataTree();
	}
	/**
	 * 获取根据教学组织机构以及用户拥有的班级的数据权限，综合生成的树,该树已经移除了班级节点
	 */
	public TeachingOrganizationalStructureNode getCurrentJxzzjgDataPermissTreeWithOutBj(){
		return getCurrentUser().userpermission.getJxzzjgDataTreeWithOutBj();
	}
	/**
	 * 获取根据教学组织机构以及用户拥有的班级的数据权限，的ID集合
	 */
	public String getCurrentJxzzjgIds(){
		return getCurrentUser().userpermission.getJxzzjgIds();
	}
	/**
	 * 是否为学生
	 * @return
	 */
	public Boolean isCurrentStudent() {
		return getCurrentUser().student;
	}
	/**
	 * 是否为教师
	 * @return
	 */
	public Boolean isCurrentTeacher() {
		return getCurrentUser().teacher;
	}
	/**
	 * 是否为其他
	 * @return
	 */
	public Boolean isCurrentOthers() {
		return getCurrentUser().others;
	}
	/**
	 * 是否是班主任
	 * @return
	 */
	public Boolean isCurrentClassTeacher() {
		return classTeacherDataPermiss.isClassTeacher();
	}

	protected Long getId() {
		return id;
	}

	protected Long getUserId() {
		return userId;
	}

	protected String getUsername() {
		return username;
	}

	protected String getLoginname() {
		return loginname;
	}

	protected Long getBmId() {
		return bmId;
	}

	protected String getBmmc() {
		return bmmc;
	}

	protected Long getBjId() {
		return bjId;
	}

	protected Long getYxId() {
		return yxId;
	}

	protected Long getZyId() {
		return zyId;
	}

	protected String getZgh() {
		return zgh;
	}

	protected String getXh() {
		return xh;
	}

	protected Boolean isStudent() {
		return student;
	}

	protected Boolean isTeacher() {
		return teacher;
	}

	protected Boolean isOthers() {
		return others;
	}

	protected Long isRylbId() {
		return rylbId;
	}


	public void setUserpermission(UserPermission userpermission) {
		this.userpermission = userpermission;
	}

	protected UserPermission getUserpermission() {
		return userpermission;
	}


	public void setBase(Base base) {
		this.base = base;
	}
	
	public void setClassTeacherDataPermiss(
			ClassTeacherDataPermiss classTeacherDataPermiss) {
		this.classTeacherDataPermiss = classTeacherDataPermiss;
	}

	/****************** UserInfo兼容处理 ************************/
	public UserInfo translateToUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setBjId(this.getBjId());
		userInfo.setBmId(this.getBmId());
		userInfo.setBmmc(this.getBmmc());
		userInfo.setJzsh(this.getZgh());
		userInfo.setLoginName(this.getLoginname());
		userInfo.setUsername(this.username);
		userInfo.setRylbId(this.rylbId);
		userInfo.setZgId(this.id);
		userInfo.setZyId(this.zyId);
		userInfo.setYxId(this.yxId);
		userInfo.setId(this.userId);
		userInfo.setRoleIds(this.userpermission.getRoleLxIds());
		if (this.username == null)
			userInfo.setLoginName(this.username);
		return userInfo;
	}
}
