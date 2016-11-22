package cn.gilight.personal.student.score.service;

import java.util.List;
import java.util.Map;

public interface StuScoreService {

	/**
	 * 获取上学期总成绩排名
	 * @param username
	 * @return
	 */
	public Map<String, Object> getLastScore(String stu_id);
	
	/**
	 * 获取本专业排名占比
	 * @param stu_id
	 * @return
	 */
	public Map<String,Object> getProportion(String stu_id);

	/**
	 * 获取该生的各学年学期成绩
	 * @param username
	 * @return
	 */
	public List<Map<String, Object>> getScoreList(String stu_id);
	
	public Map<String,Object> getCredit(String stu_id);

	public Map<String, Object> getCreditType(String stu_id);

}
