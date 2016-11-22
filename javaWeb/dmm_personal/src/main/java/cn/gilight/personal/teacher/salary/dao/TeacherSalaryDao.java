package cn.gilight.personal.teacher.salary.dao;

import java.util.List;
import java.util.Map;

public interface TeacherSalaryDao {

	/**
	 * 获取某年某月薪资
	 * @param tea_id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> getLastSalary(String tea_id, String year,String month);

	/**
	 * 获取累计薪资
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getTotalSalary(String tea_id);
	
	/**
	 * 获取薪资组成
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getSalaryCompose(String tea_id);
	
	/**
	 * 获取发放薪资的年份
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getYears(String tea_id);

	/**
	 * 获取历史薪资
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getHistorySalary(String tea_id,int year_);
	
	/**
	 * 还有几年退休
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getRetireYear(String tea_id);
	
	/**
	 * 获取近5年工资情况
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getFiveYearSalary(String tea_id);
	
	/**
	 * 获取某年某月薪资组成
	 * @param tea_id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String,Object>> getLastSalaryCom(String tea_id,String year,String month);
	
	/**
	 * 获取某年某月应发薪资组成
	 * @param tea_id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String,Object>> getLastSalaryPayable(String tea_id,String year,String month);

	/**
	 * 获取某年某月应发薪资总计
	 * @param tea_id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> getLastSalaryTotal(String tea_id,
			String year, String month);

	/**
	 * 获取某年某月代扣薪资组成
	 * @param tea_id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> getLastSalarySubtract(String tea_id,
			String year, String month);

	/**
	 * 获取某年某月代扣薪资总计
	 * @param tea_id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> getLastSalarySubtractTotal(String tea_id,
			String year, String month);
	
}
