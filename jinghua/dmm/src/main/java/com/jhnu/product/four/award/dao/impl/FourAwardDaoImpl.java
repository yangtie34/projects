package com.jhnu.product.four.award.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.four.award.dao.FourAwardDao;
import com.jhnu.util.common.MapUtils;

@Repository("FourAwardDao")
public class FourAwardDaoImpl implements FourAwardDao {
	@Autowired
	private BaseDao basedao;

	public List<Map<String, Object>> getAwardTimes() {
		// TODO 入学年级大于2011 不能硬编码
		String sql = " select nima.*,rownum r from( "
				+ " select * from (  "
				+ " select a.id,a.no_,a.Enroll_Grade as rxn,(a.Enroll_Grade+a.LENGTH_SCHOOLING) byn,a.name_,nvl(b.times,0) as times,nvl(b.money,0)as money from t_stu a "
				+ " left join( select count(*) as times,sum(money) money,stu_id from t_stu_award group by stu_id ) b "
				+ " on (a.no_=b.stu_id) )k where k.rxn>='2011' "
				+ " order by k.byn,k.times ) nima ";

		return basedao.getBaseDao().querySqlList(sql);
	}

	public List<Map<String, Object>> getAwardTimes(String Id) {
		String sql = "select * from tl_award where no_ = '" + Id + "' ";

		return basedao.getLogDao().querySqlList(sql);
	}

	public List<Map<String, Object>> getSubsidyTimesAndMoney(String Id) {

		String sql = "select * from tl_subsidy where no_ = '" + Id + "' ";

		return basedao.getLogDao().querySqlList(sql);
	}

	public List<Map<String, Object>> getFirstAwardMsg(String Id) {
		String sql = "select * from tl_first_award where no_ = '" + Id + "' ";

		return basedao.getLogDao().querySqlList(sql);
	}

	public void SaveAwardLog(List<Map<String, Object>> list) {
		
		String sql="delete  tl_award";
		basedao.getLogDao().executeSql(sql);
		
		
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_award(no_,byn,name_,times,more_than,money) values(?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(1, MapUtils.getString(LIST.get(i),"no_")
										.toString());
								ps.setString(2, MapUtils.getString(LIST.get(i),"byn")
										.toString());
								ps.setString(3, MapUtils.getString(LIST.get(i),"name_")
										.toString());
								ps.setString(4, MapUtils.getString(LIST.get(i),"times")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"more_than")
										.toString());
								ps.setString(6, MapUtils.getString(LIST.get(i),"money")
										.toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}

	public List<Map<String, Object>> getSubsidyTimesAndMoney() {
		String sql = "select a.name_,a.no_,(a.Enroll_Grade+a.LENGTH_SCHOOLING) byn,nvl(b.times,0) times,nvl(b.money,0) money from t_stu a left join("
				+ "select count(*) times ,sum(money) money,stu_id from t_stu_subsidy group by stu_id "
				+ ") b  on(a.no_=b.stu_id) where b.times > 0 order by byn";

		return basedao.getBaseDao().querySqlList(sql);

	}

	public void SaveSubsidyLog(List<Map<String, Object>> list) {
		String sql="delete tl_subsidy";
		basedao.getLogDao().executeSql(sql);
		
		
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_subsidy (no_,name_,byn,times,money)"
								+ "values (?,?,?,?,?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(1, MapUtils.getString(LIST.get(i),"no_")
										.toString());
								ps.setString(2, MapUtils.getString(LIST.get(i),"name_")
										.toString());
								ps.setString(3, MapUtils.getString(LIST.get(i),"byn")
										.toString());
								ps.setString(4, MapUtils.getString(LIST.get(i),"times")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"money")
										.toString());

							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});

	}

	public List<Map<String, Object>> getFirstAwardMsg() {
		String sql = "select * from (select c.no_,c.name_,nvl(m.batch,0) award_time,nvl(m.money,0) award_money,nvl(n.batch,0) subsidy_time,"
				+ "nvl(n.money,0) subsidy_money "
				+ "  from t_stu c left join (  "
				+ "select a.* from t_stu_award a inner join ( "
				+ "select min(batch) batch,stu_id from t_stu_award group by stu_id "
				+ ") b on (a.stu_id=b.stu_id and a.batch=b.batch) "
				+ ")m on (c.no_=m.stu_id) "
				+ "left join ( "
				+ " select a.* from t_stu_subsidy a inner join( "
				+ " select min(batch) batch,stu_id from t_stu_subsidy group by stu_id "
				+ " ) b on (a.stu_id=b.stu_id and a.batch=b.batch) "
				+ " )n on (c.no_=n.stu_id)) where  award_time  <>'0' or subsidy_time <>'0' ";

		return basedao.getBaseDao().querySqlList(sql);
	}

	public void SaveFirstAwardMsgLog(List<Map<String, Object>> list) {
        
	
		
		
		String sql="delete tl_first_award";
	    basedao.getLogDao().executeSql(sql);
		
		
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_first_award (no_,name_,award_time,award_money,subsidy_time,subsidy_money) "
								+ "values(?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(1, MapUtils.getString(LIST.get(i),"no_")
										.toString());
								ps.setString(2, MapUtils.getString(LIST.get(i),"name_")
										.toString());
								ps.setString(3, MapUtils.getString(LIST.get(i),"award_time")
										.toString());
								ps.setString(4, MapUtils.getString(LIST.get(i),"award_money")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"subsidy_time")
										.toString());
								ps.setString(6, MapUtils.getString(LIST.get(i),"subsidy_money").toString());

							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});

	}

}
