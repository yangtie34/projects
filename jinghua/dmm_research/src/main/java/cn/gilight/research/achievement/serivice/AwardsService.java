package cn.gilight.research.achievement.serivice;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface AwardsService {
	/** 
	* @Description: 查询获奖成果数
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: int 鉴定成果数
	*/
	public int queryAwardsNums(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);

	/** 
	* @Description: 查询获奖成果变化趋势
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> queryAwardsChange(String xkmlid,String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	* @Description: 查询获奖等级
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> queryAwardsLevel(String xkmlid, String startYear,String endYear, String zzjgid,String shiroTag);
	
	/** 
	* @Description: 查询获奖人单位分布
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public Object queryPeopleDept(String xkmlid, String startYear,String endYear, String zzjgid,String shiroTag);
	
	/** 
	* @Description: 查询获奖类别
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> queryAwardsType(String xkmlid, String startYear,String endYear, String zzjgid,String shiroTag);
	
	/** 
	* @Description: 查询完成人担任角色
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	*  @param role 完成人角色code
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> queryAwardsPeopleRole(String xkmlid, String startYear,String endYear, String zzjgid,String role,String shiroTag);

	/** 
	* @Description: 查询获奖成果列表
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: Page
	*/
	public Page queryList(String xkmlid, String startYear, String endYear,String zzjgid, String param, Page page,String level,String shiroTag);

	/**
	 * 获取查询获奖成果的条件code
	 * @return
	 */
	public Map<String,Object> queryAwardsCode();

	public Map<String,Object> queryDetailList(Page page, String xkmlid, String startYear,
			String endYear, String zzjgid, String name, String flag,String shiroTag);
	

	
	
	
}