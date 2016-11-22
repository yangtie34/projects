package cn.gilight.framework.permiss.service.impl;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.GetCachePermiss;

import cn.gilight.framework.permiss.service.CheckPermiss;
import cn.gilight.framework.uitl.common.ContextHolderUtils;


@Service("checkPermiss")
public class CheckPermissImpl implements CheckPermiss{

	@Override
	public boolean checkPermiss(String shiroTag) {
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = null;  
		if(principal!=null){
			username = principal.getName();  
		}
		if(username!=null){
			if(GetCachePermiss.hasPermssion(username, shiroTag)){
				return true;
			}
		}
		return false;
	}

}
