package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;


/**
 * 教学经费
 * @author caijc
 */
public interface TeachingFundsDao {
	/**
	 * 查询各项教学经费 return List<Map<String ,Object>> [{name=本科专项教学经费, value=1543,
	 * code=02},{name=本科日常运行经费, value=1543, code=01}]
	 */
	public List<Map<String, Object>> queryFundsBySty(int year,String deptId);
	
	/**
	 * 查询经费所有年份
	 * @return List
	 */
	public List<Map<String, Object>> queryFundsYear();
	
	/**
	 * 查询每年教学各项教学经费总额
	 * @param year
	 * @param code
	 * @return List
	 */
	public Map<String, Object> queryFundsByYear(int year,String code,String deptId);

	/**
	 * 传入年份学校经费总额 以及生均
	 * @return Map
	 */
	public Map<String, Object> queryFundsByAll(int year,String deptId);
	
	/**
	 * 学院与学校生均对比
	 * 
	 * @param year
	 * @param deptList
	 * @return List
	 */
	public Map<String, Object> queryFundsAvg(int year,String deptId);

	/**
	 * 各学院经费一览
	 * 
	 * @param year
	 * @param code
	 * @param deptList
	 * @return List
	 */
	public Map<String, Object> queryFundsBycollege(int year,String code,String deptId);
	
	/**
	 * 获取当年所有学院以及deptId
	 * @param year
	 * @return List
	 */
	public List<Map<String,Object>> queryFundsBydeptId(int year,String deptList);
	
	/**
	 * 获取所有经费名称以及编码
	 * @return List
	 */
	public List<Map<String,Object>> queryFundsByCode();
}
