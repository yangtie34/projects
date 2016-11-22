package com.jhkj.mosdc.sc.service;
/**
 * 自定义统计输出--学生来源地及学校统计。
 * @author Administrator
 *
 */
public interface StudentFromService {
	/**
	 * 获取各指标的总数
	 * @param params
	 * @return
	 */
	public String getCountZbs(String params);
	/**
	 * 根据指标获取各地市的对应数据。
	 * @param params
	 * @return
	 */
	public String getCountByZbId(String params);
	/**
	 * 查询grid数据。
	 * @param params
	 * @return
	 */
	public String queryGridContent(String params);
	/**
	 * 获取毕业学校grid数据。
	 * @param params
	 * @return
	 */
	public String queryByxxGridContent(String params);
	/**
	 * 保存学生来源地数据到统计临时表中。
	 */
	public void saveXsLyd2Temp();
	/**
	 * 保存学生毕业学校数据到统计临时表中。
	 */
	public void saveByxx2Temp();
}
