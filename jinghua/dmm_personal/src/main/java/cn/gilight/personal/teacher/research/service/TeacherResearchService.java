package cn.gilight.personal.teacher.research.service;

import java.util.List;
import java.util.Map;


public interface TeacherResearchService {
	
	/**
	 * 获取各科研类型数量
	 * @param tea_id
	 * @return
	 */
	public Map<String,Object> getResearchCounts(String tea_id);
	
	/**
	 * 获取各类论文数
	 * @param tea_id
	 * @return
	 */
	public Map<String,Object> getThesisCounts(String tea_id);
	
	/**
	 * 获取论文
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getThesis(String tea_id,String flag);
	
	/**
	 * 获取各类项目数
	 * @param tea_id
	 * @return
	 */
	public Map<String,Object> getProjectCounts(String tea_id);
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
	public List<Map<String,Object>> getWorks(String tea_id,String flag);
	
	/**
	 * 获取各类专利数
	 * @return
	 */
	public Map<String,Object> getPatentCounts(String tea_id);
	
	/**
	 * 获取所有专利
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getPatents(String tea_id,String flag);
	
	/**
	 * 获取各类成果数
	 * @param tea_id
	 * @return
	 */
	public Map<String,Object> getOutcomeCounts(String tea_id);
	
	/**
	 * 获取所有成果
	 * @param tea_id
	 * @return
	 */
	public List<Map<String,Object>> getOutcomes(String tea_id,String flag);
	
	

	public Map<String, Object> getWorksCounts(String tea_id);

	public List<Map<String, Object>> getSofts(String tea_id);
	
}
