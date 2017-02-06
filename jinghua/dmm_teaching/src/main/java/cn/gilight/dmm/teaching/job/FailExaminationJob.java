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

@Service("failExaminationJob")
public class FailExaminationJob {
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
	public JobResultBean doFailExamination(){
		String jobName = "向挂科补考页面-挂科记录表中添加数据";
		Long beginTime = System.currentTimeMillis();
		begin("#"+jobName+"#");
		JobResultBean result = new JobResultBean();
		//定义结果
		 String sql="DELETE FROM t_failExamination";
		baseDao.getJdbcTemplate().execute(sql);
		sql="INSERT INTO t_failExamination ("+t_sql+")";
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
	private String t_sql="SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH " 
			 +" WHERE TSSDH.CS=1 AND (((TSSDH.HIERARCHICAL_SCORE_CODE IS NULL) OR((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND TSSDH.CENTESIMAL_SCORE>0)) "
			 +" AND  TSSDH.CENTESIMAL_SCORE<=59 )  "
			 +" UNION "
			 +" SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH  "
			 +" LEFT JOIN t_code_score_hierarchy  TCSH ON TCSH.CODE_=TSSDH.HIERARCHICAL_SCORE_CODE  " 
			 +" WHERE TSSDH.CS=1 AND ((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND ((TSSDH.CENTESIMAL_SCORE=0) OR (TSSDH.CENTESIMAL_SCORE IS NULL))) AND TCSH.CENTESIMAL_SCORE<=59";
}
