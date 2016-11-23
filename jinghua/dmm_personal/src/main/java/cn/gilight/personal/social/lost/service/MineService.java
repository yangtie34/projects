package cn.gilight.personal.social.lost.service;

import java.util.List;
import java.util.Map;

public interface MineService {
	
	/**
	* @Description: 根据id查帖子
	* @return: Page
	* @param myId
	* @return
	 */
	public abstract List<Map<String, Object>> queryMyTopic(String username);
	
	/**
	* @Description: 删除帖子
	* @return: void
	* @param username
	 */
	public abstract void deleteTopic(String id);
	
	/**
	* @Description: 修改状态
	* @return: void
	* @param username
	* @param is_solve
	 */
	public abstract void modifyStatus(String id);

}
