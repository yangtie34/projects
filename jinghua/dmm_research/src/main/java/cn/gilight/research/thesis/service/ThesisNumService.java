package cn.gilight.research.thesis.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**   
* @Description: 论文发表量service
* @author Sunwg  
* @date 2016年6月12日 下午5:12:47   
*/
public interface ThesisNumService {
	/** 
	* @Description: 查询各类论文发表总量
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @return: int 论文总数量
	*/
	public int queryTotalNums(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/** 
	* @Description: 查询本校第一作者或非第一作者论文发表总量
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 组织机构id
	* @param isFirstAuth 是否第一作者
	* @return: int 论文总数量
	*/
	public int firstAuthThesisNums(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag,boolean isFirstAuth);
	
	/** 
	* @Description: 查询各院系论文发表总量和占学校总量比
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String,Object>> queryThesisNumsByDepts(String xkmlid,String startYear,String endYear,String shiroTag);
	
	/** 
	* @Description: 查询某个部门年度段内的论文发表总量和占全校比例
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 部门id
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String,Object>> queryThesisNumsByYears(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/** 
	* @Description: 各引文索引库论文发表量
	* @param xkmlid 学科门类id
	* @param startYear 开始年份
	* @param endYear 结束年份
	* @param zzjgid 部门id
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String,Object>> queryGywsykLwfbl(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/**
	* @Description: 各部门论文发表量点击下钻事件
	* @param page
	* @param xkmlid
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return
	 */
	public Map<String, Object> queryNumsList(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag,String name,String flag);

}