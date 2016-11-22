package com.jhnu.system.user;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.common.teacher.entity.Teacher;
import com.jhnu.product.common.teacher.service.TeacherService;
import com.jhnu.spring.SpringTest;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserRole;
import com.jhnu.system.permiss.service.PasswordHelper;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.product.EduUtils;
import com.jhnu.util.product.Globals;

public class SyncedUser extends SpringTest{
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
		@Test
		public void testSyncedUser() {
			/*System.out.println("：从用户表中删除除管理员用户，开始...");
			String sqlUser="delete from t_sys_user where username!='admin' ";
			baseDao.getBaseDao().getJdbcTemplate().execute(sqlUser);
			System.out.println("：从用户表中删除除管理员用户，结束！");
			System.out.println("：从用户角色表中删除除管理员用户对应角色，开始...");
			String sqlUserRole="delete from t_sys_user_role where user_id>'1' ";
			baseDao.getBaseDao().getJdbcTemplate().execute(sqlUserRole);
			System.out.println("：从用户角色表中删除除管理员用户对应角色，结束");*/
			System.out.println("：从学生表中同步学生数据到用户表，开始...");	
			String sql ="SELECT NO_,NAME_,IDNO,DEPT_ID DEPT FROM T_STU STU WHERE NOT EXISTS (SELECT 1 FROM T_SYS_USER USER_ WHERE USER_.USERNAME = STU.NO_) AND STU.STU_STATE_CODE=1 and rownum<500";
			List<Student> stus = (List<Student>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Student.class));
			//List<Student> stus = stuService.getStusInSchoolNotInUser();
			// 学生对象转化为用户对象以持久化
			System.out.println(stus.size());
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
			
			System.out.println("：从学生表中同步学生数据到用户表，结束！");
			
			System.out.println("：从教师表中同步教师数据到用户表，开始...");
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
			
			System.out.println("：从教师表中同步教师数据到用户表，结束！");
		}
}
