package com.jhkj.mosdc.permission.dao;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.po.TbJzgxx;
import com.jhkj.mosdc.framework.util.Page;
import com.jhkj.mosdc.permission.po.TbLzBm;
import com.jhkj.mosdc.permission.po.TsUser;
import com.jhkj.mosdc.permission.po.TsUserDataPermiss;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.xggl.xjgl.po.TbXjdaXjxx;
import com.jhkj.mosdc.xxzy.po.TbXxzyJxzzjg;
import com.jhkj.mosdc.xxzy.po.TbXxzyXzzzjg;

/**
 * @Comments: 用户信息DAO接口
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-5-16
 * @TIME: 下午06:22:34
 */
public interface UserDao {
	
	public UserInfo checkLogin(Map<String,Object> user) throws ClassNotFoundException;
	/**
	 * 检查用户名称是否已经存在
	 * @param userName
	 * @throws Exception
	 */
	public Boolean checkUserNameIsExists(Long id,String userName) throws Exception;
	/**
	 * 
	 * 功能说明：查询劳资部门树
	 * @param pId
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-10 @TIME: 下午8:53:05
	 */
	public List<TbLzBm> queryLzBmTree(String pId);
	/**
	 * 
	 * 功能说明：根据上级编码查找有没有子类
	 * @param lbm
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-10 @TIME: 下午9:20:33
	 */
	public Integer sumChildById(String lbm);
	/**
	 * 
	 * 功能说明：保存用户信息
	 * @param tsUser
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-14 @TIME: 上午12:12:47
	 */
	public TsUser addTsUser(TsUser tsUser)throws Exception;;
	/**
	 * 
	 * 功能说明：更新用户信息
	 * @param tsUser
	 * @param roles
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-14 @TIME: 上午12:14:08
	 */
	public TsUser updateTsUser(TsUser tsUser,String roles)throws Exception;
	/**
	 * 
	 * 功能说明：保存用户角色关系表
	 * @param userId
	 * @param roles
	 * @param managerId 管理者ＩＤ	 
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-16 @TIME: 下午2:36:35
	 */
	public void saveUserRelaRole(Long  userId, String roles,Long managerId) throws Exception;
	/**
	 * 
	 * 功能说明：查询用户角色
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-16 @TIME: 下午3:14:33
	 */
	public  List<Object[]>  queryUserRoles(Long params)  throws Exception;
	/**
	 * 
	 * 功能说明：查询用户信息，自己组sql实现
	 * @param pageParam
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-16 @TIME: 下午5:05:47
	 */
	public Page queryTsUserList(PageParam pageParam,Map paramMap,String bmId) throws Exception;
	
	/**
	 * 
	 * 功能说明：根据用户标识返回用户角色权限
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-17 @TIME: 上午11:57:42
	 */
	public  List<Object[]>  queryUserRolesByUserId(Long userId)  throws Exception;
	/**
	 * 
	 * 功能说明：重置用户密码
	 * @param userId
	 * @param defaultPassword
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-7-8 @TIME: 下午1:02:49
	 */
	public void resetPassword(Long userId,String defaultPassword) throws Exception;
	/**
	 * 
	 * 功能说明：根据用户标识获取到用户
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-7-8 @TIME: 下午1:04:55
	 */
	public TsUser getUserById(Long userId) throws Exception;
	/**
	 * 
	 * 功能说明：根据代码查找默认密码
	 * @param code
	 * @return
	 * @throws Exception
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-7-8 @TIME: 下午1:35:50
	 */
	public String queryDefaultPassword(String code)throws Exception;
	/**
	 * 获取单个职工信息
	 * @param id
	 * @return
	 */
	public TbJzgxx queryTbJzgxx(Long id);
	/**
	 * 获取单个学生信息
	 * @param id
	 * @return
	 */
	public TbXjdaXjxx queryTbXjdaXjxx(Long id);
	/**
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void updateResetUserZt(Long userId) throws Exception;
	/**
	 * 获取教学组织机构树
	 * @param pId
	 * @return
	 */
	public List<TbXxzyJxzzjg> queryTbJxzzjgTree(String pId);
	
	/**
	 * 通过教学组织机构ID和人员类别ID获取用户信息
	 * @param pageParam
	 * @param jxzzjgId
	 * @param rylbId
	 * @return
	 * @throws Exception
	 */
	public Page queryTsUserList(PageParam pageParam, Long jxzzjgId, Long rylbId,Long groupId)
			throws Exception;
	/**
	 * 查询教学组织机构树
	 * @param pId
	 * @return
	 */
	public List<TbXxzyJxzzjg> queryJxzzjgTree(Long pId);
	/**
	 * 行政组织机构树
	 * @param pId
	 * @return
	 */
	public List<TbXxzyXzzzjg> queryZzjgTree();
	/**
	 * 查询组织机构的类别
	 * @param zzjgId
	 * @return
	 */
	public int queryZzjglb(Long zzjgId);
	/**
	 * 保存数据权限
	 * @param dataPermiss
	 * @throws Exception
	 */	
	public void saveUserPermiss(TsUserDataPermiss dataPermiss) throws Exception;
	/**
	 * 查询当前用户是否存在当前组织机构树节点的权限
	 * @param userId
	 * @param bmId
	 * @return
	 */
	public List<TsUserDataPermiss> getDataPermiss(Long userId);
	/**
	 * 删除数据权限
	 * @param dataPermiss
	 */
	public void deleteDataPermiss(List<TsUserDataPermiss> dataPermiss);
	/**
	 * 获取数据权限
	 * @param userId
	 * @return
	 */
	public List queryUserDataPermissList(Long userId);
	/**
	 * 获取用户数据权限
	 * @param userId
	 * @param nodeIds
	 * @return
	 */
	 public List getUserDataPermiss(Long userId, String nodeIds);
	
	public List<TsUserDataPermiss> getUserPermiss(Long userId);
	/***
	 * 是否存在于教学组织机构表中
	 * @param zzjgId
	 * @return
	 */
	public Long hasTbJxzzjg(Long zzjgId);
	/**
	 * 更新用户信息
	 * @param tsUser
	 */
	public void updateTsUser(TsUser tsUser);
	/**
	 * 查询数据权限是否存在
	 * @param userId
	 * @return
	 */
	public List<TsUserDataPermiss> queryDataPermissEntity(Long userId);
	/**
	 * 当前用户所在处组织机构树
	 * @param bmIds
	 * @return
	 * @throws Exception 
	 */
	public List getZzjgTreeChild(String bmId) throws Exception;
	/**
	 * 当前节点的下一级所有节点
	 * @param bmIds
	 * @return
	 * @throws Exception
	 */
	public Map getZzjgTreeNode(String bmId) throws Exception;
	/**
	 * 获取教学组织机构节点的父节点信息
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List getJxzzjgNodeParents(String parentId) throws Exception;
	/**
	 * 获取行政组织机构的父节点信息
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List getXzzzjgNodeParents(String parentId) throws Exception;
	/***
	 * 根据用户名 密码，验证该密码的正确性
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean queryAndCheckOldPassword(String userId,String password) throws Exception;
	/**
	 * 获取教学组织机构的顶级节点
	 * @param pId
	 * @return
	 */
	public List getJxzzjgRoot(String pId);
	/**
	 * 获取教学组织机构的所有节点
	 * @return
	 */
	public List getJxzzjgNodes();
	/**
	 * 获取行政组织机构所有节点
	 * @return
	 */
	public List getXzzzjgNodes();
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	public void saveTsUser(TsUser user);
	/**
	 * 查询用户列表
	 * @param ids
	 * @return
	 */
	public List queryTsUserList(String ids);

}