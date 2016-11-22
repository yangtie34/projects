package com.jhkj.mosdc.sc.service;

public interface ResearchPaperRankService {
	
	/**
	 * 获取收录论文影响因子各单位排名
	 * @param params
	 * @return
	 */
	public String getResearchDeptRank(String params);
	
	/**
	 * 获取全校平均影响因子
	 * @param params
	 * @return
	 */
	public String getResearchDeptAvg(String params);
	
	/**
	 * 获取全校或某单位收录论文的排名
	 * @param params
	 * @return
	 */
	public String getResearchPaperRank(String params);
	
	/**
	 * 查看历年趋势
	 * @param params
	 * @return
	 */
	public String getPast(String params);
	
	/**
	 * 获取所有单位
	 * @param params
	 * @return
	 */
	public String getAllDept(String params);
	
}


