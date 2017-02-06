package cn.gilight.dmm.teaching.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.teaching.dao.FailExaminationDao;
import cn.gilight.framework.base.dao.BaseDao;
/**
 * 挂科补考分析
 * @author lijun
 *
 */
@Repository("failExaminationDao")
public class FailExaminationDaoImpl implements FailExaminationDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	/**
	 * 得到挂科基本信息(挂科人数  挂科率 环比变化 平均挂科数)
	 */
	@Override
	public Map<String, Object> getGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
		// 挂科人数 挂科率  环比变化  平均挂科数
		String sql="SELECT T1.CL02 CL01,(CASE T2.CL01 WHEN 0 THEN 0 ELSE (ROUND(T1.CL02/T2.CL01,3)*100) END) CL02,(CASE T3.CL02 WHEN 0 THEN 0 ELSE (ROUND((T1.CL02-T3.CL02)/T3.CL02,3)*100) END)||'%' CL03,(CASE T1.CL02 WHEN 0 THEN 0 ELSE (ROUND(T1.CL01/T1.CL02,2)) END) CL04 "
					+" FROM(SELECT COUNT(TS.NO_) CL01,COUNT(DISTINCT TS.NO_) CL02 "
					+"       FROM ("+getGkSql(schoolYear,termCode)+") TSSDH "
					+"       LEFT JOIN ("+stuSql+") TS "
					+"       ON TS.NO_ = TSSDH.STU_ID WHERE TSSDH.STU_ID IS NOT NULL) T1,"
					+" (SELECT COUNT(*) CL01 FROM ("+stuSql+")) T2, "
					+" (SELECT COUNT(TS.NO_) CL01,COUNT(DISTINCT TS.NO_) CL02 "
					+"        FROM ("+getGkLastYearSql(schoolYear,termCode)+") TSSDH "
					+"        LEFT JOIN ("+stuSql+") TS "
					+"        ON TS.NO_ = TSSDH.STU_ID WHERE TSSDH.STU_ID IS NOT NULL) T3";
		return baseDao.getJdbcTemplate().queryForList(sql).get(0);
	}
	/**
	 * 挂科分类信息(学生类别 人数 挂科率 变化)
	 */
	@Override
	public List<Map<String, Object>> getGkflInfo(String stuSql,List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear, String termCode,
			String edu) {
		// 学生类别 人数 挂科率 变化
		String sql= "SELECT T.CL01,T.CL02,"
				    +"(CASE T2.CL01 WHEN 0 THEN 0 ELSE (ROUND(T.CL02/T2.CL01,3)*100) END)||'%' CL03,"
				    + " T.CL04 FROM("
					+"SELECT T1.CL01,T1.CL02,(CASE T3.CL02 WHEN 0 THEN 0 ELSE (ROUND((T1.CL02-T3.CL02)/T3.CL02,3)*100) END)||'%' CL04 "
					+" FROM(SELECT TS.EDU_ID CL01, COUNT(DISTINCT TSSDH.STU_ID) CL02, COUNT(TS.NO_) CL03  "
					+"        FROM ("+getGkSql(schoolYear,termCode)+") TSSDH  "
					+"        RIGHT JOIN ("+stuSql+") TS "
					+"        ON TS.NO_ = TSSDH.STU_ID "
					+"        GROUP BY TS.EDU_ID) T1, "
					+" (SELECT TS.EDU_ID CL01, COUNT(DISTINCT TSSDH.STU_ID) CL02, COUNT(TS.NO_) CL03 "
					+"        FROM ("+getGkLastYearSql(schoolYear,termCode)+") TSSDH "
					+"        RIGHT JOIN ("+stuSql+") TS "
					+"       ON TS.NO_ = TSSDH.STU_ID "
					+"        GROUP BY TS.EDU_ID) T3 "
					+" WHERE T1.CL01=T3.CL01) T, "
					+" (SELECT COUNT(*) CL01 FROM ("+stuSql+")) T2";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 各年级挂科分布(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getNjGkInfo(String stuSql,List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear, String termCode,
			String edu,List<Map<String, Object>> listNjname) {
		// 年级名称 人数 人均挂科数
		String sql="SELECT T.* FROM(SELECT T1.GRADE_ CL01,T1.CL01 CL02,COUNT(DISTINCT T1.STU_ID) CL03,(CASE COUNT(DISTINCT T1.STU_ID) WHEN 0 THEN 0 ELSE (ROUND(COUNT(*)/COUNT(DISTINCT T1.STU_ID),2)) END) CL04 "
				   +" FROM(SELECT (TO_CHAR(TO_DATE('"+schoolYear.substring(0,4)+"','yyyy'),'yyyy')+1-TS.ENROLL_GRADE) CL01, "
				   +"              (CASE(TO_CHAR(TO_DATE('"+schoolYear.substring(0,4)+"','yyyy'),'yyyy')+1-TS.ENROLL_GRADE) "
				   +"                      WHEN "+listNjname.get(0).get("id")+" THEN '"+listNjname.get(0).get("mc")+"'  "
				   +"                      WHEN "+listNjname.get(1).get("id")+" THEN '"+listNjname.get(1).get("mc")+"'  "
				   +"                      WHEN "+listNjname.get(2).get("id")+" THEN '"+listNjname.get(2).get("mc")+"'  "
				   +"                      WHEN "+listNjname.get(3).get("id")+" THEN '"+listNjname.get(3).get("mc")+"'  "
//				   +"                      WHEN "+listNjname.get(4).get("id")+" THEN '"+listNjname.get(4).get("mc")+"'  "
				   +"                       END)   GRADE_ ,   "
				   +"                      TSSDH.*   "
				   +"     FROM ("+getGkSql(schoolYear,termCode)+") TSSDH "
				   +"     RIGHT JOIN ("+stuSql+") TS ON TS.NO_ = TSSDH.STU_ID "
				   +"     ) T1 "
				   +"     WHERE T1.STU_ID IS NOT NULL"
				   +"    GROUP BY T1.GRADE_ ,T1.CL01 ORDER BY T1.CL01) T WHERE T.CL01 IS NOT NULL";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 男女生挂科分布(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getXbGkInfo(String stuSql,List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear, String termCode,
			String edu) {
		// 性别  人数 人均挂科数
		String sql="SELECT T1.NAME_ CL01,COUNT(DISTINCT T1.STU_ID) CL02 ,(CASE COUNT(DISTINCT T1.STU_ID) WHEN 0 THEN 0 ELSE (ROUND(COUNT(*)/COUNT(DISTINCT T1.STU_ID),2)) END) CL03"
					+" FROM(SELECT TC.NAME_ ,TSSDH.* "
					+"    FROM ("+getGkSql(schoolYear,termCode)+") TSSDH  "
					+"    RIGHT JOIN ("+stuSql+") TS ON TS.NO_ = TSSDH.STU_ID "
					+"    LEFT JOIN T_CODE TC ON TC.CODE_=TS.SEX_CODE AND TC.CODE_TYPE='SEX_CODE' "
					+ "  ) T1 "
					+"     WHERE T1.STU_ID IS NOT NULL"
					+"    GROUP BY T1.NAME_";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 挂科课程分布--公共课/专业课(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getNatKcGkInfo(String stuSql,List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear, String termCode,
			String edu) {
		// 课程性质名称 人数 人均挂科数
		String sql="SELECT T1.CL01 CL01,COUNT(DISTINCT T1.STU_ID) CL02,(CASE COUNT(DISTINCT T1.STU_ID) WHEN 0 THEN 0 ELSE (ROUND(COUNT(*)/COUNT(DISTINCT T1.STU_ID),2)) END)  CL03 "
					+" FROM(SELECT TSSDH.*,TC.NAME_ CL01 "
					+"    FROM ("+getGkSql(schoolYear,termCode)+") TSSDH  "
					+"    LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSSDH.STU_ID "
					+"    LEFT JOIN ("+courseSql(schoolYear, termCode)+") TCA ON TCA.COURSE_CODE=TSSDH.COURSE_CODE"
					+"    RIGHT JOIN T_CODE TC ON TC.CODE_=TCA.COURSE_NATURE_CODE WHERE TC.CODE_TYPE='COURSE_NATURE_CODE' "
					+ "  ) T1 "
					+" GROUP BY T1.CL01";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 挂科课程分布--必修课/选修课(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getAttrKcGkInfo(String stuSql,List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear, String termCode,
			String edu) {
		// 课程属性名称 人数 人均挂科数
		String sql="SELECT T1.CL01 CL01,COUNT(DISTINCT T1.NO_) CL02,(CASE COUNT(DISTINCT T1.STU_ID) WHEN 0 THEN 0 ELSE (ROUND(COUNT(*)/COUNT(DISTINCT T1.STU_ID),1)) END)  CL03 "
					+" FROM(SELECT TSSDH.*,TC.NAME_ CL01,TS.NO_ "
					+"    FROM ("+getGkSql(schoolYear,termCode)+") TSSDH  "
					+"    LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSSDH.STU_ID "
					+"    LEFT JOIN ("+courseSql(schoolYear, termCode)+") TCA ON TCA.COURSE_CODE=TSSDH.COURSE_CODE"
					+"    RIGHT JOIN T_CODE TC ON TC.CODE_=TCA.COURSE_ATTR_CODE WHERE TC.CODE_TYPE='COURSE_ATTR_CODE' AND TC.ISTRUE=1 "
					+ "   ) T1 "
					+" GROUP BY T1.CL01";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 各机构挂科分布(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getJgGkInfo(String stuSql,List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear, String termCode,
			String dept) {
		String sql="SELECT COUNT(DISTINCT T1.STU_ID) CL01,(CASE COUNT(DISTINCT T1.STU_ID) WHEN 0 THEN 0 ELSE (ROUND(COUNT(*)/COUNT(DISTINCT T1.STU_ID),1)) END)  CL02 "
					+" FROM(SELECT TSSDH.* FROM (SELECT * FROM ("+getGkSql(schoolYear,termCode)+") TSSH LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSSH.STU_ID) TSSDH  "
					+" LEFT JOIN ("+stuSql+") TS ON  TS.NO_=TSSDH.STU_ID "
					+" WHERE TSSDH.DEPT_ID='"+dept+"' OR TSSDH.MAJOR_ID='"+dept+"' OR TSSDH.CLASS_ID='"+dept+"') T1";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 挂科top(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getTopGkInfo(String stuSql,List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear, String termCode,
			String edu, String lx,String gkSort,String turnPage) {
		String sql="";
		if("course".equals(lx)){
			//课程名 挂科率 挂科人数
			sql="SELECT * FROM("
				+" SELECT t2.*,ROWNUM RN FROM"
				+" (SELECT T1.NAME_ CL01,ROUND(COUNT( T1.STU_ID)/(SELECT COUNT(*) CL01 FROM ("+stuSql+")),3)*100  CL02,COUNT( T1.STU_ID) CL03 "
				+" FROM(SELECT TSSDH.*,TCO.NAME_ "
				+" FROM ("+getGkSql(schoolYear,termCode)+") TSSDH  "
				+" RIGHT JOIN ("+stuSql+") TS ON  TS.NO_=TSSDH.STU_ID  "
				+" LEFT JOIN T_COURSE TCO ON TCO.CODE_=TSSDH.COURSE_CODE "
				+ ") T1 "
				+" WHERE T1.NAME_ IS NOT NULL "
				+" GROUP BY T1.NAME_ order by  "+gkSort+") T2 WHERE T2.CL03!=0 ) T3"
				+" WHERE T3.RN BETWEEN "+turnPage+" AND "+(Integer.parseInt(turnPage)+9);
		}else if("tea".equals(lx)){
			//教师名 挂科率 挂科人数
			sql="SELECT * FROM("
				+" SELECT t2.*,ROWNUM RN FROM"
				+" (SELECT  TT.NAME_ CL01,COUNT(DISTINCT TSSDH.STU_ID) CL03 ,(CASE COUNT( TS.NO_) WHEN 0 THEN 0 ELSE (ROUND(COUNT(DISTINCT TSSDH.STU_ID)/COUNT( TS.NO_),2)) END) CL02 "
				+" FROM ("+getGkSql(schoolYear,termCode)+") TSSDH "  
				+" LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSSDH.STU_ID "
				+" RIGHT JOIN T_COURSE_ARRANGEMENT TCA ON TCA.COURSE_ID=TSSDH.COURSE_CODE "
				+" LEFT JOIN T_TEA TT ON TT.TEA_NO=TCA.TEA_ID "
				+" LEFT JOIN T_CLASS_TEACHING_XZB TCT ON TCT.TEACH_CLASS_ID=TCA.TEACHINGCLASS_ID "
				+" WHERE TT.NAME_ IS NOT NULL "
				+" GROUP BY TT.NAME_ ORDER BY "+gkSort+") T2 WHERE T2.CL03!=0 ) T3"
				+" WHERE T3.RN BETWEEN "+turnPage+" AND "+(Integer.parseInt(turnPage)+9);
		}else if("major".equals(lx)){
			//专业名 挂科率 挂科人数
		    sql="SELECT * FROM("
		    	+" SELECT t2.*,ROWNUM RN FROM"
		    	+" (SELECT T1.NAME_ CL01,ROUND(COUNT(DISTINCT T1.STU_ID)/(SELECT COUNT(*) CL02 FROM ("+stuSql+") TS LEFT JOIN T_CODE_DEPT_TEACH TCO ON TCO.ID=TS.MAJOR_ID AND  TCO.LEVEL_TYPE='ZY'  WHERE TCO.NAME_=T1.NAME_),3)*100  CL02,COUNT(T1.STU_ID) CL03 "
				+" FROM(SELECT TSSDH.*,TCO.NAME_ "
				+" FROM ("+getGkSql(schoolYear,termCode)+") TSSDH " 
				+" RIGHT JOIN ("+stuSql+") TS ON  TS.NO_=TSSDH.STU_ID  "
				+" LEFT JOIN T_CODE_DEPT_TEACH TCO ON TCO.ID=TS.MAJOR_ID AND  TCO.LEVEL_TYPE='ZY' " 
				+ ") T1 "
				+" WHERE T1.NAME_ IS NOT NULL "
				+" GROUP BY T1.NAME_ order by  "+gkSort+") T2 WHERE T2.CL03!=0 ) T3"
				+" WHERE T3.RN BETWEEN "+turnPage+" AND "+(Integer.parseInt(turnPage)+9);
		}else if("class".equals(lx)){
			// 班级名称 挂科率  挂科人数
			sql= //" SELECT T5.* FROM ("
				" SELECT * FROM("
				+" SELECT t2.*,ROWNUM RN FROM"
				+" (SELECT T1.NAME_ CL01,T1.CLASS_ID,ROUND(COUNT(DISTINCT T1.STU_ID)/(SELECT COUNT(*) CL01 FROM ("+stuSql+") TS WHERE TS.CLASS_ID=T1.CLASS_ID),3)*100  CL02,COUNT(T1.STU_ID) CL03 "
				+" FROM(SELECT TSSDH.*,TCO.NAME_,TCO.NO_ CLASS_ID  "
				+" FROM ("+getGkSql(schoolYear,termCode)+") TSSDH " 
				+" RIGHT JOIN ("+stuSql+") TS ON  TS.NO_=TSSDH.STU_ID " 
				+" LEFT JOIN T_CLASSES TCO ON TCO.NO_=TS.CLASS_ID "
				+ " ) T1 "
				+" WHERE T1.NAME_ IS NOT NULL "
				+" GROUP BY T1.NAME_,T1.CLASS_ID order by  "+gkSort+") T2  WHERE T2.CL03!=0) T3"
				+" WHERE T3.RN BETWEEN "+turnPage+" AND "+(Integer.parseInt(turnPage)+9);
				//+" ) T5,(SELECT COUTN(TS.NO_) CL01 FROM T_STU TS WHERE TS.CLASS_ID=T5.NAME_) T6";
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 学生挂科top(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getStuTopGkInfo(String stuSql,List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear, String termCode,
			String edu,String gkStuSort,String turnStuPage) {
		// 学生姓名  挂科数 班级
		String sql="SELECT * FROM("
				    +" SELECT t2.*,ROWNUM RN FROM"
				    +" (SELECT T1.CL02 CL01,T1.CL03 CL02,TCL.NAME_ CL03  "
					+" FROM(SELECT TSSDH.STU_ID CL01,TS.NAME_ CL02,COUNT(TSSDH.STU_ID) CL03,TS.CLASS_ID CL04  "
					+" FROM ("+getGkSql(schoolYear,termCode)+") TSSDH  "
					+" LEFT JOIN ("+stuSql+") TS ON  TS.NO_=TSSDH.STU_ID   "
					+" WHERE TS.NAME_ IS NOT NULL"
					+" GROUP BY TSSDH.STU_ID,TS.CLASS_ID,TS.NAME_ ORDER BY CL03 DESC) T1  "
					+" LEFT JOIN T_CLASSES TCL ON TCL.NO_=T1.CL04 ORDER BY "+gkStuSort+") T2  WHERE T2.CL02!=0) T3"
					+" WHERE T3.RN BETWEEN "+turnStuPage+" AND "+(Integer.parseInt(turnStuPage)+9);
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 挂科补考top(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getTopbkInfo(String stuSql,List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear, String termCode,
			String edu, String lx,String bkTopSort,String bkturnPage) {
		String sql="";
		if("course".equals(lx)){//因为课程所对应的人数不全 导致没有数据(主要是班级ID对不上)
			//课程名 人均挂科数
			sql="SELECT * FROM("
				+" SELECT t2.*,ROWNUM RN FROM"
				+" (SELECT TCO.NAME_ CL01,(CASE WHEN T2.CL02 IS NULL THEN 0 WHEN T2.CL02=0 THEN 0 ELSE (ROUND(T1.GKCount/T2.CL02,2)) END) CL02 FROM ( "
				+getBkSql(schoolYear, termCode,"COURSE_CODE")+") T1 LEFT JOIN ("+getBkkcStuSql(stuSql, schoolYear, termCode)+") T2 ON T2.CL01=T1.COURSE_CODE "
				+" LEFT JOIN T_COURSE TCO ON TCO.CODE_=T1.COURSE_CODE "
				+" WHERE TCO.NAME_ IS NOT NULL "
				+" ORDER BY "+bkTopSort+") T2 ) T3"
			    +" WHERE T3.CL02 IS NOT NULL AND T3.RN BETWEEN "+bkturnPage+" AND "+(Integer.parseInt(bkturnPage)+9);
		}else if("tea".equals(lx)){
			//教师名 人均挂科数
			sql="SELECT * FROM("
				+" SELECT t2.*,ROWNUM RN FROM"
				+" (SELECT TT.NAME_ CL01,(CASE WHEN T2.CL02 IS NULL  THEN 0 WHEN T2.CL02 =0  THEN 0 ELSE (ROUND(T1.CL01/T2.CL02,2)) END) CL02 FROM ("
				+" SELECT TCA.TEA_ID ,SUM(T1.GKCount) CL01 FROM T_COURSE_ARRANGEMENT TCA RIGHT JOIN ("
				+getBkSql(schoolYear, termCode,"COURSE_CODE")
				+" ) T1 ON T1.COURSE_CODE= TCA.COURSE_ID  GROUP BY TCA.TEA_ID ) T1 LEFT JOIN ("
				+geteaSql(stuSql, schoolYear, termCode)+") T2 ON T2.CL01=T1.TEA_ID  "
				+" LEFT JOIN T_TEA TT ON TT.TEA_NO=T1.TEA_ID  "
				+" WHERE TT.NAME_ IS NOT NULL "
				+" ORDER BY "+bkTopSort+") T2 ) T3"
				+" WHERE T3.CL02 IS NOT NULL AND T3.RN BETWEEN "+bkturnPage+" AND "+(Integer.parseInt(bkturnPage)+9);
			
		}else if("major".equals(lx)){
			//专业名 人均挂科数
			sql="SELECT * FROM("
				+" SELECT t2.*,ROWNUM RN FROM"
				+" (SELECT TCDT.NAME_ CL01,(CASE WHEN TCDT.COUNT_ IS NULL  THEN 0 WHEN TCDT.COUNT_ =0  THEN 0 ELSE (ROUND(T2.CL01/TCDT.COUNT_,2)) END) CL02 FROM ( "
				+" SELECT TS.MAJOR_ID ,SUM(T1.GKCount) CL01 FROM ( "+getBkSql(schoolYear, termCode,"STU_ID")
				+"   ) T1 LEFT JOIN ("+stuSql+") TS ON TS.NO_=T1.STU_ID  GROUP BY TS.MAJOR_ID   ) T2 "
				+" LEFT JOIN ("+geMajorSql(stuSql, schoolYear, termCode)+") TCDT ON TCDT.MAJOR_ID=T2.MAJOR_ID "
				+" WHERE TCDT.NAME_ IS NOT NULL "
				+" ORDER BY "+bkTopSort+") T2 ) T3"
				+" WHERE T3.CL02 IS NOT NULL AND T3.RN BETWEEN "+bkturnPage+" AND "+(Integer.parseInt(bkturnPage)+9);
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	public String getGkSql(String schoolYear,String termCode){//得到某一学年某一学期的学生挂科记录
			String sql="SELECT T1.STU_ID,T1.SCHOOL_YEAR,T1.TERM_CODE,T1.COURSE_CODE FROM "
					 +"t_failExamination T1 "
	//				 +" (SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH " 
	//				 +" WHERE TSSDH.CS=1 AND (((TSSDH.HIERARCHICAL_SCORE_CODE IS NULL) OR((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND TSSDH.CENTESIMAL_SCORE>0)) "
	//				 +" AND  TSSDH.CENTESIMAL_SCORE<=59 )  "
	//				 +" UNION "
	//				 +" SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH  "
	//				 +" LEFT JOIN t_code_score_hierarchy  TCSH ON TCSH.CODE_=TSSDH.HIERARCHICAL_SCORE_CODE  " 
	//				 +" WHERE TSSDH.CS=1 AND ((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND ((TSSDH.CENTESIMAL_SCORE=0) OR (TSSDH.CENTESIMAL_SCORE IS NULL))) AND TCSH.CENTESIMAL_SCORE<=59) T1"
					 +" WHERE SUBSTR(T1.SCHOOL_YEAR,1,4) IN ("+schoolYear+") AND T1.TERM_CODE IN ("+termCode+") ";
			return sql;
		}
	/********************************************************************************************/
	/********************************以下是公用sql**************************************************/
	public String getGkScore(){//得到所有不及格的记录
		String sql="SELECT *  "              
				+"  FROM(SELECT TSSDH.ID,TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE,TSSDH.CENTESIMAL_SCORE,TSSDH.DATE_   "
				+"  FROM T_STU_SCORE_HISTORY TSSDH  "
				+"  WHERE TSSDH.HIERARCHICAL_SCORE_CODE IS NULL  "            
				+"  UNION  "              
				+"  SELECT TSSDH.ID,TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE,TCSH.CENTESIMAL_SCORE,TSSDH.DATE_  "
				+"  FROM  T_STU_SCORE_HISTORY TSSDH   "   
				+"  LEFT JOIN t_code_score_hierarchy  TCSH ON TCSH.CODE_=TSSDH.HIERARCHICAL_SCORE_CODE  "     
				+"  WHERE TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL ) T1 WHERE T1.CENTESIMAL_SCORE<=59   ";
		return sql;
	}
	public String getEduSql(String stuSql){//得到学生类型分类人数(本专科)
		String sql="SELECT TS.EDU_ID,COUNT(TS.NO_) FROM ("+stuSql+") TS GROUP BY TS.EDU_ID";
		return sql;
	}
	public String getGkLastYearSql(String schoolYear,String termCode){//得到上一个学年某一学期的学生挂科总人数
		if("02".equals(termCode)){
			termCode="01";
			schoolYear=String.valueOf(Integer.parseInt(schoolYear));
		}else if("01".equals(termCode)){
			termCode="02";
//			schoolYear=schoolYear-1;
			schoolYear=String.valueOf(Integer.parseInt(schoolYear)-1);
		}
		if(schoolYear.contains(",")){
			
		}else {
//			schoolYear=String.valueOf(Integer.parseInt(schoolYear)-1);
		}
		String sql="SELECT T1.STU_ID,T1.SCHOOL_YEAR,T1.TERM_CODE,T1.COURSE_CODE FROM "
				+"t_failExamination T1 "
//				 +" (SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH " 
//				 +" WHERE TSSDH.CS=1 AND (((TSSDH.HIERARCHICAL_SCORE_CODE IS NULL) OR((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND TSSDH.CENTESIMAL_SCORE>0)) "
//				 +" AND  TSSDH.CENTESIMAL_SCORE<=59 )  "
//				 +" UNION "
//				 +" SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH  "
//				 +" LEFT JOIN t_code_score_hierarchy  TCSH ON TCSH.CODE_=TSSDH.HIERARCHICAL_SCORE_CODE  " 
//				 +" WHERE TSSDH.CS=1 AND ((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND ((TSSDH.CENTESIMAL_SCORE=0) OR (TSSDH.CENTESIMAL_SCORE IS NULL))) AND TCSH.CENTESIMAL_SCORE<=59) T1"
				 +" WHERE SUBSTR(T1.SCHOOL_YEAR,1,4) IN ("+schoolYear+") AND T1.TERM_CODE IN ("+termCode+") ";
		return sql;
//		String sql="SELECT T1.STU_ID,T1.SCHOOL_YEAR,T1.TERM_CODE,T1.COURSE_CODE,MIN(T1.DATE_) "   
//				  +" FROM("+getGkScore()+") T1 "
//				  +" WHERE SUBSTR(T1.SCHOOL_YEAR,1,4) IN ("+schoolYear+") AND T1.TERM_CODE IN ("+termCode+") "
//				  +" GROUP BY T1.STU_ID,T1.SCHOOL_YEAR,T1.TERM_CODE,T1.COURSE_CODE ";
//		return sql;
	}
	public String courseSql(String schoolYear,String termCode){//得到课程信息(包括选修课和必修课)
		String sql="SELECT TCAP.SCHOOL_YEAR,TCAP.TERM_CODE,TCAP.COURSE_CODE,TCAP.COURSE_ATTR_CODE,TCAP.COURSE_NATURE_CODE,TCAP.COURSE_CATEGORY_CODE  "
					+" FROM T_COURSE_ARRANGEMENT_PLAN TCAP  "
//					+" LEFT JOIN T_STU TS ON TS.CLASS_ID=TCAP.CLASS_ID"
					+" WHERE SUBSTR(TCAP.SCHOOL_YEAR,1,4) IN ("+schoolYear+") AND TCAP.TERM_CODE IN ("+termCode+")  "
					+" UNION  "
					+" SELECT  TCAP.SCHOOL_YEAR,TCAP.TERM_CODE,TCAP.SCOURE_CODE,TCAP.COURSE_ATTR_CODE,TCAP.COURSE_NATURE_CODE,TCAP.COURSE_CATEGORY_CODE   "
					+" FROM T_STU_COURSE_CHOOSE TCAP  "
					+" WHERE SUBSTR(TCAP.SCHOOL_YEAR,1,4) IN ("+schoolYear+") AND TCAP.TERM_CODE IN ("+termCode+")";
		return sql;
	}
	/**
	 * 某一学期挂科信息表
	 * @param schoolYear 学年
	 * @param termCode  学期
	 * @return
	 */
	public String getBkSql(String schoolYear,String termCode,String lx){//得到某一学期挂科信息表
		//学年 学期 课程ID 挂科数
		String sql="SELECT T1.SCHOOL_YEAR,T1.TERM_CODE,T1."+lx+",COUNT(T1.STU_ID) GKCount"
					 +" FROM("
					 +" SELECT T1.STU_ID,T1.SCHOOL_YEAR,T1.TERM_CODE,T1.COURSE_CODE FROM "
//					 +"t_failExamination T1 "
					 +" (SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH " 
					 +" WHERE TSSDH.CS>=1 AND (((TSSDH.HIERARCHICAL_SCORE_CODE IS NULL) OR((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND TSSDH.CENTESIMAL_SCORE>0)) "
					 +" AND  TSSDH.CENTESIMAL_SCORE<=59 )  "
					 +" UNION "
					 +" SELECT TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE FROM T_STU_SCORE_HISTORY TSSDH  "
					 +" LEFT JOIN t_code_score_hierarchy  TCSH ON TCSH.CODE_=TSSDH.HIERARCHICAL_SCORE_CODE  " 
					 +" WHERE TSSDH.CS>=1 AND ((TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL) AND TSSDH.CENTESIMAL_SCORE=0) AND TCSH.CENTESIMAL_SCORE<=59) T1"
					 +" WHERE SUBSTR(T1.SCHOOL_YEAR,1,4) IN ("+schoolYear+") AND T1.TERM_CODE IN ("+termCode+")) t1 "
					+" GROUP BY T1.SCHOOL_YEAR,T1.TERM_CODE,T1."+lx+"";
		return sql;
	}
	/**
	 * 得到学该门课程的学生人数
	 * @param schoolYear 学年
	 * @param termCode  学期
	 * @return
	 */
	public String getBkkcStuSql(String stuSql,String schoolYear,String termCode){//得到学该门课程的学生人数
		//课程号 学该课程人数
		 String sql="SELECT TCAP.COURSE_CODE CL01,COUNT(TS.NO_) CL02 "
					+ " FROM T_COURSE_ARRANGEMENT_PLAN TCAP "
					+ " LEFT JOIN T_CLASSES TCL ON TCL.NO_=TCAP.CLASS_ID "
					+ " LEFT JOIN ("+stuSql+") TS ON TS.CLASS_ID=TCL.NO_ "
					+ " GROUP BY TCAP.COURSE_CODE";
//		String sql="SELECT TCAP.COURSE_ID CL01,(CASE WHEN SUM(T1.CL01) IS NULL THEN 0 ELSE SUM(T1.CL01) END)  CL02 "
//				+" FROM T_COURSE_ARRANGEMENT TCAP  "
//				+" LEFT JOIN T_CLASS_TEACHING_XZB TCTX ON TCTX.TEACH_CLASS_ID=TCAP.TEACHINGCLASS_ID "
//				+" LEFT JOIN (SELECT TS.CLASS_ID,COUNT(TS.NO_) CL01 FROM ("+stuSql+") TS,("+getGkSql(schoolYear, termCode)+") t where t.stu_id=ts.no_ GROUP BY TS.CLASS_ID) T1 ON T1.CLASS_ID =TCTX.CLASS_ID "
//				+" GROUP BY TCAP.COURSE_ID ORDER BY CL02 DESC";
		return sql;
	}
	/**
	 * 得到该专业的学生人数
	 * @return
	 */
	public String geMajorSql(String stuSql,String schoolYear,String termCode){//得到该专业的学生人数
		//专业id 学生人数
		String sql="SELECT T1.MAJOR_ID,TCDT.NAME_,T1.COUNT_ FROM ( "
					+" SELECT TS.MAJOR_ID ,COUNT(TS.NO_) COUNT_ FROM ("+stuSql+") TS,("+getGkSql(schoolYear, termCode)+") t where t.stu_id=ts.no_ GROUP BY TS.MAJOR_ID ) T1 "
					+" LEFT JOIN  T_CODE_DEPT_TEACH TCDT  ON T1.MAJOR_ID=TCDT.ID ";
		return sql;
	}
	/**
	 * 得到该教师所教学科的学生总人数
	 * @return
	 */
	public String geteaSql(String stuSql,String schoolYear,String termCode){//得到该专业的学生人数
		//专业id 学生人数
		String sql="SELECT TCA.TEA_ID CL01,SUM(T1.CL02) CL02 "
					+" FROM T_COURSE_ARRANGEMENT TCA LEFT JOIN  ("+getBkkcStuSql(stuSql, schoolYear, termCode)+")"
					+"  T1 ON T1.CL01=TCA.COURSE_ID GROUP BY TCA.TEA_ID";
		return sql;
	}
}
