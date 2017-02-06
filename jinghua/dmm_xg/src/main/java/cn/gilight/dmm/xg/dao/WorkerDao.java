package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 学生工作者
 * 
 * @author xuebl
 * @date 2016年4月20日 下午3:49:20
 */
public interface WorkerDao {

	/**
	 * 查询学生工作者数量（当前时间）
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param deptList 权限
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryWorkerCountList(String start_date, String end_date, 
			List<String> deptList, List<AdvancedParam> teaAdvancedList);

	/**
	 * 获取辅导员数量（根据辅导员聘任表解析）<br>
	 * 学年学期任职的辅导员
	 * @param deptList 权限list
	 * @param xnxqList 给定时间段内的学年学期 eg:[ [2014,01],[2014,02],[2015,01] ]
	 * @param isFullTime 是否专职辅导员
	 * @return List<String>
	 */
	public List<String> queryInstructorsList(List<String> deptList, List<List<String>> xnxqList, boolean isFullTime);

	/**
	 * 获取辅导员数量（根据学生工作者表解析）<br>
	 * 这段时间任职的辅导员
	 * @param deptList 标准权限List
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param isFullTime 是否专职辅导员
	 * @return List<String>
	 */
	public List<String> queryInstructorsList(List<String> deptList, String start_date, String end_date, boolean isFullTime);

	/**
	 * 获取辅导员sql（根据学生工作者表解析）<br>
	 * 这段时间任职的辅导员
	 * @param deptList 标准权限List
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param isFullTime 是否专职辅导员
	 * @return List<String>
	 */
	public String getInstructorsSql(List<String> deptList, String start_date, String end_date, boolean isFullTime);
	
	/**
	 * 查询学生工作者职称分布数量（当前时间、权限）
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param deptList 权限
	 * @param stu_worker_code 学生工作者代码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryWorkerZcCountList(String start_date, String end_date, 
			List<String> deptList, String stu_worker_code);

	/**
	 * 查询学生工作者学历分布数量（当前时间、权限）
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param deptList 权限
	 * @param stu_worker_code 学生工作者代码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryWorkerDegreeCountList(String start_date, String end_date, 
			List<String> deptList, String stu_worker_code);

	/**
	 * 查询学生工作者出生日期（当前时间、权限）
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param deptList 权限
	 * @param stu_worker_code 学生工作者代码
	 * @return List<String>
	 */
	public List<String> queryWorkerBirthdayList(String start_date, String end_date, List<String> deptList, String stu_worker_code);

	/**
	 * 查询学生工作者性别分布数量（当前时间、权限）
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param deptList 权限
	 * @param stu_worker_code 学生工作者代码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryWorkerSexCountList(String start_date, String end_date, 
			List<String> deptList, String stu_worker_code);
	/**
	 * 获取学生工作者表记录 ID sql 【可以用这个sql 再次关联 t_tea_stu_worker 表】
	 * @param start_date
	 * @param end_date
	 * @return String "select t.id from t_tea_stu_worker t where ..."
	 */
	public String getWorkerMatchingRecordIdSql(String start_date, String end_date,
			List<String> deptList, String stu_worker_code);
	/**
	 * 获取学生工作者教职工 ID sql 【一定不能用这个sql 再次关联 t_tea_stu_worker 表】
	 * @param start_date
	 * @param end_date
	 * @return String "select t.tea_id from t where ..."
	 */
	public String getWorkerMatchingTeacherIdSql(String start_date, String end_date,
			List<String> deptList, String stu_worker_code);

}