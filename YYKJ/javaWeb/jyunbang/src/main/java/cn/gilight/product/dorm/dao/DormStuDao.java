package cn.gilight.product.dorm.dao;

import java.util.List;
import java.util.Map;

public interface DormStuDao {
	
	/**
	 * 通过当前条件获取住宿基本信息
	 * @param query
	 * @return
	 */
	public Map<String,Object> getDormStuInfoByQuery(List<Map<String,Object>> query);
	
	/**
	 * 通过当前宿舍和条件获取子节点学生住宿情况
	 * @param query
	 * @param dorm 格式为：id:0,level_type:'XX'
	 * @return
	 */
	public List<Map<String,Object>> getDormStuByQueryAndDrom(List<Map<String,Object>> query,Map<String,String> dorm);
	
	/**
	 * 通过当前寝室ID和条件获取子节点学生住宿情况
	 * @param query
	 * @param endId 宿舍根节点ID
	 * @return
	 */
	public List<Map<String, Object>> getDormStuByQueryAndEndId(List<Map<String, Object>> query, String endId);
	
	/**
	 * 通过当前条件获取要获取的条件
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> getQueryCode(List<Map<String,Object>> query);
	
}
