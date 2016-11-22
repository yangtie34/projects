package com.jhkj.mosdc.sc.job;

/**   
* @Description: TODO 不在校人数统计
* @author Sunwg  
* @date 2014-9-9 下午4:06:49   
*/
public interface BzxrstjDao {

	/** 
	* @Title: bzxxsThreeDay 
	* @Description: TODO 统计三天内不在校人数
	* @return void
	*/
	public void bzxxsThreeDay() throws Exception;;
	/**
	 * 昨日晚归名单保存。
	 * @throws Exception
	 */
	public void saveYesterDayWG() throws Exception;
	/**
	 * 昨日未住宿名单保存。
	 * @throws Exception
	 */
	public void saveYesterDayWZS() throws Exception;
	/**
	 * 晚上11点30分之前计算一下今日疑似未住宿名单保存。
	 * @throws Exception
	 */
	public void saveDayWZS() throws Exception;
}
