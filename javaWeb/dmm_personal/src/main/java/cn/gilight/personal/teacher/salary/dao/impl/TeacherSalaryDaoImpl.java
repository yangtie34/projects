package cn.gilight.personal.teacher.salary.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.teacher.salary.dao.TeacherSalaryDao;

@Repository("teacherSalaryDao")
public class TeacherSalaryDaoImpl implements TeacherSalaryDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getLastSalary(String tea_id, String year,
			String month) {
		String sql = "select t.salary lastsalary,t.year_ ,t.month_ from t_tea_salary t where t.tea_id = '"+ tea_id +"' and t.year_ = '"+ year +"' and t.month_ = '"+ month +"'";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getTotalSalary(String tea_id) {
		String sql = "select sum(t.salary) totalSalary from t_tea_salary t where t.tea_id = '"+ tea_id +"' group by t.tea_id";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getSalaryCompose(String tea_id) {
		String sql = "select t.salary_type salary_type ,sum(t.value_) salary from t_tea_salary_detail t "
				+ " left join t_tea_salary sa on sa.id = t.salary_id where sa.tea_id = '"+ tea_id +"' group by t.salary_type";
		return  baseDao.queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getYears(String tea_id) {
		String sql = "select t.year_ from t_tea_salary t where t.tea_id = '"+tea_id+"' group by t.year_ order by year_ desc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String,Object>> getHistorySalary(String tea_id,int year_) {
		String sql = "select t.year_ ,t.month_,t.time_, sum(t.salary) money from t_tea_salary t  where t.tea_id = '"+ tea_id +"' and t.year_ = '"+ year_+"' group by year_,month_ ,time_ order by month_ desc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getRetireYear(String tea_id) {
		String sql = "select substr(tea.birthday,0,4)+55-to_char(SYSDATE,'yyyy') years from  t_tea tea where tea.tea_no = '"+ tea_id +"'";
		return baseDao.queryForList(sql);
	}
	
	@Override
	public List<Map<String,Object>> getFiveYearSalary(String tea_id){
		String sql = "select r.year_,r.avg_salary from ("
				+ " select s.year_,s.avg_salary,rownum rm from ("
				+ " select t.year_,round(sum(t.salary)/count(t.month_),2) avg_salary from t_tea_salary t"
				+ " where t.tea_id = '"+ tea_id +"' group by t.year_ order by year_ ) s ) r where r.rm <= 5";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getLastSalaryCom(String tea_id,
			String year, String month) {
		String sql = "select t.salary_payable,t.salary_subtract,t.salary from t_tea_salary t where t.tea_id = '"+ tea_id +"' and t.year_ = '"+ year +"' and t.month_ = '"+ month +"'";
		return baseDao.queryForList(sql);
	}
	
	@Override
	public List<Map<String,Object>> getLastSalaryPayable(String tea_id,String year,String month){
		String sql = "select t.salary_type, t.value_ from t_tea_salary_detail t left join t_tea_salary sa on sa.id = t.salary_id "
				+ " where sa.tea_id = '"+ tea_id +"' and sa.year_ = '"+ year +"' and sa.month_ = '"+ month +"' and t.flag = '+'";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getLastSalaryTotal(String tea_id,
			String year, String month) {
		String sql = "select t.salary_id ,sum(t.value_) total_salary from t_tea_salary_detail t "
				+ " left join t_tea_salary sa on sa.id = t.salary_id "
				+ " where sa.tea_id = '"+ tea_id +"' and sa.year_ = '"+ year +"' and sa.month_ = '"+ month +"' and t.flag = '+' group by t.salary_id";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getLastSalarySubtract(String tea_id,
			String year, String month) {
		String sql = "select t.salary_type, t.value_ from t_tea_salary_detail t left join t_tea_salary sa on sa.id = t.salary_id "
				+ " where sa.tea_id = '"+ tea_id +"' and sa.year_ = '"+ year +"' and sa.month_ = '"+ month +"' and t.flag = '-'";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getLastSalarySubtractTotal(String tea_id,
			String year, String month) {
		String sql = "select t.salary_id ,sum(t.value_) subtract_salary from t_tea_salary_detail t "
				+ " left join t_tea_salary sa on sa.id = t.salary_id "
				+ " where sa.tea_id = '"+ tea_id +"' and sa.year_ = '"+ year +"' and sa.month_ = '"+ month +"' and t.flag = '-' group by t.salary_id";
		return baseDao.queryForList(sql);
	}

	

}
