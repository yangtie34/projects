package test.test1.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.gilight.framework.uitl.SystemHttpUtil;
import com.jhnu.syspermiss.util.ContextHolderUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	
//	@Autowired
//	private UserService userService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView getuserList(){
		
		//获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal(); 
	    String username = principal.getName();  
	    
	    
		List<Object> list=new ArrayList<Object>();
		list.add(username);
		
		//获取 登陆者所拥有的菜单
		List<Map<String,Object>> menus=(List<Map<String,Object>>)SystemHttpUtil.rpcPost("xgxt","resourcesService","getMenusByUserName",list); 
		
		//获取 登陆者所有拥有的权限
		List<String> perm=(List<String>)SystemHttpUtil.rpcPost("xgxt","resourcesService","getAllPermssionByUserName",list); 
		
		//验证 登陆者是否有该权限
		String checkShiro="qx:zy:view";
		list.add(checkShiro);
		boolean hasPerm=(Boolean)SystemHttpUtil.rpcPost("xgxt","resourcesService","hasPermssion",list); 
		
		//获取 登陆者所拥有该权限中的数据权限的SQL
		String dataSql=(String)SystemHttpUtil.rpcPost("xgxt","dataServeService","getDataServeSqlbyUserIdShrio",list); 
		
		
		
		//验证 登陆者是否具有该人员在该资源的数据权限
		list=new ArrayList<Object>();
		String chekcUser="201315040003";
		list.add(chekcUser);
		list.add(username);
		list.add(checkShiro);
		boolean hasData=(Boolean)SystemHttpUtil.rpcPost("xgxt","dataServeService","hasThisOnePerm",list);
		
		
		
		System.out.println("登陆者为："+username);
		System.out.println("拥有的菜单："+menus);
		System.out.println("拥有的所有权限代码："+perm);
		System.out.println("验证是否有'"+checkShiro+"'权限："+hasPerm);
		System.out.println("登陆者对'"+checkShiro+"'权限的数据范围SQL："+dataSql);
		System.out.println("登陆者所拥有该权限中的数据权限的SQL:"+dataSql);
		System.out.println("登陆者是否具有人员'"+chekcUser+"'在'"+checkShiro+"'的数据权限："+hasData);
		ModelAndView mv=null;
	//	hasData=false;
		if(hasData){
			mv=new ModelAndView("/user");
		}else{
			mv=new ModelAndView("/logout");
		}
		
		
		return mv;
	}
	
	@RequestMapping(value="/out",method=RequestMethod.GET)
	public ModelAndView logout(){
		System.out.println("============================================");
		ModelAndView mv=new ModelAndView("/logout");
		return mv;
	}
	
}
