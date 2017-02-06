package cn.gilight.dmm.teaching.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.entity.TMajorFailClass;
import cn.gilight.dmm.teaching.entity.TMajorScore;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;

/**
 * 专业开设Job
 * 
 * @author xuebl
 * @date 2016年11月3日 下午3:07:59
 */
@Service("majorStatusJob")
public class MajorStatusJob {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private HibernateDao hibernateDao;

	/**
	 * 专业成绩、排名
	 */
	@Transactional
	public JobResultBean doMajorScore(){
		String jobName = "#初始化专业成绩及排名#";
		Long beginTime = System.currentTimeMillis();
		
		JobResultBean result = new JobResultBean();
		
		/**
		 * 处理各个学年的成绩数据与排名（分专业，分课程属性）
		 * 处理学年的逻辑：查成绩表中存在的学年（从小到大），如果目标学年已经初始化且当前学年（2016-2017）大于目标学年（2015-2016）则不再初始化
		 * 分专业取平均成绩，计算排名，各课程属性平均成绩
		 */
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select t.school_year,count(0) from t_stu_score t"
				+ " group by t.school_year order by t.school_year");
		List<String> schoolYearList = new ArrayList<>();
		if(!list.isEmpty()){
			for(Map<String, Object> map : list){
				String schoolYear = MapUtils.getString(map, "school_year");
				if(isNeedInitMajorScore(schoolYear)){
					schoolYearList.add(schoolYear);
					JobResultBean re = doMajorScoreBySchoolYear(schoolYear);
					if(!re.getIsTrue()){
						result.setIsTrue(false);
						result.setMsg(re.getMsg());
						break;
					}
				}
			}
		}
 		String info = jobName+StringUtils.join(schoolYearList, "，")+"。结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
 		result.setMsg((result.getMsg()==null ? "" : result.getMsg())+info);
		return result;
	}
	/**
	 * 判断专业成绩排名是否需要初始化
	 * @param schoolYear
	 * @return boolean
	 */
	private boolean isNeedInitMajorScore(String schoolYear){
		boolean isNeed = true;
		if(schoolYear==null || schoolYear.length()<4)
			isNeed = false;
		else {
			int year = EduUtils.getSchoolYear4(schoolYear);
			int year_this = EduUtils.getSchoolYear4();
			if(baseDao.queryForInt("select count(0)  from t_major_score t where t.school_year = '"+schoolYear+"'")>0 && year < year_this){
				isNeed = false;
			}
		}
		return isNeed;
	}
	/**
	 * 处理每一年的成绩与排名
	 * @param schoolYear
	 * @return JobResultBean
	 */
	private JobResultBean doMajorScoreBySchoolYear(String schoolYear){
		String jobName = "#保存专业成绩#-"+schoolYear;
		JobResultBean result = new JobResultBean();
		// delete
		baseDao.delete("delete from t_major_score t where t.school_year = '"+schoolYear+"'");
		String CODE_ALL = "ALL";
		// 
		List<TMajorScore> saveList = new ArrayList<>();
		List<String> deptList = PmsUtils.getPmsAll();
		List<Map<String, Object>> zyList = businessDao.queryZyListStu(deptList, businessDao.getStuSql(EduUtils.getSchoolYear4(schoolYear), deptList, null));
		for(Map<String, Object> zyMap : zyList){
			String majorId = MapUtils.getString(zyMap, "id");
			TMajorScore t = new TMajorScore();
			t.setMajorId(majorId);
			t.setSchool_year(schoolYear);
			t.setCourse_attr_code(CODE_ALL);
			// 总的
			String stuSql = businessDao.getStuSql(PmsUtils.getDeptListByDeptMap(zyMap), null),
					scoreSql = businessDao.getStuScoreSql(stuSql, schoolYear, null),
					avgScoreSql = "select score from ("+businessDao.getStuScoreAvgSql(scoreSql)+")";
			List<Double> avgScoreList = baseDao.queryForListDouble(avgScoreSql);
			// 平均成绩
			t.setScore_avg(MathUtils.getAvgValueExcludeNull(avgScoreList, 1));
			saveList.add(t);
			// 不同属性课程数据
			List<Map<String, Object>> natureList = businessService.queryBzdmCourseNature();
			for(Map<String, Object> natureMap : natureList){
				TMajorScore t2 = new TMajorScore();
				t2.setMajorId(majorId);;
				t2.setSchool_year(schoolYear);
				String code_ = MapUtils.getString(natureMap, "id");
				String scoreSql_ = "select score from ("+scoreSql+") where COURSE_NATURE_CODE = '"+code_+"'";
				t2.setScore_avg(MathUtils.getAvgValueExcludeNull(baseDao.queryForListDouble(scoreSql_), 1));
				t2.setCourse_attr_code(code_);
				saveList.add(t2);
			}
		}
		// 分组、排序，一个List中多个List（平均的，各属性的），每一个List排序
		List<List<TMajorScore>> ordersList = new ArrayList<>();
		Map<String, List<TMajorScore>> ordersMap = new HashMap<>();
		List<TMajorScore> oneOrderList = null;
		for(TMajorScore t : saveList){
			String code = t.getCourse_attr_code();
			if(ordersMap.containsKey(code)){
				oneOrderList = ordersMap.get(code);
			}else{
				oneOrderList = new ArrayList<>();
				ordersList.add(oneOrderList);
				ordersMap.put(code, oneOrderList);
			}
			oneOrderList.add(t);
		}
		Collections.reverse(ordersList);
		for(List<TMajorScore> list : ordersList){
			// sort and setRanking
			sort(list, false);
			for(int i=0,len=list.size(); i<len; i++){
				list.get(i).setRanking(i+1);
			}
			if(!list.isEmpty()){
				try {
					hibernateDao.saveAll(list);
					result.setIsTrue(true);
					result.setMsg("#"+jobName+"#成功");
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (Exception e)  {
					String info = "#"+jobName+"#数据保存出错,"+e.getStackTrace();
					result.setIsTrue(false);
					result.setMsg(info);
				}
			}
		}
		return result;
	}
	
	/**
	 * 排序
	 * @param list
	 * @param isAsc void
	 */
	private void sort(List<TMajorScore> list, final boolean isAsc){
		Collections.sort(list, new Comparator<TMajorScore>(){
			Double d1 = null, d2 = null;
			@Override
			public int compare(TMajorScore o1, TMajorScore o2) {
				int k = 0;
				d1 = o1.getScore_avg();
				d2 = o2.getScore_avg();
				if(d1!=null && (d1>d2 || d2==null)){ k = 1; }
				else if(d1==null || (d1<d2 && d2!=null)){ k = -1; }
				if(!isAsc){ k = -k; }
				return k;
			}
		});
	}
	
	
	/**
	 * 专业挂科率、排名
	 */
	@Transactional
	public JobResultBean doMajorFailClass(){
		String jobName = "#初始化专业挂科率及排名#";
		Long beginTime = System.currentTimeMillis();
		
		JobResultBean result = new JobResultBean();
		
		/**
		 * 处理各个学年的成绩数据与排名（分专业，分课程属性）
		 * 处理学年的逻辑：查成绩历史表中第一次考试成绩存在的学年（从小到大），如果目标学年已经初始化且当前学年（2016-2017）大于目标学年（2015-2016）则不再初始化
		 * 分专业取平均挂科率，计算排名，各课程属性平均挂科率
		 */
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select t.school_year,count(0) from t_stu_score_history t"
				+ " where cs='1' group by t.school_year order by t.school_year");
		List<String> schoolYearList = new ArrayList<>();
		if(!list.isEmpty()){
			for(Map<String, Object> map : list){
				String schoolYear = MapUtils.getString(map, "school_year");
				if(isNeedInitMajorFailClass(schoolYear)){
					schoolYearList.add(schoolYear);
					JobResultBean re = doMajorFailClassBySchoolYear(schoolYear);
					if(!re.getIsTrue()){
						result.setIsTrue(false);
						result.setMsg(re.getMsg());
						break;
					}
				}
			}
		}
 		String info = jobName+StringUtils.join(schoolYearList, "，")+"。结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
 		result.setMsg((result.getMsg()==null ? "" : result.getMsg())+info);
		return result;
	}
	
	/**
	 * 判断专业挂科率排名是否需要初始化
	 * @param schoolYear
	 * @return boolean
	 */
	private boolean isNeedInitMajorFailClass(String schoolYear){
		boolean isNeed = true;
		if(schoolYear==null || schoolYear.length()<4)
			isNeed = false;
		else {
			int year = EduUtils.getSchoolYear4(schoolYear);
			int year_this = EduUtils.getSchoolYear4();
			if(baseDao.queryForInt("select count(0) from t_major_fail_class t where t.school_year = '"+schoolYear+"'")>0 && year < year_this){
				isNeed = false;
			}
		}
		return true; // TODO
	}
	private JobResultBean doMajorFailClassBySchoolYear(String schoolYear){
		String jobName = "#保存专业挂科率及排名#-"+schoolYear;
		JobResultBean result = new JobResultBean();
		// delete
		baseDao.delete("delete from t_major_fail_class t where t.school_year = '"+schoolYear+"'");
		String CODE_ALL = "ALL";
		// 
		List<TMajorFailClass> saveList = new ArrayList<>();
		List<String> deptList = PmsUtils.getPmsAll();
		List<Map<String, Object>> zyList = businessDao.queryZyListStu(deptList, businessDao.getStuSql(EduUtils.getSchoolYear4(schoolYear), deptList, null));
		for(Map<String, Object> zyMap : zyList){
			String majorId = MapUtils.getString(zyMap, "id");
			TMajorFailClass t = new TMajorFailClass();
			t.setMajorId(majorId);
			t.setSchool_year(schoolYear);
			t.setCourse_attr_code(CODE_ALL);
			// 总的
			String stuSql = businessDao.getStuSql(PmsUtils.getDeptListByDeptMap(zyMap), null),
					// 挂科人数、考试人数
				scoreSql = "select t.stu_id, t.course_code, t.credit,"
					+ " case when t.hierarchical_score_code is not null then hier.centesimal_score else t.centesimal_score end score,"
					+ " t.COURSE_ATTR_CODE, t.COURSE_NATURE_CODE from "
					+ " (select t.stu_id, t.course_code, ch.credit, t.centesimal_score, t.hierarchical_score_code, ch.COURSE_ATTR_CODE, ch.COURSE_NATURE_CODE"
					+ " from T_STU_SCORE_HISTORY t, T_STU_COURSE_CHOOSE ch, ("+stuSql+") stu"
					+ " where t.stu_id=ch.stu_id and t.school_year=ch.school_year and t.term_code=ch.term_code and t.course_code=ch.scoure_code"
					+ " and t.stu_id=stu.no_ and ch.credit is not null"
					+ " union select t.stu_id, t.course_code, plan.credit, t.centesimal_score, t.hierarchical_score_code, plan.COURSE_ATTR_CODE, plan.COURSE_NATURE_CODE"
					+ " from T_STU_SCORE_HISTORY t, T_COURSE_ARRANGEMENT_PLAN plan, ("+stuSql+") stu"
					+ " where t.school_year = plan.school_year and t.term_code = plan.term_code and t.course_code = plan.course_code"
					+ " and t.stu_id = stu.no_ and plan.class_id = stu.class_id and plan.credit is not null)t"
					+ " left join t_code_score_hierarchy hier on t.hierarchical_score_code = hier.code_ where"
					+ " t.centesimal_score is not null or hier.centesimal_score is not null",
				failSql = "select distinct(stu_id) from ("+scoreSql+") where score < " +Globals.FAIL_SCORE_LINE;
			// 平均成绩
			t.setFail_class_rate(MathUtils.getPercentNum(baseDao.queryForCount(failSql), baseDao.queryForCount(stuSql)));
			saveList.add(t);
			// 不同属性课程数据
			List<Map<String, Object>> natureList = businessService.queryBzdmCourseNature();
			for(Map<String, Object> natureMap : natureList){
				TMajorFailClass t2 = new TMajorFailClass();
				t2.setMajorId(majorId);;
				t2.setSchool_year(schoolYear);
				String code_ = MapUtils.getString(natureMap, "id");
				String stuSql_ = "select distinct(stu_id) from ("+scoreSql+") where COURSE_NATURE_CODE = '"+code_+"'";
				String failSql_ = failSql+" and COURSE_NATURE_CODE = '"+code_+"'";
				t2.setFail_class_rate(MathUtils.getPercentNum(baseDao.queryForCount(failSql_), baseDao.queryForCount(stuSql_)));
				t2.setCourse_attr_code(code_);
				saveList.add(t2);
			}
		}
		// 分组、排序，一个List中多个List（平均的，各属性的），每一个List排序
		List<List<TMajorFailClass>> ordersList = new ArrayList<>();
		Map<String, List<TMajorFailClass>> ordersMap = new HashMap<>();
		List<TMajorFailClass> oneOrderList = null;
		for(TMajorFailClass t : saveList){
			String code = t.getCourse_attr_code();
			if(ordersMap.containsKey(code)){
				oneOrderList = ordersMap.get(code);
			}else{
				oneOrderList = new ArrayList<>();
				ordersList.add(oneOrderList);
				ordersMap.put(code, oneOrderList);
			}
			oneOrderList.add(t);
		}
		Collections.reverse(ordersList);
		for(List<TMajorFailClass> list : ordersList){
			// sort and setRanking
			sortMajorFailClass(list, false);
			for(int i=0,len=list.size(); i<len; i++){
				list.get(i).setRanking(i+1);
			}
			if(!list.isEmpty()){
				try {
					hibernateDao.saveAll(list);
					result.setIsTrue(true);
					result.setMsg("#"+jobName+"#成功");
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (Exception e)  {
					String info = "#"+jobName+"#数据保存出错,"+e.getStackTrace();
					result.setIsTrue(false);
					result.setMsg(info);
				}
			}
		}
		return result;
	}

	/**
	 * 排序
	 * @param list
	 * @param isAsc void
	 */
	private void sortMajorFailClass(List<TMajorFailClass> list, final boolean isAsc){
		Collections.sort(list, new Comparator<TMajorFailClass>(){
			Double d1 = null, d2 = null;
			@Override
			public int compare(TMajorFailClass o1, TMajorFailClass o2) {
				int k = 0;
				d1 = o1.getFail_class_rate();
				d2 = o2.getFail_class_rate();
				if(d1!=null && (d1>d2 || d2==null)){ k = 1; }
				else if(d1==null || (d1<d2 && d2!=null)){ k = -1; }
				if(!isAsc){ k = -k; }
				return k;
			}
		});
	}
	
}
