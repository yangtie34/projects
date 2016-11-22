package com.jhnu.product.manager.card.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.card.dao.StuPayHabitDao;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.StringUtils;

@Repository("stuPayHabitDao")
public class StuPayHabitDaoImpl implements StuPayHabitDao{

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getStuPays(String startDate,String endDate) {
		String sql = "select x.pay_date,dept_id,major_id ,x.stu_sex,round(sum(x.date_numbers)/count(*),2) pay_numbers ,round(sum(x.pay_money)/count(*),2) pay_money from ("
         + " select stu.no_ stu_id,stu.dept_id dept_id,stu.major_id major_id,xb.mc stu_sex,p.pay_date,count(*) date_numbers,sum(p.pay_money) pay_money from ("
         + " select pay.card_id,pay.time_,pay.pay_money,substr(pay.time_,0,10) pay_date from t_card_pay pay "
         + " where pay.time_ between '"+ startDate +"' and '"+ endDate +"') p"
         + " inner join t_card card on card.no_ = p.card_id"
         + " inner join t_stu stu on stu.no_ = card.people_id"
         + " left join dm_zxbz.t_zxbz_xb xb on xb.dm = stu.sex_code "
         + " group by stu.no_,p.pay_date,xb.mc,dept_id,major_id order by stu.no_ desc"
         + " ) x group by x.pay_date,x.stu_sex,dept_id,major_id order by x.pay_date desc,dept_id asc,major_id asc";
		
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public void saveStuPays(List<Map<String,Object>> list){
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		if(list != null && list.size()>0){
			String delSql="delete  tl_pay_habit_day hp where hp.pay_date = '" + MapUtils.getString(LIST.get(0),"pay_date").toString() +"'";
			baseDao.getLogDao().executeSql(delSql);
		}
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_pay_habit_day values (?, ?, ?, ?,?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"pay_date").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"dept_id").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"major_id").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"stu_sex").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"pay_numbers").toString());  
		                ps.setString(6, MapUtils.getString(LIST.get(i),"pay_money").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}
	
	public List<Map<String,Object>> getStuPaysLog(String dept_id,boolean isLeaf,String startDate,String endDate){
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2=" and (hd.dept_id = '"+ dept_id +"' or hd.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = " and hd.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and hd.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select pay_date,stu_sex,round(sum(hd.pay_numbers)/count(*) ,2) pay_numbers ,"
				+ " round(sum(hd.pay_money)/count(*),2) pay_money from tl_pay_habit_day hd where hd.pay_date between '"+ startDate +"' and '"+ endDate +"'"+sql2
				+ " group by pay_date,stu_sex order by pay_date,stu_sex";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getStuPaysByDay(String dept_id,boolean isLeaf,String startDate,String endDate) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="where stu.dept_id = '"+ dept_id +"' or stu.major_id = '"+ dept_id +"' ";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "where stu.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "where stu.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select s.stu_sex,round(sum(s.pay_numbers)/count(*),2) pay_numbers,round(sum(s.pay_money)/count(*),2) pay_money from ("
				+ " select x.pay_date,x.stu_sex,round(sum(x.date_numbers)/count(*),2) pay_numbers ,round(sum(x.pay_money)/count(*),2) pay_money from ("
				+ " select stu.no_ stu_id,xb.mc stu_sex,p.pay_date,count(*) date_numbers,sum(p.pay_money) pay_money from ("
				+ " select pay.card_id,pay.time_,pay.pay_money,substr(pay.time_,0,10) pay_date from t_card_pay pay "
				+ " where pay.time_ between '"+ startDate +"' and '"+ endDate +"') p"
				+ " inner join t_card card on card.no_ = p.card_id"
				+ " inner join t_stu stu on stu.no_ = card.people_id"
				+ " left join dm_zxbz.t_zxbz_xb xb on xb.dm = stu.sex_code"
				+ sql2 + " group by stu.no_,p.pay_date,xb.mc order by stu.no_ desc"
				+ " ) x group by x.pay_date,x.stu_sex order by x.pay_date desc) s group by s.stu_sex";
		
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	public List<Map<String,Object>> getStuEatNumbers(String startDate,String endDate){
		String sql = "select dept_id,major_id,pay_date,lx,count(*) stu_numbers from ("
         + " select dept_id,major_id,pay_date,card_id,pjcs,case when pjcs between '2.4' and '3' then '日均三餐' "
         + " when pjcs between '1.5' and '2.39' then '日均两餐'"
         + " when pjcs between '0' and '1.49' then '不足两餐'"
         + " end as lx  from ("
         + " select  d.dept_id,d.major_id,d.card_id,d.pay_date,round(count(d.card_id)/count(distinct d.pay_date),2) pjcs from"
         + " (select r.pay_date,r.dept_id,r.major_id,r.card_id,r.sj,count(*) pay_numbers,sum(r.pay_money) pay_money from"
         + " (select case when substr(pay.time_,12,2) <= 9 then '早餐'"
         + " when  substr(pay.time_,12,2) between '11' and '14' then '午餐'"
         + " when substr(pay.time_,12,2) between '16' and '24' then '晚餐'"
         + " else '' end as sj,pay.*,stu.dept_id,stu.major_id,substr(pay.time_,1,10) as pay_date"
         + " from t_card_pay pay  left join t_card card on card.no_ = pay.card_id"
         + " left join t_stu stu on stu.no_ = card.people_id "
         + " where pay.card_deal_id = '210' and pay.time_ between '"+ startDate +"' and '"+ endDate +"') r "
         + " where r.sj is not null group by r.pay_date,r.dept_id,r.major_id,r.card_id,r.sj) d group by d.dept_id,d.major_id, d.card_id,d.pay_date)) "
         + " group by dept_id,major_id,pay_date, lx order by dept_id,major_id,pay_date, lx";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public void saveStuEatNumbers(List<Map<String,Object>> list){
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		if(list != null && list.size()>0){
			String delSql = "delete tl_pay_habit_eat_numbers hen  where hen.pay_date = '"+ MapUtils.getString(LIST.get(0),"pay_date").toString() +"'";
			baseDao.getLogDao().executeSql(delSql);
		}
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_pay_habit_eat_numbers values (?, ?, ?, ?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"dept_id").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"major_id").toString()); 
		                ps.setString(3, MapUtils.getString(LIST.get(i),"pay_date").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"lx").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"stu_numbers").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}
	

	public List<Map<String,Object>> getStuEatNumbersLog(String dept_id,boolean isLeaf,String startDate,String endDate){
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2=" and (t.dept_id = '"+ dept_id +"' or t.major_id = '"+ dept_id +"') ";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = " and t.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and t.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		String sql = "select lx,round(sum(stu_numbers)/count(distinct pay_date),0) stu_numbers from tl_pay_habit_eat_numbers t "
				+ " where t.pay_date between '"+ startDate +"' and '"+ endDate +"' " +sql2+" group by lx";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}
	

	@Override
	public List<Map<String, Object>> getStuPayMoney(String startDate,String endDate) {
		String sql = "select dept_id,major_id,pay_date,xfqj ,count(card_id) numbers from ("
         + " select dept_id,major_id,card_id,sjxf,pay_numbers,pay_date,case when sjxf between '0' and '5' then '0-5元'"
         + " when sjxf between '5' and '8' then '5-8元'"
         + " when sjxf between '8' and '10' then '8-10元'"
         + " when sjxf between '10' and '13' then '10-13元'"
         + " when sjxf between '13' and '15' then '13-15元'"
         + " when sjxf between '15' and '20' then '15-20元' "
         + " when sjxf >20 then '高于20元' end as xfqj from("
         + " select dept_id,major_id,card_id,pay_date,count(*) pay_numbers ,sum(pay_money) sjxf from("
         + " select substr(pay.time_,0,10) as pay_date,pay.*,stu.dept_id,stu.major_id from t_card_pay pay "
         + " left join t_card card on card.no_ = pay.card_id"
         + " left join t_stu stu on stu.no_ = card.people_id "
         + " where pay.time_ between '"+ startDate +"' and '"+ endDate +"' ) "
         + " group by dept_id,major_id,card_id,pay_date)) group by dept_id,major_id,pay_date,xfqj order by major_id";
		
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	

	@Override
	public void saveStuPayMoney(List<Map<String,Object>> list){
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		if(list != null && list.size()>0){
			String delSql = "delete tl_pay_habit_money phm  where phm.pay_date = '"+ MapUtils.getString(LIST.get(0),"pay_date").toString() +"'";
			baseDao.getLogDao().executeSql(delSql);
		}
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_pay_habit_money values (?, ?, ?, ?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"dept_id").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"major_id").toString()); 
		                ps.setString(3, MapUtils.getString(LIST.get(i),"pay_date").toString()); 
		                ps.setString(4, MapUtils.getString(LIST.get(i),"xfqj").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"numbers").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}
	
	@Override
	public List<Map<String,Object>> getStuPayMoneyLog(String dept_id,boolean isLeaf,String startDate,String endDate){
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2=" and (t.dept_id = '"+ dept_id +"' or t.major_id = '"+ dept_id +"') ";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = " and t.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and t.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		String sql = "select xfqj,round(sum(numbers)/count(distinct pay_date),0) numbers from tl_pay_habit_money t"
				+ " where t.pay_date between '"+ startDate +"' and '"+ endDate +"'"
				+ sql2 + " group by xfqj";
		
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}
	
	
}
