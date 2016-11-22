package cn.gilight.personal.student.school.dao;

import java.util.Map;

public interface MySchoolDao {

	/**
	 * 教学楼数
	 * @return
	 */
	public Map<String, Object> getTeachingBuildingCounts();
	
	/**
	 * 宿舍数
	 * @return
	 */
	public Map<String, Object> getDormitoryBuildingCounts();
	
	/**
	 * 餐厅数
	 * @return
	 */
	public Map<String, Object> getRestaurantCounts();
	
	/**
	 * 商店数
	 * @return
	 */
	public Map<String, Object> getShopCounts();
	
	/**
	 * 藏书数
	 * @return
	 */
	public Map<String, Object> getBookCounts();
	
	/**
	 * 教师团队
	 * @return
	 */
	public Map<String, Object> getTeacherCounts();

	public Map<String, Object> getStudntCounts();
	
	
}
