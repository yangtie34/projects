package cn.gilight.research.mbrws.service;

import java.util.List;
import java.util.Map;

/**   
* @Description: 工大目标任务书service
* @author Sunwg
* @date 2016年12月6日 下午2:50:09   
*/
public interface GdMbrwsService {
	/** 
	* @Description: 查询考核主题列表
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String,Object>> queryKhztList();
	
	/** 
	* @Description: 查询考核计划列表
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String, Object>> queryKhjhList();
	
	/** 
	* @Description: 查询学科排名列表
	* @param zzjgid 组织机构id
	* @param khjhid 考核计划id
	* @param shiroTag 权限标识符
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String, Object>> queryXkpmList(String zzjgid,String khjhid,String shiroTag);
	
	/** 
	 * @Description: 查询考核项目列表
	 * @param khztid 考核主题list
	 * @param zzjgid 组织机构id
	 * @param khjhid 考核计划id
	 * @return: List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryKhxmListOfkhzt(String khztid,String zzjgid,String khjhid );
	
	
}