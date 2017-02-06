package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月30日 下午3:26:50
 */
public interface ScoreHistoryDao {

	/**
	 * @deprecated
	 * 获取性别分组
	 * @param gpaSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getSex(String gpaSql);

	/**
	 * @deprecated
	 * 获取年级分组
	 * @param gpaSql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getGrade(String gpaSql);
	
}