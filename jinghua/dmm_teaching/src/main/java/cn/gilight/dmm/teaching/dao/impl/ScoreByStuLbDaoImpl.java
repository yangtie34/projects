package cn.gilight.dmm.teaching.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.teaching.dao.ScoreByStuLbDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MathUtils;

@Repository("scoreByStuLbDao")
public class ScoreByStuLbDaoImpl implements ScoreByStuLbDao {
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	
	@Override
	public List<Map<String, Object>> getCourseTypeList(String schoolYear,String termCode,List<String> deptList,List<AdvancedParam> advancedList){
		int year = Integer.parseInt(schoolYear.substring(0,4));
		String codeSql= "select * from t_code a where a.code_type = 'COURSE_TYPE_CODE'";
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String scoreSql = "select * from "+Constant.TABLE_T_STU_SCORE+" score where "
				+ " score.school_year = '"+schoolYear+"' and score.term_code = '"+termCode+"'";
		String sql = "select b.code_ as id, b.name_ as mc, b.order_ from t_course t,"
				+ " ("+codeSql+") b,("+stuSql+") stu,("+scoreSql+") cj where "
				+ " t.course_type_code = b.code_ and cj.coure_code = t.id and stu.no_ = cj.stu_id "
				+ " group by b.code_, b.name_,b.order_ order by b.order_,b.code_" ;
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> getCourseCategoryList(String schoolYear,String termCode,String codeType,List<String> deptList,List<AdvancedParam> advancedList){
		int year = Integer.parseInt(schoolYear.substring(0,4));
		String codeSql= "select * from t_code a where a.code_type = '"+codeType+"'";
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String scoreSql = "select * from "+Constant.TABLE_T_STU_SCORE+" score where "
				+ " score.school_year = '"+schoolYear+"' and score.term_code = '"+termCode+"'";
		String sql = "select id,mc,order_ from (select b.code_ as id, b.name_ as mc, b.order_ from t_course_arrangement_plan plan,"
				+ " ("+codeSql+") b,("+stuSql+") stu,("+scoreSql+") cj,t_course kc where "
				+ " plan."+codeType+" = b.code_ and cj.coure_code = plan.course_code and cj.school_year = plan.school_year"
				+ " and cj.term_code = plan.term_code and stu.no_ = cj.stu_id "
				+ " and plan.course_code = kc.id  and stu.class_id = plan.class_id "
				+ " group by b.code_, b.name_,b.order_ "
				+ " union all "
				+ " select b.code_ as id, b.name_ as mc, b.order_ from t_stu_course_choose che,"
				+ " ("+codeSql+") b,("+stuSql+") stu,("+scoreSql+") cj,t_course kc where "
				+ " che."+codeType+" = b.code_ and cj.coure_code = che.scoure_code and cj.school_year = che.school_year"
				+ " and cj.term_code = che.term_code and stu.no_ = cj.stu_id "
				+ " and che.scoure_code = kc.id  and stu.no_ = che.stu_id "
				+ " group by b.code_ ,b.name_,b.order_ ) order by order_,id";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String, Object>> getCourseList(String schoolYear,String termCode,String type,String category,String attr,
			String nature,List<String> deptList,List<AdvancedParam> advancedList){
		int year = Integer.parseInt(schoolYear.substring(0,4));
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String scoreSql = "select * from "+Constant.TABLE_T_STU_SCORE+" score where "
				+ " score.school_year = '"+schoolYear+"' and score.term_code = '"+termCode+"'";
		String sql = "select id,mc from (select kc.id, kc.name_ as mc from t_course_arrangement_plan plan,"
				+ " ("+stuSql+") stu,("+scoreSql+") cj,t_course kc where "
				+ " cj.coure_code = plan.course_code and cj.school_year = plan.school_year"
				+ " and cj.term_code = plan.term_code and stu.no_ = cj.stu_id "
				+ " and plan.course_code = kc.id  and stu.class_id = plan.class_id "
				+ ((type == null||type.equals("all")) ? "": "and kc.course_type_code = '"+type+"'")
				+ ((category == null||category.equals("all")) ? "": "and plan.course_category_code = '"+category+"'")
				+ ((attr==null||attr.equals("all")) ? "": "and plan.course_attr_code = '"+attr+"'")
				+ ((nature==null||nature.equals("all")) ? "": "and plan.course_nature_code = '"+nature+"'")
				+ " group by  kc.id, kc.name_ "
				+ " union all "
				+ " select kc.id, kc.name_ as mc from t_stu_course_choose che,"
				+ " ("+stuSql+") stu,("+scoreSql+") cj,t_course kc where "
				+ " cj.coure_code = che.scoure_code and cj.school_year = che.school_year"
				+ " and cj.term_code = che.term_code and stu.no_ = cj.stu_id "
				+ " and che.scoure_code = kc.id  and stu.no_ =che.stu_id "
				+ ((type == null||type.equals("all")) ? "": "and kc.course_type_code = '"+type+"'")
				+ ((category == null||category.equals("all")) ? "": "and che.course_category_code = '"+category+"'")
				+ ((attr==null||attr.equals("all")) ? "": "and che.course_attr_code = '"+attr+"'")
				+ ((nature==null||nature.equals("all")) ? "": "and che.course_nature_code = '"+nature+"'")
				+ " group by  kc.id, kc.name_ ) order by id";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String, Object>> getNextById(String id){
		String sql = "select t.id,t.name_ as mc from t_code_admini_div t where";
		if (id == null){
			sql = sql+" t.level_ = 1";
		}else{
			sql = sql+" t.pid = '"+id+"'";
		}
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public Double getValue(String schoolYear,String termCode,String type,String category,String attr,
			String nature,String course,String tag,List<String> deptList,List<AdvancedParam> advancedList){
		String valueSql = getValueSql(schoolYear, termCode, type, category, attr, nature, course, deptList, advancedList);
		String sql = "";Double value = 0d;
		tag = tag == null ? Constant.SCORE_AVG:tag;
		if (!Constant.SCORE_MODE.equals(tag)){
			switch(tag){
			    case Constant.SCORE_AVG:
			    	  sql = "select round(avg(score),1) as value from ("+valueSql+")";
			    	  break;
			    case Constant.SCORE_MIDDLE:
			    	  sql = "select round(median(score),1) as value from ("+valueSql+")";
			    	  break;
			    case Constant.SCORE_FC:
			    	  sql = "select round(VARIANCE(score),1) as value from ("+valueSql+")";
			    	  break;
			    case Constant.SCORE_BZC:
			    	  sql = "select round(STDDEV(score),1) as value from ("+valueSql+")";
			    	  break;
			    default:
			    	break; 
			}
			String xx = baseDao.queryForString(sql);
			value = (xx == null || xx.equals(""))?0d:Double.valueOf(xx);
		}else{
			List<Double> list = baseDao.queryForListDouble(valueSql);
			value = MathUtils.getPoint(MathUtils.getModeValue(list), 1);
		}
		return value == null ? 0d:value; 
	}
	@Override
	public List<Double> getScoreList(String schoolYear,String termCode,String type,String category,String attr,
			String nature,String course,List<String> deptList,List<AdvancedParam> advancedList){
		 String sql = "select score from ("+getValueSql(schoolYear, termCode, type, category, attr, nature, course, deptList, advancedList)+")";
		 return baseDao.queryForListDouble(sql);
	}
	private String getValueSql(String schoolYear,String termCode,String type,String category,String attr,
			String nature,String course,List<String> deptList,List<AdvancedParam> advancedList){
		int year = Integer.parseInt(schoolYear.substring(0,4));
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String scoreSql = "select  case when score.hierarchical_score_code is not null "
				+ " then hier.centesimal_score else score.centesimal_score end score,score.stu_id,"
				+ " score.school_year,score.term_code,score.coure_code "
				+ " from "+Constant.TABLE_T_STU_SCORE+" score left join "
				+ " t_code_score_hierarchy hier on score.hierarchical_score_code = hier.code_ where "
				+ " score.school_year = '"+schoolYear+"' and score.term_code = '"+termCode+"'";
		String xfSql = "select cj.*,plan.credit from t_course_arrangement_plan plan,"
				+ " ("+stuSql+") stu,("+scoreSql+") cj,t_course kc where "
				+ " cj.coure_code = plan.course_code and cj.school_year = plan.school_year"
				+ " and cj.term_code = plan.term_code and stu.no_ = cj.stu_id "
				+ " and plan.course_code = kc.id  and stu.class_id = plan.class_id "
				+ ((type == null||type.equals("all")) ? "": "and kc.course_type_code = '"+type+"'")
				+ ((category == null||category.equals("all")) ? "": "and plan.course_category_code = '"+category+"'")
				+ ((attr==null||attr.equals("all")) ? "": "and plan.course_attr_code = '"+attr+"'")
				+ ((nature==null||nature.equals("all")) ? "": "and plan.course_nature_code = '"+nature+"'")
				+ ((course == null||course.equals("all")) ? "": "and plan.course_code = '"+course+"'")
				+ " union all "
				+" select cj.*,che.credit from t_stu_course_choose che,"
				+ " ("+stuSql+") stu,("+scoreSql+") cj,t_course kc where "
				+ " cj.coure_code = che.scoure_code and cj.school_year = che.school_year"
				+ " and cj.term_code = che.term_code and stu.no_ = cj.stu_id "
				+ " and che.scoure_code = kc.id  and stu.no_ = che.stu_id "
				+ ((type == null||type.equals("all")) ? "": "and kc.course_type_code = '"+type+"'")
				+ ((category == null||category.equals("all"))? "": "and che.course_category_code = '"+category+"'")
				+ ((attr==null||attr.equals("all")) ? "": "and che.course_attr_code = '"+attr+"'")
				+ ((nature==null||nature.equals("all")) ? "": "and che.course_nature_code = '"+nature+"'")
				+ ((course == null||course.equals("all"))? "": "and che.scoure_code = '"+course+"'");
		String sql = "select x.stu_id,case when sum(x.credit) =0 then 0 else round(sum(x.score * x.credit)/sum(x.credit),1) end as score "
				+ " from ("+xfSql+") x where x.score is not null group by x.stu_id";
		return sql;
	}
}
