package cn.gilight.business.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.UserUtil;

import cn.gilight.business.service.BusinessService;
import cn.gilight.business.service.SelfDefinedCodeService;
import cn.gilight.framework.uitl.HttpResult;

@Controller
@RequestMapping("/business")
public class BusinessController {
	
	@Resource
	private SelfDefinedCodeService selfService;
	
	@Resource
	private BusinessService businessService;
	
	/** 
	* 标准代码
	*/
	@RequestMapping(value="/standardcode/codetype",method=RequestMethod.POST)
	@ResponseBody	
	public Object getData(String codeType){
		return businessService.queryBzdmListMap(codeType);
	}

	/** 
	* 自定义年份选择
	*/
	@RequestMapping(value="/selfdefined/year",method=RequestMethod.POST)
	@ResponseBody
	public Object getSelfDefinedYear(String codeType){
		return selfService.getSelfDefinedYearCode();
	}
	
	/** 
	* 自定义年份选择(近10年)
	*/
	@RequestMapping(value="/selfdefined/years",method=RequestMethod.POST)
	@ResponseBody
	public Object getSelfDefinedYears(String codeType){
		return selfService.getYears();
	}
	
	/** 
	*  学科门类
	*/
	@RequestMapping(value="/code/subject",method=RequestMethod.POST)
	@ResponseBody
	public Object getCodeSubject(){
		return businessService.queryCodeSubject();
	}
	
	/** 
	*  行政组织机构
	*/
	@RequestMapping(value="/code/depttree",method=RequestMethod.POST)
	@ResponseBody
	public Object getDeptDataService(String shiroTag){
		return  businessService.getDeptDataPermissTree(shiroTag);
	}
	
	/** 
	 *  行政组织机构
	 */
	@RequestMapping(value="/code/teachdepttree",method=RequestMethod.POST)
	@ResponseBody
	public Object getTeachDeptDataService(String shiroTag){
		return  businessService.getTeachDeptDataPermissTree(shiroTag);
	}
	
	/** 
	 *  获取登录信息
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
