package cn.gilight.product.common.teacher.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.product.common.teacher.dao.TeacherDao;
import cn.gilight.product.common.teacher.entity.Teacher;


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
		List<Teacher> teas = (List<Teacher>) baseDao.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Teacher.class), new Object[]{teacherNo});
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
		List<Teacher> teas = (List<Teacher>) baseDao.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Teacher.class), new Object[]{classId,year,term});
		return teas.size()==0?null:teas.get(0);
	}

	@Override
	public boolean isInDeptForTeacher(String teaId, String depts) {
		String sql="select t.* from t_tea t where t.tea_no=? and t.dept_id in (?) ";
		List<Map<String,Object>> list=baseDao.getJdbcTemplate().queryForList(sql,new Object[]{teaId,depts});
		return list.size()>0?true:false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Teacher> getTeasInSchoolNotInUser() {
		String sql ="SELECT TEA_NO NO_,NAME_,IDNO,DEPT_ID deptName FROM T_TEA TEA WHERE NOT EXISTS (SELECT 1 FROM T_SYS_USER USER_ WHERE USER_.USERNAME = TEA.TEA_NO) ";//AND TEA.TEA_STATE_CODE IN('1','3')";
		List<Teacher> teas = (List<Teacher>) baseDao.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Teacher.class));
		return teas;
	}
}
