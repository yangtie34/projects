package cn.gilight.research.project.serivice;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface ProjectFundService {

	/** 
	 * @Description: 科研项目经费总额
	 * @param startYear
	 * @param endYear	
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract Integer queryProjectFundTotal(
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 项目金额投入变化趋势
	 * @param startYear
	 * @param endYear	
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryFundTotalByYears(
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 各单位各级别项目投入总额
	 * @param startYear
	 * @param endYear	
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryFundTotalByDeptAndProjectLevel(
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 各级别项目投入总额
	 * @param startYear
	 * @param endYear	
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryFundTotalByProjectLevel(
			String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 各单位项目投入平均额
	 * @param startYear
	 * @param endYear	
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryFundAvgByDept(
			String startYear, String endYear, String zzjgid,String shiroTag);

	public abstract Map<String,Object> queryProjectDetail(Page page, String startYear,
			String endYear, String zzjgid,String shiroTag, String name, String flag);

}