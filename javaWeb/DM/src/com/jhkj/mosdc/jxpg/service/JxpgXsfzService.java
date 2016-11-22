package com.jhkj.mosdc.jxpg.service;

import java.util.Map;

/**
 * 教学评估-学生发展
 * @Comments:
 * Company:河南精华科技有限公司
 * Created by zhenglicai
 * @DATE:2015年1月15日
 * @TIME:下午6:13:32
 */
public interface JxpgXsfzService {

	/**
	 * 验证学生发展 招生报道情况
	 * @param params
	 * @return
	 */
	public Map saveBeforeXsbdqk(String params);
	/**
	 * 验证学生发展 学生管理员结构
	 * @param params
	 * @return
	 */
	public Map saveBeforeXsglyjg(String params);
	/**
	 * 验证学生发展 教学单位管理员与学生情况
	 * @param params
	 * @return
	 */
	public Map saveBeforeJxdwXsqk(String params);
	/**
	 * 验证学生发展情况
	 * @param params
	 * @return
	 */
	public Map saveBeforeXsfzqk(String params);
	/**
	 * 验证就业生去向分布
	 * @param params
	 * @return
	 */
	public Map saveBeforeJyqxfb(String params);
	/**
	 * 验证毕业生情况
	 * @param params
	 * @return
	 */
	public Map saveBeforeBysqk(String params);
}
