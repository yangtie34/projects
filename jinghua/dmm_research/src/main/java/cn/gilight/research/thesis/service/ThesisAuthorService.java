package cn.gilight.research.thesis.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface ThesisAuthorService {

	/** 
	 * @Description: 查询作者人数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Integer
	 */
	public abstract Integer queryTotalNums(String xkmlid, String startYear,
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
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 按照类别查询作者人数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByType(
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 按作者产量查询作者人数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByOutput(
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 查询学历分布
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByEducation(
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 查询作者年龄分布
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByAge(
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 作者职称类别
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByZyjszwJb(
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 作者职称
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByZyjszw(
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 人员类别
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByRylb(
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag);

	/** 
	 * @Description: 教职工来源
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryAuthorNumsByTeaSource(
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag);
	
	public abstract Map<String, Object> queryAuthorListByPage(Page page,String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag
			,String gender,String value,String education,String age,String zcjb,String zc,String rylb,String source);

}