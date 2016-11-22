package cn.gilight.personal.student.four.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.personal.student.four.service.FourScoreService;

@Service("fourScoreService")
public class FourScoreServiceImpl implements FourScoreService{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getScoreMap(String username) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String sql = "select ts.total,td.notpass from (select nvl(count(distinct t.coure_code),0) total from t_stu_score t"
				+ " where t.stu_id = '"+username+"')ts left join(select nvl(count(distinct tt.coure_code),0) notpass from ("
				+ " select t.stu_id,t.coure_code,case when t.hierarchical_score_code is null and t.centesimal_score < 60 then 0 "
				+ " when t.hierarchical_score_code is not null and co.name_ = '不及格' then 0 else 1 end ispass  from t_stu_score t"
				+ " left join t_code co on co.code_type = 'HIERARCHICAL_SCORE_CODE' and co.code_ = t.hierarchical_score_code"
				+ " where t.stu_id = '"+username+"') tt where tt.ispass = 0 ) td on 1=1";
		List<Map<String,Object>> list1 =  baseDao.queryListInLowerKey(sql);
		int total = 0;
		int notpass = 0;
		if(list1 != null && list1.size()>0){
			total = MapUtils.getIntValue(list1.get(0), "total");
			notpass = MapUtils.getIntValue(list1.get(0), "notpass");
		}
		
		String total_sql = "select t.zcj total_score,t.gpa from t_stu_score_total t where t.stu_id = '"+username+"'";
		List<Map<String,Object>> listTotal = baseDao.queryListInLowerKey(total_sql);
		int total_score = 0;
		double gpa = 0;
		if(listTotal != null && listTotal.size()>0){
			total_score = MapUtils.getIntValue(listTotal.get(0), "total_score");
			gpa = MapUtils.getDoubleValue(listTotal.get(0), "gpa");
		}
		String passTotal = "select count(t.stu_id) passtotal from t_stu_score_total t left join t_stu stu on stu.no_ = t.stu_id"
				+ " where t.major_id = (select s.major_id from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') and t.zcj < "+total_score;
		List<Map<String,Object>> passTotalList = baseDao.queryListInLowerKey(passTotal);
		int passtotal = 0;
		if(passTotalList != null && passTotalList.size()>0){
			passtotal = MapUtils.getIntValue(passTotalList.get(0), "passtotal");
		}
		
		String passGpa = "select count(t.stu_id) passgpa from t_stu_score_total t left join t_stu stu on stu.no_ = t.stu_id "
				+ " where  t.major_id = (select s.major_id from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') and t.gpa <"+gpa;
		List<Map<String,Object>> passGpaList = baseDao.queryListInLowerKey(passGpa);
		int passgpa = 0;
		if(passGpaList != null && passGpaList.size()>0){
			passgpa = MapUtils.getIntValue(passGpaList.get(0), "passgpa");
		}
		
		String avg = "select ts.my_avg,td.major_avg from (select t.pjcj my_avg from t_stu_score_total t where t.stu_id = '"+username+"') ts left join ("
				+ " select nvl(round(sum(t.pjcj)/count(t.stu_id),2),0) major_avg from t_stu_score_total t left join t_stu stu on stu.no_ = t.stu_id"
				+ " where t.major_id = (select s.major_id from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"')) td on 1=1";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(avg);
		double myAvg = 0;
		double majorAvg = 0;
		if(list2 != null && list2.size() >0 ){
			myAvg = MapUtils.getDoubleValue(list2.get(0), "my_avg");
			majorAvg = MapUtils.getDoubleValue(list2.get(0), "major_avg");
		}
		
		String passNums = "select count(t.stu_id) passnums from t_stu_score_total t left join t_stu stu on stu.no_ = t.stu_id "
				+ " where t.major_id = (select s.major_id from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') and t.pjcj <"+myAvg;
		List<Map<String,Object>> list3 = baseDao.queryListInLowerKey(passNums);
		int passnums = 0;
		if(list3!= null && list3.size()>0){
			passnums = MapUtils.getIntValue(list3.get(0), "passnums");
		}
		
		
		
		String totalNums = "select count(*) total from t_stu stu where stu.major_id = (select s.major_id from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') ";
		List<Map<String,Object>> list4 = baseDao.queryListInLowerKey(totalNums);
		int totalnums = 0;
		if(list4!= null && list4.size()>0){
			totalnums = MapUtils.getIntValue(list4.get(0), "total");
		}
		
		String passtotalpro = "0%";
		String passpro = "0%";
		String passgpapro = "0%";
		if(totalnums != 0){
			passpro = MathUtils.getPercent(passnums, totalnums);
			passtotalpro = MathUtils.getPercent(passtotal, totalnums);
			passgpapro = MathUtils.getPercent(passgpa, totalnums);
		}
		
		
		
		resultMap.put("total", total);
		resultMap.put("notpass", notpass);
		resultMap.put("myAvg", myAvg);
		resultMap.put("majorAvg", majorAvg);
		resultMap.put("passpro", passpro);
		resultMap.put("passtotalpro", passtotalpro);
		resultMap.put("passgpapro", passgpapro);
		double pp = Double.parseDouble(passpro.substring(0, passpro.length()-1));
		double ptp = Double.parseDouble(passtotalpro.substring(0, passtotalpro.length()-1));
		double pgp = Double.parseDouble(passgpapro.substring(0, passgpapro.length()-1));
		List<Double> ld = new ArrayList<Double>();
		ld.add(pp);
		ld.add(ptp);
		ld.add(pgp);
		double pj = MathUtils.getAvgvalue(ld);
		String pjs =pj+"%";
		resultMap.put("pjs", pjs);
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getScoreChart(String username) {
		String sql = "select td.school_year,td.term_code,td.my_avg,ts.major_avg from ("
				+ " select tt.school_year,tt.term_code ,nvl(round(sum(tt.score)/count(tt.coure_code),2),0) my_avg from ("
				+ " select t.school_year,t.term_code,t.coure_code,case when t.hierarchical_score_code is null then t.centesimal_score "
				+ " when t.hierarchical_score_code is not null then csh.centesimal_score end score from t_stu_score t"
				+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code where t.stu_id = '"+username+"') tt "
				+ " where tt.score is not null group by tt.school_year,tt.term_code) td"
				+ " left join (select tt.school_year,tt.term_code,nvl(round(sum(tt.score)/count(tt.coure_code),2),0) major_avg from ("
				+ " select t.school_year,t.term_code,t.stu_id,t.coure_code,case when t.hierarchical_score_code is null then t.centesimal_score "
				+ " when t.hierarchical_score_code is not null then csh.centesimal_score end score from t_stu_score t left join t_stu stu on stu.no_ = t.stu_id "
				+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code"
				+ " where stu.major_id = (select s.major_id from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"')) tt where tt.score is not null group by tt.school_year ,tt.term_code) ts"
				+ " on td.school_year = ts.school_year and td.term_code = ts.term_code order by td.school_year,td.term_code";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> getGoodScore(String username) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		String sql  = "select school_year ,term_code,total_score,rn from ("
				+ " select td.school_year,td.term_code,td.total_score,td.rn,rownum r from ("
				+ " select ts.school_year,ts.term_code,ts.total_score,ts.rn from ("
				+ " select tt.school_year,tt.term_code,tt.stu_id ,sum(tt.score) total_score, "
				+ " row_number()over(partition by tt.school_year,tt.term_code order by sum(tt.score) desc) rn from ("
				+ " select t.school_year,t.term_code,t.stu_id,case when t.hierarchical_score_code is null then t.centesimal_score "
				+ " else csh.centesimal_score end score from t_stu_score t left join t_stu stu on stu.no_ = t.stu_id "
				+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code"
				+ " where stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.major_id = (select s.major_id from t_stu s where s.no_ = '"+username+"')) tt where tt.score is not null"
				+ " group by tt.school_year,tt.term_code,tt.stu_id ) ts where ts.stu_id = '"+username+"' order by rn) td) where r = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		String school_year = "";
		String term_code = "";
		int total_score = 0;
		int rn = 0;
		if(list != null && list.size()>0){
			school_year = MapUtils.getString(list.get(0), "school_year");
			term_code = MapUtils.getString(list.get(0), "term_code");
			total_score = MapUtils.getIntValue(list.get(0), "total_score");
			rn = MapUtils.getIntValue(list.get(0), "rn");
		}
		
		String sqlList = "select t.school_year,t.term_code,cou.name_ course_name,case when t.hierarchical_score_code is null then t.centesimal_score "
				+ " else csh.centesimal_score end score from t_stu_score t"
				+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code "
				+ " left join t_course cou on cou.code_ = t.coure_code"
				+ " where t.stu_id = '"+username+"' and t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"'";
		List<Map<String,Object>> scoreList = baseDao.queryListInLowerKey(sqlList);
		
		resultMap.put("school_year", school_year);
		resultMap.put("term_code", term_code);
		resultMap.put("total_score", total_score);
		resultMap.put("rn", rn);
		resultMap.put("scoreList", scoreList);
		
		return resultMap;
	}

	@Override
	public Map<String, Object> scoreCourseMap(String username) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String sql = "select ts.course_name,ts.score from (select tt.course_name,tt.score ,rownum rn from ("
				+ " select t.stu_id,t.school_year,t.term_code,cou.name_ course_name,case when t.hierarchical_score_code is not null then csh.centesimal_score"
				+ " else t.centesimal_score end score from t_stu_score t"
				+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code"
				+ " left join t_course cou on cou.code_ = t.coure_code where t.stu_id = '"+username+"' order by score desc) tt) ts where ts.rn <= 3";
		List<Map<String,Object>> myList = baseDao.queryListInLowerKey(sql);
		
		String majorSql = "select td.course_name,td.avg_score from (select ts.course_name ,ts.avg_score,rownum rn from ("
				+ " select tt.coure_code ,tt.course_name,nvl(round(sum(tt.score)/count(tt.stu_id),0),0) avg_score from ("
				+ " select t.stu_id,t.school_year,t.term_code,t.coure_code,cou.name_ course_name,"
				+ " case when t.hierarchical_score_code is not null then csh.centesimal_score"
				+ " else t.centesimal_score end score from t_stu_score t"
				+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code"
				+ " left join t_course cou on cou.code_ = t.coure_code  left join t_stu stu on stu.no_ = t.stu_id"
				+ " where stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.major_id = (select s.major_id from t_stu s where s.no_ = '"+username+"')) tt "
				+ " group by tt.coure_code ,tt.course_name order by avg_score desc)ts)td where td.rn < 3";
		List<Map<String,Object>> majorList = baseDao.queryListInLowerKey(majorSql);
		
		resultMap.put("myList", myList);
		resultMap.put("majorList", majorList);
		return resultMap;
	}

}
