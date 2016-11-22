package com.jhnu.syspermiss.permiss.service.impl;

import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.GetCachePermiss;
import com.jhnu.syspermiss.permiss.service.CheckPermiss;
import com.jhnu.syspermiss.util.UserUtil;



@Service("checkPermiss")
public class CheckPermissImpl implements CheckPermiss{
	private CheckPermissImpl() {
		
	}  
    private static CheckPermissImpl CheckPermissImpl=null;
	
	public static CheckPermissImpl getInstance() {
		if (CheckPermissImpl == null){
			synchronized (new String()) {
				if (CheckPermissImpl == null){
					CheckPermissImpl = new CheckPermissImpl();
				}
			}
		}
		return CheckPermissImpl;
	}
	@Override
	public boolean checkPermiss(String shiroTag) {
		String username=UserUtil.getCasLoginName();
		if(username!=null){
			if(GetCachePermiss.hasPermssion(username, shiroTag)){
				return true;
			}
		}
		return false;
	}

}
