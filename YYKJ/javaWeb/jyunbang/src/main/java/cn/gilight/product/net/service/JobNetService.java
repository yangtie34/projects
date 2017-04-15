package cn.gilight.product.net.service;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;



public interface JobNetService {
	/**
	 * 初始化学生网络信息log
	 * @return
	 */
	public JobResultBean initNetStuMonth();
	
	/**
	 * 初始化教师网络信息log
	 * @return
	 */
	public JobResultBean initNetTeaMonth();
	
	/**
	 * 初始化网络信息log
	 * @return
	 */
	public JobResultBean initNetTypeMonth();
	
	/**
	 * 初始化学生人数
	 * @return
	 */
	public JobResultBean initStuNumMonth();
	
	/**
	 * 更新学生网络信息log
	 * @return
	 */
	public JobResultBean updateNetStuMonth();
	
	/**
	 * 更新教师网络信息log
	 * @return
	 */
	public JobResultBean updateNetTeaMonth();
	
	/**
	 * 更新网络信息log
	 * @return
	 */
	public JobResultBean updateNetTypeMonth();
	
	/**
	 * 更新学生人数log
	 * @return
	 */
	public JobResultBean updateStuNumMonth();
	
	/**
	 * 更新网络信息趋势log
	 * @return
	 */
	public JobResultBean updateNetTrend();
	/**
	 * 初始化教师网络账号预警
	 * @return
	 */
	public JobResultBean initNetTeaWarnMonth();
	
	/**
	 * 更新教师网络账号预警
	 * @return
	 */
	public JobResultBean updateNetTeaWarnMonth();
}
