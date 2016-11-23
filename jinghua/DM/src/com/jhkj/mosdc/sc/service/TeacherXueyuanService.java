package com.jhkj.mosdc.sc.service;
/**
 * 教职工学缘分析service
 * @author Administrator
 *
 */
public interface TeacherXueyuanService {
	/**
	 * 获取本校、外校学缘组成。饼图
	 * @param params
	 * @return
	 */
	public String queryBxWxzc(String params);
	/**
	 * 获取学术流派组成。饼图
	 * @param params
	 * @return
	 */
	public String queryXslp(String params);
	/**
	 * 获取毕业院校层次 饼图
	 * @param params
	 * @return
	 */
	public String queryByyxCc(String params);
	/**
	 * 毕业院校类型 饼图
	 * @param params
	 * @return
	 */
	public String queryByyxLx(String params);
	/**
	 * 毕业院校地域 饼图
	 * @param params
	 * @return
	 */
	public String queryByyxDy(String params);
	/**
	 * 获取毕业院校组成 柱图
	 * @param params
	 * @return
	 */
	public String queryByyx(String params);
	/**
	 * 获取学术流派描述文字
	 * @param params
	 * @return
	 */
	public String queryInfo1(String params);
	/**
	 * 获取毕业院校描述文字
	 * @param params
	 * @return
	 */
	public String queryInfo2(String params);
}
