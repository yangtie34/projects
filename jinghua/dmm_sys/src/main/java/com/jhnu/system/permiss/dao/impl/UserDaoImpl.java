package com.jhnu.system.permiss.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.enums.DeptEnum;
import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.dao.UserDao;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserRole;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.PropertiesUtils;
import com.jhnu.util.common.StringUtils;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
    public User createUser(final User user) {
        final String SQL = "insert into t_sys_user values(?,?,?,?,?,?,?,?,?,?,?)";
        final long ID =baseDao.getBaseDao().getSeqGenerator().nextLongValue();
        baseDao.getBaseDao().getJdbcTemplate().update(SQL, new Object[]{ID,user.getUsername(),user.getPassword(),user.getReal_name(),user.getSalt(),user.getIstrue()
        		,DateUtils.SSS.format(new Date()),DateUtils.SSS.format(new Date()),user.getDept_id(),user.getDept_category_code(),user.getId_no()});
        user.setId(ID);
        return user;
    }
    
    @Override
    public void createUsers(final List<User> users) {
    	final String sql = "insert into t_sys_user values(?,?,?,?,?,?,?,?,?,?,?)";
    	baseDao.getBaseDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				User user = users.get(i);
				if(user.getId()!=null){
					ps.setLong(1, user.getId());
				}else{
					Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
					ps.setLong(1, id);
				}
				ps.setString(2, user.getUsername());
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getReal_name());
				ps.setString(5, user.getSalt());
				ps.setInt(6, user.getIstrue());
				ps.setString(7, user.getCreate_time());
				ps.setString(8, user.getCreate_time());
				ps.setString(9, user.getDept_id());
				ps.setString(10, user.getDept_category_code());
				ps.setString(11, user.getId_no());
			}
			
			@Override
			public int getBatchSize() {
				return users.size();
			}
		});
    }

    public void updateUser(User user) {
        String sql = "update t_sys_user set username=?, password=?, salt=?, istrue=?,real_name=?,update_time=? where id=?";
        baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[]{user.getUsername(), user.getPassword(), user.getSalt(), user.getIstrue(),user.getReal_name(), DateUtils.SSS.format(new Date()),user.getId()});
    }

    public void deleteUserById(Long userId) {
        String sql = "delete from t_sys_user where id=?";
        baseDao.getBaseDao().getJdbcTemplate().update(sql, userId);
    }

    @Override
    public void correlationRoles(final Long userId, final Long... roleIds) {
		String delsql="delete from t_sys_user_role where user_id= ? ";
		baseDao.getBaseDao().getJdbcTemplate().update(delsql,new Object[]{userId});
		String sql ="insert into t_sys_user_role values(?,?,?)";
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
				ps.setLong(1, id);
				ps.setLong(2, userId);
				ps.setLong(3, roleIds[i]);
			}
			
			@Override
			public int getBatchSize() {
				return roleIds.length;
			}
		});
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public User findOne(Long userId) {
        String sql = "select id, username, password,real_name, salt, istrue from t_sys_user where id=?";
        List<User> userList = baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(User.class), userId);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public User findByUsername(String username) {
    	
        String sql = "select id, username, password,real_name, salt, istrue from t_sys_user where username=?";
        List<User> userList = baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(User.class), username);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Set<String> findRoles(String username) {
        String sql = "select r.name_ from t_sys_user u, t_sys_role r,t_sys_user_role ur where u.username=? and u.id=ur.user_id and r.id=ur.role_id and r.istrue=1";
        return new HashSet(baseDao.getBaseDao().getJdbcTemplate().queryForList(sql, String.class, username));
    }
    @Override
    public List<Role> findRolesList(String username) {
        String sql = "select r.* from t_sys_user u, t_sys_role r,t_sys_user_role ur where u.username=? and u.id=ur.user_id and r.id=ur.role_id and r.istrue=1";
        return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql, Role.class, username);
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Set<String> findPermissions(String username) {
        String sql1= "select rp.wirldcard from t_sys_user u, t_sys_role r, t_sys_user_role ur, t_sys_role_perm rp where u.username=? "
        		+ "and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and r.istrue=1 ";
        String sql2= "select up.wirldcard from t_sys_user u, t_sys_user_perm up where u.username = ? and u.id = up.user_id ";
        String sql="select distinct wirldcard from ( "+sql1+" union all "+sql2+" )";
        sql="select a.id from t_sys_resources r inner join ( "+sql+" ) a on r.shiro_tag=substr(a.id,0,instr(a.id,':',-1,1)-1) where r.istrue=1 ";
        return new HashSet(baseDao.getBaseDao().getJdbcTemplate().queryForList(sql, String.class, new Object[] { username,username }));
    }
    
    @Override
    public void addUserLogging(String username, String loginDate,String loginWay) {
    	String sql ="insert into t_sys_user_logging values(?,?,?,?)";
    	long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
    	baseDao.getBaseDao().getJdbcTemplate().update(sql, id,username,loginDate,loginWay);
    }
    
    @Override
    public int[] freezeUsers(final List<Long> userIds) {
    	String sql ="update t_sys_user set istrue=0 where id=?";
    	return baseDao.getBaseDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, userIds.get(i));
			}
			
			@Override
			public int getBatchSize() {
				return userIds.size();
			}
		});
    }
    
    @Override
    public void unfreezeUsers(final List<Long> userIds) {
    	String sql ="update t_sys_user set istrue=1 where id=?";
    	baseDao.getBaseDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, userIds.get(i));
			}
			
			@Override
			public int getBatchSize() {
				return userIds.size();
			}
		});
    }
    
    @Override
    public void correlationRoles(final List<UserRole> userRoles) {
		String sql ="insert into t_sys_user_role values("+PropertiesUtils.getDBPropertiesByName("dataSource.seq")+".nextval,?,?)";
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				UserRole ur = userRoles.get(i);
				ps.setLong(1, ur.getUserId());
				ps.setLong(2, ur.getRoleId());
			}
			
			@Override
			public int getBatchSize() {
				return userRoles.size();
			}
		});
		
    }
    
    @Override
    public String getUserLastLoginTime(String userName) {
    	String sql="SELECT MAX(LOGIN_DATE) FROM T_SYS_USER_LOGGING WHERE USERNAME=?";
    	return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, String.class, new Object[]{userName});
    }

	@Override
	public List<String> getRoleNamesByUserId(long userId) {
		String sql="SELECT NAME_ NAME FROM T_SYS_ROLE SR INNER JOIN T_SYS_USER_ROLE SUR ON SR.ID=SUR.ROLE_ID WHERE SUR.USER_ID=? ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql, String.class, new Object[]{userId});
	}

	@Override
	public Page getPageUsers(int currentPage, int numPerPage,User user) {
		StringBuffer sqlb=new StringBuffer("SELECT U.ID, U.DEPT_ID,U.USERNAME,U.REAL_NAME,U.ISTRUE,"+
				"U.CREATE_TIME,U.UPDATE_TIME,CASE WHEN U.DEPT_CATEGORY_CODE='"+DeptEnum.JX.getCode()+"' THEN DT.NAME_ ELSE D.NAME_ END DEPT_NAME, "+
		        "WM_CONCAT(R.ID) ROLE_IDS, "+
		        "WM_CONCAT(R.NAME_) ROLE_NAMES, "+
		        "WM_CONCAT(R.DESCRIPTION) ROLE_DESCS "+
			    "FROM T_SYS_USER U "+
			    "LEFT JOIN T_SYS_USER_ROLE UR ON U.ID = UR.USER_ID "+
			    "LEFT JOIN T_SYS_ROLE R  ON  ( UR.ROLE_ID = R.ID AND R.ISTRUE = 1 )  "+
			    "LEFT JOIN T_CODE_DEPT D ON (U.DEPT_ID = D.ID AND U.DEPT_CATEGORY_CODE='"+DeptEnum.ALL.getCode()+"') "+
			    "LEFT JOIN T_CODE_DEPT_TEACH DT ON (U.DEPT_ID = DT.ID AND U.DEPT_CATEGORY_CODE='"+DeptEnum.JX.getCode()+"') "+ 
			    "WHERE 1 = 1  ");
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
				sqlb.append(" AND U.ISTRUE "
						+ " = "+user.getIstrue());
			}
			if(StringUtils.hasLength(user.getDept_id())&&StringUtils.hasLength(user.getDept_teach_id())){
				sqlb.append(" AND ( ( U.DEPT_ID = '"+user.getDept_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.ALL.getCode()+"' ) ");
				sqlb.append(" OR ( U.DEPT_ID = '"+user.getDept_teach_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.JX.getCode()+"' ) ) ");
			}else if(StringUtils.hasLength(user.getDept_id())){
				sqlb.append(" AND U.DEPT_ID = '"+user.getDept_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.ALL.getCode()+"' ");
			}else if(StringUtils.hasLength(user.getDept_teach_id())){
				sqlb.append(" AND U.DEPT_ID = '"+user.getDept_teach_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.JX.getCode()+"' ");
			}
			if(StringUtils.hasLength(user.getRole_ids())){
				sqlb.append(" AND U.ID IN (SELECT SUR.USER_ID FROM T_SYS_USER_ROLE SUR WHERE SUR.ROLE_ID= "+user.getRole_ids()+" ) ");
			}
			if(StringUtils.hasLength(user.getUsernameOrRealName())){
				sqlb.append(" AND (U.USERNAME LIKE '%"+user.getUsernameOrRealName()+"%' or U.REAL_NAME LIKE '%"+user.getUsernameOrRealName()+"%') ");
			}
		}
		sqlb.append(" GROUP BY U.ID, U.DEPT_ID,U.USERNAME,U.REAL_NAME,U.ISTRUE,U.CREATE_TIME,U.UPDATE_TIME,U.DEPT_CATEGORY_CODE,DT.NAME_ ,D.NAME_ ");
		return new Page(sqlb.toString(),currentPage, numPerPage ,baseDao.getBaseDao().getJdbcTemplate(),getUserCount(user),user);
	}

	@Override
	public List<Long> getUserIdsByAddTime(String addTime){
		String sql="select id from t_sys_user where create_time = ? ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{addTime}, Long.class);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<User> getAllUsers(User user) {
		StringBuffer sqlb=new StringBuffer("SELECT U.ID, U.DEPT_ID,U.USERNAME,U.REAL_NAME,U.ISTRUE,"+
				"U.CREATE_TIME,U.UPDATE_TIME,CASE WHEN U.DEPT_CATEGORY_CODE='"+DeptEnum.JX.getCode()+"' THEN DT.NAME_ ELSE D.NAME_ END DEPT_NAME, "+
		        "WM_CONCAT(R.ID) ROLE_IDS, "+
		        "WM_CONCAT(R.NAME_) ROLE_NAMES, "+
		        "WM_CONCAT(R.DESCRIPTION) ROLE_DESCS "+
			    "FROM T_SYS_USER U "+
			    "LEFT JOIN T_SYS_USER_ROLE UR ON U.ID = UR.USER_ID "+
			    "LEFT JOIN T_SYS_ROLE R  ON  ( UR.ROLE_ID = R.ID AND R.ISTRUE = 1 )  "+
			    "LEFT JOIN T_CODE_DEPT D ON (U.DEPT_ID = D.ID AND U.DEPT_CATEGORY_CODE='"+DeptEnum.ALL.getCode()+"') "+
			    "LEFT JOIN T_CODE_DEPT_TEACH DT ON (U.DEPT_ID = DT.ID AND U.DEPT_CATEGORY_CODE='"+DeptEnum.JX.getCode()+"') "+ 
			    "WHERE 1 = 1  ");
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
				sqlb.append(" AND U.ISTRUE "
						+ " = "+user.getIstrue());
			}
			if(StringUtils.hasLength(user.getDept_id())&&StringUtils.hasLength(user.getDept_teach_id())){
				sqlb.append(" AND ( ( U.DEPT_ID = '"+user.getDept_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.ALL.getCode()+"' ) ");
				sqlb.append(" OR ( U.DEPT_ID = '"+user.getDept_teach_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.JX.getCode()+"' ) ) ");
			}else if(StringUtils.hasLength(user.getDept_id())){
				sqlb.append(" AND U.DEPT_ID = '"+user.getDept_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.ALL.getCode()+"' ");
			}else if(StringUtils.hasLength(user.getDept_teach_id())){
				sqlb.append(" AND U.DEPT_ID = '"+user.getDept_teach_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.JX.getCode()+"' ");
			}
			
			if(StringUtils.hasLength(user.getRole_ids())){
				sqlb.append(" AND U.ID IN (SELECT SUR.USER_ID FROM T_SYS_USER_ROLE SUR WHERE SUR.ROLE_ID= "+user.getRole_ids()+" ) ");
			}
			if(StringUtils.hasLength(user.getUsernameOrRealName())){
				sqlb.append(" AND (U.USERNAME LIKE '%"+user.getUsernameOrRealName()+"%' or U.REAL_NAME LIKE '%"+user.getUsernameOrRealName()+"%') ");
			}
		}
		sqlb.append(" GROUP BY U.ID, U.DEPT_ID,U.USERNAME,U.REAL_NAME,U.ISTRUE,U.CREATE_TIME,U.UPDATE_TIME,U.DEPT_CATEGORY_CODE,DT.NAME_ ,D.NAME_ ");
		return baseDao.getBaseDao().getJdbcTemplate().query(sqlb.toString(), (RowMapper) new BeanPropertyRowMapper(User.class));
	}

	@Override
	public int getUserCount(User user) {
		StringBuffer sqlb=new StringBuffer("SELECT COUNT(*) FROM (SELECT U.ID FROM T_SYS_USER U "+
										"LEFT JOIN T_SYS_USER_ROLE UR ON U.ID = UR.USER_ID "+
										"LEFT JOIN T_SYS_ROLE R ON ( UR.ROLE_ID = R.ID AND R.ISTRUE = 1 ) "+
										"WHERE 1 = 1  ");
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
			if(StringUtils.hasLength(user.getDept_id())&&StringUtils.hasLength(user.getDept_teach_id())){
				sqlb.append(" AND ( ( U.DEPT_ID = '"+user.getDept_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.ALL.getCode()+"' ) ");
				sqlb.append(" OR ( U.DEPT_ID = '"+user.getDept_teach_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.JX.getCode()+"' ) ) ");
			}else if(StringUtils.hasLength(user.getDept_id())){
				sqlb.append(" AND U.DEPT_ID = '"+user.getDept_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.ALL.getCode()+"' ");
			}else if(StringUtils.hasLength(user.getDept_teach_id())){
				sqlb.append(" AND U.DEPT_ID = '"+user.getDept_teach_id()+"' ");
				sqlb.append(" AND U.DEPT_CATEGORY_CODE = '"+DeptEnum.JX.getCode()+"' ");
			}
			if(StringUtils.hasLength(user.getRole_ids())){
				sqlb.append(" AND R.ID ="+user.getRole_ids());
			}
			if(StringUtils.hasLength(user.getUsernameOrRealName())){
				sqlb.append(" AND (U.USERNAME LIKE '%"+user.getUsernameOrRealName()+"%' or U.REAL_NAME LIKE '%"+user.getUsernameOrRealName()+"%') ");
			}
		}
		sqlb.append(" GROUP BY U.ID )");
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sqlb.toString(), Integer.class);
	}
	@Override
	public List<Map<String, Object>> getUserRoles(User user) {
		String sql ="select * "
				  +"from t_sys_role "
				  +"where role_type_code = "
				  +" (select r.role_type_code "
				  +"       from t_sys_user u, t_sys_role r, t_sys_user_role ur "
				  +"   where u.username = '"+user.getUsername()+"' "
				  +"         and u.id = ur.user_id "
				  +"        and r.id = ur.role_id "
				  +"         and r.istrue = 1 "
				  +"         and r.ismain = 1) ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
}
