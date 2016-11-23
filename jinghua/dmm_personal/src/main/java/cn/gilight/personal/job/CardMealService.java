package cn.gilight.personal.job;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;

@Service("cardMealService")
public class CardMealService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	/** 
	* @Description: 统计学生吃午餐次数及金额
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean cardLunchMeal(){
		log.warn("========begin : 统计学生吃午餐天数及金额任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		String date = DateUtils.getNowDate();
		try {
			String delsql = "DELETE FROM t_card_meal t";
			int nums = baseDao.delete(delsql);
			log.warn("======== running : 统计学生用餐次数及金额清空存储表，共"+nums+"条数据 ==============");
			String sql = "insert into t_card_meal select ts.stu_id,ts.enroll_grade,ts.sex,'' breakfast_count,nvl(count(distinct ts.pay_date),0) lunch_count "
					+ " ,'' dinner_count,'' breakfast_pay_sum,nvl(sum(ts.pay_money),0) lunch_pay_sum,'' dinner_pay_sum,'' more_two_meal_count"
					+ " ,'' more_three_meal_count,'"+date+"' date_ from ("
					+ " select tt.stu_id,tt.enroll_grade,tt.sex,tt.pay_money,tt.pay_date,case when tt.pay_time between '05:00' and '09:59' then 1 "
					+ " when tt.pay_time between '10:00' and '15:59' then 2 when tt.pay_time between '15:00' and '23:59' then 3 end cb from ("
					+ " select stu.no_ stu_id,stu.enroll_grade,co.name_ sex,t.pay_money,substr(t.time_,0,10) pay_date,substr(t.time_,12,5) pay_time from"
					+ " t_card_pay t left join t_card ca on ca.no_ = t.card_id inner join t_stu stu on stu.no_ = ca.people_id"
					+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = stu.sex_code"
					+ " left join t_code_card_deal ccd on ccd.code_ = t.card_deal_id where ccd.name_ = '餐费支出' ) tt) ts where ts.cb = 2"
					+ " group by ts.stu_id,ts.enroll_grade,ts.sex";
			baseDao.update(sql);
			result.setIsTrue(true);
			String info = "统计学生吃午餐天数及金额结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 统计学生吃午餐天数及金额出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
		
	/** 
	* @Description: 统计学生早餐次数
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean cardBreakfastMeal(){
		log.warn("========begin : 统计学生早餐次数及金额任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		String sql = "select ts.stu_id,nvl(count(distinct ts.pay_date),0) zccs,nvl(sum(ts.pay_money),0) zcje from ("
				+ " select tt.stu_id,tt.pay_money,tt.pay_date,case when tt.pay_time between '05:00' and '09:59' then 1 "
				+ " when tt.pay_time between '10:00' and '15:59' then 2 when tt.pay_time between '15:00' and '23:59' then 3 end cb from ("
				+ " select stu.no_ stu_id,t.pay_money,substr(t.time_,0,10) pay_date,substr(t.time_,12,5) pay_time "
				+ " from t_card_pay t left join t_card ca on ca.no_ = t.card_id inner join t_stu stu on stu.no_ = ca.people_id"
				+ " left join t_code_card_deal ccd on ccd.code_ = t.card_deal_id where ccd.name_ = '餐费支出' ) tt) ts "
				+ " where ts.cb = 1 group by ts.stu_id";
		
		try {
			final List<Map<String, Object>> datalist1 = baseDao.queryListInLowerKey(sql);
			if(datalist1 != null && datalist1.size()>0){
				log.warn("======== running : 统计学生早餐次数及金额开始插入结果，共更新"+datalist1.size() +"条数据 ==============");
				baseDao.batchUpdate(
						"update t_card_meal t set t.breakfast_count = ?,t.breakfast_pay_sum = ? where t.stu_id = ?",
						new BatchPreparedStatementSetter() {    
				           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
				        public void setValues(PreparedStatement ps, int i) throws SQLException {    
						        	ps.setInt(1,MapUtils.getIntValue(datalist1.get(i), "zccs"));
					        		ps.setDouble(2,MapUtils.getDoubleValue(datalist1.get(i), "zcje"));
					                ps.setString(3,MapUtils.getString(datalist1.get(i), "stu_id"));  
				              }    
				              //返回更新的结果集条数  
				        public int getBatchSize() {
				        	return datalist1.size(); 
				        }    
				 }); 
			}
			result.setIsTrue(true);
			String info = "统计学生早餐次数及金额结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 统计学生早餐次数及金额出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
	
	/** 
	* @Description: 统计学生满两餐天数
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean cardMoreTwoMeal(){
		log.warn("========begin : 统计学生满两餐天数任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		
		String sql = "select td.stu_id,nvl(count(td.pay_date),0) mlcts from("
				+ " select ts.stu_id,ts.pay_date, count(distinct ts.cb) cfcs from ("
				+ " select tt.stu_id,tt.pay_money,tt.pay_date,case when tt.pay_time between '05:00' and '09:59' then 1 "
				+ " when tt.pay_time between '10:00' and '15:59' then 2 when tt.pay_time between '15:00' and '23:59' then 3 end cb from ("
				+ " select stu.no_ stu_id,stu.enroll_grade,t.pay_money,substr(t.time_,0,10) pay_date,substr(t.time_,12,5) pay_time from t_card_pay t"
				+ " left join t_card ca on ca.no_ = t.card_id inner join t_stu stu on stu.no_ = ca.people_id"
				+ " left join t_code_card_deal ccd on ccd.code_ = t.card_deal_id where ccd.name_ = '餐费支出')tt)ts "
				+ " group by ts.stu_id,ts.pay_date)"
				+ " td where td.cfcs >= 2 group by td.stu_id";
		
		try {
			final List<Map<String, Object>> datalist1 = baseDao.queryListInLowerKey(sql);
			if(datalist1 != null && datalist1.size()>0){
				log.warn("======== running : 统计学生满两餐天数开始插入结果，共更新"+datalist1.size() +"条数据 ==============");
				baseDao.batchUpdate(
						"update t_card_meal t set t.more_two_meal_count = ? where t.stu_id = ?",
						new BatchPreparedStatementSetter() {    
				           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
				        public void setValues(PreparedStatement ps, int i) throws SQLException {    
				        		ps.setInt(1,MapUtils.getIntValue(datalist1.get(i), "mlcts"));
				        		ps.setString(2,MapUtils.getString(datalist1.get(i), "stu_id")); 
				              }    
				              //返回更新的结果集条数  
				        public int getBatchSize() {
				        	return datalist1.size(); 
				        }    
				 }); 
			}
			
			result.setIsTrue(true);
			String info = "统计学生满两餐天数结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 统计学生满两餐天数出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
	
	/** 
	* @Description: 统计学生满三餐天数
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean cardMoreThreeMeal(){
		log.warn("========begin : 统计学生满三餐天数任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		
		String sql = "select td.stu_id,nvl(count(td.pay_date),0) mccts from("
				+ " select ts.stu_id,ts.pay_date, count(distinct ts.cb) cfcs from ("
				+ " select tt.stu_id,tt.pay_money,tt.pay_date,case when tt.pay_time between '05:00' and '09:59' then 1 "
				+ " when tt.pay_time between '10:00' and '15:59' then 2 when tt.pay_time between '15:00' and '23:59' then 3 end cb from ("
				+ " select stu.no_ stu_id,t.pay_money,substr(t.time_,0,10) pay_date,substr(t.time_,12,5) pay_time from t_card_pay t"
				+ " left join t_card ca on ca.no_ = t.card_id inner join t_stu stu on stu.no_ = ca.people_id"
				+ " left join t_code_card_deal ccd on ccd.code_ = t.card_deal_id where ccd.name_ = '餐费支出')tt)ts group by ts.stu_id,ts.pay_date)"
				+ " td where td.cfcs >= 3 group by td.stu_id";
		
		try {
			final List<Map<String, Object>> datalist1 = baseDao.queryListInLowerKey(sql);
			if(datalist1 != null && datalist1.size()>0){
				log.warn("======== running : 统计学生满三餐天数插入结果，共更新"+datalist1.size() +"条数据 ==============");
				baseDao.batchUpdate(
						"update t_card_meal t set t.more_three_meal_count = ? where t.stu_id = ?",
						new BatchPreparedStatementSetter() {    
				           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
				        public void setValues(PreparedStatement ps, int i) throws SQLException {    
				        		 ps.setInt(1,MapUtils.getIntValue(datalist1.get(i), "mccts"));
				        		 ps.setString(2,MapUtils.getString(datalist1.get(i), "stu_id"));
				              }    
				              //返回更新的结果集条数  
				        public int getBatchSize() {
				        	return datalist1.size(); 
				        }    
				 }); 
			}
			
			result.setIsTrue(true);
			String info = "统计学生满三餐天数结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 统计学生满三餐天数出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
	
	
	/** 
	* @Description: 统计学生吃晚餐次数及金额
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean cardDinnerMeal(){
		log.warn("========begin : 统计学生吃晚餐次数及金额任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		String sql = "select ts.stu_id,nvl(count(distinct ts.pay_date),0) wcts ,nvl(sum(ts.pay_money),0) wcjes from ("
				+ " select tt.stu_id,tt.pay_money,tt.pay_date,case when tt.pay_time between '05:00' and '09:59' then 1 "
				+ " when tt.pay_time between '10:00' and '15:59' then 2 when tt.pay_time between '15:00' and '23:59' then 3 end cb from ("
				+ " select stu.no_ stu_id,t.pay_money,substr(t.time_,0,10) pay_date,substr(t.time_,12,5) pay_time from t_card_pay t"
				+ " left join t_card ca on ca.no_ = t.card_id inner join t_stu stu on stu.no_ = ca.people_id"
				+ " left join t_code_card_deal ccd on ccd.code_ = t.card_deal_id where ccd.name_ = '餐费支出' ) tt) ts where ts.cb = 3 group by ts.stu_id";
		
		try {
			final List<Map<String, Object>> datalist1 = baseDao.queryListInLowerKey(sql);
			if(datalist1 != null && datalist1.size()>0){
				log.warn("======== running : 统计学生吃晚餐次数及金额开始插入结果，共更新"+datalist1.size() +"条数据 ==============");
				baseDao.batchUpdate(
						"update t_card_meal t set t.dinner_count = ?,t.dinner_pay_sum = ? where t.stu_id = ?",
						new BatchPreparedStatementSetter() {    
				           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
				        public void setValues(PreparedStatement ps, int i) throws SQLException {    
						        	ps.setInt(1,MapUtils.getIntValue(datalist1.get(i), "wcts"));
					                ps.setDouble(2,MapUtils.getDoubleValue(datalist1.get(i), "wcjes"));
					                ps.setString(3,MapUtils.getString(datalist1.get(i), "stu_id"));
				              }    
				              //返回更新的结果集条数  
				        public int getBatchSize() {
				        	return datalist1.size(); 
				        }    
				 }); 
			}
			
			result.setIsTrue(true);
			String info = "统计学生吃晚餐次数及金额结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 统计学生吃晚餐次数及金额出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
		
}