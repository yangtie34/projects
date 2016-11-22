package cn.gilight.personal.student.colorPage.service;

import java.util.Map;

public interface ColorPageService {

	/**
	 * 查询彩页第一页学校情况
	 * @return
	 */
	public Map<String, Object> getSchool();
	
	/**
	 * 查询辅导员
	 * @param stu_id
	 * @return
	 */
	public Map<String, Object> getFdy(String stu_id);
	
	/**
	 * 查询三餐销量最好的窗口
	 * @return
	 */
	public Map<String,Object> QueryRestaurantWinTop();
	
	/**
	 * 查询专业性别分布
	 * @return
	 */
	public Map<String,Object> QueryMajorSex(String stu_id);
	
	/**
	 * 在校生男女人数
	 * @return
	 */
	public Map<String,Object> queryStuSex();
	
	/**
	 * 查询同母校
	 * @return
	 */
	public Map<String,Object> queryTmx(String stu_id);
	
	/**
	 * 查询同乡
	 * @return
	 */
	public Map<String,Object> queryTx(String stu_id);
	
	/**
	 * 查询学生要修的课程数及课时
	 * @return
	 */
	public Map<String,Object> queryCourse(String stu_id);
}
