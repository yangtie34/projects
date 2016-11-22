package com.jhnu.system.permiss.job.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.enums.DeptEnum;
import com.jhnu.framework.entity.JobResultBean;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.common.teacher.entity.Teacher;
import com.jhnu.product.common.teacher.service.TeacherService;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserRole;
import com.jhnu.system.permiss.job.UserJob;
import com.jhnu.system.permiss.service.PasswordHelper;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.product.EduUtils;
import com.jhnu.util.product.Globals;

@Service("userJob")
public class UserJobImpl implements UserJob{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StuService stuService;
	
	@Autowired
	private TeacherService teaService;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	private static final Logger logger = Logger.getLogger(UserJobImpl.class);
	
	@Transactional
	public JobResultBean SyncedUserJob(){
		JobResultBean jrb=new JobResultBean();
		List<User> users = new ArrayList<User>() ;
		List<UserRole> userRoles = new ArrayList<UserRole>();
		List<Student> stus =new ArrayList<Student>();
		logger.info("：从学生表中同步学生数据到用户表，开始...");	
		String create_time=DateUtils.SSS.format(new Date());
		try {
			stus = stuService.getStusInSchoolNotInUser();
		// 学生对象转化为用户对象以持久化
		for(Student stu : stus){
			String stuNo = stu.getNo_();
			User user = new User(stuNo,EduUtils.getPasswordByIdno(stu.getIdno(), stuNo));
			user.setReal_name(stu.getName_());
			user.setDept_id(stu.getDept());
			user.setIstrue(1);
			user.setCreate_time(create_time);
			user.setDept_category_code(DeptEnum.JX.getCode());
			user.setId_no(stu.getIdno());
			passwordHelper.encryptPassword(user);
			users.add(user);
		}
		userService.createUsers(users);
		
		List<Long> userIds=userService.getUserIdsByAddTime(create_time);
		
		for(Long id : userIds){
			userRoles.add(new UserRole(id,Globals.ROLE_STUDENT_ID));
		}
		userService.correlationRoles(userRoles);
		}catch(Exception e){
			jrb.setIsTrue(false);
			jrb.setMsg("同步学生时错误:"+e.getCause());
			return jrb;
		}
		logger.info("：从学生表中同步学生数据到用户表，结束！");
		
		logger.info("：从教师表中同步教师数据到用户表，开始...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		List<Teacher> teas = new ArrayList<Teacher>();
		create_time=DateUtils.SSS.format(new Date());
		try {
			teas = teaService.getTeasInSchoolNotInUser();
		// 教师对象转化为用户对象以持久化
		users.clear();
		for(Teacher tea : teas){
			String teaNo = tea.getNo_();
			User user = new User(teaNo,EduUtils.getPasswordByIdno(tea.getIdno(), teaNo));
			user.setReal_name(tea.getName_());
			user.setDept_id(tea.getDeptName());
			user.setIstrue(1);
			user.setCreate_time(create_time);
			user.setId_no(tea.getIdno());
			user.setDept_category_code(DeptEnum.ALL.getCode());
			passwordHelper.encryptPassword(user);
			users.add(user);
		}
		userService.createUsers(users);
		userRoles.clear();
		
		List<Long> userIds=userService.getUserIdsByAddTime(create_time);
		
		for(Long id : userIds){
			userRoles.add(new UserRole(id,Globals.ROLE_TEACHER_ID));
		}
		userService.correlationRoles(userRoles);
		}catch(Exception e){
			jrb.setIsTrue(false);
			jrb.setMsg("同步教师时错误:"+e.getCause());
			return jrb;
		}
		logger.info("：从教师表中同步教师数据到用户表，结束！");
		jrb.setIsTrue(true);
		jrb.setMsg("同步学生人数："+stus.size()+"人，教师人数："+teas.size()+"人。");
		return jrb;
	}
	
	@Transactional
	public JobResultBean FreezeUserJob(){
		logger.info("：批量冻结用户，开始...");
		List<Student> stus = stuService.getStusGraduated();
		// 学生对象转化为用户对象以持久化
		List<Long> userIds = new ArrayList<Long>();
		for(Student stu : stus){
			String stuNo = stu.getNo_();
			User user = userService.findByUsername(stuNo);
			if(user==null) continue;
			userIds.add(user.getId());
		}
//		userService.freezeUsers(userIds);
		
		logger.info("：批量冻结用户，结束！");
		
		JobResultBean jrb=new JobResultBean();
		return jrb;
	}
}
