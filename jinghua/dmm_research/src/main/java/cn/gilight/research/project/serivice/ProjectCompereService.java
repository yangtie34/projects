package cn.gilight.research.project.serivice;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**   
* @Description: 科研项目主持人分析service
* @author Sunwg
* @date 2016年6月23日 下午4:08:33   
*/
public interface ProjectCompereService {

	/** 
	 * @Description: 查询作者人数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Integer
	 */
	public abstract Integer queryTotalNums( String startYear,
			String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 按照性别查询人数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByGender(
			 String startYear, String endYear, String zzjgid,String shiroTag);
	
	/** 
	* @Description: 根据项目的级别查询项目主持人*次分布
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: List<Map<String,Object>>
	*/
	public abstract List<Map<String, Object>> queryCompereNumsByProjectLevel(
		String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 查询学历分布
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByEducation(
			 String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 查询作者年龄分布
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByAge(
			 String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 作者职称类别
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByZyjszwJb(
			 String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 作者职称
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByZyjszw(
			 String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 人员类别
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByRylb(
			 String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 教职工来源
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByTeaSource(
			 String startYear, String endYear, String zzjgid,String shiroTag);

	public abstract Map<String, Object> queryCompereListByPage(Page page,String startYear, String endYear, String zzjgid,String shiroTag
			,String gender,String education,String age,String zcjb,String zc,String rylb,String source);

}