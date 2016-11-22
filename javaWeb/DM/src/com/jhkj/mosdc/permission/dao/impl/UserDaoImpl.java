package com.jhkj.mosdc.permission.dao.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.po.TbJzgxx;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.framework.util.Page;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permission.dao.UserDao;
import com.jhkj.mosdc.permission.po.TbLzBm;
import com.jhkj.mosdc.permission.po.TsConfig;
import com.jhkj.mosdc.permission.po.TsJs;
import com.jhkj.mosdc.permission.po.TsUser;
import com.jhkj.mosdc.permission.po.TsUserDataPermiss;
import com.jhkj.mosdc.permission.po.TsUserJs;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.BidCreate;
import com.jhkj.mosdc.xggl.xjgl.po.TbXjdaXjxx;
import com.jhkj.mosdc.xxzy.po.TbXxzyJxzzjg;
import com.jhkj.mosdc.xxzy.po.TbXxzyXzzzjg;

/**
 * @Comments: 用户DAO接口实现类
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-5-17
 * @TIME: 上午11:34:35
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao{

	@SuppressWarnings("rawtypes")
	@Override
	public UserInfo checkLogin(Map<String,Object> obj) throws ClassNotFoundException  {
		String yhh = (String)obj.get("loginName");
		String passwd = (String) obj.get("password");
		//把传入密码赋给pd;
		String pd = passwd;
		//传入的数据不是从单点登录系统过来的要进行数据加密
		if(!obj.containsKey("isOuterSystem"))
			pd = BidCreate.Encrypt(passwd).toUpperCase();//passwd; 
//		String xxdm = (String) user.get("xxdm");
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer().append(" from TsUser u where 1=1 and u.ztId =(select t.id from TcXxbzdmjg t where t.bzdm = 'XXDM-USERZT' and t.dm = '1')");
		sql.append(" and u.loginName = '" + yhh+"'");
		sql.append(" and u.password = '" + pd+"'");
		String jsSql = "select j.jslx_id from ts_user_js t,ts_js j where t.js_id = j.id and t.user_id = ";
//		sql.append("");
		List list = this.getSession().createQuery(sql.toString()).list();
		if(!"null".equals(list) && list.size() == 1){
			TsUser map = (TsUser) list.get(0);
			UserInfo userInfo = new UserInfo();
//			userInfo.setUsername(map.getUsername());
			userInfo.setLoginName(map.getLoginName());
			userInfo.setId(map.getId());
			userInfo.setRylbId(map.getRylbId());
			userInfo.setBmId(map.getBmId());
			userInfo.setZgId(map.getZgId());
			userInfo.setGroupPermiss(Boolean.valueOf(map.getGroupPermiss()));//增加组权限标志
			List jsList = this.getJdbcTemplate().queryForList(jsSql+map.getId());
			StringBuffer roleIds = new StringBuffer();
			//获取用户的角色并放入session中
			if(jsList != null && jsList.size() >0 ){
				for(int i = 0;i<jsList.size();i++){
					Map jsMap = (Map) jsList.get(i);
					roleIds.append(jsMap.get("JSLX_ID").toString()+","); 
				}
				userInfo.setRoleIds(roleIds.substring(0, roleIds.length()-1));
			}
			//获取数据权限
			String permisssql = "select * from ts_userdatapermiss where user_id = "+map.getId();
			List permissList = this.getJdbcTemplate().queryForList(permisssql);
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
//			userInfo.setXxdm(map.getBmId().toString());
			return userInfo;
		}
		return null;
	}
	/**
	 * 
	 * @param zzjgId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Long hasTbJxzzjg(Long zzjgId){
		Long zzjglb = 2L;
		String hsql = " from TbXxzyJxzzjg where  id = "+zzjgId;
		List list = this.getHibernateTemplate().find(hsql);
		if(list != null && list.size()>0){
			zzjglb = 1L;
		}
		return zzjglb;
	}

	@Override
	public Boolean checkUserNameIsExists(Long id,String userName) throws Exception {
		String hql = " from TsUser as u where LOWER(u.username)=? ";
		if(id!=null){ // 处理修改时的判断
			hql+=" and u.id<>"+id;
		}
		int n = this.findRecordCountByHQL(hql, new Object[]{userName.toLowerCase()});
		if(n>0){
			return true;
		}else{
			return false;
		}
		
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#queryLzBmTree(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TbLzBm> queryLzBmTree(String pId) {
		String hql = " from TbLzBm as t where t.lbm=? order by t.dwdm asc, t.dwmc asc";
		return getHibernateTemplate().find(hql, new Object[] { pId });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbXxzyJxzzjg> queryTbJxzzjgTree(String pId) {
		Long pIdL = Long.valueOf(pId);
		String hql = "from TbXxzyJxzzjg t1 where t1.sfky= '1' and t1.fjdId = ? order by t1.dm asc, t1.mc asc";
//		String sql = "select {tbJxzzjg.*} from TB_JXZZJG tbJxzzjg start with tbJxzzjg.ID = "+pIdL+" connect by prior tbJxzzjg.FJD_ID = tbJxzzjg.ID order by tbJxzzjg.DM asc, tbJxzzjg.MC asc";
//		List<TbJxzzjg> list = this.getSession().createSQLQuery(sql).addEntity("tbJxzzjg", TbJxzzjg.class).list();
		return getHibernateTemplate().find(hql, new Object[]{pIdL});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbXxzyJxzzjg> queryJxzzjgTree(Long pId) {
		Long pIdL = Long.valueOf(pId);
//		String hql = "from TbJxzzjg t1 where t1.fjdId = ? order by t1.dm asc, t1.mc asc";
		String sql = "select {tbJxzzjg.*} from TB_JXZZJG tbJxzzjg start with tbJxzzjg.FJD_ID = " +pIdL+
//				"(select t.ID from TB_JXZZJG t where t.FJD_ID ="+pIdL+")" +
				" connect by prior tbJxzzjg.ID = tbJxzzjg.FJD_ID order by tbJxzzjg.QXM asc ";
		List<TbXxzyJxzzjg> list = this.getSession().createSQLQuery(sql).addEntity("tbJxzzjg", TbXxzyJxzzjg.class).list();
//		return getHibernateTemplate().find(hql, new Object[]{pIdL});
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbXxzyXzzzjg> queryZzjgTree() {
		String sql = "select {tsZzjg.*} from TB_XZZZJG tsZzjg order by tsZzjg.QXM asc";
		List<TbXxzyXzzzjg> list = this.getSession().createSQLQuery(sql).addEntity("tsZzjg", TbXxzyXzzzjg.class).list();
		return list;
	}
	
	@Override
	public void saveUserPermiss(TsUserDataPermiss dataPermiss) throws Exception{
		dataPermiss.setId(this.getId());
		this.insert(dataPermiss);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getDataPermiss(Long userId){
		String sql = "select "+
               " us.zzjg_id ZZJGID,"+
				" nvl(nvl(max(jx.mc),max(zz.mc)),max(bj.bm)) "+
               " ZZJGMC"+
               " from ts_userdatapermiss us"+
               " left join tb_jxzzjg jx on us.zzjg_id = jx.id"+
               " left join tb_xzzzjg zz on us.zzjg_id = zz.id"+
               " left join tb_xxzy_bjxxb bj on us.zzjg_id = bj.id"+
               "  where us.user_id = "+userId+
               " group by us.zzjg_id";
		List datPermissList = this.getJdbcTemplate().queryForList(sql);
		if(datPermissList != null && datPermissList.size()>0){
			return datPermissList;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getUserDataPermiss(Long userId,String nodeIds){
		String jxzzjgHql = " select j.id as id,j.fjdId as fjdId,j.mc as mc,j.sfyzjd as sfyzjd,j.cc as cc from TsUserDataPermiss t, TbXxzyJxzzjg j where t.zzjgId = j.id and t.userId = "+userId+" and t.zzjgId in ( "+nodeIds+") order by t.zzjgId ";
		List jxzzjgPermissList = this.getHibernateTemplate().find(jxzzjgHql);
		if(jxzzjgPermissList != null && jxzzjgPermissList.size()>0){
			return jxzzjgPermissList;
		}
		String hql = " select x.id as id,x.fjdId as fjdId,x.mc as mc,x.sfyzjd as sfyzjd,x.cc as cc from TsUserDataPermiss t, TbXzzzjg x where t.zzjgId = x.id and t.userId = "+userId+" and t.zzjgId in ( "+nodeIds+" ) order by t.zzjgId ";
		List xzzzjgPermissList = this.getHibernateTemplate().find(hql);
		if(xzzzjgPermissList != null && xzzzjgPermissList.size()>0){
			return xzzzjgPermissList;
		}
		return null;
	}
	@Override
	public List getJxzzjgNodes(){
		//查询教学组织机构的所有节点
		String jxzzjgSql = " from TbXxzyJxzzjg j where 1=1 and j.sfky = 1 order by j.cc,j.qxm ";
		List jxzzjgList = this.getHibernateTemplate().find(jxzzjgSql);
		return jxzzjgList;
	}
	
	@Override
	public List getXzzzjgNodes(){
		//查询教学组织机构的所有节点
		String jxzzjgSql = "select j.id as id,j.fjdId as fjdId,j.mc as mc,j.sfyzjd as sfyzjd,j.cc as cc from TbXzzzjg j where 1=1 and j.sfky = 1 order by j.id ";
		List jxzzjgList = this.getHibernateTemplate().find(jxzzjgSql);
		return jxzzjgList;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getJxzzjgNodeParents(String parentId) throws Exception{
		List jxzzList = getJxzzjgNode(parentId);
		if(jxzzList != null && jxzzList.size()>0){
			return jxzzList;
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getXzzzjgNodeParents(String parentId) throws Exception{
		List xzzzList = getXzzzjgNode(parentId);
		if(xzzzList != null && xzzzList.size()>0){
			return xzzzList;
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getZzjgTreeNode(String bmId) throws Exception{
		
		
		//行政组织机构的顶级节点为null,而教学组织机构顶级节点为0
		String xzzzjgId =bmId;
		if ("0".equals(bmId)) {
			List<TbXxzyJxzzjg> fjdList = (List<TbXxzyJxzzjg>) this.queryTbJxzzjgTree(bmId);
			if(null!=fjdList&&fjdList.size() >0){
				bmId = String.valueOf(fjdList.get(0).getId());
			}else{
				Long id = this.getId();
				TbXxzyJxzzjg jxzzjg = new TbXxzyJxzzjg();
				jxzzjg.setCc(new Long(0));
				jxzzjg.setMc("学校");
				jxzzjg.setCclx("XX");
				jxzzjg.setFjdId(new Long(0));
				jxzzjg.setSfky(new Long(1));
				jxzzjg.setId(id);
				jxzzjg.setCjr("系统自动添加");
				jxzzjg.setCjsj(DateUtils.date2StringV2(new Date()));
				bmId = id.toString();
			}
		}
		Map map = new HashMap();
		map.put("jxzzjgList", null);
		map.put("xzzzjgList", null);
		//教学组织机构当前节点查询SQL
		List jxzzjgList = getJxzzjgNode(bmId);
		//行政组织机构当前节点查询SQL
		List xzzzjgList = getXzzzjgNode(xzzzjgId);
		//系统管理员组织机构为教学和行政组织机构的组合树
		if(jxzzjgList != null && jxzzjgList.size()>0 && xzzzjgList != null && xzzzjgList.size()>0){//bmId.endsWith("0") &&
			map.put("jxzzjgList", jxzzjgList);
			map.put("xzzzjgList", xzzzjgList);
			return map;
		}
		//教学组织机构的当前用户所在节点，并返回节点信息
		if(jxzzjgList != null && jxzzjgList.size()>0){
			map.put("jxzzjgList", jxzzjgList);
			return map;
		}
		//行政组织机构的当前用户所在节点，并返回节点信息
		if(xzzzjgList != null && xzzzjgList.size()>0){
			map.put("xzzzjgList", xzzzjgList);
			return map;
		}
		return map;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	private List getJxzzjgNode(String bmId){
		String jxzzjgHql = " select j.id as id,j.fjdId as fjdId,j.mc as mc,j.sfyzjd as sfyzjd,j.cc as cc from TbXxzyJxzzjg j where j.id = "+bmId+" and j.sfky = '1'  order by j.id ";
		List jxzzjgList = this.getHibernateTemplate().find(jxzzjgHql);
		return jxzzjgList;
	}
	
	@SuppressWarnings("rawtypes")
	private List getXzzzjgNode(String bmId){
		String jxzzjgHql = " select j.id as id,j.fjdId as fjdId,j.mc as mc,j.sfyzjd as sfyzjd,j.cc as cc from TbXzzzjg j where j.id = "+bmId+" and j.sfky = '1'  order by j.id ";
		List jxzzjgList = this.getHibernateTemplate().find(jxzzjgHql);
		return jxzzjgList;
	}
	@Override
	public List getJxzzjgRoot(String pId){
		String jxzzjgHql = " select j.id as id,j.fjdId as fjdId,j.mc as mc,j.sfyzjd as sfyzjd,j.cc as cc from TbXxzyJxzzjg j where j.fjdId = "+pId+" and j.sfky = '1' order by j.id ";
		List jxzzjgList = this.getHibernateTemplate().find(jxzzjgHql);
		return jxzzjgList;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getZzjgTreeChild(String bmId) throws Exception{
		//行政组织机构的顶级节点为null,而教学组织机构顶级节点为0
		String xzzzjgIds =bmId;
		if ("0".equals(bmId)) {
			xzzzjgIds = null;
			List<TbXxzyJxzzjg> fjdList = (List<TbXxzyJxzzjg>) this.queryTbJxzzjgTree(bmId);
			if(null!=fjdList&&fjdList.size() >0){
				bmId = String.valueOf(fjdList.get(0).getId());
			}else{
				Long id = this.getId();
				TbXxzyJxzzjg jxzzjg = new TbXxzyJxzzjg();
				jxzzjg.setCc(new Long(0));
				jxzzjg.setMc("学校");
				jxzzjg.setCclx("XX");
				jxzzjg.setFjdId(new Long(0));
				jxzzjg.setSfky(new Long(1));
				jxzzjg.setId(id);
				jxzzjg.setCjr("系统自动添加");
				jxzzjg.setCjsj(DateUtils.date2StringV2(new Date()));
				bmId = id.toString();
			}
		}
		Map map = new HashMap();
		map.put("jxzzjgList", null);
		map.put("xzzzjgList", null);
		//教学组织机构当前节点查询SQL
		String jxzzjgHql = " select j.id as id,j.fjdId as fjdId,j.mc as mc,j.sfyzjd as sfyzjd,j.cc as cc from TbXxzyJxzzjg j where j.fjdId = "+bmId+" and j.sfky = '1' order by j.id ";
		List jxzzjgList = this.getHibernateTemplate().find(jxzzjgHql);
		//行政组织机构当前节点查询SQL
		String hql = " select x.id as id,x.fjdId as fjdId,x.mc as mc,x.sfyzjd as sfyzjd,x.cc as cc from TbXzzzjg x where x.fjdId  = "+xzzzjgIds+" and x.sfky = '1' order by x.id ";
		List xzzzjgList = this.getHibernateTemplate().find(hql);
		//教学组织机构的当前用户所在节点，并返回节点信息
		if(jxzzjgList != null && jxzzjgList.size()>0){
			return jxzzjgList;
		}
		//行政组织机构的当前用户所在节点，并返回节点信息
		if(xzzzjgList != null && xzzzjgList.size()>0){
			return xzzzjgList;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TsUserDataPermiss> getUserPermiss(Long userId){
		String hql = " select t from TsUserDataPermiss t where t.userId = "+userId+" order by t.zzjgId ";
		List<TsUserDataPermiss> datPermissList = this.getHibernateTemplate().find(hql);
		if(datPermissList != null && datPermissList.size()>0){
			return datPermissList;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TsUserDataPermiss> queryDataPermissEntity(Long userId){
//		this.getSession().flush();
//		this.getHibernateTemplate().flush();
		List<TsUserDataPermiss> datPermissList = this.getHibernateTemplate().find(" from TsUserDataPermiss t where t.userId = "+userId);
		if(datPermissList != null && datPermissList.size()>0){
			return datPermissList;
		}else{
			return null;
		}
	}
	
	@Override
	public void deleteDataPermiss(List<TsUserDataPermiss> dataPermiss){
		this.getHibernateTemplate().deleteAll(dataPermiss);
		/*for(TsUserDataPermiss tsUserDataPermiss:dataPermiss){
			this.getHibernateTemplate().delete(tsUserDataPermiss);
		}*/
	}
	
	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#sumChildById(java.lang.String)
	 */
	@Override
	public Integer sumChildById(String lbm) {
		String sql = "select count(*) from TB_LZ_BM t where t.lbm ='" + lbm+"'";
		int i = this.getJdbcTemplate().queryForInt(sql);
		return i;
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#addTsUser(com.jhkj.mosdc.permission.po.TsUser)
	 */
	@Override
	public TsUser addTsUser(TsUser tsUser) throws Exception {
		tsUser.setId(this.getId());
		this.insert(tsUser);
		this.getSession().flush();
		return tsUser;
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#updateTsUser(com.jhkj.mosdc.permission.po.TsUser, java.lang.String)
	 */
	@Override
	public TsUser updateTsUser(TsUser tsUser, String roles) throws Exception {
		//更新用户信息
		updateTsUser(tsUser);
		//删除用户角色关联关系
		deleteTsUserJs(tsUser.getId());
		/*Session session = this.getSession();
		String sql = "";
		// 先删除角色用户关系
		sql = "delete from ts_user_js  where user_id = '" + tsUser.getId() + "'";
		int tt = 0;
		tt = session.createSQLQuery(sql).executeUpdate();
		this.logger.debug("清除角色用户对应关系数:" + tt + " sql:" + sql);
		if (roles.length() > 2) {
			roles=roles.replaceAll("'", "");			
			String[] menus = roles.split(",");
			Long roleId;
			for (int i = 0; i < menus.length; i++) {				
				roleId = Long.valueOf(menus[i]);
				sql = MessageFormat
						.format("insert into ts_user_js(id,user_id,js_id) values({0}, {1},{2})",Struts2Utils.quoteString(this.getId())
								,Struts2Utils.quoteString(tsUser.getId()), Struts2Utils.quoteString(roleId));
				this.logger.debug("保存用户角色数据：" + sql);
				tt = session.createSQLQuery(sql).executeUpdate();
			}
		}
		session.flush();*/
		if(roles.trim().length()>0){
			String[] roleIds = roles.split(",");
			if(roleIds.length>0){
				for(int i = 0;i<roleIds.length;i++){
					TsUserJs tsUserJs = new TsUserJs();
					tsUserJs.setId(this.getId());
					tsUserJs.setJsId(Long.valueOf(roleIds[i]));
					tsUserJs.setUserId(tsUser.getId());
					this.getHibernateTemplate().save(tsUserJs);
				}
			}
			
		}
		return tsUser;
	}
	
	@Override
	public void updateTsUser(TsUser tsUser){
		this.getHibernateTemplate().clear();
		this.getHibernateTemplate().update(tsUser);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteTsUserJs(Long userId){
		String sql = " from TsUserJs j where j.userId ="+userId;
		List<TsUserJs> tsUserJsList = this.getHibernateTemplate().find(sql);
		if(tsUserJsList != null && tsUserJsList.size()>0){
			this.getHibernateTemplate().deleteAll(tsUserJsList);
		}
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#saveUserRelaRole(java.lang.Long, java.lang.String)
	 */
	@Override
	public void saveUserRelaRole(Long userId, String roles,Long managerId) throws Exception {
		Session session = this.getSession();
		String sql = "",hsql="";
		// 先删除角色用户关系
		sql = "delete from ts_user_js  where user_id = " + userId ;
		int tt = 0;
		tt = session.createSQLQuery(sql).executeUpdate();
		this.logger.debug("清除角色用户对应关系数:" + tt + " sql:" + sql);
		if (roles.length() > 2) {
			roles=roles.replaceAll("'", "");			
			String[] menus = roles.split(",");
			Long roleId;
			for (int i = 0; i < menus.length; i++) {				
				roleId = Long.valueOf(menus[i]);
				hsql = "select t from TsJs t,TcXxbzdmjg g where t.jslxId = g.id and t.userId ="+managerId+" and t.jslxId ="+roleId;
				TsJs tsJs = (TsJs) this.getSession().createQuery(hsql).list().get(0);
				sql = MessageFormat
						.format("insert into ts_user_js(id,user_id,js_id) values({0}, {1},{2})",Struts2Utils.quoteString(this.getId())
								,Struts2Utils.quoteString(userId), Struts2Utils.quoteString(tsJs.getId()));
				this.logger.debug("保存用户角色数据：" + sql);
				tt = session.createSQLQuery(sql).executeUpdate();
			}
		}
		session.flush();
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#queryUserRoles(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryUserRoles(Long userId) throws Exception {
		String sql = "select t.id,g.mc as name,(Case When t2.JS_ID>=0 Then 'True' Else 'False' End) isCheck " +
				"from ts_js t , ts_user_js t2 ,Tc_Xxbzdmjg g   where t2.user_id =  "+userId+" and t.user_id = t2.user_id and t.jslx_id = g.id";
		List<Object[]> objs = this.getSession().createSQLQuery(sql).list();
		return objs;
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#queryTsUserList(com.jhkj.mosdc.framework.bean.PageParam)
	 */
	@Override
	public Page queryTsUserList(PageParam pageParam,Map paramMap,String bmId) throws Exception {
		
/*		String sql="select t.id,"+
			       " t.username,"+
			       " t.zg_id    as zgId, "+
			       " (case when zg.xm is null then xs.xm else zg.xm end)  as zgmc,"+
			       " t.bm_id    as bmId,"+
			       " bm.dwmc    as bmMc,"+
			       " t.zt_id    as ztId,"+
			       " c.mc       as ztMc,"+
			       " t.rylb_id  as rylbId,"+
			       " tc.mc      as rylbMc"+
			       " from TS_USER t"+
			       " left join tc_xxbzdmjg tc"+
			       "  on t.rylb_id = tc.id"+
			       "  left join tc_xxbzdmjg c"+
			       "  on t.zt_id = c.id"+
			       " left join tb_jzgxx zg"+
			       "  on t.zg_id = zg.id"+
			       "  left join tb_lz_bm bm"+
			       " on t.bm_id = bm.id"+
			       " left join tb_xsjbxx xs"+
			       " on t.zg_id = xs.id ";*/
		
		String sql="select t.id as \"id\","+
			       " t.username as \"username\","+
			       " t.zg_id    as \"zgId\", "+
			       " (case when zg.xm is null then xs.xm else zg.xm end)  as \"zgMc\","+
			       " t.bm_id    as \"bmId\","+
			       " (case when bm.mc is null then z.jgmc else bm.mc end)  as \"bmMc\","+
			       " t.zt_id    as \"ztId\","+
			       " c.mc       as \"ztMc\","+
			       " t.rylb_id  as \"rylbId\","+
			       " tc.mc      as \"rylbMc\"," +
			       " ''      as \"zzjgId\"," +
			       " ''      as \"zzjgMc\"," +
			       "(case when t.grouppermiss  = 1 then '有' else '无' end) as \"groupPermiss\"," +
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
			       " left join tb_xsjbxx xs"+
			       " on t.zg_id = xs.id "+
			       " left join ts_zzjg z on t.bm_id = z.id";
		if(!bmId.equals("")){
			sql=sql+ " where t.bm_id in ("+bmId+") ";
		}
		sql=sql+"  order by t.id desc,t.username asc";
		Page page = super.findOracleSqlPage(sql, null, pageParam);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TsUserDataPermiss> queryUserDataPermissList(Long userId){
		List<TsUserDataPermiss> list = this.getSession().createQuery(" from TsUserDataPermiss u where u.userId = "+userId).list();
		return (list != null) ? list:null;
	}
	
	@Override
	public Page queryTsUserList(PageParam pageParam,Long jxzzjgId,Long rylbId,Long groupId) throws Exception {
//		String sql="select t.id,t.username,t.zg_id as zgId, zg.xm as zgmc,t.bm_id as bmId,bm.dwmc as bmMc,t.zt_id as ztId,c.mc as ztMc ,t.rylb_id as rylbId,tc.mc as rylbMc from TS_USER t  left join tc_xxbzdmjg tc on t.rylb_id = tc.id left join tc_xxbzdmjg c on t.zt_id = c.id left join tb_lz_zg zg on t.zg_id=zg.id left join tb_lz_bm bm on t.bm_id=bm.id  ";
		Long ztId = this.queryIdByBm("1", "XXDM-USERZT");
		String sql = "select t.id, "+
			       "t.username, "+
			       "t.zg_id as zgId, "+
			       "zg.xm as zgmc, "+
			       "t.bm_id as bmId, "+
			       "(case "+
			       " when bm.mc is null then "+
			       "   z.jgmc "+
			       "  else "+
			       "   bm.mc "+
			       " end) as bmMc, "+
			       "t.zt_id as ztId, "+
			       "c.mc as ztMc, "+
			       "t.rylb_id as rylbId, "+
			       "tc.mc as rylbMc "+
//			       "'' as zzjgId, "+
//			       "'' as zzjgmc "+
			  "from TS_USER t "+
			  "left join tc_xxbzdmjg tc on t.rylb_id = tc.id "+
			  "left join tc_xxbzdmjg c on t.zt_id = c.id "+
			  "left join tb_jzgxx zg on t.zg_id = zg.id "+
			  "left join tb_jxzzjg bm on t.bm_id = bm.id "+
			  "left join ts_zzjg z on t.bm_id = z.id "+
			  " where 1 = 1 "+
			  " and t.zt_id ="+ztId;
		if(jxzzjgId!=null){
			sql=sql+ " and t.bm_id="+jxzzjgId;
		}
		if(rylbId != -1L){
			sql = sql+" and t.rylb_id = "+rylbId; 
		}
		if(groupId != -1L){
			sql = sql+" and t.GROUPPERMISS = "+groupId; 
		}
		sql=sql+"  order by t.id desc,t.username asc";
		Page page = super.findOracleSqlPage(sql, null, pageParam);
		return page;
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#queryUserRolesByUserId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryUserRolesByUserId(Long userId) throws Exception {
		String sql = "select  js.js_id,g.id as jslx_id ,g.mc from Tc_Xxbzdmjg g, ts_user_js js left join ts_js t on t.id=js.js_id where t.jslx_id = g.id and js.user_id=  "+userId;
		List<Object[]> objs = this.getSession().createSQLQuery(sql).list();
		return objs;
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#resetPassword(java.lang.Long, java.lang.String)
	 */
	@Override
	public void resetPassword(Long userId, String defaultPassword) throws Exception {
		TsUser user=this.getUserById(userId);
		if(user!=null){
			user.setPassword(defaultPassword);
			this.update(user);
		}
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#getUserById(java.lang.String)
	 */
	@Override
	public TsUser getUserById(Long userId) throws Exception {
		return (TsUser)this.get(TsUser.class, userId);
	}

	/* 
	 * @see com.jhkj.mosdc.permission.dao.UserDao#queryDefaultPassword(java.lang.String)
	 */
	@Override
	public String queryDefaultPassword(String code) throws Exception {
		String defaultPassword = null;
		TsConfig tsConfig = (TsConfig) this.get(TsConfig.class, code);
		if (tsConfig != null) {
			defaultPassword = tsConfig.getConfigValue();
		}
		return defaultPassword;
	}
	
	@Override
	public TbJzgxx queryTbJzgxx(Long id){
		TbJzgxx tbJzgxx = null;
		tbJzgxx = (TbJzgxx) this.getHibernateTemplate().get(TbJzgxx.class, id);
		return tbJzgxx;
	}
	
	@Override
	public TbXjdaXjxx queryTbXjdaXjxx(Long id){
		TbXjdaXjxx tbXsjbxx = (TbXjdaXjxx) this.getHibernateTemplate().get(TbXjdaXjxx.class, id);
		return tbXsjbxx;
	}

	@Override
	public void updateResetUserZt(Long userId) throws Exception {
		TsUser user=this.getUserById(userId);
		if(user!=null){
			//TODO:等BaseDaoImpl实现后修改
			Long ztId=this.queryIdByBm("1","XXDM-USERZT");
			user.setZtId(ztId);
			this.update(user);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int queryZzjglb(Long zzjgId) {
		int zzjblb = 2;
		List<TbXxzyJxzzjg> list = this.getSession().createQuery(" from TbXxzyJxzzjg t where t.id = "+zzjgId).list();
		if(list != null && list.size()>0){
			zzjblb = 1;
		}
		return zzjblb;
	}
	/***
	 * 根据用户名 密码，验证该密码的正确性
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean queryAndCheckOldPassword(String userId,String password) throws Exception {
		String sql = "select count(*) from ts_user t where t.id="+userId+" and t.password='"+password+"'";
		return super.getJdbcTemplate().queryForInt(sql)>0;
	}
	@Override
	public void saveTsUser(TsUser user) {
		this.insert(user);
	}
	/**
	 * 根据ID查询用户对象
	 * @param ids
	 * @return
	 */
	@Override
	public List queryTsUserList(String ids){
		String hsql = "from TsUser t where t.id in ("+ids+")";
		return this.getHibernateTemplate().find(hsql);
	}
}