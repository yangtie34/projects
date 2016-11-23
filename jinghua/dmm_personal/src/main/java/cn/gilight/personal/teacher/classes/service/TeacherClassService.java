package cn.gilight.personal.teacher.classes.service;

import java.util.List;
import java.util.Map;

/**   
* @Description: 教职工信息service
* @author Sunwg  
* @date 2016年3月18日 下午5:21:51   
*/
public interface TeacherClassService {
	
	/** 
	* @Description: 查询辅导员管理的班级的总体信息
	* @param username 辅导员职工号
	* @return Map<String,Object>
	*/
	public Map<String,Object> queryClassesTotalInfo(String username);
	
	/** 
	 * @Description: 查询辅导员管理的班级的班级列表
	 * @param username 辅导员职工号
	 * @return List<Map<String,Object>> 
	 */
	public List<Map<String,Object>> queryClassList(String username);
	
	/** 
	 * @Description: 查询辅导员管理的所有学生
	* @param username
	* @return List<Map<String,Object>>
	*/
	public List<Map<String,Object>> queryStudentsList(String username);
	
	/** 
	 * @Description: 查询单个班级的信息
	 * @param classId 班级id
	 * @return Map<String,Object>
	 */
	public Map<String,Object> queryClassInfo(String classId);
	
	/** 
	* @Description: TODO 查询班级学生信息列表
	* @param classId 班级id
	* @return List<Map<String,Object>>
	*/
	public List<Map<String,Object>> queryStudentsListOfClass(String classId);
}