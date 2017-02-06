package cn.gilight.framework.permiss.controller;

import java.util.Map;
import java.util.Set;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.GetCachePermiss;
import com.jhnu.syspermiss.util.ContextHolderUtils;

@Controller
@RequestMapping("/permiss")
public class PermissionController {
	@RequestMapping("/getPermission")
	@ResponseBody	
	public Object getData(@RequestParam Map<String,Object> args){
		//获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal(); 
	    String username = principal.getName();  
	    System.out.println(username);
	    Set<String> permissions =  GetCachePermiss.findPermissions(username);
	    System.out.println(permissions);
	    for (String pers : permissions) {
			System.out.println(pers);
		}
		return permissions;
	}
}
 