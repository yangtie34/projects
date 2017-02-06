package cn.gilight.dmm.teaching.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.teaching.entity.TCourseArrangementPlan;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;

@Service("coursePlanJob")
public class CoursePlanJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService ;
	@Resource
	private HibernateDao hibernateDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Transactional
	public JobResultBean doNowPlan(){
		String jobName = " t_course_arrangement_plan当前学期开课计划表";
		String[] timeAry = businessDao.queryMaxSchoolYearTermCode("t_course_arrangement", "school_year", "term_code");
		String schoolYear = timeAry[0]+"-"+String.valueOf(Integer.parseInt(timeAry[0])+1);
 		List<TCourseArrangementPlan> list = getList(schoolYear, timeAry[1], true); 
		JobResultBean result = doResult(list,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doLsPlan(){
		String jobName = " t_course_arrangement_plan历史学期开课计划表";
		String[] timeAry = businessDao.queryMaxSchoolYearTermCode("t_course_arrangement", "school_year", "term_code");
		String schoolYear = timeAry[0]+"-"+String.valueOf(Integer.parseInt(timeAry[0])+1);
		List<TCourseArrangementPlan> list = getList(schoolYear, timeAry[1], false); 
		JobResultBean result = doResult(list,jobName);
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
	private JobResultBean doResult(List<TCourseArrangementPlan> list,String jobName){
		JobResultBean result = new JobResultBean();
		Long beginTime = System.currentTimeMillis();
		try{
 		if(!list.isEmpty()){
		hibernateDao.saveAll(list);
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
	private List<TCourseArrangementPlan> getList(String school_year,String term_code,Boolean isNow){
		school_year = isNow ? "  a.school_year ='"+school_year+"' and a.term_code = '"+term_code+"' ": " (a.school_year <> '"+school_year+"' or a.term_code <> '"+term_code+"') ";
		List<TCourseArrangementPlan> result = new ArrayList<TCourseArrangementPlan>();
		String timeSql = "select a.school_year,a.term_code,b.class_id,a.course_id as course_code,a.course_attr_code,a.course_category_code,a.course_nature_code from t_course_arrangement a,t_class_teaching_xzb b "
				+ " where a.teachingclass_id = b.teach_class_id and "+school_year + " and b.class_id is not null  group by a.school_year,a.term_code,b.class_id,a.course_id,a.course_attr_code,a.course_category_code,a.course_nature_code";
		String sql = "select time.*,course.theory_credit as credit from ("+timeSql+") time,t_course course where time.course_code = course.id ";
		result = baseDao.queryForListBean(sql,TCourseArrangementPlan.class);
	    if(!result.isEmpty()){
	    	String oldSql = " t_course_arrangement_plan a where "+school_year;
	    	int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
			if(oldCount > 0){
				baseDao.delete("delete from "+oldSql);
				info("数据已删除");
			}
	    }
	    return result;
	}
}
