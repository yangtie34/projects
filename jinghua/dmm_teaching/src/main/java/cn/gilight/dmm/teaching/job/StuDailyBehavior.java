package cn.gilight.dmm.teaching.job;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.dao.SmartDao;
import cn.gilight.dmm.teaching.entity.TStuBehaviorDaily;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("stuDailyBehavior")
public class StuDailyBehavior {
		@Resource
		private BaseDao baseDao;
		@Resource
		private BusinessDao businessDao;
		@Resource
		private HibernateDao hibernateDao;
		@Resource
		private SmartDao samrtDao; 
		private Logger log = Logger.getLogger(this.getClass());
		
		public static final SimpleDateFormat SDF = new SimpleDateFormat(
				"yyyy-MM-dd");
		public static final String JYLX_SQL = " and a.card_deal_id = '"+Constant.CODE_CARD_DEAL_210+"' ";
		@Transactional
		public JobResultBean doNowBhrDaily(){
			Long beginTime = System.currentTimeMillis();
			String jobName = "t_stu_behavior_daily最近七天的学生行为";
			String day = DateUtils.getLastWeek();
			String now = DateUtils.getNowDate();
			JobResultBean result  = getList(day,now,jobName,beginTime);
			return result;
		}
		@Transactional
		public JobResultBean doBeforeBhrDaily(){
			Long beginTime = System.currentTimeMillis();
			String jobName = "t_stu_behavior_daily最近一年的学生行为";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -1);
			String day = SDF.format(calendar.getTime());
			String now = DateUtils.getNowDate();
			JobResultBean result = getList(day,now,jobName,beginTime);
			return result;
		}
		private void begin(String info){
			log.warn("======== begin["+DateUtils.getNowDate2()+"]: "+info+" 初始化 ========");
		}
		private void info(String info){
			log.warn("======== info["+DateUtils.getNowDate2()+"]: "+info+" ========");
		}
		private void end(String info){
			log.warn("======== end["+DateUtils.getNowDate2()+"]: "+info+" ========");
		}
		private void error(String info){
			log.warn("======== error["+DateUtils.getNowDate2()+"]: "+info+" 停止执行========");
		}
		private JobResultBean doStuTimeBehavior(List<TStuBehaviorDaily> list,String jobName){
			JobResultBean result = new JobResultBean();
			Long beginTime = System.currentTimeMillis();
			try{
	 		if(!list.isEmpty()){
			hibernateDao.saveAllAutoCommit(list);
			info("插入"+jobName+list.size()+" 条");
	 		}else{
	 		info("插入0条");
	 		}
	 		String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setIsTrue(true);
			result.setMsg(info);
			}catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				String info = "#"+jobName+"#数据保存出错,"+e.getStackTrace();
				error(info);
				result.setIsTrue(false);
				result.setMsg(info);
				e.printStackTrace();
			}
			return result;
		}
		private String getsql(String table1,String table2,String join,String wheresql,Boolean islunch,String day){
			String str =  " min(substr(a.time_, 12, 4) || '0') mintime, "
			        + " max(substr(a.time_, 12, 4) || '0') maxtime ";
			if(islunch){
				str = " min(substr(a.time_, 12, 4) || '0') mintime";
			}
			String sql = "select  b.people_id as stu_id,"
					+ " substr(a.time_, 0, 10) as date_,"
					+   str
					+ " from "+table1+" a inner join "+table2+" b "
					+ " on a."+join+" = b.no_ where a.time_ like '"+day+"%'"
					+   wheresql
					+ " group by b.people_id,substr(a.time_, 0, 10)";
			return sql;
		}
		private JobResultBean getList(String start,String end,String jobName,Long beginTime){
			JobResultBean result = new JobResultBean();
			for(int k =0;k<DateUtils.getDayBetween(start, end)+1;k++){
				String day = getBeforeDate(end,k);
				String[] ary = EduUtils.getSchoolYearTerm(day);
 				int year = Integer.parseInt(ary[0].substring(0,4));
 				String stuSql = businessDao.getStuSql(year,PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
 			    String zc = getsql("t_card_pay","t_card","card_id",JYLX_SQL+" and substr(a.time_,12,2)<'"+Constant.Meal_Time_Group[0][2]+"' ",false,day);
 			    String yc = getsql("t_card_pay","t_card","card_id",JYLX_SQL+" and substr(a.time_,12,2)>='"+Constant.Meal_Time_Group[2][1]+"' ",false,day);
 			    String wc = getsql("t_card_pay","t_card","card_id",JYLX_SQL+" and substr(a.time_,12,2)>='"+Constant.Meal_Time_Group[1][1]+"' and substr(a.time_,12,2)<'"+Constant.Meal_Time_Group[1][2]+"'",true,day);
			    String dorm = getsql("t_dorm_rke","t_dorm_proof","dorm_proof_id","",false,day);
			    String sql = "select stu.no_ as stu_id,'"+day+"' as date_,zc.mintime as breakfast_,wc.mintime as lunch_,"
			    		+ " yc.mintime as dinner_,dorm.mintime as first_dormrke,dorm.maxtime as last_dormrke from ("+stuSql+") stu left join ("+zc+") zc on stu.no_ = zc.stu_id left join "
			    		+ " ("+wc+") wc on stu.no_ = wc.stu_id left join ("+yc+") yc on stu.no_ = yc.stu_id "
			    		+ " left join ("+dorm+") dorm on stu.no_ = dorm.stu_id "
			    		+ " where zc.stu_id is not null or wc.stu_id is not null "
			    		+ " or yc.stu_id is not null or dorm.stu_id is not null ";
 			    List<TStuBehaviorDaily> list = baseDao.queryForListBean(sql, TStuBehaviorDaily.class);
			if(!list.isEmpty()){
				String oldSql = " t_stu_behavior_daily where date_ = '"+day+"'";
				int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
				if(oldCount > 0){
					baseDao.delete("delete from "+oldSql);
					info("已删除"+day+"以后的数据");
				}
			}
			 result = doStuTimeBehavior(list, jobName);
			if(result.getIsTrue()==false){
				break;
			}
			}
			if(result.getIsTrue()){
				String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
				end(info);
				result.setMsg(info);
				DevUtils.p(info);
			}
			return result;
		}
		  private String getBeforeDate(String date,int len){
		    	Date dat = DateUtils.string2Date(date);
		    	Calendar cal = new GregorianCalendar(); 
				cal.setTime(dat);
				cal.add(Calendar.DATE, -len);
				dat = cal.getTime();
				return  DateUtils.date2String(dat);
		    }
	}