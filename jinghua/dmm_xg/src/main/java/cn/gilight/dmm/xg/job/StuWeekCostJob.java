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
import cn.gilight.dmm.xg.entity.TStuAbnormalWeek;
import cn.gilight.dmm.xg.service.StuHighCostService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Service("stuWeekCostJob")
public class StuWeekCostJob {
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
	
	private Logger log = Logger.getLogger(this.getClass());
	public JobResultBean doNowCost(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "每个学生上周总消费";
		begin("#"+jobName+"#");
		List<Map<String,Object>> time = (List<Map<String, Object>>) stuHighCostService.getTimeSelectList().get("list");
		List<Map<String,Object>> sb = new ArrayList<Map<String, Object>>();
		if(time.size()>0){
		sb.add(time.get(0));
		}else{
			sb = time;
		}
		JobResultBean list = getStandard(sb,jobName,beginTime);
		return list;
	}
	public JobResultBean doBeforeCost(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "每个学生每周总消费";
		begin("#"+jobName+"#");
		List<Map<String,Object>> time = (List<Map<String, Object>>) stuHighCostService.getTimeSelectList().get("list");
		JobResultBean list = getStandard(time,jobName,beginTime);
		return list;
	}
	private JobResultBean doCost(List<TStuAbnormalWeek> list,String jobName){
		JobResultBean result = new JobResultBean();
		Long beginTime = System.currentTimeMillis();
		try{
 		if(!list.isEmpty()){
		hibernateDao.saveAllAutoCommit(list);
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
	
    private JobResultBean getStandard(List<Map<String,Object>> dateList,String jobName,Long beginTime){
    	JobResultBean result = new JobResultBean();
    	String start =DateUtils.date2String(new Date()),end=DateUtils.date2String(new Date());
		String[] timeAry = EduUtils.getSchoolYearTerm(new Date());
		String schoolYear = timeAry[0],termCode = timeAry[1];
		int weekth= 0;
		for(int i=0;i<dateList.size();i++){
			Map<String,Object> dateMap = dateList.get(i);
			String date = MapUtils.getString(dateMap, "id");
			String[] dateAry = date.split("[+]");
			String[] startend = dateAry[0].split("~");weekth = Integer.parseInt(dateAry[1]);
			start =startend[0];end =startend[1];String[] xnxq = EduUtils.getSchoolYearTerm(start);
			schoolYear = xnxq[0];termCode =xnxq[1];
    	String sql = "select b.people_id as stu_id,'"+start+"' as start_date,'"+end+"' as end_date,"+weekth+" as weekth,"
       		+ " sum(a.pay_money) as sum_money,'"+schoolYear+"' as school_year,'"+termCode+"' as term_code  from t_card_pay a inner join t_card b on a.card_id= b.no_ "
       		+ "  where a.time_ between '"+start+"' and '"+end+"' and a.pay_money >0  group by  b.people_id";
    	List<TStuAbnormalWeek> list = baseDao.queryForListBean(sql, TStuAbnormalWeek.class); 
    	if(!list.isEmpty()){
    		String oldSql = " t_stu_abnormal_week where "
    				+ " weekth = "+weekth+" and school_year = '"+schoolYear+"' and term_code = '"+termCode+"'";
			int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
			if(oldCount > 0){
				baseDao.delete("delete from "+oldSql);
				info("已删除"+schoolYear+"学年"+termCode+"学期"+weekth+"周");
			}
			result = doCost(list,jobName);
			if(result.getIsTrue() == false){
				break;
			}
    	  }
		}
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setIsTrue(true);
			result.setMsg(info);
		}
    	return result;
    }
}
