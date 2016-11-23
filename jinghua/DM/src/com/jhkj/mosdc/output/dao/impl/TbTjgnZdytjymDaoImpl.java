package com.jhkj.mosdc.output.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.RowMapper;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.output.dao.TbTjgnZdytjymDao;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

/**
 * @comments:自定义统计页面dao层实现
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-12-4
 * @time:下午08:26:23
 * @version :
 */
public class TbTjgnZdytjymDaoImpl extends BaseDaoImpl implements
		TbTjgnZdytjymDao {

	// 管理员角色id,如果用户角色id内包含该id号,说明其拥有管理员权限.
	// 由于该id号既有可能变更,因而在此维护(gaodj的意思是只能通过id号判断是否是管理员,且认为该号以后专门维护,以至尽量不变)
	private static String adminRoleId = "1000000000000300";

	@Override
	public Boolean deleteTjgnZdytjym(JSONArray ids) {
		// 暂定：管理员可以删除任何信息
		// 普通用户不能删除 公有且已通过审核的的信息。
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Transaction tx = session.beginTransaction();
		try {
			for (int i = 0; i < ids.size(); i++) {
				Long id = ids.getLong(i);

				boolean flag = this.delete(session, tx, id, "delete");
				if (!flag) {
					if (!tx.wasRolledBack()) {
						tx.rollback();// 如果已经在delete方法内回滚,则不再回滚
					}
					return false;
				}
			}
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (!tx.wasRolledBack()) {
				tx.rollback();
			}
			return false;
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * 
	 * @param session
	 * @param tx
	 * @param id
	 *            页面的id
	 * @param flag
	 *            标志 delete update
	 * @return
	 */
	private boolean delete(Session session, Transaction tx, Long id, String flag) {
		try {
			String roleIds = UserPermissionUtil.getUserInfo().getRoleIds();// 角色id
			// 如果是普通用户 拼接此条 如果是管理员 此条不拼接
			if (!roleIds.contains(adminRoleId)) {
				// 查询这个id且通过审核和公有的数据,即普通用户对公有且经过审核的信息不可修改和删除
				StringBuilder sfsySql = new StringBuilder(
						"select * from (select tb.*,xx.id as sftgshid,xx.bzdm as sftgshbzdm,xx.dm as sftgshdm from (select tj.*,tc.id as sfsyid,tc.bzdm,tc.dm from tb_tjgn_zdytjym tj left join tc_xxbzdmjg tc on tj.sfsy=tc.id) tb left join tc_xxbzdmjg xx on tb.sftgsh= xx.id) t where 1=1 ")
						.append(" and t.sftgshbzdm='XXDM-SFTGSH' and t.sftgshdm='2' and t.id=")
						.append(id);
				sfsySql.append(" and t.bzdm='XXDM-SFSY' and t.dm='2'");
				Query query = session.createSQLQuery(sfsySql.toString());
				// 如果不为0 说明该id通过审核且为公有，故而不可删除
				if (query.list().size() != 0) {
					tx.rollback();
					return false;
				}
			}
			StringBuilder deleteViewSql = new StringBuilder(
					"select * from (select tj_ym_mb_gn.*,gnq_zj.id as gnqzjid from (select tj_ym_mb.*,mb_gn.id as mbgnid,mb_gn.funcareaid from (select tj_ym.*,ym_mb.id as ymmbid,ym_mb.templateid from (select tj.*,ym.id as ymid from tb_tjgn_zdytjym tj right join tb_sc_ym ym on tj.id = ym.pageid) tj_ym right join tb_sc_ym_mb ym_mb on tj_ym.ymid = ym_mb.pageid) tj_ym_mb right join tb_sc_mb_gn mb_gn on tj_ym_mb.templateid = mb_gn.templateid) tj_ym_mb_gn right join tb_sc_gnq_zj gnq_zj on tj_ym_mb_gn.funcareaid = gnq_zj.funareaid) where 1=1 ");
			deleteViewSql.append(" and id=").append(id);
			List<Map> list = this.queryListMap(deleteViewSql.toString());
			String json = Struts2Utils.list2json(list);
			JSONArray jsonArray = JSONArray.fromObject(json);
			StringBuilder ymidSbs = new StringBuilder();// 页面ids
			StringBuilder ymmbIdSbs = new StringBuilder();// 页面-模板中间表ids
			StringBuilder mbIdSbs = new StringBuilder();// 模板ids
			StringBuilder mbgnIdSbs = new StringBuilder();// 模板-功能中间表ids
			StringBuilder gnIdSbs = new StringBuilder();// 功能ids
			StringBuilder gnzjIdSbs = new StringBuilder();// 功能-组件中间表ids

			for (int j = 0, len = jsonArray.size(); j < len; j++) {
				JSONObject obj = JSONObject.fromObject(jsonArray.get(j));
				ymidSbs.append(obj.get("ymid")).append(",");
				ymmbIdSbs.append(obj.get("ymmbid")).append(",");
				mbIdSbs.append(obj.get("templateid")).append(",");
				mbgnIdSbs.append(obj.get("mbgnid")).append(",");
				gnIdSbs.append(obj.get("funcareaid")).append(",");
				gnzjIdSbs.append(obj.get("gnqzjid")).append(",");
			}

			String sc_ymSql = this.appendDeleteSql("tb_sc_ym", ymidSbs);// 输出-页面表删除sql
			String ym_mbSql = this.appendDeleteSql("tb_sc_ym_mb", ymmbIdSbs);// 输出-页面-模板中间表删除SQL
			String mbSql = this.appendDeleteSql("tb_sc_mb", mbIdSbs);// 输出-模板表删除SQL
			String mb_gnSql = this.appendDeleteSql("tb_sc_mb_gn", mbgnIdSbs);// 输出-模板-功能区中间表删除SQL
			String gnSql = this.appendDeleteSql("tb_sc_gnq", gnIdSbs);// 输出-功能表删除SQL
			String gn_zjSql = this.appendDeleteSql("tb_sc_gnq_zj", gnzjIdSbs);// 输出-功能区-组件中间表删除SQL

			// 与该id关联的所有数据均一并删除
			session.createSQLQuery(gn_zjSql).executeUpdate();
			session.createSQLQuery(gnSql).executeUpdate();
			session.createSQLQuery(mb_gnSql).executeUpdate();
			session.createSQLQuery(mbSql).executeUpdate();
			session.createSQLQuery(ym_mbSql).executeUpdate();
			session.createSQLQuery(sc_ymSql).executeUpdate();
			if ("delete".equals(flag)) {
				// 如果是删除的话 拼接此条 更新的话该条不删除而是在save方法内做更新操作
				StringBuilder deleteSql = new StringBuilder(
						"delete tb_tjgn_zdytjym where id=").append(id);
				session.createSQLQuery(deleteSql.toString()).executeUpdate();
			} else if ("update".equals(flag)) {

			} else {
			}
			return true;
		} catch (Exception e) {
			if (!tx.wasRolledBack()) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 拼接删除sql私有方法
	 * 
	 * @param tableName
	 *            表名
	 * @param deleteIds
	 *            删除ids(格式是StringBuilder类型的"1,2,...")
	 * @return
	 */
	private String appendDeleteSql(String tableName, StringBuilder deleteIds) {
		StringBuilder deleteSql = new StringBuilder();
		deleteSql.append("delete ").append(tableName).append(" where id in(");
		// 如果删除ids是空的话,那么删除语句中的id条件为空,即不拼接if语句内数据。
		if (deleteIds.length() != 0) {
			deleteSql.append(deleteIds.substring(0, deleteIds.length() - 1));
		} else {
			deleteSql.append("null");
		}
		deleteSql.append(")");
		return deleteSql.toString();
	}

	@Override
	public boolean updateTjgnZdytjym(HashMap<String, Object> map) {

		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Transaction tx = session.beginTransaction();
		Long zdytjymId = Long.valueOf(map.get("id").toString());
		try {
			// 先删除 再更新和添加的思路
			boolean isSuccessDelete = this.delete(session, tx, zdytjymId,
					"update");
			// 如果成功删除了,才进行保存更新的操作(之前共同判断出现bug后调整的)
			if (isSuccessDelete) {
				boolean isSuccessSave = this.save(session, tx, map, zdytjymId,
						"update");
				if (isSuccessSave) {
					tx.commit();
					return true;
				}
				if (!tx.wasRolledBack()) {
					tx.rollback();
				}
				return false;
			}
			if (!tx.wasRolledBack()) {
				tx.rollback();
			}
			return false;
		} catch (Exception e) {
			if (!tx.wasRolledBack()) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			session.clear();
			session.close();
		}
	}

	@Override
	public List loadTjgnZdytjymById(Long id) {
		try {
			String roleIds = UserPermissionUtil.getUserInfo().getRoleIds();// 角色id
			// 在加载的时候,如果普通用户,且该条是公有且通过审核的,应该不予以展示
			if (!roleIds.contains(adminRoleId)) {
				Session session = this.getSession();
				StringBuilder isLoadSQL = new StringBuilder(
						"select * from (select tb.*,xx.id as sftgshid,xx.bzdm as sftgshbzdm,xx.dm as sftgshdm from (select tj.*,tc.id as sfsyid,tc.bzdm,tc.dm from tb_tjgn_zdytjym tj left join tc_xxbzdmjg tc on tj.sfsy=tc.id) tb left join tc_xxbzdmjg xx on tb.sftgsh= xx.id) t where 1=1 ")
						.append(" and t.sftgshbzdm='XXDM-SFTGSH' and t.sftgshdm='2' and t.id=")
						.append(id);
				isLoadSQL.append(" and t.bzdm='XXDM-SFSY' and t.dm='2'");
				Query query = session.createSQLQuery(isLoadSQL.toString());
				if (query.list().size() > 0) {
					return null;
				}
			}
			StringBuilder view = new StringBuilder(
					"select tj.id,tj.sfsy,tj.mc as ymmc,tj.fid, mb.templatetype as mbid,gnq.id as gnqid,gnq.fnname as gnbt,gnq.helpinfo as gnsm,zj.id as zjid from tb_tjgn_zdytjym tj,tb_sc_ym ym,tb_sc_ym_mb ym_mb,tb_sc_mb mb,tb_sc_mb_gn mb_gn,tb_sc_gnq gnq,tb_sc_gnq_zj gnq_zj,tb_sc_zj zj where 1=1 ");
			view.append(" and tj.id = ym.pageid and ym.id = ym_mb.pageid and ym_mb.templateid = mb.id and mb.id = mb_gn.templateid and mb_gn.funcareaid = gnq.id and gnq.id = gnq_zj.funareaid and gnq_zj.componentid = zj.id ");
			if (id != null && !"".equals(id)) {
				view.append(" and tj.id ='").append(id).append("'");
			}
			view.append(" order by gnq_zj.sortnumber,mb_gn.sortnumber");// 按索引顺序输出(小->大)
			List<Map> list = this.queryListMap(view.toString());// 返回listMap格式的数据
			return list;// null或结果集

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Boolean saveTjgnZdytjym(HashMap<String, Object> map) {
		// 得到session
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		// 获得并开始事务
		Transaction tx = session.beginTransaction();
		try {
			Long zdytjymId = this.getId();
			boolean flag = this.save(session, tx, map, zdytjymId, "save");
			// 处理完后再提交
			if (flag) {
				tx.commit();
				return true;
			}
			if (!tx.wasRolledBack()) {
				tx.rollback();
			}
			return false;
		} catch (SQLException sqle) {
			// 遇异常回滚
			if (!tx.wasRolledBack()) {
				tx.rollback();
			}
			sqle.printStackTrace();
			return false;
		} catch (Exception e) {
			// 遇异常回滚
			if (!tx.wasRolledBack()) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * 封装的保存方法
	 * 
	 * @param session
	 *            session
	 * @param tx
	 *            事务
	 * @param map
	 *            前台传递的map格式数据
	 * @param zdytjymId
	 *            自定义统计页面id
	 * @param flag
	 *            两个值： update save
	 * @return
	 */
	private boolean save(Session session, Transaction tx,
			HashMap<String, Object> map, Long zdytjymId, String flag) {
		try {
			// 获取用户id
			Long userId = UserPermissionUtil.getUserInfo().getId();
			// 获取登陆用户角色id
			String roleIds = UserPermissionUtil.getUserInfo().getRoleIds();
			String userName = UserPermissionUtil.getUserInfo().getUsername();
			// 生成自定义统计页面id
			Long sfsy = Long.parseLong(map.get("sfsy").toString());
			Long sftgsh = null;
			// 步骤：sfsy过来后先后查询关联表,dm关联用以判断到底是操作什么的。
			// 检测是否私有为私有的sql
			StringBuilder checkSySql = new StringBuilder(
					"select * from tc_xxbzdmjg tc where tc.bzdm='XXDM-SFSY' and tc.dm='1' and tc.id=")
					.append(sfsy);
			Query syQuery = session.createSQLQuery(checkSySql.toString());
			StringBuilder shztSql = new StringBuilder(
					"select tc.id from tc_xxbzdmjg tc where tc.bzdm='XXDM-SFTGSH' ");// 查询审核状态id的sql
			Query shztQuery = null;
			// 如果私有或者是管理员角色操作的,默认均通过审核
			if (syQuery.list().size() != 0 || roleIds.contains(adminRoleId)) {
				// 如果私有不为空,说明sfsy代表私有 审核状态为已审核 执行查询已审核条件id的sql
				shztQuery = session.createSQLQuery(shztSql.append(
						"and tc.dm='2'").toString());
			} else {
				// 非私有,即公有或异常 审核状态为未审核 执行查询为审核条件id的sql
				// 如果是管理员 申请公有的也自动审核通过!!
				shztQuery = session.createSQLQuery(shztSql.append(
						"and tc.dm='1'").toString());
			}
			sftgsh = Long.parseLong(shztQuery.list().get(0).toString());
			StringBuilder zdytjymSql = null;
			if ("save".equals(flag)) {
				// 自定义统计页面插入SQL(其中菜单路径是默认插入的)
				zdytjymSql = new StringBuilder(
						"insert into tb_tjgn_zdytjym(id,mc,user_id,sfsy,fid,sftgsh,cdlj,cjzxm) values(")
						.append(zdytjymId).append(",'").append(map.get("ymmc"))
						.append("',").append(userId).append(",").append(sfsy)
						.append(",").append(map.get("fid")).append(",")
						.append(sftgsh).append(",'")
						.append("Output.Controller").append("','")
						.append(userName).append("')");

			} else if ("update".equals(flag)) {
				// 添加了是否通过审核的判断
				zdytjymSql = new StringBuilder(
						"update tb_tjgn_zdytjym t set t.mc='")
						.append(map.get("ymmc")).append("',t.sfsy=")
						.append(sfsy).append(",t.sftgsh=").append(sftgsh)
						.append(",t.fid=").append(map.get("fid"))
						.append(",xgz_id=").append(userId).append(",xgzxm='")
						.append(userName).append("'").append(" where t.id='")
						.append(zdytjymId).append("'");
			} else {
			}

			// 页面id
			Long sc_ym_id = this.getId();
			StringBuilder scYmSql = new StringBuilder(
					"insert into tb_sc_ym(id,pageid,title) values(")
					.append(sc_ym_id).append(",").append(zdytjymId)
					.append(",'").append(map.get("ymmc")).append("')");// sc_ym的插入SQL
			// 模板id
			Long sc_mb_id = this.getId();
			StringBuilder scMbSql = new StringBuilder(
					"insert into tb_sc_mb(id,templatetype) values(")
					.append(sc_mb_id).append(",'").append(map.get("mbid"))
					.append("')");// sc_mb的插入SQL
			// 页面-模板中间表id
			Long sc_ym_mb_id = this.getId();
			StringBuilder scYmMbSql = new StringBuilder(
					"insert into tb_sc_ym_mb(id,pageid,templateid) values(")
					.append(sc_ym_mb_id).append(",").append(sc_ym_id)
					.append(",").append(sc_mb_id).append(")");

			JSONArray gnList = (JSONArray) map.get("gnlist");
			if (gnList.size() == 0) {
				tx.rollback();
				return false;
			}
			for (int i = 0, gnLen = gnList.size(); i < gnLen; i++) {
				// 获取单个功能的对象
				JSONObject gns = (JSONObject) gnList.get(i);
				// 功能区id
				Long sc_gnq_id = this.getId();
				StringBuilder scGnqSql = new StringBuilder(
						"insert into tb_sc_gnq(id,fnname,helpinfo) values(")
						.append(sc_gnq_id).append(",'").append(gns.get("gnbt"))
						.append("','").append(gns.get("gnsm")).append("')");
				// 模板-功能中间表id
				Long sc_mb_gn_id = this.getId();
				StringBuilder scMbGnSql = new StringBuilder(
						"insert into tb_sc_mb_gn(id,templateid,funcareaid,sortnumber) values(")
						.append(sc_mb_gn_id).append(",").append(sc_mb_id)
						.append(",").append(sc_gnq_id).append(",'").append(i)
						.append("')");

				JSONArray zjidList = (JSONArray) gns.get("zjlist");
				if (zjidList.size() == 0) {
					tx.rollback();
					return false;
				}
				for (int j = 0, zjLen = zjidList.size(); j < zjLen; j++) {
					String zjid = (String) ((JSONObject) zjidList.get(j))
							.get("zjid");
					// 功能-组件中间表id
					Long sc_gnq_zj_id = this.getId();
					StringBuilder scGnqZjSql = new StringBuilder(
							"insert into tb_sc_gnq_zj(id,funareaid,componentid,sortnumber) values(")
							.append(sc_gnq_zj_id).append(",'")
							.append(sc_gnq_id).append("',").append(zjid)
							.append(",'").append(j).append("')");
					// 功能-组件中间表sql的新增
					session.createSQLQuery(scGnqZjSql.toString())
							.executeUpdate();
				}
				// 功能表sql的新增
				session.createSQLQuery(scGnqSql.toString()).executeUpdate();
				// 模板-功能中间表sql的新增
				session.createSQLQuery(scMbGnSql.toString()).executeUpdate();
			}
			// 模板表sql的新增
			session.createSQLQuery(scMbSql.toString()).executeUpdate();
			// 页面-模板中间表sql的新增
			session.createSQLQuery(scYmMbSql.toString()).executeUpdate();
			// 页面表sql的新增
			session.createSQLQuery(scYmSql.toString()).executeUpdate();
			// 自定义统计页面的sql新增
			session.createSQLQuery(zdytjymSql.toString()).executeUpdate();
			// 处理完后再提交
			return true;
		} catch (SQLException sqle) {
			if (!tx.wasRolledBack()) {
				tx.rollback();
			}
			sqle.printStackTrace();
			return false;
		} catch (Exception e) {
			if (!tx.wasRolledBack()) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean updateTjgnZdytjymSftgSh(JSONArray ids, String sftgsh) {
		// 前端不可添加俩一键按钮-需要动态变更的,所以可选择下拉
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		// 开启事务
		Transaction tr = session.beginTransaction();
		try {
			UserInfo u = UserPermissionUtil.getUserInfo();
			// 非管理员不可以审核或退回操作
			String roleId = u.getRoleIds();
			if (!roleId.contains(adminRoleId)) {
				if (!tr.wasRolledBack()) {
					tr.rollback();
				}
				return false;
			}
			Long userId = u.getId();// 用户id
			String userName = u.getUsername();// 用户名
			for (int i = 0; i < ids.size(); i++) {
				// 私有的 包括管理员私有的均不可批量更改是否通过审核操作
				// 用于检测该条是否是私有的，私有的则不进行是否通过审核操作
				// 2013-01-25 管理员自己的页面也不可退回或审核!(暂未更改，需要前后端统一的处理机制，暂时不改较好)
				StringBuilder checkSfsySql = new StringBuilder(
						"select * from (select tb.*,tc.id as sysyid,tc.bzdm,tc.dm from tb_tjgn_zdytjym tb,tc_xxbzdmjg tc where tb.sfsy=tc.id and tc.bzdm='XXDM-SFSY' and tc.dm='1') t where 1=1 and t.id =");
				checkSfsySql.append(ids.get(i));
				
				Query query = session.createSQLQuery(checkSfsySql.toString());
				List checkSfsyList = query.list();
				//管理员本人也不可以操作自己的页面2013-02-18更改
				StringBuilder adminSfshSql = new StringBuilder("select * from tb_tjgn_zdytjym tb where 1=1 and tb.id="+ids.get(i)+" and tb.user_id="+userId);//管理是否可以审核操作的Sql：管理员自己的页面不可做审核操作
				List checkAdminSfshList = session.createSQLQuery(adminSfshSql.toString()).list();
				if (checkSfsyList.size() != 0 || checkAdminSfshList.size() != 0) {
					// 如果是私有的数据,回滚操作；如果是管理员自己的也不可更改审核标志。1 管理员自己私有的 仅当更变为公有时才可批量更改审核标志 2
					// 普通用户则防止其通过其他途径更改其标志
					if (!tr.wasRolledBack()) {
						tr.rollback();
					}
					return false;
				}
				StringBuilder sb = new StringBuilder(
						"update tb_tjgn_zdytjym set sftgsh=");
				sb.append(sftgsh).append(",xgz_id=").append(userId)
						.append(",shrxm='").append(userName)
						.append("',xgzxm='").append(userName).append("'")
						.append(" where id=").append(ids.get(i));
				session.createSQLQuery(sb.toString()).executeUpdate();
			}
			tr.commit();
			return true;
		} catch (Exception e) {
			if (!tr.wasRolledBack()) {
				tr.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			session.clear();
			session.close();
		}
	}

	@Override
	public List queryTjgnZdytjym(List<String> conditionList, String seniorStr,
			PageParam pageParam) {
		Long userId = null;
		List<Map> list = null;
		try {
			userId = UserPermissionUtil.getUserInfo().getId();
			String roleId = UserPermissionUtil.getUserInfo().getRoleIds();
			StringBuilder sb = null;
			// 如果角色里包含管理员的角色
			if (roleId.contains(adminRoleId)) {
				// 如果是管理员的话（管理员的数据包含自定义的私有/公有的审核功能=以及应该可以看到 已经审核过 的数据以便更改之）
				StringBuilder adminView = new StringBuilder(
						"select * from (select tb.* from tb_tjgn_zdytjym tb ,tc_xxbzdmjg g where (tb.sfsy = g.id and g.dm ='1' and tb.user_id =")
						.append(userId);// 该条遍历私有数据
				adminView
						.append(") union all select tb.* from tb_tjgn_zdytjym tb,tc_xxbzdmjg g,tc_xxbzdmjg j where tb.sfsy = g.id and g.dm='2' and tb.sftgsh = j.id and j.dm != '3' ) t where 1=1 ");// 该条遍历公有且非退回的数据
				sb = adminView;
			} else {
				// 判断角色 是普通用户的话
				StringBuilder commonUserView = new StringBuilder(
						"select * from tb_tjgn_zdytjym t where 1=1 and user_id=")
						.append(userId);
				sb = commonUserView;
			}
			sb.append(assemblyParamSql(conditionList));
			if (null != seniorStr && !"".equals(seniorStr)) {
				sb.append(" and " + seniorStr);
			}
			sb.append(" order by id desc");

			pageParam.setRecordCount(queryCount(sb.toString()));
			String pagesql = getPageSql(sb.toString(), pageParam.getStart(),
					pageParam.getLimit());
			// 返回listMap格式的数据
			list = this.queryListMap(pagesql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return list;
		}

	}

	@Override
	public List queryZjData(String params, PageParam pageParam) {
		StringBuilder sb = new StringBuilder(
				"select id,mc,componenttype from tb_sc_zj  where 1=1");
		if (null != params && !"".equals(params)) {
			sb.append(" and mc like '%").append(params).append("%'");
		}
		pageParam.setRecordCount(queryCount(sb.toString()));
		String pagesql = getPageSql(sb.toString(), pageParam.getStart(),
				pageParam.getLimit());
		// 返回listMap格式的数据
		List<Map> list = this.queryListMap(pagesql);
		return list;
	}

	/***
	 * 获取该输入sql所能查询到的数据总量
	 * 
	 * @param inputsql
	 * @return
	 */
	private int queryCount(String inputsql) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from (").append(inputsql).append(")");
		int num = super.getJdbcTemplate().queryForInt(sql.toString());
		return num;
	}

	/***
	 * 拼装参数生成sql参数字符串
	 * 
	 * @param list
	 * @return
	 */
	private String assemblyParamSql(List<String> list) {
		StringBuilder sql = new StringBuilder();
		if (list != null) {
			for (String param : list) {
				String[] array = param.split("=");
				if (array.length != 1 && array.length == 2) {
					String p = array[1].trim();
					if (!"''".equals(p) && p != null) {
						sql.append(" and ").append(param);
					}
				} else if (array.length == 1) {
					sql.append(" and ").append(param);
				}
			}
		}
		return sql.toString();
	}

	private String getPageSql(String inputsql, int start, int limit) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" select * from (select t.*,rownum rown from  (")
				.append(inputsql).append(") t) where rown>  ").append(start)
				.append(" and rown<").append(start + limit);
		return sql.toString();
	}

	// 这里放置一个私有的JDBCTemplate的方法
	@SuppressWarnings("unchecked")
	private List<Map> queryListMap(String sql) {
		return super.getJdbcTemplate().query(sql, new RowMapper() {
			public ResultSetMetaData rsm = null;
			public int count = 0;

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				if (rsm == null) {
					rsm = rs.getMetaData();
					count = rsm.getColumnCount();
				}
				HashMap map = new HashMap();
				for (int i = 0; i < count; i++) {
					String columnName = rsm.getColumnName((i + 1))
							.toLowerCase();
					int sqlType = rsm.getColumnType(i + 1);
					Object sqlView = rs.getObject(columnName);
					switch (sqlType) {
					case Types.CHAR:
						map.put(columnName, sqlView.toString().trim());
						break;
					case Types.NUMERIC:
						if (sqlView == null)
							sqlView = "";
						map.put(columnName, sqlView.toString());
						break;
					default:
						map.put(columnName,
								sqlView != null ? sqlView.toString() : "");
						break;
					}
				}
				return map;
			}
		});
	}

	@Override
	public String getSftgshFlag(String shzt) {
		// 审核状态变为1或0的时候 查询相应sftgsh代码
		StringBuilder sb = new StringBuilder(
				"select tc.id from tc_xxbzdmjg tc where tc.bzdm='XXDM-SFTGSH'");
		// 1说明要更变为已审核
		if ("1".equals(shzt)) {
			sb.append(" and tc.dm='2'");
		} else if ("0".equals(shzt)) {
			// 0代表要退回
			sb.append(" and tc.dm='3'");
		} else {
			return null;// 如果非1或0 返回空
		}
		try {
			Query query = this.getSession().createSQLQuery(sb.toString());
			return query.list().get(0).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
