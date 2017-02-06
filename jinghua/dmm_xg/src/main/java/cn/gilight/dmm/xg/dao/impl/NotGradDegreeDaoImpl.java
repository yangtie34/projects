package cn.gilight.dmm.xg.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.NotGradDegreeDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.product.EduUtils;
@Repository("notGradDegreeDao")
public class NotGradDegreeDaoImpl implements NotGradDegreeDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	
	@Override
	public Map<String, Object> queryXwInfo(List<String> deptList,List<AdvancedParam> advancedList) {//已修改
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 得到无学位学生人数
		String sql="SELECT * FROM "
					+" (SELECT COUNT(DISTINCT TS.NO_) DCOUNT_ FROM  T_STU_WARNING_DEGREE TSWD LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSWD.STU_ID WHERE TSWD.CREDITS>= '"+Constant.NODEGREE_CODE+"') TD, "
					+" (SELECT COUNT(DISTINCT TS.NO_) GCOUNT_ FROM T_STU_WARNING_GRADUATION  TSWG , ("+stuSql+") TS  WHERE  TS.NO_=TSWG.STU_ID AND TSWG.CREDITS_ALL>= '"+Constant.NODEGREE_CODE+"' ) TG";
//				  +"  AND TS.STU_ROLL_CODE='1'";
		return baseDao.getJdbcTemplate().queryForList(sql).get(0);
	}

	/**
	 * 得到无学位证学生人数及比例
	 */
	@Override
	public List<Map<String, Object>> queryXwfbAndRatio(List<String> deptList,List<AdvancedParam> advancedList,String fb) {//已修改
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 院系名称 无学位人数  无学位人数比例
//		String sql="SELECT T.DEPTMC NAME_,COUNT(DISTINCT T.NO) COUNT_,ROUND((COUNT(DISTINCT T.NO)/(SELECT COUNT(TS.NO_) COUNT_ FROM ("+stuSql+") TS)),2)*100 RATIO_  FROM ("+getNodegreeStuSql(deptList, advancedList)
//				   + ") T  WHERE T.DEPTMC IS NOT NULL  GROUP BY T.DEPTMC";
//		String zs=
		String sql="SELECT  TCDT.NAME_ ,COUNT( TSWD.STU_ID) COUNT_, "
				    +" (CASE WHEN ((SELECT COUNT(DISTINCT TSWD.NO_) FROM ("+stuSql+") TSWD ) =0) THEN 0 ELSE ("
					+" ROUND(COUNT(DISTINCT TSWD.STU_ID)/"
					+" (SELECT COUNT(DISTINCT TSWD.NO_) FROM ("+stuSql+") TSWD ) ,2)*100) END) RATIO_  "
					+" FROM  (SELECT * FROM T_STU_WARNING_DEGREE TZ LEFT JOIN ("+stuSql+") TS ON TS.NO_=TZ.STU_ID WHERE TZ.CREDITS>= '"+Constant.NODEGREE_CODE+"') TSWD "
					+" LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSWD.STU_ID "
					+" LEFT JOIN  T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.DEPT_ID "
					+" WHERE TS.DEPT_ID= TSWD.DEPT_ID OR TS.MAJOR_ID =TSWD.MAJOR_ID"
//					+" AND TSWD.CREDITS>= "+Constant.NODEGREE_CODE 
//					+"  AND TS.STU_ROLL_CODE='1'"
					+" GROUP BY TCDT.NAME_"	;
		return  baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 得到无法毕业学生人数及比例
	 */
	@Override
	public List<Map<String, Object>> queryByfbAndRatio(List<String> deptList,List<AdvancedParam> advancedList,String fb) {//已修改
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
//		String sql="SELECT T.DEPTMC NAME_,COUNT( TSWD.STU_ID) COUNT_ FROM ("+getNodegreeStuSql(deptList, advancedList)
//				   + ") T, ("+stuSql+") TS WHERE TS.DEPT_ID= T.DEPTID OR TS.MAJOR_ID =T.MAJORID";
		// 院系名称 无学位人数  无法毕业人数比例
		String sql="SELECT  TCDT.NAME_ ,COUNT( TSWD.STU_ID) COUNT_, "
				+" (CASE WHEN ((SELECT COUNT(DISTINCT TSWD.NO_) FROM ("+stuSql+") TSWD ) =0) THEN 0 ELSE ("
				+" ROUND(COUNT(DISTINCT TSWD.STU_ID)/"
				+" (SELECT CASE (COUNT(DISTINCT TSWD.NO_)) WHEN 0 THEN 0 ELSE (COUNT(DISTINCT TSWD.NO_)) END  FROM ("+stuSql+") TSWD ) ,2)*100) END) RATIO_  "
				+" FROM  (SELECT * FROM T_STU_WARNING_GRADUATION TZ LEFT JOIN ("+stuSql+") TS ON TS.NO_=TZ.STU_ID WHERE TZ.CREDITS_ALL>= '"+Constant.NODEGREE_CODE+"') TSWD "
				+" LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSWD.STU_ID "
				+" LEFT JOIN  T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.DEPT_ID "
				+" WHERE TS.DEPT_ID= TSWD.DEPT_ID OR TS.MAJOR_ID =TSWD.MAJOR_ID"
//				+" AND TSWD.CREDITS_ALL>= "+Constant.NODEGREE_CODE 
//				+"  AND TS.STU_ROLL_CODE='1'"
				+" GROUP BY TCDT.NAME_"	;
		return  baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 无学位学生学科分布
	 */
	@Override
	public List<Map<String, Object>> queryXkfb(int year,List<String> deptList,List<AdvancedParam> advancedList) {//已修改
//		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String sql="SELECT TT.SUBMC NAME_,COUNT(DISTINCT TT.NO) COUNT_ FROM T_STU_WARNING_DEGREE TSWD LEFT JOIN ("+getDetailstuSql(year,deptList, advancedList)
				+ ") TT ON TSWD.STU_ID=TT.NO WHERE TSWD.CREDITS>= "+Constant.NODEGREE_CODE +" AND TT.SUBMC IS NOT NULL "
				+" GROUP BY TT.SUBMC";
		// 学科类别名称  无学位人数
//		String sql="SELECT  TCS.PNAME NAME_,COUNT(DISTINCT TSWD.STU_ID) COUNT_ "
//				+"  FROM  T_STU_WARNING_DEGREE TSWD "
//				+"  LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSWD.STU_ID "
//				+"  LEFT JOIN  T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.MAJOR_ID "
//				+"  LEFT JOIN  (select t.id,t.name_, tp.name_ pname, tp.id pid  "
//				+"  from t_code_subject_degree t, t_code_subject_degree tp  "
//				+"  where substr(t.path_,1,4)=tp.path_ order by t.level_,t.pid,t.order_ ) TCS ON TCS.ID=TCDT.SUBJECT_ID "
//				+"  WHERE TSWD.CREDITS>= "+Constant.NODEGREE_CODE +" AND TCS.PNAME IS NOT NULL "
////				+"  AND TS.STU_ROLL_CODE='1'"
//				+"  GROUP BY TCS.PNAME";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 无学位学生年级分布
	 */
	@Override
	public List<Map<String, Object>> queryNjfb(int year,List<String> deptList,List<AdvancedParam> advancedList) {//已修改
//		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 学生年级  无学位学生人数
		String sql=  "SELECT T1.GRADE_,COUNT(DISTINCT T1.NO_) COUNT_ FROM " 
				     +" (SELECT  (CASE(TO_CHAR(TO_DATE("+year+",'yyyy'),'yyyy')+1-TS.ENROLL_GRADE) "
			         +" WHEN 1 THEN '"+EduUtils.getBzdmNj().get(0).get("mc")+"'"//'一年级' "
			         +" WHEN 2 THEN '"+EduUtils.getBzdmNj().get(1).get("mc")+"'"///'二年级' "
			         +" WHEN 3 THEN '"+EduUtils.getBzdmNj().get(2).get("mc")+"'"//'三年级' "
			         +" WHEN 4 THEN '"+EduUtils.getBzdmNj().get(3).get("mc")+"'"//'四年级' "
			         +" WHEN 5 THEN '"+EduUtils.getBzdmNj().get(4).get("mc")+"'"//'五年级' "
			         +"  END)  GRADE_, TS.*"
				+"  FROM  T_STU_WARNING_DEGREE TSWD "
				+"  LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSWD.STU_ID "
				+"  WHERE TSWD.CREDITS>= "+Constant.NODEGREE_CODE 
//				+"  AND TS.STU_ROLL_CODE='1'  "
				+ ") T1"
				+"  GROUP BY T1.GRADE_";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 无学位学生性别分布
	 */
	@Override
	public List<Map<String, Object>> queryXbfb(int year,List<String> deptList,List<AdvancedParam> advancedList) {//
//		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 学生性别  无学位学生人数
		String sql="SELECT TC.NAME_ ,COUNT(DISTINCT TSWD.STU_ID)  COUNT_ "
				+"  FROM T_STU_WARNING_DEGREE TSWD "
				+"  LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSWD.STU_ID "
				+"  LEFT JOIN T_CODE TC ON TC.CODE_=TS.SEX_CODE AND TC.CODE_TYPE='"+Constant.CODE_SEX_CODE+"' "
				+"  WHERE TSWD.CREDITS>= "+Constant.NODEGREE_CODE 
//				+"  AND TS.STU_ROLL_CODE='1'"
				+"  GROUP BY TC.NAME_  ORDER BY TC.NAME_ ASC";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 无学位学生原因分布
	 */
	@Override
	public List<Map<String, Object>> queryYyfb(int year,List<String> deptList,List<AdvancedParam> advancedList) {
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 测试sql
		String sql="SELECT '学分不够' NAME_ , COUNT(TS.NO_) COUNT_ FROM T_STU_WARNING_DEGREE TSWD"
				+ " LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSWD.STU_ID "
				+"  WHERE TSWD.CREDITS>= "+Constant.NODEGREE_CODE;
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
/********************************************************************************************************************/
/***********************************以下是最近几年数据*******************************************************************/
	/**
	 * 最近几年无学位学生状态分布
	 */
	@Override
	public List<Map<String, Object>> queryStatefbByYear(List<String> deptList,List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		// 毕业年份（取最近几年），状态名称  无学位数人数
		String sql="SELECT  (TS.ENROLL_GRADE+TS.STU_STATE_CODE) YEAR_,TC.NAME_,COUNT(TS.NO_) COUNT_ "
				+"  FROM T_STU_WARNING_DEGREE TSWD "
				+"  LEFT JOIN  ("+stuSql+") TS  ON TS.NO_=TSWD.STU_ID "
				+"  LEFT JOIN  T_CODE TC ON TC.CODE_=TS.STU_STATE_CODE AND TC.CODE_TYPE='STU_STATE_CODE'  "
				+"  WHERE (TS.ENROLL_GRADE+TS.STU_STATE_CODE) BETWEEN (TO_CHAR(SYSDATE,'yyyy')-'6') AND TO_CHAR(SYSDATE,'yyyy')  AND TS.STU_ROLL_CODE='1' "
				+"  AND TSWD.CREDITS>="+Constant.NODEGREE_CODE
				+"  GROUP BY (TS.ENROLL_GRADE+TS.STU_STATE_CODE),TC.NAME_  "
				+"  ORDER BY (TS.ENROLL_GRADE+TS.STU_STATE_CODE) DESC";
		  map.put("list", baseDao.getJdbcTemplate().queryForList(sql));
		  String sql1="SELECT DISTINCT TS1.YEAR_ FROM ("+sql+") TS1 ORDER BY TS1.YEAR_ ASC";
		  map.put("year", baseDao.getJdbcTemplate().queryForList(sql1));
		  String sql2="SELECT DISTINCT TS1.NAME_ FROM ("+sql+") TS1";
		  map.put("type", baseDao.getJdbcTemplate().queryForList(sql2));
		  list.add(map);
		  return list;
	}
	/****************************************详细信息表******************************************************/
	/**
	 * 得到学生的详细信息
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	@Override
	public String getDetailstuSql(int year,List<String> deptList,List<AdvancedParam> advancedList){
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String sql="SELECT T.*,TCS.PNAME submc,'学分不够' reasonmc,"
				+ "(CASE(TO_CHAR(TO_DATE("+year+",'yyyy'),'yyyy')+1-T.ENROLL_GRADE) "
				         +" WHEN 1 THEN '"+EduUtils.getBzdmNj().get(0).get("mc")+"'"//'一年级' "
				         +" WHEN 2 THEN '"+EduUtils.getBzdmNj().get(1).get("mc")+"'"///'二年级' "
				         +" WHEN 3 THEN '"+EduUtils.getBzdmNj().get(2).get("mc")+"'"//'三年级' "
				         +" WHEN 4 THEN '"+EduUtils.getBzdmNj().get(3).get("mc")+"'"//'四年级' "
				         +" WHEN 5 THEN '"+EduUtils.getBzdmNj().get(4).get("mc")+"'"//'五年级' "
				         +"  END)  GRADEMC "
				    + " FROM ("
					+businessDao.getStuDetailSql(stuSql)
					+ ") T "
					+ " LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.CODE_=T.majorid"
					+ " LEFT JOIN  (select t.id,t.name_, tp.name_ pname, tp.id pid "
					+ " from t_code_subject_degree t, t_code_subject_degree tp "
					+ " where substr(t.path_,1,4)=tp.path_ order by t.level_,t.pid,t.order_ ) TCS ON TCS.ID=TCDT.SUBJECT_ID  WHERE 1=1 ";
		return sql;
	}
	/**
	 * 得到无学位证学生的详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	@Override
	public String getNodegreeStuSql(int year,List<String> deptList,List<AdvancedParam> advancedList){
		String sql="SELECT TT.*,TSWD.CREDITS CREDITS FROM T_STU_WARNING_DEGREE TSWD LEFT JOIN ("+getDetailstuSql(year,deptList, advancedList)
					+ ") TT ON TSWD.STU_ID=TT.NO WHERE TSWD.CREDITS>= "+Constant.NODEGREE_CODE;
		return sql;
	}
	/**
	 * 得到无学位证学生的详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	@Override
	public String getNoGraduationStuSql(int year,List<String> deptList,List<AdvancedParam> advancedList){
		String sql="SELECT TT.*,TSWD.CREDITS_ALL CREDITS FROM T_STU_WARNING_GRADUATION TSWD LEFT JOIN ("+getDetailstuSql(year,deptList, advancedList)
				+ ") TT ON TSWD.STU_ID=TT.NO WHERE TSWD.CREDITS_ALL>= "+Constant.NODEGREE_CODE;
		return sql;
	}

	@Override
	public String queryXwfbAndRatios(int year,List<String> deptList,List<AdvancedParam> advancedList,String fb) {
		
		String sql="SELECT T.DEPTID DEPT_ID ,T.MAJORID MAJOR_ID,T.CLASSID CLASS_ID  FROM (SELECT TT.*,TSWD.CREDITS CREDITS FROM T_STU_WARNING_DEGREE TSWD LEFT JOIN ("+getDetailstuSql(year,deptList, advancedList)
				+ ") TT ON TSWD.STU_ID=TT.NO WHERE TSWD.CREDITS>= "+Constant.NODEGREE_CODE +") T WHERE T.NO IS NOT NULL ";
	    if("2".equals(fb)){
	    	 sql="SELECT T.DEPTID DEPT_ID ,T.MAJORID MAJOR_ID,T.CLASSID CLASS_ID FROM (SELECT TT.*,TSWD.CREDITS_ALL CREDITS FROM T_STU_WARNING_GRADUATION TSWD LEFT JOIN ("+getDetailstuSql(year,deptList, advancedList)
	 				+ ") TT ON TSWD.STU_ID=TT.NO WHERE TSWD.CREDITS_ALL>= "+Constant.NODEGREE_CODE +") T WHERE T.NO IS NOT NULL ";
	    }
		return sql;
	}
}
