package cn.gilight.personal.teacher.teaching.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.teacher.teaching.dao.TeacherTeachingDao;

@Repository("teacherTeachingDao")
public class TeacherTeachingDaoImpl implements TeacherTeachingDao {
	@Autowired
	private BaseDao baseDao;
	@Override
	public List<Map<String, Object>> getTodayClass(String school_year,
			String term_code, String tea_id, int zc, int weak) {
		
		String sql  ="select  t.id,t.course_id,cou.name_ course_name ,cpt.start_time ||'-'|| cp.end_time time_ ,cb.name_ classroom_name from t_course_arrangement t "
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cpt on cpt.id = t.period_start"
		        + " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cp on cp.id = t.period_end"
				+ " left join t_classroom_build cb on cb.code_ = t.classroom_id"
				+ " where t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' and  t.tea_id = '"+tea_id+"' and t.day_of_week = "+weak+" and t.weeks like '%,"+zc+",%'";
		return baseDao.queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getClassSchedule(String tea_id,String school_year,String term_code, int zc,int jc) {
		String sql = "select tt.* from("
				+ " select distinct cou.name_ course_name,cb.name_ classroom, t.day_of_week, case when instr(t.period,'-')>0 then cpt.start_time||'-'||cp.end_time end time_,t.period_start  "
				+ " from t_course_arrangement t"
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cpt on cpt.id = t.period_start"
				+ " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cp on cp.id = t.period_end"
				+ " left join t_classroom_build cb on cb.code_ = t.classroom_id where t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' "
				+ " and t.tea_id = '"+tea_id+"' and t.weeks like '%,"+zc+",%' and t.period_start = "+jc+" ) tt order by day_of_week ,period_start";
		return baseDao.queryListInLowerKey(sql);
	}
	
	public List<Map<String,Object>> getTodayCourse(String courseArrangementId,String courseId){
		String sql = "select aa.* from ("
				+ " select s.course_name ,s.time_,s.classroom,s.class_name,s.school_year,s.term_code,s.no_,count(*) stus from t_stu stu inner join ("
				+ " select cou.name_ course_name,cpt.start_time ||'-'|| cp.end_time time_ ,cb.name_ classroom ,cl.name_ class_name,"
				+ " cl.no_,t.school_year,t.term_code from t_course_arrangement t"
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cpt on cpt.id = t.period_start"
		        + " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cp on cp.id = t.period_end"
				+ " left join t_classroom_build cb on cb.code_ = t.classroom_id"
				+ " right join t_class_teaching_xzb xzb on t.teachingclass_id = xzb.teach_class_id"
				+ " left join t_classes cl on cl.no_ = xzb.class_id where t.id = '"+ courseArrangementId +"' and t.course_id = '"+courseId+"') s on stu.class_id = s.no_ "       
				+ " group by s.course_name ,s.time_,s.classroom,s.class_name,s.school_year,s.term_code,s.no_ order by class_name) aa"
				+ " left join t_classes_instructor ci on ci.class_id = aa.no_ and aa.school_year = ci.school_year and aa.term_code = ci.term_code";
		return baseDao.queryForList(sql);
		
	}
	
	public List<Map<String,Object>> getTermClass(String school_year,String term_code,String tea_id){
		String sql = "select s.teachingclass_id,s.course_name,s.course_type,s.weeks,s.day_of_week,wmsys.wm_concat(class_name) class_names from ("
				+ " select t.teachingclass_id, cou.name_ course_name,co.name_ course_type,t.day_of_week,t.weeks ,cl.name_ class_name"
				+ " from t_course_arrangement t "
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join t_class_teaching_xzb xzb on xzb.teach_class_id = t.teachingclass_id"
				+ " left join t_classes cl on cl.no_ = xzb.class_id"
				+ " left join t_code co on co.code_type = 'COURSE_ATTR_CODE' and co.code_ = t.course_attr_code"
				+ " where t.school_year = '"+ school_year +"' and t.term_code = '"+ term_code +"' and t.tea_id = '"+ tea_id +"'"
				+ " group by t.teachingclass_id, cou.name_,co.name_,cl.name_,t.weeks,t.day_of_week order by t.teachingclass_id,t.weeks,t.day_of_week) s "
				+ " group by s.teachingclass_id,s.course_name,s.course_type,s.weeks,s.day_of_week order by s.teachingclass_id,s.course_name,s.course_type,s.weeks,s.day_of_week";
		return baseDao.queryForList(sql);
		
	}

	

}
