package cn.gilight.research.project.serivice ;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface ProjectNumsService {

	/** 
	 * @Description: 查询科研项目总数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Integer
	 */
	public abstract Integer queryTotalNums(String startYear, String endYear,
			String zzjgid,String shiroTag);

	/** 
	 * @Description: 查询每年科研项目数量
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryProjectNumsOfYears(
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 各单位各级别项目总数
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryProjectNumsOfDeptAndLevel(
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 项目级别分布
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryProjectNumsOfLevel(
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 各级项目下达部门分布
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryIssuedDeptNumsOfProjectLevel(
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 项目等级分布
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryProjectRankNumsOfProjectLevel(
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 科研项目列表
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Map<String, Object>
	 */
	public abstract Map<String, Object> queryProjectList(Page page,String queryString,
			String startYear, String endYear, String zzjgid,String shiroTag,String setupYear,String dept,String level,String issuedDept);

}