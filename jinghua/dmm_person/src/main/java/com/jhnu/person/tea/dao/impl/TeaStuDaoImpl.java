package com.jhnu.person.tea.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.tea.dao.TeaStuDao;
import com.jhnu.system.common.page.Page;

@Repository("teaStuDao")
public class TeaStuDaoImpl implements TeaStuDao {
	@Autowired
	private BaseDao baseDao;
	
	public String getGradeIds(String teaId,String gradeId){
		String gradeIds="'" + gradeId + "'";
		String sql="";
		if(gradeId.equals("")){
			gradeIds=getGrade(teaId,1).replace(", tc.name_", "");;
		}
		sql=" in (" + gradeIds + ")";
		return sql;
	}
	public String getteaGradeIds(String teaId,String teachId){
		String gradeIds="select t.CLASS_ID id "
				+ " from T_CLASS_TEACHING_XZB t "
				+ "  where  t.TEACH_CLASS_ID ='" + teachId + "'";
		String sql="";
		if(teachId.equals("")){
			gradeIds=getGrade(teaId,1).replace(", tc.name_", "");
		}
		sql=" in (" + gradeIds + ")";
		return sql;
	}
	public String getClasss(String teaId,String gradeId,int i){//0 教学班 1行政班
		String sql="";
		if(i==0){
			sql=getteaGradeIds(teaId, gradeId);
		}else{
			sql=getGradeIds(teaId, gradeId);
		}
		return sql;
	}
	public String getGrade(String id,int i) {
		String sql ="";
		if(i==0){
			
		sql="select distinct t.teachingclass_id id, tc.name_ "
				+ " from t_course_arrangement t "
				+ " left join t_class_teaching tc   on  t.teachingclass_id=tc.code_  "
				+ "  where  t.tea_id='" + id + "'";
		}else{
			sql="select distinct tc.no_ id, tc.name_  "
					+ " from t_classes_instructor t "
					+ " left join T_CLASSES tc on tc.no_=t.class_id "
					+ " where t.tea_id='" + id + "'";
			/* sql="select distinct tc.no_ id, tc.name_ "
					  +" from t_course_arrangement t "
					  +"  left join T_CLASS_TEACHING_XZB TCTX ON  TCTX.TEACH_CLASS_ID=T.TEACHINGCLASS_ID "
					  +"  left join T_CLASSES tc on tc.no_=tctx.class_id "
					  +"  where t.tea_id = '"+id+"' and tc.no_ is not null";*/
		}
		return sql;
		
	}
	public String getxbbl(String teaId,String gradeId,int i) {
		String sql= "SELECT count(s.no_) value ,tc1.name_ field "
				+ " FROM T_STU s   "
				+ " left join t_code tc1 on tc1.code_ = s.sex_code  and tc1.code_type='SEX_CODE'"
				+" where s.class_id" + getClasss(teaId, gradeId,i)
				+ " group by tc1.name_";
		return sql;
	}
	public String getqjxbbl(String teaId,String gradeId,int i) {
		String sql="SELECT TC1.NAME_ feild ,COUNT(TC1.NAME_) value "
				+"  FROM T_STU_LEAVE TSL "
				+"  LEFT JOIN T_STU TS ON TS.NO_=TSL.STU_ID AND  TS.class_id"  + getClasss(teaId, gradeId,i)
				+"  left join t_code tc1 on tc1.code_ = ts.sex_code  and tc1.code_type='SEX_CODE' "
				+"  GROUP BY TC1.NAME_";
		return sql;
	}
	
	//--------------------------------------------------------
	@Override
	public Page stuxzbxx(String teaId,String gradeId, int currentPage, int numPerPage) {
		String sql = "SELECT s.id,s.no_ ,s.name_,tc1.name_ SEX, d.name_ DEPT ,x.name_ GRADE  "
				+ " FROM T_STU s   "
				+ " left join t_code tc1 on tc1.code_ = s.sex_code  and tc1.code_type='SEX_CODE'   "
				+ " left join t_code_dept d on to_number(d.code_)=s.dept_id  "
				+ " left join T_CLASSES x on x.no_=s.class_id  ";
			sql+=" where s.class_id" + getClasss(teaId, gradeId,0);
		return new Page(sql.toString(), currentPage, numPerPage, baseDao
				.getBaseDao().getJdbcTemplate(), null);
	}
	@Override
	public List<Map<String, Object>> stuxbbl(String teaId,String gradeId) {
		String sql =getxbbl(teaId, gradeId,1);
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List stuxzGrade(String id) {
		String sql = getGrade(id,0);
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Map getStuxx(String stuId) {
		return null;
	}
	/**
	 * 学生管理 --教学班级信息
	 */
	@Override
	public List jxStuGrade(String id) {
		String sql=getGrade(id,1);
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 学生管理-- 学生学科通过率
	 */
	@Override
	public List jxStutgl(String teaId,String gradeId) {
		if(gradeId.equalsIgnoreCase("")){
			gradeId="";
		}else{
			gradeId="and tcap.teachingclass_id='"+gradeId+"'";
		}
		// 课程名 通过率
		String sql=" SELECT TCL.NAME_ field ,decode(COUNT(TSS.STU_ID),0,0,ROUND(COUNT(CASE WHEN TSS.CENTESIMAL_SCORE>=60 THEN 1 END )/COUNT(TSS.STU_ID),2)*100) value ,'通过率' name" 
				 +"  FROM  T_COURSE TCL "
				 +"  inner JOIN T_COURSE_ARRANGEMENT TCAP ON TCL.CODE_ = TCAP.Course_Id"
				 + "        and tcap.tea_id='"+teaId+"' "+gradeId
				 +"  inner JOIN T_STU_SCORE TSS ON  TSS.COURE_CODE=TCL.CODE_  "
				 +"  GROUP BY TCL.NAME_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 学生管理--全部人数性别通过率
	 */
	@Override
	public List<Map<String, Object>> jxStuxbbl(String teaId,String gradeId) {
		String sql=getxbbl(teaId, gradeId,1);
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 学生管理--请假人数比例
	 */
	@Override
	public List<Map<String, Object>> jxStuqjxb(String teaId,String gradeId) {
		// 人数 性别
		String sql=getqjxbbl(teaId, gradeId,0);
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 获取教学学生信息
	 */
	@Override
	public Page getjxStuxx(String teaId,String gradeId,int currentPage, int numPerPage) {
		//  学号 姓名 性别 班级  联系电话 通过 挂科 请假
		String sql="SELECT DISTINCT TL.NO_ NO_ ,TL.NAME_ NAME_,TL.SEX_  SEX_,TL.CLASS_ CLASS_ ,TL.TEL TEL_,TLL.TG TG,TLL.GK GK,TLB.QJ QJ "
				+"  FROM (SELECT TS.NO_ ,TS.NAME_ ,TC1.NAME_ SEX_,TSC.TEL,TC.NAME_ CLASS_ "
				+"  FROM T_CLASSES TC "
				+"  LEFT JOIN T_STU TS ON TS.CLASS_ID=TC.NO_ "
				+"  left join t_code tc1 on tc1.code_ = ts.sex_code  and tc1.code_type='SEX_CODE' "
				+"  LEFT JOIN T_STU_COMM TSC ON TSC.STU_ID=TS.NO_ "
				+"  WHERE TC.NO_" + getClasss(teaId, gradeId,1) 
				+ ") TL "
				+"  LEFT JOIN  "
				+"  (SELECT  "
				+"  TSS.STU_ID, "
				+"  COUNT(CASE WHEN TSS.CENTESIMAL_SCORE>=60 THEN 1 END) TG, "
				+"  COUNT(CASE WHEN TSS.CENTESIMAL_SCORE<60 THEN 1 END)  GK "
				+"  FROM T_STU_SCORE TSS  "
				+"  RIGHT JOIN T_STU TS  ON TS.NO_=TSS.STU_ID AND TS.class_id" + getClasss(teaId, gradeId,1)
				+"  GROUP BY TSS.STU_ID )  TLL ON TL.NO_=TLL.STU_ID "
				+"  LEFT JOIN  "
				+"  (SELECT TSL.STU_ID,COUNT(TSL.STU_ID) QJ "
				+"  FROM T_STU TS "
				+"  LEFT JOIN T_STU_LEAVE TSL ON TSL.STU_ID=TS.NO_ "
				+"  GROUP BY TSL.STU_ID) TLB ON TLB.STU_ID = TL.NO_";
		return new Page(sql.toString(), currentPage, numPerPage, baseDao
				.getBaseDao().getJdbcTemplate(), null);
	}
	/**
	 * 获得行政学生信息明细
	 */
	@Override
	public List getStuxxmx(String id) {
		//获取个人信息
		//String sql="SELECT TS.NAME_ 姓名,tc1.name_ 性别,tc3.name_ 学籍状态,TCDTL.NAME_ 院系,TCDT.NAME_ 专业,TCLL.NAME_ 班级 "
		String sql="SELECT TS.NAME_ cl01,tc1.name_ cl02,tc3.name_ cl03,TCDTL.NAME_ cl04,TCDT.NAME_ cl05,TCLL.NAME_ cl06 "
				+"  FROM T_STU TS  "
				+ " left join t_code tc1 on tc1.code_ = ts.sex_code  and tc1.code_type='SEX_CODE'  "
				+ " left join t_code tc3 on tc3.code_ = ts.STU_ROLL_CODE  and tc3.code_type='STU_ROLL_CODE' "
				+"  LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.MAJOR_ID "
				+"  LEFT JOIN T_CODE_DEPT_TEACH TCDTL ON TCDTL.ID=TS.DEPT_ID "
				+"  LEFT JOIN T_CLASSES TCLL ON TCLL.NO_=TS.CLASS_ID "
				+"  WHERE TS.NO_='"+id+"'";
		List list=new ArrayList();
		Map map=new HashMap();
		map.put("xx", baseDao.getBaseDao().getJdbcTemplate().queryForList(sql));
		// 成绩明细
		//姓名 院系 专业 学年 学期 课程名 综合成绩 等级成绩
		sql="SELECT DISTINCT "//TS.NAME_ 姓名,TCDTL.NAME_ 院系,TCDT.NAME_ 专业,TCLL.NAME_ 班级  ,"
				//+ "TSS.SCHOOL_YEAR 学年, TC1.NAME_ 学期,TC.NAME_ 课程,TSS.CENTESIMAL_SCORE 综合成绩,TCLA.THEORY_CREDIT  学分 "
				+ "TSS.SCHOOL_YEAR cl01, TC1.NAME_ cl02,TC.NAME_ cl03,TSS.CENTESIMAL_SCORE cl04,TC.THEORY_CREDIT  cl05 "
				+"  FROM T_STU_SCORE TSS  "
				+"  LEFT JOIN T_COURSE TC ON TC.CODE_=TSS.COURE_CODE  "
				+"  LEFT JOIN T_CODE TC1 ON TC1.CODE_TYPE='TERM_CODE' AND TSS.TERM_CODE=TC1.CODE_ "
				+"  LEFT JOIN T_STU TS ON TS.NO_=TSS.STU_ID "
				+"  LEFT JOIN T_CODE_DEPT_TEACH TCDT ON TCDT.ID=TS.MAJOR_ID "
				+"  LEFT JOIN T_CODE_DEPT_TEACH TCDTL ON TCDTL.ID=TS.DEPT_ID "
				+"  LEFT JOIN T_CLASSES TCLL ON TCLL.NO_=TS.CLASS_ID "
				+"  LEFT JOIN T_COURSE_ARRANGEMENT_PLAN TCAP ON TCAP.CLASS_ID=TCLL.NO_ "
				//+"  LEFT JOIN T_COURSE TCLA ON TCLA.CODE_=TCAP.COURSE_CODE "
				+"  WHERE TSS.STU_ID='"+id+"' ORDER BY TSS.SCHOOL_YEAR";
		
		map.put("cj", baseDao.getBaseDao().getJdbcTemplate().queryForList(sql));
		//请假明细
		//开始时间 结束时间 请假天数 请假原因
		 sql="SELECT TSL.START_TIME cl01,TSL.END_TIME cl02 , "
			+"	 TO_DATE(TSL.END_TIME,'yyyy-mm-dd')-TO_DATE(TSL.START_TIME,'yyyy-mm-dd') cl03, "
			+"	 TSL.LEAVE_REASON cl04"
			+"	 FROM T_STU_LEAVE TSL "
			+"	 WHERE TSL.STU_ID='"+id+"'";
		 map.put("qj", baseDao.getBaseDao().getJdbcTemplate().queryForList(sql));
		 list.add(map);
		return list;
	}
	@Override
	public List<Map<String, Object>> xzStuqjxb(String teaId, String gradeId) {
		String sql=getqjxbbl(teaId, gradeId,1);
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	

}
