package cn.gilight.research.thesis.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**   
* @Description: 高影响力期刊发文service
* @author Sunwg  
* @date 2016年6月12日 下午5:12:47   
*/
public interface ThesisGyxlqkPubService {
	/** 
	* @Description: 查询各类论文发表总量
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: int 论文总数量
	*/
	public int querySCINums(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/** 
	* @Description: 查询每年的SCI发文量
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String,Object>> querySCINumsByYear(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	
	/** 
	 * @Description: 查询每年的SCI发文量
	 * @param xkmlid 学科门类id
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @return: List<Map<String,Object>>
	 */
	public List<Map<String,Object>> querySCINumsByDept(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);

	/** 
	 * @Description: 查询发文期刊载文量
	 * @param xkmlid 学科门类id
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @return: List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryIncludeNumsByPeriodical(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/**
	* @Description: 高影响力期刊发文分析下钻
	* @return: Map<String,Object>
	* @param page
	* @param xkmlid 学科门类id
    * @param startYear 开始年份
    * @param endYear 结束年份
    * @param zzjgid 组织机构id
	* @param qklbid 期刊类别id
	 */
	public Map<String,Object> queryGyxlqkPubList(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag,String name,String flag);
}