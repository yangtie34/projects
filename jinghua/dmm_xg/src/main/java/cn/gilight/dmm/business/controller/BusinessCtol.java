package cn.gilight.dmm.business.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.UserUtil;

import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.framework.uitl.HttpResult;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年12月20日 下午8:29:45
 */
@Controller
@RequestMapping("business")
public class BusinessCtol {

	@Resource
	private BusinessService businessService;
	

	/** 
	 * 获取登录信息
	 */
	@RequestMapping("/home/logininfo")
	@ResponseBody
	public HttpResult getLoginInfo(){
		HttpResult result = new HttpResult();
		Map<String,Object> info = new HashMap<String,Object>();
		info.put("loginUser", UserUtil.getCasLoginName());
		info.put("changePasswdSysUrl", businessService.queryChangePasswdSystemUrl());
		result.setResult(info);
		result.setSuccess(true);
		return result;
	}
	
}
