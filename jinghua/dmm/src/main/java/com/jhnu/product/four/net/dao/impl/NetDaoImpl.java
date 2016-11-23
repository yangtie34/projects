package com.jhnu.product.four.net.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.four.net.dao.NetDao;
import com.jhnu.util.common.MapUtils;
@Repository("netDao")
public class NetDaoImpl implements NetDao {
	@Autowired
	private BaseDao basedao;
   @Override
	public List<Map<String, Object>> getMax(){
		String sql = "select NO_,ENROLL_GRADE from T_STU group by NO_,ENROLL_GRADE";
		String sql1 = "select a.ENROLL_GRADE, to_char(round(avg((to_date(nvl(t.off_time,t.on_time), 'YYYY/MM/DD HH24:MI:SS')"
				+ " -to_date(t.on_time, 'YYYY/MM/DD HH24:MI:SS')) *86400000 / 60 / 60 / 1000),2),'FM99999990.99')"
				+ " as njsj from t_net_record t left join T_STU a"
				+ " on t.account_id = a.NO_ where a.ENROLL_GRADE is not null group by a.ENROLL_GRADE";
		String sql2 = "select max1.account_id,max1.mtime as maxsj,nj.njsj from (select t.account_id,to_char(round(max((to_date(nvl(t.off_time,t.on_time),'YYYY/MM/DD HH24:MI:SS') - to_date(t.on_time,'YYYY/MM/DD HH24:MI:SS'))* 86400000 / 60 / 60 / 1000 ),2),'FM99999990.99') as mtime "
				+ " from t_net_record t  group by t.account_id) max1 left join ("+sql+") stu on stu.no_ = max1.account_id left join ("+sql1
						+ ") nj on stu.ENROLL_GRADE = nj.ENROLL_GRADE";
		return  basedao.getBaseDao().querySqlList(sql2);
	}
   @Override
	public List<Map<String, Object>> getSum(){
		String sql = "(select t.account_id,to_char(round(avg((to_date(nvl(t.off_time,t.on_time),'YYYY/MM/DD HH24:MI:SS') - to_date(t.on_time,'YYYY/MM/DD HH24:MI:SS'))* 86400000 / 60 / 60 / 1000 ),2),'FM99999990.99') as atime "
				+ " from t_net_record t  group by t.account_id) ";
		String sql1 = "select sum1.account_id,sum1.stime,avg1.atime from (select t.account_id,to_char(round(sum((to_date(nvl(t.off_time,t.on_time),'YYYY/MM/DD HH24:MI:SS') - to_date(t.on_time,'YYYY/MM/DD HH24:MI:SS'))* 86400000 / 60 / 60 / 1000 ),2),'FM99999990.99') as stime "
				+ " from t_net_record t  group by t.account_id ) sum1 left join "+sql+" avg1 on "
						+ " sum1.account_id = avg1.account_id  ";
		return  basedao.getBaseDao().querySqlList(sql1);
	}
   @Override
	public List<Map<String, Object>> getJcsxsj(){
		String sql = "select a.account_id,c.tm as jtime from (select account_id, max(cs1) as cs2 from "
				+ " (select t.account_id,substr(t.on_time, 12, 2) as tm,count(*) as cs1 "
				+ " from t_net_record t group by t.account_id, substr(t.on_time, 12, 2)) "
				+ " group by account_id) a inner join (select b.account_id,"
				+ " substr(b.on_time, 12, 2) as tm, count(*) as cs1 from t_net_record b "
				+ " group by b.account_id, substr(b.on_time, 12, 2)) c on a.account_id = c.account_id and a.cs2 = c.cs1 ";
		return  basedao.getBaseDao().querySqlList(sql);
	}
   @Override
   public void saveNetMax(List<Map<String, Object>> max){
	   final int COUNT = max.size();
		final List<Map<String, Object>> LIST = max;
		String delSql="delete tl_net_max";
		basedao.getLogDao().executeSql(delSql);
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_net_max (no_,mtime,ntime) values (?, ?, ?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(1, MapUtils.getString(LIST.get(i),"ACCOUNT_ID")
										.toString());
								ps.setString(2, MapUtils.getString(LIST.get(i),"MAXSJ")
										.toString());
								ps.setString(3, MapUtils.getString(LIST.get(i),"NJSJ")
										.toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});

   }
   @Override
	public void saveNetSum(List<Map<String, Object>> sum){
	   final int COUNT = sum.size();
			final List<Map<String, Object>> LIST = sum;
			String delSql="delete tl_net_sum";
			basedao.getLogDao().executeSql(delSql);
			basedao.getLogDao()
					.getJdbcTemplate()
					.batchUpdate(
							"insert into tl_net_sum (no_,stime,atime) values (?, ?, ?)",
							new BatchPreparedStatementSetter() {
								// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
								public void setValues(PreparedStatement ps, int i)
										throws SQLException {
									ps.setString(1, MapUtils.getString(LIST.get(i),"ACCOUNT_ID")
											.toString());
									ps.setString(2, MapUtils.getString(LIST.get(i),"STIME")
											.toString());
									ps.setString(3, MapUtils.getString(LIST.get(i),"ATIME")
											.toString());
								}

								// 返回更新的结果集条数
								public int getBatchSize() {
									return COUNT;
								}
							});
		
	}
   @Override
    public void saveNetJcsxsj(List<Map<String, Object>> jcsxsj){
	   final int COUNT = jcsxsj.size();
		final List<Map<String, Object>> LIST = jcsxsj;
		String delSql="delete tl_net_jczx";
		basedao.getLogDao().executeSql(delSql);
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_net_jczx (no_,jtime) values (?, ?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(1, MapUtils.getString(LIST.get(i),"ACCOUNT_ID")
										.toString());
								ps.setString(2, MapUtils.getString(LIST.get(i),"JTIME")
										.toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
    	
    }
   @Override
   public List<Map<String, Object>> getNetMaxTime(String Id) {
	   String sql = "select case when to_number(mtime) <0"
	   		+ " then '未下线' else mtime end as maxtime,case when to_number(ntime) <0  "
	   		+ " then '未知' else ntime end  as njtime from tl_net_max where no_ = '"+Id+"'";
	return basedao.getLogDao().querySqlList(sql);
	   
   }
   @Override
   public List<Map<String, Object>> getNetSumTime(String Id) {
	   String sql = "select case when to_number(stime) <0"
	   		+ " then '未下线' else stime end as sumtime,case when to_number(atime) <0  "
	   		+ " then '未知' else atime end as avgtime from tl_net_sum where no_ = '"+Id+"'";
	return basedao.getLogDao().querySqlList(sql);
	   
   }
   @Override
   public List<Map<String, Object>> getNetJcOnlineTime(String Id) {
	   String sql = "select jtime as jctime "
	   		+ "from tl_net_jczx where no_ = '"+Id+"'";
	return basedao.getLogDao().querySqlList(sql);
   }
   @Override
   public List<Map<String, Object>> getNetBq(String Id) {
	   String sql ="select case when to_number(t.atime) <  to_number(a.ntime)/2 then '与世隔绝' "
	   		+ " when to_number(t.atime) >=to_number(a.ntime)/2 and to_number(t.atime) < to_number(a.ntime) * 2 "
	   		+ " then '普通网民' elsed '网虫' end as bq"
	   		+ " from tl_net_sum t left join tl_net_max a on t.no_ = a.no_ where t.no_ = '"+Id+"'";
	return basedao.getLogDao().querySqlList(sql);
	   
   }
}
