package cn.gilight.dmm.xg.dao.impl;

/**
 * 学费减免
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.xg.dao.FeeRemissionDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.product.EduUtils;
@Repository("feeRemissionDao")
public class FeeRemissionDaoImpl implements FeeRemissionDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;	
	/**
	 * 得到学年及学生类别（本科，专科，本专科）
	 */
	@Override
	public List<Integer> querySchoolYear() {
		// 得到学年
//		String sql="SELECT DISTINCT TSJ.SCHOOL_YEAR FROM T_STU_JM TSJ";	
		List<Integer> list =new ArrayList<>();
		Integer minYear = businessDao.queryMinSchoolYear("T_STU_JM","SCHOOL_YEAR");//得到最小学年
		int year = Integer.valueOf(EduUtils.getSchoolYear4());//得到当前学年
		for(int i=0;i<=year-minYear;i++){
			list.add(minYear+i);
		}
		return list;
	}
	/**
	 * 得到总的学费减免人数以及金额
	 */
	@Override
	public List<Map<String, Object>> queryJmInfo(List<String> deptList,String schoolYear, String edu,List<AdvancedParam> advancedList) {
//		String edu="";
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(Integer.valueOf(schoolYear.substring(0, 4)), deptList, advancedList);
		// 人数 总金额  人均金额  覆盖率
		String sql="SELECT T1.RS,T1.ZMONEY_,T1.MONEY_,ROUND(T1.RS/T2.COUNT_,2)*100 FGL "
				+"  FROM (SELECT COUNT(DISTINCT TSJ.STU_ID) RS,SUM(TSJ.MONEY) ZMONEY_,ROUND(SUM(TSJ.MONEY)/COUNT(*),2) MONEY_ "
				+"  FROM (SELECT * FROM  T_STU_JM TSJL LEFT JOIN ("+stuSql+") TSS ON TSS.NO_=TSJL.STU_ID WHERE TSJL.SCHOOL_YEAR='"+schoolYear+"' AND TSJL.STU_ID IS NOT NULL) TSJ "
				+"  LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSJ.STU_ID "
//				+"  WHERE TSJ.SCHOOL_YEAR='"+schoolYear+"'"
				+"  WHERE (TSJ.DEPT_ID=TS.DEPT_ID "
				+" OR TSJ.MAJOR_ID = TS.MAJOR_ID)   "
				+" ) T1, "
				+"  (SELECT COUNT(*) COUNT_ FROM ("+stuSql+") TS) T2";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 学费减免分布
	 */
	@Override
	public String queryJmfb(List<String> deptList,List<AdvancedParam> advancedList,int year) {
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String sql="SELECT TS.NO_ NO,TS.MAJOR_ID,TS.DEPT_ID,TS.NAME_ NAME,TSJ.SCHOOL_YEAR,TCC.NAME_ SEXMC,TCDT.NAME_ DEPTMC,TCDTM.NAME_ MAJORMC ,TCL.NAME_ CLASSMC ,TSJ.TYPEMC TYPEMC,TSJ.MONEY MONEY " 
				+ " FROM (SELECT DISTINCT(T.STU_ID),T.SCHOOL_YEAR ,TC.NAME_ TYPEMC,T.MONEY MONEY FROM ( "
				+ "         SELECT * FROM T_STU_JM TSJ WHERE SUBSTR(TSJ.SCHOOL_YEAR,0,4)='"+year+"' "
				+ "        ) T LEFT JOIN T_CODE TC ON TC.CODE_=T.JM_CODE AND TC.CODE_TYPE='JM_CODE') TSJ "
				+ " LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSJ.STU_ID "
				+ " LEFT JOIN T_CODE TCC ON TCC.CODE_=TS.SEX_CODE AND TCC.CODE_TYPE='SEX_CODE' "
				+ " LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.CODE_=TS.DEPT_ID "
				+ " LEFT JOIN T_CODE_DEPT_TEACH TCDTM ON TCDTM.CODE_=TS.MAJOR_ID "
				+ " LEFT JOIN T_CLASSES TCL ON TCL.NO_=TS.CLASS_ID";
		return sql;
//		String sql="";
////		String id_=advancedList.get(1).getValues();
//		// 某学年学生数据sql
//		String stuSql = businessDao.getStuSql(schoolYear, deptList, advancedList);	
//		if("count".equals(fb)){//按人数查询
//			//  减免类型  学生人数
//			sql="SELECT T.TYPEMC TNAME_ ,COUNT(DISTINCT T.NO) COUNT_  FROM ("+getXfjmStuSql(deptList, advancedList, schoolYear)+") T WHERE T.NO IS NOT NULL AND (T.DEPT_ID='"+id_+"' OR T.MAJOR_ID='"+id_+"') GROUP BY T.TYPEMC";
//		}else if("money".equals(fb)){//按金额查询
//			// 院系名称 减免类型  减免金额
//			 sql="SELECT TCDT.NAME_ NAME_,TC.NAME_ TNAME_,SUM(TSJ.MONEY) COUNT_"
//						+" FROM (SELECT * FROM  T_STU_JM TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSJ "
//						+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSJ.STU_ID "
//						+" LEFT JOIN  T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.DEPT_ID "
//						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSJ.JM_CODE AND TC.CODE_TYPE='JM_CODE'"
////						+" WHERE TS.DEPT_ID= "+id_+ " "
////						+" AND SUBSTR(TSJ.SCHOOL_YEAR,0,4)="+schoolYear
//						+" WHERE SUBSTR(TSJ.SCHOOL_YEAR,1,4)="+schoolYear
//						+" AND (TSJ.DEPT_ID=TS.DEPT_ID "
//						+" OR TSJ.MAJOR_ID = TS.MAJOR_ID)"
//						+" GROUP BY TCDT.NAME_ ,TC.NAME_";	
//		}else if("scale".equals(fb)){//按覆盖率查询
//			// 院系名称 减免类型  减免金额
//			 sql= " SELECT T1.NAME_ NAME_,T1.TNAME_ TNAME_,ROUND(T1.COUNT_/T2.COUNT_,3)*100 COUNT_ "
//					    +" FROM(SELECT TCDT.NAME_ NAME_,TC.NAME_ TNAME_,COUNT(*) COUNT_"
//						+" FROM (SELECT * FROM  T_STU_JM TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSJ "
//						+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSJ.STU_ID "
//						+" LEFT JOIN  T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.DEPT_ID "
//						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSJ.JM_CODE AND TC.CODE_TYPE='JM_CODE'"
//						//+" WHERE TS.DEPT_ID= "+id_+ " "
//						//+" AND SUBSTR(TSJ.SCHOOL_YEAR,0,4)="+schoolYear
//						+" WHERE SUBSTR(TSJ.SCHOOL_YEAR,1,4)="+schoolYear
//						+" AND (TSJ.DEPT_ID=TS.DEPT_ID "
//						+" OR TSJ.MAJOR_ID = TS.MAJOR_ID)"
//						+" GROUP BY TCDT.NAME_ ,TC.NAME_) T1 ,"
//						+" (SELECT COUNT(*) COUNT_ FROM ("+stuSql+")  TS) T2";	
//		}
//		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 学费减免分布(班级)
	 */
	@Override
	public List<Map<String, Object>> queryBjjmfb(int schoolYear,List<String> deptList,List<AdvancedParam> advancedList, String fb) {
		String sql="";
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(schoolYear, deptList, advancedList);	
		if("count".equals(fb)){//按人数查询
			// 院系名称 减免类型  学生人数
			sql="SELECT TCDT.NAME_ NAME_,TC.NAME_ TNAME_,COUNT(TSJ.STU_ID) COUNT_"
					+" FROM (SELECT * FROM  T_STU_JM TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSJ "
					+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSJ.STU_ID "
					+" LEFT JOIN  T_CLASSES TCDT ON TCDT.teach_dept_id=TSJ.MAJOR_ID "
					+" LEFT JOIN T_CODE TC ON TC.CODE_=TSJ.JM_CODE AND TC.CODE_TYPE='JM_CODE'"
					+" WHERE SUBSTR(TSJ.SCHOOL_YEAR,1,4)="+schoolYear
					+" AND TCDT.NO_=TSJ.CLASS_ID AND TCDT.GRADE="+schoolYear
					+" GROUP BY TCDT.NAME_ ,TC.NAME_";	
		}else if("money".equals(fb)){//按金额查询
			// 院系名称 减免类型  减免金额
			sql="SELECT TCDT.NAME_ NAME_,TC.NAME_ TNAME_,SUM(TSJ.MONEY) COUNT_"
					+" FROM (SELECT * FROM  T_STU_JM TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSJ "
					+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSJ.STU_ID "
					+" LEFT JOIN  T_CLASSES TCDT ON TCDT.teach_dept_id=TSJ.MAJOR_ID "
					+" LEFT JOIN T_CODE TC ON TC.CODE_=TSJ.JM_CODE AND TC.CODE_TYPE='JM_CODE'"
					+" WHERE SUBSTR(TSJ.SCHOOL_YEAR,1,4)="+schoolYear
					+" AND TCDT.NO_=TSJ.CLASS_ID AND TCDT.GRADE="+schoolYear
					+" GROUP BY TCDT.NAME_ ,TC.NAME_";	
		}else if("scale".equals(fb)){//按覆盖率查询
			// 院系名称 减免类型  减免金额
			sql= " SELECT T1.NAME_ NAME_,T1.TNAME_ TNAME_,ROUND(T1.COUNT_/T2.COUNT_,3)*100 COUNT_ "
					+" FROM(SELECT TCDT.NAME_ NAME_,TC.NAME_ TNAME_,COUNT(*) COUNT_"
					+" FROM (SELECT * FROM  T_STU_JM TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSJ "
					+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSJ.STU_ID "
					+" LEFT JOIN  T_CLASSES TCDT ON TCDT.teach_dept_id=TSJ.MAJOR_ID "
					+" LEFT JOIN T_CODE TC ON TC.CODE_=TSJ.JM_CODE AND TC.CODE_TYPE='JM_CODE'"
					+" WHERE SUBSTR(TSJ.SCHOOL_YEAR,1,4)="+schoolYear
					+" AND TCDT.NO_=TSJ.CLASS_ID AND TCDT.GRADE="+schoolYear
					+" GROUP BY TCDT.NAME_ ,TC.NAME_) T1 ,"
					+" (SELECT COUNT(*) COUNT_ FROM ("+stuSql+")  TS) T2";	
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 查询历年学费减免变化
	 */
	@Override
	public List<Map<String, Object>> queryYearJmfb(List<String> deptList,int year,String bh,List<AdvancedParam> advancedList, String edu) {
		String sql="";
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		if("count".equals(bh)){//按人数查询
			//减免类型 人数
			sql="SELECT T.TYPEMC TNAME_ ,COUNT(DISTINCT T.NO) COUNT_  FROM ("+getXfjmStuSql(deptList, advancedList, year)+") T WHERE T.NO IS NOT NULL  GROUP BY T.TYPEMC";
		}else if("money".equals(bh)){
			//减免类型 金额
			 sql="SELECT TC.NAME_ TNAME_ ,SUM(TSJ.MONEY) COUNT_ "
						+" FROM (SELECT * FROM  T_STU_JM TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSJ "
						+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSJ.STU_ID "
						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSJ.JM_CODE AND TC.CODE_TYPE='JM_CODE'  "
						+" WHERE SUBSTR(TSJ.SCHOOL_YEAR,1,4)="+year
						+" AND (TSJ.DEPT_ID=TS.DEPT_ID "
						+" OR TSJ.MAJOR_ID = TS.MAJOR_ID) "
						+" GROUP BY TC.NAME_ ";
		}else if("scale".equals(bh)){
			//减免类型 覆盖率
			 sql= "SELECT T1.TNAME_ TNAME_,ROUND(T1.COUNT_/T2.COUNT_,2)*100 COUNT_ " 
					    +" FROM(SELECT TC.NAME_ TNAME_ ,COUNT(*) COUNT_ "
						+" FROM (SELECT * FROM  T_STU_JM TSJL LEFT JOIN T_STU TSS ON TSS.NO_=TSJL.STU_ID) TSJ "
						+" LEFT JOIN ("+stuSql+")  TS ON TS.NO_=TSJ.STU_ID "
						+" LEFT JOIN T_CODE TC ON TC.CODE_=TSJ.JM_CODE AND TC.CODE_TYPE='JM_CODE' "//+pid+" "
						+" WHERE SUBSTR(TSJ.SCHOOL_YEAR,1,4)="+year
						+" AND (TSJ.DEPT_ID=TS.DEPT_ID "
						+" OR TSJ.MAJOR_ID = TS.MAJOR_ID) "
						+" GROUP BY TC.NAME_) T1,"
						+" (SELECT COUNT(*) COUNT_ FROM ("+stuSql+")  TS) T2";			
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 得到学费减免类型
	 */
	@Override
	public List<Map<String, Object>> queryJmType() {
		// 学费减免类型
		String sql="SELECT TC.NAME_ TNAME_ FROM T_CODE TC WHERE TC.CODE_TYPE = 'JM_CODE' ";
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
	 * 得到学费减免学生的详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	@Override
	public String getXfjmStuSql(List<String> deptList,List<AdvancedParam> advancedList,int year){
		String stuSql = businessDao.getStuSql(year, deptList, advancedList);
		String sql="SELECT TS.NO_ NO,TS.MAJOR_ID,TS.DEPT_ID,TS.NAME_ NAME,TSJ.SCHOOL_YEAR,TCC.NAME_ SEXMC,TCDT.NAME_ DEPTMC,TCDTM.NAME_ MAJORMC ,TCL.NAME_ CLASSMC ,TSJ.TYPEMC TYPEMC,TSJ.MONEY MONEY " 
				+ " FROM (SELECT DISTINCT(T.STU_ID),T.SCHOOL_YEAR ,wm_concat(TC.NAME_) TYPEMC,wm_concat(T.MONEY) MONEY FROM ( "
				+ "         SELECT * FROM T_STU_JM TSJ WHERE SUBSTR(TSJ.SCHOOL_YEAR,0,4)='"+year+"' "
				+ "        ) T LEFT JOIN T_CODE TC ON TC.CODE_=T.JM_CODE AND TC.CODE_TYPE='JM_CODE' GROUP BY T.STU_ID,T.SCHOOL_YEAR) TSJ "
				+ " LEFT JOIN ("+stuSql+") TS ON TS.NO_=TSJ.STU_ID "
				+ " LEFT JOIN T_CODE TCC ON TCC.CODE_=TS.SEX_CODE AND TCC.CODE_TYPE='SEX_CODE' "
				+ " LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.CODE_=TS.DEPT_ID "
				+ " LEFT JOIN T_CODE_DEPT_TEACH TCDTM ON TCDTM.CODE_=TS.MAJOR_ID "
				+ " LEFT JOIN T_CLASSES TCL ON TCL.NO_=TS.CLASS_ID";
		return sql;
	}
}

