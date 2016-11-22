package cn.gilight.personal.student.course.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.student.course.dao.CourseDao;

@Repository("courseDao")
public class CourseDaoImpl implements CourseDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getTodayCourse(String stu_id,String school_year,String term_code,int zc,int week) {
		String sql = "select tt.course_name,tt.classroom,tt.course_type,tt.tea_name,case when tt.time_ is null then tt.period else tt.time_ end time_ from"
				+ " (select t.id,cou.name_ course_name,cb.name_ classroom,co.name_ course_type,tea.name_ tea_name, t.period,"
				+ " case when instr(t.period,'-')>0 then cpt.start_time||'-'||cp.end_time end time_ from t_course_arrangement t  "
				+ " left join t_class_teaching_xzb xzb on xzb.teach_class_id = t.teachingclass_id left join t_course cou on cou.code_ = t.course_id "
				+ " left join t_tea tea on tea.tea_no = t.tea_id left join t_classroom_build cb on cb.code_ = t.classroom_id "
				+ " left join t_code co on co.code_type = 'COURSE_ATTR_CODE' and co.code_ = t.course_attr_code "
				+ " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cpt on cpt.id = t.period_start "
				+ " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cp on cp.id = t.period_end"
				+ " left join t_stu stu on stu.class_id = xzb.class_id where stu.no_ = '"+stu_id+"' and t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' and t.weeks like '%,"+zc+",%' and t.day_of_week = "+week+")"
				+ " tt  order by time_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getSchedule(String stu_id,String school_year,String term_code,int zc,int jc) {
		String sql = "select tt.course_name,tt.classroom,tt.day_of_week,case when tt.time_ is null then tt.period "
				+ " else tt.time_ end time_ ,tt.period_start from ("
				+ " select t.id,cou.name_ course_name,cb.name_ classroom ,t.day_of_week,t.period_start,"
				+ " t.period,case when instr(t.period,'-')>0 then cpt.start_time||'-'||cp.end_time end time_ from t_course_arrangement t "
				+ " left join t_class_teaching_xzb xzb on xzb.teach_class_id = t.teachingclass_id"
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join t_classroom_build cb on cb.code_ = t.classroom_id"
				+ " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cpt on cpt.id = t.period_start"
				+ " left join (select tp.* from (select t.*,case when t.start_date > t.end_date and (to_char(sysdate,'MM-dd') > t.start_date or to_char(sysdate,'MM-dd')< t.end_date) then 1"
				+ " when t.start_date < t.end_date and to_char(sysdate,'MM-dd') between t.start_date and t.end_date then 1 else 0 end flag"
				+ " from t_course_period_time t )tp where tp.flag = 1) cp on cp.id = t.period_end"
				+ " left join t_stu stu on stu.class_id = xzb.class_id where stu.no_ = '"+stu_id+"' and t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"'"
				+ " and t.weeks like '%,"+zc+",%' ) tt where tt.period_start = "+jc+"  order by tt.day_of_week,tt.time_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getEmptyClassroom(String school_year,
			String term_code, int zc, int week, int period_start, int period_end) {
		String sql = "select * from t_classroom_build t left join ("
				+ " select distinct ca.classroom_id from t_course_arrangement ca where ca.school_year = '"+school_year+"'"
				+ " and ca.term_code = '"+term_code+"' and ca.weeks like '%,"+zc+",%' and"
				+ " ca.day_of_week = "+week+" and ca.period_start = "+period_start+" and ca.period_end = "+period_end+")tt"
				+ " on t.code_ = tt.classroom_id where tt.classroom_id is  null and length(t.code_)>4 order by t.name_";
		return baseDao.queryListInLowerKey(sql);
	}

}
