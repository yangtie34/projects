package com.jhnu.system.permiss.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.handle.HasOneException;
import com.jhnu.framework.exception.param.EmptyParamException;
import com.jhnu.framework.exception.param.FormatParamException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.product.common.stu.dao.StuDao;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.teacher.dao.TeacherDao;
import com.jhnu.product.common.teacher.entity.Teacher;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.dao.UserDao;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserExpandInfo;
import com.jhnu.system.permiss.entity.UserRole;
import com.jhnu.system.permiss.service.PasswordHelper;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.product.Globals;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private StuDao stuDao;
	
	@Autowired
	private TeacherDao teacherDao;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Override
	@Transactional
    public User createUser(User user) throws AddException,ParamException {
		User uu = null;
		if(user != null){
			if(StringUtils.isEmpty(user.getUsername())){
				throw new EmptyParamException("用户名不能为空");
			}else{
				User u = userDao.findByUsername(user.getUsername());
				if(u != null){
					throw new HasOneException("用户名已经存在");
				}
			}
			if(StringUtils.isEmpty(user.getPassword())){
				throw new EmptyParamException("密码不能为空");
			}
			if(StringUtils.isEmpty(user.getReal_name())){
				throw new EmptyParamException("真实名字不能为空");
			}
			if(user.getUsername().indexOf("@")>=0){
				throw new FormatParamException("数据内容异常");
			}
			//加密密码
			logger.info("====创建用户密码加密开始====");
	        passwordHelper.encryptPassword(user);
	        logger.info("====创建用户密码加密结束====");
	        logger.info("====创建用户开始保存====");
	        uu = userDao.createUser(user);
	        logger.info("====创建用户结束保存====");
		}
		return uu;
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user =userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) throws ParamException{
    	if(StringUtils.isEmpty(userId)){
    		throw new EmptyParamException("用户ID不能为空");
    	}
    	logger.info("====开始批量添加用户角色，添加"+roleIds.length+"个角色====");
        userDao.correlationRoles(userId, roleIds);
        logger.info("====批量添加用户结束====");
    }
	
    @Override
	public void correlationRoles(List<UserRole> userRoles) {
    	logger.info("====开始批量添加用户角色，添加"+userRoles.size()+"条数据====");
		userDao.correlationRoles(userRoles);
		logger.info("====批量添加用户角色结束====");
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
    public void addUserLogging(String username, String loginWay) {
    	String time = DateUtils.SSS.format(new Date());
    	logger.info("====保存用户登录时间方式开始====");
    	userDao.addUserLogging(username,time, loginWay);
    	logger.info("====保存用户登录时间方式结束====");
    }
    
    @Override
    public int[] freezeUsers(List<String> userIds) {
    	List<Long> l=new ArrayList<Long>();
    	for(String id:userIds){
    		l.add(Long.parseLong(id));
    	}
    	logger.info("====开始冻结用户列表，冻结"+l.size()+"个用户====");
    	logger.info("====冻结用户列表结束====");
    	return userDao.freezeUsers(l);
    	
    }
    
    @Override
    public void unfreezeUsers(List<String> userIds) {
    	List<Long> l=new ArrayList<Long>();
    	for(String id:userIds){
    		l.add(Long.parseLong(id));
    	}
    	logger.info("====开始解冻用户列表，解冻"+l.size()+"个用户====");
    	userDao.unfreezeUsers(l);
    	logger.info("====解冻用户列表结束====");
    }
    
    @Override
    public List<User> createUsers(List<User> users) {
    	logger.info("====开始批量新增用户，新增"+users.size()+"个用户====");
    	userDao.createUsers(users);
    	logger.info("====批量新增用户结束====");
    	return users;
    }
    
   @Override
	public String getUserLastLoginTime(String userName) {
	   return null;
	}
   
    @Override
    public UserExpandInfo getUserExpandInfo(String userName) {
    	UserExpandInfo uei = new UserExpandInfo();
    	Set<String> roles = findRoles(userName);
    	String lastLoginTime = userDao.getUserLastLoginTime(userName);
    	if(roles.contains(Globals.ROLE_ADMIN)){
    		uei = new UserExpandInfo(userName, "系统管理员", "系统管理员", lastLoginTime,null,"");
    	}else if(roles.contains(Globals.ROLE_TEACHER)){
    		//TODO 下面的那句教师查询语句有错误
    		Teacher tea = teacherDao.getTeacherInfo(userName);
    		uei = new UserExpandInfo(userName, tea.getName_(), tea.getDeptName(), lastLoginTime,tea.getSex(),tea.getEnrollDate());
    	}else if(roles.contains(Globals.ROLE_STUDENT)){
    		Student stu = stuDao.getStudentInfo(userName);
    		uei = new UserExpandInfo(userName, stu.getName_(), stu.getDept()+"/"+stu.getMajor(), lastLoginTime,stu.getSex(),stu.getEnrolldate());
    	}
    	return uei;
    }
    
	@Override
	public boolean checkPassword(Long userId, String password) {
		User user =userDao.findOne(userId);
		String oldPwd=user.getPassword();
        user.setPassword(password);
        passwordHelper.encryptPassword(user,user.getSalt());
        if(oldPwd.equals(user.getPassword())){
        	return true;
        }else{
        	return false;
        }
	}
	
	@Override
	public List<String> getRoleNamesByUserId(long userId) {
		return userDao.getRoleNamesByUserId(userId);
	}
	
	@Override
	public Page getPageUsers(int currentPage, int numPerPage,User user) {
		return userDao.getPageUsers(currentPage, numPerPage, user);
	}
	
	@Override
	public List<User> getAllUsers(User user) {
		return userDao.getAllUsers(user);
	}
	
	@Override
	public void deleteUserById(Long id){
		logger.info("====开始删除用户====");
		userDao.deleteUserById(id);
		logger.info("====删除用户结束====");
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
	public void resetPassword(Long userId) {
		//TODO 当前是将重置密码设置为123456
		logger.info("====开始重置密码====");
		changePassword(userId,"123456");
		logger.info("====重置密码结束====");
	}

	@Override
	public ResultBean addUserRoleAjax(String userId,String roleIds) {
		ResultBean rb = new ResultBean();
		String[] rids = roleIds.split(",");
		Long[] roleId = new Long[rids.length];
		for(int i=0;i<rids.length;i++){
			roleId[i] = Long.parseLong(rids[i]);
		}
		try {
			correlationRoles(Long.parseLong(userId), roleId);
			rb.setTrue(true);
		} catch (ParamException e) {
			rb.setTrue(false);
			rb.setName(e.getMessage());
		}
		
		return rb;
	}
}
