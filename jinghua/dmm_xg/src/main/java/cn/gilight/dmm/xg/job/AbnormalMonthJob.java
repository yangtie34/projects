package cn.gilight.dmm.xg.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.StuCostDao;
import cn.gilight.dmm.xg.entity.TStuAbnormalMonth;
import cn.gilight.dmm.xg.entity.TStuAbnormalWeek;
import cn.gilight.dmm.xg.service.StuHighCostService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;
@Service("abnormalMonthJob")
public class AbnormalMonthJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private StuCostDao stuCostDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	public JobResultBean doNowCost(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "每个学生上月总消费";
		begin("#"+jobName+"#");
		List<Map<String,Object>> time = stuCostDao.queryMonthList();
		List<Map<String,Object>> sb = new ArrayList<Map<String, Object>>();
		if(time.size()>0){
		sb.add(time.get(0));
		}else{
			sb = time;
		}
		JobResultBean list = getStandard(sb,jobName,beginTime);
		return list;
	}
	public JobResultBean doLsCost(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "每个学生每月总消费";
		begin("#"+jobName+"#");
		List<Map<String,Object>> time = stuCostDao.queryMonthList();
		JobResultBean list = getStandard(time,jobName,beginTime);
		return list;
	}
	private JobResultBean doCost(List<TStuAbnormalMonth> list,String jobName){
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
		for(int i=0;i<dateList.size();i++){
			Map<String,Object> dateMap = dateList.get(i);
			String schoolYear = MapUtils.getString(dateMap,"schoolyear");
			String termCode = MapUtils.getString(dateMap,"term");
			String start = MapUtils.getString(dateMap,"startdate");
			String end = MapUtils.getString(dateMap,"enddate");
			int month = MapUtils.getIntValue(dateMap, "month");
    	String sql = "select c.no_ as stu_id,"+month+" as month,"
       		+ " sum(a.pay_money) as sum_money,'"+schoolYear+"' as school_year,'"+termCode+"' as term_code  from t_card_pay a inner join t_card b on a.card_id= b.no_ "
       		+ " inner join t_stu c on b.people_id = c.no_ where a.time_ between '"+start+"' and '"+end+"' and a.pay_money >0  and a.card_deal_id in ("+Constant.Code_Card_Deal_Type+")  group by  c.no_";
    	List<TStuAbnormalMonth> list = baseDao.queryForListBean(sql, TStuAbnormalMonth.class); 
    	if(!list.isEmpty()){
    		String oldSql = " t_stu_abnormal_month where "
    				+ " month = "+month+" and school_year = '"+schoolYear+"' and term_code = '"+termCode+"'";
			int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
			if(oldCount > 0){
				baseDao.delete("delete from "+oldSql);
				info("已删除"+schoolYear+"学年"+termCode+"学期"+month+"月");
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
