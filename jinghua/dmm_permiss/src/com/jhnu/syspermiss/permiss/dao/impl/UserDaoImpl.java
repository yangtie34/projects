package com.jhnu.syspermiss.permiss.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jhnu.syspermiss.permiss.dao.UserDao;
import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.entity.User;
import com.jhnu.syspermiss.permiss.entity.UserRole;
import com.jhnu.syspermiss.util.DateUtils;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;

public class UserDaoImpl implements UserDao {
	private UserDaoImpl() {
		
	}  
    private static UserDaoImpl UserDaoImpl=null;
	
	public static UserDaoImpl getInstance() {
		if (UserDaoImpl == null){
			synchronized (new String()) {
				if (UserDaoImpl == null){
					UserDaoImpl = new UserDaoImpl();
				}
			}
		}
		return UserDaoImpl;
	}
	private BaseDao baseDao=BaseDao.getInstance();

	 public User createUser(User user) {
	        String SQL = "insert into t_sys_user values(?,?,?,?,?,?,?,?)";
	        long ID =0;
	        if(user.getId() == null){
	        	ID=user.getId();
	        }else{
	        	ID=baseDao.getSeq();
	        	user.setId(ID);
	        }
	        baseDao.excute(SQL,new Object[]{ID,user.getUsername(),user.getPassword(),user.getReal_name(),user.getSalt(),user.getIstrue()
	        		,DateUtils.SSS.format(new Date()),DateUtils.SSS.format(new Date())});
	        
	        return user;
	    }

	    public void updateUser(User user) {
	        String sql = "update t_sys_user set username=?, password=?, salt=?, istrue=?,real_name=?,update_time=? where id=?";
	        baseDao.excute(sql, new Object[]{user.getUsername(), user.getPassword(), user.getSalt(), user.getIstrue(),user.getReal_name(), DateUtils.SSS.format(new Date()),user.getId()});
	    }

	    public void deleteUserById(Long userId) {
	        String sql = "delete from t_sys_user where id=?";
	        baseDao.excute(sql, new Object[]{userId});
	    }

    @SuppressWarnings({ "unchecked" })
	@Override
    public User findOne(Long userId) {
        String sql = "select id, username, password,real_name, salt, istrue from t_sys_user where id=?";
        List<User> userList = baseDao.query(sql,User.class, userId);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public User findByUsername(String username) {
    	
        String sql = "select id, username, password,real_name, salt, istrue from t_sys_user where username=?";
        List<User> userList = baseDao.query(sql,User.class, username);
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
    @SuppressWarnings({ "unchecked" })
    @Override
    public List<Role> findRolesList(String username) {
        String sql = "select r.* from t_sys_user u, t_sys_role r,t_sys_user_role ur where u.username=? and u.id=ur.user_id and r.id=ur.role_id and r.istrue=1";
        return baseDao.query(sql, Role.class, username);
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Set<String> findPermissions(String username) {
    	//动作和冗余字段wirldcard不使用   
        /*String sql1= "select rp.wirldcard from t_sys_user u, t_sys_role r, t_sys_user_role ur, t_sys_role_perm rp where u.username=? "
        		+ "and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and r.istrue=1 ";
        String sql2= "select up.wirldcard from t_sys_user u, t_sys_user_perm up where u.username = ? and u.id = up.user_id ";
        String sql="select distinct wirldcard from ( "+sql1+" union all "+sql2+" )";
        sql="select a.id from t_sys_resources r inner join ( "+sql+" ) a on r.shiro_tag=substr(a.id,0,instr(a.id,':',-1,1)-1) where r.istrue=1 ";*/
        
        
        String sql1= "select rp.resource_id from t_sys_user u, t_sys_role r, t_sys_user_role ur, t_sys_role_perm rp where u.username=? "
        		+ "and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and r.istrue=1 ";
        String sql2= "select up.resource_id from t_sys_user u, t_sys_user_perm up where u.username = ? and u.id = up.user_id ";
        String sql="select distinct resource_id from ( "+sql1+" union all "+sql2+" )";
        sql="select r.shiro_tag||':*' id from t_sys_resources r where r.id in ( "+sql+" ) and r.istrue=1 ";
        return new HashSet(baseDao.queryForList(sql, String.class, new Object[] { username,username }));
    }
    
    @Override
    public String getUserLastLoginTime(String userName) {
    	String sql="SELECT MAX(LOGIN_DATE) FROM T_SYS_USER_LOGGING WHERE USERNAME=?";
    	return baseDao.queryForObject(sql, String.class, new Object[]{userName});
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRoleNamesByUserId(long userId) {
		String sql="SELECT NAME_ NAME FROM T_SYS_ROLE SR INNER JOIN T_SYS_USER_ROLE SUR ON SR.ID=SUR.ROLE_ID WHERE SUR.USER_ID=? ";
		return baseDao.queryForList(sql, String.class, new Object[]{userId});
	}


	@Override
	public void correlationRole(UserRole userRole) {
        final String SQL = "insert into t_sys_user_role values(?,?,?)";
        final long ID =baseDao.getSeq();
        baseDao.excute(SQL,new Object[]{ID,userRole.getUserId(),userRole.getRoleId()});
    }

	@Override
	public void noCorrelationRole(Long userId, Long roleId) {
		 final String SQL = "DELETE  t_sys_user_role where user_id=? and role_id=?";
	     baseDao.excute(SQL,new Object[]{userId,roleId});
	}

	@Override
	public void clearCorrelationRole(Long userId) {
		 final String SQL = "DELETE  t_sys_user_role where user_id=? ";
	     baseDao.excute(SQL,new Object[]{userId});
	}

}
