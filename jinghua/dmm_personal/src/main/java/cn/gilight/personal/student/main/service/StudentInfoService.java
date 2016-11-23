package cn.gilight.personal.student.main.service;

import java.util.Map;

/**   
* @Description: 学生个人信息
* @author Sunwg  
* @date 2016年4月26日 下午3:30:57   
*/
public interface StudentInfoService {

	/** 
	 * @Description: 根据学号获取学生基本信息
	 * @param @param sno 学号
	 * @return Map<String,Object>
	 */
	public abstract Map<String, Object> getStudentSimpleInfo(String sno);

	/** 
	 * @Description: 根据学生学号获取学生详细信息
	 * @param  sno 学号
	 * @return Map<String,Object>
	 */
	public abstract Map<String, Object> getStudentDetailInfo(String sno);
	
	/** 
	* @Description: 保存或者更新学生的电话
	* @param @param sno 学号
	* @param @param tel 电话
	* @return boolean 处理结果
	*/
	public abstract boolean saveOrUpdateStudentTel(String sno,String tel) throws SecurityException, NoSuchFieldException;
}