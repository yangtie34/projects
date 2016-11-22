package com.jhnu.edu.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.jhnu.edu.dao.UserDao;
import com.jhnu.edu.entity.User;
import com.jhnu.framework.base.dao.BaseDao;
import com.jhnu.framework.page.Page;
import com.jhnu.framework.util.getSql.SqlByBeans;
import com.jhnu.syspermiss.permiss.entity.UserRole;
import com.jhnu.syspermiss.util.DateUtils;


@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	private BaseDao baseDao;
    public User createUser(final User user) {
        final String SQL = "insert into t_sys_user values(?,?,?,?,?,?,?,?)";
        final String ID =baseDao.getId();
        baseDao.update(SQL, new Object[]{ID,user.getUsername(),user.getPassword(),user.getReal_name(),user.getSalt(),user.getIstrue()
        		,DateUtils.SSS.format(new Date()),DateUtils.SSS.format(new Date())});
        user.setId(ID);
        return user;
    }

    public void updateUser(User user) {
        String sql = "update t_sys_user set username=?, password=?, salt=?, istrue=?,real_name=?,update_time=? where id=?";
        baseDao.update(sql, new Object[]{user.getUsername(), user.getPassword(), user.getSalt(), user.getIstrue(),user.getReal_name(), DateUtils.SSS.format(new Date()),user.getId()});
    }

    public void deleteUserById(Long userId) {
        String sql = "delete from t_sys_user where id=?";
        baseDao.update(sql, userId);
    }

    @Override
    public void correlationRoles(final Long userId, final Long... roleIds) {
		String delsql="delete from t_sys_user_role where user_id= ? ";
		baseDao.update(delsql,new Object[]{userId});
		for(int i=0;i<roleIds.length;i++){
			String sql ="insert into t_sys_user_role values(?,?,?)";
			baseDao.update(sql,new Object[]{baseDao.getId(),userId,roleIds[i]});
		}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public User findOne(Long userId) {
        String sql = "select id, username, password,real_name, salt, istrue from t_sys_user where id='"+userId+"'";
        List<User> userList = baseDao.query(sql,User.class);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public User findByUsername(String username) {
    	
        String sql = "select id, username, password,real_name, salt, istrue from t_sys_user where username='"+username+"'";
        List<User> userList = baseDao.query(sql,User.class);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Set<String> findRoles(String username) {
        String sql = "select r.name_ from t_sys_user u, t_sys_role r,t_sys_user_role ur where u.username=? and u.id=ur.user_id and r.id=ur.role_id and r.istrue=1";
        return new HashSet(baseDao.queryForList(sql, String.class, username));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Set<String> findPermissions(String username) {
        String sql1= "select rp.wirldcard from t_sys_user u, t_sys_role r, t_sys_user_role ur, t_sys_role_perm rp where u.username=? "
        		+ "and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and r.istrue=1 ";
        String sql2= "select up.wirldcard from t_sys_user u, t_sys_user_perm up where u.username = ? and u.id = up.user_id ";
        String sql="select distinct wirldcard from ( "+sql1+" union all "+sql2+" )";
        return new HashSet(baseDao.queryForList(sql, String.class, new Object[] { username,username }));
    }
    
    @Override
    public void addUserLogging(String username, String loginDate,String loginWay) {
    	String sql ="insert into t_sys_user_logging values(?,?,?,?)";
    	String id = baseDao.getId();
    	baseDao.update(sql, id,username,loginDate,loginWay);
    }
    
    @Override
    public void freezeUsers(final List<Long> userIds) {
    	for(int i=0;i<userIds.size();i++){
    		String sql ="update t_sys_user set istrue=0 where id=?";
    		baseDao.update(sql,new Object[]{userIds.get(i)});
    	}
    }
    
    @Override
    public void unfreezeUsers(final List<Long> userIds) {
    	for(int i=0;i<userIds.size();i++){
    		String sql ="update t_sys_user set istrue=1 where id=?";
			baseDao.update(sql,new Object[]{userIds.get(i)});
		}
    }
    
    @Override
    public void createUsers(final List<User> users) {
    	final String now = DateUtils.SSS.format(new Date()); 
    	for(int i=0;i<users.size();i++){
    		users.get(i).setCreate_time(now);
    		users.get(i).setUpdate_time(now);
    		users.get(i).setId(baseDao.getId());
    	}
    	String sql=new SqlByBeans().insertSql(users);
    	baseDao.execute(sql);
    }
    
    @Override
    public void correlationRoles(final List<UserRole> userRoles) {
    	
    	for(int i=0;i<userRoles.size();i++){
    		String sql ="insert into t_sys_user_role values(?,?,?)";
    		UserRole ur = userRoles.get(i);
			baseDao.update(sql,new Object[]{baseDao.getId(),ur.getUserId(),ur.getRoleId()});
		}
    }
    
    @Override
    public String getUserLastLoginTime(String userName) {
    	String sql="SELECT MAX(LOGIN_DATE) max_ FROM T_SYS_USER_LOGGING WHERE USERNAME=?";
    	Map<String, Object> map= baseDao.queryForList(sql,  new Object[]{userName}).get(0);
    	return (String) map.get("MAX_");
    }

	@Override
	public List<String> getRoleNamesByUserId(String userId) {
		String sql="SELECT NAME_ NAME FROM T_SYS_ROLE SR INNER JOIN T_SYS_USER_ROLE SUR ON SR.ID=SUR.ROLE_ID WHERE SUR.USER_ID=? ";
		List<Map<String, Object>> list= baseDao.queryForList(sql, String.class, new Object[]{userId});
		List<String> l=new LinkedList<String>();
		for(int i=0;i<list.size();i++){
			l.add((String) list.get(i).get("NAME"));
		}
		 return l;
	}

	@Override
	public Page getPageUsers(int currentPage, int numPerPage,User user) {
		StringBuffer sqlb=new StringBuffer("select u.id, u.dept_id,u.username,u.real_name,u.istrue,"+
				"u.create_time,u.update_time,d.name_ dept_name, "+
		        "wm_concat(r.id) role_ids, "+
		        "wm_concat(r.name_) role_names, "+
		        "wm_concat(r.description) role_descs "+
			    "from t_sys_user u "+
			    "left join t_sys_user_role ur on u.id = ur.user_id "+
			    "left join t_sys_role r  on ur.role_id = r.id "+
			    "left join t_code_dept d on u.dept_id = d.id "+ 
			    "where r.istrue = 1");
		if(user !=null ){
			if(user.getId()!=null){
				sqlb.append(" AND U.ID = "+user.getId());
			}
			if(StringUtils.hasLength(user.getUsername())){
				sqlb.append(" AND U.USERNAME LIKE '%"+user.getUsername()+"%' ");
			}
			if(StringUtils.hasLength(user.getReal_name())){
				sqlb.append(" AND U.REAL_NAME LIKE '%"+user.getReal_name()+"%' ");
			}
			if(user.getIstrue()!=null){
				sqlb.append(" AND U.ISTRUE = "+user.getIstrue());
			}
			if(StringUtils.hasLength(user.getDept_id())){
				sqlb.append(" AND U.dept_id = '"+user.getDept_id()+"' ");
			}
			if(StringUtils.hasLength(user.gotRole_ids())){
				sqlb.append(" AND u.id in (select sur.user_id from t_sys_user_role sur where sur.role_id= "+user.gotRole_ids()+" ) ");
			}
			if(StringUtils.hasLength(user.gotUsernameOrRealName())){
				sqlb.append(" AND (U.USERNAME LIKE '%"+user.gotUsernameOrRealName()+"%' or U.REAL_NAME LIKE '%"+user.gotUsernameOrRealName()+"%') ");
			}
		}
		sqlb.append(" group by u.id, u.dept_id,u.username,u.real_name,u.istrue,u.create_time,u.update_time,d.name_ ");
		return  new Page(sqlb.toString(), currentPage, numPerPage, baseDao.getJdbcTemplate()
				, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<User> getAllUsers(User user) {
		StringBuffer sqlb=new StringBuffer("select u.id, u.dept_id,u.username,u.real_name,u.istrue,"+
				"u.create_time,u.update_time,d.name_ dept_name, "+
		        "wm_concat(r.id) rode_ids, "+
		        "wm_concat(r.name_) role_names, "+
		        "wm_concat(r.description) role_descs "+
			    "from t_sys_user u "+
			    "left join t_sys_user_role ur on u.id = ur.user_id "+
			    "left join t_sys_role r  on ur.role_id = r.id "+
			    "left join t_code_dept d on u.dept_id = d.id "+ 
			    "where r.istrue = 1");
		if(user !=null ){
			if(user.getId()!=null){
				sqlb.append(" AND U.ID = "+user.getId());
			}
			if(StringUtils.hasLength(user.getUsername())){
				sqlb.append(" AND U.USERNAME LIKE '%"+user.getUsername()+"%' ");
			}
			if(StringUtils.hasLength(user.getReal_name())){
				sqlb.append(" AND U.REAL_NAME LIKE '%"+user.getReal_name()+"%' ");
			}
			if(user.getIstrue()!=null){
				sqlb.append(" AND U.ISTRUE = "+user.getIstrue());
			}
			if(StringUtils.hasLength(user.getDept_id())){
				sqlb.append(" AND U.dept_id = '"+user.getDept_id()+"' ");
			}
			if(StringUtils.hasLength(user.gotRole_ids())){
				sqlb.append(" AND u.id in (select sur.user_id from t_sys_user_role sur where sur.role_id= "+user.gotRole_ids()+" ) ");
			}
			if(StringUtils.hasLength(user.gotUsernameOrRealName())){
				sqlb.append(" AND (U.USERNAME LIKE '%"+user.gotUsernameOrRealName()+"%' or U.REAL_NAME LIKE '%"+user.gotUsernameOrRealName()+"%') ");
			}
		}
		sqlb.append(" group by u.id, u.dept_id,u.username,u.real_name,u.istrue,u.create_time,u.update_time,d.name_ ");
		return baseDao.query(sqlb.toString(), User.class);
	}

	@Override
	public int getUserCount(User user) {
		StringBuffer sqlb=new StringBuffer("select count(*) COUNT_ from (select * from t_sys_user u "+
										"left join t_sys_user_role ur on u.id = ur.user_id "+
										"left join t_sys_role r on ur.role_id = r.id "+
										"where r.istrue = 1 ");
		if(user !=null ){
			if(user.getId()!=null){
				sqlb.append(" AND U.ID = "+user.getId());
			}
			if(StringUtils.hasLength(user.getUsername())){
				sqlb.append(" AND U.USERNAME LIKE '%"+user.getUsername()+"%' ");
			}
			if(StringUtils.hasLength(user.getReal_name())){
				sqlb.append(" AND U.REAL_NAME LIKE '%"+user.getReal_name()+"%' ");
			}
			if(user.getIstrue()!=null){
				sqlb.append(" AND U.ISTRUE = "+user.getIstrue());
			}
			if(StringUtils.hasLength(user.getDept_id())){
				sqlb.append(" AND U.dept_id = '"+user.getDept_id()+"' ");
			}
			if(StringUtils.hasLength(user.gotRole_ids())){
				sqlb.append(" AND r.id ="+user.gotRole_ids());
			}
		}
		sqlb.append(" group by u.id )");
		return Integer.parseInt((String) baseDao.queryForList(sqlb.toString()).get(0).get("COUNT_"));
	}
	
}
