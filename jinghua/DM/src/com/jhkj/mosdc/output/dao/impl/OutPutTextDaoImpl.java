package com.jhkj.mosdc.output.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.DataException;
import org.springframework.jdbc.core.RowMapper;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.output.dao.OutPutTextDao;

/**
 * @comments:输出文本dao层接口
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-12
 * @time:下午05:16:24
 * @version :
 */
public class OutPutTextDaoImpl extends BaseDaoImpl implements OutPutTextDao {

	@Override
	public List<Object> queryGlTextList(String textId) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		List<Object> list = null;
		try {
			StringBuilder sb = new StringBuilder(
					"select t.* from tb_sc_wbtjgnb t where 1=1 ");
			Query query = session.createSQLQuery(sb.append(" and t.wbid ='")
					.append(textId).append("'").append(" order by t.sywz")
					.toString());
			list = query.list();
		} catch (HibernateException h) {
			h.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List<Object> queryTextList(String componid) {
		String queryString = null;
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		StringBuilder sb = new StringBuilder(
				"select t.* from tb_sc_wbtjb t where 1=1 ");
		queryString = sb.append(" and t.componid ='").append(componid)
				.append("'").toString();
		Query query = this.getSession().createSQLQuery(queryString);
		List<Object> list = query.list();
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryListValue(String sql) {
		List<Object> list = null;
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createSQLQuery(sql);
			list = query.list();
		} catch (DataException de) {
			System.out.println("OutPutTextDaoImpl 错误原因:DataException");// +de.getCause());
		} catch (HibernateException h) {
			System.out
					.println("OutPutTextDaoImpl 错误原因:HibernateException");// +h.getCause());
		} catch (RuntimeException r) {
			System.out.println("OutPutTextDaoImpl 错误原因:RuntimeException");// +r.getCause());
		} catch (Exception s) {
			System.out.println("OutPutTextDaoImpl 错误原因:Exception");// +s.getCause());
		} finally {
			session.clear();
			session.close();
		}
		return list;
	}

	@Override
	public List<Map> queryGridListForText(String gridView,
			List<String> conditionList, String seniorQuery, PageParam pageParam) {
		StringBuilder sb = new StringBuilder(
				"select * from (select t.*,rownum rown from (");
		sb.append(gridView).append(") t where 1=1 ");
		if (null != conditionList && conditionList.size() != 0) {
			for (int i = 0; i < conditionList.size(); i++) {
				sb.append("and t." + conditionList.get(i));
			}
		} else if (null != seniorQuery && !"".equals(seniorQuery)) {
			sb.append(" and " + seniorQuery);
		} else {

		}

		pageParam.setRecordCount(queryTotalCount(sb + ")"));// 计算总条数

		sb.append(") where rown>  ").append(pageParam.getStart())
				.append(" and rown<=")
				.append(pageParam.getStart() + pageParam.getLimit());// 分页遍历
		List<Map> list = this.queryListMap(sb.toString());
		return list;
	}

	/***
	 * 将sql查询到的数据转换成为List<Map>
	 * 
	 * @param sql
	 * @return
	 */
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
					String columnName = rsm.getColumnName((i + 1));
					// 名称为保持与stsxm表查出的一致,sql拼接有字母需加引号
					// 规则：当首字母为大写是,认为均为大写字母,全部转为小写
					char columnChar = columnName.charAt(0);
					if ('A' <= columnChar && columnChar <= 'Z') {
						columnName = columnName.toLowerCase();
					}
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

	/**
	 * 计算遍历sql的总数
	 * 
	 * @param querySQL
	 * @return
	 */
	private int queryTotalCount(String querySQL) {
		StringBuilder sb = new StringBuilder("select count(*) from (");
		sb.append(querySQL).append(") where 1=1");
		int totalCount = this.getJdbcTemplate().queryForInt(sb.toString());
		return totalCount;
	}

}
