package cn.gilight.personal.teacher.warning.service;

import java.util.List;
import java.util.Map;

/**   
* @Description: TODO 学生预警处理service
* @author Sunwg  
* @date 2016年3月31日 下午5:20:32   
*/
public interface TeacherWarningService {
	/** 
	* @Description: 查询班主任管理的班级中的高低消费学生的数量
	* @param username
	* @return Map<String,Object>
	*/
	public Map<String, Object> queryConsumeNums(String username,int months);
	
	/** 
	* @Description: 查询男女生日均消费值
	* @return Map<String,Object>
	*/
	public Map<String, Object> queryAvgDayConsume();

	/** 
	 * @Description: 查询指定月数内的高消费|低消费学生列表
	 * @param username
	 * @return Map<String,Object>
	 */
	public List<Map<String, Object>> queryXfycStudentsList(String username,int months,int ishigh);
	

	/** 
	 * @Description: 查询班主任管理的班级中的住宿异常学生的数量
	 * @param username
	 * @param date
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryStayNums(String username,String date);
	
	/** 
	 * @Description: 查询晚归学生名单
	 * @param username
	 * @param date
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryLateStudentsList(String username,String date);
	
	/** 
	 * @Description: 查询疑似不在校学生名单
	 * @param username
	 * @param date
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryOutStudentList(String username,String date);
	
	/** 
	 * @Description: 查询班主任管理的班级中的学业异常人数
	 * @param username
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryStudyNums(String username,String xn,String xq);
	
	/** 
	 * @Description: 查询成绩异常学生名单
	 * @param username
	 * @return Map<String,Object>
	 */
	public List<Map<String, Object>> queryCourseFailStudents(String xn,String xq,String classid);
}