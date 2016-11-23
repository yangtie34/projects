package cn.gilight.personal.social.liao.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.page.Page;
import cn.gilight.personal.po.TLiaoRelation;
import cn.gilight.personal.po.TLiaoRelationApply;
import cn.gilight.personal.po.TLiaoRelationLog;
import cn.gilight.personal.social.liao.service.FriendService;
@Service("friendService")
public class FriendServiceImpl implements FriendService  {
	Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private HibernateDao hibernate;
	
	/** 
	* @Description: 查询我的好友数量
	* @param username
	* @return: List<Map<String,Object>>
	*/
	@Override
	public int queryFriendNum(String username) {
		String sql = "SELECT COUNT(1)"
				+ " FROM T_LIAO_RELATION T"
				+ " INNER JOIN T_SYS_USER U ON (T.USERNAMEA = U.USERNAME AND T.USERNAMEB = '"+username+"') "
				+ " OR (T.USERNAMEB = U.USERNAME AND T.USERNAMEA = '"+username+"')"
				+ " WHERE U.USERNAME != '" +username+ "'";
		return baseDao.queryForIntBase(sql);
	}
	
	/** 
	* @Description: 查询我的好友列表 
	* @param username
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String, Object>> queryFriendList(String username) {
		String sql = "SELECT T.ID,U.REAL_NAME,U.USERNAME,W.WECHAT_HEAD_IMG"
				+ " FROM T_LIAO_RELATION T"
				+ " INNER JOIN T_SYS_USER U ON (T.USERNAMEA = U.USERNAME AND T.USERNAMEB = '"+username+"') "
				+ " OR (T.USERNAMEB = U.USERNAME AND T.USERNAMEA = '"+username+"')"
				+ " LEFT JOIN T_WECHAT_BIND W ON U.USERNAME = W.USERNAME "
				+ " WHERE U.USERNAME != '" +username+ "'"
				+ " ORDER BY U.REAL_NAME";
		return baseDao.queryListInLowerKey(sql);
	}

	/** 
	* @Description: 删除好友关系
	* @param id 
	* @return: void
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	*/
	@Override
	@Transactional
	public void delFriendShip(String id){
		TLiaoRelation relation = hibernate.getById(id, TLiaoRelation.class);
		if(relation != null){
			String applyId = relation.getApplyId();
			if (applyId != null && applyId.length() > 0) {
				TLiaoRelationApply apply = hibernate.getById(applyId, TLiaoRelationApply.class);
				if (apply != null) {
					apply.setState(4);
					hibernate.update(apply);
				}
			}
			try {
				TLiaoRelationLog log = new TLiaoRelationLog();
				log.setUsernamea(relation.getUsernamea());
				log.setUsernameb(relation.getUsernameb());
				log.setCreateTime(new Date());
				log.setOperation("delete");
				hibernate.save(log);
			} catch (Exception e) {
				logger.error("保存删除日志失败：" + e.getMessage());
			}
			
			hibernate.delete(relation);
		}
	}
	
	/** 
	 * @Description: 搜索用户信息
	 * @param searchText 
	 * @return:  Page
	 */
	@Override
	public Page searchUserList(Page page,String searchText){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		String sql = "SELECT T.USERNAME, T.REAL_NAME,W.WECHAT_HEAD_IMG,A.ID APPLY_ID,DECODE(A.ID,NULL,0,1) IS_APPLY,A.USERNAME APPLY_USERNAME,A.TARGET_USERNAME APPLY_TARGET"
				+ " FROM T_SYS_USER T LEFT JOIN T_WECHAT_BIND W ON T.USERNAME = W.USERNAME "
				+ " LEFT JOIN T_LIAO_RELATION_APPLY A ON ((A.USERNAME = T.USERNAME AND A.TARGET_USERNAME = '"+username+"') "
				+ "OR (A.TARGET_USERNAME = T.USERNAME  AND A.USERNAME = '"+username+"')) AND A.STATE = 0  "
				+ " WHERE T.USERNAME != '"+username+"' AND(T.USERNAME LIKE '%"+searchText+"%' OR T.REAL_NAME LIKE '%"+searchText+"%')"
				+ " AND T.USERNAME NOT IN "
					+ "(SELECT USERNAMEA USERNAME FROM T_LIAO_RELATION R WHERE R.USERNAMEB = '"+username+"' "
					+ "	UNION "
					+ "	SELECT USERNAMEB USERNAME FROM T_LIAO_RELATION R WHERE R.USERNAMEA = '"+username+"')"
			    + " AND T.USERNAME != 'admin'"
				+ " ORDER BY T.REAL_NAME ";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	
	@Override
	public List<Map<String, Object>> queryFriendApplyMessageOfUser(String username) {
		String sql = "SELECT  TT.*,S.REAL_NAME,ST.REAL_NAME TARGET_REAL_NAME FROM ("
				+ "SELECT T.*,W.WECHAT_HEAD_IMG,1 IS_MINE FROM T_LIAO_RELATION_APPLY T"
				+ " LEFT JOIN T_WECHAT_BIND W ON W.USERNAME = T.TARGET_USERNAME"
				+ " WHERE T.USERNAME = '"+username+"' AND (T.STATE = 1 OR T.STATE = 2)"
				+ " UNION"
				+ " SELECT T.*,W.WECHAT_HEAD_IMG,0 IS_MINE FROM T_LIAO_RELATION_APPLY T"
				+ " LEFT JOIN T_WECHAT_BIND W ON W.USERNAME = T.USERNAME"
				+ " WHERE T.TARGET_USERNAME = '"+username+"' AND T.STATE = 0"
				+ ")TT INNER JOIN T_SYS_USER S  ON TT.USERNAME = S.USERNAME"
				+ " INNER JOIN T_SYS_USER ST  ON TT.TARGET_USERNAME = ST.USERNAME"
				+ " ORDER BY CREATE_TIME DESC";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/** 
	* @Description: 改变未读消息的状态
	* @return: void 
	*/
	@Override
	@Transactional
	public void changeFriendUnreadMessageState(String username){
		String sql = "update T_LIAO_RELATION_APPLY T SET T.STATE = 3 WHERE T.USERNAME = '"+username+"' AND (T.STATE = 1 OR T.STATE = 2)";
		baseDao.update(sql);
	}
	
	/** 
	* @Description: 检查两个人是不是好友关系
	* @param usernamea
	* @param usernameb
	* @return: boolean
	*/
	public boolean isFriend(String usernamea,String usernameb){
		String sql = "SELECT T.ID FROM T_LIAO_RELATION T "
				+ " WHERE (T.USERNAMEA= '"+usernamea+"' AND T.USERNAMEB = '"+usernameb+"') "
				+ " OR (T.USERNAMEB = '"+usernamea+"' AND T.USERNAMEA = '"+usernameb+"')";
		int counts = baseDao.queryForInt(sql);
		if (counts == 0) {
			return false;
		}else{
			return true;
		}
	}
}