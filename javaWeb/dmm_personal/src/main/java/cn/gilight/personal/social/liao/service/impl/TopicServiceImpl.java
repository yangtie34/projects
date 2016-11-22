package cn.gilight.personal.social.liao.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.po.TLiaoTopic;
import cn.gilight.personal.social.liao.service.TopicService;
@Service("topicService")
public class TopicServiceImpl implements TopicService{
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private HibernateDao hibernate;
	
	/** 
	* @Description: 查询帖子列表
	* @param page
	* @return: Page
	*/
	@Override
	public Page queryTopicListByPage(Page page){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String loginUser = principal.getName();
		String sql = "SELECT T.ID,T.USERNAME,S.REAL_NAME, T.CONTENT,wb.wechat_head_img, "
				+ " TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME,"
				+ " DECODE(T.USERNAME,'"+loginUser+"',1,0) IS_MINE, R.ID FRIEND_ID "
				+ "  FROM T_LIAO_TOPIC T "
				+ " INNER JOIN T_SYS_USER S ON T.USERNAME = S.USERNAME "
				+ "  LEFT JOIN T_LIAO_RELATION R ON (T.USERNAME = R.USERNAMEA AND R.USERNAMEB = '"+loginUser+"') OR ( T.USERNAME= R.USERNAMEB AND R.USERNAMEA ='"+loginUser+"')"
				+ " left join t_wechat_bind wb on wb.username = t.username "
				+ " WHERE T.STATE = 1 ORDER BY T.CREATE_TIME DESC ";
		page = baseDao.queryWithPageInLowerKey(sql, page);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> result = page.getResult();
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> it = result.get(i);
			String id = MapUtils.getString(it, "id");
			//图片
			it.put("images", this.queryImageListOfTopic(id));
			//默认取出5条评论
			it.put("comments", this.queryTop5CommentsOfTopic(id));
		}
		return page;
	}
	
	/** 
	* @Description: 查询帖子列表
	* @param page
	* @return: Page
	*/
	@Override
	public Page queryTopicListOfMyFriendAndMeByPage(Page page){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String loginUser = principal.getName();
		String sql = "SELECT TT.*,S.REAL_NAME,wb.wechat_head_img,co.name_ sex FROM (SELECT T.ID,T.USERNAME, T.CONTENT, "
				+ " TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME,R.ID FRIEND_ID FROM T_LIAO_TOPIC T"
				+ " INNER JOIN T_LIAO_RELATION R ON (T.USERNAME = R.USERNAMEA AND R.USERNAMEB = '"+loginUser+"') OR ( T.USERNAME= R.USERNAMEB AND R.USERNAMEA ='"+loginUser+"')"
				+ " WHERE T.STATE = 1 "
				+ " UNION "
				+ " SELECT T.ID,T.USERNAME, T.CONTENT,   TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME,NULL FRIEND_ID"
				+ " FROM T_LIAO_TOPIC T WHERE T.STATE = 1 AND T.USERNAME =  '"+loginUser+"') TT"
				+ " INNER JOIN T_SYS_USER S ON TT.USERNAME = S.USERNAME"
				+ " left join t_wechat_bind wb on wb.username = s.username"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = wb.wechat_sex"
				+ " ORDER BY TT.CREATE_TIME DESC";
		page = baseDao.queryWithPageInLowerKey(sql, page);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> result = page.getResult();
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> it = result.get(i);
			String id = MapUtils.getString(it, "id");
			//图片
			it.put("images", this.queryImageListOfTopic(id));
			//默认取出5条评论
			it.put("comments", this.queryTop5CommentsOfTopic(id));
		}
		return page;
	}
	
	/** 
	* @Description: 查询今日发帖数量
	* @return: int
	*/
	public int queryTopicNumOfToday(){
		String sql = "SELECT COUNT(1)FROM T_LIAO_TOPIC T "
				+ " WHERE T.STATE = 1 AND TO_CHAR(T.CREATE_TIME,'YYYY-MM-DD') = TO_CHAR(SYSDATE,'YYYY-MM-DD') ";
		return baseDao.queryForIntBase(sql);
	}
	
	/** 
	 * @Description: 查询用户帖子数量
	 * @param username
	 * @return: int
	 */
	@Override
	public int queryTopicNumOfUser(String username){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String loginUser = principal.getName();
		String sql = "SELECT COUNT(1)"
				+ "  FROM T_LIAO_TOPIC T"
				+ "  LEFT JOIN T_LIAO_RELATION R ON (T.USERNAME = R.USERNAMEA AND R.USERNAMEB = '"+loginUser+"') OR ( T.USERNAME= R.USERNAMEB AND R.USERNAMEA ='"+loginUser+"')"
				+ " WHERE T.STATE = 1 AND T.USERNAME='"+username+"' ORDER BY T.CREATE_TIME DESC ";
		return baseDao.queryForIntBase(sql);
	}
	
	
	/** 
	 * @Description: 查询帖子列表
	 * @param page
	 * @param username
	 * @return: Page
	 */
	@Override
	public Page queryTopicListOfUserByPage(Page page,String username){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String loginUser = principal.getName();
		String sql = "SELECT T.ID,T.USERNAME,S.REAL_NAME,T.CONTENT,wb.wechat_head_img, "
				+ " TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME,"
				+ " DECODE(T.USERNAME,'"+loginUser+"',1,0) IS_MINE, R.ID FRIEND_ID "
				+ "  FROM T_LIAO_TOPIC T"
				+ " INNER JOIN T_SYS_USER S ON T.USERNAME = S.USERNAME "
				+ "  LEFT JOIN T_LIAO_RELATION R ON (T.USERNAME = R.USERNAMEA AND R.USERNAMEB = '"+loginUser+"') OR ( T.USERNAME= R.USERNAMEB AND R.USERNAMEA ='"+loginUser+"')"
				+ " left join t_wechat_bind wb on wb.username = t.username "
				+ " WHERE T.STATE = 1 AND T.USERNAME='"+username+"' ORDER BY T.CREATE_TIME DESC ";
		page = baseDao.queryWithPageInLowerKey(sql, page);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> result = page.getResult();
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> it = result.get(i);
			String id = MapUtils.getString(it, "id");
			//图片
			it.put("images", this.queryImageListOfTopic(id));
			//默认取出5条评论
			it.put("comments", this.queryTop5CommentsOfTopic(id));
		}
		return page;
	}
	
	/** 
	* @Description: 查询帖子的图片
	* @param topicId
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String, Object>> queryImageListOfTopic(String topicId){
		String sql = "SELECT T.ID,T.TOPIC_ID,T.IMG_URL FROM T_LIAO_TOPIC_IMAGE T WHERE T.TOPIC_ID = '"+topicId+"' ORDER BY T.CREATE_TIME";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/** 
	* @Description: 查询帖子前五条评论
	* @param topicId
	* @return: Page
	*/
	@Override
	public Page queryTop5CommentsOfTopic(String topicId){
		Page page = new Page();
		page.setCurpage(1);
		page.setPagesize(5);
		String sql = "  SELECT T.ID,T.USERNAME,S.REAL_NAME,T.TOPIC_ID,T.CONTENT,TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME "
				+ " FROM T_LIAO_TOPIC_REPLY T "
				+ " INNER JOIN T_SYS_USER S  ON T.USERNAME = S.USERNAME "
				+ " WHERE T.TOPIC_ID = '"+topicId+"' ORDER BY T.CREATE_TIME";
		page = baseDao.queryWithPageInLowerKey(sql, page);
		for (int i = 0; i < page.getResult().size(); i++) {
			@SuppressWarnings("unchecked")
			Map<String, Object> it = (Map<String, Object>) page.getResult().get(i);
			String id = MapUtils.getString(it, "id");
			it.put("replyList", this.queryCommentReplyList(id));
		}
		return page;
	}
	
	/** 
	* @Description: 查询帖子评论
	* @param topicId
	* @return: List<Map<String, Object>>
	*/
	@Override
	public List<Map<String, Object>> queryCommentsOfTopic(String topicId){
		String sql = " SELECT T.ID,T.USERNAME,wb.wechat_head_img,S.REAL_NAME,T.TOPIC_ID,T.CONTENT,TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME FROM T_LIAO_TOPIC_REPLY T"
				+ " INNER JOIN T_SYS_USER S  ON T.USERNAME = S.USERNAME"
				+ " left join t_wechat_bind wb on wb.username = t.username WHERE T.TOPIC_ID = '"+topicId+"' ORDER BY T.CREATE_TIME";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> it = list.get(i);
			String id = MapUtils.getString(it, "id");
			it.put("replyList", this.queryCommentReplyList(id));
		}
		return list;
	}
	
	/** 
	* @Description: 查询评论回复列表
	* @param commentId
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String, Object>> queryCommentReplyList(String commentId){
		String sql = " SELECT T.ID,T.USERNAME, S.REAL_NAME, T.TO_USERNAME, ST.REAL_NAME TO_REAL_NAME,T.REPLY_ID,T.CONTENT, "
				+ "TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME "
				+ " FROM T_LIAO_TOPIC_REPLY_ANSWER T "
				+ " INNER JOIN T_SYS_USER S  ON T.USERNAME = S.USERNAME"
				+ " INNER JOIN T_SYS_USER ST  ON T.TO_USERNAME = ST.USERNAME "
				+ " WHERE T.REPLY_ID = '"+commentId+"' ORDER BY T.CREATE_TIME";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/**
	 * 根据id查询帖子信息
	 */
	@Override
	@Transactional
	public Map<String, Object> queryTopicInfo(String id){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String loginUser = principal.getName();
		String sql = "SELECT T.ID,T.USERNAME,wb.wechat_head_img,S.REAL_NAME,T.CONTENT, "
				+ " TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME,"
				+ " DECODE(T.USERNAME,'"+loginUser+"',1,0) IS_MINE, R.ID FRIEND_ID "
				+ "  FROM T_LIAO_TOPIC T  INNER JOIN T_SYS_USER S  ON T.USERNAME = S.USERNAME "
				+ "  LEFT JOIN T_LIAO_RELATION R ON (T.USERNAME = R.USERNAMEA AND R.USERNAMEB = '"+loginUser+"') OR ( T.USERNAME= R.USERNAMEB AND R.USERNAMEA ='"+loginUser+"')"
				+ " left join t_wechat_bind wb on wb.username = t.username"
				+ " WHERE T.ID = '"+id+"' AND T.STATE = 1 ORDER BY T.CREATE_TIME DESC ";
		List<Map<String, Object>> topiclist = baseDao.queryListInLowerKey(sql);
		
		if(topiclist.size() > 0){
			Map<String, Object> it = topiclist.get(0);
			//图片
			it.put("images", this.queryImageListOfTopic(id));
			//默认取出5条评论
			it.put("comments", this.queryCommentsOfTopic(id));
			if(MapUtils.getIntValue(it, "is_mine") == 1){
				sql = "UPDATE T_LIAO_TOPIC_REPLY T SET T.STATE = 1 WHERE T.TOPIC_ID = '"+id+"'";
				baseDao.update(sql);
				sql = "UPDATE T_LIAO_TOPIC_REPLY_ANSWER T SET T.STATE = 1 WHERE T.REPLY_ID IN (SELECT ID FROM T_LIAO_TOPIC_REPLY WHERE TOPIC_ID = '"+id+"')";
				baseDao.update(sql);
			}
			return it;
		}
		else{
			return null;
		}
	}

	/** 
	 * @Description: 删除帖子（改变状态）
	 * @param id
	 */
	@Override
	public void deleteTopic(String id) {
		TLiaoTopic topic = hibernate.getById(id, TLiaoTopic.class);
		topic.setState(false);
		hibernate.update(topic);
	}
	/** 
	 * @Description: 深度删除帖子（彻底删除）
	 * @param id
	 */
	@Override
	@Transactional
	public void deepDeleteTopic(String id) {
		String sql = "DELETE FROM T_LIAO_TOPIC_REPLY_ANSWER T WHERE T.REPLY_ID IN (SELECT ID FROM T_LIAO_TOPIC_REPLY WHERE TOPIC_ID = '"+id+"')";
		baseDao.delete(sql);
		sql = "DELETE FROM T_LIAO_TOPIC_REPLY WHERE TOPIC_ID = '"+id+"' ";
		baseDao.delete(sql);
		sql = "DELETE FROM T_LIAO_TOPIC WHERE ID = '"+id+"'";
		baseDao.delete(sql);
	}
	
	/** 
	* @Description: 查询指定用户的未读消息
	* @param username
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String, Object>> queryUnReadMessageOfUser(String username){
		String sql = "SELECT OT.*,TS.REAL_NAME TOPIC_REAL_NAME,S.REAL_NAME,W.WECHAT_HEAD_IMG FROM ("
					+ "SELECT T.ID TOPIC_ID, T.USERNAME TOPIC_USERNAME,T.CONTENT TOPIC_CONTENT, TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') TOPIC_CREATE_TIME,"
					+ " R.ID,R.USERNAME,R.CONTENT, TO_CHAR(R.CREATE_TIME , 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME,"
					+ " 1 IS_MINE,1 IS_REPLY,0 IS_ANSWER  FROM T_LIAO_TOPIC T"
					+ " INNER JOIN T_LIAO_TOPIC_REPLY R ON T.ID = R.TOPIC_ID WHERE R.STATE = 0 AND T.USERNAME = '"+username+"' " //我发表帖子别人评论
				+ " UNION"
					+ " SELECT T.ID TOPIC_ID,T.USERNAME TOPIC_USERNAME,T.CONTENT TOPIC_CONTENT,TO_CHAR(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') TOPIC_CREATE_TIME,"
					+ " A.ID,A.USERNAME,A.CONTENT, TO_CHAR(A.CREATE_TIME , 'YYYY-MM-DD HH24:MI:SS') CREATE_TIME,"
					+ " 1 IS_MINE,0 IS_REPLY,1 IS_ANSWER"
					+ "  FROM T_LIAO_TOPIC T"
					+ " INNER JOIN T_LIAO_TOPIC_REPLY R"
					+ " ON T.ID = R.TOPIC_ID"
					+ " INNER JOIN T_LIAO_TOPIC_REPLY_ANSWER A ON R.ID = A.REPLY_ID  AND A.USERNAME != '"+username+"'"
					+ " WHERE T.USERNAME = '"+username+"' AND A.STATE = 0" //我发表的帖子，别人的评论的回复
				+ " UNION"
					+ " SELECT"
					+ " TP.ID TOPIC_ID,TP.USERNAME TOPIC_USERNAME, TP.CONTENT TOPIC_CONTENT,TO_CHAR(TP.CREATE_TIME,'YYYY-MM-DD HH24:MI:SS') TOPIC_CREATE_TIME,"
					+ " TA.ID,TA.USERNAME,TA.CONTENT, TO_CHAR(TA.CREATE_TIME,'YYYY-MM-DD HH24:MI:SS') CREATE_TIME,"
					+ " 0 IS_MINE,0 IS_REPLY,1 IS_ANSWER"
					+ " FROM T_LIAO_TOPIC_REPLY_ANSWER TA"
					+ " INNER JOIN T_LIAO_TOPIC_REPLY TR ON TA.REPLY_ID = TR.ID"
					+ " INNER JOIN T_LIAO_TOPIC TP ON TR.TOPIC_ID = TP.ID AND TP.USERNAME != '"+username+"'"
					+ " WHERE TA.STATE = 0 AND TA.TO_USERNAME = '"+username+"' AND TA.USERNAME != '"+username+"'" //别人发表的帖子，我评论了，别人回复我
				+ ")OT INNER JOIN T_SYS_USER S ON OT.USERNAME = S.USERNAME "
				+ " INNER JOIN T_SYS_USER TS ON OT.TOPIC_USERNAME = TS.USERNAME "
				+ " LEFT JOIN T_WECHAT_BIND  W ON W.USERNAME = OT.USERNAME"
				+ " ORDER BY OT.CREATE_TIME DESC ";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/** 
	* @Description: 将用户的未读消息标志为已读
	* @return: void
	* @param username 
	*/
	@Override
	@Transactional
	public void changeUnreadMessageStateOfUser(String username){
		String sql = "UPDATE T_LIAO_TOPIC_REPLY T SET T.STATE = 1"
				+ "	 WHERE T.STATE = 0 AND T.TOPIC_ID IN "
				+ "(SELECT P.ID FROM T_LIAO_TOPIC P WHERE P.USERNAME = '"+username+"')";
		baseDao.update(sql);
		sql = "UPDATE T_LIAO_TOPIC_REPLY_ANSWER T SET T.STATE = 1 WHERE (T.REPLY_ID IN (SELECT R.ID FROM T_LIAO_TOPIC_REPLY R INNER JOIN T_LIAO_TOPIC P ON R.TOPIC_ID = P.ID AND P.USERNAME = '"+username+"') OR T.TO_USERNAME = '"+username+"') AND T.STATE = 0 AND T.USERNAME != '"+username+"'";
		baseDao.update(sql);
	}
}