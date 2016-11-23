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

@Service("cardDeptTop3Service")
public class CardDeptTop3Service {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
		
	/** 
	* @Description: 餐厅早中晚窗口top3
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean cardDeptTop3(){
		log.warn("========begin : 计算本月窗口排名top3任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		
		String date = DateUtils.getLastMonth().substring(0, 7);
		String sql1 = "select '"+date+"' month_,'早' eat_time,tt.code_ dept_code,tt.name_ dept_name,rn from ("
				+ " select st.*,rownum rn from("
				+ " select cd.code_,cd.name_,sum(t.pay_money) total_ from t_card_pay t "
				+ " left join t_card_port cp on cp.no_ = t.card_port_id"
				+ " left join t_card_dept cd on cd.code_ = cp.card_dept_id "
				+ " where t.time_ like '"+date+"%' and  substr(t.time_,12,2)<10 and t.card_deal_id = '210'  and cd.name_ not like '%其他%'"
				+ " group by cd.code_,cd.name_ order by total_ desc) st ) tt where tt.rn <= 3";
		String sql2 = "select '"+date+"' month_,'午' eat_time,tt.code_ dept_code,tt.name_ dept_name,rn from ("
				+ " select st.*,rownum rn from("
				+ " select cd.code_,cd.name_,count(t.pay_money) total_ from t_card_pay t "
				+ " left join t_card_port cp on cp.no_ = t.card_port_id"
				+ " left join t_card_dept cd on cd.code_ = cp.card_dept_id "
				+ " where t.time_ like '"+date+"%' and  substr(t.time_,12,2) between '10' and '15' and t.card_deal_id = '210'  and cd.name_ not like '%其他%'"
				+ " group by cd.code_,cd.name_ order by total_ desc) st ) tt where tt.rn <= 3";
		String sql3 = "select '"+date+"' month_,'晚' eat_time,tt.code_ dept_code,tt.name_ dept_name,rn from ("
				+ " select st.*,rownum rn from("
				+ " select cd.code_,cd.name_,count(t.pay_money) total_ from t_card_pay t "
				+ " left join t_card_port cp on cp.no_ = t.card_port_id"
				+ " left join t_card_dept cd on cd.code_ = cp.card_dept_id "
				+ " where t.time_ like '"+date+"%' and  substr(t.time_,12,2)>16 and t.card_deal_id = '210'  and cd.name_ not like '%其他%'"
				+ " group by cd.code_,cd.name_ order by total_ desc) st ) tt where tt.rn <= 3";
		try {
			final List<Map<String, Object>> datalist1 = baseDao.queryForList(sql1);
			if(datalist1 != null && datalist1.size()>0){
				String delsql = "DELETE FROM t_card_dept_month t where t.eat_time = '早'";
				int nums = baseDao.delete(delsql);
				log.warn("======== running : 计算本月窗口排名top3清空早餐存储表，共"+nums+"条数据 ==============");
				log.warn("======== running : 计算本月窗口排名top3早餐开始插入结果，共"+datalist1.size() +"条数据 ==============");
				baseDao.batchUpdate(
						"insert into t_card_dept_month values(?,?,?,?,?)",
						new BatchPreparedStatementSetter() {    
				           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
				        public void setValues(PreparedStatement ps, int i) throws SQLException {    
				                ps.setString(1,MapUtils.getString(datalist1.get(i), "MONTH_"));
				                ps.setString(2,MapUtils.getString(datalist1.get(i), "EAT_TIME"));
				                ps.setString(3,MapUtils.getString(datalist1.get(i), "DEPT_CODE")); 
				                ps.setString(4,MapUtils.getString(datalist1.get(i), "DEPT_NAME")); 
				                ps.setString(5,MapUtils.getString(datalist1.get(i), "RN")); 
				              }    
				              //返回更新的结果集条数  
				        public int getBatchSize() {
				        	return datalist1.size(); 
				        }    
				 }); 
			}
			final List<Map<String, Object>> datalist2 = baseDao.queryForList(sql2);
			if(datalist2 != null && datalist2.size()>0){
				String delsql = "DELETE FROM t_card_dept_month t where t.eat_time = '午'";
				int nums = baseDao.delete(delsql);
				log.warn("======== running : 计算本月窗口排名top3清空午餐存储表，共"+nums+"条数据 ==============");
				log.warn("======== running : 计算本月窗口排名top3午餐开始插入结果，共"+datalist2.size() +"条数据 ==============");
				baseDao.batchUpdate(
						"insert into t_card_dept_month values(?,?,?,?,?)",
						new BatchPreparedStatementSetter() {    
				           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
				        public void setValues(PreparedStatement ps, int i) throws SQLException {    
				                ps.setString(1,MapUtils.getString(datalist2.get(i), "MONTH_"));
				                ps.setString(2,MapUtils.getString(datalist2.get(i), "EAT_TIME"));
				                ps.setString(3,MapUtils.getString(datalist2.get(i), "DEPT_CODE")); 
				                ps.setString(4,MapUtils.getString(datalist2.get(i), "DEPT_NAME")); 
				                ps.setString(5,MapUtils.getString(datalist2.get(i), "RN")); 
				              }    
				              //返回更新的结果集条数  
				        public int getBatchSize() {
				        	return datalist2.size(); 
				        }    
				 }); 
			}
			final List<Map<String, Object>> datalist3 = baseDao.queryForList(sql3);
			if(datalist3 != null && datalist3.size()>0){
				String delsql = "DELETE FROM t_card_dept_month t where t.eat_time = '晚'";
				int nums = baseDao.delete(delsql);
				log.warn("======== running : 计算本月窗口排名top3清空晚餐存储表，共"+nums+"条数据 ==============");
				log.warn("======== running : 计算本月窗口排名top3晚餐开始插入结果，共"+datalist3.size() +"条数据 ==============");
				baseDao.batchUpdate(
						"insert into t_card_dept_month values(?,?,?,?,?)",
						new BatchPreparedStatementSetter() {    
				           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
				        public void setValues(PreparedStatement ps, int i) throws SQLException {    
				                ps.setString(1,MapUtils.getString(datalist3.get(i), "MONTH_"));
				                ps.setString(2,MapUtils.getString(datalist3.get(i), "EAT_TIME"));
				                ps.setString(3,MapUtils.getString(datalist3.get(i), "DEPT_CODE")); 
				                ps.setString(4,MapUtils.getString(datalist3.get(i), "DEPT_NAME")); 
				                ps.setString(5,MapUtils.getString(datalist3.get(i), "RN")); 
				              }    
				              //返回更新的结果集条数  
				        public int getBatchSize() {
				        	return datalist3.size(); 
				        }    
				}); 
			}
			result.setIsTrue(true);
			String info = "计算本月窗口排名top3结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 计算本月窗口排名top3出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
		
}