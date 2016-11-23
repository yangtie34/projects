package com.jhnu.product.mosdc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.framework.entity.JobResultBean;
import com.jhnu.product.mosdc.dao.UserSynDao;
import com.jhnu.product.mosdc.service.UserSynService;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserRole;
import com.jhnu.system.permiss.service.PasswordHelper;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.product.EduUtils;
import com.jhnu.util.product.Globals;


@Service("userSynService")
public class UserSynServiceImpl implements UserSynService {
	@Autowired
	private UserSynDao userSynDao;
	@Autowired
	private PasswordHelper passwordHelper;
	@Autowired
	private UserService userService;
	@Override
	public JobResultBean userSyn4Stu() {
		List<Map<String,Object>> list = this.userSynDao.getStusNotInUser();
		JobResultBean jrb=this.getJobObject(list, "XH","XH",Globals.ROLE_STUDENT_ID);
		jrb.setIsTrue(true);
		return jrb;
	}

	@Override
	public JobResultBean userSyn4Jzg() {
		List<Map<String,Object>> list = this.userSynDao.getJzgsNotInUser();
		JobResultBean jrb=this.getJobObject(list, "ZGH","ZGH", Globals.ROLE_TEACHER_ID);
		jrb.setIsTrue(true);
		return jrb;
	}

	@Override
	public JobResultBean userSyn4WelStu() {
		List<Map<String,Object>> list = this.userSynDao.getWelStusNotInUser();
		JobResultBean jrb=this.getJobObject(list,"XH","KSH",Globals.ROLE_STUDENT_ID);
		jrb.setIsTrue(true);
		return jrb;
	}
	//具体执行方法
	private JobResultBean getJobObject(List<Map<String,Object>> list,String pno,String pno1,Long roleType){
		JobResultBean jrb=new JobResultBean();
		String create_time=DateUtils.SSS.format(new Date());
		User user =null;
		List<User> users = new ArrayList<User>() ;
		List<UserRole> userRoles = new ArrayList<UserRole>();
		for(Map<String,Object> xs:list){
			String stuNo = MapUtils.getString(xs, pno);
			if(stuNo==null){
				stuNo = MapUtils.getString(xs, pno1);
			}
			String sfzh = MapUtils.getString(xs, "SFZH");
			if(stuNo!=null && !"".equals(stuNo)){
				user = new User(stuNo,EduUtils.getPasswordByIdno(sfzh, stuNo));
				user.setId(MapUtils.getLong(xs, "ID"));
				user.setReal_name(MapUtils.getString(xs, "XM"));
				user.setDept_id(MapUtils.getString(xs, "YX_ID"));
				user.setIstrue(1);
				user.setCreate_time(create_time);
				user.setId_no(sfzh);
				passwordHelper.encryptPassword(user);
				users.add(user);
			}
		}
		if(users.size()!=0){
			userService.createUsers(users);
			List<Long> userIds=userService.getUserIdsByAddTime(create_time);
			for(Long id : userIds){
				userRoles.add(new UserRole(id,roleType));
			}
			userService.correlationRoles(userRoles);
			jrb.setMsg("synNum："+list.size());
		}
		return jrb;
	}


}
