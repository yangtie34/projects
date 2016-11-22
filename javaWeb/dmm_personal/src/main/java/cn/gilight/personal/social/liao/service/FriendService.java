package cn.gilight.personal.social.liao.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface FriendService {
	/** 
	* @Description: 查询我的好友数量
	* @param username
	* @return: List<Map<String,Object>>
	*/
	public abstract int queryFriendNum(String username);
	
	/** 
	 * @Description: 查询我的好友列表 
	 * @param username
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryFriendList(String username);
	
	/** 
	* @Description: 删除好友关系
	* @param id 
	* @return: void
	*/
	public abstract void delFriendShip(String id);

	/** 
	 * @Description: 搜索用户信息
	 * @param searchText 
	 * @return:  Page
	 */
	public abstract Page searchUserList(Page page,String searchText);
	
	/** 
	* @Description: 查询好友请求消息
	* @return: List<Map<String,Object>>
	*/
	public abstract List<Map<String, Object>> queryFriendApplyMessageOfUser(String username);

	/** 
	* @Description: 改变未读消息的状态
	* @return: void 
	*/
	public abstract void changeFriendUnreadMessageState(String username);
	
	/** 
	* @Description: 检查两个人是不是好友关系
	* @param usernamea
	* @param usernameb
	* @return: boolean
	*/
	public abstract boolean isFriend(String usernamea,String usernameb);
}