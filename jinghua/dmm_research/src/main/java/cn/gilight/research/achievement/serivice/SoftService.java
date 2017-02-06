package cn.gilight.research.achievement.serivice;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface SoftService {
	/** 
	* @Description: 查询计算机著作权数
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: int 计算机著作权数
	*/
	public int querySoftNums(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);

	/** 
	* @Description: 查询计算机著作权数变化趋势
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> querySoftChange(String xkmlid,String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	* @Description: 查询计算机著作权取得方式
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> querySoftGet(String xkmlid, String startYear,String endYear, String zzjgid,String shiroTag);
	
	/** 
	* @Description: 查询计算机著作权人员单位分布
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> querySoftPeopleDept(String xkmlid, String startYear,String endYear, String zzjgid,String shiroTag);

	/** 
	* @Description: 查询计算机著作权列表
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: Page
	*/
	public Page queryList(String xkmlid, String startYear, String endYear,String zzjgid, String param, Page page,String copyright,String getcode,String shiroTag);

	/** 
	* @Description: 查询活跃软件开发者
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> querySoftAuthor(String xkmlid, String startYear,String endYear, String zzjgid,String shiroTag);

	/**
	 * 查询条件code
	 * @return
	 */
	public Map<String,Object> querySoftCode();

	public Map<String,Object> querySoftDetail(Page page, String xkmlid, String startYear,
			String endYear, String zzjgid, String name, String flag,String shiroTag);

	
	
	
}