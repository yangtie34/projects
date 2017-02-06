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
import cn.gilight.dmm.xg.dao.StuCostDao;
import cn.gilight.dmm.xg.entity.TStuAbnormalTerm;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Service("stuTermCostJob")
public class StuTermCostJob {
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
	@Transactional
	public JobResultBean doNowCost(){
		String jobName= "每个学生本学期总消费";
		begin("#"+jobName+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		List<Map<String,Object>> sb = new ArrayList<Map<String, Object>>();
		if(time.size()>0){
		sb.add(time.get(0));
		}else{
			sb = time;
		}
		List<TStuAbnormalTerm> list = getStandard(sb);
		JobResultBean result = doCost(list,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doBeforeCost(){
		String jobName= "每个学生每个学期总消费";
		begin("#"+jobName+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		List<TStuAbnormalTerm> list = getStandard(time);
		JobResultBean result = doCost(list,jobName);
		return result;
	}
	private JobResultBean doCost(List<TStuAbnormalTerm> list,String jobName){
		JobResultBean result = new JobResultBean();
		Long beginTime = System.currentTimeMillis();
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
	
    private List<TStuAbnormalTerm> getStandard(List<Map<String,Object>> dateList){
    	List<TStuAbnormalTerm> result = new ArrayList<TStuAbnormalTerm>();
    	String start =DateUtils.date2String(new Date()),end=DateUtils.date2String(new Date());
		String[] timeAry = EduUtils.getSchoolYearTerm(new Date());
		String schoolYear = timeAry[0],termCode = timeAry[1];
		for(int i=0;i<dateList.size();i++){
			start =MapUtils.getString(dateList.get(i),"startdate");
			end =MapUtils.getString(dateList.get(i),"enddate");
			schoolYear = MapUtils.getString(dateList.get(i),"schoolyear");
			termCode = MapUtils.getString(dateList.get(i),"term");
			String zcsql= getEatSql(start, end, Constant.Meal_Time_Group[0]);
			String wcsql= getEatSql(start, end, Constant.Meal_Time_Group[1]);
			String ycsql= getEatSql(start, end, Constant.Meal_Time_Group[2]);
    	    String sql = "select t.no_ as stu_id,'"+schoolYear+"' as school_year"
    	    		+ " ,'"+termCode+"' as term_code,(nvl(zc.value,0)+nvl(wc.value,0)+nvl(yc.value,0)) as sum_money,"
    	    		+ " nvl(zc.cs,0) as breakfast_,nvl(wc.cs,0) as lunch_,nvl(yc.cs,0) as dinner_"
    	    		+ " from t_stu t left join ("+zcsql+") zc on t.no_ = zc.stu_id left join ("+wcsql+") wc on t.no_ = wc.stu_id"
    	    		+ "  left join  ("+ycsql+") yc on t.no_ = yc.stu_id where  (nvl(zc.value,0)+nvl(wc.value,0)+nvl(yc.value,0)) >0 ";
    	List<TStuAbnormalTerm> list = baseDao.queryForListBean(sql, TStuAbnormalTerm.class); 
    	if(!list.isEmpty()){
    		String oldSql = " t_stu_abnormal_term where school_year = '"+schoolYear+"' and term_code = '"+termCode+"'";
			int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
			if(oldCount > 0){
				baseDao.delete("delete from "+oldSql);
				info("已删除"+schoolYear+"学年"+termCode+"学期");
			}
    	  }
    	result.addAll(list);
		}
    	return result;
    }
    
    private String getEatSql(String start,String end,String[] ary){
    	String sql= "select no_ as stu_id,sum(value) as value,count(0) as cs from "
    			+ " (select b.no_,substr(t.time_,0,10) as days,sum(t.pay_money) as value"
    			+ " from t_card_pay t,t_card a,t_stu b where "
    			+ " substr(t.time_,0,10) between '"+start+"' and '"+end+"' "
    			+ " and substr(t.time_,12,2) >='"+ary[1]+"' and substr(t.time_,12,2) <'"+ary[2]+"' "
    			+ " and t.pay_money >0 and t.card_id = a.no_ and a.people_id = b.no_ and t.card_deal_id "
    			+ " in ("+Constant.Code_Card_Deal_Type+") group by b.no_,substr(t.time_,0,10)) group by no_";
    	return sql;
    }
}
