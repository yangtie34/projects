package cn.gilight.research.achievement.serivice;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface PatentService {
	/** 
	* @Description: 查询专利数
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: int 鉴定成果数
	*/
	public int queryPatentNums(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);

	/** 
	* @Description: 查询专利类型
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> queryPatentType(String xkmlid,String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	* @Description: 查询专利实施状态
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> queryPatentState(String xkmlid, String startYear,String endYear, String zzjgid,String shiroTag);

	/** 
	* @Description: 查询专利变化趋势
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @param param 参数（type专利类型，state专利实施状态，zl专利）
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> queryPatentChange(String xkmlid, String startYear,String endYear,String zzjgid, String param,String shiroTag);
	
	/** 
	* @Description: 查询单位分布
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @param param 参数（type专利类型，state专利实施状态，zl专利）
	* @return: List<Map<String, Object>>
	*/
	public List<Map<String, Object>> queryPatentDept(String xkmlid, String startYear,String endYear,String zzjgid, String param,String shiroTag);
	
	/** 
	* @Description: 查询贡献度高的院系
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: <Map<String, Object>
	*/
	public Map<String,Object> queryConDept(String xkmlid, String startYear,String endYear,String zzjgid,String shiroTag);

	public Map<String,Object> queryPatentDetail(Page page, String xkmlid, String startYear,
			String endYear, String zzjgid, String name, String flag,String shiroTag);
	
	
}