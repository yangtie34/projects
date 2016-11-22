package com.jhnu.syspermiss.permiss.service.impl;

import java.util.List;
import java.util.Set;

import com.jhnu.syspermiss.permiss.dao.UserDao;
import com.jhnu.syspermiss.permiss.dao.impl.UserDaoImpl;
import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.entity.User;
import com.jhnu.syspermiss.permiss.entity.UserRole;
import com.jhnu.syspermiss.permiss.service.UserService;
import com.jhnu.syspermiss.util.PasswordHelperUtil;

public class UserServiceImpl implements UserService{
	private UserServiceImpl() {
		
	}  
    private static UserServiceImpl UserServiceImpl=null;
	
	public static UserServiceImpl getInstance() {
		if (UserServiceImpl == null){
			synchronized (new String()) {
				if (UserServiceImpl == null){
					UserServiceImpl = new UserServiceImpl();
				}
			}
		}
		return UserServiceImpl;
	}
	private UserDao userDao=UserDaoImpl.getInstance();
	
	@Override
	public boolean checkPassword(Long userId, String password) {
		User user =userDao.findOne(userId);
		if(user ==null){
			return false;
		}
		String oldPwd=user.getPassword();
        user.setPassword(password);
        PasswordHelperUtil.encryptPassword(user,user.getSalt());
        if(oldPwd.equals(user.getPassword())){
        	return true;
        }else{
        	return false;
        }
	}
	
	@Override
    public void changePassword(Long userId, String newPassword) {
        User user =userDao.findOne(userId);
        user.setPassword(newPassword);
        PasswordHelperUtil.encryptPassword(user);
        userDao.updateUser(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Set<String> findRoles(String username) {
        return userDao.findRoles(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return userDao.findPermissions(username);
    }
	@Override
	public List<String> getRoleNamesByUserId(long userId) {
		return userDao.getRoleNamesByUserId(userId);
	}
	@Override
	public String getUserRootRole(String username) {
		Set<String> roles=userDao.findRoles(username);
		for (String role : roles) {  
			  if("admin".equals(role)){
				  return "admin";
			  }else if("student".equals(role)){
				  return "student";
			  }else if("teacher".equals(role)){
				  return "teacher";
			  }
		}
		return null;
	}

	@Override
	public User createUser(User user) {
		return userDao.createUser(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public void deleteUserById(Long userId) {
		userDao.deleteUserById(userId);
	}

	@Override
	public List<Role> findRolesList(String username) {
		return userDao.findRolesList(username);
	}
	
	
    @Override
    public void correlationRole(UserRole userRole) {
		userDao.correlationRole(userRole);
	}

	@Override
	public void noCorrelationRole(Long userId, Long roleId) {
		userDao.noCorrelationRole(userId, roleId);
		
	}

	@Override
	public void clearCorrelationRole(Long userId) {
		userDao.clearCorrelationRole(userId);
	}
	
}
