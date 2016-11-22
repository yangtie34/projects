package cn.gilight.personal.student.school.service;

import java.util.Map;

public interface MySchoolService {
	/**
	 * 获取学校餐厅，教学楼，超市，藏书，教师，宿舍楼数量
	 * @return
	 */
	public Map<String,Object> getCounts();

	/**
	 * 获取学校人数
	 * @return
	 */
	public Map<String, Object> getPeopleCounts();

	/**
	 * 获取学校信息
	 * @return
	 */
	public Map<String, Object> getSchool();
}
