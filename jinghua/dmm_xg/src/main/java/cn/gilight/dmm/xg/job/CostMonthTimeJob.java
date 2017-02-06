package cn.gilight.dmm.xg.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.StuCostDao;
import cn.gilight.dmm.xg.entity.TStuCostTimeMonth;
import cn.gilight.dmm.xg.service.StuHighCostService;
import cn.gilight.dmm.xg.service.StuLowCostService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;
@Service("costMonthTimeJob")
public class CostMonthTimeJob {
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
	private StuLowCostService stuLowCostService;
	@Resource
	private StuCostDao stuCostDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	@Transactional
	public JobResultBean getTimeList(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "高低消费月选择存储";
		begin("#"+jobName+"#");
		JobResultBean result = getMonthData(jobName, beginTime);
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
	private JobResultBean doTime(List<TStuCostTimeMonth> list,String jobName,Long beginTime){
		JobResultBean result = new JobResultBean();
	
		try{
 		if(!list.isEmpty()){
		hibernateDao.saveAll(list);
		info("插入"+jobName+list.size()+" 条");
 		}else{
 		info("插入0条");
 		}
 		String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
		end(info);
		result.setIsTrue(true);
		result.setMsg(info);
		}catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			String info = "#"+jobName+"#数据保存出错,"+e.getStackTrace();
			error(info);
			result.setIsTrue(false);
			result.setMsg(info);
			e.printStackTrace();
		}
		return result;
	}
    public JobResultBean getMonthData(String jobName,Long beginTime){
    	String now = DateUtils.getNowDate();
    	JobResultBean jg = new JobResultBean();
    	List<Map<String,Object>> list = stuCostDao.querySchoolStart(now);
    	List<TStuCostTimeMonth>result = new ArrayList<TStuCostTimeMonth>();
//    	if (list == null || list.isEmpty()){
//    		Map<String,Object> temp2 = new HashMap<String, Object>();
//    		temp2.put("id", 1);temp2.put("mc", "请初始化学期开始,结束日期");
//    		result.add(temp2);
//    		return result;
//    	}
    	for(Map<String,Object> temp: list){
    		String start = MapUtils.getString(temp, "startdate");
    		String end = MapUtils.getString(temp, "enddate");
    		String schoolYear =  MapUtils.getString(temp, "schoolyear");
    		String termCode =  MapUtils.getString(temp, "term");
    		String sql = "select * from t_stu_cost_standard t where t.school_year='"+schoolYear+"'"
    				+ " and t.term_code = '"+termCode+"' and t.type_ = '"+Constant.Type_DayCost_Standard+"'";
    		List<Map<String,Object>> standardList =baseDao.queryListInLowerKey(sql);
    		if (standardList == null || standardList.isEmpty()){
    			continue;
    		}
    		if (DateUtils.compare(end, now)){end = now;}
    		int j = 1;
    		for (String i = start;DateUtils.compare(end, i);){
    			TStuCostTimeMonth temp1 = new TStuCostTimeMonth();
    			String js =DateUtils.getNextMonthYesterday(i);
    			if(DateUtils.compare(js, end) && !end.equals(now)){js= end;}
    			if(DateUtils.compare(js, end) &&  end.equals(now)){break;}
    			int days = DateUtils.getDayBetween(i, js);
    			temp1.setMonth(j);
    			temp1.setDays(days);
    			temp1.setEnd_date(js);
    			temp1.setSchool_year(schoolYear);
    			temp1.setTerm_code(termCode);
    			temp1.setStart_date(i);
    			result.add(temp1);
    			i = DateUtils.getNextDayByLen(js, 1);
    		    j++;
    		}
    		baseDao.delete("delete from t_stu_cost_time_month t where "
    				+ " t.school_year ='"+schoolYear+"' and t.term_code = '"+termCode+"'"); 
    	}
    	jg = doTime(result, jobName, beginTime);
    	return jg;
    }
}
