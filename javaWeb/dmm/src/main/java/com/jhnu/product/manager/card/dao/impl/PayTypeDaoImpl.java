package com.jhnu.product.manager.card.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.card.dao.PayTypeDao;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.StringUtils;

@Repository("payTypeDao")
public class PayTypeDaoImpl implements PayTypeDao{
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getPay(String startDate,String endDate) {
		
		String sql ="select  dept_id,major_id,pay_date,card_deal_id,xfjd,count(*) xfcs ,sum(pay_money) pay_money from("
				 + " select t.*,substr(time_,0,10) pay_date, case "
				 + " when substr(time_,12,5)  between '06:00' and '10:00'  then '1' "
				 + " when substr(time_,12,5) between '10:01' and '16:00' then '2' "
				 + " else '3' "
				 + " end as xfjd from ("
				 + " select pay.*,stu.dept_id,stu.major_id from t_card_pay pay left join t_card card on card.no_ = pay.card_id"
				 + " left join t_stu stu on stu.no_ = card.people_id "
				 + " where  pay.time_ between '"+ startDate +"' and '"+ endDate +"' "
				 + " ) t) group by dept_id,major_id,pay_date,card_deal_id,xfjd";
		
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public void  savePay(List<Map<String,Object>> list){
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		if(list != null && list.size()>0){
			String delSql="delete  tl_pay p where p.pay_date = '" + MapUtils.getString(LIST.get(0),"pay_date").toString() +"'";
			baseDao.getLogDao().executeSql(delSql);
		}
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_pay values (?, ?, ?, ?,?,?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"dept_id").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"major_id").toString());
		                ps.setString(3, MapUtils.getString(LIST.get(i),"pay_date").toString());    
		                ps.setString(4, MapUtils.getString(LIST.get(i),"card_deal_id").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"xfjd").toString());  
		                ps.setString(6, MapUtils.getString(LIST.get(i),"xfcs").toString());  
		                ps.setString(7, MapUtils.getString(LIST.get(i),"pay_money").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<Map<String, Object>> getPayLog(String dept_id,boolean isLeaf,String type_code, String startDate,String endDate) {

		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and t.dept_id = '"+ dept_id +"' or t.major_id = '"+ dept_id +"' ";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and t.major_id in ("+ dept_id +") "; //显示某些学专业数据
			}else{
				sql2 = "and t.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select xfjd,sum(xfcs) xfcs,sum(pay_money) pay_money from tl_pay t "
				+ " where t.card_deal_id = '"+ type_code +"' and t.pay_date between '"+ startDate+"' and '"+ endDate + "' "
				+ sql2 +" group by xfjd";
	 return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPayDetail(String startDate, String endDate) {
		
		String sql ="select dept_id,major_id,pay_date,card_deal_id,pname ,name_, sum(decode(xfjd, '1', pay_money,0)) zao, "
         + " sum(decode(xfjd, '2', pay_money,0)) zhong, "
         + " sum(decode(xfjd, '3', pay_money,0)) wan "
         + " from ("
         + " select r.dept_id,r.major_id,r.card_deal_id,r.pname,r.name_,r.xfjd,pay_date,sum(pay_money) pay_money from"
         + " (select t.dept_id,t.major_id, t.card_deal_id,t.pay_money,substr(t.time_,0,10) pay_date,t.card_port_id,cd.name_ pname ,dept.name_ ,case "
         + " when substr(time_,12,5)  between '06:00' and '10:00'  then '1' "
         + " when substr(time_,12,5) between '10:01' and '16:00' then '2' "
         + " else '3' "
         + " end as xfjd from ( select pay.*,stu.dept_id,stu.major_id from t_card_pay pay "
         + " left join t_card card on card.no_ = pay.card_id"
         + " left join t_stu stu on stu.no_ = card.people_id "
         + " where  pay.time_ between '"+ startDate+"' and '"+ endDate +"' ) t"
         + " left join t_card_port port on t.card_port_id = port.no_"
         + " left join t_card_dept dept on dept.id = port.card_dept_id"
         + " left join t_card_dept cd on cd.id = dept.pid"
         + " ) r  where xfjd is not null group by r.dept_id,r.major_id,r.pay_date,r.card_deal_id, r.pname,r.name_,r.xfjd "
         + " order by r.dept_id ,r.major_id  ,pay_date desc,r.pname desc,r.name_ desc,r.xfjd desc "
         + " ) group by dept_id,major_id,pay_date,card_deal_id, pname, name_ order by pname,(zao+zhong+wan) desc";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void savePayDetail(List<Map<String, Object>> list) {
			final int COUNT = list.size();
			final List<Map<String,Object>> LIST=list;
			if(list != null && list.size()>0){
				String delSql="delete  tl_pay_time pt where pt.pay_date = '" + MapUtils.getString(LIST.get(0),"pay_date").toString() +"'";
				baseDao.getLogDao().executeSql(delSql);
			}
			baseDao.getLogDao().getJdbcTemplate().batchUpdate(
					"insert into tl_pay_time values (?, ?, ?, ?,?,?,?,?,?)",
					new BatchPreparedStatementSetter() {    
			           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
			        public void setValues(PreparedStatement ps, int i) throws SQLException {    
			                ps.setString(1, MapUtils.getString(LIST.get(i),"dept_id").toString());    
			                ps.setString(2, MapUtils.getString(LIST.get(i),"major_id").toString());
			                ps.setString(3, MapUtils.getString(LIST.get(i),"pay_date").toString());    
			                ps.setString(4, MapUtils.getString(LIST.get(i),"pname").toString());    
			                ps.setString(5, MapUtils.getString(LIST.get(i),"name_").toString());  
			                ps.setString(6, MapUtils.getString(LIST.get(i),"zao").toString());  
			                ps.setString(7, MapUtils.getString(LIST.get(i),"zhong").toString());  
			                ps.setString(8, MapUtils.getString(LIST.get(i),"wan").toString());  
			                ps.setString(9, MapUtils.getString(LIST.get(i),"card_deal_id").toString());  
			              }    
			              //返回更新的结果集条数  
			        public int getBatchSize() {
			        	return COUNT; 
			        }    
			 }); 
	}

	@Override
	public List<Map<String, Object>> getPayDetailLog(String dept_id,
			boolean isLeaf, String type_code, String startDate, String endDate) {

		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and t.dept_id = '"+ dept_id +"' or t.major_id = '"+ dept_id +"' ";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and t.major_id in ("+ dept_id +") "; //显示某些学专业数据
			}else{
				sql2 = "and t.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select pname,name_,sum(zao) zao,sum(zhong) zhong,sum(wan) wan from tl_pay_time t where t.card_deal_id = '"+ type_code +"' and "
				+ " t.pay_date between '"+ startDate  +"' and '"+ endDate +"' "+ sql2
				+ " group by pname,name_ order by pname,name_";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}

}
