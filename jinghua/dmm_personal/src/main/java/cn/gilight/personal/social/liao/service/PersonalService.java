package cn.gilight.personal.social.liao.service;

import java.util.Map;

public interface PersonalService {
	/** 
	 * @Description: 查询个人信息
	 * @return : Map<String,Object>
	 */
	public abstract Map<String,Object> queryInfoOfPerson(String username);
	
	/**
	 * 查询是否是学生
	 * @param username
	 * @return
	 */
	public boolean isStudent(String username);
	
	/** 
	* @Description: 检查用户是否存在
	* @param username
	* @return: boolean
	*/
	public abstract boolean isPersonExist(String username);

	/**
	 * @Description: 查询个人消费情况
	 * @param username
	 * @return Map<String,Object>
	 */
	public abstract Map<String,Object> queryCard(String username);

	/**
	 * 查询个人图书借阅
	 * @param username
	 * @return
	 */
	public abstract Map<String,Object> queryBook(String username);

	/**
	 * 查询个人课程成绩
	 * @param username
	 * @return
	 */
	public abstract Map<String,Object> queryCourse(String username);

}