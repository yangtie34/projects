package cn.gilight.research.writing.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface WorkService {

	/**
	 * 查询著作数量
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return
	 */
	public int queryWorkNums(String xkmlid, String startYear, String endYear,String zzjgid,String shiroTag);
	
	/**
	 * 查询著作作者承担角色
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return
	 */
	public List<Map<String,Object>> queryWorkAuthorRole(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/**
	 * 查询著作数历年变化趋势
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return
	 */
	public List<Map<String,Object>> queryWorkNumsChange(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag);
	
	/**
	 * 查询著作单位分布
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return
	 */
	public List<Map<String,Object>> queryWorkDept(String xkmlid, String startYear,String endYear, String zzjgid,String shiroTag);


	/**
	 * 查询带分页的著作列表
	 * @param page
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @param param
	 * @return
	 */
	public Page queryWorkList(Page page, String xkmlid, String startYear,String endYear, String zzjgid, String param,String shiroTag);

	/**
	 * 点击下钻事件
	 * @param page
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @param name
	 * @param flag
	 * @return
	 */
	public Map<String,Object> queryClickDetail(Page page, String xkmlid, String startYear,String endYear, String zzjgid, String name, String flag,String shiroTag);

	

}
