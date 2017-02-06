package cn.gilight.dmm.xg.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.xg.dao.FeeWarningDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.product.EduUtils;
/**
 * 学费预警
 * @author lijun
 *
 */
@Repository("feeWarningDao")
public class FeeWarningDaoImpl implements FeeWarningDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	/**
	 * 得到当前学年未缴纳学费学生总人数和总金额
	 */
	@Override
	public Map<String, Object> queryXfInfo(List<String> deptList,List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		//未缴费学生人数 未缴费金额
		String sql="SELECT COUNT(DISTINCT TSWC.STU_ID) COUNT_ ,(CASE WHEN SUM(TSWC.ARREAR_MONEY) IS NULL THEN 0 ELSE SUM(TSWC.ARREAR_MONEY) END) MONEY_  "
					+" FROM (SELECT TSC.*,TS.DEPT_ID,TS.MAJOR_ID FROM T_STU_CHARGE TSC , ("+stuSql+") TS WHERE TS.NO_=TSC.STU_ID)  TSWC "
//					+" WHERE SUBSTR(TSWC.SCHOOL_YEAR,1,4)='"+year+"'"
				    +" WHERE TSWC.ARREAR_MONEY >0";
		return baseDao.getJdbcTemplate().queryForList(sql).get(0);
	}
	/**
	 * 未缴纳学费类型分布
	 */
	@Override
	public List<Map<String, Object>> queryXflxfb(List<String> deptList,List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 学生年级  学生人数
		String sql="SELECT  TC.NAME_,SUM(TSWC.ARREAR_MONEY) COUNT_ "
					+"  FROM (SELECT TSC.*,TS.DEPT_ID,TS.MAJOR_ID FROM T_STU_CHARGE TSC , ("+stuSql+") TS WHERE TS.NO_=TSC.STU_ID)  TSWC "
					+"  LEFT JOIN T_CODE TC ON TC.CODE_=TSWC.CHARGE_CODE AND TC.CODE_TYPE='CHARGE_CODE' "
//					+"  WHERE SUBSTR(TSWC.SCHOOL_YEAR,1,4)='"+year+"'"
					+"  WHERE TSWC.ARREAR_MONEY >=0"
					+"  GROUP BY TC.NAME_";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 未缴纳学费学生类型分布
	 */
	@Override
	public List<Map<String, Object>> queryXslxfb(List<String> deptList,List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 学生年级  学生人数
		String sql="SELECT T1.GRADE_,COUNT(DISTINCT T1.STU_ID) COUNT_ FROM "
				   +" (SELECT (CASE(TO_CHAR(TO_DATE('"+year+"','yyyy'),'yyyy')-TSWC.ENROLL_GRADE) "
			       +" WHEN 1 THEN '"+EduUtils.getBzdmNj().get(0).get("mc")+"'"//'一年级' "
			       +" WHEN 2 THEN '"+EduUtils.getBzdmNj().get(1).get("mc")+"'"///'二年级' "
			       +" WHEN 3 THEN '"+EduUtils.getBzdmNj().get(2).get("mc")+"'"//'三年级' "
			       +" WHEN 4 THEN '"+EduUtils.getBzdmNj().get(3).get("mc")+"'"//'四年级' "
			       +" WHEN 5 THEN '"+EduUtils.getBzdmNj().get(4).get("mc")+"'"//'五年级' "
				   +"       END)   GRADE_,TSWC.* "
				   +" FROM  (SELECT TSC.*,TS.DEPT_ID,TS.MAJOR_ID,TS.ENROLL_GRADE FROM T_STU_CHARGE TSC , ("+stuSql+") TS WHERE TS.NO_=TSC.STU_ID) TSWC "
//				   +" WHERE SUBSTR(TSWC.SCHOOL_YEAR,1,4)='"+year+"'"
				   +" WHERE TSWC.ARREAR_MONEY >0"
				   +" ) T1 "
				   + " GROUP BY T1.GRADE_";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 某一学年欠费金额及比例(欠费人数/人数)
	 */
	@Override
	public List<Map<String, Object>> queryQfjeAndRadio(int year,
			List<String> deptList,List<AdvancedParam> advancedList) {
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		// 学年 金额  比例
		String sql="SELECT SUBSTR(TSWC.SCHOOL_YEAR,1,4) YEAR_,SUM(TSWC.ARREAR_MONEY) MONEY_,ROUND(COUNT(DISTINCT TSWC.STU_ID) /(SELECT COUNT(DISTINCT TS.NO_) FROM ("+stuSql+") TS),2)*100 RADIO "
	    			+" FROM (SELECT TSC.*,TS.DEPT_ID,TS.MAJOR_ID FROM T_STU_CHARGE TSC , ("+stuSql+") TS WHERE TS.NO_=TSC.STU_ID ) TSWC "
					+" WHERE TSWC.ARREAR_MONEY >0"
					+" GROUP BY SUBSTR(TSWC.SCHOOL_YEAR,1,4)";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 欠费详细信息
	 */
	@Override
	public String queryQfInfo(List<String> deptList,
			List<AdvancedParam> advancedList,Integer year, String edu, String lx, String slx) {
		String yearSql="";String zjztSql="";String QflxSql="";
		int now_year = EduUtils.getSchoolYear4();//得到当前学年
		if(year==1){
			yearSql="";
		}else{
			yearSql=" AND SUBSTR(TSC.SCHOOL_YEAR,0,4) ="+year;
		}
		if(slx.contains(",")){
			zjztSql=" TS.STU_ROLL_CODE IN ("+slx+") ";
		}else{
			zjztSql=" TS.STU_ROLL_CODE ="+slx;
		}
		if(lx.contains(",")){
			QflxSql=" TSC.CHARGE_CODE IN ("+lx+")  ";
		}else{
			QflxSql=" TSC.CHARGE_CODE ="+lx;
		}
		String stuSql;
		// 某学年学生数据sql
		stuSql= businessDao.getStuSql(now_year, deptList, advancedList);
	    String 	sql="SELECT * FROM ("
			+" SELECT TSC.*,TS.DEPT_ID,TS.MAJOR_ID,TS.STU_CATEGORY_ID  FROM T_STU_CHARGE TSC , (SELECT * FROM ("+stuSql+") TS WHERE "+zjztSql+" ) TS  WHERE "+QflxSql+" AND TS.NO_=TSC.STU_ID AND TSC.ARREAR_MONEY >0 "+yearSql+" )  TS "
			+" LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TS.DEPT_ID=TCDT.ID "
			+" LEFT JOIN T_CODE_STU_CATEGORY TCSC ON TCSC.ID=TS.STU_CATEGORY_ID ";
	    return sql;
//		String id_=advancedList.get(1).getValues();
//		List<Map<String,Object>> list =new ArrayList<>();
//		Map<String,Object> map =new HashMap<>();
//		String sql="SELECT COUNT(DISTINCT TS.NO_) CL02 FROM (SELECT * FROM ("+stuSql+") TS WHERE "+zjztSql+" ) TS";
//		map.put("CL02", baseDao.getJdbcTemplate().queryForList(sql).get(0));
//		// 院系 学生数 欠费人数 占比 金额
//		 sql="SELECT TCDT.NAME_ CL01,"
////				+" (SELECT COUNT(DISTINCT TS.NO_) CL02 FROM (SELECT * FROM ("+stuSql+") TS WHERE "+zjztSql+" ) TS) CL02, "
//				+" TS.DEPT_ID,COUNT(DISTINCT TS.STU_ID) CL03,"
//				+" (CASE WHEN (SELECT COUNT(DISTINCT TS.NO_) FROM (SELECT * FROM ("+stuSql+") TS WHERE "+zjztSql+" ) TS) =0 THEN 0 ELSE (ROUND(COUNT(DISTINCT TS.STU_ID)/(SELECT COUNT(DISTINCT TS.NO_) FROM (SELECT * FROM ("+stuSql+") TS WHERE "+zjztSql+" ) TS),2)) END)*100 CL04, "
//				+" SUM(TS.ARREAR_MONEY) CL05 FROM ("
//				+" SELECT TSC.*,TS.DEPT_ID,TS.MAJOR_ID,TS.STU_CATEGORY_ID  FROM T_STU_CHARGE TSC , (SELECT * FROM ("+stuSql+") TS WHERE "+zjztSql+" ) TS  WHERE "+QflxSql+" AND TS.NO_=TSC.STU_ID AND TSC.ARREAR_MONEY >0 )  TS "
////			    +" AND SUBSTR(TSWC.SCHOOL_YEAR,1,4)='"+now_year+"'"
//				+" LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TS.DEPT_ID=TCDT.ID "
//				+" LEFT JOIN T_CODE_STU_CATEGORY TCSC ON TCSC.ID=TS.STU_CATEGORY_ID "
//				+" WHERE TS.DEPT_ID=  "+id_
//				+" GROUP BY TCDT.NAME_,TS.DEPT_ID";
//		 	map.put("list", baseDao.getJdbcTemplate().queryForList(sql));
//		list.add(map);
//		 	return list;
	}
	/**
	 * 发送邮件内容
	 */
	@Override
	public Map<String, Object> sendQfInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String year, String edu,
			String lx, String slx) {
		String yearSql=""; String eduSql="";String zjztSql="";String QflxSql="";
		int rYear=Integer.parseInt(year);
		if(rYear==1){
			yearSql="";
		}else{
			yearSql=" AND SUBSTR(TSC.SCHOOL_YEAR,0,4) ="+EduUtils.getSchoolYear4();
		}
		if(slx.contains(",")){
			zjztSql=" TS.STU_ROLL_CODE IN ("+slx+") ";
		}else{
			zjztSql=" TS.STU_ROLL_CODE ="+slx;
		}
		if(lx.contains(",")){
			QflxSql=" TSC.CHARGE_CODE IN ("+lx+")  ";
		}else{
			QflxSql=" TSC.CHARGE_CODE ="+lx;
		}
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(EduUtils.getSchoolYear4(), deptList, advancedList);
		String id_=advancedList.get(0).getValues();//得到院系ID
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		// 学年 学号 姓名 性别 专业 班级  欠费类型 欠费金额
		String sql= "SELECT TSWC.SCHOOL_YEAR CL01, TSWC.STU_ID CL02,TS.NAME_ CL03,   TC.NAME_ CL04,    TCDT.NAME_ CL05, TCS.NAME_ CL06 ,  TCC.NAME_ CL07 ,  TSWC.ARREAR_MONEY CL08"
					+" FROM (SELECT * FROM ("+stuSql+") TS WHERE "+zjztSql+" ) TS  "
					+" RIGHT JOIN (SELECT * FROM T_STU_CHARGE TSC WHERE "+QflxSql+yearSql+" AND TSC.ARREAR_MONEY >0 ) TSWC ON TS.NO_=TSWC.STU_ID "
					+" LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TS.MAJOR_ID=TCDT.ID "
					+" LEFT JOIN T_CODE TC ON TC.CODE_=TS.SEX_CODE AND TC.CODE_TYPE='SEX_CODE' "
					+" LEFT JOIN T_CODE TCC ON TCC.CODE_=TSWC.CHARGE_CODE AND TCC.CODE_TYPE='CHARGE_CODE' "
					+" LEFT JOIN T_CLASSES TCS ON TCS.NO_=TS.CLASS_ID "
					+" WHERE TS.DEPT_ID= "+id_+ " ";
		map.put("xx", baseDao.getJdbcTemplate().queryForList(sql));
		//学生学历类别  学生人数
		sql="SELECT TCE.NAME_ NAME_, COUNT(DISTINCT TSWC.STU_ID) COUNT_ "
			+" FROM (SELECT * FROM ("+stuSql+") TS WHERE "+zjztSql+" ) TS  "
			+" RIGHT JOIN (SELECT * FROM T_STU_CHARGE TSC WHERE "+QflxSql+yearSql+" AND TSC.ARREAR_MONEY >0) TSWC ON TS.NO_=TSWC.STU_ID "
			+" LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TS.DEPT_ID=TCDT.ID "
			+" LEFT JOIN T_CODE_EDUCATION TCE ON TCE.ID=TS.EDU_ID "
			+" WHERE TS.DEPT_ID= "+id_+ " "
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
	public List<Map<String, Object>> queryQfTypeList() {
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
				+ "(CASE(TO_CHAR(TO_DATE('2015','yyyy'),'yyyy')+1-T.ENROLL_GRADE) "//"+EduUtils.getSchoolYear4()+"
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
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(EduUtils.getSchoolYear4(), deptList, advancedList);
		String sql="SELECT TT.*,TSWD.qfnamemc qfnamemc,TSWD.SCHOOL_YEAR,TSWD.ARREAR_MONEY money FROM  (SELECT TSC.*,TSC.NAME_ qfnamemc FROM (SELECT *  FROM T_STU_CHARGE TSC LEFT JOIN T_CODE TC ON TC.CODE_=TSC.CHARGE_CODE AND TC.CODE_TYPE='CHARGE_CODE') TSC,("+stuSql+") TS WHERE TS.NO_=TSC.STU_ID AND TSC.ARREAR_MONEY>0) TSWD , ("+getDetailstuSql(deptList, advancedList)
					+ ") TT WHERE TSWD.STU_ID=TT.NO ";
		return sql;
	}
}
