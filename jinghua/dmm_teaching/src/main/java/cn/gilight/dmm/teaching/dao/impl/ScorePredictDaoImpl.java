package cn.gilight.dmm.teaching.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.teaching.dao.ScorePredictDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.UserUtil;
/**
 * 挂科预测
 * @author lijun
 *
 */
@Repository("scorePredictDao")
public class ScorePredictDaoImpl implements ScorePredictDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	/**
	 * 得到课程
	 */
	@Override
	public List<Map<String, Object>> queryCourseByType(String id, String schoolYear,
			String termCode) {
		   String course_sql=" AND T.COURSE_NATURE_CODE='"+id+"'";
		   if("all".equals(id)){
			   course_sql=""; 
		   }
		   String sql="SELECT DISTINCT T.SCOURE_CODE CODE_,TC.NAME_||'（'||T.COURSETYPEMC||'）' NAME_ FROM ( "
					+ getjcSql()+ " ) T  LEFT JOIN T_COURSE TC ON TC.CODE_=T.SCOURE_CODE  "
					+ " WHERE  T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"' AND TC.NAME_ IS NOT NULL "
					+ course_sql;
		return  baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 得到当前学年辅导员所带班级及人数
	 */
	@Override
	public List<Map<String, Object>> queryGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,String userId,String majorType) {
		List<Map<String,Object>>list =new ArrayList<>();
		Map<String,Object> map =new HashMap<>();
		if(schoolYear.contains(",")){
			schoolYear=schoolYear.split(",")[0];
		}
		//userId="2007052";
		String sql=getjcSql(schoolYear, termCode, edu, userId, majorType);
		//得到辅导员带的班级个数
		String sql1="SELECT COUNT(DISTINCT TCI.CLASS_ID) CLASSSUM FROM ("+sql+") TCI" ;
		map.put("classsum", baseDao.getJdbcTemplate().queryForList(sql1).get(0));
		//得到辅导员所带专业种类
		sql1="SELECT DISTINCT TCDT.NAME_ MAJORMC FROM ("+sql+") TCIL LEFT JOIN T_CLASSES TC ON TC.NO_=TCIL.CLASS_ID LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.CODE_=TC.TEACH_DEPT_ID ";
		map.put("majorsort", baseDao.getJdbcTemplate().queryForList(sql1));
		sql1="SELECT COUNT(DISTINCT TS.NO_) STUSUM FROM ("+sql+") TCIL LEFT JOIN T_STU TS ON TS.CLASS_ID=TCIL.CLASS_ID";
		map.put("stusum", baseDao.getJdbcTemplate().queryForList(sql1).get(0));
		list.add(map);
		return list;
	}
	/**
	 * 成绩预测概况
	 */
	@Override
	public List<Map<String, Object>> queryScoreInfo(List<String> deptList,List<AdvancedParam> advancedList
			,String schoolYear, String termCode, String edu,String courseType,String courseid,String majorType) {
//		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String userId=UserUtil.getCasLoginName();//userId="2007052";//得到辅导员工号
		String choosecoures_sql="";
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		if(schoolYear.contains(",")){
			schoolYear=schoolYear.split(",")[0];
		}
		if("all".equals(courseid)){
			if("all".equals(courseType)){
			}else{
				choosecoures_sql="AND T.COURSE_NATURE_CODE='"+courseType+"'";
			}
		}else{
			choosecoures_sql="AND T.COURSE_ID='"+courseid+"'";
		}
		Map<String, Object> map =new HashMap<String, Object>();
		String fdy_class_choose_sql="SELECT DISTINCT T.CLASS_ID FROM ( "+getjcSql(schoolYear, termCode, edu, userId, majorType)+") T";
		String sql1="SELECT * FROM ("+getDetailSql()+") T WHERE T.NAME_='"+majorType+"' AND T.CLASS_ID IN ("+fdy_class_choose_sql+") AND T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"'"+choosecoures_sql;
		String t_sql=getjcSql(schoolYear, termCode, edu, userId, majorType);
		//班级名称 课程总数
		   String coursesum_sql=" SELECT T3.CLASSMC CLASSMC,COUNT(DISTINCT T3.COURSE_ID) COURSESUM FROM ( "
				   +sql1
				   + " ) T3 GROUP BY T3.CLASSMC";
		   //班级名称 班级学生数
		   String stusum_sql= " SELECT TCIL.CLASSMC,COUNT(TCIL.NO_) STUSUM FROM ( " + t_sql+ " ) TCIL  GROUP BY TCIL.CLASSMC ";
		   String scoresort_sql="SELECT  "
				+ " TSSPB.CLASSMC, "
				+ " SUM(case when TSSPB.PREDICT_SCORE>=90 then 1 else 0 end) CL01, "
				+ " SUM(case when TSSPB.PREDICT_SCORE between 80 and 89 then 1 else 0 end) CL02, "
				+ " SUM(case when TSSPB.PREDICT_SCORE between 70 and 79  then 1 else 0 end) CL03, "
				+ " SUM(case when TSSPB.PREDICT_SCORE between 60 and 69  then 1 else 0 end) CL04, "
				+ " SUM(case when TSSPB.PREDICT_SCORE<60 then 1 else 0 end) CL05 "
				+ " FROM ( "
				+ sql1
				+ " ) TSSPB  "
				+ " GROUP BY TSSPB.CLASSMC";
		   String count_sql="SELECT T.CLASSMC, COUNT(*) COUNT_ FROM ("+getDetailSql()+") T WHERE T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"' AND T.NAME_='"+majorType+"' GROUP BY T.CLASSMC";
		   String sql=" SELECT T1.CLASSMC CL01,T1.COURSESUM CL03,T2.STUSUM CL02, "
					+ " T3.CL01 CL04,(CASE WHEN T4.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T3.CL01/T4.COUNT_,2)*100) END) CL05, "
					+ " T3.CL02 CL06,(CASE WHEN T4.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T3.CL02/T4.COUNT_,2)*100) END) CL07, "
					+ " T3.CL03 CL08,(CASE WHEN T4.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T3.CL03/T4.COUNT_,2)*100) END) CL09, "
					+ " T3.CL04 CL10,(CASE WHEN T4.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T3.CL04/T4.COUNT_,2)*100) END) CL11, "
					+ " T3.CL05 CL12,(CASE WHEN T4.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T3.CL05/T4.COUNT_,2)*100) END) CL13 "
					+ " FROM ("+coursesum_sql+") T1,("+stusum_sql+") T2,("+scoresort_sql+") T3,("+count_sql+") T4 "
					+ " WHERE T1.CLASSMC=T2.CLASSMC AND T2.CLASSMC=T3.CLASSMC AND T4.CLASSMC=T3.CLASSMC";
		   map.put("scoreinfo", baseDao.getJdbcTemplate().queryForList(sql));
		   String z_sql="SELECT "+getInfoSql(sql1, "SELECT COUNT(DISTINCT T.STU_ID) CL04,COUNT(DISTINCT T.COURSE_ID) CL05 ,");
		   map.put("count", baseDao.getJdbcTemplate().queryForList(z_sql));
		   list.add(map);
		return list;
	}
	/**
	 * 成绩分布
	 */
	@Override
	public List<Map<String, Object>> queryScorefb(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu, String majorType) {
		if(schoolYear.contains(",")){
			schoolYear=schoolYear.split(",")[0];
		}
//		  String sql=getInfoSql(schoolYear, termCode, edu, majorType);
		String userId=UserUtil.getCasLoginName();//userId="2007052";//得到辅导员工号
		String fdy_class_choose_sql="SELECT DISTINCT T.CLASS_ID FROM ( "+getjcSql(schoolYear, termCode, edu, userId, majorType)+") T";
		String sql="SELECT * FROM (T_STU_PREDICT_SCORE_RESULT_FDY) T WHERE T.NAME_='"+majorType+"' AND T.CLASS_ID IN ("+fdy_class_choose_sql+") AND T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"'";
		 //得到平均成绩和准确率
		  String avg_sql="SELECT T.COURSE_ID,T.COURSEMC,T.COURSETYPEMC,COUNT( T.STU_ID) COUNT_,ROUND(SUM(T.PREDICT_SCORE)/(COUNT( T.STU_ID)),1) AVG_, ROUND(SUM(T.ACCURACY)/(COUNT( T.STU_ID)),1) ZQL FROM ("+sql+") T GROUP BY T.COURSE_ID,T.COURSEMC,T.COURSETYPEMC";
		 //各个分数段所占的人次
		  String rang_sql="SELECT  "
					+ " T.COURSEMC,T.NAME_, "
					+ " SUM(case when T.PREDICT_SCORE>=90 then 1 else 0 end) CL01, "
					+ " SUM(case when T.PREDICT_SCORE between 80 and 89 then 1 else 0 end) CL02, "
					+ " SUM(case when T.PREDICT_SCORE between 70 and 79  then 1 else 0 end) CL03, "
					+ " SUM(case when T.PREDICT_SCORE between 60 and 69  then 1 else 0 end) CL04, "
					+ " SUM(case when T.PREDICT_SCORE<60 then 1 else 0 end) CL05 "
					+ " FROM ( "+sql+") T GROUP BY T.COURSEMC,T.NAME_";
		  String z_sql=" SELECT T1.COURSEMC CL01,T1.COURSETYPEMC CL02,T1.AVG_ CL03,T1.ZQL CL04, "
					+ " T2.CL01 CL05,(CASE WHEN T1.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T2.CL01/T1.COUNT_,2)*100) END) CL06, "
					+ " T2.CL02 CL07,(CASE WHEN T1.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T2.CL02/T1.COUNT_,2)*100) END) CL08, "
					+ " T2.CL03 CL09,(CASE WHEN T1.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T2.CL03/T1.COUNT_,2)*100) END) CL10, "
					+ " T2.CL04 CL11,(CASE WHEN T1.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T2.CL04/T1.COUNT_,2)*100) END) CL12, "
					+ " T2.CL05 CL13,(CASE WHEN T1.COUNT_=0 THEN '0' ELSE TO_CHAR(ROUND(T2.CL05/T1.COUNT_,2)*100) END) CL14 "
					+ " FROM ("+avg_sql+") T1,("+rang_sql+") T2 "
					+ " WHERE T1.COURSEMC=T2.COURSEMC ";
		  return baseDao.getJdbcTemplate().queryForList(z_sql);
	}
	/**
	 * 成绩分布(按课程性质)
	 */
	@Override
	public List<Map<String, Object>> queryScorefbByNature(
			List<String> deptList, List<AdvancedParam> advancedList,
			String schoolYear, String termCode, String edu, String majorType) {
		if(schoolYear.contains(",")){
			schoolYear=schoolYear.split(",")[0];
		}
		String userId=UserUtil.getCasLoginName();//userId="2007052";//得到辅导员工号
		String fdy_class_choose_sql="SELECT DISTINCT T.CLASS_ID FROM ( "+getjcSql(schoolYear, termCode, edu, userId, majorType)+") T";
		String fdy_classes_sql="SELECT * FROM (T_STU_PREDICT_SCORE_RESULT_FDY) T WHERE T.NAME_='"+majorType+"' AND T.CLASS_ID IN ("+fdy_class_choose_sql+") AND T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"'";//"+getDetailstuSql(deptList, advancedList)+"

		String sql="SELECT  "
				+ " T.COURSETYPEMC NAME_, "
				+ " SUM(case when T.PREDICT_SCORE>=90 then 1 else 0 end) CL01, "
				+ " SUM(case when T.PREDICT_SCORE between 80 and 89 then 1 else 0 end) CL02, "
				+ " SUM(case when T.PREDICT_SCORE between 70 and 79  then 1 else 0 end) CL03, "
				+ " SUM(case when T.PREDICT_SCORE between 60 and 69  then 1 else 0 end) CL04, "
				+ " SUM(case when T.PREDICT_SCORE between 40 and 59  then 1 else 0 end) CL05, "
				+ " SUM(case when T.PREDICT_SCORE<=39 then 1 else 0 end) CL06 "
				+ " FROM ("+fdy_classes_sql+") T"
//				+ " FROM ( "+getInfoSql(schoolYear, termCode, edu, majorType)+") T "
				+ " GROUP BY T.COURSETYPEMC";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/******************************************************************************************/
	/**
	 * 基础数据表（辅导员工号，班级名称，学年，学期，专业名称，学生学号）
	 * @param schoolYear
	 * @param termCode
	 * @param edu
	 * @param userId
	 * @param majorType
	 * @return
	 */
	public String getjcSql(String schoolYear, String termCode, String edu,String userId,String majorType){
		String major_sql=" AND TCDT.NAME_='"+majorType+"'";
		if("all".equals(majorType)){
			major_sql="";
		}
		String sql=" SELECT T.*,TCL.NAME_ CLASSMC FROM ("
				+ " SELECT T.*,TS.NO_ FROM ( "
				+ " SELECT TCI.*,TCDT.NAME_  FROM T_CLASSES_INSTRUCTOR TCI "
			    + " LEFT JOIN T_CLASSES TCL ON TCL.NO_=TCI.CLASS_ID LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.CODE_=TCL.TEACH_DEPT_ID"
				+ " WHERE TCI.TEA_ID='"+userId+"' AND TCI.SCHOOL_YEAR='"+schoolYear+"' AND TCI.TERM_CODE='"+termCode+"' "
				+ major_sql
				+ " ) T , T_STU TS WHERE TS.CLASS_ID=T.CLASS_ID"
				+ " ) T LEFT JOIN T_CLASSES TCL ON TCL.NO_=T.CLASS_ID";
		return  sql;
	}
	/**
	 * 得到课程ID表（学年，学期，课程ID，课程性质，专业名称，课程名称，课程属性名称）
	 * @return
	 */
	public String getjcSql(){
		String sql= " SELECT T.*,TCO.NAME_ COURSEMC,TC.NAME_ COURSETYPEMC FROM ("
				    + " SELECT TSCC.SCHOOL_YEAR,TSCC.TERM_CODE,TSCC.SCOURE_CODE,TSCC.COURSE_NATURE_CODE,TS.CLASS_ID FROM T_STU_COURSE_CHOOSE TSCC LEFT JOIN T_STU TS ON TS.NO_=TSCC.STU_ID "
					+ " UNION ALL "
					+ " SELECT TCAP.SCHOOL_YEAR,TCAP.TERM_CODE,TCAP.COURSE_CODE,TCAP.COURSE_NATURE_CODE,TCAP.CLASS_ID FROM T_COURSE_ARRANGEMENT_PLAN TCAP "
					+ "  ) T,T_COURSE TCO,T_CODE TC WHERE T.SCOURE_CODE=TCO.CODE_ AND TC.CODE_=T.COURSE_NATURE_CODE AND TC.CODE_TYPE='COURSE_NATURE_CODE'";
		return sql;
	}
	/**
	 * 辅导员所需信息总表（学年，学期，教师工号，班级id，课程id，课程属性
	 * @param schoolYear
	 * @param termCode
	 * @param edu
	 * @param userId
	 * @param majorType
	 * @param courseid
	 * @param courseType
	 * @return
	 */
	public String getInfoSql(String schoolYear, String termCode, String edu,String userId,String majorType,String courseid,String courseType ){
		String choosecoures_sql="";
		if("all".equals(courseid)){
			if("all".equals(courseType)){
			}else{
				choosecoures_sql="WHERE T2.COURSE_NATURE_CODE='"+courseType+"'";
			}
		}else{
			choosecoures_sql="WHERE T2.SCOURE_CODE='"+courseid+"'";
		}
		String sql="SELECT T.*,TCL.NAME_ CLASSMC FROM ( "
				+ " SELECT T1.SCHOOL_YEAR,T1.TERM_CODE,T1.TEA_ID,T1.CLASS_ID,T2.SCOURE_CODE,T2.COURSE_NATURE_CODE FROM ( "
				+ getjcSql(schoolYear, termCode, edu, userId, majorType)+ " ) T1 LEFT JOIN ( "+ getjcSql()+ " ) T2 ON T1.CLASS_ID=T2.CLASS_ID "
				+ choosecoures_sql
				+ "  ) T LEFT JOIN T_CLASSES TCL ON TCL.NO_=T.CLASS_ID";
		return sql;
	}
	/**
	 * 辅导员所需信息表（学年，学期，教师工号，班级id，课程id，课程属性
	 * @param schoolYear
	 * @param termCode
	 * @param edu
	 * @param userId
	 * @param majorType
	 * @param courseid
	 * @param courseType
	 * @return
	 */
	public String getInfoSql(String schoolYear, String termCode, String edu,String majorType){
		String userId=UserUtil.getCasLoginName();//userId="2007052";//得到辅导员工号
		//课程ID,课程属性ID,班级ID,课程属性名称,学生学号
		 String t_sql=" SELECT T2.*,TS.NO_ FROM ("
				    + " SELECT T1.*,TC.NAME_ FROM ( "
					+ " SELECT T.SCOURE_CODE,T.COURSE_NATURE_CODE,T.CLASS_ID  FROM ("
					+ getInfoSql(schoolYear, termCode, edu, userId, majorType, "all", "all")
					+ " ) T  GROUP BY T.SCOURE_CODE,T.COURSE_NATURE_CODE,T.CLASS_ID"
					+ " ) T1 LEFT JOIN T_CODE TC ON TC.CODE_=T1.COURSE_NATURE_CODE AND TC.CODE_TYPE='COURSE_NATURE_CODE'"
					+ " ) T2 LEFT JOIN T_STU TS ON TS.CLASS_ID=T2.CLASS_ID";
		  t_sql="SELECT T.*,T1.COURSEMC FROM ("+t_sql+") T ,("+getjcSql()+") T1 WHERE T.SCOURE_CODE=T1.SCOURE_CODE";
			 //课程ID,课程名称，课程属性名称
		  String coursesort_sql="SELECT T.SCOURE_CODE, T.COURSEMC,T.NAME_ FROM ("+t_sql+") T GROUP BY T.COURSEMC,T.NAME_,T.SCOURE_CODE";
		  String sql="SELECT * FROM ("+coursesort_sql+") T ,T_STU_SCORE_PREDICT_BEH TSSRB WHERE TSSRB.COURSE_ID=T.SCOURE_CODE ";
		  return sql;
	}
	/**
	 * 得到下钻的详细信息
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	@Override
	public String getDetailstuSql(List<String> deptList,List<AdvancedParam> advancedList){
		//学号 学年 学期 预测成绩 年级 课程ID 预测日期 逃课次数 准确率 班级ID 专业ID 专业名称 课程名称 课程性质ID  课程性质名称
		String sql="SELECT DISTINCT T1.*,T2.COURSEMC,T2.COURSE_NATURE_CODE,T2.COURSETYPEMC  FROM ("
				+ " SELECT TSSP.*,TS.CLASS_ID,TS.MAJOR_ID,TCDT.NAME_ "
				+ " FROM T_STU_SCORE_PREDICT_BEH TSSP "
				+ " LEFT JOIN T_STU TS ON TS.NO_=TSSP.STU_ID "
				+ " LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.CODE_=TS.MAJOR_ID"
				+ " ) T1,("+getjcSql()+") T2 WHERE T1.COURSE_ID=T2.SCOURE_CODE";
		return sql;
	}
	/**
	 * 得到详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	@Override
	public String getlookStuSql(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType){
		String userId=UserUtil.getCasLoginName();//userId="2007052";//得到辅导员工号
		String fdy_class_choose_sql="SELECT DISTINCT T.CLASS_ID FROM ( "+getjcSql(schoolYear, termCode, edu, userId, majorType)+") T";
		String fdy_classes_sql="SELECT * FROM (T_STU_PREDICT_SCORE_RESULT_FDY) T WHERE T.NAME_='"+majorType+"' AND T.CLASS_ID IN ("+fdy_class_choose_sql+") AND T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"'";//"+getDetailstuSql(deptList, advancedList)+"
		String other_classes_sql="SELECT * FROM ((T_STU_PREDICT_SCORE_RESULT_FDY)) T WHERE T.NAME_='"+majorType+"' AND T.CLASS_ID NOT IN ("+fdy_class_choose_sql+")AND T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"'";
		fdy_classes_sql="SELECT '我所带的班级' CL00,"+getInfoSql(fdy_classes_sql,"SELECT COUNT(DISTINCT T.STU_ID) CL04,COUNT(DISTINCT T.COURSE_ID) CL05,");
		other_classes_sql="SELECT '当年其他班级' CL00,"+getInfoSql(other_classes_sql,"SELECT COUNT(DISTINCT T.STU_ID) CL04,COUNT(DISTINCT T.COURSE_ID) CL05,");
		String sql=fdy_classes_sql+" union all "+other_classes_sql;
		return sql;
	}
	/**
	 * 去对比详细信息
	 */
	@Override
	public String getContrastStuSql(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
		    String termCode, String edu, String majorType, String coursemc,String coursetypemc) {
		if("null".equals(coursetypemc)) coursetypemc="";
		String userId=UserUtil.getCasLoginName();//userId="2007052";//得到辅导员工号
		String fdy_class_choose_sql="SELECT DISTINCT T.CLASS_ID FROM ( "+getjcSql(schoolYear, termCode, edu, userId, majorType)+") T";
		String fdy_classes_sql="SELECT * FROM (T_STU_PREDICT_SCORE_RESULT_FDY) T WHERE T.CLASS_ID IN ("+fdy_class_choose_sql+") AND T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"' AND T.COURSEMC='"+coursemc+"'";
		String other_classes_sql="SELECT * FROM (T_STU_PREDICT_SCORE_RESULT_FDY) T WHERE T.NAME_='"+majorType+"' AND T.CLASS_ID NOT IN ("+fdy_class_choose_sql+")AND T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"'";
		String other_major_classes_sql="SELECT * FROM (T_STU_PREDICT_SCORE_RESULT_FDY) T WHERE T.NAME_ !='"+majorType+"' AND T.CLASS_ID NOT IN ("+fdy_class_choose_sql+")AND T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"'";
		fdy_classes_sql="SELECT '我带的班级' CL00,'"+coursetypemc+"' CL01,"+getInfoSql(fdy_classes_sql,"SELECT ROUND(SUM(T.PREDICT_SCORE)/(COUNT( T.STU_ID)),1) CL04, ROUND(SUM(T.ACCURACY)/(COUNT( T.STU_ID)),1) CL05,");
		other_classes_sql="SELECT '当年同专业其他班级' CL00,'"+coursetypemc+"' CL01,"+getInfoSql(other_classes_sql,"SELECT ROUND(SUM(T.PREDICT_SCORE)/(COUNT( T.STU_ID)),1) CL04, ROUND(SUM(T.ACCURACY)/(COUNT( T.STU_ID)),1) CL05,");
		other_major_classes_sql="SELECT '当年不同专业班级' CL00,'"+coursetypemc+"' CL01,"+getInfoSql(other_major_classes_sql,"SELECT ROUND(SUM(T.PREDICT_SCORE)/(COUNT( T.STU_ID)),1) CL04, ROUND(SUM(T.ACCURACY)/(COUNT( T.STU_ID)),1) CL05,");
		String sql=fdy_classes_sql+" union all "+other_classes_sql+" union all "+other_major_classes_sql;
		return sql;
	}
	/**
	 * 成绩预测准确率详情
	 */
	@Override
	public String getAccuracyStuSql(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			 String majorType, String coursemc,
			String coursetypemc) {
		String major_sql="SELECT * FROM (T_STU_PREDICT_SCORE_RESULT_FDY) T WHERE  T.SCHOOL_YEAR='"+schoolYear+"' AND T.NAME_ ='"+majorType+"'AND T.COURSEMC='"+coursemc+"'";
		String other_major_sql="SELECT * FROM (T_STU_PREDICT_SCORE_RESULT_FDY) T WHERE  T.SCHOOL_YEAR='"+schoolYear+"' AND T.NAME_ !='"+majorType+"'AND T.COURSEMC='"+coursemc+"'";
		major_sql="SELECT '"+schoolYear+"学年' CL00, '本专业' CL01 , '"+coursetypemc+"' CL02,(CASE WHEN COUNT( T.STU_ID)=0 THEN '0' ELSE (TO_CHAR(ROUND(SUM(T.ACCURACY)/(COUNT( T.STU_ID)),1))||'%') END) CL03 FROM ("+major_sql+") T";
		other_major_sql="SELECT '"+schoolYear+"学年' CL00, '其他专业' CL01 , '"+coursetypemc+"' CL02,(CASE WHEN COUNT( T.STU_ID)=0 THEN '0' ELSE (TO_CHAR(ROUND(SUM(T.ACCURACY)/(COUNT( T.STU_ID)),1))||'%') END) CL03 FROM ("+other_major_sql+") T";
		String sql=major_sql+" union all "+other_major_sql;
		return sql;
	}
	public String getInfoSql(String chooseSql,String cxSql){
		String  sql=cxSql
				   + " SUM(case when T.PREDICT_SCORE>=90 then 1 else 0 end) CL06, "
		           + " SUM(case when T.PREDICT_SCORE between 80 and 89 then 1 else 0 end) CL07, "
		           + " SUM(case when T.PREDICT_SCORE between 70 and 79  then 1 else 0 end) CL08,"
		           + " SUM(case when T.PREDICT_SCORE between 60 and 69  then 1 else 0 end) CL09,"
				   + " SUM(case when T.PREDICT_SCORE<60 then 1 else 0 end) CL10 ,COUNT(T.STU_ID) CL11"
		           + " FROM ("+chooseSql
		           + " ) T ";
		     sql= " T.CL04 CL02 ,T.CL05 CL03,"
				+ " T.CL06 CL04,(CASE WHEN T.CL11=0 THEN '0' ELSE TO_CHAR(ROUND(T.CL06/T.CL11,3)*100) END) CL05,"
				+ " T.CL07 CL06,(CASE WHEN T.CL11=0 THEN '0' ELSE TO_CHAR(ROUND(T.CL07/T.CL11,3)*100) END) CL07,"
				+ " T.CL08 CL08,(CASE WHEN T.CL11=0 THEN '0' ELSE TO_CHAR(ROUND(T.CL08/T.CL11,3)*100) END) CL09,"
				+ " T.CL09 CL10,(CASE WHEN T.CL11=0 THEN '0' ELSE TO_CHAR(ROUND(T.CL09/T.CL11,3)*100) END) CL11,"
				+ " T.CL10 CL12,(CASE WHEN T.CL11=0 THEN '0' ELSE TO_CHAR(ROUND(T.CL10/T.CL11,3)*100) END) CL13"
				+ " FROM ("+sql+") T";
		return sql;
	}
	@Override
	public String getDetailstuSql(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu, String majorType, String coursemc,
			String coursetypemc, String choose,String classes) {
		       String course_sql=" AND T.COURSEMC='"+coursemc+"' ",
		    		   class_sql=" AND (T.CLASS_ID='"+classes+"' OR T.CLASSMC='"+classes+"')",
		    		   coursetype=" AND T.COURSETYPEMC='"+coursetypemc+"' ",
		    		   score_sql=" AND (T.PREDICT_SCORE BETWEEN "+choose+" AND ("+choose+"+9)) ";
		       String userId=UserUtil.getCasLoginName();//userId="2007052";//得到辅导员工号
				String fdy_class_choose_sql="SELECT DISTINCT T.CLASS_ID FROM ( "+getjcSql(schoolYear, termCode, edu, userId, majorType)+") T";
		       if("all".equals(coursemc)){course_sql=""; }
		       if("all".equals(coursetypemc)){coursetype=""; }
		       if("all".equals(choose)){score_sql=""; }
		       if(choose.contains("<60")){score_sql="AND T.PREDICT_SCORE <= 59 "; }
		       if(choose.contains("40")){score_sql="AND (T.PREDICT_SCORE <= 59 AND T.PREDICT_SCORE >= 40) "; }
		       if("0".equals(choose)){score_sql="AND T.PREDICT_SCORE <= 39 "; }
		       if("all".equals(classes)){class_sql=" AND T.CLASS_ID IN ("+fdy_class_choose_sql+") "; }
		       String sql=" SELECT T.STU_ID CL01, T.STUXM CL02,T.CLASSMC CL03,"
		    		    + " T.SCHOOL_YEAR CL04,T.TERM_CODE CL05,T.COURSEMC CL06,"
		    		    + " T.COURSETYPEMC CL07,T.PREDICT_SCORE CL08 "
		       		    + " FROM (  "
					    + " SELECT TPSRF.*,TS.NAME_ STUXM FROM ("+getDetailSql()+") TPSRF "
					    + " LEFT JOIN T_STU TS ON TS.NO_=TPSRF.STU_ID "
					    + " ) T WHERE T.SCHOOL_YEAR='"+schoolYear+"' AND T.TERM_CODE='"+termCode+"' "
					    + " AND T.NAME_='"+majorType+"' "
					    + course_sql+coursetype+score_sql+class_sql;
		return sql;
	}
/***************************得到信息表********************************************/
	public String getDetailSql(){
		String sql=" SELECT TPSR.STU_ID,TPSR.SCHOOL_YEAR,TPSR.TERM_CODE, "
					+ " TPSR.PREDICT_SCORE,TPSR.GRADE_ID,TPSR.COURSE_ID,TPSR.DATE_, "
					+ " TPSR.TK_COUNT,TPSR.ACCURACY,TPSR.MAJOR_ID,TPSR.NAME_,TPSR.COURSEMC, "
					+ " TPSR.COURSE_NATURE_CODE,TPSR.COURSETYPEMC,TPSR.CLASS_ID,TCL.NAME_ CLASSMC "
					+ " FROM T_STU_PREDICT_SCORE_RESULT_FDY TPSR  LEFT JOIN T_CLASSES TCL ON TCL.NO_=TPSR.CLASS_ID";
		return sql;
	}
	
	
}
