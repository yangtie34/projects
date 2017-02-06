package cn.gilight.dmm.xg.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.text.html.ListView;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.StuCostDao;
import cn.gilight.dmm.xg.entity.TStuCostStandard;
import cn.gilight.dmm.xg.service.StuHighCostService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;

@Service("stuCostStandardJob")
public class StuCostStandardJob {
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
	public static final String[] HIGHCOST = {"+",">","高消费学生","highcost"};
	/**
	 * 加号代表是高消费
	 */
	public static final String[] LOWCOST = {"-","<","低消费学生","lowcost"};
	private Logger log = Logger.getLogger(this.getClass());
	@Transactional
	public JobResultBean doNow(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "当前学期高低消费标准";
		begin("#"+jobName+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		List<Map<String,Object>> sb = new ArrayList<Map<String, Object>>();
		if(time.size()>0){
		sb.add(time.get(0));
		}
		JobResultBean list = getStandard(sb,jobName,beginTime);
		return list;
	}
	@Transactional
	public JobResultBean doLs(){
		Long beginTime = System.currentTimeMillis();
		String jobName= "历史学期高低消费标准";
		begin("#"+jobName+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		List<Map<String,Object>> sb = new ArrayList<Map<String, Object>>();
		if (time.size()>1){
			sb.addAll(time.subList(1,time.size()));
		}
		JobResultBean list = getStandard(sb,jobName,beginTime);
		return list;
	}
	private JobResultBean doCost(List<TStuCostStandard> list,String jobName,Long beginTime){
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
    	List<TStuCostStandard> list = new ArrayList<TStuCostStandard>();
		for(Map<String,Object> map : dateList){
			String schoolYear = MapUtils.getString(map, "schoolyear"),
				   termCode   = MapUtils.getString(map, "term"),
				   start      = MapUtils.getString(map, "startdate"),
				   end        = MapUtils.getString(map, "enddate");
			String term = termCode.equals(Globals.TERM_FIRST)?Globals.TERM_SECOND:Globals.TERM_FIRST;
			String year = termCode.equals(Globals.TERM_FIRST)?String.valueOf(Integer.parseInt(schoolYear.substring(0,4))-1)+"-"+String.valueOf(Integer.parseInt(schoolYear.substring(0,4))):schoolYear;
			String sql = "select t.start_date,t.end_date from t_school_start t where t.school_year='"+year+"' and  t.term_code = '"+term+"'";
			String str = "select min(substr(t.time_,0,10)) as min,max(substr(t.time_,0,10)) as max from t_card_pay t where length(t.time_) >=10";
			Map<String,Object> minAndMax = baseDao.queryMapInLowerKey(str);
			String min =  MapUtils.getString(minAndMax, "min"),max= MapUtils.getString(minAndMax, "max");
			String midSql = "select nvl(median(t.breakfast_+t.lunch_+t.dinner_),0) as value from t_stu_abnormal_term t where t.school_year = '"+schoolYear+"' and t.term_code ='"+termCode+"'";
			String midSql1 = "select nvl(median(t.breakfast_+t.lunch_+t.dinner_),0) as value from t_stu_abnormal_term t where t.school_year = '"+year+"' and t.term_code ='"+term+"'";
			Double mid = baseDao.queryForListDouble(midSql).get(0),mid1 = baseDao.queryForListDouble(midSql1).get(0);
			if(min == null){
				return result; 
			}
			Map<String,Object> xx = baseDao.queryMapInLowerKey(sql);
			String start1 = MapUtils.getString(xx, "start_date"),end1 = MapUtils.getString(xx, "end_date");
			int days = 0;
			String wheresqlx = " where t.school_year='"+year+"' and  t.term_code = '"+term+"' ";
			String wheresql = wheresqlx+" and (t.breakfast_+t.lunch_+t.dinner_) >="+mid1+"";
			String str2 = " where t.school_year='"+schoolYear+"' and  t.term_code = '"+termCode+"' ";
			String str1 =  str2+" and (t.breakfast_+t.lunch_+t.dinner_) >="+mid+"";
	        String start_one =null,end_one=null,start_two = null,end_two= null;
			if (start1 != null || end1 != null){
				if(DateUtils.compare(start1, max)){
					continue;
				}else if (DateUtils.compare(min, end)){
					continue;
				}else if (DateUtils.compare(min, end1) && DateUtils.compare(start, min) && DateUtils.compare(max, end)){
				    days = DateUtils.getDayBetween(start, end);
				    start_one=start;end_one = end;
				    wheresql =str1;wheresqlx = str2;
				}else if (DateUtils.compare(min, end1) && DateUtils.compare(start, min) && DateUtils.compare(end, max)){
					days = DateUtils.getDayBetween(start, max);
					start_one=start;end_one = max;
					wheresql =str1;wheresqlx = str2;
				}else if (DateUtils.compare(min, end1) && DateUtils.compare(start, min) && DateUtils.compare(start, max)){
					continue;
				}else if(DateUtils.compare(min, end1) && DateUtils.compare(min, start) && DateUtils.compare(max, end)){
					days = DateUtils.getDayBetween(min, end);
					start_one=min;end_one = end;
					wheresql =str1;wheresqlx = str2;
				}else if(DateUtils.compare(min, end1) && DateUtils.compare(min, start) && DateUtils.compare(end, max)){
					days = DateUtils.getDayBetween(min, max);
					start_one=min;end_one = max;
					wheresql =str1;wheresqlx = str2;
				}else if(DateUtils.compare(min, end1) && DateUtils.compare(min, start) && DateUtils.compare(min, end)){
					continue;
				}else if(DateUtils.compare(end1, min) && DateUtils.compare(min, start1) && DateUtils.getDayBetween(start1, min)<30 && DateUtils.compare(max, end1)){
					days =  DateUtils.getDayBetween(min, end1);
					start_one=min;end_one = end1;
				}else if(DateUtils.compare(end1, min) && DateUtils.compare(min, start1) && DateUtils.compare(end1, max)){
					days =  DateUtils.getDayBetween(min, max);
					start_one=min;end_one = max;
				}else if(DateUtils.compare(end1, min) && DateUtils.compare(start1,min) && DateUtils.compare(end1, max)&& DateUtils.compare(max, start1)){
					days =  DateUtils.getDayBetween(start1, max);
					start_one=start1;end_one = max;
				}else if(DateUtils.compare(end1, min) && DateUtils.compare(start1,min) && DateUtils.compare(max, end1)){
					days =  DateUtils.getDayBetween(start1, end1);
					start_one=start1;end_one = end1;
				}else if(DateUtils.compare(end1, min) && DateUtils.compare(start1,min) && DateUtils.compare(start1, max)){
					continue;
				}else if(DateUtils.compare(end1, min) && DateUtils.compare(min, start1) && DateUtils.getDayBetween(start1, min)>=30 && DateUtils.compare(max, end)){
					days =  DateUtils.getDayBetween(min, end1)+DateUtils.getDayBetween(start, end);
					start_one=min;end_one = end1;start_two=start;end_one = end;
					wheresqlx =" where ((t.school_year='"+year+"' and  t.term_code = '"+term+"') or (t.school_year='"+schoolYear+"' and  t.term_code = '"+termCode+"')) ";
				}else if(DateUtils.compare(end1, min) && DateUtils.compare(min, start1) && DateUtils.getDayBetween(start1, min)>=30 && DateUtils.compare(max, start) && DateUtils.compare(end, max)){
					days =  DateUtils.getDayBetween(min, end1)+DateUtils.getDayBetween(start, max);
					start_one=min;end_one = end1;start_two=start;end_one = max;
					wheresqlx =" where ((t.school_year='"+year+"' and  t.term_code = '"+term+"') or (t.school_year='"+schoolYear+"' and  t.term_code = '"+termCode+"')) ";
				}else if(DateUtils.compare(end1, min) && DateUtils.compare(min, start1) && DateUtils.getDayBetween(start1, min)>=30 && DateUtils.compare(max, end1) && DateUtils.compare(start, max)){
					days =  DateUtils.getDayBetween(min, end1);
					start_one=min;end_one = end1;
				}else{
					continue;
				}
			}else{
				if(DateUtils.compare(min, end)){
					continue;
				}else if(DateUtils.compare(start, max)){
					continue;
				}else if (DateUtils.compare(start, min) && DateUtils.compare(max,end)){
					days =  DateUtils.getDayBetween(start, end);
					start_one=start;end_one = end;
					wheresql =str1;wheresqlx =str2;
				}else if(DateUtils.compare(start, min)&& DateUtils.compare(end,max) ){
					days =  DateUtils.getDayBetween(start, max);
					start_one=start;end_one = max;
					wheresql =str1;wheresqlx =str2;
				}else if(DateUtils.compare(min, start)&&DateUtils.compare(end, min)&& DateUtils.compare(end,max)){
					days =  DateUtils.getDayBetween(min, max);
					start_one=min;end_one = max;
					wheresql =str1;wheresqlx =str2;
				}else if(DateUtils.compare(min, start)&&DateUtils.compare(end, min)&& DateUtils.compare(max,end)){
					days =  DateUtils.getDayBetween(min, end);
					start_one=min;end_one = end;
					wheresql =str1;wheresqlx =str2;
				}else{
					continue;
				}
			}
			if (start_two != null){
				String xxSql= "select nvl(median(t.breakfast_+t.lunch_+t.dinner_),0) as value from t_stu_abnormal_term t "+wheresqlx;
				Double sb = baseDao.queryForListDouble(xxSql).get(0);
				wheresql = wheresqlx+" and (t.breakfast_+t.lunch_+t.dinner_) >='"+sb+"'";
			}
			 String wheresql1 = wheresqlx+" and t.meal_name = '早餐'",
			 wheresql2 = wheresqlx+" and t.meal_name = '午餐'",
			 wheresql3 = wheresqlx+" and t.meal_name = '晚餐'",
			 tableName = "t_stu_abnormal_term",
		     tableName1 = "t_stu_abnormal_meal";
		     TStuCostStandard temp = getData(days,schoolYear,termCode,tableName, wheresql,Constant.Type_DayCost_Standard,false,start_one,end_one,start_two,end_two),
		    		         temp1= getData(days,schoolYear,termCode,tableName1,wheresql1,Constant.Type_BreakFastCost_Standard,true,start_one,end_one,start_two,end_two),
		    		         temp2= getData(days,schoolYear,termCode,tableName1,wheresql2,Constant.Type_LunchCost_Standard,true,start_one,end_one,start_two,end_two),
		    		         temp3= getData(days,schoolYear,termCode,tableName1,wheresql3,Constant.Type_DinnerCost_Standard,true,start_one,end_one,start_two,end_two);
		     list.add(temp);list.add(temp1);list.add(temp2);list.add(temp3);
		}
		result = doCost(list, jobName, beginTime);
		return result;
   }
    private TStuCostStandard getData(int days,String schoolYear,String termCode,String tableName,String whereSql,String type,Boolean isMeal,
    		String start_one,String end_one,String start_two,String end_two){
    	TStuCostStandard  map = new TStuCostStandard();
    	Map<String,Object> termHighBzMap = stuCostDao.queryCostValue(tableName, HIGHCOST, whereSql);
    	Map<String,Object> termLowBzMap = stuCostDao.queryCostValue(tableName, LOWCOST, whereSql);
		String highName= MapUtils.getString(termHighBzMap, "name"),lowName = MapUtils.getString(termLowBzMap, "name");
		String highCode= MapUtils.getString(termHighBzMap, "code"),lowCode = MapUtils.getString(termLowBzMap, "code");
    	Double termHighBz = MapUtils.getDoubleValue(termHighBzMap, "value") ,termLowBz =MapUtils.getDoubleValue(termLowBzMap, "value") ; 
    	Double dayHighBz = 0d,dayLowBz = 0d;
		if (isMeal){
			dayHighBz = termHighBz;dayLowBz = termLowBz;
		}else{
		dayHighBz = MathUtils.get2Point(MathUtils.getDivisionResult(termHighBz, Double.valueOf(days), 2));
		dayLowBz = MathUtils.get2Point(MathUtils.getDivisionResult(termLowBz, Double.valueOf(days), 2));
		}
        map.setSchool_year(schoolYear);map.setTerm_code(termCode);map.setHigh_standard(dayHighBz);
        map.setLow_standard(dayLowBz);map.setType_(type);map.setStart_one(start_one);map.setEnd_one(end_one);
        map.setStart_two(start_two);map.setEnd_two(end_two);map.setHigh_alg_name(highName);map.setLow_alg_name(lowName);
        map.setHigh_alg(highCode);map.setLow_alg(lowCode);
        baseDao.delete("delete from t_stu_cost_standard t where t.school_year ='"+schoolYear+"'"
				+ " and t.term_code ='"+termCode+"' and t.type_= '"+type+"'");
        return map;
    }
}
