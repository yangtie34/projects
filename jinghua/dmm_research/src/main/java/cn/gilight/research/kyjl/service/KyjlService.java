package cn.gilight.research.kyjl.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface KyjlService {

	/**
	 * 刷新奖励结果
	 */
	public void refreshResult(String year);

	/**
	 * 查询总奖励金额
	 * @param year
	 * @return
	 */
	public Map<String,Object> queryFund(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询各奖项金额分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryAwardPie(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询获奖人员单位分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryAwardDept(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 获奖人员排名
	 * @param year
	 * @param page
	 * @param param
	 * @return
	 */
	public Page queryAwardPeople(String year,String xkmlid,String zzjgid, Page page, String param,String shiroTag);

	/**
	 * 获取立项奖奖励名单
	 * @param page
	 * @param year
	 * @return
	 */
	public Map<String,Object> querySetupList(Page page, String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 获取立项奖项目级别奖励金额分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> querySetupLevel(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 获取立项奖项目程度奖励金额分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> querySetupRank(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 立项奖获奖人单位分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> querySetupDept(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询结项奖获奖名单
	 * @param page
	 * @param year
	 * @return
	 */
	public Map<String,Object> queryEndList(Page page, String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询结项奖获奖人员单位分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryEndDept(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询论文收录获奖名单
	 * @param page
	 * @param year
	 * @return
	 */
	public Map<String,Object> queryThesisInList(Page page, String year,String xkmlid,String zzjgid,String shiroTag);
	
	/**
	 * 查询论文转载获奖名单
	 * @param page
	 * @param year
	 * @return
	 */
	public Map<String,Object> queryThesisReshipList(Page page, String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询论文收录期刊分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryThesisIn(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询论文转载期刊分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryThesisReship(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 论文获奖人员单位分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryThesisDept(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 获取专利奖励结果
	 * @param page
	 * @param year
	 * @return
	 */
	public Map<String,Object> queryPatentList(Page page, String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 专利类别奖励分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryPatentType(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 发明人单位分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryPatentDept(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询科研经费奖励名单
	 * @param page
	 * @param year
	 * @return
	 */
	public Map<String,Object> queryFundList(Page page, String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 科研经费奖单位分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryFundDept(String year,String xkmlid,String zzjgid,String shiroTag);
	
	/**
	 * 查询个人获奖总额
	 * @param year
	 * @return
	 */
	public Map<String,Object> queryPersonalFundTotal(String year,String tea_id);
	
	/**
	 * 查询个人获奖奖项分布
	 * @param year
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> queryPersonalAward(String year,String tea_id);
	
	/**
	 * 查询个人获奖立项奖明细
	 * @param year
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> queryPersonalSetup(Page page,String year,String tea_id);
	
	/**
	 * 查询个人获奖结项奖明细
	 * @param year
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> queryPersonalEnd(Page page,String year,String tea_id);

	/**
	 * 查询个人获奖研究成果奖明细
	 * @param year
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> queryPersonalAchievement(Page page,String year,String tea_id);
	
	/**
	 * 查询个人获奖论文奖明细
	 * @param year
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> queryPersonalThesis(Page page,String year,String tea_id);
	
	/**
	 * 查询个人获奖专利奖明细
	 * @param year
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> queryPersonalPatent(Page page,String year,String tea_id);
	
	/**
	 * 查询个人获奖项目经费奖明细
	 * @param year
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> queryPersonalFund(Page page,String year,String tea_id);
	
	/**
	 * 查询个人成果转化奖明细
	 * @param page
	 * @param year
	 * @param tea_id
	 * @return
	 */
	public Map<String, Object> queryPersonalTransform(Page page,String year,String tea_id);

	/**
	 * 获取获奖成果奖励名单
	 * @param page
	 * @param year
	 * @return
	 */
	public Map<String, Object> queryAchievementList(Page page, String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 获取获奖成果参与奖奖励名单
	 * @param page
	 * @param year
	 * @return
	 */
	public Map<String, Object> queryAchievementList2(Page page, String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询获奖成果奖励单位分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryAchievementDept(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询科研成果转化奖获奖名单
	 * @param page
	 * @param year
	 * @return
	 */
	public Map<String, Object> queryTransformList(Page page, String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询科研成果转化奖获奖人单位分布
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> queryTransformDept(String year,String xkmlid,String zzjgid,String shiroTag);

	/**
	 * 查询获奖明细
	 * @param page
	 * @param year
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public Map<String,Object> queryAwardDetail(Page page, String year,String xkmlid,String zzjgid, String paramName,
			String paramValue,String shiroTag);

	/**
	 * 查询奖励结果明细
	 * @param page
	 * @param year
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public Map<String,Object> queryDetail(Page page, String year, String xkmlid,String zzjgid,String name,String paramName,
			String paramValue,String shiroTag);
}
