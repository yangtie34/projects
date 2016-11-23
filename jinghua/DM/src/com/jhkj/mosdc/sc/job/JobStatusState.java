package com.jhkj.mosdc.sc.job;

/**   
* @Description: TODO Job的执行状态
* @author Sunwg  
* @date 2014-9-10 下午4:24:06   
*/
public class JobStatusState {
	/** 
	* 学生三天不在校job执行状态，1 未执行，0 执行中
	*/ 
	public static byte THREE_DAY_NOT_IN_SCHOOL = 1;
	/**
	 * 导出完毕标志  院系、专业、班级；1=导出完毕，0=开始导出。
	 */
	public static byte EXPORT_OVER_YX =1;
	public static byte EXPORT_OVER_ZY =1;
	public static byte EXPORT_OVER_BJ =1;
	/**
	 * 晚归统计标识
	 */
	public static byte YESTERDAY_WG = 1;
	public static byte EXPORT_YESTERDAY_WG = 1;
	/**
	 * 未住宿统计标识
	 */
	public static byte YESTERDAY_WZS = 1;
	public static byte EXPORT_YESTERDAY_WZS = 1;
}
