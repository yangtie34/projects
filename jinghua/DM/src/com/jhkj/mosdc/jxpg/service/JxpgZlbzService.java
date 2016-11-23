package com.jhkj.mosdc.jxpg.service;

import java.util.Map;

/**
 * 教学评估-质量保障
 * @Comments:
 * Company:河南精华科技有限公司
 * Created by zhenglicai
 * @DATE:2015年1月15日
 * @TIME:下午2:10:18
 */
public interface JxpgZlbzService {
	/**
	 * 保存教学管理队伍结构数据前 对录入数据进行校验
	 * @param params
	 * @return
	 */
	public Map saveBeforeJxdwjg(String params);
	/**
	 * 保存教学研究情况数据前 对录入数据进行校验
	 * @param params
	 * @return
	 */
	public Map saveBeforeJxyjqk(String params);
}
