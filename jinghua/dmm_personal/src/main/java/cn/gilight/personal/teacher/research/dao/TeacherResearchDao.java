package cn.gilight.personal.teacher.research.dao;

import java.util.List;
import java.util.Map;

public interface TeacherResearchDao {
	
	/**
	 * 获取项目数
	 * @param tea_id
	 * @return
	 */
	public int getProjectCounts(String tea_id);
	
	/**
	 * 获取著作数
	 * @param tea_id
	 * @return
	 */
	public int getWorkCounts(String tea_id);
	
	/**
	 * 获取专利数
	 * @param tea_id
	 * @return
	 */
	public int getPatentCounts(String tea_id);
	
	/**
	 * 获取论文数
	 * @param tea_id
	 * @return
	 */
	public int getThesisCounts(String tea_id);
	
	/**
	 * 获取获奖成果数
	 * @param tea_id
	 * @return
	 */
	public int getOutcomeAwardCounts(String tea_id);
	
	/**
	 * 获取鉴定成果数
	 * @param tea_id
	 * @return
	 */
	public int getOutcomeAppraisalCount(String tea_id);
	
	/**
	 * 获取获奖论文数
	 * @param tea_id
	 * @return
	 */
	public int getAwardThesisCounts(String tea_id);

	/**
	 * 获取收录论文（期刊收录）数
	 * @param tea_id
	 * @return
	 */
	public int getInThesisCounts(String tea_id);
	
	/**
	 * 获取收录论文（会议收录）数
	 * @param tea_id
	 * @return
	 */
	public int getMeetingThesisCounts(String tea_id);

	/**
	 * 获取转载论文数
	 * @param tea_id
	 * @return
	 */
	public int getReshipThesisCounts(String tea_id);
	
	/**
	 * 获取所有论文
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getThesises(String tea_id);

	/**
	 * 获取国家级项目数
	 * @param tea_id
	 * @return
	 */
	public int getNationalProject(String tea_id);

	
	/**
	 * 获取在研项目数
	 * @param tea_id
	 * @return
	 */
	public int getInquestsProject(String tea_id);
	
	/**
	 * 获取所有项目
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getProjects(String tea_id,String flag);

	/**
	 * 获取所有著作
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getWorks(String tea_id,String flag);

	/**
	 * 获取受理专利数
	 * @param tea_id
	 * @return
	 */
	public int getAcceptPatent(String tea_id);
	/**
	 * 获取授权专利数
	 * @param tea_id
	 * @return
	 */
	public int getAccreditPatent(String tea_id);

	/**
	 * 获取所有专利
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getPatents(String tea_id,String flag);
	
	public List<Map<String, Object>> getAppraisalOutcomes(String tea_id);
	
	public List<Map<String, Object>> getAwardOutcomes(String tea_id);


	/**
	 * 获取获奖论文
	 * @param tea_id
	 * @return
	 */
	public List<Map<String, Object>> getAwardThesis(String tea_id);

	public List<Map<String, Object>> getInThesis(String tea_id);

	public List<Map<String, Object>> getMeetingThesis(String tea_id);

	public List<Map<String, Object>> getReshipThesis(String tea_id);

	public int getLeaderProject(String tea_id);

	public int getChiefEditor(String tea_id);
	public int getPartake(String tea_id);

	public int getSoftCounts(String tea_id);

	public List<Map<String, Object>> getSofts(String tea_id);

	
 }
