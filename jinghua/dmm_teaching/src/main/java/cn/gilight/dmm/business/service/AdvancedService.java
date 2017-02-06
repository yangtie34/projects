package cn.gilight.dmm.business.service;

import java.util.List;
import java.util.Map;

/**
 * 高级查询服务
 * 
 * @author xuebl
 * @date 2016年5月18日 下午2:40:25
 */
public interface AdvancedService {

	/**
	 * 获取服务数据
	 * @param tag
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryAdvancedList(String tag);

	/**
	 * 服务 - 获取教学组织机构数据（学生单位）（不包括教学单位level_type!='JXDW'）
	 * @param shiroTag
	 * @return Object
	 */
	public Object getDeptTeachDataService(String shiroTag);

	/**
	 * 服务 - 获取教学组织机构数据（教学单位）
	 * @param shiroTag
	 * @return Object
	 */
	public Object getDeptTeachTeachDataService(String shiroTag);
	
	/**
	 * 服务 - 获取行政组织机构数据
	 * @param shiroTag
	 * @return Object
	 */
	public Object getDeptDataService(String shiroTag);
    
	/**
	 * 服务 - 获取生源地数据
	 * @return Object
	 */
	public Object getOriginDataService();
	
}