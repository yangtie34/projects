package cn.gilight.personal.student.major.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.personal.student.major.dao.MyMajorDao;

@Repository("myMajorDao")
public class MyMajorDaoImpl implements MyMajorDao{

	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getMajor(String stu_id) {
		String sql = "select cdt.name_ major from t_stu t left join t_code_dept_teach cdt on cdt.id = t.major_id where t.no_ = '"+ stu_id +"'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getCourse(String stu_id,String school_year,String term_code) {
		String sql = "select cou.name_ course_name,co.name_ course_type,t.credit  from t_course_arrangement_plan t left join t_stu stu on stu.class_id = t.class_id "
				+ " inner join t_course cou on cou.code_ = t.course_code"
				+ " left join t_code co on co.code_type = 'COURSE_ATTR_CODE' and co.code_ = t.course_attr_code"
				+ " where stu.no_ = '"+ stu_id +"' and t.school_year = '"+ school_year +"' and t.term_code = '"+ term_code+"'";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getCourseScore(String stu_id,String school_year, String term_code) {
		String sql = "select tt.course_name,tt.course_type,tt.credit,round(count(case when tt.hierarchical_score_code is null and tt.centesimal_score >= 60 then 1 "
				+ " when tt.hierarchical_score_code is not null and tt.hierarchical_score_code != '05' then 1 else null end)/count(*),2)*100||'%' pass from ("
				+ " select cou.name_ course_name,co.name_ course_type,t.credit ,s.* from t_stu_score s"
				+ " left join t_course_arrangement_plan t on t.course_code = s.coure_code"
				+ " left join t_stu stu on stu.class_id = t.class_id "
				+ " left join t_course cou on cou.code_ = t.course_code"
				+ " left join t_code co on co.code_type = 'COURSE_ATTR_CODE' and co.code_ = t.course_attr_code"
				+ " where stu.no_ = '"+ stu_id +"' and t.school_year = '"+ school_year +"' and t.term_code = '"+ term_code +"') tt group by tt.course_name,tt.course_type,tt.credit";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Page getChooseCourse(Page page) {
		String sql = "select cou.name_ course_name,tt.course_attr,cou.theory_credit credit,tt.choose_count,round(count("
				+ " case when s.hierarchical_score_code is null and s.centesimal_score >= 60 then 1 "
				+ " when s.hierarchical_score_code is not null and s.hierarchical_score_code != '05' then 1 else null end)/count(*),2)*100||'%' pass from ("
				+ " select t.scoure_code ,co.name_ course_attr, count(*) choose_count from t_stu_course_choose t"
				+ " left join t_code co on co.code_type = 'COURSE_ATTR_CODE' and co.code_ = t.course_attr_code group by "
				+ " t.scoure_code,co.name_ order by choose_count desc) tt"
				+ " inner join t_course cou on cou.code_ = tt.scoure_code"
				+ " inner join t_stu_score s on s.coure_code = tt.scoure_code group by cou.name_,tt.course_attr,"
				+ " cou.theory_credit,tt.choose_count order by tt.choose_count desc";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Page getPostgraduate(Page page,int year) {
		String sql = "select t.major,count(*) counts from t_stu_pg_exam t where t.exam_year > = '"+year+"' group by t.major order by counts desc";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
}
