package cn.gilight.personal.social.liao.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface TopicService {
	/** 
	 * @Description: 查询帖子列表
	 * @param page
	 * @return: Page
	 */
	public abstract Page queryTopicListByPage(Page page);
	
	/** 
	* @Description: 查询我的好友和我发布的聊天
	* @param page
	* @return: Page 
	*/
	public Page queryTopicListOfMyFriendAndMeByPage(Page page);
	
	/** 
	* @Description: 查询今日发帖数量
	* @return: int
	*/
	public abstract int queryTopicNumOfToday();
	
	/** 
	 * @Description: 查询用户帖子数量
	 * @param username
	 * @return: int
	 */
	public abstract int queryTopicNumOfUser(String username);
	
	/** 
	 * @Description: 查询帖子列表
	 * @param page
	 * @param username
	 * @return: Page
	 */
	public abstract Page queryTopicListOfUserByPage(Page page,String username);

	/** 
	 * @Description: 查询帖子的图片
	 * @param topicId
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryImageListOfTopic(
			String topicId);

	/** 
	 * @Description: 查询帖子前五条评论
	 * @param topicId
	 * @return: Page
	 */
	public abstract Page queryTop5CommentsOfTopic(String topicId);

	/** 
	 * @Description: 查询帖子评论
	 * @param topicId
	 * @return: List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> queryCommentsOfTopic(
			String topicId);

	/** 
	 * @Description: 查询评论回复列表
	 * @param commentId
	 * @return: List<Map<String,Object>>
	 */
	public abstract List<Map<String, Object>> queryCommentReplyList(
			String commentId);

	/** 
	 * @Description: 查询帖子
	 * @param id
	 * @return: Map<String, Object>
	 */
	public abstract Map<String, Object> queryTopicInfo(String id);
	
	/** 
	 * @Description: 删除帖子（改变帖子的状态）
	 * @param id
	 */
	public abstract void deleteTopic(String id);
	
	/** 
	 * @Description: 深度删除帖子
	 * @param id
	 */
	public abstract void deepDeleteTopic(String id);
	
	/** 
	* @Description: 查询指定用户的未读消息
	* @param username
	* @return: List<Map<String,Object>>
	*/
	public List<Map<String, Object>> queryUnReadMessageOfUser(String username);
	
	/** 
	* @Description: 将用户的未读消息标志为已读
	* @return: void
	* @param username 
	*/
	public void changeUnreadMessageStateOfUser(String username);
}