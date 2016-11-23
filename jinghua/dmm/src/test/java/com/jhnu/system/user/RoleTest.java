package com.jhnu.system.user;


import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.spring.SpringTest;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.service.RoleService;

public class RoleTest extends SpringTest{
	
	@Resource
	private RoleService roleService;
	
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	@Test
	public void testCreate(){
		Role role = new Role();
		role.setName_("adm");
		role.setDescription("adm");
		role.setIstrue(1);
		System.out.println("==========修改开始==========");
		Role r;
		try {
			r = roleService.createRole(role);
			System.out.println(r.getId());
		} catch (AddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("==========修改结束==========");
	}
	
	/*@Test
	public void testQuery(){
		Role role = new Role();
		//role.setId(1L);
		//role.setName_("admin");
		role.setIstrue(1);
		System.out.println("==========查询开始==========");
		List<Role> roleList = roleService.getAllRole(role);
		if(roleList != null){
			for(Role r : roleList){
				System.out.println(r.getName_()+","+r.getDescription());
			}
		}
		Page page = roleService.getPageRole(3, 1, role);
		List<Role> lr = (List<Role>) page.getResultListObject();
		if(lr != null){
			for(Role r : lr){
				System.out.println(r.getName_()+","+r.getDescription());
			}
		}
		System.out.println("==========查询结束==========");
	}
	@Test
	public void testUpdate(){
		Role role = new Role();
		role.setId(1L);
		role.setName_("adm");
		System.out.println("==========修改开始==========");
		roleService.updateRole(role);
		System.out.println("==========修改结束==========");
	}*/

}
