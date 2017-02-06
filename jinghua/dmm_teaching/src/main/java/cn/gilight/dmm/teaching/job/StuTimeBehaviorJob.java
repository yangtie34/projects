package cn.gilight.dmm.teaching.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.ptg.IntPtg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.BhrConstant;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.teaching.dao.SmartDao;
import cn.gilight.dmm.teaching.entity.TStuBehaviorTime;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 学生行为-时间维度
 * 
 * @author hanpl
 * 
 */
@Service("stuTimeBehaviorJob")
public class StuTimeBehaviorJob {


	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private SmartDao samrtDao; 
	@Resource
	private BusinessService businessService ;
	private Logger log = Logger.getLogger(this.getClass());
	
	public static final String JYLX_SQL = " and t.card_deal_id = '"+Constant.CODE_CARD_DEAL_210+"' ";
	@Transactional
	public JobResultBean doNowEat(){
		String jobName = "t_stu_behavior_time当前早中晚餐人数最多时间";
		List<TStuBehaviorTime> all = new ArrayList<>();
		String wheresql = " and substr(t.time_,12,2) <'"+Constant.Meal_Time_Group[0][2]+"' "+JYLX_SQL;
		String wheresql1 = " and substr(t.time_,12,2) <'"+Constant.Meal_Time_Group[1][2]+"' and substr(t.time_,12,2) >='"+Constant.Meal_Time_Group[1][1]+"'" +JYLX_SQL;
		String wheresql2 = " and substr(t.time_,12,2) >='"+Constant.Meal_Time_Group[2][1]+"' "+JYLX_SQL;
		String table1 = "T_CARD_PAY",table2 ="T_CARD",column ="t.card_id";
		List<TStuBehaviorTime> list1 = queryNowBhrList(table1, table2, column, wheresql,BhrConstant.Season_Date[0][1],BhrConstant.T_STU_BEHTIME_TYPE_BREAKFAST,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list2 = queryNowBhrList(table1, table2, column, wheresql,BhrConstant.Season_Date[1][1],BhrConstant.T_STU_BEHTIME_TYPE_BREAKFAST,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list3 = queryNowBhrList(table1, table2, column, wheresql1,BhrConstant.Season_Date[0][1],BhrConstant.T_STU_BEHTIME_TYPE_LUNCH,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list4 = queryNowBhrList(table1, table2, column, wheresql1,BhrConstant.Season_Date[1][1],BhrConstant.T_STU_BEHTIME_TYPE_LUNCH,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list5 = queryNowBhrList(table1, table2, column, wheresql2,BhrConstant.Season_Date[0][1],BhrConstant.T_STU_BEHTIME_TYPE_DINNER,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list6 = queryNowBhrList(table1, table2, column, wheresql2,BhrConstant.Season_Date[1][1],BhrConstant.T_STU_BEHTIME_TYPE_DINNER,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		all.addAll(list1);all.addAll(list2);all.addAll(list3);
		all.addAll(list4);all.addAll(list5);all.addAll(list6);
		JobResultBean result = doStuTimeBehavior(all,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doBeforeEat(){
		String jobName = "t_stu_behavior_time历史早中晚餐人数最多时间";
		List<TStuBehaviorTime> all = new ArrayList<>();
		String wheresql = " and substr(t.time_,12,2) <'"+Constant.Meal_Time_Group[0][2]+"' "+JYLX_SQL;
		String wheresql1 = " and substr(t.time_,12,2) <'"+Constant.Meal_Time_Group[1][2]+"' and substr(t.time_,12,2) >='"+Constant.Meal_Time_Group[1][1]+"' "+JYLX_SQL;
		String wheresql2 = " and substr(t.time_,12,2) >='"+Constant.Meal_Time_Group[2][1]+"' "+JYLX_SQL;
		String table1 = "T_CARD_PAY",table2 ="T_CARD",column ="t.card_id";
		List<TStuBehaviorTime> list1 = queryBhrList(table1, table2, column, wheresql,BhrConstant.Season_Date[0][1],BhrConstant.T_STU_BEHTIME_TYPE_BREAKFAST,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list2 = queryBhrList(table1, table2, column, wheresql,BhrConstant.Season_Date[1][1],BhrConstant.T_STU_BEHTIME_TYPE_BREAKFAST,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list3 = queryBhrList(table1, table2, column, wheresql1,BhrConstant.Season_Date[0][1],BhrConstant.T_STU_BEHTIME_TYPE_LUNCH,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list4 = queryBhrList(table1, table2, column, wheresql1,BhrConstant.Season_Date[1][1],BhrConstant.T_STU_BEHTIME_TYPE_LUNCH,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list5 = queryBhrList(table1, table2, column, wheresql2,BhrConstant.Season_Date[0][1],BhrConstant.T_STU_BEHTIME_TYPE_DINNER,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list6 = queryBhrList(table1, table2, column, wheresql2,BhrConstant.Season_Date[1][1],BhrConstant.T_STU_BEHTIME_TYPE_DINNER,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		all.addAll(list1);all.addAll(list2);all.addAll(list3);
		all.addAll(list4);all.addAll(list5);all.addAll(list6);
		JobResultBean result = doStuTimeBehavior(all,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doNowBookRke(){
		String jobName = "t_stu_behavior_time当前进出图书馆人数最多时间";
		List<TStuBehaviorTime> all = new ArrayList<>();
		String wheresql = " and substr(t.time_,12,2) <'"+BhrConstant.AMPM_TIME+"'";
		String wheresql1 = " and substr(t.time_,12,2) >='"+BhrConstant.AMPM_TIME+"'";
		String table1 = "T_BOOK_RKE",table2 ="T_BOOK_READER",column ="t.BOOK_READER_ID";
		List<TStuBehaviorTime> list1 = queryNowBhrList(table1, table2, column, wheresql,BhrConstant.Season_Date[0][1],BhrConstant.AM_BOOK_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list2 = queryNowBhrList(table1, table2, column, wheresql,BhrConstant.Season_Date[1][1],BhrConstant.AM_BOOK_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list3 = queryNowBhrList(table1, table2, column, wheresql1,BhrConstant.Season_Date[0][1],BhrConstant.PM_BOOK_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list4 = queryNowBhrList(table1, table2, column, wheresql1,BhrConstant.Season_Date[1][1],BhrConstant.PM_BOOK_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		all.addAll(list1);all.addAll(list2);all.addAll(list3);all.addAll(list4);
		JobResultBean result = doStuTimeBehavior(all,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doBeforeBookRke(){
		String jobName = "t_stu_behavior_time历史进出图书馆人数最多时间";
		List<TStuBehaviorTime> all = new ArrayList<>();
		String wheresql = " and substr(t.time_,12,2) <'"+BhrConstant.AMPM_TIME+"'";
		String wheresql1 = " and substr(t.time_,12,2) >='"+BhrConstant.AMPM_TIME+"'";
		String table1 = "T_BOOK_RKE",table2 ="T_BOOK_READER",column ="t.BOOK_READER_ID";
		List<TStuBehaviorTime> list1 = queryBhrList(table1, table2, column, wheresql,BhrConstant.Season_Date[0][1],BhrConstant.AM_BOOK_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list2 = queryBhrList(table1, table2, column, wheresql,BhrConstant.Season_Date[1][1],BhrConstant.AM_BOOK_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list3 = queryBhrList(table1, table2, column, wheresql1,BhrConstant.Season_Date[0][1],BhrConstant.PM_BOOK_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list4 = queryBhrList(table1, table2, column, wheresql1,BhrConstant.Season_Date[1][1],BhrConstant.PM_BOOK_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		all.addAll(list1);all.addAll(list2);all.addAll(list3);all.addAll(list4);
		JobResultBean result = doStuTimeBehavior(all,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doNowDormRke(){
		String jobName = "t_stu_behavior_time当前出入宿舍人数最多时间";
		List<TStuBehaviorTime> all = new ArrayList<>();
		String wheresql = " a.first_dormrke < a.breakfast_ ";
		String wheresql1 = " a.last_dormrke > a.dinner_";
		String str = "",join = " a.first_dormrke",join1 =" a.last_dormrke";
		List<TStuBehaviorTime> list1 = queryNowBhrList(str, str, join, wheresql,BhrConstant.Season_Date[0][1],BhrConstant.OUT_DORM_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list2 = queryNowBhrList(str,str, join, wheresql,BhrConstant.Season_Date[1][1],BhrConstant.OUT_DORM_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list3 = queryNowBhrList(str, str,join1, wheresql1,BhrConstant.Season_Date[0][1],BhrConstant.IN_DORM_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list4 = queryNowBhrList(str, str,join1, wheresql1,BhrConstant.Season_Date[1][1],BhrConstant.IN_DORM_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		all.addAll(list1);all.addAll(list2);all.addAll(list3);all.addAll(list4);
		JobResultBean result = doStuTimeBehavior(all,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doBeforeDormRke(){
		String jobName = "t_stu_behavior_time历史出入宿舍人数最多时间";
		List<TStuBehaviorTime> all = new ArrayList<>();
		String wheresql = " a.first_dormrke < a.breakfast_ and a.first_dormrke<'"+Constant.Meal_Time_Group[0][2]+"'";
		String wheresql1 = " a.last_dormrke > a.dinner_ and a.last_dormrke>'"+Constant.Meal_Time_Group[2][1]+"'";
		String str = "",join = " a.first_dormrke",join1 =" a.last_dormrke";
		List<TStuBehaviorTime> list1 = queryBhrList(str, str, join, wheresql,BhrConstant.Season_Date[0][1],BhrConstant.OUT_DORM_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list2 = queryBhrList(str,str, join, wheresql,BhrConstant.Season_Date[1][1],BhrConstant.OUT_DORM_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list3 = queryBhrList(str, str,join1, wheresql1,BhrConstant.Season_Date[0][1],BhrConstant.IN_DORM_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		List<TStuBehaviorTime> list4 = queryBhrList(str, str,join1, wheresql1,BhrConstant.Season_Date[1][1],BhrConstant.IN_DORM_RKE,BhrConstant.T_STU_BEHTIME_STUTYPE_SMART);
		all.addAll(list1);all.addAll(list2);all.addAll(list3);all.addAll(list4);
		JobResultBean result = doStuTimeBehavior(all,jobName);
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
	private List<TStuBehaviorTime> queryBhrList(String table1,String table2,String join,String wheresql,String season,String type,String stutype){
		String xq ="select t.code_ as id,t.name_ as mc from t_code t where code_type ='TERM_CODE' order by t.code_ desc";
//		int year = businessDao.queryMinSchoolYear(Constant.TABLE_T_STU_SCORE, "SCHOOL_YEAR");
		List<Map<String, Object>> temp = getYearAndTerm();
		int  x = MapUtils.getIntValue(temp.get(0), "year");
		String  term = MapUtils.getString(temp.get(0), "term");
		int year =x-3;
		List<TStuBehaviorTime> list1 = new ArrayList<>();
		int nowyear = x;
		List<Map<String,Object>> xqlist = baseDao.queryListInLowerKey(xq);
		for(int j=nowyear;j>year-1;j--){
		
			for(int i=0;i<xqlist.size();i++){
			String xqid = MapUtils.getString(xqlist.get(i), "id");
			if(!xqid.equals(term)||j!=nowyear){
			String schoolyear=String.valueOf(j)+"-"+String.valueOf(j+1);
			 List<TStuBehaviorTime> list = new ArrayList<>();
			 if (type.equals(BhrConstant.OUT_DORM_RKE)||type.equals(BhrConstant.IN_DORM_RKE)){
				  list =  getDormRkeList(table1, table2, join, wheresql, schoolyear, xqid, season, type, stutype) ;
			  }else{
				  list =  getPayAndBookList(table1, table2, join, wheresql, schoolyear, xqid, season, type, stutype) ;
			  }
			  
			 list1.addAll(list);
			}
			}
		}
	
		return list1;
	}
	private List<TStuBehaviorTime> queryNowBhrList(String table1,String table2,String join,String wheresql,String season,String type,String stutype){
		  String day = DateUtils.getNowDate();	
		  List<TStuBehaviorTime> list1 = new ArrayList<>();
		  List<Map<String, Object>> temp = getYearAndTerm();
		  String  schoolYear = MapUtils.getString(temp.get(0), "schoolyear");
		  String  term = MapUtils.getString(temp.get(0), "term");
		  if (type.equals(BhrConstant.OUT_DORM_RKE)||type.equals(BhrConstant.IN_DORM_RKE)){
			  list1 =  getDormRkeList(table1, table2, join, wheresql, schoolYear, term, season, type, stutype) ;
		  }else{
			  list1 =  getPayAndBookList(table1, table2, join, wheresql, schoolYear, term, season, type, stutype) ;
		  }
		  
		return list1;
	}
	private List<Map<String, Object>> getYearAndTerm(){
		List<Map<String, Object>> list = businessService.queryBzdmScoreXnXq();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map : list){
			Map<String, Object> temp = new HashMap<String, Object>();
			String id = MapUtils.getString(map, "id");
			String[] ary = id.split(",");
			temp.put("schoolyear", ary[0]);
			temp.put("term", ary[1]);
			temp.put("year", Integer.parseInt(ary[0].substring(0,4)));
			result.add(temp);	
 		}
		return result;
	}
	private JobResultBean doStuTimeBehavior(List<TStuBehaviorTime> list,String jobName){
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
	private List<TStuBehaviorTime> getPayAndBookList(String table1,String table2,String join,String wheresql,String schoolYear,String termCode,String season,String type,String stutype){
		String start = "", end = "";
		Map<String,Object> map = getStartEnd(schoolYear, termCode, season);
		if(map==null){
        	return new ArrayList<TStuBehaviorTime>();
        }else{
        	start = MapUtils.getString(map, "start");
        	end = MapUtils.getString(map, "end");
        }
		String str  = " select substr(t.time_,12,4)||'0' as mc,count(*) as value from "+table1+" t  inner join " +table2 +" a on "+join
				+ " = a.no_ inner join t_stu_score_gpa gpa on a.people_id = gpa.stu_id where gpa.gpa > "+samrtDao.queryXbGpa()+" and gpa.school_year = '"+schoolYear+"'"
				+ " and gpa.term_code = '"+termCode+"' and gpa.gpa_code = '"+Constant.SCORE_GPA_BASE_CODE+"' and t.time_ >= '"+start+"' and t.time_ < '"+end+"' "+ wheresql +" group by substr(t.time_,12,4)||'0'";
		String sql = " select a.mc as value_,'"+schoolYear+"' as school_year, '"+termCode+"' as term_code, "
				+ " '"+type+"' as type_,'"+stutype+"' as stu_type,'"+season+"' as season"
				+ "  from ("+str+") a where a.value in (select max(b.value) from ("+str+")b) order by a.mc";
		List<TStuBehaviorTime> List = baseDao.queryForListBean(sql, TStuBehaviorTime.class);
		List<TStuBehaviorTime> result = new ArrayList<>();
		if(!List.isEmpty()){
			deleteList(schoolYear,termCode,season,type,stutype);
			if(List.size()>1){
					result.add(List.get(0));
			}else{
			    result.addAll(List);
			}
		}
		return result;
	}
	private List<TStuBehaviorTime> getDormRkeList(String table1,String table2,String join,String wheresql,String schoolYear,String termCode,String season,String type,String stutype){
		String start = "", end = "";
		Map<String,Object> map = getStartEnd(schoolYear, termCode, season);
		if(map==null){
        	return new ArrayList<TStuBehaviorTime>();
        }else{
        	start = MapUtils.getString(map, "start");
        	end = MapUtils.getString(map, "end");
        }
		String str  = "select "+join+" as mc,count(*) as value from t_stu_behavior_daily a inner join t_stu_score_gpa b  on a.stu_id = b.stu_id "
				+ " where "+wheresql+" and b.gpa>"+samrtDao.queryXbGpa()+" and a.date_ >'"+start+"'"
				+ " and a.date_< '"+end+"' and b.gpa_code = '"+Constant.SCORE_GPA_BASE_CODE+"' and b.term_code = '"+termCode+"' and b.school_year = '"+schoolYear+"' group by "+join+"";
		String sql = " select a.mc as value_,'"+schoolYear+"' as school_year, '"+termCode+"' as term_code, "
				+ " '"+type+"' as type_,'"+stutype+"' as stu_type,'"+season+"' as season"
				+ "  from ("+str+") a where a.value in (select max(b.value) from ("+str+")b) order by a.mc ";
		List<TStuBehaviorTime> List = baseDao.queryForListBean(sql, TStuBehaviorTime.class);
			deleteList(schoolYear,termCode,season,type,stutype);
			List<TStuBehaviorTime> result = new ArrayList<>();
			if(!List.isEmpty()){
				deleteList(schoolYear,termCode,season,type,stutype);
				if(List.size()>1){
						result.add(List.get(0));
				}else{
				    result.addAll(List);
				}
			}
		return result;
	}
	private void deleteList(String schoolYear,String termCode,String season,String type,String stutype){
		String oldSql = " T_STU_BEHAVIOR_TIME t where t.school_year = '"+schoolYear+"' and t.term_code = '"+termCode+"' and "
				+ "t.type_= '"+type+"' and t.stu_type='"+stutype+"' and t.season='"+season+"'";
		int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
		if(oldCount > 0){
			baseDao.delete("delete from "+oldSql);
			info("已删除"+schoolYear+"学年"+termCode+"学期学生行为数据");
		}
	}
	private Map<String,Object> getStartEnd(String schoolYear,String termCode,String season){
		String[] time = EduUtils.getTimeQuantum(schoolYear,termCode);
		int year = Integer.parseInt(schoolYear.substring(0,4));String start = time[0]; String end = time[1];
		int a = Integer.parseInt(time[0].substring(5,7));
		int jj = Integer.parseInt(BhrConstant.Season_Date[0][0].substring(0,2)),
			jj1 = Integer.parseInt(BhrConstant.Season_Date[1][0].substring(0,2));
		if(season.equals(BhrConstant.Season_Date[0][1])){
			if(a>=jj1){
				return null;
			}else if(a>jj&&a<jj1){
				 end = String.valueOf(year)+"-"+BhrConstant.Season_Date[1][0];
			}else if( a<=jj){
				start = String.valueOf(year+1)+"-"+BhrConstant.Season_Date[0][0]; 
			}
		}else if(season.equals(BhrConstant.Season_Date[1][1])){
			if(a>=jj1){
			}else if(a>jj&&a<jj1){
				start = String.valueOf(year)+"-"+BhrConstant.Season_Date[1][0]; 
			}else if(a<=jj){
				end = String.valueOf(year+1)+"-"+BhrConstant.Season_Date[0][0];
			}
		}
		Map<String,Object> map= new HashMap<>();
		map.put("start",start);
		map.put("end", end);
		return map;
	}
}
