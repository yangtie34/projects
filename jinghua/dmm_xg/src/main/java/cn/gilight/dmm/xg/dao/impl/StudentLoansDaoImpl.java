package cn.gilight.dmm.xg.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.xg.dao.StudentLoansDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.product.EduUtils;
@Repository("studentLoansDao")
public class StudentLoansDaoImpl implements StudentLoansDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;	
	/**
	 * 得到总的助学贷款人数以及金额
	 */
	@Override
	public List<Map<String, Object>> queryZxInfo(List<String> deptList,String schoolYear, String id,String pid) {// 查询助学贷款总体情况
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(Integer.valueOf(schoolYear.substring(0, 4)), deptList, null);
		String edu="";
		if("".equals(pid)){
			pid="";
		}else{
			pid=" AND TS.DEPT_ID= "+pid;
		}	
		if(id.contains(",")){
			edu= "' AND TS.EDU_ID in ("+id+") ";
		}else{
			edu= "' AND TS.EDU_ID = '"+id+"' ";
		}
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		// 人数 总金额  人均金额  覆盖率
		String sql="SELECT T1.RS,T1.ZMONEY_,T1.MONEY_,CASE WHEN T2.COUNT_=0 then 0 else ROUND(T1.RS/T2.COUNT_,2)*100 end FGL "
				+"  FROM (SELECT COUNT(DISTINCT TSL.STU_ID) RS,SUM(TSL.MONEY) ZMONEY_,ROUND(SUM(TSL.MONEY)/COUNT(*),2) MONEY_ "
				+"  FROM T_STU_LOAN TSL "
				+"  LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSL.STU_ID "
				+"  WHERE TSL.SCHOOL_YEAR='"+schoolYear+edu+pid+" ) T1, "
				+"  (SELECT COUNT(*) COUNT_ FROM ("+stuSql+") TS) T2";
		map.put("zt", baseDao.getJdbcTemplate().queryForList(sql).get(0));
		sql="  SELECT T1.NAME_ NAME_,T1.RS RS ,ROUND(T1.AVG_,2) AVG_,ROUND(T1.RS/T3.COUNT_,2) ZB,ROUND(T1.RS/T2.COUNT_,2) FGL "
			+" FROM(SELECT TC.NAME_ NAME_,COUNT(*) RS,SUM(TSL.MONEY)/COUNT(*) AVG_ "
			+" FROM T_STU_LOAN TSL "
			+" LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSL.STU_ID "
			+" LEFT JOIN T_CODE TC ON TC.CODE_=TSL.LOAN_CODE AND TC.CODE_TYPE='LOAN_CODE' "
			+"  WHERE TSL.SCHOOL_YEAR='"+schoolYear+edu+pid+" "
			+" GROUP BY TC.NAME_) T1, "
			+" (SELECT COUNT(*) COUNT_ FROM ("+stuSql+") TS) T2, "
			+" (SELECT COUNT(*) COUNT_,SUM(TSL.MONEY) MONEY_ FROM T_STU_LOAN TSL WHERE TSL.SCHOOL_YEAR='"+schoolYear+"') T3 " ;
		map.put("fl", baseDao.getJdbcTemplate().queryForList(sql));
		list.add(map);
		return list;
	}

	/**
	 * 查询助学金贷款学生行为
	 */
	@Override
	public List<Map<String, Object>> queryZxxw(List<String> deptList,String schoolYear, String id) {
		// TODO Auto-generated method stub
		String sql="SELECT * FROM T_STU_LOAN";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 助学贷款分布
	 */
	@Override
	public List<Map<String, Object>> queryZxfb(int schoolYear,List<String> deptList,List<AdvancedParam> advancedList,String fb,String id_) {
		String sql="";
//		String id_=advancedList.get(1).getValues();
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(schoolYear, deptList, advancedList);
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		
		if("count".equals(fb)){//按人数查询
			// 院系名称 减免类型  学生人数
//			 sql="SELECT TCDT.NAME_ NAME_,TC.NAME_ TNAME_,COUNT(*) COUNT_"
//						+" FROM (SELECT * FROM  T_STU_LOAN TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSL "
//						+" LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSL.STU_ID "
//						+" LEFT JOIN  T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.DEPT_ID "
//						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSL.LOAN_CODE AND TC.CODE_TYPE='LOAN_CODE'"
//						+" WHERE  "
//						+" SUBSTR(TSL.SCHOOL_YEAR,0,4)="+schoolYear
//						+" AND (TSL.DEPT_ID=TS.DEPT_ID "
//						+" OR TSL.MAJOR_ID = TS.MAJOR_ID)"
//						+" GROUP BY TCDT.NAME_ ,TC.NAME_";	
			sql="SELECT T.TYPEMC TNAME_ ,COUNT(DISTINCT T.NO) COUNT_  FROM ("+getZxdkStuSql(deptList, advancedList, schoolYear)+") T WHERE T.NO IS NOT NULL AND SUBSTR(T.SCHOOL_YEAR,0,4)='"+schoolYear+"' AND (T.DEPT_ID='"+id_+"' OR T.MAJOR_ID='"+id_+"') GROUP BY T.TYPEMC";
		}else if("money".equals(fb)){
			// 院系名称 减免类型  减免金额
			 sql="SELECT TCDT.NAME_ NAME_,TC.NAME_ TNAME_,SUM(TSL.MONEY) COUNT_"
						+" FROM (SELECT * FROM  T_STU_LOAN TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSL "
						+" LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSL.STU_ID "
						+" LEFT JOIN  T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.DEPT_ID "
						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSL.LOAN_CODE AND TC.CODE_TYPE='LOAN_CODE'"
						+" WHERE TS.DEPT_ID= "+id_+ " "
						+" AND SUBSTR(TSL.SCHOOL_YEAR,0,4)="+schoolYear
						+" AND (TSL.DEPT_ID=TS.DEPT_ID "
						+" OR TSL.MAJOR_ID = TS.MAJOR_ID)"
						+" GROUP BY TCDT.NAME_ ,TC.NAME_";		
		}else if("scale".equals(fb)){
			// 院系名称 减免类型  减免金额
			 sql= " SELECT T1.NAME_ NAME_,T1.TNAME_ TNAME_,ROUND(T1.COUNT_/T2.COUNT_,2)*100 COUNT_ "
					    +" FROM(SELECT TCDT.NAME_ NAME_,TC.NAME_ TNAME_,COUNT(*) COUNT_"
						+" FROM (SELECT * FROM  T_STU_LOAN TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSL "
						+" LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSL.STU_ID "
						+" LEFT JOIN  T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.DEPT_ID "
						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSL.LOAN_CODE AND TC.CODE_TYPE='LOAN_CODE'"
						+" WHERE TS.DEPT_ID= "+id_+ " "
						+" AND SUBSTR(TSL.SCHOOL_YEAR,0,4)="+schoolYear
						+" AND (TSL.DEPT_ID=TS.DEPT_ID "
						+" OR TSL.MAJOR_ID = TS.MAJOR_ID)"
						+" GROUP BY TCDT.NAME_ ,TC.NAME_) T1 ,"
						+" (SELECT COUNT(*) COUNT_ FROM ("+stuSql+") TS) T2";		
		}else{
			map.put("kz", "kz");
		}
	
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 查询历年助学贷款变化
	 */
	@Override
	public List<Map<String, Object>> queryYearZxfb(List<String> deptList,int year,String bh,List<AdvancedParam> advancedList) {
		String sql="";
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if("count".equals(bh)){//按人数查询
			//年份 减免类型 人数
			 sql="SELECT TC.NAME_ TNAME_ ,COUNT(*) COUNT_ "
						+" FROM (SELECT * FROM  T_STU_LOAN TSJL LEFT JOIN ("+stuSql+") TSS ON TSS.NO_=TSJL.STU_ID) TSL "
						+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSL.STU_ID "
						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSL.LOAN_CODE AND TC.CODE_TYPE='LOAN_CODE' "
						+" WHERE SUBSTR(TSL.SCHOOL_YEAR,1,4)="+year
						+" AND (TSL.DEPT_ID=TS.DEPT_ID "
						+" OR TSL.MAJOR_ID = TS.MAJOR_ID)"
						+" GROUP BY TC.NAME_ ";
		}else if("money".equals(bh)){
			//年份 减免类型 人数
			 sql="SELECT TC.NAME_ TNAME_ ,SUM(TSL.MONEY) COUNT_ "
						+" FROM (SELECT * FROM  T_STU_LOAN TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSL "
						+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSL.STU_ID "
						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSL.LOAN_CODE AND TC.CODE_TYPE='LOAN_CODE' "
						+" WHERE SUBSTR(TSL.SCHOOL_YEAR,1,4)="+year
						+" AND (TSL.DEPT_ID=TS.DEPT_ID "
						+" OR TSL.MAJOR_ID = TS.MAJOR_ID)"
						+" GROUP BY TC.NAME_ ";
		}else if("scale".equals(bh)){
			//减免类型 人数
			 sql= "SELECT T1.TNAME_ TNAME_,ROUND(T1.COUNT_/T2.COUNT_,2)*100 COUNT_ " 
					    +" FROM(SELECT TC.NAME_ TNAME_ ,COUNT(*) COUNT_ "
						+" FROM (SELECT * FROM  T_STU_LOAN TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSL "
						+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSL.STU_ID "
						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSL.LOAN_CODE AND TC.CODE_TYPE='LOAN_CODE' "
						+" WHERE SUBSTR(TSL.SCHOOL_YEAR,1,4)="+year
						+" AND (TSL.DEPT_ID=TS.DEPT_ID "
						+" OR TSL.MAJOR_ID = TS.MAJOR_ID)"
						+" GROUP BY TC.NAME_) T1,"
						+" (SELECT COUNT(*) COUNT_ FROM ("+stuSql+") TS) T2";			
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 得到学费减免类型
	 */
	@Override
	public List<Map<String, Object>> queryJmType() {
		// 学费减免类型
		String sql="SELECT TC.NAME_ TNAME_ FROM T_CODE TC WHERE TC.CODE_TYPE = 'LOAN_CODE' ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
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
	public String getZxdkStuSql(List<String> deptList,List<AdvancedParam> advancedList,int year){
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String sql="SELECT TS.NO_ NO,TS.MAJOR_ID,TS.DEPT_ID, TS.NAME_ NAME,TSJ.SCHOOL_YEAR,TCC.NAME_ SEXMC,TCDT.NAME_ DEPTMC,TCDTM.NAME_ MAJORMC ,TCL.NAME_ CLASSMC ,TC.NAME_ TYPEMC,TSJ.MONEY MONEY " 
				+ " FROM T_STU_LOAN TSJ "
				+ " LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSJ.STU_ID "
				+ " LEFT JOIN T_CODE TC ON TC.CODE_=TSJ.LOAN_CODE AND TC.CODE_TYPE='LOAN_CODE' "
				+ " LEFT JOIN T_CODE TCC ON TCC.CODE_=TS.SEX_CODE AND TCC.CODE_TYPE='SEX_CODE' "
				+ " LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.CODE_=TS.DEPT_ID "
				+ " LEFT JOIN T_CODE_DEPT_TEACH TCDTM ON TCDTM.CODE_=TS.MAJOR_ID "
				+ " LEFT JOIN T_CLASSES TCL ON TCL.NO_=TS.CLASS_ID";
		return sql;
	}
}
