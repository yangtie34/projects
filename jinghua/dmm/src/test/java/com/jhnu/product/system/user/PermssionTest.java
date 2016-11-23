package com.jhnu.product.system.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.spring.SpringTest;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.entity.RolePermssion;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserRole;
import com.jhnu.system.permiss.service.DataServeService;
import com.jhnu.system.permiss.service.PermssionService;
import com.jhnu.system.permiss.service.ResourcesService;
import com.jhnu.system.permiss.service.RoleService;
import com.jhnu.system.permiss.service.UserService;

public class PermssionTest extends SpringTest{
	
	@Resource
	private PermssionService permssionService;
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private DataServeService dataServeService;
	
	
//	@Test
	public void testaa(){
		List<RolePermssion> p=permssionService.getRolePermssion(null);
		System.out.println(p.size());
	}
	
//	@Test
	public void userTest(){
		User user=new User();
		user.setIstrue(1);
		user.setPassword("123");
		user.setReal_name("张三");
		user.setUsername("123");
			try {
				userService.createUser(user);
			} catch (AddException e) {
				System.out.println(e.toString());
				System.out.println(e.getMessage());
				System.out.println(e.getCause());
		//		e.printStackTrace();
			} catch (ParamException e) {
				e.printStackTrace();
			}
	//	userService.changePassword(1000000847l, "123");
		Long[] d=new Long[4];
		d[0]=2l;
		d[1]=1000000848l;
		d[2]=1l;
		d[3]=4l;
		List<UserRole> list=new ArrayList<UserRole>();
		UserRole u=new UserRole();
		u.setUserId(1000000847l);
		u.setRoleId(2l);
		list.add(u);
		UserRole u2=new UserRole();
		u2.setUserId(1000000847l);
		u2.setRoleId(1000000848l);
		list.add(u2);
//		userService.correlationRoles(list);
//		userService.correlationRoles(1000000847l,d);
		
//		Set<String> dd=userService.findRoles("123");
//		System.out.println("d");
		
	}
	
//	@Test
	public void roleTest(){
		Role role=new Role();
		role.setIstrue(1);
		role.setName_("aada");
		role.setDescription("测试");
//		roleService.createRole(role);
		
		Long[] d=new Long[4];
		d[0]=2l;
		d[1]=1000000848l;
		d[2]=1l;
		d[3]=4l;
	//	roleService.addUserRoles(1000000848l, d);
		roleService.deleteRole(1000000895l);
	}
	
//	@Test
	public void resTest(){
		
	//	resourcesService.createResource(resource);
		
		
	}
	
//	@Test
	public void permTest(){
		
	}
	
	@Test
	public void dataTest(){
		String sql=dataServeService.getSqlbyDataServe(dataServeService.getDataServe("admin", "ddddd"));
		System.out.println(sql);
	}
	
}
