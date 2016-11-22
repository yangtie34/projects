package cn.gilight.personal.student.score.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.student.score.dao.StuScoreDao;

@Repository("stuScoreDao")
public class StuScoreDaoImpl implements StuScoreDao{

	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getScore(String stu_id, String school_year,String term_code) {
		String sql2 ="select tt.stu_id,tt.total_score,rownum rn from ("
				+ " select t.stu_id,sum(t.centesimal_score) total_score from t_stu_score t"
				+ " left join t_stu stu on stu.no_ = t.stu_id"
				+ " where t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' and stu.major_id = "
				+ " (select s.major_id from t_stu s where s.no_='"+stu_id+"') and stu.enroll_grade = "
				+ " (select st.enroll_grade from t_stu st where st.no_ = '"+stu_id+"') "
				+ " group by t.stu_id order by total_score desc) tt";
		
		String sql = "select yy.stu_id,yy.total_score ,yy.rank_,xx.total_score high_score from ("
				+ " select dd.stu_id,dd.total_score,dd.rn rank_ from ("+ sql2 +") dd where dd.stu_id = '"+stu_id+"' ) yy"
				+ " left join (select dd.stu_id,dd.total_score,dd.rn rank_ from ("+ sql2 +") dd where dd.rn = 1) xx on 1=1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int getMajorCounts(String stu_id) {
		String sql = "select * from t_stu stu "
				+ " where stu.major_id = (select s.major_id from t_stu s where s.no_='"+stu_id +"') "
				+ " and stu.enroll_grade = (select st.enroll_grade from t_stu st where st.no_ = '"+stu_id +"')";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> getScoreTerm(String stu_id) {
		String sql = "select t.school_year,t.term_code from t_stu_score t where t.stu_id = '"+stu_id+"' group by t.school_year,"
				+ " t.term_code order by t.school_year desc,t.term_code desc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getScoreBySchoolTerm(String stu_id,String school_year, String term_code) {
		String sql = "select cou.name_ course_name,cou.theory_credit credit,case when t.hierarchical_score_code is not null then co.name_ else "
				+ " to_char(t.centesimal_score) end score from t_stu_score t "
				+ " left join t_code co on co.code_type = 'HIERARCHICAL_SCORE_CODE' and co.code_ = t.hierarchical_score_code"
				+ " left join t_course cou on cou.code_ = t.coure_code "
				+ " where t.stu_id = '"+stu_id+"' and t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"'";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> getTotalCredit(String stu_id) {
		String sql = "select nvl(sum(t.credit),0) total_credit  from t_course_arrangement_plan t "
				+ " left join t_stu stu on stu.class_id = t.class_id where stu.no_ = '"+stu_id+"'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Map<String, Object> getTotalCreditCourseAttr(String stu_id) {
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select co.name_ course_attr, nvl(sum(t.credit),0) total_credit  from t_course_arrangement_plan t "
				+ " left join t_code co on co.code_type = 'COURSE_ATTR_CODE' and co.code_ = t.course_attr_code"
				+ " left join t_stu stu on stu.class_id = t.class_id where stu.no_ = '"+stu_id+"' group by  co.name_";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		double bx = 0d;
		double xx = 0d;
		for(Map<String,Object> map : list){
			if(MapUtils.getString(map, "course_attr").contains("必修")){
				bx = bx + MapUtils.getDoubleValue(map, "total_credit");
			}
			if(MapUtils.getString(map, "course_attr").contains("选")){
				xx = xx + MapUtils.getDoubleValue(map, "total_credit");
			}
		}
		result.put("bx", bx);
		result.put("xx", xx);
		return result;
	}

	@Override
	public Map<String, Object> getMyCredit(String stu_id) {
		String sql = "select nvl(sum(cap.credit),0) my_credit from t_stu_score t"
				+ " left join t_stu stu on stu.no_ = t.stu_id"
				+ " left join t_course_arrangement_plan cap on cap.class_id = stu.class_id and cap.school_year = t.school_year"
				+ " and cap.term_code = t.term_code and t.coure_code = cap.course_code"
				+ " where t.stu_id = '"+stu_id+"' and (t.centesimal_score >= 60 or t.hierarchical_score_code != '05')";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Map<String, Object> getMyCreditCourseAttr(String stu_id) {
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select co.name_ course_attr, nvl(sum(cap.credit),0) my_credit from t_stu_score t"
				+ " left join t_stu stu on stu.no_ = t.stu_id"
				+ " left join t_course_arrangement_plan cap on cap.class_id = stu.class_id and cap.school_year = t.school_year"
				+ " left join t_code co on co.code_type = 'COURSE_ATTR_CODE' and co.code_ = cap.course_attr_code"
				+ " and cap.term_code = t.term_code and t.coure_code = cap.course_code"
				+ " where t.stu_id = '"+stu_id+"' and (t.centesimal_score >= 60 or t.hierarchical_score_code != '05') group by co.name_";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			double mybx = 0d;
			double myxx = 0d;
			for(Map<String,Object> map : list){
				if(MapUtils.getString(map, "course_attr").contains("必修")){
					mybx = mybx + MapUtils.getDoubleValue(map, "my_credit");
				}
				if(MapUtils.getString(map, "course_attr").contains("选")){
					myxx = myxx + MapUtils.getDoubleValue(map, "my_credit");
				}
			}
			result.put("mybx", mybx);
			result.put("myxx", myxx);
		}
		return result;
	}
}
