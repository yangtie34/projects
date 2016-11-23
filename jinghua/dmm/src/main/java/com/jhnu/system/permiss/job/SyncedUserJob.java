package com.jhnu.system.permiss.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.common.teacher.entity.Teacher;
import com.jhnu.product.common.teacher.service.TeacherService;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserRole;
import com.jhnu.system.permiss.service.PasswordHelper;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.product.EduUtils;
import com.jhnu.util.product.Globals;

@DisallowConcurrentExecution
public class SyncedUserJob implements Job{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StuService stuService;
	
	@Autowired
	private TeacherService teaService;
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	private static final Logger logger = Logger.getLogger(SyncedUserJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("：从学生表中同步学生数据到用户表，开始...");	
		List<Student> stus = stuService.getStusInSchoolNotInUser();
		// 学生对象转化为用户对象以持久化
		List<User> users = new ArrayList<User>();
		for(Student stu : stus){
			String stuNo = stu.getNo_();
			User user = new User(stuNo,EduUtils.getPasswordByIdno(stu.getIdno(), stuNo));
			user.setReal_name(stu.getName_());
			user.setDept_id(stu.getDept());
			user.setIstrue(1);
			passwordHelper.encryptPassword(user);
			users.add(user);
		}
		userService.createUsers(users);
		
		List<UserRole> userRoles = new ArrayList<UserRole>();
		
		for(User user : users){
			userRoles.add(new UserRole(user.getId(),Globals.ROLE_STUDENT_ID));
		}
		userService.correlationRoles(userRoles);
		
		logger.info("：从学生表中同步学生数据到用户表，结束！");
		
		logger.info("：从教师表中同步教师数据到用户表，开始...");
		List<Teacher> teas = teaService.getTeasInSchoolNotInUser();
		// 教师对象转化为用户对象以持久化
		users.clear();
		for(Teacher tea : teas){
			String teaNo = tea.getNo_();
			User user = new User(teaNo,EduUtils.getPasswordByIdno(tea.getIdno(), teaNo));
			user.setReal_name(tea.getName_());
			user.setDept_id(tea.getDeptName());
			user.setIstrue(1);
			passwordHelper.encryptPassword(user);
			users.add(user);
		}
		userService.createUsers(users);
		userRoles.clear();
		
		for(User user : users){
			userRoles.add(new UserRole(user.getId(),Globals.ROLE_TEACHER_ID));
		}
		userService.correlationRoles(userRoles);
		
		logger.info("：从教师表中同步教师数据到用户表，结束！");
	}

}
