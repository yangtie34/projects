package cn.gilight.dmm.xg.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import cn.gilight.dmm.xg.entity.TStuAbnormalMeal;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("stuMealCostJob")
public class StuMealCostJob {
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
		JobResultBean result = new JobResultBean();
		Long beginTime = System.currentTimeMillis();
		String jobName= "近期每个学生每餐消费金额";
		String jobName1= "近期每个学生早餐消费金额";
		String jobName2= "近期每个学生午餐消费金额";
		String jobName3= "近期每个学生晚餐消费金额";
		begin("#"+jobName1+"#");
		begin("#"+jobName2+"#");
		begin("#"+jobName3+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		List<Map<String,Object>> sb = new ArrayList<Map<String, Object>>();
		if(time.size()>0){
		sb.add(time.get(0));
		}else{
			sb = time;
		}
		JobResultBean list = getStandard(sb,Constant.Meal_Time_Group[0],true,jobName1,beginTime);
		JobResultBean list1 = getStandard(sb,Constant.Meal_Time_Group[1],true,jobName2,beginTime);
		JobResultBean list2 = getStandard(sb,Constant.Meal_Time_Group[2],true,jobName3,beginTime);
		if(list.getIsTrue() && list1.getIsTrue() && list2.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}else{
			String info = "#"+jobName+"#数据保存出错";
			error(info);
			result.setIsTrue(false);
			result.setMsg(info);
		}
		return result;
	}
	public JobResultBean doBeforeCostZc(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "历史每个学生早餐消费金额";
		begin("#"+jobName+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		JobResultBean list = getStandard(time,Constant.Meal_Time_Group[0],false,jobName,beginTime);
		return list;
	}
	public JobResultBean doBeforeCostWc(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "历史每个学生午餐消费金额";
		begin("#"+jobName+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		JobResultBean list = getStandard(time,Constant.Meal_Time_Group[1],false,jobName,beginTime);
		return list;
	}
	public JobResultBean doBeforeCostYc(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "历史每个学生晚餐消费金额";
		begin("#"+jobName+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		JobResultBean list = getStandard(time,Constant.Meal_Time_Group[2],false,jobName,beginTime);
		return list;
	}
	public JobResultBean doCost(List<TStuAbnormalMeal> list,String jobName){
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
	
    private JobResultBean getStandard(List<Map<String,Object>> dateList,String[] type,Boolean isNow,String jobName,Long beginTime){
    	JobResultBean result = new JobResultBean();
    	String start =DateUtils.date2String(new Date()),end=DateUtils.date2String(new Date());
		String[] timeAry = EduUtils.getSchoolYearTerm(new Date());
		String schoolYear = timeAry[0],termCode = timeAry[1];
		int weekth= 0; 
		for(int i=0;i<dateList.size();i++){
			List<TStuAbnormalMeal> list = new ArrayList<TStuAbnormalMeal>();
			schoolYear=MapUtils.getString(dateList.get(i), "schoolyear");
			termCode =MapUtils.getString(dateList.get(i), "term");
			end = MapUtils.getString(dateList.get(i), "enddate");
			String now = DateUtils.date2String(new Date()); 
			end = end== null|| Integer.parseInt(now.replace("-", ""))<Integer.parseInt(end.replace("-", ""))?now:end;
		if(isNow){
			start = getBeforeDate(end,7);
		}else{
			start = MapUtils.getString(dateList.get(i), "startdate");
		}
		for(int k =0;k<DateUtils.getDayBetween(start, end)+1;k++){
			String day = getBeforeDate(end,k);int x = 0;
    	String sql = "select c.no_ as stu_id, '"+schoolYear+"' as school_year,'"+termCode+"' as term_code,substr(a.time_,0,10) as date_,'"+type[0]+"' as meal_name,sum(a.pay_money) as sum_money from t_card_pay a inner join t_card b on a.card_id= b.no_ "
       		+ " inner join t_stu c on b.people_id = c.no_  where a.time_ like '"+day+"%' and substr(a.time_,12,2) >='"+type[1]+"' and substr(a.time_,12,2) <'"+type[2]+"' and a.pay_money >0 and a.card_deal_id = '"+Constant.CODE_CARD_DEAL_210+"' group by  c.no_,substr(a.time_,0,10)";
    	 list = baseDao.queryForListBean(sql, TStuAbnormalMeal.class); 
    	if(!list.isEmpty()){
     	    	String oldSql = " t_stu_abnormal_meal where school_year = '"+schoolYear+"' and term_code = '"+termCode+"' and date_  = '"+day+"' and meal_name = '"+type[0]+"' ";
     			int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
     			if(oldCount > 0){
     				baseDao.delete("delete from "+oldSql);
     			info("已删除"+schoolYear+"学年"+termCode+"学期"+type[0]);
     		}
    		}
    	 result = doCost(list,jobName);
		 if(result.getIsTrue()==false){
				break;
			}
//    	 for(int j=0;j<list.size();j++){
//    	 if(j!=0 && j % 1000==0){
//    		 result = doCost(list.subList(x, j),jobName);
//    		 if(result.getIsTrue()==false){
// 				break;
// 			}
//    		 System.gc();
//    		 x = j;
//    	 }
//    	 if(list.size() - j < 1000){
//    		 result = doCost(list.subList(j, list.size()),jobName);
//    		 if(result.getIsTrue()==false){
// 				break;
// 			}
//    		 System.gc(); 
//    	 }
//		}
		}
		}
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}
    	return result;
    }
    private String getBeforeDate(String date,int len){
    	Date dat = DateUtils.string2Date(date);
    	Calendar cal = new GregorianCalendar(); 
		cal.setTime(dat);
		cal.add(Calendar.DATE, -len);
		dat = cal.getTime();
		return  DateUtils.date2String(dat);
    }
}

