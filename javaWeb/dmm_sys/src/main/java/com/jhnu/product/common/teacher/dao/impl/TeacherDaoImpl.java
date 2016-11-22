package com.jhnu.product.common.teacher.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.teacher.dao.TeacherDao;
import com.jhnu.product.common.teacher.entity.Teacher;

@Repository("teacherDao")
public class TeacherDaoImpl implements TeacherDao{
	@Autowired
	private BaseDao baseDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Teacher getTeacherInfo(String teacherNo) {
		String sql="SELECT TEA.TEA_NO NO_,TEA.NAME_,TEA.IDNO,DEPT.NAME_ DEPTNAME,SEX.NAME_ SEX FROM T_TEA TEA "+
						" LEFT JOIN T_CODE_DEPT DEPT ON DEPT.ID = TEA.DEPT_ID "
						+ " LEFT JOIN T_CODE SEX ON SEX.CODE_ = TEA.SEX_CODE AND SEX.CODE_TYPE='SEX_CODE' WHERE TEA_NO=?";
		List<Teacher> teas = (List<Teacher>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Teacher.class), new Object[]{teacherNo});
		return teas.size()==0?null:teas.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Teacher getInstructorInfo(String classId, String year, String term) {
		String sql="SELECT TEA.TEA_NO NO_,TEA.NAME_,TEA.IDNO,DEPT.NAME_ DEPTNAME,SEX.NAME_ SEX FROM T_TEA TEA "+
				" inner join T_CLASSES_INSTRUCTOR ci on tea.tea_no=ci.tea_id "+
				" LEFT JOIN T_CODE_DEPT DEPT ON DEPT.ID = TEA.DEPT_ID  "+
				" LEFT JOIN T_CODE SEX ON SEX.CODE_ = TEA.SEX_CODE AND SEX.CODE_TYPE='SEX_CODE' "+
				" where ci.class_id=? and ci.school_year=? and ci.term_code=? ";
		List<Teacher> teas = (List<Teacher>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Teacher.class), new Object[]{classId,year,term});
		return teas.size()==0?null:teas.get(0);
	}

	@Override
	public boolean isInDeptForTeacher(String teaId,List lists) {
		String andsql="";
		if(lists.get(0)!=null){
			return true;
		}else if(lists.get(1)!=null){
			andsql+=" and t.DEPT_ID in ("+lists.get(1)+")";
		}
		/*else if(lists.get(2)!=null){
			andsql+=" and t.SUBJECT_ID in ("+lists.get(2)+")";
		}else if(lists.get(3)!=null){
			andsql+=" and t.CLASS_ID in ("+lists.get(3)+")";
		}*/
		String sql="select t.* from t_tea t where t.tea_no=? "+andsql
				;
		List<Map<String,Object>> list=baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{teaId});
		return list.size()>0?true:false;
	}

	@Override
	public List<Teacher> getTeasInSchoolNotInUser() {
		String sql ="SELECT TEA_NO NO_,NAME_,IDNO,DEPT_ID deptName FROM T_TEA TEA WHERE NOT EXISTS (SELECT 1 FROM T_SYS_USER USER_ WHERE USER_.USERNAME = TEA.TEA_NO) ";//AND TEA.TEA_STATE_CODE IN('1','3')";
		List<Teacher> teas = (List<Teacher>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Teacher.class));
		return teas;
	}
}
