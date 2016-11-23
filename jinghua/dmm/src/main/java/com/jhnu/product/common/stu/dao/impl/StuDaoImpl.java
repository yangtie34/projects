package com.jhnu.product.common.stu.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.common.stu.dao.StuDao;
import com.jhnu.product.common.stu.entity.LateDormStudent;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.util.common.StringUtils;
@Repository("stuDao")
public class StuDaoImpl implements StuDao {
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public int getStuCByEndYear(int endYear) {
		String sql ="SELECT COUNT(*) FROM T_STU WHERE (LENGTH_SCHOOLING+TO_NUMBER(ENROLL_GRADE))=?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, new Object[]{endYear}, Integer.class);
	}
	
	@Override
	public int getStuCByEndYearAndSexcode(int endYear, String sexCode) {
		String sql ="SELECT COUNT(*) FROM T_STU WHERE (LENGTH_SCHOOLING+TO_NUMBER(ENROLL_GRADE))=? AND SEX_CODE=?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, new Object[]{endYear,sexCode}, Integer.class);
	}
	
	@Override
	public int getStuCByEndYearAndMajor(int endYear, String majorId) {
		String sql ="SELECT COUNT(*) FROM T_STU WHERE (LENGTH_SCHOOLING+TO_NUMBER(ENROLL_GRADE))=? AND MAJOR_ID=?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, new Object[]{endYear,majorId}, Integer.class);
	}

	@Override
	public int getEndYearByStuId(String stuId) {
		String sql="select (st.Enroll_Grade+st.LENGTH_SCHOOLING) byn from t_stu st where st.no_=? ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql,new Object[]{stuId},Integer.class);
	}
	@Override
	public int getSchoolYearByStuId(String stuId) {
		String sql="select st.LENGTH_SCHOOLING from t_stu st where st.no_=? ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql,new Object[]{stuId},Integer.class);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Student> getStusByEndyear(int endYear) {
		String sql ="SELECT NO_,NAME_,IDNO FROM T_STU WHERE (LENGTH_SCHOOLING+TO_NUMBER(ENROLL_GRADE))=?";
		List<Student> stus = (List<Student>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Student.class), new Object[]{endYear});
		return stus;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Student> getStusGraduated() {
		String sql ="SELECT NO_,NAME_,IDNO FROM T_STU WHERE STU_STATE_CODE=4";
		List<Student> stus = (List<Student>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Student.class));
		return stus;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Student> getStusInSchool() {
		String sql ="SELECT NO_,NAME_,IDNO,MAJOR_ID MAJOR,DEPT_ID DEPT FROM T_STU WHERE STU_STATE_CODE IN('1','3')";
		List<Student> stus = (List<Student>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Student.class));
		return stus;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Student> getStusInSchoolNotInUser() {
		String sql ="SELECT NO_,NAME_,IDNO,DEPT_ID DEPT FROM T_STU STU WHERE NOT EXISTS (SELECT 1 FROM T_SYS_USER USER_ WHERE USER_.USERNAME = STU.NO_) AND STU.STU_STATE_CODE=1";
		List<Student> stus = (List<Student>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Student.class));
		return stus;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Student getStudentInfo(String stuId) {
		String sql ="SELECT STU.NO_,STU.NAME_,STU.IDNO,MAJOR.NAME_ MAJOR,DEPT.NAME_ DEPT,SEX.NAME_ SEX,STU.ENROLL_DATE ENROLLDATE,STU.CLASS_ID FROM T_STU STU "+
						" LEFT JOIN T_CODE_DEPT_TEACH MAJOR ON MAJOR.ID = STU.MAJOR_ID  "+
						" LEFT JOIN T_CODE_DEPT_TEACH DEPT ON DEPT.ID = STU.DEPT_ID "
						+ " LEFT JOIN T_CODE SEX ON SEX.CODE_ = STU.SEX_CODE AND SEX.CODE_TYPE='SEX_CODE' WHERE NO_= ?";
		List<Student> stus = (List<Student>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Student.class), new Object[]{stuId});
		return stus.size()==0?null:stus.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Student getStudentInfoByIdno(String stuIdNo) {
		String sql ="SELECT STU.NO_,STU.NAME_,STU.IDNO,MAJOR.NAME_ MAJOR,DEPT.NAME_ DEPT,SEX.NAME_ SEX,STU.ENROLL_DATE ENROLLDATE,STU.CLASS_ID FROM T_STU STU "+
				" LEFT JOIN T_CODE_DEPT_TEACH MAJOR ON MAJOR.ID = STU.MAJOR_ID  "+
				" LEFT JOIN T_CODE_DEPT_TEACH DEPT ON DEPT.ID = STU.DEPT_ID "
				+ " LEFT JOIN T_CODE SEX ON SEX.CODE_ = STU.SEX_CODE AND SEX.CODE_TYPE='SEX_CODE' WHERE STU.IDNO= ?";
		List<Student> stus = (List<Student>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Student.class), new Object[]{stuIdNo});
		return stus.size()==0?null:stus.get(0);
	}

	@Override
	public List<LateDormStudent> getLateDormStudents(LateDormStudent lds) {
		StringBuffer sql=new StringBuffer("select * from t_latedorm_stu where 1=1");
		if(lds!=null){
			if(StringUtils.hasText(lds.getStu_id())){
				sql.append(" and Stu_id='");
				sql.append(lds.getStu_id());
				sql.append("' ");
			}
			if(StringUtils.hasText(lds.getSex_code())){
				sql.append(" and sex_code='");
				sql.append(lds.getSex_code());
				sql.append("' ");
			}
			if(StringUtils.hasText(lds.getDept_id())){
				sql.append(" and dept_id='");
				sql.append(lds.getDept_id());
				sql.append("' ");
			}
			if(StringUtils.hasText(lds.getMajor_id())){
				sql.append(" and major_id='");
				sql.append(lds.getMajor_id());
				sql.append("' ");
			}
			if(StringUtils.hasText(lds.getClass_id())){
				sql.append(" and class_id='");
				sql.append(lds.getClass_id());
				sql.append("' ");
			}
			if(StringUtils.hasText(lds.getAction_date())){
				sql.append(" and action_date='");
				sql.append(lds.getAction_date());
				sql.append("' ");
			}
		}
		sql.append(" order by late_time ");
		List<LateDormStudent> ldsList = (List<LateDormStudent>) baseDao.getBaseDao().getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<LateDormStudent>(LateDormStudent.class));
		return ldsList;
	}

	@Override
	public void saveLateDormStudent(List<LateDormStudent> ldsList) {
		final int COUNT = ldsList.size();
		final List<LateDormStudent> LIST=ldsList;
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(
				"insert into t_latedorm_stu values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		        		LateDormStudent lsd=LIST.get(i);
		                ps.setString(1, lsd.getStu_id());    
		                ps.setString(2, lsd.getStu_name());    
		                ps.setString(3, lsd.getSex_code());    
		                ps.setString(4, lsd.getDept_id());  
		                ps.setString(5, lsd.getDept_name());    
		                ps.setString(6, lsd.getMajor_id());    
		                ps.setString(7, lsd.getMajor_name());    
		                ps.setString(8, lsd.getClass_id());  
		                ps.setString(9, lsd.getClass_name());    
		                ps.setString(10, lsd.getLate_time());    
		                ps.setString(11, lsd.getAddress()); 
		                ps.setString(12, lsd.getAction_date());    
		                ps.setString(13, lsd.getExe_time());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

	@Override
	public List<LateDormStudent> getLateDormStudentBytime(String startTime,String endTime,String action_date,String exe_time) {
		String sql="select stu_id,stu_name, sex_code,dept_id,dept_name,major_id,major_name,class_id,class_name,max(late_time) late_time,address,action_date,exe_time from "+
				"(select s.no_ stu_id,s.name_ stu_name,s.sex_code,cd.id dept_id,cd.name_ dept_name,cdt.id major_id,cdt.name_ major_name,c.no_ class_id,c.name_ class_name , "+
				"dr.time_ late_time,dr.address ,? action_date ,? exe_time "+
				"from T_DORM_RKE dr inner join T_DORM_PROOF dp on dr.dorm_proof_id=dp.no_ inner join t_stu s on dp.people_id=s.no_ "+
				"left join t_code_dept cd on s.dept_id=cd.id  "+
				"left join t_code_dept_teach cdt on s.major_id=cdt.id "+
				"left join t_classes c on s.class_id=c.no_ "+
				"where (s.LENGTH_SCHOOLING+s.ENROLL_GRADE)>to_number(to_char(sysdate,'yyyy')) and dr.time_ between ? and ? ) "+
				"group by stu_id,stu_name, sex_code,dept_id,dept_name,major_id,major_name,class_id,class_name,address,action_date,exe_time ";
		List<LateDormStudent> ldsList = (List<LateDormStudent>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper<LateDormStudent>(LateDormStudent.class), new Object[]{action_date,exe_time,startTime,endTime});
		return ldsList;
	}

	@Override
	public boolean isInClassForStudent(String stuId,String classes) {
		String sql="select t.* from t_stu t where t.no_=? and t.class_id in ("+classes+") ";
		List<Map<String,Object>> list=baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{stuId});
		return list.size()>0?true:false;
	}
}
