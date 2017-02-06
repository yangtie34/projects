package cn.gilight.dmm.teaching.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.entity.TStuBehavior;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.product.EduUtils;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

@Service("scorePredictJob")
public class ScorePredictResultJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	private Logger log = Logger.getLogger(this.getClass());
	
	@Transactional
	public JobResultBean predictsScoreResult(){
		String jobName = "t_stu_predict_score_result_fdy辅导员所带班级学生成绩预测结果";
		Long beginTime = System.currentTimeMillis();
		begin("#"+jobName+"#");
		JobResultBean result = new JobResultBean();
		//学号 学年 学期 预测成绩 年级 课程ID 预测日期 逃课次数 准确率 班级ID 专业ID 专业名称 课程名称 课程性质ID  课程性质名称
		String t_sql="SELECT DISTINCT T1.*,T2.COURSEMC,T2.COURSE_NATURE_CODE,T2.COURSETYPEMC  FROM ("
				+ " SELECT TSSP.*,TS.CLASS_ID,TS.MAJOR_ID,TCDT.NAME_ "
				+ " FROM T_STU_SCORE_PREDICT_BEH TSSP "
				+ " LEFT JOIN T_STU TS ON TS.NO_=TSSP.STU_ID "
				+ " LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.CODE_=TS.MAJOR_ID"
				+ " ) T1,("+getjcSql()+") T2 WHERE T1.COURSE_ID=T2.SCOURE_CODE";
		//定义结果
		 String sql="DELETE FROM t_stu_predict_score_result_fdy";
		baseDao.getJdbcTemplate().execute(sql);
		sql="INSERT INTO t_stu_predict_score_result_fdy ("+t_sql+")";
		baseDao.getJdbcTemplate().update(sql);
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
	/**
	 * 得到课程ID表（学年，学期，课程ID，课程性质，专业名称，课程名称，课程属性名称）
	 * @return
	 */
	private String getjcSql(){
		String sql= " SELECT T.*,TCO.NAME_ COURSEMC,TC.NAME_ COURSETYPEMC FROM ("
				    + " SELECT TSCC.SCHOOL_YEAR,TSCC.TERM_CODE,TSCC.SCOURE_CODE,TSCC.COURSE_NATURE_CODE,TS.CLASS_ID FROM T_STU_COURSE_CHOOSE TSCC LEFT JOIN T_STU TS ON TS.NO_=TSCC.STU_ID "
					+ " UNION ALL "
					+ " SELECT TCAP.SCHOOL_YEAR,TCAP.TERM_CODE,TCAP.COURSE_CODE,TCAP.COURSE_NATURE_CODE,TCAP.CLASS_ID FROM T_COURSE_ARRANGEMENT_PLAN TCAP "
					+ "  ) T,T_COURSE TCO,T_CODE TC WHERE T.SCOURE_CODE=TCO.CODE_ AND TC.CODE_=T.COURSE_NATURE_CODE AND TC.CODE_TYPE='COURSE_NATURE_CODE'";
		return sql;
	}
}
