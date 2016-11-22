package com.jhkj.mosdc.output.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.RowMapper;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.output.dao.OutPutChartDao;

/**
 * @comments:输出组件图形通用dao层实现
 * @author hanking(E-mail : IsaidIwillgoon@gm ail.com)
 * @date:2012-11-6
 * @time:下午03:22:28
 * @version :
 */
public class OutPutChartDaoImpl extends BaseDaoImpl implements OutPutChartDao {

	// 简单测试统计表--如果可行的话 应该可以走数据库配置 改表的功能有：可做统计的字段、id以及可用

	@Override
	public List<Object> queryStudentStatisticsTable(String zd,
			String condition, String fw) {
		String tableStr = " (select tb2.mc as zymc,v4.* from "
				+ "(select tb1.mc as yxmc, v3.* from "
				+ "(select tc2.mc as njmc, v2.* from "
				+ "(select tc1.mc as xbmc, v1.* from "
				+ "(select t.id,t.xb_id,t.nj_id, t.zy_id,t.yx_id, t.csrq,t.sg, t.tz from tb_xsjbxx t where t.sfzx='1000000000000000') v1 "
				+ "inner join tc_xxbzdmjg tc1 on v1.xb_id = tc1.id and tc1.bzdm = 'DM-RDXB') v2  "
				+ "inner join tc_xxbzdmjg tc2 on tc2.id = v2.nj_id and tc2.bzdm = 'XXDM-NJ') v3 "
				+ "inner join tb_jxzzjg tb1 on tb1.id = v3.yx_id) v4 "
				+ "inner join tb_jxzzjg tb2 on tb2.id = v4.zy_id) t ";
		StringBuilder sb = new StringBuilder("select count(");
		List<Object> list = null;
		sb = sb.append(zd + ") from ").append(tableStr).append("where ")
				.append(zd).append("='").append(condition).append("' ").append(
						fw);
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createSQLQuery(sb.toString());
		list = query.list();
		session.close();
		return list;
	}

	@Override
	public String queryStudentStatisticsTableCount(String fw) {
		String tableStr = " (select tb2.mc as zymc,v4.* from "
				+ "(select tb1.mc as yxmc, v3.* from "
				+ "(select tc2.mc as njmc, v2.* from "
				+ "(select tc1.mc as xbmc, v1.* from "
				+ "(select t.id,t.xb_id,t.nj_id, t.zy_id,t.yx_id, t.csrq,t.sg, t.tz from tb_xsjbxx t where t.sfzx='1000000000000000') v1 "
				+ "inner join tc_xxbzdmjg tc1 on v1.xb_id = tc1.id and tc1.bzdm = 'DM-RDXB') v2  "
				+ "inner join tc_xxbzdmjg tc2 on tc2.id = v2.nj_id and tc2.bzdm = 'XXDM-NJ') v3 "
				+ "inner join tb_jxzzjg tb1 on tb1.id = v3.yx_id) v4 "
				+ "inner join tb_jxzzjg tb2 on tb2.id = v4.zy_id) t ";
		// 除100 是将统计的数 交由数据库处理 省去了代码内计算
		StringBuilder sb = new StringBuilder("select count(*)/100 from ");
		List<Object> list = null;
		sb.append(tableStr).append(" where 1=1 ").append(fw);
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createSQLQuery(sb.toString());
		list = query.list();
		BigDecimal bg = (BigDecimal) list.get(0);
		session.close();
		return bg.toString();
	}

	@Override
	public List<Object> queryWdCxzdmSql(String id) {

		StringBuilder sb = new StringBuilder(
				"select t.cxzdm,t.wdsql,t.cid from tb_sc_wdb t where t.id ='");
		List<Object> list = null;
		sb.append(id).append("'");
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createSQLQuery(sb.toString());
		list = query.list();
		session.close();
		return list;
	}

	public List<Object> queryWdList(String wdSql) {
		List<Object> list = null;
		list = this.getHibernateTemplate().find(wdSql);
		return list;
	}

	@Override
	public List<Object> queryZbLists(String zbIds) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		StringBuilder sb = new StringBuilder(
				"select t.*　from tb_sc_zbb t where 1=1 ");
		sb.append(" and t.id in (").append(zbIds).append(")");
		Query query = session.createSQLQuery(sb.toString());
		List list = query.list();
		session.close();
		return list;
	}

	@Override
	public List<Object> queryZbSqlResult(String sql, String condition) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ").append(condition);
		Query query = session.createSQLQuery(sb.toString());
		List<Object> list = query.list();
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryWdFwZbList(String tableName, String componentId) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		StringBuilder sb = new StringBuilder("select t.* from " + tableName
				+ " t where 1=1 ");
		sb.append(" and t.componid ='").append(componentId).append("'");
		Query query = session.createSQLQuery(sb.toString());
		List<Object> list = query.list();
		session.close();
		return list;
	}

	/*******************************************************************
	 * 新思路
	 ******************************************************************/
	@Override
	public List<Object> queryFwList(String componentId, String id) {
		return this.queryList("tb_sc_fwb", componentId, id);
	}

	@Override
	public List<Object> queryWdList(String componentId, String id) {
		return this.queryList("tb_sc_wdb", componentId, id);
	}

	@Override
	public List<Object> queryZbList(String componentId, String id) {
		return this.queryList("tb_sc_zbb", componentId, id);
	}

	@Override
	public List<Object> queryWbtjgnList(String id) {
		return this.queryList("tb_sc_wbtjgnb", null, id);
	}

	@Override
	public List<Object> queryCommonSQL(String sql) {
		List<Object> list = null;
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		try{
			Query query = session.createSQLQuery(sql);
			list = query.list();
		}catch(Exception e){e.printStackTrace();}finally{
			session.close();
			
		}
		return list;
	}

	/**
	 * 用于遍历纬度表、范围表、指标表的一个封装方法
	 * 
	 * @param tableName
	 * @param componentId
	 * @param id
	 * @return
	 */
	private List<Object> queryList(String tableName, String componentId,
			String id) {
		List<Object> list = null;
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		StringBuilder sb = new StringBuilder("select t.* from " + tableName
				+ " t where 1 = 1 ");
		if (null == componentId && null != id) {
			sb.append(" and t.id ='").append(id).append("'");
		} else if (null == id && componentId != null) {
			sb.append(" and t.componid ='").append(componentId).append("'");
		} else {
			// 不做处理
		}
		try{
			Query query = session.createSQLQuery(sb.toString());
			list = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public boolean isFwLessZb(String componentId) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		StringBuilder fwSql = new StringBuilder("select * from tb_sc_fwb t where 1=1 ");
		StringBuilder zbSql = new StringBuilder("select * from tb_sc_zbb t where 1=1 ");
		StringBuilder conditionSql = new StringBuilder(" and t.componid=");
		conditionSql.append(componentId);
		fwSql.append(conditionSql);
		zbSql.append(conditionSql);
        Query queryFw = session.createSQLQuery(fwSql.toString());
        Query queryZb = session.createSQLQuery(zbSql.toString());
        int fw = queryFw.list().size();
        int zb = queryZb.list().size();
        session.close();
		return fw<zb;//范围是否小于指标条数 
	}

}
