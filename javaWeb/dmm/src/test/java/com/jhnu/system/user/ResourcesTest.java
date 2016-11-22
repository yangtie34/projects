package com.jhnu.system.user;




import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.spring.SpringTest;
import com.jhnu.system.permiss.entity.Resources;
import com.jhnu.system.permiss.service.ResourcesService;

public class ResourcesTest extends SpringTest{
	
	@Resource
	private ResourcesService resourcesService;
	
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	@Test
	public void testCreate(){
		System.out.println("==========创建开始==========");
		Resources r = new Resources();
		r.setDescription("资源12");
		r.setName_("资源12");
		r.setIstrue(1);
		r.setKeyWord("2");
		r.setLevel_(2);
		r.setOrder_(3);
		r.setPath_("00000010");
		r.setPid(0L);
		r.setResource_type("02");
		r.setShiro_tag("2");
		r.setUrl_("resources/resources12");
		try {
			resourcesService.createResource(r);
		} catch (AddException | ParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(re.getId());
		System.out.println("==========创建结束==========");
	}
	/*@Test
	public void testDelete(){
		System.out.println("==========删除开始==========");
		resourcesService.deleteResources(3L);
		System.out.println("==========删除结束==========");
	}*/
	
	/*@Test
	public void testQuery(){
		Resources r = new Resources();
		r.setIstrue(0);
		r.setLevel_(2);
		System.out.println("==========查询开始==========");
		//List<Resources> lr = resourcesService.getAllResources();
		List<Resources> lr = resourcesService.getResourcesByThis(r);
		if(lr!=null){
			for(Resources re : lr){
				System.out.println(re.getUrl_()+","+ re.getName_());
			}
		}
		System.out.println("==========查询结束==========");
	}*/
	/*@Test
	public void testUpdate(){
		Resources r = new Resources();
		r.setId(1L);
		r.setDescription("资源11");
		r.setName_("资源11");
		r.setResource_type("03");
		r.setPid(0L);
		r.setKeyWord("1");
		r.setLevel_(2);
		r.setOrder_(1);
		r.setPath_("00000001");
		r.setShiro_tag("11");
		r.setUrl_("resources/resources11");
		System.out.println("==========修改开始==========");
		resourcesService.updateResources(r);
		System.out.println("==========修改结束==========");
	}*/

}
