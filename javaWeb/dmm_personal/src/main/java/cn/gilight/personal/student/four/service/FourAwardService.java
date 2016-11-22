package cn.gilight.personal.student.four.service;

import java.util.Map;

public interface FourAwardService {

	/**
	 * 查询学生获奖
	 * @param username
	 * @return
	 */
	public Map<String, Object> getAward(String username);
	
	/**
	 * 查询学生违纪
	 * @param username
	 * @return
	 */
	public Map<String, Object> getPunish(String username);

}
