package com.jhnu.product.wechat.parent.band;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.wechat.parent.band.entity.WechatPwd;
import com.jhnu.product.wechat.parent.band.service.WechatPwdService;
import com.jhnu.spring.SpringTest;
import com.jhnu.system.permiss.service.impl.PasswordHelperImpl;
import com.jhnu.util.product.EduUtils;


public class InitWechatPwdTest extends SpringTest {
	@Resource
	private WechatPwdService wechatPwdService;
	@Autowired
	private PasswordHelperImpl passwordHelper;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void testaa(){
		System.out.println("-------------------------开始获取-----------------------");
		List<Student> stus = wechatPwdService.getStusInSchoolNotInWechat();
		System.out.println("-------------------------获取结束-----------------------");
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
			System.out.println(stu.getName_()+":"+pwd);
		}
		
		System.out.println("-------------------------开始插入-----------------------");
		wechatPwdService.addWechatPasswords(pwds);
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

}
