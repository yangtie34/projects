package cn.gilight.dmm.xg.job;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.framework.base.dao.BaseDao;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Service("notGradDegreeJob")
public class NotGradDegreeJob {
	@Resource
	private BaseDao baseDao;
	private Logger log = Logger.getLogger(this.getClass());
	/**
	 * 初始化学生预警-学位预警表中数据
	 * @return
	 */
	@Transactional
	public JobResultBean warningDegreeData(){
		String jobName = "向学生预警-学位预警表中添加数据";
		Long beginTime = System.currentTimeMillis();
		begin("#"+jobName+"#");
		//定义结果
		JobResultBean result = new JobResultBean();
		String sql="DELETE FROM T_STU_WARNING_DEGREE";
		baseDao.getJdbcTemplate().execute(sql);
		//执行sql
		sql="INSERT INTO T_STU_WARNING_DEGREE ("+getWxwData()+")";
		baseDao.getJdbcTemplate().update(sql);
//		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}
		return result;
	}
	/**
	 * 初始化学生预警-毕业预警表中数据
	 * @return
	 */
	@Transactional
	public JobResultBean warningGraduationData(){
		String jobName = "向学生预警-毕业预警表中添加数据";
		Long beginTime = System.currentTimeMillis();
		begin("#"+jobName+"#");
		//定义结果
		JobResultBean result = new JobResultBean();
		String sql="DELETE FROM T_STU_WARNING_GRADUATION";
		baseDao.getJdbcTemplate().execute(sql);
		//执行sql
		sql="INSERT INTO T_STU_WARNING_GRADUATION (SELECT T1.STU_ID,T1.CREDITS,T2.CREDITS_NOT_PASS FROM ("+getWxwData()+") T1,("+getWbyData()+") T2 WHERE T1.STU_ID=T2.STU_ID)";
		baseDao.getJdbcTemplate().update(sql);
//		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}
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
	private String year = EduUtils.getSchoolYear9();
	private String termCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[1];
	private String getWxwData(){
		 String wxw_sql="SELECT TSS.STU_ID,SUM(TC.THEORY_CREDIT) CREDITS FROM "
				+" (SELECT T1.STU_ID,T1.SCHOOL_YEAR,T1.TERM_CODE,T1.COURSE_CODE FROM  (SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH  WHERE TSSDH.CS=1 AND (((TSSDH.HIERARCHICAL_SCORE_CODE IS NULL) OR((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND TSSDH.CENTESIMAL_SCORE>0))  AND  TSSDH.CENTESIMAL_SCORE<=59 )   UNION  SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH   LEFT JOIN t_code_score_hierarchy  TCSH ON TCSH.CODE_=TSSDH.HIERARCHICAL_SCORE_CODE   WHERE TSSDH.CS=1 AND ((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND TSSDH.CENTESIMAL_SCORE=0) AND TCSH.CENTESIMAL_SCORE<=59) T1 " // WHERE T1.SCHOOL_YEAR='"+year+"' AND T1.TERM_CODE = '"+termCode+"'
				+" ) TSS  "
				+" LEFT JOIN T_COURSE TC ON TC.CODE_=TSS.COURSE_CODE "
				+" GROUP BY TSS.STU_ID"	;
		 return wxw_sql;
	}
	private String getWbyData(){
	 String wby_sql="SELECT TSS.STU_ID,SUM(TC.THEORY_CREDIT) CREDITS_NOT_PASS FROM( "
						  +" SELECT T1.STU_ID,T1.SCHOOL_YEAR,T1.TERM_CODE,T1.COURE_CODE FROM  (SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURE_CODE FROM T_STU_SCORE TSSDH  WHERE (((TSSDH.HIERARCHICAL_SCORE_CODE IS NULL) OR((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND TSSDH.CENTESIMAL_SCORE>0))  AND  TSSDH.CENTESIMAL_SCORE<=59 )   UNION  SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURE_CODE FROM T_STU_SCORE TSSDH   LEFT JOIN t_code_score_hierarchy  TCSH ON TCSH.CODE_=TSSDH.HIERARCHICAL_SCORE_CODE   WHERE ((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND TSSDH.CENTESIMAL_SCORE=0) AND TCSH.CENTESIMAL_SCORE<=59) T1 WHERE T1.SCHOOL_YEAR='"+year+"' AND T1.TERM_CODE  = '"+termCode+"' "
						  +" ) TSS "
						  +" LEFT JOIN T_COURSE TC ON TC.CODE_=TSS.COURE_CODE "
						  +" GROUP BY TSS.STU_ID";
	 return wby_sql;
	}
}
