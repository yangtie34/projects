
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jhnu.framework.entity.ResultBean;
import com.jhnu.system.permiss.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mvc.xml","classpath:spring-config.xml"})
public class BookTest {
	
	@Resource
	private UserService userService;
	
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
		
	}
	
	@Test
	public void initBook(){
		for(int i=0;i<10;i++){
			ResultBean result=userService.updateUserpwdAjax("admin", "123456", "123456");
			System.out.println(result.getIsTrue());
			System.out.println(result.getName());
		}
		
	}
}