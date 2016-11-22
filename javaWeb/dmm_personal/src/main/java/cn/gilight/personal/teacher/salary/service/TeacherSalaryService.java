package cn.gilight.personal.teacher.salary.service;

import java.util.List;
import java.util.Map;

public interface TeacherSalaryService {
	
	/**
	 * 获取上月工资
	 * @param tea_id
	 * @return
	 */
	public Map<String,Object> getLastSalary(String tea_id);
	
	/**
	 * 获取累计薪资
	 * @param tea_id
	 * @return
	 */
	public Map<String,Object> getTotalSalary(String tea_id);
	
	/**
	 * 获取薪资组成
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getSalaryCompose(String tea_id);
	
	/**
	 * 获取历史薪资
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getHistorySalary(String tea_id);
	
	/**
	 * 获取到退休还能拿到的总薪资
	 * @param tea_id
	 * @return
	 */
	public Map<String,Object> getRetireTotalSalary(String tea_id);

	/**
	 * 获取近5年工资情况
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getFiveYearSalary(String tea_id) ;
	
	/**
	 * 获取某年某月薪资组成
	 * @param tea_id
	 * @param year_
	 * @param month_
	 * @return
	 */
	public Map<String,Object> getLastSalaryCom(String tea_id,String year_,String month_);
	
	/**
	 * 获取某年某月应发薪资组成
	 * @param tea_id
	 * @param year_
	 * @param month_
	 * @return
	 */
	public List<Map<String,Object>> getLastSalaryPayable(String tea_id,String year_,String month_);
	/**
	 * 获取某年某月应发薪资总计
	 * @param tea_id
	 * @param year_
	 * @param month_
	 * @return
	 */
	public Map<String,Object> getLastSalaryTotal(String tea_id,String year_,String month_);
	/**
	 * 获取某年某月代扣薪资组成
	 * @param tea_id
	 * @param year_
	 * @param month_
	 * @return
	 */
	public List<Map<String,Object>> getLastSalarySubtract(String tea_id,String year_,String month_);
	/**
	 * 获取某年某月代扣薪资总计
	 * @param tea_id
	 * @param year_
	 * @param month_
	 * @return
	 */
	public Map<String,Object> getLastSalarySubtractTotal(String tea_id,String year_,String month_);
}
