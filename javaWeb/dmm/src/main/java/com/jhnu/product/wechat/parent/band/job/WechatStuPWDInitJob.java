package com.jhnu.product.wechat.parent.band.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.wechat.parent.band.entity.WechatPwd;
import com.jhnu.product.wechat.parent.band.service.WechatPwdService;
import com.jhnu.system.permiss.job.SyncedUserJob;
import com.jhnu.system.permiss.service.PasswordHelper;
import com.jhnu.util.product.EduUtils;

public class WechatStuPWDInitJob implements Job {
	@Autowired
	private WechatPwdService  wechatPwdService;
	@Autowired
	private PasswordHelper passwordHelper;
	
	private static final Logger logger = Logger.getLogger(SyncedUserJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("：从学生表中同步数据至学生微信密码表，开始...");
		logger.info("-------------------------开始获取-----------------------");
		List<Student> stus = wechatPwdService.getStusInSchoolNotInWechat();
		logger.info("-------------------------获取结束-----------------------");
		// 学生对象转化为用户对象以持久化
		List<WechatPwd> pwds = new ArrayList<WechatPwd>();
		for(Student stu : stus){
			String stuNo = stu.getNo_();
			WechatPwd pwd = new WechatPwd();
			pwd.setIs_change(0);
			pwd.setPhone_no("");
			String password = EduUtils.getPasswordByIdno(stu.getIdno(), stuNo);
			pwd.setPwd(passwordHelper.simpleEncryptPassword(stuNo,password));
			pwd.setStu_id(stuNo);
			
			pwds.add(pwd);
			logger.info(stu.getName_()+":"+pwd);
		}
		
		logger.info("-------------------------开始插入-----------------------");
		wechatPwdService.addWechatPasswords(pwds);
		
		logger.info("：从学生表中同步数据至学生微信密码表，结束...");
	}
}
