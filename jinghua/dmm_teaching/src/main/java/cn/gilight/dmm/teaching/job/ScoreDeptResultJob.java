package cn.gilight.dmm.teaching.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.dao.SmartDao;
import cn.gilight.dmm.teaching.entity.TStuScoreResultDept;
import cn.gilight.dmm.teaching.service.ScoreService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;

@Service("scoreDeptResultJob")
public class ScoreDeptResultJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService ;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private ScoreService scoreService;  
	@Resource
	private SmartDao smartDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	@Transactional
	public JobResultBean doNowDept(){
		String jobName = " t_stu_score_result_dept各组织结构当前学期成绩结果";
		List<TStuScoreResultDept> list1 = new ArrayList<TStuScoreResultDept>(); 
		List<Map<String, Object>> temp = getYearAndTerm();
		if(!temp.isEmpty()){
			temp = temp.subList(0, 1);
		}
		for(Map<String, Object> temp1 : temp){
			String schoolYear = MapUtils.getString(temp1, "schoolyear"),
			       termCode = MapUtils.getString(temp1, "term");
			Integer year = MapUtils.getIntValue(temp1, "year");  
			List<TStuScoreResultDept> list = getList(schoolYear, termCode, year);
		list1.addAll(list);
		}
		JobResultBean result = doResult(list1,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doLsDept(){
		String jobName = " t_stu_score_result_dept各组织结构历史每学期成绩结果";
		List<TStuScoreResultDept> list1 = new ArrayList<TStuScoreResultDept>(); 
		List<Map<String, Object>> temp = getYearAndTerm();
		if(temp.size()>1){
			temp = temp.subList(1, temp.size());
		}
		for(Map<String, Object> temp1 : temp){
			String schoolYear = MapUtils.getString(temp1, "schoolyear"),
			       termCode = MapUtils.getString(temp1, "term");
			Integer year = MapUtils.getIntValue(temp1, "year");  
			List<TStuScoreResultDept> list = getList(schoolYear, termCode, year);
		list1.addAll(list);
		}
		JobResultBean result = doResult(list1,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doNowYearDept(){
		String jobName = " t_stu_score_result_dept各组织结构当前学年成绩结果";
		List<TStuScoreResultDept> list1 = new ArrayList<TStuScoreResultDept>(); 
		List<Map<String, Object>> temp = getYear();
		if(!temp.isEmpty()){
			temp = temp.subList(0, 1);
		}
		for(Map<String, Object> temp1 : temp){
			String schoolYear = MapUtils.getString(temp1, "schoolyear"),
			       termCode = MapUtils.getString(temp1, "term");
			Integer year = MapUtils.getIntValue(temp1, "year");  
			List<TStuScoreResultDept> list = getList(schoolYear, termCode, year);
		list1.addAll(list);
		}
		JobResultBean result = doResult(list1,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doLsYearDept(){
		String jobName = " t_stu_score_result_dept各组织结构历史每学年成绩结果";
		List<TStuScoreResultDept> list1 = new ArrayList<TStuScoreResultDept>(); 
		List<Map<String, Object>> temp = getYear();
		if(temp.size()>1){
			temp = temp.subList(1, temp.size());
		}
		for(Map<String, Object> temp1 : temp){
			String schoolYear = MapUtils.getString(temp1, "schoolyear"),
			       termCode = MapUtils.getString(temp1, "term");
			Integer year = MapUtils.getIntValue(temp1, "year");  
			List<TStuScoreResultDept> list = getList(schoolYear, termCode, year);
		list1.addAll(list);
		}
		JobResultBean result = doResult(list1,jobName);
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
	private JobResultBean doResult(List<TStuScoreResultDept> list,String jobName){
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
	private List<Map<String, Object>> getYear(){
		List<Map<String, Object>> list = businessService.queryBzdmScoreXn();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map : list){
			Map<String, Object> temp = new HashMap<String, Object>();
			String id = MapUtils.getString(map, "id");
			String[] ary = id.split("-");
			temp.put("schoolyear", id);
			temp.put("term", null);
			temp.put("year", Integer.parseInt(ary[0]));
			result.add(temp);	
 		}
		return result;
	}
	private List<TStuScoreResultDept> getList(String schoolYear,String termCode,int year){
		List<String> deptList1 = PmsUtils.getPmsAll();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(),
		                           YX = businessDao.queryYxList(deptList1),
		                           ZY = businessDao.queryZyListStu(deptList1),
		                           BJ = businessDao.queryBjList(deptList1, year);
		     Map<String, Object>   XX = businessDao.querySchoolData(); 
		     XX.put("stusql", businessDao.getStuSql(year, deptList1, new ArrayList<AdvancedParam>()));
		     list.addAll(YX);list.addAll(ZY);list.addAll(BJ);
		     XX.put("dept_id", MapUtils.getString(XX, "id"));
		     String edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		     List<AdvancedParam> advancedParamList = new ArrayList<AdvancedParam>();
		     if(edu != null && !"".equals(edu)){
		    	 AdvancedParam advancedEdu = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu);
		         AdvancedUtil.add(advancedParamList, advancedEdu);
		     }
		for(Map<String,Object> map : list){
//			String id = MapUtils.getString(map, "id");
//			Integer level = MapUtils.getInteger(map, "level_");
			List<String> deptList = PmsUtils.getDeptListByDeptMap(map); // 系统配置班级level_=5,班级level_=5并不影响权限组合权限
			 /*if( level == PmsUtils.PMS_BJ_LEVEL){
				 deptList = PmsUtils.getDeptListByClassIds(id);
			 }*/
			String stusql = businessDao.getStuSql(year, deptList, advancedParamList);
			map.put("stusql", stusql);
			map.put("dept_id", MapUtils.getString(map, "id"));
		}
		    list.add(XX);
		    List<TStuScoreResultDept> list1 = getGridListParamAndValues(list, schoolYear, termCode, new ArrayList<AdvancedParam>());
		    return list1;
	}
	/** 计算 院系/专业/班级/学科 绩点列表数据 */
	@SuppressWarnings("unchecked")
	private List<TStuScoreResultDept> getGridListParamAndValues(List<Map<String, Object>> list, String schoolYear, String termCode,
			 List<AdvancedParam> businessAdvancedList) {
		List<TStuScoreResultDept> result = new ArrayList<TStuScoreResultDept>();
		String stuSqlKey = "stusql", stuSql = "", scoreSql = "", betterSql = "", 
				failSql = "", rebuildSql = "",gpaSql = "",smartSql ="";
		List<Double> gpaList = new ArrayList<Double>(),
		           scoreList = new ArrayList<Double>();
		for(Map<String, Object> map : list){
			stuSql = MapUtils.getString(map, stuSqlKey);
			Map<String, Object> paramMap = scoreService.getParamMapForScoreOrGpa(stuSql, schoolYear, termCode,null,null,businessAdvancedList);
			gpaList   = (List<Double>) paramMap.get("valueList");
			String sql = businessDao.getStuScoreSql(stuSql, schoolYear, termCode);
			scoreList  = baseDao.queryForListDouble("select a.score from (select case when sum(t.credit)= 0 then 0 "
					+ " else sum(t.score * t.credit)/sum(t.credit) end as score,t.stu_id from ("+sql+") t group by t.stu_id) a where a.score is not null");	
			scoreSql   = MapUtils.getString(paramMap, "scoreSql");
			betterSql  = MapUtils.getString(paramMap, "betterSql");
			failSql    = MapUtils.getString(paramMap, "failSql");
			rebuildSql = MapUtils.getString(paramMap, "rebuildSql");
			gpaSql = businessDao.getStuGpaSql(stuSql, schoolYear, termCode, Constant.SCORE_GPA_BASE_CODE);
			smartSql = "select * from ("+gpaSql+") where gpa >= "+smartDao.queryXbGpa()+"";
			TStuScoreResultDept scoreMap = getScoreValueMapByTarget(gpaList, scoreSql, betterSql, failSql, rebuildSql,scoreList,gpaSql,smartSql);
			scoreMap.setSchool_year(schoolYear);
			scoreMap.setTerm_code(termCode);
			scoreMap.setDept_id(MapUtils.getString(map, "dept_id"));
			String termSql = termCode == null?" is null":" = '"+termCode+"'";
			baseDao.delete("delete from T_STU_SCORE_RESULT_DEPT t where t.dept_id ='"+MapUtils.getString(map, "dept_id")+"' and t.school_year ='"+schoolYear+"'"
					+ " and t.term_code "+termSql+"");
			result.add(scoreMap);
		}
		return result;
	}
	private TStuScoreResultDept getScoreValueMapByTarget(List<Double> valueList, String scoreSql, 
			String betterSql, String failSql, String rebuildSql, List<Double> scoreList,String gpaSql,String smartSql) {
		TStuScoreResultDept	scoreMap = new TStuScoreResultDept();
		Double avgValue = MathUtils.getAvgValue(valueList),
				scoreAvg = MathUtils.getAvgValue(scoreList);
		int count = scoreSql.equals("") || scoreSql == null?0:baseDao.queryForCount(scoreSql),
				gpaCount = gpaSql.equals("") || gpaSql == null ?0:baseDao.queryForCount(gpaSql),
				smartCount = smartSql.equals("") || smartSql == null?0:baseDao.queryForCount(smartSql);
			String beforeSql = "";
			if (gpaSql != null && !gpaSql.equals("")){
				beforeSql = "select gpa from (select gpa,rownum r from (select gpa from ("+gpaSql+") "
						+ " order by gpa))  where r > "+MathUtils.getDivisionResult(gpaCount, 10, 0)+"";
			}
			Double scale = MathUtils.get2Point(MathUtils.getDivisionResult(smartCount, gpaCount, 4)*100);
			List<Double> beforeList =beforeSql==null || beforeSql.equals("")?new ArrayList<Double>():baseDao.queryForListDouble(beforeSql);	
		scoreMap.setGpa_avg(avgValue);
			scoreMap.setGpa_middle(MathUtils.getMiddleValue(valueList));
			scoreMap.setGpa_mode(MathUtils.getModeValue(valueList));
			scoreMap.setGpa_fc(MathUtils.getVariance(valueList));
			scoreMap.setGpa_bzc(MathUtils.getStandardDeviation(valueList));
			scoreMap.setBetter(betterSql.equals("")?0:MathUtils.getPercentNum(baseDao.queryForCount(betterSql), count));
			scoreMap.setFail(failSql.equals("")?0:MathUtils.getPercentNum(baseDao.queryForCount(failSql), count));
			scoreMap.setRebuild(rebuildSql.equals("")?0:MathUtils.getPercentNum(baseDao.queryForCount(rebuildSql), count));
			scoreMap.setScore_avg(scoreAvg);
			scoreMap.setScore_middle(MathUtils.getMiddleValue(scoreList));
			scoreMap.setScore_mode(MathUtils.getModeValue(scoreList));
			scoreMap.setScore_fc(MathUtils.getVariance(scoreList));
			scoreMap.setScore_bzc(MathUtils.getStandardDeviation(scoreList));	
			scoreMap.setGpa_before(MathUtils.getAvgValue(beforeList));
			scoreMap.setSmart_count(smartCount);
			scoreMap.setSmart_scale(scale);
			int underCount = 0;
				for(Double value : valueList){
					if(value < avgValue) underCount++;
				}
			scoreMap.setUnder(MathUtils.getPercentNum(underCount, valueList.size()));
		return scoreMap;
	}
}
