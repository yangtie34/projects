package cn.gilight.personal.student.four.service;

import java.util.Map;

public interface FourFirstService {
	
	/**
	 * 第一次消费
	 * @param username
	 * @return
	 */
	public Map<String,Object> getFirstCard(String username);
	
	/**
	 * 第一次挂科
	 * @param username
	 * @return
	 */
	public Map<String,Object> getFirstNotPass(String username);
	
	/**
	 * 第一次违纪
	 * @param username
	 * @return
	 */
	public Map<String,Object> getFirstPunish(String username);
	
	/**
	 * 第一次借书
	 * @param username
	 * @return
	 */
	public Map<String,Object> getFirstBook(String username);
	
	/**
	 * 第一次获得奖学金
	 * @param username
	 * @return
	 */
	public Map<String,Object> getFirstAward(String username);
	
	/**
	 * 第一次获得助学金
	 * @param username
	 * @return
	 */
	public Map<String,Object> getFirstSubsidy(String username);
	
	/**
	 * 第一次进图书馆
	 * @param username
	 * @return
	 */
	public Map<String,Object> getFirstBookRke(String username);
	
	
}
