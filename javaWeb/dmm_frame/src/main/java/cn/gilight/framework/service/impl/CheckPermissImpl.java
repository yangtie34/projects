package cn.gilight.framework.service.impl;

import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.GetCachePermiss;

import cn.gilight.framework.service.CheckPermiss;
import cn.gilight.framework.uitl.common.UserUtil;


@Service("checkPermiss")
public class CheckPermissImpl implements CheckPermiss{

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
