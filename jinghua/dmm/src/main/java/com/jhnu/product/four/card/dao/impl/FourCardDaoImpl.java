package com.jhnu.product.four.card.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.four.card.dao.FourCardDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.MapUtils;

@Repository("fourCardDao")
public class FourCardDaoImpl implements FourCardDao {

	@Autowired
	private BaseDao basedao;
	
	private static final Logger logger = Logger.getLogger(FourCardDaoImpl.class);

	public List<Map<String, Object>> getFirstPay() {
		

		String sql = " select a.card_id id,a.pay_money money,b.time time_,d.name_ window, cd.name_ style,fuck.no_ no_,fuck.name_ sname from t_card_pay a "
				+ " inner join (select cp.card_id,min(TIME_) as time from t_card_pay cp group by cp.card_id) b on "
				+ " (a.time_=b.time and a.card_id=b.card_id) "
				+ " inner join t_card nima on nima.no_=a.card_id "
				+ " right join t_stu fuck on fuck.no_=nima.people_id"
				+ " left join t_card_port p on a.card_port_id = p.no_"
				+ " left join t_card_dept d on p.card_dept_id=d.id"
				+ " left join t_code_card_deal cd on a.card_deal_id= cd.id"
				+ " where "
				+ " nima.card_identity_id in (select id from t_code_card_identity start with id ='P1'  connect by prior id=pid)";

		

		return basedao.getBaseDao().querySqlList(sql);
	}

	public List<Map<String, Object>> getFirstPayMsg(String Id) {
		String sql = "select * from tl_card_fpay where no_ = '" + Id + "'";
		return basedao.getLogDao().querySqlList(sql);
	}
    
	
	public List<Map<String,Object>> getAvgWashTimes(String SexCode,String byn){
		
		String sql="select nvl(round(avg(times),2),0) as avg_t from tl_wash where byn='"+byn+"' and sex_code='"+SexCode+"'";
		
		
		return basedao.getLogDao().querySqlList(sql);
	     
	}

	public List<Map<String, Object>> getFavoriteWindow(String Id) {

	
		String sql = "select * from(   "
				+ "    select t.*,rownum r from        "
				+ "    (select k.time,k.port,d.name_ from(  "
				+ "    select a.times as time,a.card_port_id as port from "
				+ "    (select count(ID) as times,card_port_id from t_card_pay where card_id in "
				+ "    (select no_ as card_id from t_card where people_id='201107010049' ) "
				+ "    group by card_port_id) a ) k "
				+ "    left join t_card_port b on (b.no_=k.port) "
				+ "    left join t_card_dept d on (d.id=b.card_dept_id) order by time desc )t ) where r=1 ";

		return basedao.getBaseDao().querySqlList(sql);

	}

	public List<Map<String, Object>> getWashTimes() {

		String sql = " select nima.* ,rownum r from "
				+ " (  select nvl(fuck.times,0) times,f.no_ fno,s.name_,s.sex_code,s.no_ ,(s.Enroll_Grade+s.LENGTH_SCHOOLING) byn "
				+ "  from (  select k.card_id,count(k.time_) as times   "
				+ "  from ( select distinct * from( select card_id,substr(time_,0,10) as time_ "
				+ "   from t_card_pay where card_deal_id='211') ) k group by k.card_id order by times  ) fuck "
				+ "   right join t_card f on(f.no_=fuck.card_id) right join t_stu s on(s.no_=f.people_id)"
				+ "   where  f.card_identity_id in (select id from t_code_card_identity start with id ='P1'  connect by prior id=pid)"
				+ "   order by sex_code,byn,times ) nima where nima.name_ is not null";

		return basedao.getBaseDao().querySqlList(sql);
	}

	public List<Map<String, Object>> getWashTimes(String Id) {
		String sql = "select * from tl_wash where no_ = '" + Id + "'";

		return basedao.getLogDao().querySqlList(sql);

	}

	public List<Map<String, Object>> getPeopleNumByBynAndSex() {

		String sql = "   select * from (  select count(*) as p_num,sex_code,byn from"
				+ " (       select nima.* ,rownum r from"
				+ "    (  select nvl(fuck.times,0) times,f.no_ fno,s.name_,s.sex_code,s.no_ ,(s.Enroll_Grade+s.LENGTH_SCHOOLING) byn "
				+ "    from (  select k.card_id,count(k.time_) as times   "
				+ "    from ( select distinct * from( select card_id,substr(time_,0,10) as time_ "
				+ "    from t_card_pay where card_deal_id='211') ) k group by k.card_id order by times  ) fuck "
				+ "    right join t_card f on(f.no_=fuck.card_id) right join t_stu s on(s.no_=f.people_id)"
				+ "   where  f.card_identity_id in (select id from t_code_card_identity start with id ='P1'  connect by prior id=pid)"
				+ "    ) nima where nima.name_ is not null  )result  group by sex_code,byn  ) "
				+ "    order by sex_code,byn";
		return basedao.getBaseDao().querySqlList(sql);

	}

	public void saveFirstJob(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		String delSql="delete tl_card_fpay";
		basedao.getLogDao().executeSql(delSql);
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_card_fpay (no_,id,time_,money,style,window,sname) values (?, ?, ?, ?, ?,?,?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(1, MapUtils.getString(LIST.get(i),"no_")
										.toString());
								ps.setString(2, MapUtils.getString(LIST.get(i),"id")
										.toString());
								ps.setString(3, MapUtils.getString(LIST.get(i),"time_")
										.toString());
								ps.setString(4, MapUtils.getString(LIST.get(i),"money")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"style")
										.toString());
								ps.setString(6, MapUtils.getString(LIST.get(i),"window")
										.toString());
								ps.setString(7, MapUtils.getString(LIST.get(i),"sname")
										.toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});


	}

	public void saveWashLog(List<Map<String, Object>> list) {
       
		String sql="delete tl_wash";
		basedao.getLogDao().executeSql(sql);
	    
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into  tl_wash(no_,name_,sex_code,card_id,byn,times,more_than,avg_t) values(?,?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(1, MapUtils.getString(LIST.get(i),"no_")
										.toString());
								ps.setString(2, MapUtils.getString(LIST.get(i),"name_")
										.toString());
								ps.setString(3, MapUtils.getString(LIST.get(i),"sex_code")
										.toString());
								ps.setString(4, MapUtils.getString(LIST.get(i),"fno")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"byn")
										.toString());
								ps.setString(6, MapUtils.getString(LIST.get(i),"times")
										.toString());
								ps.setString(7, MapUtils.getString(LIST.get(i),"more_than")
										.toString());
								if(MapUtils.getString(LIST.get(i),"avg_t").toString()=="0.0")
								{
									ps.setString(8, "0");
								}
								else{
								ps.setString(8, MapUtils.getString(LIST.get(i),"avg_t")
										.toString());
								}
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});

	}

	public void savePayTimesAndMoneyLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		logger.info("====================================开始删除成功===================================");
		String delSql="delete tl_pay_times_money";
		basedao.getLogDao().executeSql(delSql);
		logger.info("====================================删除成功===================================");
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_pay_times_money(no_,name_,card_no,times,money,more_than,avg_m) "
								+ "values (?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(1, MapUtils.getString(LIST.get(i),"no_")
										.toString());
								ps.setString(2, MapUtils.getString(LIST.get(i),"name_")
										.toString());
								ps.setString(3, MapUtils.getString(LIST.get(i),"card_no")
										.toString());
								ps.setString(4, MapUtils.getString(LIST.get(i),"times")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"money")
										.toString());
								ps.setString(6, MapUtils.getString(LIST.get(i),"more_than")
										.toString());
								ps.setString(7, MapUtils.getString(LIST.get(i),"avg_m")
										.toString());

							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}


	
	@Override
	public List<Map<String, Object>> getSumMoneyAndPayTimes() {
		String createSql="insert into t_temp_card_summoney select count(*) as times,sum(pay_money) as money, "+
						 "card_id from t_card_pay t group by t.card_id";
		basedao.getBaseDao().getJdbcTemplate().execute(createSql);
		logger.info("=============================还不执行1？ ======");
		createSql="insert into t_temp_card_payday  select card_id,count(*) days from "+
				  " ( select count(*) days,  card_id from t_card_pay group by card_id,substr(TIME_, 0, 10) ) group by card_id";
		basedao.getBaseDao().getJdbcTemplate().execute(createSql);
		logger.info("=============================还不执行2？ ======");
		List<Map<String, Object>> list =  new ArrayList<Map<String,Object>>();
		String sql = "select k.*,rownum r from(  "+
					   "select a.no_,a.name_,nvl(b.no_,0) card_no,nvl(c.times,0) times,nvl(c.money,0) money,  "+
					   "nvl(d.days,0),a.length_schooling style,  "+
					   "(a.Enroll_Grade+a.LENGTH_SCHOOLING) byn,cast(nvl(c.money,0)/nvl(d.days,1) as number(8,4)) avg_m   "+
					   " from t_stu a  "+
					   " left join t_card b on a.no_=b.people_id    "+
					   " left join t_temp_card_summoney c on c.card_id=b.no_  "+
					   "left join t_temp_card_payday d  on d.card_id=b.no_  "+
					  " where (a.Enroll_Grade+a.LENGTH_SCHOOLING)<=to_number(to_char(sysdate,'yyyy')) "+
					  " order by byn,avg_m) k";
	          list=basedao.getBaseDao().querySqlList(sql);
	          logger.info("=============================还不执行3？ ======");
		createSql="delete from t_temp_card_summoney";
		basedao.getBaseDao().getJdbcTemplate().execute(createSql);
		logger.info("=============================还不执行4？ ======");
		createSql="delete from t_temp_card_payday";
		basedao.getBaseDao().getJdbcTemplate().execute(createSql);
		logger.info("=============================还不执行5？ ======");
		return list;

	}
	public List<Map<String, Object>> getSumMoneyAndPayTimes(String Id) {
		String sql = "select * from tl_pay_times_money where no_ = '" + Id + "'";
		return basedao.getLogDao().querySqlList(sql);
	}

	@Override
	public void saveMealsCount(List<Map<String, Object>> list) {
		final String now = DateUtils.SSS.format(new Date());
		final List<Map<String, Object>> fList = list;
		String delSql = "DELETE FROM TL_CARD_MEALS";
		basedao.getLogDao().getJdbcTemplate().execute(delSql);
		String inertSql = "insert into tl_card_meals (STU_NO, SEX_CODE, END_YEAR, "
				+ " BREAKFAST_DAYS, LUNCH_DAYS, DINNER_DAYS,"
				+ " BREAKFAST_MORE_THAN, LUNCH_MORE_THAN, DINNER_MORE_THAN, STATISTICS_TIME) "
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		basedao.getLogDao().getJdbcTemplate()
				.batchUpdate(inertSql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setString(1, MapUtils.getString(fList.get(i),"STU_NO").toString());
						ps.setString(2, MapUtils.getString(fList.get(i),"SEX_CODE").toString());
						ps.setString(3, MapUtils.getString(fList.get(i),"BYXN").toString());
						ps.setInt(4,MapUtils.getIntValue(fList.get(i),"COUNT_ZC"));
						ps.setInt(5,MapUtils.getIntValue(fList.get(i),"COUNT_MIDDLE"));
						ps.setInt(6,MapUtils.getIntValue(fList.get(i),"COUNT_WC"));
						ps.setString(7, MapUtils.getString(fList.get(i),"BREAKFAST_MORE_THAN")
								.toString());
						ps.setString(8, MapUtils.getString(fList.get(i),"LUNCH_MORE_THAN")
								.toString());
						ps.setString(9, MapUtils.getString(fList.get(i),"DINNER_MORE_THAN")
								.toString());
						ps.setString(10, now);
					}

					@Override
					public int getBatchSize() {
						return fList.size();
					}
				});

	}

	@Override
	public Page getPayCount(int currentPage,int numPerPage,Integer totalRows,Integer status) {
		String sql="select t1.cou count_,t1.su sum_,decode(t2.cou,0,'0.00%',round(t1.cou/t2.cou*100,2) || '%') count_ratio,decode(t2.su,0,'0.00%',round(t1.su/t2.su*100,2) || '%') sum_ratio ,t1.did deal_id,t1.cid card_id,t1.sno stu_id from "+
				"(select nvl(count(*),0) cou,nvl(sum(t.pay_money),0) su,t.card_id cid,t.card_deal_id did,s.no_ sno from t_card_pay t inner join t_card c on t.card_id=c.no_ "+
				"right join t_stu s on c.people_id=s.no_ where s.stu_state_code='1' group by t.card_id,s.no_ ,t.card_deal_id ) t1 "+
				"inner join  "+
				"(select nvl(count(*),0) cou,nvl(sum(t.pay_money),0) su,t.card_id cid,s.no_ sno from t_card_pay t inner join t_card c on t.card_id=c.no_ "+
				"right join t_stu s on c.people_id=s.no_  where s.stu_state_code='1' group by t.card_id ,s.no_ ) t2 "+
				"on t1.cid=t2.cid";
		String tempTable="t_temp_payCount";
		String tempSql="select * from "+tempTable;
		Page page=null;
		String delSql="delete "+tempTable;
		if(status==0){
			basedao.getBaseDao().executeSql(delSql);
			sql= "insert into "+tempTable+" value (" + sql+ " )";
			basedao.getBaseDao().executeSql(sql);
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else if (status == 1) {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		}
		return page;
	}

	@Override
	public void savePayCountLog(List<Map<String, Object>> list,boolean isFist) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		if(isFist){
			String delSql="delete tl_card_pay_count";
			basedao.getLogDao().executeSql(delSql);
		}
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_card_pay_count values (?, ?, ?, ?, ?, ?, ?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setInt(
										1,
										MapUtils.getIntValue(LIST.get(i),"count_"));
								ps.setString(2, MapUtils.getString(LIST.get(i),"count_ratio")
										.toString());
								ps.setDouble(3,MapUtils.getDoubleValue(LIST.get(i),"sum_"));
								ps.setString(4, MapUtils.getString(LIST.get(i),"sum_ratio")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"deal_id")
										.toString());
								ps.setString(6, MapUtils.getString(LIST.get(i),"card_id")
										.toString());
								ps.setString(7, MapUtils.getString(LIST.get(i),"stu_id")
										.toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}

	@Override
	public List<Map<String, Object>> getPayCountLog(String id) {
		String sql = "select case t.deal_id when '210' then '餐厅' when '215' then '超市' when '211' then '洗浴' when '220' then '用水' else '其他' end as name,count_ value from tl_card_pay_count t where t.stu_id= '"+id+"' ";
		return basedao.getLogDao().queryListMapInLowerKeyBySql(sql);
	}

	@Override
	public Page getLikeEat(int currentPage,int numPerPage,Integer totalRows,Integer status) {
		String sql="select t1.cou count_,t1.su sum_,decode(t2.cou,0,'0.00%',round(t1.cou / t2.cou * 100, 2) || '%') count_ratio,decode(t2.su,0,'0.00%',round(t1.su/t2.su*100,2) || '%') sum_ratio ,t1.name_,t1.cid card_id,t1.sno stu_id from "+
				"(select nvl(sum(a.pay_money),0) su,nvl (count(*),0) cou ,d.name_,a.card_id cid,s.no_ sno from t_card_pay a                                                          "+
				"inner join t_card_port p on a.card_port_id=p.no_                                                                                                                    "+
				"inner join t_card_dept d on p.card_dept_id=d.id                                                                                                                     "+
				"inner join t_card c on a.card_id=c.no_                                                                                                                              "+
				"right join t_stu s on c.people_id=s.no_                                                                                                                             "+
				"where a.card_deal_id='210' and s.stu_state_code='1'                                                            "+
				"group by s.no_,a.card_id,d.name_                                                                                                                                    "+
				"order by s.no_,cou desc) t1                                                                                                                                         "+
				"inner join                                                                                                                                                          "+
				"(select nvl(sum(a.pay_money),0) su,nvl (count(*),0) cou ,a.card_id cid,s.no_ sno from t_card_pay a                                                                  "+
				"inner join t_card_port p on a.card_port_id=p.no_                                                                                                                    "+
				"inner join t_card_dept d on p.card_dept_id=d.id                                                                                                                     "+
				"inner join t_card c on a.card_id=c.no_                                                                                                                              "+
				"right join t_stu s on c.people_id=s.no_                                                                                                                             "+
				"where a.card_deal_id='210' and s.stu_state_code='1'                                                           "+
				"group by s.no_,a.card_id                                                                                                                                            "+
				"order by s.no_,cou desc) t2                                                                                                                                         "+
				"on t1.cid=t2.cid ";
		String tempTable="t_temp_likeEat";
		String tempSql="select * from "+tempTable;
		Page page=null;
		String delSql="delete "+tempTable;
		if(status==0){
			basedao.getBaseDao().executeSql(delSql);
			sql= "insert into "+tempTable+" value (" + sql+ " )";
			basedao.getBaseDao().executeSql(sql);
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else if (status == 1) {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		}
		return page;
	}

	@Override
	public void saveLikeEatLog(List<Map<String, Object>> list,boolean isfrist) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		if(isfrist){
			String delSql="delete tl_card_like_eat";
			basedao.getLogDao().executeSql(delSql);
		}
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_card_like_eat values (?, ?, ?, ?,?,?,?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setInt(1,MapUtils.getIntValue(LIST.get(i),"count_"));
								ps.setString(2, MapUtils.getString(LIST.get(i),"count_ratio"));
								ps.setDouble(3,MapUtils.getDoubleValue(LIST.get(i),"sum_"));
								ps.setString(4, MapUtils.getString(LIST.get(i),"sum_ratio")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"name_")
										.toString());
								ps.setString(6, MapUtils.getString(LIST.get(i),"card_id")
										.toString());
								ps.setString(7, MapUtils.getString(LIST.get(i),"stu_id")
										.toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}

	@Override
	public List<Map<String, Object>> getLikeEatLog(String id, int order) {
		String sql = "select * from (select t.*,rownum r from ( select * from tl_card_like_eat where stu_id=? order by count_ desc ) t ) where r<=? ";
		return basedao.getLogDao().getJdbcTemplate()
				.queryForList(sql, new Object[] { id, order });
	}

	@Override
	public Page getLikeShop(int currentPage,int numPerPage,Integer totalRows,Integer status) {
		String sql="select t1.cou count_,t1.su sum_,decode(t2.cou,0,'0.00%',round(t1.cou / t2.cou * 100, 2) || '%') count_ratio,decode(t2.su,0,'0.00%',round(t1.su/t2.su*100,2) || '%') sum_ratio ,t1.name_,t1.cid card_id,t1.sno stu_id from "+
				"(select nvl(sum(a.pay_money),0) su,nvl (count(*),0) cou ,d.name_,a.card_id cid,s.no_ sno from t_card_pay a                                                          "+
				"inner join t_card_port p on a.card_port_id=p.no_                                                                                                                    "+
				"inner join t_card_dept d on p.card_dept_id=d.id                                                                                                                     "+
				"inner join t_card c on a.card_id=c.no_                                                                                                                              "+
				"right join t_stu s on c.people_id=s.no_                                                                                                                             "+
				"where a.card_deal_id='215' and                                                                                                                                      "+
				"s.stu_state_code='1'                                                           "+
				"group by s.no_,a.card_id,d.name_                                                                                                                                    "+
				"order by s.no_,cou desc) t1                                                                                                                                         "+
				"inner join                                                                                                                                                          "+
				"(select nvl(sum(a.pay_money),0) su,nvl (count(*),0) cou ,a.card_id cid,s.no_ sno from t_card_pay a                                                                  "+
				"inner join t_card_port p on a.card_port_id=p.no_                                                                                                                    "+
				"inner join t_card_dept d on p.card_dept_id=d.id                                                                                                                     "+
				"inner join t_card c on a.card_id=c.no_                                                                                                                              "+
				"right join t_stu s on c.people_id=s.no_                                                                                                                             "+
				"where a.card_deal_id='215' and                                                                                                                                      "+
				"s.stu_state_code='1'                                                           "+
				"group by s.no_,a.card_id                                                                                                                                            "+
				"order by s.no_,cou desc) t2                                                                                                                                         "+
				"on t1.cid=t2.cid                                                                                                                                                    ";
		String tempTable="t_temp_likeShop";
		String tempSql="select * from "+tempTable;
		Page page=null;
		String delSql="delete "+tempTable;
		if(status==0){
			basedao.getBaseDao().executeSql(delSql);
			sql= "insert into "+tempTable+" value (" + sql+ " )";
			basedao.getBaseDao().executeSql(sql);
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else if (status == 1) {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		}
		return page;
	}

	@Override
	public void saveLikeShopLog(List<Map<String, Object>> list,boolean isfrist) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		if(isfrist){
			String delSql="delete tl_card_like_shop";
			basedao.getLogDao().executeSql(delSql);
		}
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_card_like_shop values (?, ?, ?, ?, ?, ?, ?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setInt(1,MapUtils.getIntValue(LIST.get(i),"count_"));
								ps.setString(2, MapUtils.getString(LIST.get(i),"count_ratio")
										.toString());
								ps.setDouble(3,MapUtils.getDoubleValue(LIST.get(i),"sum_"));
								ps.setString(4, MapUtils.getString(LIST.get(i),"sum_ratio")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"name_")
										.toString());
								ps.setString(6, MapUtils.getString(LIST.get(i),"card_id")
										.toString());
								ps.setString(7, MapUtils.getString(LIST.get(i),"stu_id")
										.toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}

	@Override
	public List<Map<String, Object>> getLikeShopLog(String id, int order) {
		String sql = "select * from (select t.*,rownum r from ( select * from tl_card_like_shop where stu_id=? order by count_ desc ) t ) where r<=? ";
		return basedao.getLogDao().getJdbcTemplate()
				.queryForList(sql, new Object[] { id, order });
	}

	@Override
	public Page getLikeWash(int currentPage,int numPerPage,Integer totalRows,Integer status) {
		String sql="select t1.cou count_,t1.su sum_,decode(t2.cou,0,'0.00%',round(t1.cou / t2.cou * 100, 2) || '%') count_ratio,decode(t2.su,0,'0.00%',round(t1.su/t2.su*100,2) || '%') sum_ratio ,t1.name_,t1.cid card_id,t1.sno stu_id from "+
				"(select nvl(sum(a.pay_money),0) su,nvl (count(*),0) cou ,d.name_,a.card_id cid,s.no_ sno from                                                                       "+
				"(select substr(t.time_,0,10),sum(t.pay_money) pay_money,t.card_id,t.card_port_id from t_card_pay t                                                                  "+
				"        where t.card_deal_id='211' group by t.card_id,substr(t.time_,0,10),t.card_port_id ) a                                                                       "+
				"inner join t_card_port p on a.card_port_id=p.no_                                                                                                                    "+
				"inner join t_card_dept d on p.card_dept_id=d.id                                                                                                                     "+
				"inner join t_card c on a.card_id=c.no_                                                                                                                              "+
				"right join t_stu s on c.people_id=s.no_                                                                                                                             "+
				"where s.stu_state_code='1'                                                     "+
				"group by s.no_,a.card_id,d.name_                                                                                                                                    "+
				"order by s.no_,cou desc) t1                                                                                                                                         "+
				"inner join                                                                                                                                                          "+
				"(select nvl(sum(a.pay_money),0) su,nvl (count(*),0) cou ,a.card_id cid,s.no_ sno from                                                                               "+
				"(select substr(t.time_,0,10),sum(t.pay_money) pay_money,t.card_id,t.card_port_id from t_card_pay t                                                                  "+
				"        where t.card_deal_id='211' group by t.card_id,substr(t.time_,0,10),t.card_port_id ) a                                                                       "+
				"inner join t_card_port p on a.card_port_id=p.no_                                                                                                                    "+
				"inner join t_card_dept d on p.card_dept_id=d.id                                                                                                                     "+
				"inner join t_card c on a.card_id=c.no_                                                                                                                              "+
				"right join t_stu s on c.people_id=s.no_                                                                                                                             "+
				"where s.stu_state_code='1'                                                     "+
				"group by s.no_,a.card_id                                                                                                                                            "+
				"order by s.no_,cou desc) t2                                                                                                                                         "+
				"on t1.cid=t2.cid                                                                                                                                                    ";
		String tempTable="t_temp_likeWash";
		String tempSql="select * from "+tempTable;
		Page page=null;
		String delSql="delete "+tempTable;
		if(status==0){
			basedao.getBaseDao().executeSql(delSql);
			sql= "insert into "+tempTable+" value (" + sql+ " )";
			basedao.getBaseDao().executeSql(sql);
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else if (status == 1) {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		}
		return page;
	}

	@Override
	public void saveLikeWashLog(List<Map<String, Object>> list,boolean isfrist) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		if(isfrist){
			String delSql="delete tl_card_like_wash";
			basedao.getLogDao().executeSql(delSql);
		}
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_card_like_wash values (?, ?, ?, ?, ?, ?, ?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setInt(1,MapUtils.getIntValue(LIST.get(i),"count_"));
								ps.setString(2, MapUtils.getString(LIST.get(i),"count_ratio")
										.toString());
								ps.setDouble(3,MapUtils.getDoubleValue(LIST.get(i),"sum_"));
								ps.setString(4, MapUtils.getString(LIST.get(i),"sum_ratio")
										.toString());
								ps.setString(5, MapUtils.getString(LIST.get(i),"name_")
										.toString());
								ps.setString(6, MapUtils.getString(LIST.get(i),"card_id")
										.toString());
								ps.setString(7, MapUtils.getString(LIST.get(i),"stu_id")
										.toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}

	@Override
	public List<Map<String, Object>> getLikeWashLog(String id, int order) {
		String sql = "select * from (select t.*,rownum r from ( select * from tl_card_like_wash where stu_id=? order by count_ desc ) t ) where r<=?";
		return basedao.getLogDao().getJdbcTemplate()
				.queryForList(sql, new Object[] { id, order });
	}

	@Override
	public List<Map<String, Object>> getAllRecCount() {
		String sql="select t1.count_ ,d.id deal_id,d.name_,t1.end_year from                                     "+
				" (select count(t.money_) count_,t.card_deal_id,t.end_year from                              "+
				"  (select t2.*,(s.Enroll_Grade+s.LENGTH_SCHOOLING) end_year from t_card_recharge t2         "+
				"    inner join t_card c on t2.card_id=c.no_                                                 "+
				"    right join t_stu s on c.people_id=s.no_                                                 "+
				"    where s.stu_state_code='1' ) t  "+
				" group by t.card_deal_id ,t.end_year) t1                                                    "+
				"inner join t_code_card_deal d on t1.card_deal_id=d.id                                       "+
				"order by t1.end_year,count_ desc                                                            ";
		return basedao.getBaseDao().querySqlList(sql);
	}

	@Override
	public void saveAllRecCountLog(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		String delSql="delete tl_card_all_rec_count";
		basedao.getLogDao().executeSql(delSql);
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_card_all_rec_count values (?, ?, ?, ?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(1, MapUtils.getString(LIST.get(i),"count_")
										.toString());
								ps.setString(2, MapUtils.getString(LIST.get(i),"deal_id")
										.toString());
								ps.setString(3, MapUtils.getString(LIST.get(i),"name_")
										.toString());
								ps.setString(4, MapUtils.getString(LIST.get(i),"end_year")
										.toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}

	@Override
	public List<Map<String, Object>> getAllRecCountLog(String endYear) {
		String sql = "select * from tl_card_all_rec_count where end_year= ?";
		return basedao.getLogDao().getJdbcTemplate()
				.queryForList(sql, new Object[] { endYear });
	}

	@Override
	public Page getRecCount(int currentPage,int numPerPage,Integer totalRows,Integer status) {
		String sql="select t2.a count_,t2.su sum_,d.name_,t2.card_id,t2.card_deal_id deal_id,s.no_ stu_id from "+
				" ( select nvl(count(*),0) a,nvl(sum(money_ ),0) su ,br.card_id,br.card_deal_id  from                     "+
				" t_card_recharge br group by br.card_id,br.card_deal_id ) t2                               "+
				" inner join t_code_card_deal d on t2.card_deal_id=d.id                                     "+
				"inner join t_card c on t2.card_id=c.no_                                                    "+
				"right join t_stu s on c.people_id=s.no_                                                    "+
				"where s.stu_state_code='1'         ";
			//	"order by s.no_,count_ desc ";==注释掉,插入不能有order by
		String tempTable="t_temp_RecCount";
		String tempSql="select * from "+tempTable;
		Page page=null;
		String delSql="delete "+tempTable;
		if(status==0){
			basedao.getBaseDao().executeSql(delSql);
			sql= "insert into "+tempTable+" value (" + sql+ " )";
			basedao.getBaseDao().executeSql(sql);
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else if (status == 1) {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		} else {
			page = new Page(tempSql, currentPage, numPerPage, basedao
					.getBaseDao().getJdbcTemplate(), totalRows);
		}
		return page;
	}

	@Override
	public void saveRecCountLog(List<Map<String, Object>> list,boolean isfrist) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		if(isfrist){
			String delSql="delete tl_card_rec_count";
			basedao.getLogDao().executeSql(delSql);
		}
		basedao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_card_rec_count values (?, ?, ?, ?, ?, ?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setInt(1,MapUtils.getIntValue(LIST.get(i),"count_"));
								ps.setDouble(2,MapUtils.getDoubleValue(LIST.get(i),"sum_"));
								ps.setString(3, MapUtils.getString(LIST.get(i),"deal_id"));
								ps.setString(4, MapUtils.getString(LIST.get(i),"name_"));
								ps.setString(5, MapUtils.getString(LIST.get(i),"card_id"));
								ps.setString(6, MapUtils.getString(LIST.get(i),"stu_id"));
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}

	@Override
	public List<Map<String, Object>> getRecCountLog(String id, int order) {
		String sql = "select * from (select t.*,rownum r from ( select name_ name,sum_ value from tl_card_rec_count where stu_id='"+id+"' order by count_ desc ) t ) where r<="+order;
		return basedao.getLogDao().queryListMapInLowerKeyBySql(sql);
	}

	@Override
	public Map<String, List<Map<String, Object>>> getMealsCount() {
		try{
			this.dropTempMealsCount();
		}catch(Exception se){
			
		}
		this.createTempMealsCount();
		String breakfast = "breakfast", lunch = "lunch", dinner = "dinner";
		String sql1 = "SELECT ROWNUM RN ,T.* FROM (SELECT * FROM TEMP_CARD_MEALS ORDER BY BYXN,SEX_CODE,COUNT_ZC)T";
		String sql2 = "SELECT ROWNUM RN ,T.* FROM (SELECT * FROM TEMP_CARD_MEALS ORDER BY BYXN,SEX_CODE,COUNT_MIDDLE)T";
		String sql3 = "SELECT ROWNUM RN ,T.* FROM (SELECT * FROM TEMP_CARD_MEALS ORDER BY BYXN,SEX_CODE,COUNT_WC)T";
		Map<String, List<Map<String, Object>>> mealsMap = new HashMap<String, List<Map<String, Object>>>();
		mealsMap.put(breakfast, basedao.getBaseDao().getJdbcTemplate()
				.queryForList(sql1));
		mealsMap.put(lunch, basedao.getBaseDao().getJdbcTemplate()
				.queryForList(sql2));
		mealsMap.put(dinner, basedao.getBaseDao().getJdbcTemplate()
				.queryForList(sql3));
		return mealsMap;
	}

	@Override
	public void createTempMealsCount() {
		String cTmpSql = "CREATE TABLE TEMP_CARD_MEALS AS SELECT PEOPLE_ID STU_NO,SEX_CODE,BYXN,"
				+ " COUNT(CASE CB WHEN 1 THEN DATE_ END ) COUNT_ZC,COUNT(CASE CB WHEN 2 THEN DATE_ END ) COUNT_MIDDLE,"
				+ " COUNT(CASE CB WHEN 3 THEN DATE_ END ) COUNT_WC FROM (SELECT PEOPLE_ID,DATE_,CB,BYXN,SEX_CODE,COUNT(*) COUNT_ FROM ("
				+ " SELECT PAY.*,CASE WHEN SUBSTR(PAY.TIME_,12,5) BETWEEN '05:00' AND '09:59' THEN 1 WHEN SUBSTR(PAY.TIME_,12,5) "
				+ " BETWEEN '10:00' AND '15:59' THEN 2 WHEN SUBSTR(PAY.TIME_,12,5) BETWEEN '16:00' AND '23:59' THEN 3 END CB,"
				+ " (STU.LENGTH_SCHOOLING+TO_NUMBER(STU.ENROLL_GRADE)) BYXN,C.PEOPLE_ID,STU.SEX_CODE,SUBSTR(PAY.TIME_,1,10) DATE_ "
				+ " FROM T_CARD_PAY PAY INNER JOIN T_CARD C ON PAY.CARD_ID = C.NO_ INNER JOIN T_STU STU ON STU.NO_ = C.PEOPLE_ID WHERE PAY.CARD_DEAL_ID = 210"
				+ " ) GROUP BY PEOPLE_ID,DATE_,CB,BYXN,SEX_CODE) where SEX_CODE is not null GROUP BY PEOPLE_ID,SEX_CODE,BYXN  ";
		basedao.getBaseDao().getJdbcTemplate().execute(cTmpSql);
	}

	@Override
	public void dropTempMealsCount() {
		String dTmpSql = "DROP TABLE TEMP_CARD_MEALS";
		basedao.getBaseDao().getJdbcTemplate().execute(dTmpSql);
	}

	@Override
	public void createTempMealsAvg() {
		String cTmpSql = "CREATE TABLE TEMP_CARD_MEALS_STUAVG AS "
				+ " SELECT PEOPLE_ID STU_NO,BYXN,SEX_CODE,AVG(CASE CB WHEN 1 THEN SUM_ END ) AVG_ZC,"
				+ " AVG(CASE CB WHEN 2 THEN SUM_ END ) AVG_MIDDLE,AVG(CASE CB WHEN 3 THEN SUM_ END ) AVG_WC  FROM ("
				+ " SELECT PEOPLE_ID,DATE_,CB,BYXN,SEX_CODE,SUM(PAY_MONEY) SUM_ FROM ("
				+ " SELECT PAY.*,CASE WHEN SUBSTR(PAY.TIME_,12,5) BETWEEN '05:00' AND '09:59' THEN 1"
				+ " WHEN SUBSTR(PAY.TIME_,12,5) BETWEEN '10:00' AND '15:59' THEN 2"
				+ " WHEN SUBSTR(PAY.TIME_,12,5) BETWEEN '16:00' AND '23:59' THEN 3 END CB,"
				+ " (STU.LENGTH_SCHOOLING+TO_NUMBER(STU.ENROLL_GRADE)) BYXN,C.PEOPLE_ID,STU.SEX_CODE,SUBSTR(PAY.TIME_,1,10) DATE_ FROM T_CARD_PAY PAY"
				+ " INNER JOIN T_CARD C ON PAY.CARD_ID = C.NO_ INNER JOIN T_STU STU ON STU.NO_ = C.PEOPLE_ID WHERE PAY.CARD_DEAL_ID = 210"
				+ " ) GROUP BY PEOPLE_ID,DATE_,CB,BYXN,SEX_CODE) GROUP BY PEOPLE_ID,BYXN,SEX_CODE";
		basedao.getBaseDao().getJdbcTemplate().execute(cTmpSql);
	}

	@Override
	public void dropTempMealsAvg() {
		String dTmpSql = "DROP TABLE TEMP_CARD_MEALS_STUAVG";
		basedao.getBaseDao().getJdbcTemplate().execute(dTmpSql);
	}

	@Override
	public List<Map<String, Object>> getMealsAvg() {
		try{
			this.dropTempMealsAvg();
		}catch(Exception se){
			
		}
		this.createTempMealsAvg();
		String sql = "SELECT STU_NO,BYXN,SEX_CODE,AVG_ZC,AVG_MIDDLE,AVG_WC FROM TEMP_CARD_MEALS_STUAVG";
		return basedao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void saveMealsAvg(List<Map<String, Object>> list) {
		final List<Map<String, Object>> saveList = list;
		String delSql = "DELETE FROM TL_CARD_MEALS_STUAVG";
		basedao.getLogDao().getJdbcTemplate().execute(delSql);
		String sql = "INSERT INTO TL_CARD_MEALS_STUAVG VALUES(?,?,?,?,?,?)";

		basedao.getLogDao().getJdbcTemplate()
				.batchUpdate(sql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setString(1, MapUtils.getString(saveList.get(i),"STU_NO")
								.toString());
						ps.setString(2, MapUtils.getString(saveList.get(i),"BYXN").toString());
						ps.setString(3, MapUtils.getString(saveList.get(i),"SEX_CODE")
								.toString());
						ps.setString(4, String.format(
								"%.2f",
								MapUtils.getString(saveList.get(i),"AVG_ZC") == "" ? 0.00f
										: Float.parseFloat(MapUtils.getString(saveList.get(i),"AVG_ZC").toString())));
						ps.setString(
								5,
								String.format(
										"%.2f",
										MapUtils.getString(saveList.get(i),"AVG_MIDDLE") == "" ? 0.00f
												: Float.parseFloat(MapUtils.getString(saveList.get(i),"AVG_MIDDLE").toString())));
						ps.setString(6, String.format(
								"%.2f",
								MapUtils.getString(saveList.get(i),"AVG_WC") == "" ? 0.00f
										: Float.parseFloat(MapUtils.getString(saveList.get(i),"AVG_WC").toString())));

					}

					@Override
					public int getBatchSize() {
						return saveList.size();
					}
				});
	}

	@Override
	public List<Map<String, Object>> getMealsAvgLog(String id) {
		String sql = "SELECT AVG_.*,T.AVG_ZC_ALL,T.AVG_MIDDLE_ALL,T.AVG_WC_ALL FROM TL_CARD_MEALS_STUAVG AVG_ LEFT JOIN ("
				+ " SELECT BYXN,SEX_CODE,ROUND(AVG(AVG_ZC),2) AVG_ZC_ALL,ROUND(AVG(AVG_MIDDLE),2) AVG_MIDDLE_ALL ,ROUND(AVG(AVG_WC),2) "
				+ " AVG_WC_ALL FROM TL_CARD_MEALS_STUAVG GROUP BY BYXN,SEX_CODE)"
				+ " T ON T.BYXN = AVG_.BYXN AND T.SEX_CODE = AVG_.SEX_CODE  WHERE STU_NO = ?";
		return basedao.getLogDao().getJdbcTemplate()
				.queryForList(sql, new Object[] { id });
	}

	@Override
	public List<Map<String, Object>> getMealsAvgLogByYearSex(String endYear,
			String sex) {
		String sql = "SELECT BYXN,SEX_CODE,ROUND(AVG(AVG_ZC),2) AVG_ZC_ALL,ROUND(AVG(AVG_MIDDLE),2) AVG_MIDDLE_ALL "
				+ ",ROUND(AVG(AVG_WC),2) AVG_WC_ALL FROM TL_CARD_MEALS_STUAVG WHERE BYXN=? AND SEX_CODE=? GROUP BY BYXN,SEX_CODE";
		return basedao.getLogDao().getJdbcTemplate()
				.queryForList(sql, new Object[] { endYear, sex });
	}

	@Override
	public List<Map<String, Object>> getMealsCountLog(String id) {
		String sql = "SELECT * FROM TL_CARD_MEALS WHERE STU_NO=?";
		return basedao.getLogDao().getJdbcTemplate()
				.queryForList(sql, new Object[] { id });
	}

	@Override
	public Page getCardDetailLog(int currentPage,int numPerPage, String tj) {
		String sql="select * from tl_card_detail where 1=1 "+ tj+" order by time_ desc";
		Page page = new Page(sql, currentPage, numPerPage, basedao.getLogDao().getJdbcTemplate(),null);
		return page;
	}

	@Override
	public List<Map<String, Object>> getCardDetailGroupByDeal(String id) {
		String sql="select t.card_deal_id id ,t.card_deal_name mc  from tl_card_detail t where stu_id ='"+id+"' group by t.card_deal_name,t.card_deal_id  order by id ";
		return basedao.getLogDao().queryListMapInLowerKeyBySql(sql);
	}

	@Override
	public List<Map<String, Object>> getCardDetailGroupByTime(String id) {
		String sql="select substr(t.time_,0,4) ||'年' mc,substr(t.time_,0,4) id  from tl_card_detail t where 1=1 and stu_id ='"+id+"' group by substr(t.time_,0,4)  order by id ";
		return basedao.getLogDao().queryListMapInLowerKeyBySql(sql);
	}
	
}
