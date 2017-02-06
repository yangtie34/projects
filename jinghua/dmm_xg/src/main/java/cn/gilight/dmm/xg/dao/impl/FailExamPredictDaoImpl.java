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
import cn.gilight.dmm.xg.dao.FailExamPredictDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.product.EduUtils;
/**
 * 挂科预测
 * @author lijun
 *
 */
@Repository("failExamPredictDao")
public class FailExamPredictDaoImpl implements FailExamPredictDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	/**
	 * 得到当前学年挂科预测学生人数和预测挂科概率
	 */
	@Override
	public Map<String, Object> queryGkInfo(List<String> deptList,List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		//期末考试人数  挂科概率
		String sql="SELECT T2.COUNT_ COUNT_ ,(ROUND(T2.COUNT_/T3.ZCOUNT_,2)*100) GL FROM ("
				+ "SELECT COUNT(DISTINCT T1.STU_ID) COUNT_ FROM ("+yc_sql+") T1 WHERE T1.PREDICT_SCORE<=59) T2,"
				+ "(SELECT COUNT(TS.NO_) ZCOUNT_ FROM ("+stuSql+") TS) T3";
		return baseDao.getJdbcTemplate().queryForList(sql).get(0);
	}
	/**
	 * 挂科预测学生性别类型分布
	 */
	@Override
	public List<Map<String, Object>> queryGklxfb(List<String> deptList,List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 学生性别  学生人数
		String sql="SELECT T1.SEXMC NAME_,COUNT(DISTINCT T1.STU_ID) COUNT_ FROM ("+yc_sql+") T1 WHERE T1.PREDICT_SCORE<=59 GROUP BY T1.SEXMC";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 挂科预测学生年级类型分布
	 */
	@Override
	public List<Map<String, Object>> queryXslxfb(List<String> deptList,List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 学生年级  学生人数
		String sql="SELECT GRADEMC GRADE_,COUNT(DISTINCT T1.STU_ID) COUNT_ FROM ("+yc_sql+") T1 WHERE T1.PREDICT_SCORE<=59 GROUP BY T1.GRADEMC";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 某一学年挂科预测学生人数和预测挂科概率(预测挂科人数挂科概率)
	 */
	@Override
	public String queryGkrsAndRadio(
			List<String> deptList,List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
	//院系名称 预测挂科人数 预测挂科人数比例 
		String sql=" SELECT T1.DEPT_ID,T1.MAJOR_ID,T1.CLASS_ID FROM ("+yc_sql+") T1 WHERE T1.PREDICT_SCORE<=59";
		return sql;
	}
	/**
	 * 挂科预测学生的详细信息
	 */
	@Override
	public String queryGkxxInfo(List<String> deptList,
			List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
	//院系名称 预测挂科人数 预测挂科人数比例 
		String sql="SELECT T2.DEPT_ID, T2.DEPTMC CL01,T2.COUNT_ CL03,T2.MCOUNT_ CL04 FROM ("
				+ " SELECT t1.dept_id,T1.DEPTMC,COUNT(DISTINCT T1.STU_ID) COUNT_,COUNT(T1.COURSE_ID) MCOUNT_  FROM ("+yc_sql+") T1 WHERE T1.PREDICT_SCORE<=59  GROUP BY T1.DEPTMC,t1.dept_id) T2";
		return sql;
	}
	/**
	 * 发送邮件内容
	 */
	@Override
	public Map<String, Object> sendGkInfo(List<String> deptList,
			List<AdvancedParam> advancedList) {
//		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(EduUtils.getSchoolYear4(), deptList, advancedList);
		String id_=advancedList.get(0).getValues();//得到院系ID
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
//		// 学号 性别 性别 专业 班级
		String sql= "SELECT TSWC.STU_ID CL01,TS.NAME_ CL02,TC.NAME_ CL03,TCDT.NAME_ CL04,TCS.NAME_ CL05 "
					+" FROM ("+stuSql+") TS  "
					+" RIGHT JOIN (SELECT DISTINCT T1.STU_ID FROM ("+yc_sql+") T1 WHERE T1.PREDICT_SCORE<=59 AND T1.DEPT_ID='"+id_+"' ) TSWC ON TS.NO_=TSWC.STU_ID "
					+" LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TS.MAJOR_ID=TCDT.ID "
					+" LEFT JOIN T_CODE TC ON TC.CODE_=TS.SEX_CODE AND TC.CODE_TYPE='SEX_CODE' "
					+" LEFT JOIN T_CLASSES TCS ON TCS.NO_=TS.CLASS_ID ";
		map.put("xx", baseDao.getJdbcTemplate().queryForList(sql));
//		//学生学历类别  学生人数
		sql="SELECT TCE.NAME_ NAME_, COUNT(TSWC.STU_ID) COUNT_ "
			+" FROM ("+stuSql+") TS  "
			+" RIGHT JOIN (SELECT DISTINCT T1.STU_ID FROM ("+yc_sql+") T1 WHERE T1.PREDICT_SCORE<=59 AND T1.DEPT_ID='"+id_+"' ) TSWC ON TS.NO_=TSWC.STU_ID "
			+" LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TS.DEPT_ID=TCDT.ID "
			+" LEFT JOIN T_CODE_EDUCATION TCE ON TCE.ID=TS.EDU_ID "
			+" GROUP BY TCE.NAME_";
		map.put("lx", baseDao.getJdbcTemplate().queryForList(sql));
		return map;
	}
	@Override
	public List<Map<String, Object>> queryXjList() {
		// 学籍状态代码  学籍状态名称
		String sql="SELECT TC.CODE_ id,TC.NAME_ MC FROM T_CODE TC WHERE TC.CODE_TYPE='STU_ROLL_CODE'";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List<Map<String, Object>> queryGkTypeList() {
		// 学籍状态代码  学籍状态名称
		String sql="SELECT TC.CODE_ id,TC.NAME_ MC FROM T_CODE TC WHERE TC.CODE_TYPE='CHARGE_CODE'";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 得到学院email地址
	 */
	@Override
	public List<Map<String, Object>> queryEmailList() {
		// 学院ID email地址
		String sql="SELECT * FROM T_EMAIL";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	//预测成绩sql语句
	// 学号 学年 学期 预测成绩 年级 课程id 性别  院系
	String yc_sql=" SELECT  TFP.*,TC.NAME_ SEXMC,DEPT.NAME_ DEPTMC ,TS.DEPT_ID,TS.MAJOR_ID,TS.CLASS_ID, "
					+ " (CASE TFP.GRADE_ID "
				       +" WHEN '1' THEN '"+EduUtils.getBzdmNj().get(0).get("mc")+"'"//'一年级' "
				       +" WHEN '2' THEN '"+EduUtils.getBzdmNj().get(1).get("mc")+"'"///'二年级' "
				       +" WHEN '3' THEN '"+EduUtils.getBzdmNj().get(2).get("mc")+"'"//'三年级' "
				       +" WHEN '4' THEN '"+EduUtils.getBzdmNj().get(3).get("mc")+"'"//'四年级' "
				       +" WHEN '5' THEN '"+EduUtils.getBzdmNj().get(4).get("mc")+"'"//'五年级' "
					   +"       END)   GRADEMC, "
				       +" TCO.NAME_ coursemc "
				 + " FROM T_STU_SCORE_PREDICT_BEH TFP "
				 + " LEFT JOIN T_STU TS  ON TS.NO_=TFP.STU_ID "
				 + " LEFT JOIN T_CODE TC ON TC.CODE_=TS.SEX_CODE AND TC.CODE_TYPE='SEX_CODE' "
				 + " LEFT JOIN T_CODE_DEPT_TEACH DEPT ON DEPT.CODE_=TS.DEPT_ID "
				 + " LEFT JOIN T_COURSE TCO ON TCO.CODE_=TFP.COURSE_ID";
	/****************************************详细信息表******************************************************/
	/**
	 * 得到学生的详细信息
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	@Override
	public String getDetailstuSql(List<String> deptList,List<AdvancedParam> advancedList){
		String sql="SELECT T.*,"
				+ "(CASE(TO_CHAR(TO_DATE('"+EduUtils.getSchoolYear4()+"','yyyy'),'yyyy')+1-T.ENROLL_GRADE) "//"+EduUtils.getSchoolYear4()+"
				         +" WHEN 1 THEN '"+EduUtils.getBzdmNj().get(0).get("mc")+"'"//'一年级' "
				         +" WHEN 2 THEN '"+EduUtils.getBzdmNj().get(1).get("mc")+"'"///'二年级' "
				         +" WHEN 3 THEN '"+EduUtils.getBzdmNj().get(2).get("mc")+"'"//'三年级' "
				         +" WHEN 4 THEN '"+EduUtils.getBzdmNj().get(3).get("mc")+"'"//'四年级' "
				         +" WHEN 5 THEN '"+EduUtils.getBzdmNj().get(4).get("mc")+"'"//'五年级' "
				         +"  END)  GRADEMC "
				    + " FROM ("
					+businessDao.getStuDetailSql(deptList, advancedList)
					+ ") T ";
		return sql;
	}
	/**
	 * 得到无学位证学生的详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	@Override
	public String getNodegreeStuSql(List<String> deptList,List<AdvancedParam> advancedList){
//		String sql="SELECT TT.*,TSWD.coursemc,T1.TK_COUNT score FROM  (SELECT DISTINCT T1.STU_ID,T1.coursemc，T1.TK_COUNT FROM ("+yc_sql+") T1 WHERE 	T1.PREDICT_SCORE<=59) TSWD , ("+getDetailstuSql(deptList, advancedList)
//					+ ") TT WHERE TSWD.STU_ID=TT.NO ";
		String sql="SELECT T.*,T.PREDICT_SCORE score,T.TK_COUNT tkcount,TCL.NAME_ classmc,TCDT.NAME_ majormc,TS.NAME_ name,T.STU_ID NO FROM ("+yc_sql+") T left join t_stu ts on ts.no_=t.stu_id left join T_CLASSES TCL on tcl.no_=ts.class_id LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.CODE_=TS.MAJOR_ID WHERE T.PREDICT_SCORE<=59 ";
		return sql;
	}
}
