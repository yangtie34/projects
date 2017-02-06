package cn.gilight.personal.teacher.main.service;

import java.util.List;
import java.util.Map;

/**   
* @Description: 教职工信息service
* @author Sunwg  
* @date 2016年3月18日 下午5:21:51   
*/
public interface TeacherInfoService {

	/** 
	 * @Description: 根据教职工职工号获取职工基本信息
	 * @param @param teano
	 * @return Map<String,Object>
	 */
	public abstract Map<String, Object> getTeacherSimpleInfo(String teano);

	/** 
	 * @Description: 根据教职工职工号获取教职工详细信息
	 * @param  teano
	 * @return Map<String,Object>
	 */
	public abstract Map<String, Object> getTeacherDetailInfo(String teano);
	
	public abstract void submitAdvice(String username,String advice);
	
	/** 
	* @Title: queryTeacherHistoryList 
	* @Description: TODO
	* @param username
	* @return List<Map<String,Object>>
	*/
	public abstract List<Map<String,Object>> queryTeacherHistoryList(String username);
	
	/** 
	* @Description: TODO 查询教职工的工作历时概况
	* @param @param username
	* @return Map<String,Object>
	*/
	public abstract Map<String,Object> queryTeacherHistoryInfo(String username);

}