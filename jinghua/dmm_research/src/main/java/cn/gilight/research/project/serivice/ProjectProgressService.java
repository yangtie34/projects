package cn.gilight.research.project.serivice;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface ProjectProgressService {

	/** 
	 * @Description: 查询在研项目数量
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Integer
	 */
	public abstract Integer queryGoingOnProjectNums(String level,
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 查询各单位不同状态的项目数
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryProjectNumsByDeptAndState(
			String level, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 科研项目超期时长分布
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryTimeoutProjectNums(
			String level, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 科研项目状态组成
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryProjectNumsByState(
			String level, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 各单位科研项目到期完成率排名
	 * @param page
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Map<String,Object>
	 */
	public abstract Map<String,Object> queryOrderByDept(Page page,String level,
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 单位 主持人完成率排名
	 * @param page
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Map<String,Object>
	 */
	public abstract Map<String,Object> queryOrderByCompere(Page page,String level,
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 超期项目列表
	 * @param page
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Map<String,Object>
	 */
	public abstract Map<String,Object> queryTimeOutProjectList(Page page,String level,
			String startYear, String endYear, String zzjgid,String shiroTag);

	public Map<String,Object> queryProgressDetail(Page page, String level,
			String startYear, String endYear, String zzjgid,String shiroTag, String name,
			String flag);

	
}