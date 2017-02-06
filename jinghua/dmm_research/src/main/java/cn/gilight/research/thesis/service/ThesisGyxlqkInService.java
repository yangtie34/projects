package cn.gilight.research.thesis.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**   
* @Description: 高影响力期刊论文收录service
* @author Sunwg
* @date 2016年6月16日 上午11:55:07   
*/
public interface ThesisGyxlqkInService {
	/** 
	* @Description: 查询高影响力期刊列表
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String, Object>> getFamousTypes();
	
	/** 
	* @Description: 查询一类 期刊收录的论文数量
	* @param periodicalType　期刊类别
	* @param xkmlid 学科门类
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: Integer 
	*/
	public Integer getIncludeNumsOfPeriodical(String periodicalType,String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);

	/** 
	 * @Description:  查询一类 期刊收录的论文在各个院系分布情况
	 * @param periodicalType　期刊类别
	 * @param xkmlid 学科门类
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @return: List<Map<String,Object>> 
	 */
	public List<Map<String,Object>> getIncludeNumsOfPeriodicalByDept(String periodicalType,String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);

	/** 
	 * @Description:  查询一类 期刊收录的论文发表时间分布情况
	 * @param periodicalType　期刊类别
	 * @param xkmlid 学科门类
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @return: List<Map<String,Object>> 
	 */
	public List<Map<String,Object>> getIncludeNumsOfPeriodicalByYear(String periodicalType,String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/** 
	 * @Description:  查询一类 期刊收录的论文发表时间分布情况
	 * @param periodicalType　期刊类别
	 * @param xkmlid 学科门类
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @return: List<Map<String,Object>> 
	 */
	public List<Map<String,Object>> getSCIImpactFactor(String periodicalType,String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/** 
	 * @Description:  查询一类 期刊收录的论文发表时间分布情况
	 * @param xkmlid 学科门类
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @return: List<Map<String,Object>> 
	 */
	public List<Map<String,Object>> getSCIZone(String periodicalType,String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/** 
	* @Description: 分页查询论文收录详情
	* @param page 分页
	* @param periodicalType 期刊类别
	* @param xkmlid 学科门类
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @param zzjgmc 组织机构名称
	* @param pubYear 发表年份
	* @param thesisInSci SCI论文收录分区
	* @return: Map<String,Object>
	*/
	public Map<String,Object> queryIncludeListOfPeriodical(Page page,String periodicalType,String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag,
				String zzjgmc,String pubYear,String thesisInSci);
}