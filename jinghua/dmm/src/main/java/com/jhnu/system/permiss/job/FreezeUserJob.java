package com.jhnu.system.permiss.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.service.UserService;

@DisallowConcurrentExecution
public class FreezeUserJob implements Job{
	@Autowired
	private StuService stuService;
	@Autowired
	private UserService userService;
	
	private static final Logger logger = Logger.getLogger(FreezeUserJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
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
	}

	
}
