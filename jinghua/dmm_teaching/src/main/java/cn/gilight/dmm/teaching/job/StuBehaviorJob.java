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
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.entity.TStuBehavior;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("stuBehaviorJob")
public class StuBehaviorJob {
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
	public JobResultBean doBehaviorEarly(){
		String jobName = "所有学生每学年学期平均早起次数";
			List<Map<String,Object>> yearlist = queryYearList();
			String type =Constant.BEHAVIOR_EARLY_AVG;
			JobResultBean result = doBehavior(yearlist,jobName,type);
		return result;
	}
	@Transactional
	public JobResultBean doBehaviorBreakfast(){
		String jobName = "所有学生每学年学期平均早餐次数";
			List<Map<String,Object>> yearlist = queryYearList();
			String type =Constant.BEHAVIOR_BREAKFAST_AVG;
			JobResultBean result = doBehavior(yearlist,jobName,type);
		return result;
	}
	@Transactional
	public JobResultBean doBehaviorBorrowBook(){
		String jobName = "所有学生每学年学期平均借书数";
			List<Map<String,Object>> yearlist = queryYearList();
			String type =Constant.BEHAVIOR_BORROW_AVG;
			JobResultBean result = doBehavior(yearlist,jobName,type);
		return result;
	}
	@Transactional
	public JobResultBean doBehaviorBookRke(){
		String jobName = "所有学生每学年学期平均进出图书馆次数";
			List<Map<String,Object>> yearlist = queryYearList();
			String type =Constant.BEHAVIOR_BOOK_RKE;
			JobResultBean result = doBehavior(yearlist,jobName,type);
		return result;
	}
	@Transactional
	public JobResultBean doNowBehaviorEarly(){
		String jobName = "当前学年学期所有学生平均早起次数";
			List<Map<String,Object>> yearlist = queryNowYearList();
			String type =Constant.BEHAVIOR_EARLY_AVG;
			JobResultBean result = doBehavior(yearlist,jobName,type);
		return result;
	}
	@Transactional
	public JobResultBean doNowBehaviorBreakfast(){
		String jobName = "当前学年学期所有学生平均早餐次数";
			List<Map<String,Object>> yearlist = queryNowYearList();
			String type =Constant.BEHAVIOR_BREAKFAST_AVG;
			JobResultBean result = doBehavior(yearlist,jobName,type);
		return result;
	}
	@Transactional
	public JobResultBean doNowBehaviorBorrowBook(){
		String jobName = "当前学年学期所有学生平均借书数";
			List<Map<String,Object>> yearlist = queryNowYearList();
			String type =Constant.BEHAVIOR_BORROW_AVG;
			JobResultBean result = doBehavior(yearlist,jobName,type);
		return result;
	}
	@Transactional
	public JobResultBean doNowBehaviorBookRke(){
		String jobName = "当前学年学期所有学生平均进出图书馆次数";
			List<Map<String,Object>> yearlist = queryNowYearList();
			String type =Constant.BEHAVIOR_BOOK_RKE;
			JobResultBean result = doBehavior(yearlist,jobName,type);
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
	private String queryEarlyUp(String stuSql,String start_date,String end_date,int dayLen){
	    String hour_start = Constant.Early_Time[0],hour_end_else=Constant.Early_Time[1];
	    String sql = "select a.no_ as stu_id, round((count(*)/"+dayLen+")*30,2) as value,'"+Constant.BEHAVIOR_EARLY_AVG+"' as type from ("
	    		+ " select ship.no_,substr(t.time_, 0, 10) day from T_DORM_RKE t, T_DORM_PROOF card,(" +stuSql+ ") ship"
				+ " where t.dorm_proof_id=card.no_ and card.people_id=ship.no_ and t.time_ >= '" +start_date+ "'"
				+ (end_date==null ? "" : " and t.time_ <= '" +end_date+ "'")
				+ " and substr(t.time_,12,2) >= '" +hour_start+ "' and substr(t.time_,12,2) < '" +hour_end_else+ "' group by ship.no_,substr(t.time_, 0, 10)) a group by a.no_";
		return sql;
  }
	private String queryAvgBreakfastCount(String stuSql,String start_date,String end_date,int dayLen){
		String breakfast_start =Constant.Meal_Time_Group[0][1],breakfase_end_else=Constant.Meal_Time_Group[0][2];
		String sql = "select a.no_ as stu_id, round((count(*)/"+dayLen+")*30,2) as value,'"+Constant.BEHAVIOR_BREAKFAST_AVG+"' as type from ("
				+ " select ship.no_, substr(t.time_, 0, 10) day from T_CARD_PAY t, t_card card, (" +stuSql+ ") ship"
				+ " where t.card_id=card.no_ and card.people_id=ship.no_ and t.time_ >= '" +start_date+ "'"
				+ (end_date==null ? "" : " and t.time_ <= '" +end_date+ "'")
				+ " and substr(t.time_,12,2) >= '" +breakfast_start+ "' and substr(t.time_,12,2) < '" +breakfase_end_else+ "'"
				+ " group by ship.no_,substr(t.time_, 0, 10)) a group by a.no_";
		return sql;
	}
	
	private String queryBookCount(String stuSql,String start_date,String end_date,int dayLen){
		String sql = "select ship.no_ as stu_id,round((count(*)/"+dayLen+")*30,2) as value,'"+Constant.BEHAVIOR_BORROW_AVG+"' as type from T_BOOK_BORROW bw, (" +stuSql+ ") ship"
				+ " where bw.book_reader_id=ship.no_ and bw.borrow_time >= '" +start_date+ "'"
				+ (end_date==null ? "" : " and bw.borrow_time <= '" +end_date+ "'")+" group by ship.no_";
		return sql; 
	}
	
	private String queryBookRke(String stuSql,String start_date,String end_date,int dayLen){
		String sql = "select ship.no_ as stu_id,round((count(*)/"+dayLen+")*30,2) as value,'"+Constant.BEHAVIOR_BOOK_RKE+"' as type from T_BOOK_RKE rke, (" +stuSql+ ") ship"
				+ " where rke.book_reader_id=ship.no_ and rke.time_ >= '" +start_date+ "'"
				+ (end_date==null ? "" : " and rke.time_ <= '" +end_date+ "'")+" group by ship.no_";
		return sql; 
	}
	private List<Map<String,Object>> queryYearList(){
		String xq ="select t.code_ as id,t.name_ as mc from t_code t where code_type ='TERM_CODE' order by t.code_ desc";
		int year = businessDao.queryMinSchoolYear("t_stu", "enroll_grade");
		String day = DateUtils.getNowDate();
		int nowyear = EduUtils.getSchoolYear4();
		List<Map<String,Object>> xqlist = baseDao.queryListInLowerKey(xq);
		List<Map<String,Object>> yearlist = new ArrayList<Map<String,Object>>();
		String[] date = EduUtils.getSchoolYearTerm(day);
		String nowxq = date[1];
		for(int j=nowyear;j>year-1;j--){
		     int xnid = j;
			for(int i=0;i<xqlist.size();i++){
			String xqid = MapUtils.getString(xqlist.get(i), "id");
			Map<String,Object> temp = new HashMap<String,Object>();
			if(!xqid.equals(nowxq)||xnid!=nowyear){
			if( xnid == nowyear && Integer.parseInt(xqid) > Integer.parseInt(nowxq)){
			}else{
				temp.put("xn",xnid);
				temp.put("xq", xqid);
				yearlist.add(temp);
			}
			}
			}
		}
		return yearlist;
	}
	private List<Map<String,Object>> queryNowYearList(){
		  Map<String,Object> map = new HashMap<>();
		  String day = DateUtils.getNowDate();
		  int  xn =EduUtils.getSchoolYear4();
		  String[] date = EduUtils.getSchoolYearTerm(day);
		  map.put("xn", xn);
		  map.put("xq", date[1]);
		  List<Map<String,Object>>  yearlist = new ArrayList<>();
		  yearlist.add(map);
		return yearlist;
	}
 private List<TStuBehavior> queryResult(List<Map<String,Object>> yearlist,String type){
	 String day = DateUtils.getNowDate();List<TStuBehavior> list1 = new ArrayList<>();
		for(Map<String,Object> map :yearlist){
			
			int schoolYear = MapUtils.getIntValue(map, "xn");
			String schoolYear1 = String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1);
			String term = MapUtils.getString(map, "xq");
			baseDao.delete("delete from t_stu_behavior t where t.type_ ='"+type+"' and t.school_year ='"+schoolYear1+"'"
					+ " and t.term_code = '"+term+"'");
			String stuSql = businessDao.getStuSql(schoolYear,PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
			String[] time = EduUtils.getTimeQuantum(schoolYear1,term);
			String start = time[0],end =time[1];
			long endstr = Long.valueOf(end.replaceAll("[-\\s:]",""));
			long nowstr = Long.valueOf(day.replaceAll("[-\\s:]",""));
			if(endstr>nowstr){
				end = day;
			}
			int dayLen = DateUtils.getDayBetween(start, end)+1;
			if (dayLen<1){
				return list1;
			}
			String str= "select t.stu_id,'"+schoolYear1+"' as school_year,'"+term+"' as term_code,t.value as value_,t.type as type_ from (";
			String str1= " )t";String sql = "";
			if (type.equals(Constant.BEHAVIOR_EARLY_AVG)){
			sql =str+queryEarlyUp(stuSql,start,end,dayLen)+str1;
			}else if (type.equals(Constant.BEHAVIOR_BREAKFAST_AVG)){
				sql = str+queryAvgBreakfastCount(stuSql,start,end,dayLen)+str1;	
			}else if (type.equals(Constant.BEHAVIOR_BORROW_AVG)){
				sql = str+queryBookCount(stuSql,start,end,dayLen)+str1;
			}else if(type.equals(Constant.BEHAVIOR_BOOK_RKE)){
				sql= str+queryBookRke(stuSql,start,end,dayLen)+str1;
			}
			List<TStuBehavior> scoreGpaList = baseDao.queryForListBean(sql, TStuBehavior.class);
			list1.addAll(scoreGpaList);
		}
		return list1;
 }
 private JobResultBean doBehavior(List<Map<String,Object>> yearlist,String jobName,String type){
		String day = DateUtils.getNowDate();
		JobResultBean result = new JobResultBean();
		List<TStuBehavior> list1 = new ArrayList<>();
		Long beginTime = System.currentTimeMillis();
		info("历史数据删除成功");
		begin("#"+jobName+"#"+day);
		try{
			list1 = queryResult(yearlist,type);
			if(!list1.isEmpty()){
			   hibernateDao.saveAll(list1);
			}
			info("插入"+jobName+list1.size()+" 条");
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
}
