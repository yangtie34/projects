package cn.gilight.dmm.xg.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import cn.gilight.dmm.xg.entity.TStuAbnormalYear;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;
@Service("stuYearCostJob")
public class StuYearCostJob {
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
		String jobName= "每个学生本学年总消费";
		begin("#"+jobName+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		time =mergeList(time);
		List<Map<String,Object>> sb = new ArrayList<Map<String, Object>>();
		if(time.size()>0){
		sb.add(time.get(0));
		}else{
			sb = time;
		}
		List<TStuAbnormalYear> list = getStandard(sb);
		JobResultBean result = doCost(list,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doBeforeCost(){
		String jobName= "每个学生每个学年总消费";
		begin("#"+jobName+"#");
		String day = DateUtils.getNowDate();
		List<Map<String,Object>> time = stuCostDao.querySchoolStart(day);
		time = mergeList(time);
		List<TStuAbnormalYear> list = getStandard(time);
		JobResultBean result = doCost(list,jobName);
		return result;
	}
	private JobResultBean doCost(List<TStuAbnormalYear> list,String jobName){
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
	
    private List<TStuAbnormalYear> getStandard(List<Map<String,Object>> dateList){
    	List<TStuAbnormalYear> result = new ArrayList<TStuAbnormalYear>();
    	String start =DateUtils.date2String(new Date()),end=DateUtils.date2String(new Date());
		String[] timeAry = EduUtils.getSchoolYearTerm(new Date());
		String schoolYear = timeAry[0],termCode =timeAry[1]; 
		for(int i=0;i<dateList.size();i++){
			start =MapUtils.getString(dateList.get(i),"startdate");
			end =MapUtils.getString(dateList.get(i),"enddate");
			end = end == null?DateUtils.date2String(new Date()):end;
			schoolYear = MapUtils.getString(dateList.get(i),"schoolyear");
			termCode = MapUtils.getString(dateList.get(i),"term");
			String whereSql = " where a.time_ between '"+start+"' and '"+end+"' ";
			if (termCode.equals(Globals.TERM_SECOND)){
				String str = "select start_date as startdate,end_date as enddate from t_school_start where school_year = '"+schoolYear+"' and term_code = '"+Globals.TERM_FIRST+"'";
				Map<String,Object> xx = baseDao.queryMapInLowerKey(str);
				if(xx != null && !xx.isEmpty()){
					String start1 =MapUtils.getString(dateList.get(i),"startdate");
					String end1 =MapUtils.getString(dateList.get(i),"enddate");
					whereSql = " where (a.time_ between '"+start+"' and '"+end+"' or (a.time_ between '"+start1+"' and '"+end1+"')) ";
				}
				
			}
    	String sql = "select c.no_ as stu_id, '"+schoolYear+"' as school_year,sum(a.pay_money) as sum_money from t_card_pay a inner join t_card b on a.card_id= b.no_ "
       		+ " inner join t_stu c on b.people_id = c.no_ "+whereSql+" and a.pay_money >0 and a.card_deal_id in ("+Constant.Code_Card_Deal_Type+") group by c.no_";
    	List<TStuAbnormalYear> list = baseDao.queryForListBean(sql, TStuAbnormalYear.class); 
    	if(!list.isEmpty()){
    		String oldSql = " t_stu_abnormal_year where school_year = '"+schoolYear+"'";
			int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
			if(oldCount > 0){
				baseDao.delete("delete from "+oldSql);
				info("已删除"+schoolYear+"学年");
			}
    	  }
    	result.addAll(list);
		}
    	return result;
    }
    /**
	 * 合并list中map的value相同的
	 * @param list 
	 * @return  List<Map<String,Object>>
	 */
	private List<Map<String,Object>> mergeList(List<Map<String,Object>> list){
        List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = list.get(i);
            Map<String,Object> newMap=isExistSame(i,list,map,retList);
            if(null==newMap){
              continue;
            }
            else{
                retList.add(newMap);
            }
        }
        return retList;
    }
    /**
     * value 相同的map合并
     * @param i  
     * @param value  
     * @param name
     * @param list
     * @return Map<String,Object>
     */
    private Map<String,Object> isExistSame(int i,List<Map<String,Object>> list,Map<String,Object> map,List<Map<String,Object>> result){
    	String name = MapUtils.getString(map, "schoolyear");
        for(Map<String,Object> temp:result){
        	String mc = MapUtils.getString(temp, "schoolyear");
    		if(mc.equals(name)){
    			return null;
    		}
        }
    	for(int j=0;j<list.size();j++){
        	if(j==i){
        		continue;
        	}
            Map<String,Object> innerMap = list.get(j);
            String name1 = MapUtils.getString(innerMap,"schoolyear");
            String start = MapUtils.getString(innerMap,"startdate");
            String end = MapUtils.getString(innerMap,"enddate");
            if(name.equals(name1)){
                map.put("start", start);
                map.put("end", end);
            }
        }
        return map;
    }
}
