package cn.gilight.research.achievement.serivice;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface AppraisalService {
	/** 
	* @Description: 查询鉴定成果数
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: int 鉴定成果数
	*/
	public int queryNums(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);

	/** 
	* @Description: 查询鉴定成果变化趋势
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> queryChange(String xkmlid,String startYear, String endYear, String zzjgid,String shiroTag);
	
	/**
	 * @Description: 鉴定成果列表点击下钻事件
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid 
	 * @param year 下钻年份
	 * @return
	 */
	public Map<String,Object> queryChangeClick(Page page,String xkmlid, String startYear,String endYear, String zzjgid, String name,String flag,String shiroTag);

	/**
	 * @Description: 查询鉴定级别
	 * @param xkmlid 学科门类id
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryGrade(String xkmlid, String startYear, String endYear,String zzjgid,String shiroTag);
	
	/**
	 * @Description: 查询鉴定成果水平
	 * @param xkmlid 学科门类id
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryLevel(String xkmlid, String startYear, String endYear,String zzjgid,String shiroTag);

	/**
	 * @Description: 查询鉴定形式
	 * @param xkmlid 学科门类id
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryMode(String xkmlid, String startYear, String endYear,String zzjgid,String shiroTag);

	/**
	 * @Description: 查询完成人承担角色
	 * @param xkmlid 学科门类id
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @param role 作者承担角色代码
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryPeopleRole(String xkmlid, String startYear,String endYear, String zzjgid,String role,String shiroTag);

	/**
	 * @Description: 查询鉴定成果列表
	 * @param xkmlid 学科门类id
	 * @param startYear 开始年份
	 * @param endYear 结束年份
	 * @param zzjgid 组织机构id
	 * @param param 模糊匹配成果名
	 * @return Page
	 */
	public Page queryList(String xkmlid, String startYear, String endYear,String zzjgid, String param,Page page,String grade,String level,String mode,String shiroTag);

	/**
	 * 获取查询成果的条件code
	 * @return
	 */
	public Map<String,Object> queryAchievementCode();

	
	
	
	
	
}