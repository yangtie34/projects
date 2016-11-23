package com.jhkj.mosdc.pano.service;
/**
 * 全景-前台统一请求service入口类。
 * 即前台是通过该类的2个方法分别获取学生全景和教师全景的；
 * 将参数传递给这2个参(pageId,ryId)数即可获得相应的全景信息。
 * @ClassName: IPanoService 
 * @author zhangzg 
 * @date 2013年11月1日 下午4:13:58 
 *
 */
public interface IPanoService {
	/**
	 * 根据学生id和页面id获取学生全景mini信息。
	 * @Title: getStuPanoById
	 * @param params 
	 * @return String
	 * @throws
	 */
	public String getStuMiniPanoById(String params);
	/**
	 * 根据教职工id和页面id获取教职工mini全景信息。
	 * @Title: getTeacherPanoById 
	 * @return String
	 * @throws
	 */
	public String getTeacherMiniPanoById(String params);
}
