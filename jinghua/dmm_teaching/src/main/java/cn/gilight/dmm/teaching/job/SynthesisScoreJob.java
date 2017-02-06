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
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;

@Service("synthesisScoreJob")
public class SynthesisScoreJob {
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
	public JobResultBean doScore(){
		String jobName = "初始化t_stu_score,t_stu_score_history中的score,credit字段";
		Long beginTime = System.currentTimeMillis();
		JobResultBean result = new JobResultBean();
		String score = getUpdateSql(false, false),//t_stu_score的score字段初始化sql
			   credit = getUpdateSql(false, true),//t_stu_score的credit字段初始化sql
			   hisScore = getUpdateSql(true, false),//t_stu_score_history的score字段初始化sql
			   hisCredit = getUpdateSql(true, true);//t_stu_score_history的credit字段初始化sql
		 baseDao.getJdbcTemplate().update(score);
		 baseDao.getJdbcTemplate().update(credit);
		 baseDao.getJdbcTemplate().update(hisScore);
		 baseDao.getJdbcTemplate().update(hisCredit);
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}
		return result;
	}
	private void end(String info){
		log.warn("======== end["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	private String getUpdateSql(Boolean isHistory,Boolean isCredit){
		String table = isHistory ? " t_stu_score_history " : "t_stu_score";
		String x = isHistory ? "course_code" : "coure_code";
		String str = " select t.stu_id,t.school_year,t.term_code,t."+x+","+(isHistory ? "t.cs," :"")+" "
				+ " case when t.centesimal_score>0 then t.centesimal_score "
				+ " when t.centesimal_score =0 and a.code_ is null then  "
				+ " t.centesimal_score when (t.centesimal_score = 0 or t.centesimal_score is null) "
				+ " and a.code_ is not null then a.centesimal_score else null end as score "
				+ " from "+table+" t left join t_code_score_hierarchy a "
				+ " on  t.hierarchical_score_code = a.code_ where t.score is null";
		String str1 = "select a.stu_id,a.school_year,a.term_code,a."+x+","+(isHistory ? "a.cs," :"")+" "
				+ " case when b.credit is not null then b.credit "
				+ " when b.credit is null and d.credit is not null then d.credit "
				+ " else e.theory_credit end as credit,"
				+ " case when b.course_attr_code is not null then  b.course_attr_code "
				+ " else d.course_attr_code end as course_attr_code,case when b.course_nature_code is not null"
				+ " then b.course_nature_code else d.course_nature_code end as course_nature_code,case when b.course_category_code "
				+ " is not null then b.course_category_code else d.course_category_code end as course_category_code "
				+ " from "+table+" a inner join t_stu c "
				+ " on a.stu_id = c.no_ left join t_course_arrangement_plan b "
				+ " on a.school_year = b.school_year and a.term_code  = b.term_code "
				+ " and a."+x+" = b.course_code  and c.class_id = b.class_id "
				+ " left join t_stu_course_choose d on a.stu_id = d.stu_id "
				+ " and a.school_year = d.school_year and a.term_code= d.term_code and a."+x+"= d.scoure_code  "
				+ " left join t_course e on a."+x+" = e.id where a.credit is null ";
		String sql= " update "+table+" a set a.score =(select b.score from ("+str+") b where "
		  + " b.stu_id=a.stu_id and b.school_year = a.school_year and b.term_code = a.term_code and b."+x+" = a."+x+" "
		  + " "+(isHistory ?" and a.cs = b.cs ":"")+") "
		  + " where exists (select 1 from ("+str+") b "
		  + " where b.stu_id=a.stu_id and b.school_year = a.school_year and b.term_code = a.term_code and b."+x+" = a."+x+" "
		  + " "+(isHistory ?" and a.cs = b.cs ":"")+") ";
		if(isCredit){
			sql = " update "+table+" a set (a.credit,a.course_attr_code,a.course_nature_code,a.course_category_code) =(select b.credit "
					 + ", b.course_attr_code,b.course_nature_code,b.course_category_code from ("+str1+") b where "
					  + " b.stu_id=a.stu_id and b.school_year = a.school_year and b.term_code = a.term_code and b."+x+" = a."+x+" "
					  + " "+(isHistory ?" and a.cs = b.cs ":"")+") "
					  + " where exists (select 1 from ("+str1+") b "
					  + " where b.stu_id=a.stu_id and b.school_year = a.school_year and b.term_code = a.term_code and b."+x+" = a."+x+" "
					  + " "+(isHistory ?" and a.cs = b.cs ":"")+") ";
		}
		System.out.println(sql);
		return sql;
	}
}
