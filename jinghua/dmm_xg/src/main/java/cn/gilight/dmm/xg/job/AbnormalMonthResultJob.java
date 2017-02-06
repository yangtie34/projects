package cn.gilight.dmm.xg.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.xg.dao.StuCostDao;
import cn.gilight.dmm.xg.entity.TStuAbnormalWeek;
import cn.gilight.dmm.xg.service.StuHighCostService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;
@Service("abnormalMonthResultJob")
public class AbnormalMonthResultJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private StuHighCostService stuHighCostService;
	@Resource
	private StuCostDao stuCostDao;
	
	/**
	 * 加号代表是高消费
	 */
	public static final String[] HIGHCOST = {"+",">","高消费学生","highcost","high_standard"};
	/**
	 * 加号代表是高消费
	 */
	public static final String[] LOWCOST = {"-","<","低消费学生","lowcost","low_standard"};
	private Logger log = Logger.getLogger(this.getClass());
	@Transactional
	public JobResultBean doNowHigh(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "上月高消费名单";
		begin("#"+jobName+"#");
		List<Map<String,Object>> time = stuCostDao.queryMonthList();
		List<Map<String,Object>> sb = new ArrayList<Map<String, Object>>();
		if(time.size()>0){
		sb.add(time.get(0));
		}else{
			sb = time;
		}
		JobResultBean list = getStandard(sb,jobName,beginTime,HIGHCOST);
		return list;
	}
	@Transactional
	public JobResultBean doNowLow(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "上月低消费名单";
		begin("#"+jobName+"#");
		List<Map<String,Object>> time = stuCostDao.queryMonthList();
		List<Map<String,Object>> sb = new ArrayList<Map<String, Object>>();
		if(time.size()>0){
		sb.add(time.get(0));
		}else{
			sb = time;
		}
		JobResultBean list = getStandard(sb,jobName,beginTime,LOWCOST);
		return list;
	}
	@Transactional
	public JobResultBean doLsLow(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "历史每月低消费名单";
		begin("#"+jobName+"#");
		List<Map<String,Object>> time = stuCostDao.queryMonthList();
		JobResultBean list = getStandard(time,jobName,beginTime,LOWCOST);
		return list;
	}
	@Transactional
	public JobResultBean doLsHigh(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "历史每月高消费名单";
		begin("#"+jobName+"#");
		List<Map<String,Object>> time = stuCostDao.queryMonthList();
		JobResultBean list = getStandard(time,jobName,beginTime,HIGHCOST);
		return list;
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
	
    private JobResultBean getStandard(List<Map<String,Object>> dateList,String jobName,Long beginTime,String[] type){
    	JobResultBean result = new JobResultBean();
		for(int i=0;i<dateList.size();i++){
			Map<String,Object> dateMap = dateList.get(i);
			String schoolYear = MapUtils.getString(dateMap,"schoolyear");
			String termCode = MapUtils.getString(dateMap,"term");
			String start = MapUtils.getString(dateMap,"startdate");
			String end = MapUtils.getString(dateMap,"enddate");
			int month = MapUtils.getIntValue(dateMap, "month");
		String wheresql = " where t.school_year = '"+schoolYear+"' and t.term_code = '"+termCode+"' and t.month = "+month+" ";
    	Double val = stuCostDao.queryStandard(schoolYear, termCode, Constant.Type_DayCost_Standard, type);
    	if (val == null){
    		continue;
    	}
    	int days = DateUtils.getDayBetween(start, end);
    	val = days*val;
    	String deletesql ="DELETE FROM t_stu_abnormal_month_result t "+wheresql+" and t.type_ = '"+type[3]+"'";
		baseDao.getJdbcTemplate().execute(deletesql);
    	String sql = "insert into t_stu_abnormal_month_result (select t.id,t.stu_id,t.school_year,t.term_code,t.month,'"+type[3]+"' as type_,t.sum_money from t_stu_abnormal_month t "+wheresql+" and t.sum_money "+type[1]+val+")";
		baseDao.getJdbcTemplate().update(sql); 
    }
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}
		return result;
   }
}
