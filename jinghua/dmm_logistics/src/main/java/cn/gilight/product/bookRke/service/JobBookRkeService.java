package cn.gilight.product.bookRke.service;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;



public interface JobBookRkeService {
	/**
	 * 初始化图书馆门禁信息log
	 * @return
	 */
	public JobResultBean initBookRkeStuMonth();
	
	/**
	 * 初始化学生出入图书馆门禁信息log
	 * @return
	 */
	public JobResultBean initStuRkeBookMonth();
	/**
	 * 更新图书馆门禁信息log
	 * @return
	 */
	public JobResultBean updateBookRkeStuMonth();
	/**
	 * 更新学生出入图书馆门禁信息log
	 * @return
	 */
	public JobResultBean updateStuRkeBookMonth();
}
