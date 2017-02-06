package cn.gilight.dmm.xg.job;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.xg.entity.TStuWarningNotstay;
import cn.gilight.dmm.xg.entity.TStuWarningSkipClasses;
import cn.gilight.dmm.xg.entity.TStuWarningStayLate;
import cn.gilight.dmm.xg.entity.TStuWarningStayNotin;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.StringUtils;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月12日 下午4:10:35
 */
@Service("stuWarningJob")
public class StuWarningJob {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private BusinessService businessService;
	
	private Logger log = Logger.getLogger(this.getClass());

	private static final String T_SkipClasses = Constant.TABLE_T_STU_WARNING_SKIP_CLASSES;
	private static final String T_Notstay     = Constant.TABLE_T_STU_WARNING_NOTSTAY;
	private static final String T_Stay_Late   = Constant.TABLE_T_STU_WARNING_STAY_LATE;
	private static final String T_Stay_Notin  = Constant.TABLE_T_STU_WARNING_STAY_NOTIN;
	private static final String T_Stay_Late_Old  = Constant.TABLE_T_CARD_STAY_LATE;
	private static final String T_Stay_Notin_Old = Constant.TABLE_T_CARD_STAY_NOTIN;

	/**
	 * 疑似不在校
	 * @return JobResultBean
	 */
	@Transactional
	public JobResultBean doStayNotin(){
		Long beginTime = System.currentTimeMillis();
		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		String jobName = "#疑似不在校学生"+yesterDay+"#";
		begin(jobName);
		// 判断昨天是否需要初始化
		boolean isDo = false;
		String schoolYear = null, termCode = null;
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select t.* from t_school_start t order by t.school_year,t.term_code");
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date");
			// 2016-09-01开学，从2016-09-04开始预警
			if(DateUtils.compareEqual(yesterDay, startDate) && DateUtils.compareEqual(endDate, yesterDay)
				&& DateUtils.compareEqual(yesterDay, DateUtils.getNextDayByLen(startDate, Constant.WARNING_STAY_NOTIN_DAY_LEN))){
				isDo = true;
				schoolYear= MapUtils.getString(map, "school_year");
				termCode  = MapUtils.getString(map, "term_code");
				break;
			}
		}
		//定义结果
		JobResultBean result = new JobResultBean();
		String info = jobName;
		if(!isDo){
			info += "，不在上课时间";
		}else{
			result = doStayNotinByDate(yesterDay, schoolYear, termCode);
		}
		info += "，结束执行。共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
		end(info);
		result.setMsg(info);
		DevUtils.p(info);
		return result;
	}
	/**
	 * 历史-疑似不在校
	 * @return JobResultBean
	 */
	@Transactional
	public JobResultBean doStayNotinHistory(){
		Long beginTime = System.currentTimeMillis();
		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		String jobName = "#历史-疑似不在校学生#";
		begin(jobName);
		//定义结果
		JobResultBean result = new JobResultBean();
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t order by t.school_year,t.term_code");
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date"),
				   everyDate = startDate;
			// (startDate <= everyDate <= endDate) && (everyDate < yesterDay) 历史日期判断
			for( ; (DateUtils.compareEqual(endDate, everyDate)&&DateUtils.compare(yesterDay, everyDate))
					&& DateUtils.compareEqual(yesterDay, DateUtils.getNextDayByLen(startDate, Constant.WARNING_STAY_NOTIN_DAY_LEN)); ){
				result = doStayNotinByDate(everyDate, MapUtils.getString(map, "school_year"), MapUtils.getString(map, "term_code"));
				if(result.getIsTrue()==false){
					break;
				}
				everyDate = DateUtils.getNextDayByLen(everyDate, 1);
			}
			if(result.getIsTrue()==false){
				break;
			}
		}
		String info = jobName;
		info += "，结束执行。共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
		end(info);
		result.setMsg(info);
		DevUtils.p(info);
		return result;
	}
	private JobResultBean doStayNotinByDate(String date, String schoolYear, String termCode){
		//定义结果
		JobResultBean result = new JobResultBean();
		// 删除数据
		int count = baseDao.queryForInt("select count(0) from "+T_Stay_Notin+" where date_ = '"+date+"'");
		if(count > 0){
			baseDao.delete("Delete from "+T_Stay_Notin+" where date_ = '"+date+"'");
			info(date+":删除"+date+"数据: "+count+"条");
		}
		// 根据最长无记录天数判断 2016-10-04号时不在校学生：最后一次记录在2016-10-02之前
		String lessDate = DateUtils.getNextDayByLen(date, -(Constant.WARNING_STAY_NOTIN_DAY_LEN-1));
		String sql = "select sno stu_id, bjid class_id, '"+schoolYear+"' school_year, '"+termCode+"' term_code, '"+date+"' date_,"+
				"lasttime lasttime from "+T_Stay_Notin_Old+" where datetime = '"+date+"' and lasttime < '"+lessDate+"'";
		List<TStuWarningStayNotin> list = baseDao.queryForListBean(sql, TStuWarningStayNotin.class);
		if(!list.isEmpty())
			try {
				hibernateDao.saveAll(list);
				result.setIsTrue(true);
			} catch (SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				String info = "# #数据保存出错,"+e.getStackTrace();
				error(info);
				result.setIsTrue(false);
				result.setMsg(info);
				e.printStackTrace();
			}
		return result;
	}
	
	/**
	 * 晚勤晚归  晚上10:30以后回宿舍的学生为晚归
	 * @return JobResultBean
	 */
	@Transactional
	public JobResultBean doStayLate(){
		Long beginTime = System.currentTimeMillis();
		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		String jobName = "#晚勤晚归学生"+yesterDay+"#";
		begin(jobName);
		// 判断昨天是否需要初始化
		boolean isDo = false;
		String schoolYear = null, termCode = null;
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select t.* from t_school_start t order by t.school_year,t.term_code");
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date");
			if(DateUtils.compareEqual(yesterDay, startDate) && DateUtils.compareEqual(endDate, yesterDay)){
				isDo = true;
				schoolYear= MapUtils.getString(map, "school_year");
				termCode  = MapUtils.getString(map, "term_code");
				break;
			}
		}
		//定义结果
		JobResultBean result = new JobResultBean();
		String info = jobName;
		if(!isDo){
			info += "，不在上课时间";
		}else{
			result = doStayLateByDate(yesterDay, schoolYear, termCode);
		}
		info += "，结束执行。共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
		end(info);
		result.setMsg(info);
		DevUtils.p(info);
		return result;
	}
	/**
	 * 晚勤晚归历史
	 * @return JobResultBean
	 */
	@Transactional
	public JobResultBean doStayLateHistory(){
		Long beginTime = System.currentTimeMillis();
		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		String jobName = "#历史-晚勤晚归学生#";
		begin(jobName);

		//定义结果
		JobResultBean result = new JobResultBean();
		
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t order by t.school_year,t.term_code");
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date"),
				   everyDate = startDate;
			// (startDate <= everyDate <= endDate) && (everyDate < yesterDay) 历史日期判断
			for( ; (DateUtils.compareEqual(endDate, everyDate)&&DateUtils.compare(yesterDay, everyDate)); ){
				result = doStayLateByDate(everyDate, MapUtils.getString(map, "school_year"), MapUtils.getString(map, "term_code"));
				if(result.getIsTrue()==false){
					break;
				}
				everyDate = DateUtils.getNextDayByLen(everyDate, 1);
			}
			if(result.getIsTrue()==false){
				break;
			}
		}
		String info = jobName;
		info += "，结束执行。共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
		end(info);
		result.setMsg(info);
		DevUtils.p(info);
		return result;
	}
	private JobResultBean doStayLateByDate(String date, String schoolYear, String termCode){
		//定义结果
		JobResultBean result = new JobResultBean();
		// 删除数据
		int count = baseDao.queryForInt("select count(0) from "+T_Stay_Late+" where date_ = '"+date+"'");
		if(count > 0){
			baseDao.delete("Delete from "+T_Stay_Late+" where date_ = '"+date+"'");
			info(date+":删除"+date+"数据: "+count+"条");
		}
		String sql = "select sno stu_id, bjid class_id, '"+schoolYear+"' school_year, '"+termCode+"' term_code,"
				+ "'" +date+ "' date_, backtime backtime from "+T_Stay_Late_Old+" where datetime = '"+date+"'"
						+ " and backtime >= '"+Constant.WARNING_STAY_LATE_TIME+"'";
		List<TStuWarningStayLate> list = baseDao.queryForListBean(sql, TStuWarningStayLate.class);
		if(!list.isEmpty())
			try {
				hibernateDao.saveAll(list);
				result.setIsTrue(true);
			} catch (SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				String info = "# #数据保存出错,"+e.getStackTrace();
				error(info);
				result.setIsTrue(false);
				result.setMsg(info);
				e.printStackTrace();
			}
		
		return result;
	}
	
	/**
	 * 未住宿
	 * @return JobResultBean
	 */
	@Transactional
	public JobResultBean doNotStay(){
		Long beginTime = System.currentTimeMillis();
		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		String jobName = "#疑似未住宿学生"+yesterDay+"#";
		begin(jobName);
		// 判断昨天是否需要初始化
		boolean isDo = false;
		String schoolYear = null, termCode = null;
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select t.* from t_school_start t order by t.school_year,t.term_code");
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date");
			if(DateUtils.compareEqual(yesterDay, startDate) && DateUtils.compareEqual(endDate, yesterDay)){
				isDo = true;
				schoolYear= MapUtils.getString(map, "school_year");
				termCode  = MapUtils.getString(map, "term_code");
				break;
			}
		}
		//定义结果
		JobResultBean result = new JobResultBean();
		String info = jobName;
		if(!isDo){
			info += "，不在上课时间";
		}else{
			result = doNotStayByDate(yesterDay, schoolYear, termCode);
		}
		info += "，结束执行。共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
		end(info);
		result.setMsg(info);
		DevUtils.p(info);
		return result;
	}

	/**
	 * 未住宿历史
	 * @return JobResultBean
	 */
	@Transactional
	public JobResultBean doNotStayHistory(){
		Long beginTime = System.currentTimeMillis();
		String jobName = "#疑似未住宿学生#";
		begin(jobName);
		
		//定义结果
		JobResultBean result = new JobResultBean();
		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t order by t.school_year,t.term_code");
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date"),
				   everyDate = startDate;
			// (startDate <= everyDate <= endDate) && (everyDate < yesterDay) 历史日期判断
			for( ; (DateUtils.compareEqual(endDate, everyDate)&&DateUtils.compare(yesterDay, everyDate)); ){
				result = doNotStayByDate(everyDate, MapUtils.getString(map, "school_year"), MapUtils.getString(map, "term_code"));
				if(result.getIsTrue()==false){
					break;
				}
				everyDate = DateUtils.getNextDayByLen(everyDate, 1);
			}
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
	/**
	 * 疑似未住宿：【有一卡通消费数据没有门禁刷卡记录】以及【一卡通门禁都有记录但消费记录晚于刷卡记录】的学生
	 */
	private JobResultBean doNotStayByDate(String date, String schoolYear, String termCode){
		//定义结果
		JobResultBean result = new JobResultBean();
		// 删除数据
		int count = baseDao.queryForInt("select count(0) from "+T_Notstay+" where date_ = '"+date+"'");
		if(count > 0){
			baseDao.delete("Delete from "+T_Notstay+" where date_ = '"+date+"'");
			info(date+":删除"+date+"数据: "+count+"条");
		}
		String sql = "select pay.stu_id, stu.class_id, '" +date+ "' date_, '"+schoolYear+"' school_year, '"+termCode+"' term_code from"
				+ " (select card.people_id stu_id,max(pay.time_) time from t_card_pay pay, t_card card"
				+ " where pay.card_id=card.no_ and pay.time_ like '" +date+ "%' group by card.people_id) pay"
				+ " left join "
				+ " (select t.people_id stu_id,max(rke.time_) time from t_dorm_rke rke,T_DORM_PROOF t where rke.DORM_PROOF_ID=t.no_"
				+ " and rke.time_ like '" +date+ "%' group by t.people_id) dorm"
				+ " on pay.stu_id = dorm.stu_id"
				+ " left join t_stu stu on stu.no_=pay.stu_id"
				+ " where dorm.stu_id is null or (dorm.stu_id is not null and pay.time > dorm.time)";
		try {
			List<TStuWarningNotstay> list = baseDao.queryForListBean(sql, TStuWarningNotstay.class);
			if(!list.isEmpty()) hibernateDao.saveAll(list);
			result.setIsTrue(true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			String info = "# #数据保存出错,"+e.getStackTrace();
			error(info);
			result.setIsTrue(false);
			result.setMsg(info);
			e.printStackTrace();
		}
		return result;
	}
	
	private void begin(String info){
		log.warn("======== begin : "+info+" 初始化 ========");
	}
	private void info(String info){
		log.warn("======== info : "+info+" ========");
	}
	private void end(String info){
		log.warn("======== end : "+info+" ========");
	}
	private void error(String info){
		log.warn("======== error : "+info+" 停止执行========");
	}
	
	/**
	 * 疑似逃课：上课期间消费(餐厅、超市、澡堂、茶水房)、门禁出入
	 * @return String
	 */
	@Transactional
	public JobResultBean doSkipClasses(){
		Long beginTime = System.currentTimeMillis();
		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		String jobName = "#疑似逃课学生"+yesterDay+"#";
		begin(jobName);
		// 判断昨天是否需要初始化
		boolean isDo = false;
		String schoolYear = null, termCode = null;
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t order by t.school_year,t.term_code");
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date");
			if(DateUtils.compareEqual(yesterDay, startDate) && DateUtils.compareEqual(endDate, yesterDay)){
				isDo = true;
				schoolYear= MapUtils.getString(map, "school_year");
				termCode  = MapUtils.getString(map, "term_code");
				break;
			}
		}
		//定义结果
		JobResultBean result = new JobResultBean();
		String info = jobName;
		if(!isDo){
			info += "，课程已结束，不在上课时间";
		}else{
			result = doSkipClassesByDate(yesterDay, schoolYear, termCode);
		}

		/*String startDate = "2016-05-07", endDate = "2016-05-17", everyDate = startDate;
		for( ; DateUtils.compareEqual(endDate, everyDate); ){
			doSkipClassesByDate(everyDate, "2015-2016", "02");
			everyDate = DateUtils.getNextDayByLen(everyDate, 1);
		}*/
		
		info += "，结束执行。共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
		end(info);
		result.setMsg(info);
		DevUtils.p(info);
		return result;
	}

	/**
	 * 疑似逃课(历史数据，执行一次)：上课期间消费(餐厅、超市、澡堂、茶水房)、门禁出入
	 * @return String
	 */
	@Transactional
	public JobResultBean doSkipClassesHistory(){
		Long beginTime = System.currentTimeMillis();
		String jobName = "#历史-疑似逃课学生#";
		begin(jobName);
		//定义结果
		JobResultBean result = new JobResultBean();
		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t order by t.school_year,t.term_code");
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date"),
				   everyDate = startDate;
			// (startDate <= everyDate <= endDate) && (everyDate < yesterDay) 历史日期判断
			for( ; (DateUtils.compareEqual(endDate, everyDate)&&DateUtils.compare(yesterDay, everyDate)); ){
				result = doSkipClassesByDate(everyDate, MapUtils.getString(map, "school_year"), MapUtils.getString(map, "term_code"));
				if(result.getIsTrue()==false){
					break;
				}
				everyDate = DateUtils.getNextDayByLen(everyDate, 1);
			}
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
	/**
	 * 获取疑似逃课数据
	 * @param date 获取哪一天的结果
	 * @return JobResultBean
	 */
	public JobResultBean doSkipClassesByDate(String date, String schoolYear, String termCode) {
		//定义结果
		JobResultBean result = new JobResultBean();
		int count = baseDao.queryForInt("select count(0) from "+T_SkipClasses+" where date_ = '"+date+"'");
		if(count > 0){
			baseDao.delete("Delete from "+T_SkipClasses+" where date_ = '"+date+"'");
			info(date+":删除"+date+"数据: "+count+"条");
		}
		/**
		 * 昨天上课期间：消费(餐厅、超市、澡堂、茶水房)、门禁出入的数据（宿舍、图书馆）
		 * 按教学班处理（教学班上课时间段内，班级内学生有记录的）
		 * 1.昨天上课的教学班：课程安排id、上课时间段、对应学生
		 * 2.循环：时间段对应的学生中 有记录的学生
		 */
		String term_start_day = businessService.getSchoolStartDate(schoolYear, termCode); // 学期开始时间
		if(term_start_day == null){
			String info = schoolYear+"学年 "+termCode+"学期 没有设置开学时间（开课时间）";
			info(info);
			result.setIsTrue(false);
			result.setMsg(info);
			return result;
		}
		try {
			int weak    = DateUtils.getZcByDateFromBeginDate(DateUtils.string2Date(term_start_day), DateUtils.string2Date(date));
			int weakDay = DateUtils.getWeekNo(date);
			String kcSql = "select t.id, t.teachingclass_id, t.period_start, t.period_end, t.school_year, t.term_code"
					+ " from T_COURSE_ARRANGEMENT t where t.school_year='"+schoolYear+"' and t.term_code='"+termCode+"'"
					+ " and t.weeks like '%,"+weak+",%'"
					+ " and t.day_of_week = " +weakDay
					+ " order by t.teachingclass_id";
			List<Map<String, Object>> kcList = baseDao.queryListInLowerKey(kcSql, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR);
			/**
			 * 组装 教学班的数据（一条课程安排记录的上课时间段）（分教学班处理数据）
			 */
			// 不同节次上课时间
			Map<String, Period> periodSectionMap = getPeriodSectionMap(Integer.valueOf(schoolYear.substring(0, 4)), date);
			// kcList->kcMap; {classId : {id:[[10:00,10:50],[11:00,11:50]], id2:[]}, classId:{} }
			Map<String, Map<String, List<String[]>>> kcMap = new HashMap<>();
			Map<String, List<String[]>> periodSectionOneMap = null; // 教学班各上课安排、上课时间 { id:[[10:00,10:50],[11:00,11:50]], id2:[[],[]]
			List<String[]> periodSectionOneList = null; // 一个课程安排对应的时间段;eg:[[10:00,10:50],[11:00,11:50]]
			String teachingclassId = null, arrangementId = null;
			for(Map<String, Object> map : kcList){
				teachingclassId = MapUtils.getString(map, "teachingclass_id");
				if(teachingclassId==null || "".equals(teachingclassId)){
//					DevUtils.p("教学班"+teachingclassId);
					continue;
				}
				arrangementId   = MapUtils.getString(map, "id"); // 课程安排ID
				periodSectionOneList = getPeriodSection(periodSectionMap, MapUtils.getInteger(map, "period_start"), MapUtils.getInteger(map, "period_end"));
				if(periodSectionOneList==null || periodSectionOneList.isEmpty()) continue; // 无上课节次、无上课时间 直接去掉 20161013
				if(!kcMap.containsKey(teachingclassId)){
					periodSectionOneMap = new HashMap<>();
					kcMap.put(teachingclassId, periodSectionOneMap);
				}else{
					periodSectionOneMap = kcMap.get(teachingclassId);
				}
				periodSectionOneMap.put(arrangementId, periodSectionOneList);
			}
			List<TStuWarningSkipClasses> insetList = new ArrayList<>();
			String classStuSql = null, timeSql = null, stuNoSql = null, oneSql = null;
			List<String> timeSqlList = new ArrayList<>();
			for(Map.Entry<String, Map<String, List<String[]>>> entry : kcMap.entrySet()){
				/**
				 * 一个教学班内的学生 出现的异常记录
				 */
				teachingclassId = entry.getKey();
				classStuSql = "select stu_id from T_CLASS_TEACHING_STU where teach_class_id = '" +teachingclassId+ "'";
				// 一个教学班的多个课程安排时间
				periodSectionOneMap = entry.getValue();
				// 每个上课安排逃课的学生；分开查询的目的是，分别存储不同上课安排的逃课学生
				for(Map.Entry<String, List<String[]>> en : periodSectionOneMap.entrySet()){
					/**
					 * 一个教学班 每个课程安排（上课时间段） 出现的异常记录
					 * 消费(餐厅 210、超市 215、澡堂 211、茶水房 220、公交 223、 医疗 214)、门禁出入的数据
					 */
					arrangementId = en.getKey(); // 课程安排ID
					// 上课时间段sql，两节课，两组时间段
					periodSectionOneList = en.getValue();
					timeSqlList.clear();
					for(String[] timeAry : periodSectionOneList){
						timeSqlList.add("(t.time_ >= '"+timeAry[0]+"' and t.time_ < '"+timeAry[1]+"')");
					}
					if(!timeSqlList.isEmpty()){
						timeSql = " and (" +StringUtils.join(timeSqlList, " or ")+ ")";
						stuNoSql = "select distinct(stu.stu_id) stu_id from T_CARD_PAY t, t_card card, ("+classStuSql+") stu"
								+ " where t.card_id=card.no_ and card.people_id=stu.stu_id"
								+ " and t.card_deal_id in ("+Constant.WARNING_SKIP_CLASSES_CARD_DEAL_TYPE+")"
								+ timeSql
								+ " union select distinct(stu.stu_id) from T_DORM_RKE t, T_DORM_PROOF proof, ("+classStuSql+") stu"
								+ " where t.dorm_proof_id=proof.no_ and proof.people_id=stu.stu_id"
								+ timeSql
								+ " union select distinct(stu.stu_id) from T_BOOK_RKE t, T_BOOK_READER reader, ("+classStuSql+") stu"
								+ " where t.book_reader_id=reader.no_ and reader.people_id=stu.stu_id"
								+ " and reader.people_type_code in ("+Constant.WARNING_SKIP_CLASSES_READER_IDENTITY_STU+")"
								+ timeSql;
						oneSql = "select stu.stu_id, s.class_id, '"+date+"' as date_, '"+arrangementId+"' as course_arrangement_id,"
								+ " '"+schoolYear+"' school_year, '"+termCode+"' term_code from"
								+ " ("+stuNoSql+") stu, t_stu s where stu.stu_id=s.no_ ";
						insetList.addAll(baseDao.queryForListBean(oneSql, TStuWarningSkipClasses.class));
					}
				}
			}
			if(!insetList.isEmpty()){
				hibernateDao.saveAll(insetList);
			}
			result.setIsTrue(true);
		}catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			String info = "# #数据保存出错,"+e.getStackTrace();
			error(info);
			result.setIsTrue(false);
			result.setMsg(info);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取课程安排ID对应的时间段
	 * @param periodSectionMap 节次时间段
	 * @param period_start 开始节次
	 * @param period_end 结束节次
	 * @return List<String[]>
	 */
	private List<String[]> getPeriodSection(Map<String, Period> periodSectionMap, Integer period_start, Integer period_end){
		if(period_start==null || period_end==null){
			return null;
		}
		List<String[]> list = new ArrayList<>();
		Object o = null;
		for( ; period_start<=period_end; period_start++){
			o = MapUtils.getObject(periodSectionMap, period_start+"");
			if(o != null) list.add(((Period) o).getAry());
		}
		return list;
	}
	/**
	 * 获取节次对应时间段
	 * @param schoolYear 学年；eg：2014
	 * @param date 日期；eg：2015-05-29
	 * @return Map<String,Period>
	 */
	private Map<String, Period> getPeriodSectionMap(int schoolYear, String date){
		String sql = "select t.id, '"+date+" '||t.start_time as start_time, '"+date+" '||t.end_time as end_time from T_COURSE_PERIOD_TIME t";
		sql += " where ((case when t.start_date>t.end_date then '"+schoolYear+"' else '"+(schoolYear+1)+"' end)||'-'||t.start_date) <= '"+date+"'"
				+ " and ('"+(schoolYear+1)+"'||'-'||t.end_date) >= '"+date+"' order by start_time,id";
		List<Map<String, Object>> periodList = baseDao.queryListInLowerKey(sql);
		Map<String, Period> reMap = new HashMap<>();
		for(Map<String, Object> map : periodList){
			String id = MapUtils.getString(map, "id");
			reMap.put(id, new Period(id, MapUtils.getString(map, "start_time"), MapUtils.getString(map, "end_time")));
		} 
		return reMap;
	}
	/**
	 * 节次
	 */
	class Period {
		private String id; // 节次号
		private String[] ary = new String[2]; // 节次时间段 [10:00,10:50]
		Period(String id, String start_time, String end_time){
			this.setId(id);
			ary[0] = start_time;
			ary[1] = end_time;
		}
		public String[] getAry() { return ary; }
		public void setAry(String[] ary) { this.ary = ary; }
		public String getId() { return id; }
		public void setId(String id) { this.id = id; }
	}
	
	
	@SuppressWarnings({ "resource" })
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-mvc.xml");
		StuWarningJob sj = context.getBean(StuWarningJob.class);
		
//		BaseDao baseDao = context.getBean(BaseDao.class);
//		HibernateDao hibernateDao = context.getBean(HibernateDao.class);
//		baseDao.insert("insert into t_school_start(id,school_year,term_code,start_date,end_date) values(222,2222,3333,444,555)");
//		sj.setDao(baseDao, hibernateDao);
		
		String date = "2016-10-18", schoolYear = "2016-2017", termCode = "01";
		DevUtils.p("开始：获取疑似逃课数据（"+date+"）（"+schoolYear+","+termCode+"）......"+DateUtils.getNowDate2());
		long time1 = System.currentTimeMillis();
		sj.doSkipClassesByDate(date, schoolYear, termCode);
		long time2 = System.currentTimeMillis();
		DevUtils.p("time："+(time2-time1)/1000);
		DevUtils.p("结束：获取疑似逃课数据（"+date+"）（"+schoolYear+","+termCode+"）......"+DateUtils.getNowDate2());
	}
}
