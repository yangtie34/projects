package cn.gilight.wechat.service;

import java.util.List;
import java.util.Map;

public interface WordQueryService {
	
	/** 
	* @Description: 查询上月班级土豪
	* @param @param sno
	* @return Map<String,Object>
	*/
	public abstract Map<String, Object> queryTyrant(String sno,String month);

	/**
	 * 查询该学年学期的班长和辅导员
	 * @param school_year
	 * @param term_code
	 * @param stu_id
	 * @return
	 */
	public abstract Map<String, Object> queryInstructor(String school_year, String term_code, String stu_id);

	/**
	 * 查询室友
	 * @param username
	 * @return
	 */
	public abstract List<Map<String, Object>> queryRoomie(String username);
	
	/**
	 * 查询学生未还图书
	 * @param username
	 * @return
	 */
	public abstract List<Map<String, Object>> queryStuInBorrow(String username);
	
	/**
	 * 查询学生同专业老乡
	 * @param username
	 * @return
	 */
	public abstract List<Map<String, Object>> queryStuPaisan(String username);

	/**
	 * 获取早中晚窗口top3
	 * @return
	 */
	public abstract List<Map<String, Object>> getCardDeptMonth();
	
	/**
	 * 获取学生本专业学霸
	 */
	public abstract List<Map<String, Object>> getMajorSmart(String username);
	
	/**
	 * 查询学生挂科科目
	 * @return
	 */
	public abstract List<Map<String, Object>> getStuFlunk(String username,String school_year,String term_code);

}
